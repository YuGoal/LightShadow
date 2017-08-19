package com.caoyu.lightshadow.ui;

import android.caoyu.com.lightshadow.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.caoyu.lightshadow.ui.recommend.PicFragment;
import com.caoyu.lightshadow.base.BaseActivity;
import com.caoyu.lightshadow.view.PinchImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ImagePagerActivity extends BaseActivity {

    LinkedList<PinchImageView> viewCache;
    private ViewPager mViewPager;
    private ArrayList<String> infoList;
    private List<Fragment> fragmentList;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        mViewPager = findViewById(R.id.pager);
        viewCache = new LinkedList<PinchImageView>();
        infoList = new ArrayList<>();
        infoList = getIntent().getStringArrayListExtra("infoList");
        fragmentList = new ArrayList<>();
        for (int i = 0; i < infoList.size(); i++) {
            fragmentList.add(new PicFragment(infoList.get(i)));
        }
        index = getIntent().getIntExtra("index", 0);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        mViewPager.setCurrentItem(index);
    }
}
