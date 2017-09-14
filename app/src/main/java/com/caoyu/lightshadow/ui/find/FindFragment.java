package com.caoyu.lightshadow.ui.find;

import android.caoyu.com.lightshadow.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caoyu.lightshadow.api.JuheApi;
import com.caoyu.lightshadow.api.ToDayApi;
import com.caoyu.lightshadow.api.model.Meizi;
import com.caoyu.lightshadow.api.model.Two;
import com.caoyu.lightshadow.util.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.caoyu.lightshadow.api.ToDayApi.APPKEY;
import static com.caoyu.lightshadow.api.ToDayApi.APPSIGN;
import static com.caoyu.lightshadow.util.CrashHandler.TAG;
import static com.zyao89.view.zloading.Z_TYPE.LEAF_ROTATE;

/**
 * 发现
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment implements OnRefreshListener {
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
        mSmartrefreshlayout.setOnRefreshListener(this);
        initData(1);
        //布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecycleview.setLayoutManager(layoutManager);
        mRecycleview.addItemDecoration(new GridSpacingItemDecoration(1, 10, true));
        mRecycleview.setHasFixedSize(false);
        return view;
    }

    private void initData(int page) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        initData(1);
    }

    /**
     * 获取当前时间标识码精确到毫秒
     */
    public static String getNowtimeKeyStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
        String nowdate = df.format(new Date());// new Date()为获取当前系统时间
        return nowdate;
    }

    public  static String getMonth(){
        return getNowtimeKeyStr().substring(4,6);
    }

    public  static String getDay(){
        return getNowtimeKeyStr().substring(6,8);
    }
}
