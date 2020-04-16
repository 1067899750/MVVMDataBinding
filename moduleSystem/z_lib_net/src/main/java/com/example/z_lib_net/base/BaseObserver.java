package com.example.z_lib_net.base;



import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_base.model.MvvmNetworkObserver;
import com.example.z_lib_net.error.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class BaseObserver<T> implements Observer<T> {
    BaseModel baseModel;
    MvvmNetworkObserver<T> mvvmNetworkObserver;
    public BaseObserver(BaseModel baseModel, MvvmNetworkObserver<T> mvvmNetworkObserver) {
        this.baseModel = baseModel;
        this.mvvmNetworkObserver = mvvmNetworkObserver;
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof ExceptionHandle.ResponeThrowable){
            mvvmNetworkObserver.onFailure(e);
        } else {
            mvvmNetworkObserver.onFailure(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onNext(T data) {
        mvvmNetworkObserver.onSuccess(data, false);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if(baseModel != null){
            baseModel.addDisposable(d);
        }
    }

    @Override
    public void onComplete() {
    }
}
