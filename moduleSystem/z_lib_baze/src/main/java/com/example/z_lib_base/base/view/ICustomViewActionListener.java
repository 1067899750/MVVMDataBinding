package com.example.z_lib_base.base.view;

import android.view.View;
/**
 *
 * @description 点击事件回调
 * @author puyantao
 * @date 2020/4/15 16:57
 */
public interface ICustomViewActionListener {
    String ACTION_ROOT_VIEW_CLICKED = "action_root_view_clicked";

    /**
     *
     * @param action
     * @param view
     * @param viewModel
     */
    void onAction(String action, View view, BaseCustomViewModel viewModel);
}
