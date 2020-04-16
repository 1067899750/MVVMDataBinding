package com.example.z_lib_base.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.z_lib_base.R;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

/**
 *
 * @description 加载框
 * @author puyantao
 * @date 2020/4/15 9:33
 */
public class LoadingDialog {

    private LoadingDialog() {
        throw new IllegalArgumentException("unable to new object!");
    }

    private static Dialog createDialog(Builder builder) {
        View contentView = LayoutInflater.from(builder.mContext).inflate(R.layout.base_dialog_loading, null);
        ProgressBar pbLoading = contentView.findViewById(R.id.pb_loading);
        pbLoading.setVisibility(builder.isShowBar ? View.VISIBLE : View.GONE);
        TextView tvMessage = contentView.findViewById(R.id.tv_message);
        tvMessage.setText(builder.mMessage);

        Dialog dialog = new AlertDialog.Builder(builder.mContext, R.style.MyDialogStyle)
                .setView(contentView)
                .setOnCancelListener(builder.mOnCancelListener)
                .create();
        dialog.setCanceledOnTouchOutside(builder.mCancelableOnTouchOutside);
        dialog.setCancelable(builder.mCancelable);
        if (builder.mContext instanceof Activity) {
            dialog.setOwnerActivity((Activity) builder.mContext);
        }
        return dialog;
    }

    public static class Builder {
        private Context mContext;
        private boolean mCancelable;
        private boolean mCancelableOnTouchOutside;
        private DialogInterface.OnCancelListener mOnCancelListener;
        private boolean isShowBar;
        private String mMessage;

        public Builder(Context context) {
            this.mContext = context;
            this.mCancelable = true;
            this.mCancelableOnTouchOutside = false;
            this.isShowBar = true;
            this.mMessage = "加载中...";
        }

        public Builder setContext(Context context) {
            mContext = context;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setCancelableOnTouchOutside(boolean cancelableOnTouchOutside) {
            mCancelableOnTouchOutside = cancelableOnTouchOutside;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setShowBar(boolean showBar) {
            isShowBar = showBar;
            return this;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Builder setMessage(@StringRes int resId) {
            setMessage(mContext.getString(resId));
            return this;
        }

        public Dialog create() {
            return createDialog(this);
        }

        public Dialog show() {
            Dialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
