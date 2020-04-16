package com.example.z_lib_net.base;


import com.example.z_lib_net.environment.IEnvironment;
import com.example.z_lib_net.error.HttpErrorHandler;
import com.example.z_lib_net.intercepter.CommonRequestInterceptor;
import com.example.z_lib_net.intercepter.CommonResponseInterceptor;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/16 9:21
 */
public abstract class NetworkApi implements IEnvironment {
    private static INetworkRequiredInfo iNetworkRequiredInfo;
    private static HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;
    private static boolean mIsFormal = true;

    public NetworkApi() {
        if (!mIsFormal) {
            mBaseUrl = getTest();
        }
        mBaseUrl = getFormal();
    }

    public static void init(INetworkRequiredInfo networkRequiredInfo) {
        iNetworkRequiredInfo = networkRequiredInfo;
    }


    protected Retrofit  getRetrofit(Class service){
        if (retrofitHashMap.get(mBaseUrl + service.getName()) != null){
            return retrofitHashMap.get(mBaseUrl + service.getName());
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitHashMap.put(mBaseUrl + service.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        // 10MB
        int cacheSize = 100 * 1024 * 1024;
        if (mOkHttpClient == null){
            OkHttpClient.Builder okHttpClineBuilder = new OkHttpClient.Builder()
                    .cache(new Cache(iNetworkRequiredInfo.getApplicationContext().getCacheDir(), cacheSize))
                    .addInterceptor(new CommonRequestInterceptor(iNetworkRequiredInfo))
                    .addInterceptor(new CommonResponseInterceptor());
            if (getInterceptor() != null) {
                okHttpClineBuilder.addInterceptor(getInterceptor());
            }

            if (iNetworkRequiredInfo != null &&(iNetworkRequiredInfo.isDebug())) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClineBuilder.addInterceptor(httpLoggingInterceptor);
            }
            mOkHttpClient = okHttpClineBuilder.build();
        }
        return mOkHttpClient;
    }



    public  <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = (Observable<T>)upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(getAppErrorHandler())
                        .onErrorResumeNext(new HttpErrorHandler<T>());
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    protected abstract Interceptor getInterceptor();

    protected abstract <T> Function<T, T> getAppErrorHandler();

}





















