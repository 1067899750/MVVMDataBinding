package com.example.z_lib_base.base;

import com.example.z_lib_base.intercepter.IModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/15 11:01
 */
public class BaseModel implements IModel {
    /**
     * 管理RxJava，主要针对RxJava异步操作造成的内存泄漏
     */
    private CompositeDisposable mCompositeDisposable;

    public BaseModel() {
    }

    @Override
    public void onCleared() {
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    public void addDisposable(Disposable d) {
        if (d == null) {
            return;
        }
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
    }
}
