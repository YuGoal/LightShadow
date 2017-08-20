package com.caoyu.lightshadow.ui.find;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.caoyu.com.lightshadow.R;

import com.caoyu.lightshadow.api.Api;
import com.caoyu.lightshadow.api.RecommendApi;
import com.caoyu.lightshadow.api.model.One;
import com.caoyu.lightshadow.api.model.Two;
import com.caoyu.lightshadow.base.BaseFragment;
import com.caoyu.lightshadow.ui.recommend.RecommendAdapter;
import com.jungle.mediaplayer.base.VideoInfo;
import com.jungle.mediaplayer.widgets.JungleMediaPlayer;
import com.jungle.mediaplayer.widgets.SimpleJungleMediaPlayerListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 发现
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment {
    RecyclerView mRecycleview;
    SmartRefreshLayout mSmartrefreshlayout;
    private RecommendAdapter mAdapter;
    private List<Two.ResultsBean> mData;
    ZLoadingDialog dialog;
    private int index = 1;
    JungleMediaPlayer mMediaPlayer;
    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        mMediaPlayer = view.findViewById(R.id.video);
        mMediaPlayer.setPlayerListener(new SimpleJungleMediaPlayerListener() {
            @Override
            public void onTitleBackClicked() {
                if (mMediaPlayer.isFullscreen()) {
                    mMediaPlayer.switchFullScreen(false);
                    return;
                }
                getActivity().finish();
            }
        });
        Api.getRetrofit().create(RecommendApi.class).getVideo("1")
                .enqueue(new Callback<Two>() {
                    @Override
                    public void onResponse(Call<Two> call, Response<Two> response) {
                        // play
                        String videoUrl = response.body().getResults().get(1).getUrl();
                        mMediaPlayer.playMedia(new VideoInfo(videoUrl));
                    }

                    @Override
                    public void onFailure(Call<Two> call, Throwable t) {

                    }
                });

        return view;
    }

}
