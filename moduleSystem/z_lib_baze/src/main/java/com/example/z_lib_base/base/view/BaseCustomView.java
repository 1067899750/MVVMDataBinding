package com.example.z_lib_base.base.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author puyantao
 * @describe 自定义 View 基类
 * @create 2020/4/15 16:38
 */
public abstract class BaseCustomView<V extends ViewDataBinding, VM extends BaseCustomViewModel> extends LinearLayout
        implements ICustomView<VM>, View.OnClickListener {
    protected V dataBinding;
    protected VM viewModel;
    private ICustomViewActionListener mListener;

    public BaseCustomView(Context context) {
        super(context);
        init();

    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public View getRootView() {
        return dataBinding.getRoot();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (setViewLayoutId() != 0){
            dataBinding = DataBindingUtil.inflate(inflater, setViewLayoutId(), this, false);
            dataBinding.getRoot().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onAction(ICustomViewActionListener.ACTION_ROOT_VIEW_CLICKED, v, viewModel);
                    }
                    onRootClick(v);
                }
            });
            this.addView(dataBinding.getRoot());
        }

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void setData(VM data) {
        viewModel = data;
        setDataToView(viewModel);
        if (dataBinding != null){
            dataBinding.executePendingBindings();
        }
        onDataUpdated();
    }

    protected void onDataUpdated() {
    }

    @Override
    public void setStyle(int resId) {

    }

    @Override
    public void setActionListener(ICustomViewActionListener listener) {
        this.mListener = listener;
    }

    protected V getDataBinding(){
        return dataBinding;
    }

    protected VM getViewModel(){
        return viewModel;
    }

    protected abstract int setViewLayoutId();

    protected abstract void setDataToView(VM data);

    protected abstract void onRootClick(View view);

}




















