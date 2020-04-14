package com.example.z_module_user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.z_lib_common.arouter.ARouterUtils;


/**
 *
 * @description 用户
 * @author puyantao
 * @date 2020/4/14 14:08
 */
@Route(path = ARouterUtils.USER_MAIN_FRAGMENT)
public class UserMainFragment extends Fragment {

    public UserMainFragment() {
    }

    public static UserMainFragment newInstance(String param1, String param2) {
        UserMainFragment fragment = new UserMainFragment();
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
        return inflater.inflate(R.layout.user_fragment_main, container, false);
    }
}
