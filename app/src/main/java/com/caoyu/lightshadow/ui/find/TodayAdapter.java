package com.caoyu.lightshadow.ui.find;

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
import com.caoyu.lightshadow.api.model.Two;

import java.util.List;

/**
 * Created by yugoal on 2017/8/20.
 */

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.VideoViewHolder> {
    private Context mContext;
    private List<Two.ResultBean> mItem;
    private LayoutInflater mInflater;

    public TodayAdapter(Context context, List<Two.ResultBean> item) {
        this.mContext = context;
        this.mItem = item;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public TodayAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        heightArray = new SparseArray<Integer>();
        TodayAdapter.VideoViewHolder view = new TodayAdapter.VideoViewHolder(mInflater.inflate(R.layout.item_today, parent, false));
        return view;
    }

    @Override
    public void onBindViewHolder(final TodayAdapter.VideoViewHolder holder, final int position) {
        holder.mTv_title.setText(mItem.get(position).getTitle());
        holder.mTv_time.setText(mItem.get(position).getYear()+"-"+mItem.get(position).getMonth()+"-"+mItem.get(position).getDay());
        holder.mTv_des.setText(mItem.get(position).getDes());
        holder.mTv_lunar.setText(mItem.get(position).getLunar());
        Glide.with(mContext)
                .load(mItem.get(position).getPic())
                .asBitmap()
                .placeholder(R.color.cardview_light_background) // can also be a drawable
                .error(R.color.cardview_dark_background) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImg_pic);
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView mImg_pic;
        TextView mTv_title;
        TextView mTv_time;
        TextView mTv_lunar;
        TextView mTv_des;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mImg_pic = itemView.findViewById(R.id.img_pic);
            mTv_des = itemView.findViewById(R.id.tv_des);
            mTv_title = itemView.findViewById(R.id.tv_title);
            mTv_time = itemView.findViewById(R.id.tv_time);
            mTv_lunar = itemView.findViewById(R.id.tv_lunar);
        }
    }
}
