package com.example.z_lib_base.bus.command;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/15 13:46
 */
public interface BindingFunction<T> {
    /**
     *  回调
     * @return
     */
    T call();
}
