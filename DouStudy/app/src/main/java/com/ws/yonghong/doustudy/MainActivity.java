package com.ws.yonghong.doustudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ws.yonghong.doustudy.utilcode.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MainRecyclerViewAdapter mAdapter;
    private List<ItemMainBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGetData();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_activity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mRecyclerView.addItemDecoration(new MainDividerDecoration(this));
//初始化适配器
        mAdapter = new MainRecyclerViewAdapter(mList);
//设置添加或删除item时的动画，这里使用默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//设置适配器
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initGetData() {

        List<String> listStr = ResourceUtils.readAssets2List("list.txt");
        if (listStr == null || listStr.isEmpty()) {
            return;
        }
        for (String str : listStr) {
            String[] strMany = str.split(",");
            if (strMany.length == 3) {
                ItemMainBean mItemMainBean = new ItemMainBean(strMany[0].trim(), strMany[1].trim(), strMany[2].trim());
                if (mItemMainBean == null) {
                    return;
                }
                mList.add(mItemMainBean);
            }
        }
    }
}
