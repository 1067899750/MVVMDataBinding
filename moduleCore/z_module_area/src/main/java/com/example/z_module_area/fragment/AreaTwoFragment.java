package com.example.z_module_area.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.example.z_lib_base.base.BaseMVVMFragment;
import com.example.z_module_area.BR;
import com.example.z_module_area.R;
import com.example.z_module_area.databinding.AreaTwoFragmentBinding;
import com.example.z_module_area.viewmodel.AreaTwoViewModel;

/**
 * @author puyantao
 * @description
 * @date 2020/4/25 9:03
 */
public class AreaTwoFragment extends BaseMVVMFragment<AreaTwoFragmentBinding, AreaTwoViewModel>
        implements BaiduMap.OnMapClickListener {
    private Context mContext;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private NotifyLister mNotifyLister;
    private Marker mMarker;
    //纬度信息
    private double mlatitude = 0.0d;
    //经度信息
    private double mlongitude = 0.0d;
    // 是否首次定位
    boolean isFirstLoc = true;
    private NotiftLocationListener listener;
    private BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.area_icon_openmap_mark);

    public AreaTwoFragment() {
        // Required empty public constructor
    }


    public static AreaTwoFragment newInstance() {
        AreaTwoFragment fragment = new AreaTwoFragment();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.area_fragment_two;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    public void initData() {
        super.initData();
        mBaiduMap = mBinding.mapView.getMap();
        mBaiduMap.setOnMapClickListener(this);
        // 初始化定位服务
        mLocationClient = new LocationClient(mContext);
        listener = new NotiftLocationListener();
        // 注册 BDAbstractLocationListener 定位监听函数
        mLocationClient.registerLocationListener(listener);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mNotifyLister = new NotifyLister();
        mLocationClient.start();


    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        mViewModel.uc.onChangeFragment.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                    addCircleNotifyOnClick();
                } else if (integer == 2) {
                    starNotifyOnClick();
                }
            }
        });
    }


    /**
     * 地图点击事件获取中心点
     *
     * @param latLng 经纬度
     */
    @Override
    public void onMapClick(LatLng latLng) {
        if (null != mMarker) {
            mMarker.remove();
        }
        mlatitude = latLng.latitude;
        mlongitude = latLng.longitude;
        mBinding.guide.setText("纬度:" + mlatitude + "经度:" + mlongitude);
        mBinding.guide.setTextColor(Color.RED);
        MarkerOptions ooA = new MarkerOptions().position(latLng).icon(bd).zIndex(9).draggable(true);
        mMarker = (Marker) mBaiduMap.addOverlay(ooA);

        BDLocation location = new BDLocation();
        location.setLatitude(mlatitude);
        location.setLongitude(mlongitude);
        mViewModel.setLocationMessage(location, mContext);
    }

    @Override
    public void onMapPoiClick(MapPoi mapPoi) {

    }

    /**
     * 在地图中添加提醒范围
     *
     * @param v
     */
    public void addCircleNotifyOnClick() {
        mBaiduMap.clear();
        if (mlatitude == 0.0d || mlongitude == 0.0d) {
            Toast.makeText(mContext, "请点击地图添加中心点坐标", Toast.LENGTH_SHORT).show();
        } else {
            LatLng latLng = new LatLng(mlatitude, mlongitude);
            int radius = Integer.parseInt(mBinding.guide.getText().toString());
            // 添加圆
            addCircle(latLng, radius);
        }
    }


    /**
     * 开启/关闭 位置提醒
     *
     * @param v
     */
    public void starNotifyOnClick() {
        if (mBinding.startNotify.getText().toString().equals("开启提醒")) {
            if (mlatitude == 0.0d && mlongitude == 0.0d) {
                Toast.makeText(mContext, "点击地图添加提醒点", Toast.LENGTH_SHORT).show();
            } else {
                mLocationClient.registerLocationListener(mNotifyLister);
                mLocationClient.start();
                mBinding.startNotify.setText("关闭提醒");
            }
        } else {
            if (mNotifyLister != null) {
                // 取消注册的位置提醒监听
                mLocationClient.unRegisterLocationListener(mNotifyLister);
                mBinding.startNotify.setText("开启提醒");
            }
        }
    }


    /**
     * 定位请求回调接口
     */
    public class NotiftLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e("fence", "onReceiveLocation");
            //Receive Location
            double longtitude = location.getLongitude();
            double latitude = location.getLatitude();
            setCountLocationMaker(location);
//            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.getDirection()).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            // 设置定位数据
//            mBaiduMap.setMyLocationData(locData);
//
            mViewModel.setLocationMessage(location, mContext);

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(latitude, longtitude);
                MapStatus.Builder builder = new MapStatus.Builder();
                //地圖縮放
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

    }

    /**
     * 位置提醒功能，可供地理围栏需求比较小的开发者使用
     */
    public class NotifyLister extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

        }
    }


    /**
     *  設置當前位置 maker
     * @param location
     */
    public void setCountLocationMaker(BDLocation location) {
        try {
            if (location != null) {
                //获取纬度信息
                double latitude = location.getLatitude();
                //获取经度信息
                double longitude = location.getLongitude();
                LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
                // 构建Marker图标
                BitmapDescriptor bitmap = null;
                // 推算结果
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.area_icon_openmap_focuse_mark);

                // 构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
                // 在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
    }


        @Override
        public void onStop () {
            // TODO Auto-generated method stub
            super.onStop();
        }

        @Override
        public void onResume () {
            super.onResume();
            mBinding.mapView.onResume();
        }

        @Override
        public void onPause () {
            super.onPause();
            mBinding.mapView.onPause();
        }

        @Override
        public void onDestroy () {
            super.onDestroy();
            // 释放资源
            bd.recycle();
            // 取消注册的位置提醒监听
            mLocationClient.unRegisterLocationListener(mNotifyLister);
            // 停止定位
            mLocationClient.stop();
            // 释放地图资源
            mBaiduMap.clear();
            mBinding.mapView.onDestroy();
        }

        /**
         * 在地图中添加提醒范围
         *
         * @param latLng 中心点经纬度信息
         * @param radius 提醒半径
         */
        public void addCircle (LatLng latLng,int radius){
            // 绘制圆
            OverlayOptions ooCircle = new CircleOptions().fillColor(0x000000FF)
                    .center(latLng).stroke(new Stroke(5, 0xAA000000))
                    .radius(radius);
            mBaiduMap.addOverlay(ooCircle);
        }

    }













