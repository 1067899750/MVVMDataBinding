package com.example.z_lib_base.model;


import com.example.z_lib_base.base.BaseModel;

/**
 * @author puyantao
 * @description viewModel 和 model 沟通的接口
 * @date 2020/4/17 14:07
 */
public interface IBaseModelListener<T> {
    /**
     * 成功调用
     *
     * @param model 用来判断哪个model传来的数据
     * @param tag   区分调用接口标识
     * @param data  数据
     */
    void onLoadFinish(BaseModel model, T data, String tag);

    /**
     * 调用失败
     *
     * @param model   用来判断哪个model传来的数据
     * @param message 错误信息
     */
    void onLoadFail(BaseModel model, String message);
}



















