package com.example.z_lib_common.action.data;


/**
 *  注入全局的数据仓库，可以考虑使用Dagger2。（根据项目实际情况搭建，千万不要为了架构而架构）
 */
public class Injection {
    public static DemoRepository provideDemoRepository() {
        LocalDataSourceImpl localDataSource = LocalDataSourceImpl.getInstance();
        //分支数据仓库
        return DemoRepository.getInstance(localDataSource);
    }
}
