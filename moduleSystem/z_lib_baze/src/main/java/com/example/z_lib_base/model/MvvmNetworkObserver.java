package com.example.z_lib_base.model;

public interface MvvmNetworkObserver<F> {
    /**
     *  成功
     * @param data 返回的数据
     * @param isFromCache
     */
    void onSuccess(F data, boolean isFromCache);

    /**
     *  失败
     * @param e
     */
    void onFailure(Throwable e);
}
