package com.example.z_module_news.newlist;

import android.view.ViewGroup;


import com.example.z_lib_base.base.reclclerview.BaseViewHolder;
import com.example.z_lib_base.base.view.BaseCustomViewModel;
import com.example.z_lib_common.widget.views.picturetitleview.PictureTitleView;
import com.example.z_lib_common.widget.views.picturetitleview.PictureTitleViewViewModel;
import com.example.z_lib_common.widget.views.titleview.TitleView;
import com.example.z_lib_common.widget.views.titleview.TitleViewViewModel;

import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;


/**
 *
 * @description RecyclerView 通用适配器
 * @author puyantao
 * @date 2020/4/15 17:15
 */
public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ObservableList<BaseCustomViewModel> mItems;
    private final int VIEW_TYPE_PICTURE_TITLE = 1;
    private final int VIEW_TYPE_TITLE = 2;

    NewsListRecyclerViewAdapter() {
    }

    void setData(ObservableList<BaseCustomViewModel> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mItems != null && mItems.size() > 0) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(mItems.get(position) instanceof PictureTitleViewViewModel){
            return VIEW_TYPE_PICTURE_TITLE;
        } else if(mItems.get(position) instanceof TitleViewViewModel){
            return VIEW_TYPE_TITLE;
        }
        return -1;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_PICTURE_TITLE) {
            PictureTitleView pictureTitleView = new PictureTitleView(parent.getContext());
            return new BaseViewHolder(pictureTitleView);
        } else if(viewType == VIEW_TYPE_TITLE) {
            TitleView titleView = new TitleView(parent.getContext());
            return new BaseViewHolder(titleView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }
}
