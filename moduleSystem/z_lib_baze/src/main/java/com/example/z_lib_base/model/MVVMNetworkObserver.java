package com.example.z_lib_base.model;

public interface MVVMNetworkObserver<F> {
    /**
     * 成功
     *
     * @param data        返回的数据
     * @param tag         接口类型
     * @param isFromCache 是否缓存
     */
    void onSuccess(F data, String tag, boolean isFromCache);

    /**
     * 失败
     *
     * @param e
     */
    void onFailure(Throwable e);
}
