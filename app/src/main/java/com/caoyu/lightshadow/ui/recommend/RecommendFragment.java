package com.caoyu.lightshadow.ui.recommend;

import android.caoyu.com.lightshadow.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caoyu.lightshadow.api.Api;
import com.caoyu.lightshadow.api.RecommendApi;
import com.caoyu.lightshadow.api.model.IdList;
import com.caoyu.lightshadow.api.model.One;
import com.caoyu.lightshadow.util.GridSpacingItemDecoration;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zyao89.view.zloading.Z_TYPE.LEAF_ROTATE;

/**
 * 推荐
 */
public class RecommendFragment extends Fragment {
    RecyclerView mRecycleview;
    private RecommendAdapter mAdapter;
    private List<One.ResultsBean> mData;
    ZLoadingDialog dialog;

    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        mRecycleview = view.findViewById(R.id.recycleview);
        dialog = new ZLoadingDialog(getActivity());//动画
        initData();
        mData = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycleview.setLayoutManager(layoutManager);
        mRecycleview.addItemDecoration(new GridSpacingItemDecoration(1, 10, true));
        mRecycleview.setHasFixedSize(false);
        return view;
    }


    private void initData() {
        dialog.setLoadingBuilder(LEAF_ROTATE)//设置类型
                .setLoadingColor(R.color.orange)//颜色
                .setHintText("Loading...")
                .show();
        //获取列表
        Api.getRetrofit().create(RecommendApi.class).getMeizi("1")
                .enqueue(new Callback<One>() {
                    @Override
                    public void onResponse(Call<One> call, Response<One> response) {
                        if (response.isSuccess()) {
                            for (One.ResultsBean i : response.body().getResults()) {
                                mData.add(i);
                            }
                            //设置adapter
                            mAdapter = new RecommendAdapter(getActivity(), mData);
                            mRecycleview.setAdapter(mAdapter);
                            dialog.dismiss();
                        }else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<One> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
    }
}
