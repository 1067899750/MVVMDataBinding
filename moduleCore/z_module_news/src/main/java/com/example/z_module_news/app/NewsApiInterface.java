package com.example.z_module_news.app;

import com.example.z_module_news.headlinenews.NewsChannelsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 
 * @description
 * @author puyantao
 * @date 2020/4/16 10:03
 */
public interface NewsApiInterface {
    @GET("release/channel")
    Observable<NewsChannelsBean> getNewsChannels();
}
