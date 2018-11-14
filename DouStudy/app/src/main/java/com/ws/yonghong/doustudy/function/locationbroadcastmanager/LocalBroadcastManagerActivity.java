package com.ws.yonghong.doustudy.function.locationbroadcastmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ws.yonghong.doustudy.R;
import com.ws.yonghong.doustudy.utilcode.util.ToastUtils;

public class LocalBroadcastManagerActivity extends AppCompatActivity {

    IntentFilter filter;
    LocalBroadcastManager mLocalBroadcastManager;
    public static final String ACTION_STR = "con.dou.to.broadcast";
    private Button btn_send_broad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        filter = new IntentFilter();
        filter.addAction(ACTION_STR);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);//获得实例
        mLocalBroadcastManager.registerReceiver(receiver, filter);//注册监听

        btn_send_broad = findViewById(R.id.btn_send_broad);
        btn_send_broad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.setAction(ACTION_STR);
                LocalBroadcastManager.getInstance(LocalBroadcastManagerActivity.this).sendBroadcastSync(mIntent);
            }
        });
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_STR)) {
                ToastUtils.showShort("我收到了本地广播");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(receiver);//取消监听，注意unregisterReceiver()方法来自LocalBroadcastManager;
    }
}
