package com.mwee.xdroid.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mwee.xdroid.adpter.GirlAdapter;
import com.mwee.xdroid.model.Item;

import cn.droidlover.xdroidbase.base.SimpleItemCallback;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

/**
 * Created by zhangmin on 2017/9/15.
 */

public class GirlFragment extends BasePagerFragment {

    GirlAdapter girlAdapter;

    @Override
    public SimpleRecAdapter getAdapter() {
        if (girlAdapter == null) {
            girlAdapter = new GirlAdapter(getContext());
            girlAdapter.setItemClick(new SimpleItemCallback<Item,GirlAdapter.ViewHolder>(){
                @Override
                public void onItemClick(int position, Item model, int tag, GirlAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    WebActivity.launch(context, model.getUrl(), model.getDesc());
                }
            } );
        }
        return girlAdapter;
    }

    @Override
    public String getType() {
        return "福利";
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        return gridLayoutManager;
    }


    public static GirlFragment newInstance() {
        return new GirlFragment();
    }
}
