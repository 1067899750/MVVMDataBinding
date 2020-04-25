package com.example.z_module_user;

import android.app.Application;
import android.view.MenuItem;
import android.view.View;

import com.example.z_lib_base.base.BaseViewModel;
import com.example.z_lib_base.bus.event.SingleLiveEvent;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import java.util.Date;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/17 17:26
 */
public class UserFragmentViewModel extends BaseViewModel {
    public ObservableField<String> dateStr = new ObservableField<>();

    public UserFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        public SingleLiveEvent<Object> dateChangeTool = new SingleLiveEvent<>();
    }


    /**
     *  时间选择器
     * @param view
     */
    public void onDateClick(View view){
        uc.dateChangeTool.setValue(" ");
    }


}










