package com.caoyu.lightshadow.ui.recommend;

import android.caoyu.com.lightshadow.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caoyu.lightshadow.api.Api;
import com.caoyu.lightshadow.api.RecommendApi;
import com.caoyu.lightshadow.api.model.One;
import com.caoyu.lightshadow.util.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
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
public class RecommendFragment extends Fragment implements OnRefreshLoadmoreListener {
    RecyclerView mRecycleview;
    SmartRefreshLayout mSmartrefreshlayout;
    private RecommendAdapter mAdapter;
    private List<One.ResultsBean> mData;
    ZLoadingDialog dialog;
    private int index = 1;

    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        mRecycleview = view.findViewById(R.id.recycleview);
        mSmartrefreshlayout = view.findViewById(R.id.smartrefreshlayout);
        dialog = new ZLoadingDialog(getActivity());//动画
        dialog.setLoadingBuilder(LEAF_ROTATE)//设置类型
                .setLoadingColor(R.color.orange)//颜色
                .setHintText("Loading...")
                .show();
        mSmartrefreshlayout.setOnRefreshLoadmoreListener(this);
        initData();
        //布局管理
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecycleview.setLayoutManager(layoutManager);
        mRecycleview.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        mRecycleview.setHasFixedSize(false);
        return view;
    }

    private void initData() {
        index = 1;
        //获取列表
        Api.getRetrofit().create(RecommendApi.class).getMeizi(String.valueOf(index))
                .enqueue(new Callback<One>() {
                    @Override
                    public void onResponse(Call<One> call, Response<One> response) {
                        if (response.isSuccess()) {
                            mData = new ArrayList<>();
                            for (One.ResultsBean i : response.body().getResults()) {
                                mData.add(i);
                            }
                            //设置adapter
                            mAdapter = new RecommendAdapter(getActivity(), mData);
                            mRecycleview.setAdapter(mAdapter);
                        }
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        mSmartrefreshlayout.finishRefresh();
                    }

                    @Override
                    public void onFailure(Call<One> call, Throwable t) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        mSmartrefreshlayout.finishRefresh();
                    }
                });
    }

    private void loadData() {
        index = ++index;
        //获取列表
        Api.getRetrofit().create(RecommendApi.class).getMeizi(String.valueOf(index))
                .enqueue(new Callback<One>() {
                    @Override
                    public void onResponse(Call<One> call, Response<One> response) {
                        if (response.isSuccess() && response.body().getResults().size() > 0) {
                            for (One.ResultsBean i : response.body().getResults()) {
                                mData.add(i);
                            }
                            //设置adapter
                            mAdapter.notifyDataSetChanged();
                        }
                        mSmartrefreshlayout.finishLoadmore();
                    }

                    @Override
                    public void onFailure(Call<One> call, Throwable t) {
                        mSmartrefreshlayout.finishLoadmore();
                    }
                });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        loadData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        initData();
    }
}
