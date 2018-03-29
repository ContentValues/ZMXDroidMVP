package com.mwee.xdroid.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mwee.xdroid.model.Item;
import com.mwee.xdroid.R;

import butterknife.BindView;
import cn.droidlover.xdroid.kit.KnifeKit;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;
import cn.droidlover.xdroidbase.imageloader.ILFactory;

/**
 * Created by wanglei on 2016/12/10.
 */

public class GirlAdapter extends SimpleRecAdapter<Item, GirlAdapter.ViewHolder> {


    public GirlAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_girl;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Item item = data.get(position);

        ILFactory.getLoader().loadNet(holder.ivGirl, item.getUrl(), null);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSimpleItemClick() != null) {
                    getSimpleItemClick().onItemClick(position, item, 0, holder);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_girl)
        ImageView ivGirl;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
