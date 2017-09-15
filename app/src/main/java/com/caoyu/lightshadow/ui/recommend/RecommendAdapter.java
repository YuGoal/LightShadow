package com.caoyu.lightshadow.ui.recommend;

import android.caoyu.com.lightshadow.R;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caoyu.lightshadow.api.model.Meizi;
import com.caoyu.lightshadow.ui.ImagePagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐适配器
 * Created by caoyu on 2017/8/12.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {
    private Context mContext;
    private List<Meizi.ShowapiResBodyBean._$0Bean> mItem;
    private LayoutInflater mInflater;
//    private SparseArray<Integer> heightArray;

    public RecommendAdapter(Context context, List<Meizi.ShowapiResBodyBean._$0Bean> item) {
        this.mContext = context;
        this.mItem = item;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        heightArray = new SparseArray<Integer>();
        RecommendViewHolder view = new RecommendViewHolder(mInflater.inflate(R.layout.item_recommend, parent, false));
        return view;
    }

    @Override
    public void onBindViewHolder(final RecommendViewHolder holder, final int position) {
        //view布局参数
        ViewGroup.LayoutParams para;
        para = holder.imageView.getLayoutParams();
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();//获取屏幕宽度
        int height = wm.getDefaultDisplay().getHeight();//获取屏幕高度
        para.height = height / 3;
        para.width = width / 2;
        holder.imageView.setLayoutParams(para);
        Glide.with(mContext)
                .load(mItem.get(position).getThumb())
                .asBitmap()
                .placeholder(R.color.white) // can also be a drawable
                .error(R.color.primary_dark) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> info = new ArrayList<String>();
                for (Meizi.ShowapiResBodyBean._$0Bean i :mItem){
                    info.add(i.getThumb());
                }
                Intent intent = new Intent(mContext, ImagePagerActivity.class);
                intent.putStringArrayListExtra("infoList", info);
                intent.putExtra("index", position);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
