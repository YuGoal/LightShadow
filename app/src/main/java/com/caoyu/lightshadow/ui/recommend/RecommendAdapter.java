package com.caoyu.lightshadow.ui.recommend;

import android.caoyu.com.lightshadow.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caoyu.lightshadow.api.model.Recommend;

import java.util.List;

/**
 * 推荐适配器
 * Created by caoyu on 2017/8/12.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {
    private Context mContext;
    private List<Recommend.FeedListBean> mItem;
    private LayoutInflater mInflater;

    public RecommendAdapter(Context context, List<Recommend.FeedListBean> item) {
        this.mContext = context;
        this.mItem = item;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecommendViewHolder holder = new RecommendViewHolder(mInflater.inflate(R.layout.item_recommend, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        holder.textView.setText(mItem.get(position).getTitle());
        Glide.with(mContext)
                .load("http://image.wufazhuce.com/Fm8RsSyXPm4f1dpPTxDDaAp1R0ry")
                .placeholder(R.color.cardview_light_background) // can also be a drawable
//                        .error(R.drawable.ic_home_black_24dp) // will be displayed if the image cannot be loaded
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.title);
        }
    }
}
