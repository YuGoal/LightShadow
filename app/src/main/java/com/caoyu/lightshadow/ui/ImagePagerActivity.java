package com.caoyu.lightshadow.ui;

import android.caoyu.com.lightshadow.R;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caoyu.lightshadow.base.BaseActivity;
import com.caoyu.lightshadow.view.PinchImageView;

import java.util.ArrayList;
import java.util.LinkedList;

public class ImagePagerActivity extends BaseActivity {

    LinkedList<PinchImageView> viewCache ;
    private ViewPager mViewPager;
    private ArrayList<String> infoList;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        mViewPager = findViewById(R.id.pager);
        viewCache = new LinkedList<PinchImageView>();
        infoList = new ArrayList<>();
        infoList = getIntent().getStringArrayListExtra("infoList");
        index = getIntent().getIntExtra("index",0);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return infoList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PinchImageView piv;
                if (viewCache.size() > 0) {
                    piv = viewCache.remove();
                    piv.reset();
                } else {
                    piv = new PinchImageView(ImagePagerActivity.this);
                }
                Glide.with(ImagePagerActivity.this)
                        .load(infoList.get(position))
                        .placeholder(R.color.cardview_light_background) // can also be a drawable
                        .error(R.color.cardview_dark_background) // will be displayed if the image cannot be loaded
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(piv);
                container.addView(piv);
                return piv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                PinchImageView piv = (PinchImageView) object;
                container.removeView(piv);
                viewCache.add(piv);
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                PinchImageView piv = (PinchImageView) object;
                Glide.with(ImagePagerActivity.this)
                        .load(infoList.get(position))
                        .placeholder(R.color.cardview_light_background) // can also be a drawable
                        .error(R.color.cardview_dark_background) // will be displayed if the image cannot be loaded
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(piv);
            }
        });
    }
}
