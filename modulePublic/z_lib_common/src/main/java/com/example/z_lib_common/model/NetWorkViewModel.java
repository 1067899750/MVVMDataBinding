package com.example.z_lib_common.model;

import android.app.Application;

import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_base.base.BaseViewModel;
import com.example.z_lib_base.model.IBaseModelListener;
import com.example.z_lib_base.untils.log.XLog;
import com.example.z_lib_net.base.BaseResponse;

import androidx.annotation.NonNull;

/**
 * @author puyantao
 * @describe 需要网络请求的 ViewModel
 * @create 2020/4/17 15:08
 */
public abstract class NetWorkViewModel<M extends BaseModel, D> extends BaseViewModel<M> implements IBaseModelListener<D> {

    public NetWorkViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        model.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        model.unRegister(this);
    }


    @Override
    public void onLoadFinish(BaseModel model, D data, String tag) {
        finishLoadData();
    }

    @Override
    public void onLoadFail(BaseModel model, String message) {
        finishLoadData();
    }

    /**
     *  结束加载数据
     */
    protected abstract void finishLoadData();

}

















