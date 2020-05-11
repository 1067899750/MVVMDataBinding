package com.example.z_lib_base.base.view;

import android.app.Application;

import com.example.z_lib_base.base.BaseModel;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author puyantao
 * @description 自定义通用View基类model
 * @date 2020/4/15 16:39
 */
public class BaseCustomViewModel<M extends BaseModel> extends AndroidViewModel implements Serializable {
    protected M model;
    public String jumpUri;

    public BaseCustomViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.onCleared();
        }
    }


}














