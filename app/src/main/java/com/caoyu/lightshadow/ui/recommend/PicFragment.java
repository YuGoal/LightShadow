package com.caoyu.lightshadow.ui.recommend;


import android.annotation.SuppressLint;
import android.caoyu.com.lightshadow.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caoyu.lightshadow.ui.ImagePagerActivity;
import com.caoyu.lightshadow.view.PinchImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class PicFragment extends Fragment {
    private String mUrl;

    public PicFragment(String url) {
        this.mUrl = url;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic, container, false);
        PinchImageView piv = view.findViewById(R.id.pinch_image);
        Glide.with(getActivity())
                .load(mUrl)
                .placeholder(R.color.cardview_dark_background) // can also be a drawable
                .error(R.color.cardview_dark_background) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(piv);
        return view;
    }
}
