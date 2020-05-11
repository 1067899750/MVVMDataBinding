package com.example.z_lib_base.base;


import android.text.TextUtils;

import com.example.z_lib_base.base.intercepter.IModel;
import com.example.z_lib_base.model.BaseCachedData;
import com.example.z_lib_base.model.IBaseModelListener;
import com.example.z_lib_base.model.MVVMNetworkObserver;
import com.example.z_lib_base.utils.GsonUtils;
import com.example.z_lib_base.utils.SPUtils;
import com.example.z_lib_base.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author puyantao
 * @description
 * @date 2020/4/15 11:01
 */
public abstract class BaseModel<F> implements IModel, MVVMNetworkObserver<F> {
    /**
     * 管理RxJava，主要针对RxJava异步操作造成的内存泄漏
     */
    private CompositeDisposable mCompositeDisposable;

    protected ReferenceQueue<IBaseModelListener> mReferenceQueue;
    protected ConcurrentLinkedQueue<WeakReference<IBaseModelListener>> mWeakListenerArrayList;

    /**
     * 缓存信息
     */
    private BaseCachedData<F> mData;
    /**
     * 缓存标识
     */
    private String mCachedPreferenceKey;
    private Class<F> clazz;

    /**
     * 不带缓存初始还
     */
    public BaseModel() {
        mReferenceQueue = new ReferenceQueue<>();
        mWeakListenerArrayList = new ConcurrentLinkedQueue<>();
    }

    /**
     * 带缓存初始还
     *
     * @param clazz              json转化的class
     * @param cachePreferenceKey 缓存的key
     */
    public BaseModel(Class<F> clazz, String cachePreferenceKey) {
        mReferenceQueue = new ReferenceQueue<>();
        mWeakListenerArrayList = new ConcurrentLinkedQueue<>();
        this.clazz = clazz;
        this.mCachedPreferenceKey = cachePreferenceKey;
        if (mCachedPreferenceKey != null) {
            mData = new BaseCachedData<F>();
        }
    }

    /**
     * 注册监听
     *
     * @param listener
     */
    public void register(IBaseModelListener listener) {
        if (listener == null) {
            return;
        }
        synchronized (this) {
            // 每次注册的时候清理已经被系统回收的对象
            Reference<? extends IBaseModelListener> releaseListener = null;
            while ((releaseListener = mReferenceQueue.poll()) != null) {
                mWeakListenerArrayList.remove(releaseListener);
            }
            for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                IBaseModelListener listenerItem = weakListener.get();
                if (listenerItem == listener) {
                    return;
                }
            }
            WeakReference<IBaseModelListener> weakListener = new WeakReference<IBaseModelListener>(listener, mReferenceQueue);
            mWeakListenerArrayList.add(weakListener);
        }
    }


    /**
     * 取消监听
     *
     * @param listener
     */
    public void unRegister(IBaseModelListener listener) {
        if (listener == null) {
            return;
        }
        synchronized (this) {
            for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                IBaseModelListener listenerItem = weakListener.get();
                if (listener == listenerItem) {
                    mWeakListenerArrayList.remove(weakListener);
                    break;
                }
            }
        }
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

    /**
     * 刷新数据
     */
    public abstract void refresh();

    /**
     * 加载数据
     */
    protected abstract void load();

    /**
     * 是否更新数据，可以在这里设计策略，可以是一天一次，一月一次等等，
     * 默认是每次请求都更新
     */
    protected boolean isNeedToUpdate() {
        return true;
    }


    /**
     * 由于渠道处在App的首页，为了保证app打开的时候由于网络慢或者异常的情况下tablayout不为空，
     * 所以app对渠道数据进行了预制；
     * 加载完成以后会立即进行网络请求，同时缓存在本地，今后app打开都会从preference读取，而不在读取
     * apk预制数据，由于渠道数据变化没那么快，在app第二次打开的时候会生效，并且是一天请求一次。
     */
    protected void saveDataToPreference(F data) {
        if (data != null) {
            mData.data = data;
            mData.updateTimeInMills = System.currentTimeMillis();
            SPUtils.getInstance().put(mCachedPreferenceKey, GsonUtils.toJson(mData));
        }
    }

    public void getCachedDataAndLoad() {
        if (mCachedPreferenceKey != null) {
            String saveDataString = SPUtils.getInstance().getString(mCachedPreferenceKey);
            if (!TextUtils.isEmpty(saveDataString)) {
                try {
                    F savedData = GsonUtils.fromLocalJson(new JSONObject(saveDataString).getString("data"), clazz);
                    if (savedData != null && savedData != null) {
                        onSuccess(savedData, true, " ");
                        if (isNeedToUpdate()) {
                            load();
                        }
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        load();
    }


    /**
     * 发消息给UI线程
     *
     * @param data        数据
     * @param isFromCache 是否缓存
     */
    protected void loadSuccess(F data, boolean isFromCache, String... tag) {
        synchronized (this) {
            for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                if (weakListener.get() instanceof IBaseModelListener) {
                    IBaseModelListener listenerItem = weakListener.get();
                    if (listenerItem != null) {
                        listenerItem.onLoadFinish(this, data, tag.length > 0 ? tag[0] : " ");
                        //不是从缓存获取的数据要重新保存
                        if (mCachedPreferenceKey != null && !isFromCache) {
                            saveDataToPreference(data);
                        }
                    }
                }
            }
        }
    }


    protected void loadFail(final String errorMessage) {
        synchronized (this) {
            for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                if (weakListener.get() instanceof IBaseModelListener) {
                    IBaseModelListener listenerItem = weakListener.get();
                    if (listenerItem != null) {
                        ToastUtils.showLongToast(errorMessage);
                        listenerItem.onLoadFail(this, errorMessage);
                    }
                }
            }
        }
    }

}















