package com.caoyu.lightshadow.ui.recommend;

import android.annotation.SuppressLint;
import android.caoyu.com.lightshadow.R;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caoyu.lightshadow.api.JuheApi;
import com.caoyu.lightshadow.api.ToDayApi;
import com.caoyu.lightshadow.api.model.Meizi;
import com.caoyu.lightshadow.base.BaseFragment;
import com.caoyu.lightshadow.util.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.caoyu.lightshadow.util.CrashHandler.TAG;

/**
 * 推荐
 */
@SuppressLint("ValidFragment")
public class RecommendFragment extends BaseFragment implements OnRefreshLoadmoreListener {
    RecyclerView mRecycleview;
    SmartRefreshLayout mSmartrefreshlayout;
    private RecommendAdapter mAdapter;
    private List<Meizi.ShowapiResBodyBean._$0Bean> mData;
    private int index = 1;

    private String mCode;

    public RecommendFragment(String code) {
        this.mCode = code;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        mRecycleview = view.findViewById(R.id.recycleview);
        mSmartrefreshlayout = view.findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setOnRefreshLoadmoreListener(this);
        mData = new ArrayList<>();
        //设置adapter
        mAdapter = new RecommendAdapter(getActivity(), mData);
        mRecycleview.setAdapter(mAdapter);
        index = 1;
        initData();
        //布局管理
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecycleview.setLayoutManager(layoutManager);
        mRecycleview.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));
        mRecycleview.setHasFixedSize(false);

        return view;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    protected void initData() {
        showProgressDialog("Loding...");
        index = 1;
        //获取列表
        Map<String, String> map = new HashMap<>();
        map.put("showapi_appid", ToDayApi.APPKEY);
        map.put("showapi_sign", ToDayApi.APPSIGN);
        map.put("type", mCode);
        map.put("num", "20");
        map.put("page", String.valueOf(index));
        ToDayApi.getRetrofit().create(JuheApi.class).getMeizi(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        closeProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.optInt("showapi_res_code") == 0) {
                                mData.clear();
                                JSONObject object = jsonObject.getJSONObject("showapi_res_body");
                                for (int i = 0; i < 20; i++) {
                                    Meizi.ShowapiResBodyBean._$0Bean bean = new Meizi.ShowapiResBodyBean._$0Bean();
                                    bean.setThumb(object.getJSONObject("" + i).optString("thumb"));
                                    bean.setUrl(object.getJSONObject("" + i).optString("url"));
                                    bean.setTitle(object.getJSONObject("" + i).optString("title"));
                                    mData.add(bean);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mSmartrefreshlayout.finishRefresh();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        closeProgressDialog();
                        mSmartrefreshlayout.finishRefresh();
                    }
                });
    }

    private void loadData() {
        index = ++index;
        //获取列表
        Map<String, String> map = new HashMap<>();
        map.put("showapi_appid", ToDayApi.APPKEY);
        map.put("showapi_sign", ToDayApi.APPSIGN);
        map.put("type", mCode);
        map.put("num", "20");
        map.put("page", String.valueOf(index));
        ToDayApi.getRetrofit().create(JuheApi.class).getMeizi(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.optInt("showapi_res_code") == 0) {
                                JSONObject object = jsonObject.getJSONObject("showapi_res_body");
                                for (int i = 0; i < 20; i++) {
                                    Meizi.ShowapiResBodyBean._$0Bean bean = new Meizi.ShowapiResBodyBean._$0Bean();
                                    bean.setTitle(object.getJSONObject("" + i).optString("title"));
                                    bean.setThumb(object.getJSONObject("" + i).optString("thumb"));
                                    bean.setUrl(object.getJSONObject("" + i).optString("url"));
                                    mData.add(bean);
                                }
                                //设置adapter
                                mAdapter.notifyDataSetChanged();
                            } else {
                                index = --index;
                            }
                        } catch (Exception e) {
                            index = --index;
                            Log.e(TAG, "onResponse: " + e.toString());
                        }
                        mSmartrefreshlayout.finishLoadmore();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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
