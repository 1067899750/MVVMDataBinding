package com.example.z_module_area.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.z_lib_base.base.BaseMVVMFragment;
import com.example.z_module_area.BR;
import com.example.z_module_area.R;
import com.example.z_module_area.databinding.AreaOneFragmentBinding;
import com.example.z_module_area.viewmodel.AreaOneViewModel;

/**
  *@description
  *@author puyantao
  *@date 2020/4/25 8:53
  */
public class AreaOneFragment extends BaseMVVMFragment<AreaOneFragmentBinding, AreaOneViewModel> {

    public AreaOneFragment() {
    }

    public static AreaOneFragment newInstance() {
        AreaOneFragment fragment = new AreaOneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.area_ragment_one;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    public void initData() {
        super.initData();
    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
    }

}











