package com.example.z_lib_base.base.reclclerview;

import android.view.View;


import com.example.z_lib_base.base.view.BaseCustomViewModel;
import com.example.z_lib_base.base.view.ICustomView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 *
 * @description RecyclerView.ViewHolder 基类
 * @author puyantao
 * @date 2020/4/15 17:15
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    ICustomView view;

    public BaseViewHolder(ICustomView view) {
        super((View) view);
        this.view = view;
    }

    public void bind(@NonNull BaseCustomViewModel item) {
        view.setData(item);
    }
}