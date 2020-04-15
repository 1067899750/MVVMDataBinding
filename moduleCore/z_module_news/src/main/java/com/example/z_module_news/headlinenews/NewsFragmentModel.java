package com.example.z_module_news.headlinenews;

import android.app.Application;

import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_base.base.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/15 15:43
 */
public class NewsFragmentModel extends BaseViewModel {
    public ObservableField<String> name = new ObservableField<>();

    public NewsFragmentModel(@NonNull Application application) {
        super(application);
    }

    public NewsFragmentModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


}












