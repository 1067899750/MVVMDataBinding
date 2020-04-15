package com.example.z_lib_common.widget.views.picturetitleview;

import android.app.Application;

import com.example.z_lib_base.base.view.BaseCustomViewModel;

import androidx.annotation.NonNull;

/**
 * @author puyantao
 * @description
 * @date 2020/4/15 17:07
 */
public class PictureTitleViewViewModel extends BaseCustomViewModel {
    public String avatarUrl;
    public String title;

    public PictureTitleViewViewModel(@NonNull Application application) {
        super(application);
    }
}