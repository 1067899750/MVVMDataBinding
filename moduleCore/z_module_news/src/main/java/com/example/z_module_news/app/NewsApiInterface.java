package com.example.z_module_news.app;

import com.example.z_lib_net.base.BaseResponse;
import com.example.z_module_news.headlinenews.NewsChannelsBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 
 * @description
 * @author puyantao
 * @date 2020/4/16 10:03
 */
public interface NewsApiInterface {
    /**
     *
     * @return
     */
    @POST("mobile/billRecord/billHomePage.htm")
    Observable<NewsChannelsBean> getBillHomePage();

}
