package com.example.z_lib_common.action.viewadapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.databinding.BindingAdapter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 *
 * @description 设置Glide加载ImageView的图片
 * @author puyantao
 * @date 2020/4/15 17:36
 */
public class ImageViewAdapters {

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
    @BindingAdapter(value = {"isVisible"}, requireAll = false)
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}














