package com.example.z_module_service.viewmodel;

import android.app.Application;

import com.example.z_lib_base.base.BaseViewModel;
import com.example.z_lib_base.bus.event.SingleLiveEvent;


import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;

/**
 * @author puyantao
 * @describe
 * @create 2020/5/7 8:53
 */
public class BookAssetsDetailMapViewModel extends BaseViewModel {
    /**
     * 是否显示保存按钮， true显示， false 显示取消按钮和列表
     */
    public ObservableBoolean isShowDetailList = new ObservableBoolean();


    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        public SingleLiveEvent<Boolean> borrowSwitchEvent = new SingleLiveEvent<>();
    }

    public BookAssetsDetailMapViewModel(@NonNull Application application) {
        super(application);
        isShowDetailList.set(false);
    }



}









