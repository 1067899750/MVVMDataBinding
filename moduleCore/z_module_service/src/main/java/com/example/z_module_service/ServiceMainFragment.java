package com.example.z_module_service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.z_lib_base.base.BaseMVVMFragment;
import com.example.z_lib_common.arouter.ARouterUtils;
import com.example.z_lib_common.utils.ValueUtil;
import com.example.z_module_service.activity.BookAssetsDetailMapActivity;
import com.example.z_module_service.databinding.ServiceMainFragmentBinding;
import com.example.z_lib_base.utils.LocationService;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;


/**
 * @author puyantao
 * @description 服务
 * @date 2020/4/14 14:07
 */
@Route(path = ARouterUtils.SERVICE_MAIN_FRAGMENT)
public class ServiceMainFragment extends BaseMVVMFragment<ServiceMainFragmentBinding, ServiceFragmentViewModel> {
    private Context mContext;
    /**
     * 默认数据
     */
    private String defaultLon;
    private String defaultLat;
    private String defaultLoc;
    /**
     * 传入后台经纬度
     */
    private String lonDetail;
    private String latDetail;


    private BDAbstractLocationListener locationLis = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            doSetLocation(bdLocation);
        }
    };


    public ServiceMainFragment() {
    }

    public static ServiceMainFragment newInstance() {
        ServiceMainFragment fragment = new ServiceMainFragment();
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
        return R.layout.service_fragment_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        if (AndPermission.hasPermissions(getActivity(), Permission.ACCESS_FINE_LOCATION)) {
            if (LocationService.getInstance(mContext).getBdLocation() == null) {
                LocationService.getInstance(mContext).callback(locationLis);
//                    LocationService.getInstance(this).start();
            } else {
                doSetLocation(LocationService.getInstance(mContext).getBdLocation());
            }
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        mBinding.locIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookAssetsDetailMapActivity.startBookAssetsDetailMapActivity(getActivity(),
                        mViewModel.locStr.get(),
                        ValueUtil.isStrNotEmpty(lonDetail) ? lonDetail : defaultLon,
                        ValueUtil.isStrNotEmpty(latDetail) ? latDetail : defaultLat,
                        defaultLoc,
                        false,
                        ValueUtil.isStrEmpty(lonDetail) && ValueUtil.isStrEmpty(latDetail));

            }
        });
    }


    private void doSetLocation(BDLocation bdLocation) {
        if (bdLocation == null) {
        } else {
            defaultLat = String.valueOf(bdLocation.getLatitude());
            defaultLon = String.valueOf(bdLocation.getLongitude());
            defaultLoc = bdLocation.getStreet() + bdLocation.getStreetNumber();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (BookAssetsDetailMapActivity.GET_MAP == requestCode && resultCode == BookAssetsDetailMapActivity.RESULT_MAP_OK) {
            if (data.hasExtra("lon") && data.hasExtra("lat")) {
                lonDetail = data.getStringExtra("lon");
                latDetail = data.getStringExtra("lat");
            }

            if (data.hasExtra("address")) {
                mViewModel.locStr.set(data.getStringExtra("address"));
            }

        }
    }


}




































