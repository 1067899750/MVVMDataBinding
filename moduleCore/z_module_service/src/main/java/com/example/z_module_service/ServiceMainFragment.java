package com.example.z_module_service;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.z_lib_common.arouter.ARouterUtils;


/**
 *
 * @description 服务
 * @author puyantao
 * @date 2020/4/14 14:07
 */
@Route(path = ARouterUtils.SERVICE_MAIN_FRAGMENT)
public class ServiceMainFragment extends Fragment {

    public ServiceMainFragment() {
    }

    public static ServiceMainFragment newInstance() {
        ServiceMainFragment fragment = new ServiceMainFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.service_fragment_main, container, false);
    }
}
