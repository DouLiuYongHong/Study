package com.ws.yonghong.doustudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ws.yonghong.doustudy.R;
import com.ws.yonghong.doustudy.View.RipplesView;
import com.ws.yonghong.doustudy.task.AbsTask;
import com.ws.yonghong.doustudy.task.TaskManager;
import com.ws.yonghong.doustudy.utilcode.util.LogUtils;

public class TaskManagerActivity extends AppCompatActivity {


    private RipplesView mRipplesView;
    AbsTask<String> absTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripples);
        mRipplesView = findViewById(R.id.ripplesView);
    }


    public void start(View v) {
        absTask = TaskManager.task().start(new AbsTask<String>() {
            @Override
            protected String doBackground() throws Throwable {
                LogUtils.i("正在执行代码中......");

                return "执行完成了";
            }

            @Override
            protected void onSuccess(String result) {
                LogUtils.i(result);
            }

            @Override
            protected void onError(Throwable ex, boolean isCallbackError) {

            }
        });
    }

    public void stop(View v) {
        if (!absTask.isCancelled()) {
            absTask.cancel();
        }

    }
}
