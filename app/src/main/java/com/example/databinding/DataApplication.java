package com.example.databinding;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.z_lib_base.base.BaseApplication;
import com.example.z_lib_base.utils.CommonUtils;
import com.example.z_lib_common.bankres.ConfigPermission;
import com.example.z_lib_common.multipackage.EnvType;
import com.example.z_lib_base.utils.log.XLog;
import com.example.z_lib_net.untils.NetUtils;

import androidx.multidex.MultiDex;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/14 8:45
 */
public class DataApplication extends BaseApplication {
    /**
     * 多环境打包标志  1：开发环境 2：测试环境3：生产环境
     */
    public static int envType = BuildConfig.ENV_TYPE;

    @Override
    public void onCreate() {
        super.onCreate();
        if (CommonUtils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        NetUtils.init(this, getResources().getString(R.string.base_url));
        //出书画Log
        XLog.init(BuildConfig.LOG_DEBUG, "--logApp-->");



        //初始化多环境打包
        initMultiPackage();
        getOtherMessage();
        parseManifests();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }

    private void getOtherMessage(){
        String str = getPackageName();
        if (str.contains(".debug")){
            str = str.substring(0, str.length() - 6);
        } else if (str.contains(".debugUat")){
            str = str.substring(0, str.length() - 9);
        }
        ConfigPermission.APPLICATION_PACKAGE = str;
        // resValue 资源文件调用

        XLog.d("---> base_url ",getResources().getString(R.string.base_url));
        XLog.d("---> mall_base_url ",getResources().getString(R.string.mall_base_url));
        Log.d("---> app_client ",getResources().getString(R.string.app_client));

        // buildConfigField 配置文件调用
        XLog.d("---> QQ_APPID ",BuildConfig.QQ_APPID);
        XLog.d("---> LOG_DEBUG ",BuildConfig.LOG_DEBUG + "");
        XLog.d("---> APPLICATION_ID ",BuildConfig.APPLICATION_ID);


    }


    private void parseManifests() {
        String packageName = getApplicationContext().getPackageName();
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                String appid = appInfo.metaData.getString("PUSH_APPID");
                String appsecret = appInfo.metaData.getString("PUSH_APPSECRET");
                String appkey = appInfo.metaData.getString("PUSH_APPKEY");

                XLog.d("---> PUSH_APPID ",appid + "");
                XLog.d("---> PUSH_APPSECRET ",appsecret + "");
                XLog.d("---> PUSH_APPKEY ",appkey + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化多环境打包
     */
    private void initMultiPackage() {
        switch (envType) {
            case EnvType.DEVELOP:
                //开发环境（
                MY_STR = "开发环境";
                break;
            case EnvType.CHECK:
                //测试环境
                MY_STR = "测试环境";
                break;
            case EnvType.PRODUCT:
                //生产环境
                MY_STR = "生产环境";
                break;
        }
    }


}









