package com.caoyu.lightshadow.ui.recommend;

import android.caoyu.com.lightshadow.R;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caoyu.lightshadow.api.model.One;

import java.util.List;

/**
 * 推荐适配器
 * Created by caoyu on 2017/8/12.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {
    private Context mContext;
    private List<One.ResultsBean> mItem;
    private LayoutInflater mInflater;

    public RecommendAdapter(Context context, List<One.ResultsBean> item) {
        this.mContext = context;
        this.mItem = item;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendViewHolder(
                MaterialRippleLayout.on(mInflater.inflate(R.layout.item_recommend, parent, false))
                        .rippleOverlay(true)
                        .rippleAlpha(0.5f)
                        .rippleDuration(1)
                        .rippleColor(mContext.getResources().getColor(R.color.primary_dark))
                        .rippleHover(true)
                        .create()
        );
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        holder.mTitle.setText(mItem.get(position).getCreatedAt());
        holder.mContent.setText(mItem.get(position).getSource());
        holder.mForword.setText(mItem.get(position).getWho());
        Glide.with(mContext)
                .load(mItem.get(position).getUrl())
                .placeholder(R.color.cardview_dark_background) // can also be a drawable
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
        TextView mTitle;
        TextView mContent;
        TextView mForword;
        LinearLayout linearLayout;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            mTitle = itemView.findViewById(R.id.tv_title);
            mContent = itemView.findViewById(R.id.tv_content);
            mForword = itemView.findViewById(R.id.tv_forward);
            linearLayout = itemView.findViewById(R.id.line);
        }
    }
}
