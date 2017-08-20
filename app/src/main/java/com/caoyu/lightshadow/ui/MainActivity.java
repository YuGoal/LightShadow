package com.caoyu.lightshadow.ui;

import android.caoyu.com.lightshadow.R;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    private List<Fragment> fragmentContainter;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            animateToolbar();
        }
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragment();
    }

    private void initFragment() {
        fragmentContainter = new ArrayList<>();
        RecommendFragment recommendFragment = new RecommendFragment();
        FindFragment findFragment = new FindFragment();
        fragmentContainter.add(findFragment);
        fragmentContainter.add(recommendFragment);
        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {

                return fragmentContainter.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragmentContainter.get(arg0);
            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    mNavigation.getMenu().getItem(0).setChecked(false);
                }
                mNavigation.getMenu().getItem(position).setChecked(true);

                prevMenuItem = mNavigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewpager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mViewpager.setCurrentItem(1);
                    return true;
            }
            return false;
        }

    };

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
                    .setDuration(1500)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(this));
        }
    }
}
