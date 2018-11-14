package com.ws.yonghong.doustudy.activity;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ws.yonghong.doustudy.R;
import com.ws.yonghong.doustudy.utilcode.util.LogUtils;
import com.ws.yonghong.doustudy.utilcode.util.ToastUtils;

import java.util.List;

import static android.net.wifi.WifiManager.WIFI_MODE_FULL;
import static android.net.wifi.WifiManager.WIFI_MODE_FULL_HIGH_PERF;
import static android.net.wifi.WifiManager.WIFI_MODE_SCAN_ONLY;

public class WifiLockActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "WifiLockActivity";
    private TextView tv_show;
    private Button btn_get_provider;
    private Button btn_get;

    private final static String WIFI_LOCAK_NAME = "wifi_locak_name";
    private WifiManager wifiManager;
    // 定义一个WifiLock
    private WifiManager.WifiLock mWifiLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_lock);
        btn_get_provider = (Button) findViewById(R.id.btn_get_provider);
        btn_get = (Button) findViewById(R.id.btn_get);
        btn_get_provider.setOnClickListener(this);
        btn_get.setOnClickListener(this);
        this.wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context
                .WIFI_SERVICE);
        if (this.wifiManager.isWifiEnabled()) {
            this.mWifiLock = this.wifiManager.createWifiLock(WIFI_MODE_SCAN_ONLY, WIFI_LOCAK_NAME);
        } else {
            this.mWifiLock = this.wifiManager.createWifiLock(WIFI_MODE_FULL, WIFI_LOCAK_NAME);
        }
    }


    /**
     * 锁定WifiLock
     */
    public void acquireWifiLock() {
        if (mWifiLock != null && !mWifiLock.isHeld()) {
            mWifiLock.acquire();
            ToastUtils.showShort("acquireWifiLock");
            LogUtils.i(TAG,"acquireWifiLock");
        }
    }

    /**
     * 解锁WifiLock
     */
    public void releaseWifiLock() {
        if (mWifiLock != null && mWifiLock.isHeld()) {
            mWifiLock.release();
            ToastUtils.showShort("releaseWifiLock");
            LogUtils.i(TAG, "releaseWifiLock");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:

                releaseWifiLock();
                break;
            case R.id.btn_get_provider:

                acquireWifiLock();
                break;
        }
    }
}
