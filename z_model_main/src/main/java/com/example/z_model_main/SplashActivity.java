package com.example.z_model_main;

import android.content.Intent;
import android.os.Bundle;


import com.example.z_lib_base.model.MVVMNetworkObserver;
import com.example.z_lib_common.action.data.LocalDataSourceImpl;
import com.example.z_lib_net.NewNetworkApi;
import com.example.z_lib_net.base.BaseObserver;
import com.example.z_model_main.bean.LoginModel;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author puyantao
 * @describe 利用给SplashActivity设置带有背景的SplashTheme来避免启动白屏的问题
 * @create 2020/4/17 8:35
 */
public class SplashActivity extends AppCompatActivity implements MVVMNetworkObserver<LoginModel> {
    private String password = "e10adc3949ba59abbe56e057f20f883e";
    private String mobile = "18503970627";
    private String cid = "09164b5a280eda07839aabbd9e3c5961";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNet();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


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
                        applySchedulers(new BaseObserver<LoginModel>(this, "login")));
    }


    @Override
    public void onSuccess(LoginModel data, String tag, boolean isFromCache) {
        if (tag.equals("login")) {
            LocalDataSourceImpl.getInstance().saveUserName(data.realName);
            LocalDataSourceImpl.getInstance().saveMobile(data.mobile);
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onFailure(Throwable e) {

    }
}














