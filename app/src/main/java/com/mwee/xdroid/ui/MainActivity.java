package com.mwee.xdroid.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mwee.xdroid.R;
import com.mwee.xdroid.demo.ExpandableActivity;
import com.mwee.xdroid.demo.TestActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroid.base.XActivity;
import cn.droidlover.xdroidbase.base.XFragmentAdapter;

public class MainActivity extends XActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    List<Fragment> fragmentList = new ArrayList<>();
    String[] titles = {"首页", "妹子", "happer*", "家的", "小野猫", "作品"};
    XFragmentAdapter adapter;


    @Override
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(GirlFragment.newInstance());

        WebFragment fragment1 = new WebFragment();
        fragment1.setParams("http://7xi8d6.com1.z0.glb.clouddn.com/20171025112955_lmesMu_katyteiko_25_10_2017_11_29_43_270.jpeg");

        WebFragment fragment2 = new WebFragment();
        fragment2.setParams("https://ws1.sinaimg.cn/large/610dc034ly1fjqw4n86lhj20u00u01kx.jpg");

        WebFragment fragment3 = new WebFragment();
        fragment3.setParams("https://ws1.sinaimg.cn/large/610dc034ly1fjs25xfq48j20u00mhtb6.jpg");

        WebFragment fragment4 = new WebFragment();
        fragment4.setParams("https://ws1.sinaimg.cn/large/610dc034ly1fk05lf9f4cj20u011h423.jpg");

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);

        adapter = new XFragmentAdapter(getSupportFragmentManager(), fragmentList, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(6);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            TestActivity.launch(this);
            return true;
        }
        if (id == R.id.action_expandable) {
            ExpandableActivity.launch(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
