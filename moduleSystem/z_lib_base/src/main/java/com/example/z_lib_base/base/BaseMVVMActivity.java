package com.example.z_lib_base.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.z_lib_base.bus.Messenger;
import com.example.z_lib_base.base.intercepter.IBaseView;
import com.example.z_lib_base.widget.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/14 16:16
 */
public abstract class BaseMVVMActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseView {
    protected V mBinding;
    protected VM mViewModel;
    private int viewModelId;
    private Dialog mDialog;

    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPersimmions();
        //页面接受的参数方法
        initParam();
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        mViewModel.registerRxBus();
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除Messenger注册
        Messenger.getDefault().unregister(mViewModel);
        if (mViewModel != null){
            mViewModel.removeRxBus();
        }
        if (mBinding != null){
            mBinding.unbind();
        }
    }

    /**
     * 注入绑定
     *
     * @param savedInstanceState
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        mBinding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        viewModelId = initVariableId();
        mViewModel = initViewModel();
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        mBinding.setVariable(viewModelId, mViewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        mBinding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(mViewModel);
        mViewModel.injectLifecycleProvider(this);

    }


    /**
     * 刷新布局
     */
    public void refreshLayout() {
        if (mViewModel != null) {
            mBinding.setVariable(viewModelId, mViewModel);
        }
    }

    /**
     * ====================================注册ViewModel与View的契约UI回调事件=================================
     **/
    private void registerUIChangeLiveDataCallBack() {
        //加载对话框显示
        mViewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showDialog(s);
            }
        });
        //加载对话框消失
        mViewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                dismissDialog();
            }
        });

        //跳入新页面
        mViewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle build = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, build);
            }
        });
        //跳入ContainerActivity
        mViewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        mViewModel.getUC().getFinishEvent().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                finish();
            }
        });
        //带参关闭界面
        mViewModel.getUC().getFinishParamsEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> stringObjectMap) {
                Bundle bundle = (Bundle) stringObjectMap.get(BaseViewModel.ParameterField.BUNDLE);
                Intent intent = getIntent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        //关闭上一层
        mViewModel.getUC().getOnBackPressedEvent().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                onBackPressed();
            }
        });
        //toast显示
        mViewModel.getUC().getShowToastEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(BaseMVVMActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void showDialog(String title) {
        if (mDialog != null) {
            mDialog.show();
        } else {
            mDialog = new LoadingDialog.Builder(this).create();
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }


    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    /**
     * =====================================================================
     **/
    @Override
    public void initParam() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    /**
     * 创建ViewModel, 重写该方法新构建初始化数据, 不重新构建默认传入的 ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }
}














