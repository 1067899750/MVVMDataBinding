package com.example.z_model_main.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.z_lib_base.base.BaseViewModel;
import com.example.z_lib_base.bus.event.SingleLiveEvent;
import com.example.z_lib_base.bus.command.BindingCommand;
import com.example.z_lib_base.bus.command.BindingConsumer;
import com.example.z_lib_common.arouter.ARouterUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import q.rorbin.badgeview.QBadgeView;


/**
 * @author puyantao
 * @describe
 * @create 2020/4/15 11:13
 */
public class MainViewModel extends BaseViewModel {
    public ArrayList<String> mFragments = new ArrayList<>();

    /**
     * 封装一个界面发生改变的观察者
     */
    public UIChangeObservable uc = new UIChangeObservable();

    public MainViewModel(@NonNull Application application) {
        super(application);
        mFragments.add(ARouterUtils.NEWS_MAIN_FRAGMENT);
        mFragments.add(ARouterUtils.AREA_MAIN_FRAGMENT);
        mFragments.add(ARouterUtils.SERVICE_MAIN_FRAGMENT);
        mFragments.add(ARouterUtils.USER_MAIN_FRAGMENT);
    }


    public class UIChangeObservable {
        public SingleLiveEvent<MenuItem> pNavigationPosition = new SingleLiveEvent<>();
    }


    public BindingCommand<MenuItem> onNavigationChangeCommand = new BindingCommand<MenuItem>(new BindingConsumer<MenuItem>() {
        @Override
        public void call(MenuItem item) {
            uc.pNavigationPosition.setValue(item);
        }
    });


    /**
     * 根据位置得到对应的 Fragment
     *
     * @param position
     * @return
     */
    public String getFragment(int position) {
        if (mFragments != null && mFragments.size() > 0) {
            String fragment = mFragments.get(position);
            return fragment;
        }
        return null;
    }



    /**
     * 返回处发的 fragment
     *
     * @param fragmentId
     * @return
     */
    public Fragment createNewInstance(String fragmentId) {
        if (fragmentId.equals(ARouterUtils.NEWS_MAIN_FRAGMENT)) {
            return (Fragment) ARouter.getInstance().build(ARouterUtils.NEWS_MAIN_FRAGMENT).navigation();
        } else if (fragmentId.equals(ARouterUtils.AREA_MAIN_FRAGMENT)) {
            return (Fragment) ARouter.getInstance().build(ARouterUtils.AREA_MAIN_FRAGMENT).navigation();
        } else if (fragmentId.equals(ARouterUtils.SERVICE_MAIN_FRAGMENT)) {
            return (Fragment) ARouter.getInstance().build(ARouterUtils.SERVICE_MAIN_FRAGMENT).navigation();
        } else if (fragmentId.equals(ARouterUtils.USER_MAIN_FRAGMENT)) {
            return (Fragment) ARouter.getInstance().build(ARouterUtils.USER_MAIN_FRAGMENT).navigation();
        } else {
            return new Fragment();
        }
    }

    /**
     * BottomNavigationView显示角标
     *
     * @param viewIndex  tab索引
     * @param showNumber 显示的数字，小于等于0是将不显示
     */
    public void showBadgeView(Context context,BottomNavigationView navigationView, int viewIndex, int showNumber) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView)navigationView.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);
            // 从子tab中获得其中显示图片的ImageView
            View icon = view.findViewById(com.google.android.material.R.id.icon);
            // 获得图标的宽度
            int iconWidth = icon.getWidth();
            // 获得tab的宽度/2
            int tabWidth = view.getWidth() / 2;
            // 计算badge要距离右边的距离
            int spaceWidth = tabWidth - iconWidth;

            // 显示badegeview
            new QBadgeView(context).bindTarget(view)
                    .setGravityOffset(spaceWidth + 50, 13, false)
                    .setBadgeNumber(showNumber);
        }

    }

    @SuppressLint("RestrictedApi")
    public void disableShiftMode(BottomNavigationView bottomView) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




















