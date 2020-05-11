package com.example.z_module_service;

import android.app.Application;

import com.example.z_lib_base.base.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/17 17:26
 */
public class ServiceFragmentViewModel extends BaseViewModel {
    /**
     * 位置
     */
    public ObservableField<String> locStr = new ObservableField<>();
    public ServiceFragmentViewModel(@NonNull Application application) {
        super(application);
    }








}















