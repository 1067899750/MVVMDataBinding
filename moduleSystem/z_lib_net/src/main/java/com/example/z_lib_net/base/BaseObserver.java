package com.example.z_lib_net.base;


import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_base.model.MVVMNetworkObserver;
import com.example.z_lib_net.error.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author puyantao
 * @description 重写观察着Observer类
 * @date 2020/4/16 17:11
 */
public class BaseObserver<T> implements Observer<T> {
    private BaseModel baseModel;
    private MVVMNetworkObserver<T> mNetworkObserver;
    private String mTag;

    public BaseObserver(BaseModel baseModel, MVVMNetworkObserver<T> observer, String tag) {
        this.baseModel = baseModel;
        this.mNetworkObserver = observer;
        this.mTag = tag;
    }

    public BaseObserver(MVVMNetworkObserver<T> observer, String tag) {
        this.mNetworkObserver = observer;
        this.mTag = tag;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            mNetworkObserver.onFailure(e);
        } else {
            mNetworkObserver.onFailure(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onNext(T data) {
        mNetworkObserver.onSuccess(data, mTag, false);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (baseModel != null) {
            baseModel.addDisposable(d);
        }
    }

    @Override
    public void onComplete() {
    }
}
