package com.caoyu.lightshadow.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by caoyu on 2017/9/14.
 */

public class MyAdapter extends FragmentPagerAdapter {

    private String[] titleList;
    private ArrayList<Fragment> fragmentList;

    public MyAdapter(FragmentManager fm, String[] titleList, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList[position];
    }
}