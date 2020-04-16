package com.example.z_lib_net.base;

import android.app.Application;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/16 9:40
 */
public interface INetworkRequiredInfo {
    String getAppVersionName();
    String getAppVersionCode();
    boolean isDebug();
    Application getApplicationContext();
}
