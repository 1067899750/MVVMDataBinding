package com.example.z_model_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.z_lib_base.base.BaseMVVMActivity;
import com.example.z_model_main.databinding.MainActivityBinding;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

/**
 * @author puyantao
 * @description
 * @date 2020/4/14 9:13
 */
public class MainActivity extends BaseMVVMActivity<MainActivityBinding, MainViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.main_activity;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    public void initData() {
        updateFragment(0);
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
            viewModel.disableShiftMode(binding.bottomView);
        }

        binding.bottomView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        viewModel.showBadgeView(this, binding.bottomView, 3, 5);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.pNavigationPosition.observe(this, new Observer<MenuItem>() {
            @Override
            public void onChanged(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_home) {
                    updateFragment(0);
                } else if (itemId == R.id.menu_area) {
                    updateFragment(1);
                } else if (itemId == R.id.menu_services) {
                    updateFragment(2);
                } else if (itemId == R.id.menu_user) {
                    updateFragment(3);
                }
                //actionBar
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(item.getTitle());
                }

            }
        });
    }

    public void updateFragment(int position) {
        String baseFragment = viewModel.getFragment(position);
        switchFragment(baseFragment);
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
        for (int i = 0; i < viewModel.mFragments.size(); i++) {
            String tag = String.valueOf(viewModel.mFragments.get(i));
            Fragment fragment = mFragmentManager.findFragmentByTag(tag);
            if (viewModel.mFragments.get(i) == fragmentId) {
                if (fragment == null || !fragment.isAdded()) {
                    fragment = viewModel.createNewInstance(fragmentId);
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





















