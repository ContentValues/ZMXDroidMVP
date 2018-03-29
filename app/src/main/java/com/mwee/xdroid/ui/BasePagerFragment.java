package com.mwee.xdroid.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mwee.xdroid.R;
import com.mwee.xdroid.model.GankResults;
import com.mwee.xdroid.net.JsonCallback;
import com.mwee.xdroid.net.NetApi;
import com.mwee.xdroid.net.NetError;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroid.base.XLazyFragment;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;
import okhttp3.Call;

/**
 * Created by zhangmin on 2017/9/14.
 */

public abstract class BasePagerFragment extends XLazyFragment {


    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;
    @BindView(R.id.tvError)
    TextView tvError;


    protected static final int PAGE_SIZE = 10;
    protected static final int MAX_PAGE = 10;

    @Override
    public void initData(Bundle savedInstanceState) {

        initAdapter();
        loadData(1);
    }


    private void initAdapter() {
        XRecyclerView xRecyclerView = contentLayout.getRecyclerView();

        //xRecyclerView.verticalLayoutManager(getContext());
        xRecyclerView.setLayoutManager(getLayoutManager());
        xRecyclerView.setAdapter(getAdapter());

        xRecyclerView.setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                loadData(1);
            }

            @Override
            public void onLoadMore(int page) {
                loadData(page);
            }
        });
        xRecyclerView.useDefLoadMoreView();
    }


    public void loadData(final int currentPager) {

        NetApi.getGankData(getType(), PAGE_SIZE, currentPager, new JsonCallback<GankResults>(1 * 60 * 60 * 1000) {
            @Override
            public void onFail(Call call, NetError e, int id) {
                //contentLayout.showError();
                showError(e);
            }

            @Override
            public void onResponse(GankResults response, int id) {
                if (!response.isError()) {
                    if (currentPager <= 1) {
                        getAdapter().setData(response.getResults());
                    } else {
                        getAdapter().addData(response.getResults());
                    }
                    contentLayout.getRecyclerView().setPage(currentPager, MAX_PAGE);
                    if (getAdapter().getItemCount() < 1) {
                        contentLayout.showEmpty();
                        return;
                    }

                }

            }
        });

    }

    public void showError(NetError error) {
        if (error != null) {
            switch (error.getType()) {
                case NetError.ParseError:
                    tvError.setText("数据解析异常");
                    break;

                case NetError.AuthError:
                    tvError.setText("身份验证异常");
                    break;

                case NetError.BusinessError:
                    tvError.setText("业务异常");
                    break;

                case NetError.NoConnectError:
                    tvError.setText("网络无连接");
                    break;

                case NetError.NoDataError:
                    tvError.setText("数据为空");
                    break;

                case NetError.OtherError:
                    tvError.setText("其他异常");
                    break;
            }
            contentLayout.showError();
        }
    }


    @OnClick(
            R.id.tvError
    )
    public void clickEvent(View view) {
        switch (view.getId()) {

            case R.id.tvError:
                contentLayout.getRecyclerView().onRefresh();
                break;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_pager;
    }


    public abstract SimpleRecAdapter getAdapter();


    public abstract String getType();


    public abstract RecyclerView.LayoutManager getLayoutManager();
}
