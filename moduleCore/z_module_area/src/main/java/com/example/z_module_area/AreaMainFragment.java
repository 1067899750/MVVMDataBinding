package com.example.z_module_area;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.z_lib_common.arouter.ARouterUtils;


/**
 *
 * @description 地区
 * @author puyantao
 * @date 2020/4/14 14:05
 */
@Route(path = ARouterUtils.AREA_MAIN_FRAGMENT)
public class AreaMainFragment extends Fragment {

    public AreaMainFragment() {
    }

    public static AreaMainFragment newInstance() {
        AreaMainFragment fragment = new AreaMainFragment();
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
        return inflater.inflate(R.layout.area_fragment_main, container, false);
    }
}
