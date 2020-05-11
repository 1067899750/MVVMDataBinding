package com.example.z_module_news.headlinenews;

import com.example.z_lib_base.base.BaseModel;

import com.example.z_lib_common.model.NetTagUtil;
import com.example.z_lib_net.NewNetworkApi;
import com.example.z_lib_net.base.BaseObserver;
import com.example.z_lib_net.base.BaseResponse;
import com.example.z_module_news.app.NewsApiInterface;


/**
 * @author puyantao
 * @describe
 * @create 2020/4/16 10:37
 */
public class NewFragmentModel extends BaseModel<BaseResponse> {
    public NewFragmentModel() {
        super(BaseResponse.class, "page");
    }

    @Override
    public void refresh() {

    }


    @Override
    protected void load() {
        NewNetworkApi.getService(NewsApiInterface.class)
                .getBillHomePage()
                .compose(NewNetworkApi.
                        getInstance().
                        applySchedulers(new BaseObserver<BaseResponse>(this, this, NetTagUtil.PAGE_TAG)));
    }


    @Override
    public void onSuccess(BaseResponse data, boolean isFromCache, String tag) {
        loadSuccess(data, isFromCache, tag);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
    }


}









