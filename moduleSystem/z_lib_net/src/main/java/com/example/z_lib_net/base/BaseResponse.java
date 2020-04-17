package com.example.z_lib_net.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/16 9:47
 */
public class BaseResponse {
    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("errorCode")
    @Expose
    public Integer errorCode;

    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;


    @SerializedName("message")
    @Expose
    public String message;


}















