package com.example.z_module_area;

import android.app.Application;
import android.view.MenuItem;
import android.view.View;

import com.example.z_lib_base.base.BaseViewModel;
import com.example.z_lib_base.bus.event.SingleLiveEvent;
import com.example.z_lib_common.arouter.ARouterUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;

import java.util.ArrayList;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/17 17:26
 */
public class AreaFragmentViewModel extends BaseViewModel {
    public static String AREA_ONE = "AreaOneFragment";
    public static String AREA_TWO = "AreaTwoFragment";
    public static String AREA_THREE = "AreaThreeFragment";
    public static String AREA_FOUR = "AreaFourFragment";
    public static String AREA_FIVE = "AreaFiveFragment";

    public ObservableInt fragmentTag = new ObservableInt();

    public AreaFragmentViewModel(@NonNull Application application) {
        super(application);
        fragmentTag.set(0);
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Integer> onChangeFragment = new SingleLiveEvent<>();
    }


    /**
     * 切换 fragment
     *
     * @param view
     * @param tag
     */
    public void onChangeClick(View view, int tag) {
        fragmentTag.set(tag);
        uc.onChangeFragment.setValue(tag);
    }


}





