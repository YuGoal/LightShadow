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
    private List<One.DataBean.ContentListBean> mData;
    ZLoadingDialog dialog ;
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
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
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
        Api.getRetrofit().create(RecommendApi.class).getIdList()
                .enqueue(new Callback<IdList>() {
                    @Override
                    public void onResponse(Call<IdList> call, Response<IdList> response) {
                        if (response.body().getData().size() > 0) {
                            Api.getRetrofit().create(RecommendApi.class).getOneList(response.body().getData().get(0))
                                    .enqueue(new Callback<One>() {
                                        @Override
                                        public void onResponse(Call<One> call, Response<One> response) {
                                            if (response.body().getData().getContent_list().size() > 0) {
                                                for (One.DataBean.ContentListBean i : response.body().getData().getContent_list()) {
                                                    mData.add(i);
                                                }
                                                //设置adapter
                                                mAdapter = new RecommendAdapter(getActivity(), mData);
                                                mRecycleview.setAdapter(mAdapter);
                                            }
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onFailure(Call<One> call, Throwable t) {
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Call<IdList> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
    }
}
