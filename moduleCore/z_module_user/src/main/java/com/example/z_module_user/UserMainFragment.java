package com.example.z_module_user;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.z_lib_base.base.BaseMVVMFragment;
import com.example.z_lib_common.arouter.ARouterUtils;
import com.example.z_lib_common.utils.ValueUtil;
import com.example.z_module_user.databinding.UserMainFragmentBinding;

import java.util.Calendar;
import java.util.Locale;


/**
 *
 * @description 用户
 * @author puyantao
 * @date 2020/4/14 14:08
 */
@Route(path = ARouterUtils.USER_MAIN_FRAGMENT)
public class UserMainFragment extends BaseMVVMFragment<UserMainFragmentBinding, UserFragmentViewModel> {
    public Context mContext;

    public UserMainFragment() {
    }

    public static UserMainFragment newInstance() {
        UserMainFragment fragment = new UserMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void initParam() {
        super.initParam();
    }


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.user_fragment_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    public void initData() {
        super.initData();
    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        mViewModel.uc.dateChangeTool.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                if (ValueUtil.isStrEmpty(mViewModel.dateStr.get())){
                    emptyDataChoose();
                } else {
                    hasDataChoose();
                }
            }
        });
    }


    private void emptyDataChoose() {
        Calendar myCalendar = Calendar.getInstance(Locale.CHINA);
        //获取Calendar对象中的年
        int year = myCalendar.get(Calendar.YEAR);
        //获取Calendar对象中的月
        int month = myCalendar.get(Calendar.MONTH);
        //获取这个月的第几天
        int day = myCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(mContext, AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
            /**params：view：该事件关联的组件
             * params：myyear：当前选择的年
             * params：monthOfYear：当前选择的月
             * params：dayOfMonth：当前选择的日
             */
            @Override
            public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {
                mViewModel.dateStr.set(myyear + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, year, month, day);
        //显示DatePickerDialog组件
        dpd.show();
    }

    private void hasDataChoose() {
        try {
            String[] dates =  mViewModel.dateStr.get().split("[-]");
            //获取Calendar对象中的年
            int year = Integer.valueOf(dates[0]);
            //获取Calendar对象中的月
            int month = Integer.valueOf(dates[1]) - 1;
            //获取这个月的第几天
            int day = Integer.valueOf(dates[2]);
            DatePickerDialog dpd = new DatePickerDialog(mContext, AlertDialog.THEME_HOLO_LIGHT,
                    new DatePickerDialog.OnDateSetListener() {
                /**params：view：该事件关联的组件
                 * params：myyear：当前选择的年
                 * params：monthOfYear：当前选择的月
                 * params：dayOfMonth：当前选择的日
                 */
                @Override
                public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {
                    mViewModel.dateStr.set(myyear + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                }
            }, year, month, day);
            //显示DatePickerDialog组件
            dpd.show();
        } catch (Exception e) {
            emptyDataChoose();
        }
    }
}










