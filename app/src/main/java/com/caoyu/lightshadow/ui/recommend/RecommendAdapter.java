package com.caoyu.lightshadow.ui.recommend;

import android.caoyu.com.lightshadow.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
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
//    private SparseArray<Integer> heightArray;

    public RecommendAdapter(Context context, List<One.ResultsBean> item) {
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
//        if (heightArray.get(position) == null) {
//            Glide.with(mContext)
//                    .load(mItem.get(position).getUrl())
//                    .asBitmap()
//                    .placeholder(R.color.cardview_light_background) // can also be a drawable
//                    .error(R.color.cardview_dark_background) // will be displayed if the image cannot be loaded
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//
//                        @Override
//                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
//                            // Do something with bitmap here.
//                            int height = bitmap.getHeight(); //获取bitmap信息，可赋值给外部变量操作，也可在此时行操作。
//                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.imageView.getLayoutParams();
//                            layoutParams.height = height;
//                            holder.imageView.setLayoutParams(layoutParams);
//                            heightArray.put(position, height);
//                        }
//
//                    });
//        } else {
//            int height = heightArray.get(position);
//            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.imageView.getLayoutParams();
//            layoutParams.height = height;
//            holder.imageView.setLayoutParams(layoutParams);
//        }
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
                .load(mItem.get(position).getUrl())
                .asBitmap()
                .placeholder(R.color.cardview_light_background) // can also be a drawable
                .error(R.color.cardview_dark_background) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

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
