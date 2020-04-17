package com.example.z_lib_net;

import com.example.z_lib_net.base.NetworkApi;
import com.example.z_lib_net.base.BaseResponse;
import com.example.z_lib_net.error.ExceptionHandle;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/16 9:22
 */
public class NewNetworkApi extends NetworkApi {
    private static volatile NewNetworkApi mInstance;
    public static NewNetworkApi getInstance(){
        if (mInstance == null){
            synchronized (NewNetworkApi.class){
                if (mInstance == null){
                    mInstance = new NewNetworkApi();
                }
            }
        }
        return mInstance;
    }

    public static <T> T getService(Class<T> service){
        return getInstance().getRetrofit(service).create(service);
    }


    @Override
    protected Interceptor getInterceptor() {
        return null;
    }

    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                //response中code码不会0 出现错误
                if (response instanceof BaseResponse && ((BaseResponse) response).code != 0) {
                    ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                    exception.code = ((BaseResponse) response).code;
                    exception.message = ((BaseResponse) response).message != null ? ((BaseResponse) response).message : "";
                    throw exception;
                }
                return response;
            }
        };
    }

}











