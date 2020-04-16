package com.example.databinding;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.z_lib_base.base.BaseApplication;
import com.example.z_lib_base.untils.CommonUtils;
import com.example.z_lib_net.base.NetworkApi;

import androidx.multidex.MultiDex;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/14 8:45
 */
public class DataApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (CommonUtils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        NetworkApi.init(new NetworkRequestInfo(this));

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }

}









