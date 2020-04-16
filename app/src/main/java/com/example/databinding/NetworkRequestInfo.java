package com.example.databinding;


import android.app.Application;

import com.example.z_lib_net.base.INetworkRequiredInfo;


/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/16 13:58
 */
public class NetworkRequestInfo implements INetworkRequiredInfo {
    private Application mApplication;
    public NetworkRequestInfo(Application application){
        this.mApplication = application;
    }

    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public Application getApplicationContext() {
        return mApplication;
    }

}
