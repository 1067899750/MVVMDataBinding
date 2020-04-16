package com.example.z_module_news.headlinenews;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.z_lib_base.base.BaseMVVMFragment;
import com.example.z_lib_common.arouter.ARouterUtils;
import com.example.z_module_news.BR;
import com.example.z_module_news.R;
import com.example.z_module_news.databinding.HeadlineNewsFragmentDataBinding;


/**
 * @author puyantao
 * @description 新闻界面
 * @date 2020/4/14 10:30
 */
@Route(path = ARouterUtils.NEWS_MAIN_FRAGMENT)
public class HeadlineNewsFragment extends BaseMVVMFragment<HeadlineNewsFragmentDataBinding, NewsFragmentViewModel> {


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
    public void initParam() {
        super.initParam();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.news_fragment_headline;
    }


    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.name.set("新闻界面");
    }


    @Override
    public void initViewObservable() {

    }
}















