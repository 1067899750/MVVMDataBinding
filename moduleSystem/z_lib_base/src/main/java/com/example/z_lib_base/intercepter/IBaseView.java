package com.example.z_lib_base.intercepter;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/14 16:58
 */
public interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    void initParam();
    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}















