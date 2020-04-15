package com.example.z_lib_common.widget.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.databinding.BindingAdapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class CommonBindingAdapters {

    @BindingAdapter(value = {"imageUrl", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView view, String url, int placeholderRes) {
        if(!TextUtils.isEmpty(url)) {
            Glide.with(view.getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes))
                    .transition(withCrossFade())
                    .into(view);
        }
    }
    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}














