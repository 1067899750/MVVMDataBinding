package com.example.z_model_main.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/17 9:54
 */
public class LoginModel {
    @SerializedName(value = "success")
    @Expose
    public String success;
    /**
     *  电话
     */
    @SerializedName(value = "mobile")
    @Expose
    public String mobile;

    @SerializedName(value = "realName")
    @Expose
    public String realName;

    @SerializedName(value = "userName")
    @Expose
    public String userName;

}














