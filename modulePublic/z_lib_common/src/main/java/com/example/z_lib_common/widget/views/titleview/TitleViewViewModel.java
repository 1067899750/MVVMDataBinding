package com.example.z_lib_common.widget.views.titleview;

import android.app.Application;

import com.example.z_lib_base.base.view.BaseCustomViewModel;

import androidx.annotation.NonNull;

/**
 * @author puyantao
 * @description
 * @date 2020/4/15 17:08
 */
public class TitleViewViewModel extends BaseCustomViewModel {
    public String title;

    public TitleViewViewModel(@NonNull Application application) {
        super(application);
    }
}