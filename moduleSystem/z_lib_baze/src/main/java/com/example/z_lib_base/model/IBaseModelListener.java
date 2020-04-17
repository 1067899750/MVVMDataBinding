package com.example.z_lib_base.model;


/**
 * @author puyantao
 * @description viewModel 和 model 沟通的接口
 * @date 2020/4/17 14:07
 */
public interface IBaseModelListener<T> {
    /**
     * 成功调用
     *
     * @param tag
     * @param data
     */
    void onLoadFinish(T data, String tag);

    /**
     * 调用失败
     *
     * @param tag
     * @param message
     */
    void onLoadFail(String message);
}



















