package com.mwee.xdroid.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mwee.xdroid.R;

import butterknife.BindView;
import cn.droidlover.xdroid.base.XLazyFragment;
import cn.droidlover.xstatecontroller.XStateController;

/**
 * Created by zhangmin on 2017/11/16.
 */

public class WebFragment extends XLazyFragment {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.contentLayout)
    XStateController contentLayout;

    String url;


    @Override
    public void initData(Bundle savedInstanceState) {

        initContentLayout();
        initRefreshLayout();
        initWebView();
    }


    public void setParams(String url) {
        this.url = url;
    }


    @Override
    public void setListener() {

    }

    private void initContentLayout() {
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null));
    }

    private void initRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(url);
            }
        });

    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    if (contentLayout != null)
                        contentLayout.showContent();
                    if (webView != null)
                        url = webView.getUrl();
                } else {
                    if (contentLayout != null)
                        contentLayout.showLoading();
                }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);

        webView.loadUrl(url);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }
    }


}
