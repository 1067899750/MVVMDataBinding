package com.example.z_lib_net.dns;

/**
 *
 * @description IP基类
 * @author puyantao
 * @date 2019/10/14 10:22
 */
public class HttpDns {
    private static HttpDns mInstance = null;
    /**
     *  URL
     */
    public static String baseUrl;

    public static HttpDns getInstance() {
        if (mInstance == null) {
            synchronized (HttpDns.class) {
                if (mInstance == null) {
                    mInstance = new HttpDns();
                }
            }
        }
        return mInstance;
    }

    public void init(String url) {
        baseUrl = url;
    }


    public String getIp() {
        return baseUrl;
    }
}













