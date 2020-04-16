package com.example.z_lib_net.intercepter;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.z_lib_net.untils.NetUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 *
 * @description 读取 ssionId 和 token
 * @author puyantao
 * @date 2019/10/24 15:43
 */
public class ReadCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            //只用ssionId,不用token
            for (String header : originalResponse.headers("Set-Cookie")) {
                //拦截ssionId
                if (header.contains("JSESSIONID")){
                    cookies.add(header);
                }
                //拦截token
//                if (header.contains("token")) {
//                    cookies.add(header);
//                }
            }
            SharedPreferences.Editor config = NetUtils.getContext().getSharedPreferences("config", Context.MODE_PRIVATE).edit();
            if (!cookies.isEmpty()) {
                config.putStringSet("cookie", cookies);
            }

            //拦截token
            List<String> yyttoken = originalResponse.headers("yyttoken");
            if (yyttoken != null && yyttoken.size() > 0) {
                if (!yyttoken.get(0).isEmpty()) {
                    config.putString("yyttoken", yyttoken.get(0));
                }
            }
            config.commit();
        }

        return originalResponse;
    }
}

















