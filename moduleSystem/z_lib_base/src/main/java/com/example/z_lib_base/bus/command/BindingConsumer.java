package com.example.z_lib_base.bus.command;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/15 13:45
 */
public interface BindingConsumer<T> {
    /**
     *  回调
     * @param t
     */
    void call(T t);
}
