package com.example.databinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.z_lib_base.base.BaseApplication;
import com.example.z_lib_base.untils.CommonUtils;

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


    }
}









