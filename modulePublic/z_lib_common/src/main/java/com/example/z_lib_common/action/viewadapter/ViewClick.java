package com.example.z_lib_common.action.viewadapter;

import android.view.View;

import com.example.z_lib_base.bus.command.BindingCommand;
import com.jakewharton.rxbinding2.view.RxView;

import androidx.databinding.BindingAdapter;
import io.reactivex.functions.Consumer;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/16 14:31
 */
public class ViewClick {

    @BindingAdapter(value = {"onClickCommand"}, requireAll = false)
    public static void onViewClickCommand(View view, BindingCommand command){
        RxView.clicks(view)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (command != null){
                            command.execute();
                        }
                    }
                });
    }

}
