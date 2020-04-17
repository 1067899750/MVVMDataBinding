package com.example.z_module_news.bean;

import com.example.z_lib_net.base.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * @author puyantao
 * @description
 * @date 2020/4/16 9:55
 */
public class NewsChannelsBean extends BaseResponse {
    @SerializedName("data")
    @Expose
    public NewsDataBean mNewsDataBean;

    public class NewsDataBean {
        @SerializedName("payTotal")
        @Expose
        public Integer payTotal;
        @SerializedName("incomeTotal")
        @Expose
        public Integer incomeTotal;
        @SerializedName("yearBorrowTotal")
        @Expose
        public String yearBorrowTotal;
        @SerializedName("bidFixedTotal")
        @Expose
        public String bidFixedTotal;
        @SerializedName("bidFinanaceTotal")
        @Expose
        public String bidFinanaceTotal;
    }


}
