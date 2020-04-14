package com.example.z_model_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import q.rorbin.badgeview.QBadgeView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.z_lib_common.arouter.ARouterManager;
import com.example.z_lib_common.arouter.ARouterUtils;
import com.example.z_model_main.databinding.MainActivityBinding;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author puyantao
 * @description
 * @date 2020/4/14 9:13
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mFragments;
    private int mPosition = 0;
    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        mFragments = new ArrayList<>();
        mFragments.add(ARouterUtils.NEWS_MAIN_FRAGMENT);
        mFragments.add(ARouterUtils.AREA_MAIN_FRAGMENT);
        mFragments.add(ARouterUtils.SERVICE_MAIN_FRAGMENT);
        mFragments.add(ARouterUtils.USER_MAIN_FRAGMENT);
        updateFragment();
        //init toolBar
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.main_menu_home));

        /**
         * Disable shift method require for to prevent shifting icon.
         * When you select any icon then remain all icon shift
         * @param view
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            disableShiftMode(binding.bottomView);
        }

        binding.bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_home) {
                    mPosition = 0;
                } else if (itemId == R.id.menu_area) {
                    mPosition = 1;
                } else if (itemId == R.id.menu_services) {
                    mPosition = 2;
                } else if (itemId == R.id.menu_user) {
                    mPosition = 3;
                }
                updateFragment();
                //actionBar
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(item.getTitle());
                }
                return true;
            }
        });
        binding.bottomView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        showBadgeView(3, 5);

    }

    /**
     * BottomNavigationView显示角标
     *
     * @param viewIndex  tab索引
     * @param showNumber 显示的数字，小于等于0是将不显示
     */
    private void showBadgeView(int viewIndex, int showNumber) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) binding.bottomView.getChildAt(0);
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
            new QBadgeView(this).bindTarget(view)
                    .setGravityOffset(spaceWidth + 50, 13, false)
                    .setBadgeNumber(showNumber);
        }

    }

    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView bottomView) {
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

    public void updateFragment() {
        String baseFragment = getFragment(mPosition);
        switchFragment(baseFragment);
    }

    /**
     * 根据位置得到对应的 Fragment
     *
     * @param position
     * @return
     */
    private String getFragment(int position) {
        if (mFragments != null && mFragments.size() > 0) {
            String fragment = mFragments.get(position);
            return fragment;
        }
        return null;
    }


    /**
     * 切换Fragment
     *
     * @param fragmentId 上一个fragment
     */
    private void switchFragment(String fragmentId) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < mFragments.size(); i++) {
            String tag = String.valueOf(mFragments.get(i));
            Fragment fragment = mFragmentManager.findFragmentByTag(tag);
            if (mFragments.get(i) == fragmentId) {
                if (fragment == null || !fragment.isAdded()) {
                    fragment = createNewInstance(fragmentId);
                    mFragmentTransaction.add(R.id.fl_content, fragment, tag);
                } else {
                    mFragmentTransaction.show(fragment);
                }
            } else if (fragment != null) {
                mFragmentTransaction.hide(fragment);
            }
        }
        mFragmentTransaction.commitAllowingStateLoss();
    }


    /**
     * 返回处发的 fragment
     *
     * @param fragmentId
     * @return
     */
    private Fragment createNewInstance(String fragmentId) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return true;
    }
}





















