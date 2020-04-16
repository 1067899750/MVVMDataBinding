package com.example.z_lib_net.environment;
/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/16 9:29
 */
public interface IEnvironment {
    /**
     *  正式环境
     * @return
     */
    String getFormal();

    /**
     * 测试环境
     * @return
     */
    String getTest();
}
