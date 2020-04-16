package com.example.z_module_news.headlinenews;

import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_base.model.MvvmNetworkObserver;

import com.example.z_lib_net.NewNetworkApi;
import com.example.z_lib_net.base.BaseObserver;
import com.example.z_module_news.app.NewsApiInterface;
import com.orhanobut.logger.Logger;


/**
 * @author puyantao
 * @describe
 * @create 2020/4/16 10:37
 */
public class NewFragmentModel extends BaseModel implements MvvmNetworkObserver<NewsChannelsBean>{


    public void initNet() {
        NewNetworkApi.getService(NewsApiInterface.class)
                .getNewsChannels()
                .compose(NewNetworkApi.
                        getInstance().
                        applySchedulers(new BaseObserver<NewsChannelsBean>(this, this)));
    }


    @Override
    public void onSuccess(NewsChannelsBean data, boolean isFromCache) {
        Logger.d(data.toString());
    }

    @Override
    public void onFailure(Throwable e) {
        Logger.d(e.toString());
    }


}









