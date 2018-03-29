package com.mwee.xdroid.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mwee.xdroid.R;

import cn.droidlover.xdroid.base.XActivity;

/**
 * Created by zhangmin on 2017/10/2.
 */

public class XReclyViewDemoActivity extends XActivity {


    @Override
    public void initData(Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.xReclyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(null);

    }

    @Override
    public int getLayoutId() {
        return R.layout.xreclyview_activity;
    }
}
