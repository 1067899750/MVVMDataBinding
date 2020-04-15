package com.example.z_lib_base.intercepter;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/15 11:01
 */
public interface IModel {
    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    void onCleared();
}
