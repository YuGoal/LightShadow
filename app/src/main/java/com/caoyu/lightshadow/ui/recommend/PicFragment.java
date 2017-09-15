package com.caoyu.lightshadow.ui.recommend;


import android.annotation.SuppressLint;
import android.caoyu.com.lightshadow.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caoyu.lightshadow.view.ArrowDownloadButton;
import com.caoyu.lightshadow.view.PinchImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class PicFragment extends Fragment {
    @BindView(R.id.pinch_image)
    PinchImageView pinchImage;
    @BindView(R.id.button)
    TextView button;
    Unbinder unbinder;
    private String mUrl;

    public PicFragment(String url) {
        this.mUrl = url;
    }

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getContext(),"保存成功",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic, container, false);
        unbinder = ButterKnife.bind(this, view);
        Glide.with(getActivity())
                .load(mUrl)
                .placeholder(R.color.cardview_dark_background) // can also be a drawable
                .error(R.color.cardview_dark_background) // will be displayed if the image cannot be loaded
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(pinchImage);
        return view;
    }

    public  void saveImageToGallery(Context context, Bitmap bmp) {
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
            mHandler.sendEmptyMessage(0);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.pinch_image, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pinch_image:
                getActivity().finish();
                break;
            case R.id.button:
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
                                saveImageToGallery(getActivity(), bitmap);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

}
