package com.caoyu.lightshadow.ui.recommend;


import android.annotation.SuppressLint;
import android.caoyu.com.lightshadow.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.caoyu.lightshadow.ui.ImagePagerActivity;
import com.caoyu.lightshadow.view.ArrowDownloadButton;
import com.caoyu.lightshadow.view.PinchImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class PicFragment extends Fragment {
    private String mUrl;
    ArrowDownloadButton arrowDownloadButton;
    public PicFragment(String url) {
        this.mUrl = url;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic, container, false);
        PinchImageView piv = view.findViewById(R.id.pinch_image);
        arrowDownloadButton= view.findViewById(R.id.button);
        arrowDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrowDownloadButton.startAnimating();
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       OkHttpClient client = new OkHttpClient();
                       Request request = new Request.Builder()
                               .url(mUrl)
                               .build();
                       try {
                           Response response = client.newCall(request).execute();
                           if (response.isSuccessful()) {
                               final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                               saveImageToGallery(getActivity(),bitmap);
                               getActivity().runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       arrowDownloadButton.setProgress(100);
                                   }
                               });
                           }
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
               }).start();
            }
        });

        Glide.with(getActivity())
                .load(mUrl)
                .placeholder(R.color.cardview_dark_background) // can also be a drawable
                .error(R.color.cardview_dark_background) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(piv);
        return view;
    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Athena");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    class DownloadImageThread extends Thread {
        private String url;
        private ArrowDownloadButton mDownLoadButton;

        public DownloadImageThread(String url, ArrowDownloadButton downloadButton) {
            this.url = url;
            this.mDownLoadButton = downloadButton;
        }

        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    saveImageToGallery(getActivity(),bitmap);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDownLoadButton.setProgress(100);
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
