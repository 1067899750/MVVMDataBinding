package com.example.z_module_news.headlinenews;

import android.app.Application;

import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_base.bus.command.BindingAction;
import com.example.z_lib_base.bus.command.BindingCommand;
import com.example.z_lib_base.utils.ToastUtils;
import com.example.z_lib_base.utils.log.XLog;
import com.example.z_lib_common.model.NetTagUtil;
import com.example.z_lib_common.model.NetWorkViewModel;
import com.example.z_lib_net.base.BaseResponse;
import com.example.z_module_news.bean.NewsChannelsBean;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/15 15:43
 */
public class NewsFragmentViewModel extends NetWorkViewModel<NewFragmentModel, BaseResponse> {
    public ObservableField<String> name = new ObservableField<>();

    public NewsFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public NewFragmentModel initModel() {
        return new NewFragmentModel();
    }


    public BindingCommand requestNet = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            model.getCachedDataAndLoad();
        }
    });

    @Override
    public void onLoadFinish(BaseModel model, BaseResponse data, String tag) {
        super.onLoadFinish(model, data, tag);
        if (tag.equals(NetTagUtil.PAGE_TAG)) {
            if (data instanceof NewsChannelsBean) {
                NewsChannelsBean.NewsDataBean dataBean = ((NewsChannelsBean) data).mNewsDataBean;
                XLog.d("--->", data.toString());
            }
        }
    }

    @Override
    protected void finishLoadData() {
        ToastUtils.showLongToast("finish load data");
    }


}












