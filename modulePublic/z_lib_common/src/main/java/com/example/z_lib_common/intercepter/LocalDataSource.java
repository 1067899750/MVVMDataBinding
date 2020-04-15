package com.example.z_lib_common.intercepter;

/**
 *
 * @description 本地登录数据
 * @author puyantao
 * @date 2020/4/15 15:29
 */
public interface LocalDataSource {
    /**
     * 保存用户名
     */
    void saveUserName(String userName);

    /**
     * 保存用户密码
     */

    void savePassword(String password);

    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 获取用户密码
     */
    String getPassword();
}
