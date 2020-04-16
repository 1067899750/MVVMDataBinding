package com.example.z_module_news.headlinenews;

import com.example.z_lib_net.base.NewBaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/16 9:55
 */
public class NewsChannelsBean extends NewBaseResponse {
    @SerializedName("showapi_res_body")
    @Expose
    public ShowapiResBody showapiResBody;

    public class ChannelList {
        @SerializedName("channelId")
        @Expose
        public String channelId;
        @SerializedName("name")
        @Expose
        public String name;
    }

    public class ShowapiResBody {
        @SerializedName("totalNum")
        @Expose
        public Integer totalNum;
        @SerializedName("ret_code")
        @Expose
        public Integer retCode;
        @SerializedName("channelList")
        @Expose
        public List<ChannelList> channelList = null;
    }
}
