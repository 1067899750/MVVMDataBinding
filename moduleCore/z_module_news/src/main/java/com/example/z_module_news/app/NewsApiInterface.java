package com.example.z_module_news.app;

import com.example.z_module_news.bean.NewsChannelsBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

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
