package com.caoyu.lightshadow.ui.recommend;

import android.caoyu.com.lightshadow.R;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.caoyu.lightshadow.api.Api;
import com.caoyu.lightshadow.api.RecommendApi;
import com.caoyu.lightshadow.api.model.Recommend;
import com.caoyu.lightshadow.util.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 推荐
 */
public class RecommendFragment extends Fragment {
    RecyclerView mRecycleview;
    private RecommendAdapter mAdapter;
    private List<Recommend.FeedListBean> mData;

    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        mRecycleview = view.findViewById(R.id.recycleview);
        initData();
        mData = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycleview.setLayoutManager(layoutManager);
        mRecycleview.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        mRecycleview.setHasFixedSize(false);
        return view;
    }



    private void initData() {
        Api.getRetrofit().create(RecommendApi.class).getRecommend()
                .enqueue(new Callback<Recommend>() {
                    @Override
                    public void onResponse(Call<Recommend> call, Response<Recommend> response) {
                        if ("SUCCESS".equals(response.body().getResult())) {
                            if (response.body().getFeedList().size() > 0) {
                                for (Recommend.FeedListBean i : response.body().getFeedList()) {
                                    mData.add(i);
                                }
                                //设置adapter
                                mAdapter = new RecommendAdapter(getActivity(), mData);
                                mRecycleview.setAdapter(mAdapter);
                                Toast.makeText(getContext(),"请求成功"+mData.size(),Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(getView(),"请求失败",100);
                        }
                    }

                    @Override
                    public void onFailure(Call<Recommend> call, Throwable t) {
                        Snackbar.make(getView(),"请求失败",100);
                    }
                });
    }
}
