package com.example.z_module_news.headlinenews;

import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_base.model.MVVMNetworkObserver;

import com.example.z_lib_net.NewNetworkApi;
import com.example.z_lib_net.base.BaseObserver;
import com.example.z_module_news.app.NewsApiInterface;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;


/**
 * @author puyantao
 * @describe
 * @create 2020/4/16 10:37
 */
public class NewFragmentModel extends BaseModel implements MVVMNetworkObserver<Object> {
    private String password = "e10adc3949ba59abbe56e057f20f883e";
    private String mobile = "18503970627";
    private String cid = "09164b5a280eda07839aabbd9e3c5961";

    public void initNet() {
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("password", password);
        loginMap.put("mobile", mobile);
        loginMap.put("cid", cid);
        loginMap.put("loginType", "1");
        NewNetworkApi.getService(NewsApiInterface.class)
                .getLoginInfo(loginMap)
                .compose(NewNetworkApi.
                        getInstance().
                        applySchedulers(new BaseObserver<Object>(this, this, "login")));
    }


    @Override
    public void onSuccess(Object data, String tag, boolean isFromCache) {
        if (tag.equals("login")){
            NewNetworkApi.getService(NewsApiInterface.class)
                    .getBillHomePage()
                    .compose(NewNetworkApi.
                            getInstance().
                            applySchedulers(new BaseObserver<Object>(this, this, "page")));
        } else if (tag.equals("page")){
            Logger.d(data.toString());
        }

    }

    @Override
    public void onFailure(Throwable e) {
        Logger.d(e.toString());
    }


}









