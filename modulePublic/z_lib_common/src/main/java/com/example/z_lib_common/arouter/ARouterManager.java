package com.example.z_lib_common.arouter;


import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.HashMap;

/**
 * @author puyantao
 * @description ARouter管理器
 * @date 2019/10/24 10:47
 */
public class ARouterManager {

    /**
     * @param path 路径
     * @return
     */
    public static Object navigation(String path) {
        Postcard postcard = ARouter.getInstance().build(path);
        return postcard.navigation();
    }


    /**
     * @param path 路径
     * @param map  参数
     * @return
     */
    public static Object navigation(String path, HashMap<String, String> map) {
        Postcard postcard = ARouter.getInstance().build(path);
        for (String key : map.keySet()) {
            postcard.withString(key, map.get(key));
        }
        return postcard.navigation();
    }

}











