package com.example.openstop.openstop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Tianshunkai on 2017/12/1.
 */

public class zhoubian extends Activity implements LocationSource,
        AMapLocationListener, AMap.OnCameraChangeListener, PoiSearch.OnPoiSearchListener {



    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_DES = "des";



    private  MapView mapView;
    private ListView mapList;


    private AMapLocationClient mLocationClient;
    private LocationSource.OnLocationChangedListener mListener;
    private LatLng latlng;
    private String city;
    private String eric;
    private AMap aMap;
    private String deepType = "";// poi搜索类型
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private PoiResult poiResult; // poi返回的结果
    //private PoiOverlay poiOverlay;// poi图层
    private List<PoiItem> poiItems;// poi数据

    private PoiSearch_adapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_zhoubian);

        Intent intent = getIntent();
        eric = intent.getStringExtra("eric");

        mapView=(MapView)findViewById(R.id.map_local);
        mapList=(ListView)findViewById(R.id.map_list);
        mapView.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnCameraChangeListener(this);
            setUpMap();
        }

        deepType = eric;//这里以餐饮为例
    }

    private void setUpMap() {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getApplicationContext());
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.close));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    protected void doSearchQuery() {
        aMap.setOnMapClickListener(null);// 进行poi搜索时清除掉地图点击事件
        int currentPage = 0;
        query = new PoiSearch.Query(deepType,"" , city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        LatLonPoint lp = new LatLonPoint(latlng.latitude, latlng.longitude);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));
        // 设置搜索区域为以lp点为圆心，其周围2000米范围
        poiSearch.searchPOIAsyn();// 异步搜索

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                // 显示我的位置
                mListener.onLocationChanged(aMapLocation);
                //设置第一次焦点中心
                latlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14), 1000, null);
                city = aMapLocation.getProvince();
                doSearchQuery();
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        latlng = cameraPosition.target;
        aMap.clear();
        aMap.addMarker(new MarkerOptions().position(latlng));
        doSearchQuery();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        mLocationClient.startLocation();
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if(rCode==1000)
        {
            if(result!=null&&result.getQuery()!=null)
            {
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();
                    if (poiItems != null && poiItems.size() > 0) {
                        adapter = new PoiSearch_adapter();
                        mapList.setAdapter(adapter);
                        //mapList.setOnItemClickListener(new mOnItemClickListener());

                    }
                }else {
                    Toast.makeText(this,"无结果", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this,"无结果", Toast.LENGTH_SHORT).show();
            }


        }else if (rCode == 27) {
            Toast.makeText(this,"error_network", Toast.LENGTH_SHORT).show();

        } else if (rCode == 32) {
            Toast.makeText(this,"error_key", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this,"error_other"+rCode, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationClient.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    public class PoiSearch_adapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return poiItems.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            View view;
            if (convertView == null) {
                view = View.inflate(zhoubian.this, R.layout.layout_item, null);
            }else{
                view = convertView;
            }
            TextView tv_name = (TextView) view.findViewById(R.id.item_tv);
            tv_name.setText(poiItems.get(position).getTitle());
            TextView tv_content = (TextView) view.findViewById(R.id.item_tv2);
            tv_content.setText(poiItems.get(position).getSnippet());
            return view;
        }
    }
}

