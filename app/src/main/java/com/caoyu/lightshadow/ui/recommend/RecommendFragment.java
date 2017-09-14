package com.caoyu.lightshadow.ui.recommend;

import android.annotation.SuppressLint;
import android.caoyu.com.lightshadow.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caoyu.lightshadow.api.Api;
import com.caoyu.lightshadow.api.JuheApi;
import com.caoyu.lightshadow.api.RecommendApi;
import com.caoyu.lightshadow.api.ToDayApi;
import com.caoyu.lightshadow.api.model.Meizi;
import com.caoyu.lightshadow.api.model.One;
import com.caoyu.lightshadow.util.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zyao89.view.zloading.ZLoadingDialog;

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
import static com.zyao89.view.zloading.Z_TYPE.LEAF_ROTATE;

/**
 * 推荐
 */
@SuppressLint("ValidFragment")
public class RecommendFragment extends Fragment implements OnRefreshLoadmoreListener {
    RecyclerView mRecycleview;
    SmartRefreshLayout mSmartrefreshlayout;
    private RecommendAdapter mAdapter;
    private List<Meizi.ShowapiResBodyBean._$0Bean> mData;
    ZLoadingDialog dialog;
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
        dialog = new ZLoadingDialog(getActivity());//动画
        dialog.setLoadingBuilder(LEAF_ROTATE)//设置类型
                .setLoadingColor(R.color.orange)//颜色
                .setHintText("Loading...")
                .show();
        mSmartrefreshlayout.setOnRefreshLoadmoreListener(this);

        mData = new ArrayList<>();
        //设置adapter
        mAdapter = new RecommendAdapter(getActivity(), mData);
        mRecycleview.setAdapter(mAdapter);
        initData();
        //布局管理
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecycleview.setLayoutManager(layoutManager);
        mRecycleview.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        mRecycleview.setHasFixedSize(false);

        return view;
    }

    private void initData() {
        index = 1;
        //获取列表
        Map<String, String> map = new HashMap<>();
        map.put("showapi_appid", ToDayApi.APPKEY);
        map.put("showapi_sign", ToDayApi.APPSIGN);
        map.put("type", mCode);
        map.put("num", "10");
        map.put("page", String.valueOf(index));
        ToDayApi.getRetrofit().create(JuheApi.class).getMeizi(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.optInt("showapi_res_code") == 0) {
                                mData.clear();
                                JSONObject object = jsonObject.getJSONObject("showapi_res_body");
                                for (int i = 0; i < 20; i++) {
                                    Meizi.ShowapiResBodyBean._$0Bean bean = new Meizi.ShowapiResBodyBean._$0Bean();
                                    bean.setThumb(object.getJSONObject(""+i).optString("thumb"));
                                    bean.setUrl(object.getJSONObject(""+i).optString("url"));
                                    bean.setTitle(object.getJSONObject(""+i).optString("title"));
                                    mData.add(bean);
                                }
                                mAdapter.notifyDataSetChanged();
                                mRecycleview.setAdapter(mAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        mSmartrefreshlayout.finishRefresh();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            if (jsonObject.optInt("showapi_res_code") == 0) {
                                JSONObject object = jsonObject.getJSONObject("showapi_res_body");
                                for (int i = 0; i < 9; i++) {
                                    Meizi.ShowapiResBodyBean._$0Bean bean = new Meizi.ShowapiResBodyBean._$0Bean();
                                    bean.setTitle(object.getJSONObject("" + i).optString("title"));
                                    bean.setThumb(object.getJSONObject("" + i).optString("thumb"));
                                    bean.setUrl(object.getJSONObject("" + i).optString("url"));
                                    mData.add(bean);
                                }
                            }
                            //设置adapter
                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
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
