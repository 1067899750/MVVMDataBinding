package com.example.z_lib_net.untils;

import android.content.Context;
import com.example.z_lib_net.base.NetworkApi;

/**
 * @author puyantao
 * @describe
 * @create 2019/10/24 15:30
 */
public class NetUtils {
    private static Context context;

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context, String url) {
        NetUtils.context = context;
        NetworkApi.init(url);
    }


    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("you should init first");
    }




    /**
     * 全局获取String的方法
     *
     * @param id 资源Id
     * @return String
     */
    public static String getString(int id) {
        return context.getResources().getString(id);
    }



}












