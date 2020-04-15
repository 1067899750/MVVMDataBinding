package com.example.z_lib_common.action.data;


import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_common.intercepter.LocalDataSource;

import androidx.annotation.NonNull;

/**
 * @author puyantao
 * @description MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据（一个应用可以有多个Repositor）
 * @date 2020/4/15 15:12
 */
public class DemoRepository extends BaseModel implements LocalDataSource {
    private volatile static DemoRepository INSTANCE = null;
    private final LocalDataSource mLocalDataSource;

    private DemoRepository(@NonNull LocalDataSourceImpl localDataSource) {
        this.mLocalDataSource = localDataSource;
    }

    public static DemoRepository getInstance(LocalDataSourceImpl localDataSource) {
        if (INSTANCE == null) {
            synchronized (DemoRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DemoRepository(localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }
}











