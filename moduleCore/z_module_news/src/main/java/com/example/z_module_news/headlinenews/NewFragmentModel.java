package com.example.z_module_news.headlinenews;

import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_base.model.IBaseModelListener;
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
public class NewFragmentModel extends BaseModel<BaseResponse> {
    public NewFragmentModel() {
        super();
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
                        applySchedulers(new BaseObserver<BaseResponse>(this, this, "page")));
    }

    @Override
    public void onSuccess(BaseResponse data, String tag, boolean isFromCache) {
        loadSuccess(data, tag);
    }

    @Override
    public void onFailure(Throwable e) {
        loadFail(e.getMessage());
    }


}









