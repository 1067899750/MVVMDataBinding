package com.example.z_module_user;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.z_lib_base.base.BaseMVVMFragment;
import com.example.z_lib_base.base.BaseViewModel;
import com.example.z_lib_common.arouter.ARouterUtils;
import com.example.z_module_user.databinding.UserMainFragmentBinding;


/**
 *
 * @description 用户
 * @author puyantao
 * @date 2020/4/14 14:08
 */
@Route(path = ARouterUtils.USER_MAIN_FRAGMENT)
public class UserMainFragment extends BaseMVVMFragment<UserMainFragmentBinding, BaseViewModel> {

    public UserMainFragment() {
    }

    public static UserMainFragment newInstance() {
        UserMainFragment fragment = new UserMainFragment();
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
        return R.layout.user_fragment_main;
    }

    @Override
    public int initVariableId() {
        return 0;
    }
}
