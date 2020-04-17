package com.example.z_module_news.headlinenews;

import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_base.model.MVVMNetworkObserver;

import com.example.z_lib_base.untils.log.XLog;
import com.example.z_lib_net.NewNetworkApi;
import com.example.z_lib_net.base.BaseObserver;
import com.example.z_lib_net.base.BaseResponse;
import com.example.z_module_news.app.NewsApiInterface;
import com.example.z_module_news.bean.NewsChannelsBean;
import com.orhanobut.logger.Logger;



/**
 * @author puyantao
 * @describe
 * @create 2020/4/16 10:37
 */
public class NewFragmentModel extends BaseModel implements MVVMNetworkObserver<BaseResponse> {
    public void initNet() {
        NewNetworkApi.getService(NewsApiInterface.class)
                .getBillHomePage()
                .compose(NewNetworkApi.
                        getInstance().
                        applySchedulers(new BaseObserver<BaseResponse>(this, this, "page")));
    }


    @Override
    public void onSuccess(BaseResponse data, String tag, boolean isFromCache) {
        if (tag.equals("page")){
            if (data instanceof NewsChannelsBean){
                NewsChannelsBean.NewsDataBean dataBean = ((NewsChannelsBean) data).mNewsDataBean;
                XLog.d("--->", data.toString());
            }
        }
    }

    @Override
    public void onFailure(Throwable e) {
        Logger.d(e.toString());
    }


}









