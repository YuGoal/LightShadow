package com.caoyu.lightshadow.ui.find;

import android.caoyu.com.lightshadow.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.caoyu.lightshadow.api.Api;
import com.caoyu.lightshadow.api.JuheApi;
import com.caoyu.lightshadow.api.model.One;
import com.caoyu.lightshadow.api.model.Two;
import com.caoyu.lightshadow.ui.recommend.RecommendAdapter;
import com.caoyu.lightshadow.util.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.caoyu.lightshadow.api.Api.APPKEY;
import static com.zyao89.view.zloading.Z_TYPE.LEAF_ROTATE;

/**
 * 发现
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment implements OnRefreshLoadmoreListener {
    RecyclerView mRecycleview;
    SmartRefreshLayout mSmartrefreshlayout;
    private TodayAdapter mAdapter;
    private List<Two.ResultBean> mData;
    ZLoadingDialog dialog;

    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecycleview.setLayoutManager(layoutManager);
        mRecycleview.addItemDecoration(new GridSpacingItemDecoration(1, 10, true));
        mRecycleview.setHasFixedSize(false);

        return view;
    }

    private void initData() {
        Map params = new HashMap();//请求参数
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("v", "1.0");//版本，当前：1.0
        params.put("month", "8");//月
        params.put("day", "21");//日
        Api.getRetrofit(Api.JUHE_URL).create(JuheApi.class).toDay(params)
                .enqueue(new Callback<Two>() {
                    @Override
                    public void onResponse(Call<Two> call, Response<Two> response) {
                        if (response.isSuccess()) {
                            mData = new ArrayList<>();
                            for (Two.ResultBean i : response.body().getResult()) {
                                mData.add(i);
                            }
                            //设置adapter
                            mAdapter = new TodayAdapter(getActivity(), mData);
                            mRecycleview.setAdapter(mAdapter);
                        }
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        mSmartrefreshlayout.finishRefresh();
                    }

                    @Override
                    public void onFailure(Call<Two> call, Throwable t) {
                        mSmartrefreshlayout.finishRefresh();
                    }
                });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }
}
