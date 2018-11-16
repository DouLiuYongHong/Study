package com.ws.yonghong.doustudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ws.yonghong.doustudy.R;
import com.ws.yonghong.doustudy.view.RipplesView;

public class RipplesViewActivity extends AppCompatActivity {


    private RipplesView mRipplesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripples);
        mRipplesView = findViewById(R.id.ripplesView);
    }


    public void start(View v) {
        mRipplesView.start();
    }

    public void stop(View v) {
        mRipplesView.stop();
    }
}
