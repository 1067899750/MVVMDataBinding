package com.example.z_module_service.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.z_lib_base.base.BaseMVVMActivity;
import com.example.z_lib_base.utils.StringUtils;
import com.example.z_lib_common.utils.ValueUtil;
import com.example.z_module_service.BR;
import com.example.z_module_service.R;
import com.example.z_module_service.adapter.MallMapDialogAdapter;
import com.example.z_module_service.bean.MallCustomBusinessAddressBean;
import com.example.z_module_service.databinding.BookAssetsDetailMapActivityBinding;
import com.example.z_module_service.viewmodel.BookAssetsDetailMapViewModel;


import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author puyantao
 * @description 地图
 * @date 2020/5/7 9:09
 */
public class BookAssetsDetailMapActivity extends BaseMVVMActivity<BookAssetsDetailMapActivityBinding, BookAssetsDetailMapViewModel>
        implements BaseQuickAdapter.OnItemClickListener {
    private PoiSearch mPoiSearch;
    public static int GET_MAP = 55;
    public static int RESULT_MAP_OK = 56;
    BaiduMap mBaiduMap;

    ArrayList<MallCustomBusinessAddressBean> addressBeans;
    public GeoCoder geoCoder;
    public int beforeCount = 0;
    private String searchDetail;
    /**
     * 显示的位置
     */
    private String showLocDetail;
    private String lon;
    private String lat;

    private String mResultLon;
    private String mResultLat;
    /**
     * 是根据经纬度搜索还是地址, true 经纬度， false 地址
     */
    private boolean isSearchType;
    /**
     * 是否带小区和厂房名字定位
     */
    private boolean isHaveFirstLoc;
    private String houseLoc;
    /**
     * 控制是否第一次进入地图设置当前位置
     */
    private boolean isFirstShow = true;
    /**
     * 控制移动时输入框是否变化
     */
    private boolean isRemoveLoc = true;
    /**
     * 定位的当前位置
     */
    private String locAddress;
    private MallMapDialogAdapter mallMapDialogAdapter;
    private boolean canTouchSearch = false;

    /**
     * 移动根据经纬度定位返回结果相关
     */
    public OnGetGeoCoderResultListener geoCoderResultListener = new OnGetGeoCoderResultListener() {
        //把地址转换成经纬度
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            if (null != geoCodeResult && null != geoCodeResult.getLocation()) {
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(BookAssetsDetailMapActivity.this, "搜索结果出错,请稍后再试", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    double latitude = geoCodeResult.getLocation().latitude;
                    double longitude = geoCodeResult.getLocation().longitude;
                    final MallCustomBusinessAddressBean loc = new MallCustomBusinessAddressBean();
                    loc.setNameDetail(geoCodeResult.getAddress());
                    loc.setLon(longitude);
                    loc.setLat(latitude);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            moveToAddress(latitude, longitude);
                        }
                    }, 200);

                }
            }
        }

        //经纬度转换成地址
        @Override
        public void onGetReverseGeoCodeResult(final ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
                return;
            } else {
                //详细地址
                if (StringUtils.isEmpty(reverseGeoCodeResult.getSematicDescription())) {
                    return;
                }
                MallCustomBusinessAddressBean wbLifeCustomBusinessAddressBean = new MallCustomBusinessAddressBean();
                wbLifeCustomBusinessAddressBean.setLat(reverseGeoCodeResult.getLocation().latitude);
                wbLifeCustomBusinessAddressBean.setLon(reverseGeoCodeResult.getLocation().longitude);
                wbLifeCustomBusinessAddressBean.setNameDetail(reverseGeoCodeResult.getSematicDescription());

                if (addressBeans.size() != 0) {
                    addressBeans.remove(addressBeans.size() - 1);
                }
                addressBeans.add(wbLifeCustomBusinessAddressBean);
                mallMapDialogAdapter.replaceData(addressBeans);
                //设置地址
                if (isRemoveLoc) {
                    mBinding.addressEt.setText(reverseGeoCodeResult.getSematicDescription());
                    mResultLon = reverseGeoCodeResult.getLocation().longitude + "";
                    mResultLat = reverseGeoCodeResult.getLocation().latitude + "";
                }
                isRemoveLoc = true;
                //行政区号
            }
        }
    };

    /**
     * 搜索结果监听
     */
    public OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            beforeCount = 0;
            if (poiResult == null || poiResult.getAllPoi() == null) {
                return;
            }
            if (poiResult.getAllPoi().size() >= 10) {
                beforeCount = 10;
            } else {
                beforeCount = poiResult.getAllPoi().size();
            }

            if (isFirstShow) {
                mBinding.addressEt.setText(searchDetail);
                mResultLon = poiResult.getAllPoi().get(0).getLocation().longitude + "";
                mResultLat = poiResult.getAllPoi().get(0).getLocation().latitude + "";
                moveToAddress(poiResult.getAllPoi().get(0).getLocation().latitude,
                        poiResult.getAllPoi().get(0).getLocation().longitude);
                if (isHaveFirstLoc) {
                    houseLoc = poiResult.getAllPoi().get(0).getAddress();
                    isRemoveLoc = false;
                }
                isFirstShow = false;
            }
            addressBeans.clear();
            for (int position = 0; position < beforeCount; position++) {
                MallCustomBusinessAddressBean businessAddressBean = new MallCustomBusinessAddressBean();
                //名字
                String nameStr = poiResult.getAllPoi().get(position).getName();
                if (ValueUtil.isStrNotEmpty(nameStr)) {
                    businessAddressBean.setNameDetail(nameStr);
                    //详细地址
                    String detailStr = poiResult.getAllPoi().get(position).getAddress();
                    businessAddressBean.setAddressLoc(detailStr);
                    //经纬度
                    if (poiResult.getAllPoi().get(position).getLocation() != null) {
                        businessAddressBean.setLon(poiResult.getAllPoi().get(position).getLocation().longitude);
                        businessAddressBean.setLat(poiResult.getAllPoi().get(position).getLocation().latitude);
                        addressBeans.add(businessAddressBean);
                    }
                }
            }

            //把搜索数据加到最后的列表
            if (ValueUtil.isStrNotEmpty(lon) && ValueUtil.isStrNotEmpty(lat)) {
                if (beforeCount == 0) {
                    addressBeans.clear();
                    MallCustomBusinessAddressBean item = new MallCustomBusinessAddressBean();
                    item.setLat(Double.parseDouble(lat));
                    item.setLon(Double.parseDouble(lon));
                    item.setNameDetail(searchDetail);
                    addressBeans.add(item);
                }

            }
            //塞入adapter中
            mallMapDialogAdapter.replaceData(addressBeans);
            mallMapDialogAdapter.notifyDataSetChanged();
            //重新塞入值
            beforeCount = addressBeans.size();

            if (StringUtils.isEmpty(lon) || StringUtils.isEmpty(lat)) {
                if (addressBeans.size() > 0) {
                    mBinding.addressEt.setText(addressBeans.get(0).getNameDetail());
                    mResultLon = addressBeans.get(0).getLon() + "";
                    mResultLat = addressBeans.get(0).getLat() + "";
                    moveToAddress(addressBeans.get(0).getLat(), addressBeans.get(0).getLon());
                }
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        }
    };

    /**
     * @param activity
     * @param detail      输入框的详细地址
     * @param lon         经度
     * @param lat         纬度
     * @param loc_address 当前定位地址
     * @param isFirstLoc  是否有默认的搜索地址
     * @param searchType  true 经纬度搜索， false 当前位置搜索
     */
    public static void startBookAssetsDetailMapActivity(Activity activity, String detail,
                                                        String lon, String lat, String loc_address,
                                                        boolean isFirstLoc, boolean searchType) {
        Intent intent = new Intent(activity, BookAssetsDetailMapActivity.class);
        intent.putExtra("detail", detail);
        intent.putExtra("search_type", searchType);
        intent.putExtra("lon", ValueUtil.isStrNotEmpty(lon) ? lon : "0.0");
        intent.putExtra("lat", ValueUtil.isStrNotEmpty(lat) ? lat : "0.0");
        intent.putExtra("loc_address", loc_address);
        intent.putExtra("first_loc", isFirstLoc);
        activity.startActivityForResult(intent, GET_MAP);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.service_activity_book_assets_cdetail_map;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        super.initParam();
        isHaveFirstLoc = getIntent().getBooleanExtra("first_loc", true);
        isSearchType = getIntent().getBooleanExtra("search_type", true);
        showLocDetail = getIntent().getStringExtra("detail");
        lon = getIntent().getStringExtra("lon");
        lat = getIntent().getStringExtra("lat");
        mResultLon = lon;
        mResultLat = lat;
        locAddress = getIntent().getStringExtra("loc_address");
        if (ValueUtil.isStrNotEmpty(showLocDetail)) {
            searchDetail = showLocDetail;
        } else {
            searchDetail = locAddress;
        }
    }

    @Override
    public void initData() {
        super.initData();
        addressBeans = new ArrayList<>();

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

        mBinding.mapListRecycler.setLayoutManager(new LinearLayoutManager(this));
        mBinding.mapListRecycler.setFocusableInTouchMode(false);

        mallMapDialogAdapter = new MallMapDialogAdapter(this);
        mallMapDialogAdapter.bindToRecyclerView(mBinding.mapListRecycler);
        mallMapDialogAdapter.setOnItemClickListener(this);
        mBinding.mapListRecycler.setAdapter(mallMapDialogAdapter);
        initSearchData();
        if (isSearchType) {
            getSearchData();
            moveToAddress(Double.parseDouble(lat), Double.parseDouble(lon));
        } else {
            mBinding.addressEt.setText(searchDetail);
            if (ValueUtil.isStrNotEmpty(lat) && ValueUtil.isStrNotEmpty(lon)) {
                moveToAddress(Double.parseDouble(lat), Double.parseDouble(lon));
            }
        }
    }

    /**
     * 搜索位置数据
     */
    public void getSearchData() {
        try {
            Double lonV = Double.valueOf(mResultLon);
            Double latV = Double.valueOf(mResultLat);
            PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                    //检索关键字
                    .keyword(searchDetail)
                    //检索位置
                    .location(new LatLng(latV, lonV))
                    //附近检索半径
                    .radius(10000);
            //发起请求
            mPoiSearch.searchNearby(nearbySearchOption);
            //释放检索对象
            mPoiSearch.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initSearchData() {
        searchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                canTouchSearch = true;
            }
        }, 500);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        //保存地理位置
        mBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                try {
                    data.putExtra("lon", mResultLon);
                    data.putExtra("lat", mResultLat);

                    if (isHaveFirstLoc && showLocDetail.equals(mBinding.addressEt.getText().toString())) {
                        data.putExtra("address", isHaveFirstLoc ? locAddress : houseLoc);
                    } else {
                        data.putExtra("address", mBinding.addressEt.getText().toString());
                    }
                    setResult(RESULT_MAP_OK, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                if (canTouchSearch) {
                    getAddressName(mapStatus);
                }
            }
        });

        mBinding.touchLine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        //取消地理位置列表
        mBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRemoveLoc = true;
                mViewModel.isShowDetailList.set(false);
            }
        });
        //搜索地理位置
        mBinding.searchAddressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.isShowDetailList.set(true);
                searchDetail = mBinding.addressEt.getText().toString();
                getSearchData();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mBinding.bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mBinding.bmapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管
        mPoiSearch.destroy();
        mBinding.bmapView.onDestroy();
    }


    /**
     * 开始检索
     */
    private void searchData() {
        mBaiduMap = mBinding.bmapView.getMap();
        mBinding.bmapView.showZoomControls(false);
        mBaiduMap.setOnMapDoubleClickListener(new BaiduMap.OnMapDoubleClickListener() {
            @Override
            public void onMapDoubleClick(LatLng latLng) {

            }
        });
        chooseMyLocation();
    }

    /**
     * 根据经纬度获取获取当前位置，在进行定位
     *
     * @param mapStatus
     */
    private void getAddressName(MapStatus mapStatus) {
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(geoCoderResultListener);
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(mapStatus.target).radius(500));
    }


    private void chooseMyLocation() {
        // 开启定位功能
        mBaiduMap.setMyLocationEnabled(true);
    }

    /**
     * 移动位置
     */
    private void moveToAddress(Double lat, Double lon) {
        LatLng cenpt = new LatLng(lat, lon);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(cenpt)
                //放大地图到18倍
                .zoom(18)
                .build();
        //改变地图状态
        //mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
    }


    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        isRemoveLoc = false;
        mViewModel.isShowDetailList.set(false);
        mBinding.addressEt.setText(addressBeans.get(i).getAddressLoc());
        mResultLon = addressBeans.get(i).getLon() + "";
        mResultLat = addressBeans.get(i).getLat() + "";
        moveToAddress(addressBeans.get(i).getLat(), addressBeans.get(i).getLon());
    }
}
