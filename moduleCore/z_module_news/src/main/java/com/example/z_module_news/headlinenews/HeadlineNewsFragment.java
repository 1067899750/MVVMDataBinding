package com.example.z_module_news.headlinenews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.z_lib_common.arouter.ARouterUtils;
import com.example.z_module_news.R;


/**
 * @author puyantao
 * @description 新闻界面
 * @date 2020/4/14 10:30
 */
@Route(path = ARouterUtils.NEWS_MAIN_FRAGMENT)
public class HeadlineNewsFragment extends Fragment {


    public HeadlineNewsFragment() {
        // Required empty public constructor
    }

    public static HeadlineNewsFragment newInstance() {
        HeadlineNewsFragment fragment = new HeadlineNewsFragment();
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
        return inflater.inflate(R.layout.news_fragment_headline, container, false);
    }
}
