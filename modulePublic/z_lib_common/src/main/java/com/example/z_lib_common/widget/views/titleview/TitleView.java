package com.example.z_lib_common.widget.views.titleview;

import android.content.Context;
import android.view.View;

import com.example.z_lib_base.base.view.BaseCustomView;
import com.example.z_lib_common.R;
import com.example.z_lib_common.databinding.TitleViewBinding;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/4/16 8:33
 */
public class TitleView extends BaseCustomView<TitleViewBinding, TitleViewViewModel> {
    public TitleView(Context context) {
        super(context);
    }

    @Override
    public int setViewLayoutId() {
        return R.layout.title_view;
    }

    @Override
    public void setDataToView(TitleViewViewModel data) {
        getDataBinding().setViewModel(data);
    }

    @Override
    public void onRootClick(View view) {

    }
}
