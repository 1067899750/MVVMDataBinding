package com.example.z_model_main.viewmodel;

import android.app.Application;

import com.example.z_lib_base.base.BaseViewModel;
import com.example.z_lib_common.action.data.DemoRepository;
import com.example.z_lib_common.action.data.Injection;

import androidx.annotation.NonNull;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/17 9:02
 */
public class SplashViewModel extends BaseViewModel<DemoRepository> {
    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public DemoRepository initModel() {
        return Injection.provideDemoRepository();
    }


}











