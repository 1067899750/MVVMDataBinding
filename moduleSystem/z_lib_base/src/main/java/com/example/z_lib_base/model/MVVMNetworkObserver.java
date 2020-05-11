package com.example.z_lib_base.model;

/**
 * @author puyantao
 * @description
 * @date 2020/4/17 9:52
 */
public interface MVVMNetworkObserver<F> {
    /**
     * 成功
     *
     * @param data        返回的数据
     * @param isFromCache 是否缓存
     * @param tag         接口类型
     */
    void onSuccess(F data, boolean isFromCache, String tag);

    /**
     * 失败
     *
     * @param e
     */
    void onFailure(Throwable e);
}
