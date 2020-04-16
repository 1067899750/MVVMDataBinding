package com.example.z_lib_common.bankres;

/**
 *
 * Description
 * Author puyantao
 * Email 1067899750@qq.com
 * Date 2019/8/16 10:56
 */
public class BankResFactory {

    private static IRes iBankRes;

    public static IRes getInstance() {
        if (iBankRes != null) {
            return iBankRes;
        }
        switch (ConfigPermission.APPLICATION_PACKAGE) {
            case "com.data.product":
                iBankRes = new ResProduct();
                break;
            case "com.data.check":
                iBankRes = new ResCheck();
                break;
            case "com.data.develop":
                iBankRes = new ResDevelop();
                break;
            default:
                iBankRes = new ResDevelop();
        }
        return iBankRes;
    }
}
