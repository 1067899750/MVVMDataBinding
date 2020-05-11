package com.example.z_module_area;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.z_lib_base.base.BaseMVVMFragment;
import com.example.z_lib_common.arouter.ARouterUtils;
import com.example.z_module_area.databinding.AreaMainFragmentBinding;
import com.example.z_module_area.fragment.AreaFiveFragment;
import com.example.z_module_area.fragment.AreaFourFragment;
import com.example.z_module_area.fragment.AreaOneFragment;
import com.example.z_module_area.fragment.AreaThreeFragment;
import com.example.z_module_area.fragment.AreaTwoFragment;

import java.util.ArrayList;

/**
 * @author puyantao
 * @description 地区
 * @date 2020/4/14 14:05
 */
@Route(path = ARouterUtils.AREA_MAIN_FRAGMENT)
public class AreaMainFragment extends BaseMVVMFragment<AreaMainFragmentBinding, AreaFragmentViewModel> {
    public ArrayList<String> mFragments;
    public Fragment oldFragment;
    public Fragment newFragment;

    public AreaMainFragment() {
    }

    public static AreaMainFragment newInstance() {
        AreaMainFragment fragment = new AreaMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.area_fragment_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        mFragments = new ArrayList<>();
        mFragments.add(AreaFragmentViewModel.AREA_ONE);
        mFragments.add(AreaFragmentViewModel.AREA_TWO);
        mFragments.add(AreaFragmentViewModel.AREA_THREE);
        mFragments.add(AreaFragmentViewModel.AREA_FOUR);
        mFragments.add(AreaFragmentViewModel.AREA_FIVE);
        updateFragment(0);
    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        mViewModel.uc.onChangeFragment.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                updateFragment(integer);
            }
        });
    }


    public void updateFragment(int position) {
        String baseFragment = getFragment(position);
        switchFragment(baseFragment);
    }


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
     * 切换Fragment
     *
     * @param fragmentId 上一个fragment
     */
    private void switchFragment(String fragmentId) {
        FragmentManager mFragmentManager = getChildFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        newFragment = createNewInstance(fragmentId);
        if (oldFragment != null && oldFragment.isAdded()){
            if (fragmentId.equals(oldFragment.getTag())){
                return;
            }
            mFragmentTransaction.remove(oldFragment);
        }
        mFragmentTransaction.add(R.id.fl_content, newFragment, fragmentId);
        oldFragment = newFragment;
        mFragmentTransaction.commitAllowingStateLoss();
    }


    /**
     * 返回处发的 fragment
     *
     * @param fragmentId
     * @return
     */
    public Fragment createNewInstance(String fragmentId) {
        if (fragmentId.equals(AreaFragmentViewModel.AREA_ONE)) {
            return AreaOneFragment.newInstance();

        } else if (fragmentId.equals(AreaFragmentViewModel.AREA_TWO)) {
            return AreaTwoFragment.newInstance();

        } else if (fragmentId.equals(AreaFragmentViewModel.AREA_THREE)) {
            return AreaThreeFragment.newInstance();

        } else if (fragmentId.equals(AreaFragmentViewModel.AREA_FOUR)) {
            return AreaFourFragment.newInstance();

        } else if (fragmentId.equals(AreaFragmentViewModel.AREA_FIVE)) {
            return AreaFiveFragment.newInstance();

        } else {
            return new Fragment();
        }
    }


}












