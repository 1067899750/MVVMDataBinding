package com.example.z_lib_base.utils;

import android.content.Context;
import android.webkit.WebView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class LocationService {
    private static volatile LocationService mInstance;
    private LocationClient client = null;
    private LocationClientOption mOption;
    private BDLocation bdLocation;
    private List<BDAbstractLocationListener> mListeners;

    /***
     *
     * @param context
     */
    public LocationService(Context context) {
        mListeners = new ArrayList<>();
        if (client == null) {
            client = new LocationClient(context);
            client.setLocOption(getDefaultLocationClientOption());
        }
        client.registerLocationListener(mLocationListener);
    }

    private BDAbstractLocationListener mLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            stop();
            if (bdLocation != null && (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation
                    || bdLocation.getLocType() == BDLocation.TypeOffLineLocation)) {
                setBdLocation(bdLocation);
                if (onLocationBackListenner != null){
                    onLocationBackListenner.onBack(bdLocation);
                }
            } else {
                setBdLocation(null);
            }
            if (mListeners == null){
                return;
            }
            for (BDAbstractLocationListener listener : mListeners) {
                if (LocationService.mInstance == null){
                    continue;
                }
                listener.onReceiveLocation(LocationService.mInstance.bdLocation);
            }
            mListeners.clear();
        }
    };

    public static LocationService getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LocationService.class) {
                if (mInstance == null) {
                    mInstance = new LocationService(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }




    public boolean registerListener(BDAbstractLocationListener listener) {
        boolean isSuccess = false;
        if (listener != null) {
            client.registerLocationListener(listener);
            isSuccess = true;
        }
        return isSuccess;
    }



    public void unregisterListener(BDAbstractLocationListener listener) {
        if (client != null && listener != null) {
            client.unRegisterLocationListener(listener);
        }
    }


    /**
     * 回调定位，当定位失败回调缓存的location对象
     */
    public void callback(@NonNull BDAbstractLocationListener listener) {
        if (bdLocation != null) {
            listener.onReceiveLocation(bdLocation);
        } else {
            mListeners.add(listener);
            start();
        }
    }


    /***
     *
     * @param option
     * @return isSuccessSetOption
     */
    public boolean setLocationOption(LocationClientOption option) {
        boolean isSuccess = false;
        if (option != null && client != null) {
            stop();
            client.setLocOption(option);
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * 开发者应用如果有H5页面使用了百度JS接口，该接口可以辅助百度JS更好的进行定位
     *
     * @param webView 传入webView控件
     */
    public void enableAssistanLocation(WebView webView) {
        if (client != null) {
            client.enableAssistantLocation(webView);
        }
    }

    /**
     * 停止H5辅助定位
     */
    public void disableAssistantLocation() {
        if (client != null) {
            client.disableAssistantLocation();
        }
    }

    /***
     *
     * @return DefaultLocationClientOption  默认O设置
     */
    public LocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setOpenGps(false);//可选，默认false，设置是否开启Gps定位
            mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用

        }
        return mOption;
    }


    /**
     * @return DIYOption 自定义Option设置
     */
    public LocationClientOption getOption() {
        return new LocationClientOption();
    }

    public synchronized boolean start() {
        if (client != null && !client.isStarted()) {
            client.start();
            System.out.println("location start()");
            return true;
        }
        return false;
    }

    public synchronized void stop() {
        if (client != null && client.isStarted()) {
            System.out.println("location  stop()");
            client.stop();
        }
    }

    public boolean isStart() {
        return client.isStarted();
    }

    public boolean requestHotSpotState() {
        return client.requestHotSpotState();
    }

    public BDLocation getBdLocation() {
        return bdLocation;
    }

    public void setBdLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }

    /**
     * 销毁定位对象
     */
    public void onDestroy() {
        if (client != null) {
            client.unRegisterLocationListener(mLocationListener);
        }
        stop();
        client = null;
        mInstance = null;
    }

    private OnLocationBackListenner onLocationBackListenner;
    public void setOnLocationBackListenner(OnLocationBackListenner onLocationBackListenner){
        this.onLocationBackListenner = onLocationBackListenner;
    }
    public interface OnLocationBackListenner{
        void onBack(BDLocation bdLocation);
    }
}

