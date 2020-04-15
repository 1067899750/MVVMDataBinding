package com.example.z_lib_common.widget.views.picturetitleview;

import android.content.Context;
import android.view.View;

import com.example.z_lib_base.base.view.BaseCustomView;
import com.example.z_lib_common.R;
import com.example.z_lib_common.databinding.PictureTitleViewBinding;


/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class PictureTitleView extends BaseCustomView<PictureTitleViewBinding, PictureTitleViewViewModel> {
    public PictureTitleView(Context context) {
        super(context);
    }

    @Override
    public int setViewLayoutId() {
        return R.layout.picture_title_view;
    }

    @Override
    public void setDataToView(PictureTitleViewViewModel data) {
        getDataBinding().setViewModel(data);
    }

    @Override
    public void onRootClick(View view) {
    }

}
