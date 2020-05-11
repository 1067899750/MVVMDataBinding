package com.example.z_module_service.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.z_module_service.R;
import com.example.z_module_service.bean.MallCustomBusinessAddressBean;


public class MallMapDialogAdapter extends BaseQuickAdapter<MallCustomBusinessAddressBean, BaseViewHolder> {

    private Context mContext;
    private TextView addressTips;
    private LinearLayout bottomLine;


    public MallMapDialogAdapter(Context context) {
        super(R.layout.service_address_dialog_viewholder, null);
        this.mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, MallCustomBusinessAddressBean item) {
        helper.setText(R.id.address_name_tv, item.getNameDetail());
        helper.setText(R.id.address_loc_tv, item.getAddressLoc());
        if (helper.getLayoutPosition() == getItemCount() - 1) {
            helper.getView(R.id.bottom_line).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.bottom_line).setVisibility(View.VISIBLE);
        }


    }
}
