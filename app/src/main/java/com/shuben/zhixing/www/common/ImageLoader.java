package com.shuben.zhixing.www.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.shuben.zhixing.www.BaseApplication;
import com.shuben.zhixing.www.R;

public class ImageLoader {
    public static void loadSpn(String path, ImageView v) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.color.black);
        Glide.with(BaseApplication.application)
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(v);
    }

    public static void print(String path, ImageView v) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(BaseApplication.application)
                .asBitmap().load("file:///" + path)
                .apply(options)
                .into(v);

    }

    public static void load(String path, ImageView v) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(BaseApplication.application).asBitmap().load(path)
                .apply(options)
                .into(v);

    }

    public static void load(String path, ImageView v, int res) {
        RequestOptions options = new RequestOptions()
                .placeholder(res)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(BaseApplication.application).asBitmap().load(path)
                .apply(options)
                .into(v);

    }


    public static void local(int res, ImageView v) {
        RequestOptions options = new RequestOptions()
                .placeholder(res)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(BaseApplication.application).asBitmap().load(res)
                .apply(options)
                .into(v);
    }



    }


