package com.caoyu.lightshadow.ui;

import android.caoyu.com.lightshadow.R;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.caoyu.lightshadow.base.BaseActivity;
import com.caoyu.lightshadow.ui.find.FindFragment;
import com.caoyu.lightshadow.ui.recommend.RecommendFragment;
import com.caoyu.lightshadow.util.AnimUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主界面
 * Created by caoyu on 2017/8/12.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;


    private String[] tabTitleArray = {"大胸妹", "小清新", "文艺范", "性感妹", "大长腿", "黑丝袜", "小翘臀"};
    private String[] tabTpyeArray = {"34", "35", "36", "37", "38", "39", "40"};

    private ArrayList<Fragment> fragmentList  = new ArrayList<>();
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            animateToolbar();
        }
        initTab();
        initFragment();

        adapter = new MyAdapter(getSupportFragmentManager(),tabTitleArray,fragmentList);
        mViewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(mViewpager, true);
        tablayout.setTabsFromPagerAdapter(adapter);
    }

    private void initTab() {
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (String i:tabTitleArray){
            tablayout.addTab(tablayout.newTab().setText(i));
        }
    }

    private void initFragment() {
        for (int i=0;i<tabTpyeArray.length;i++){
            fragmentList.add(new RecommendFragment(tabTpyeArray[i]));
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void animateToolbar() {
        // this is gross but toolbar doesn't expose it's children to animate them :(
        View t = toolbar.getChildAt(0);
        if (t != null && t instanceof TextView) {
            TextView title = (TextView) t;

            // fade in and space out the title.  Animating the letterSpacing performs horribly so
            // fake it by setting the desired letterSpacing then animating the scaleX ¯\_(ツ)_/¯
            title.setAlpha(0f);
            title.setScaleX(0.5f);

            title.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(300)
                    .setDuration(1200)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(this));
        }
    }
}
