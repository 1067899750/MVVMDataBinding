package com.example.z_model_main;

import com.example.z_model_main.bean.LoginModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 
 * @description
 * @author puyantao
 * @date 2020/4/16 10:03
 */
public interface NewsApiInterface {
    /**
     * 登录
     * @param map
     * @return
     */
    @GET("mobile/login.htm")
    Observable<LoginModel> getLoginInfo(@QueryMap Map<String, String> map);


}
