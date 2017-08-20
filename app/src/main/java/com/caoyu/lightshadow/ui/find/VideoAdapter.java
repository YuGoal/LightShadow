package com.caoyu.lightshadow.ui.find;

import android.caoyu.com.lightshadow.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caoyu.lightshadow.api.model.One;

import java.util.List;

/**
 * Created by yugoal on 2017/8/20.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context mContext;
    private List<One.ResultsBean> mItem;
    private LayoutInflater mInflater;
//    private SparseArray<Integer> heightArray;

    public VideoAdapter(Context context, List<One.ResultsBean> item) {
        this.mContext = context;
        this.mItem = item;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        heightArray = new SparseArray<Integer>();
        VideoAdapter.VideoViewHolder view = new VideoAdapter.VideoViewHolder(mInflater.inflate(R.layout.item_video, parent, false));
        return view;
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.VideoViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;

        public VideoViewHolder(View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.image);
        }
    }
}
