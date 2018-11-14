package com.ws.yonghong.doustudy.function.rxjavaretrofit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ws.yonghong.doustudy.R;
import com.ws.yonghong.doustudy.utilcode.util.ToastUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxjavaRetrofitActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        initHtttp();
    }

    private void initHtttp() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService github = retrofit.create(GitHubService.class);
        github.listRepos("dou");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
