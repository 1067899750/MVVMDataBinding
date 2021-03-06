package com.example.z_lib_common.action.data;


import com.example.z_lib_common.intercepter.LocalDataSource;
import com.example.z_lib_base.utils.SPUtils;

/**
 *
 * @description 本地数据源，可配合Room框架使用
 * @author puyantao
 * @date 2020/4/15 15:30
 */
public class LocalDataSourceImpl implements LocalDataSource {
    private volatile static LocalDataSourceImpl INSTANCE = null;

    public static LocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private LocalDataSourceImpl() {
        //数据库Helper构建
    }

    @Override
    public void saveUserName(String userName) {
        SPUtils.getInstance().put("UserName", userName);
    }

    @Override
    public void savePassword(String password) {
        SPUtils.getInstance().put("password", password);
    }

    @Override
    public String getUserName() {
        return SPUtils.getInstance().getString("UserName");
    }

    @Override
    public String getPassword() {
        return SPUtils.getInstance().getString("password");
    }

    @Override
    public void saveMobile(String mobile) {
        SPUtils.getInstance().put("mobile", mobile);
    }

    @Override
    public String getMobile() {
        return SPUtils.getInstance().getString("mobile");
    }
}
