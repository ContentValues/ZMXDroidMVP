package com.mwee.xdroid.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mwee.xdroid.adpter.HomeAdapter;
import com.mwee.xdroid.model.Item;
import cn.droidlover.xdroidbase.base.SimpleItemCallback;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;
import cn.droidlover.xdroidbase.log.XLog;

/**
 * Created by zhangmin on 2017/9/14.
 */

public class HomeFragment extends BasePagerFragment {

    HomeAdapter homeAdapter;

    public SimpleRecAdapter getAdapter() {

        if (homeAdapter == null) {
            homeAdapter = new HomeAdapter(getContext());
            homeAdapter.setItemClick(new SimpleItemCallback<Item, HomeAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, Item model, int tag, HomeAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    WebActivity.launch(context, model.getUrl(), model.getDesc());
                }
            });
        }
        return homeAdapter;

    }

    @Override
    public String getType() {
        return "all";
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

}
