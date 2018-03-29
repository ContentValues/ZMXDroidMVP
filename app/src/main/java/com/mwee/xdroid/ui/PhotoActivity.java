package com.mwee.xdroid.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import cn.droidlover.xdroid.base.XActivity;

/**
 * Created by zhangmin on 2017/9/27.
 */

public class PhotoActivity extends XActivity {
    @Override
    public void initData(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(PhotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PhotoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MEDIA_CONTENT_CONTROL}, 1);
        }

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ContextCompat.checkSelfPermission(PhotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
