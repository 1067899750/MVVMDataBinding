package com.example.z_lib_net;

import com.example.z_lib_net.base.NetworkApi;
import com.example.z_lib_net.base.NewBaseResponse;
import com.example.z_lib_net.error.ExceptionHandle;
import com.example.z_lib_net.untils.TecentUtil;

import java.io.IOException;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

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
    public String getFormal() {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/";
    }

    @Override
    public String getTest() {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/";
    }

    @Override
    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String timeStr = TecentUtil.getTimeStr();
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Source", "source");
                builder.addHeader("Authorization", TecentUtil.getAuthorization(timeStr));
                builder.addHeader("Date", timeStr);
                return chain.proceed(builder.build());
            }
        };
    }

    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                //response中code码不会0 出现错误
                if (response instanceof NewBaseResponse && ((NewBaseResponse) response).showapiResCode != 0) {
                    ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                    exception.code = ((NewBaseResponse) response).showapiResCode;
                    exception.message = ((NewBaseResponse) response).showapiResError != null ? ((NewBaseResponse) response).showapiResError : "";
                    throw exception;
                }
                return response;
            }
        };
    }
}











