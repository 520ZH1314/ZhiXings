package com.base.zhixing.www.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.base.zhixing.www.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;



public class MyImageLoader {


    public static void loadSpn(Context context,String path, ImageView v) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.color.black);
        Glide.with(context)
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(v);
    }

    public static void print(Context context,String path, ImageView v) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context)
                .asBitmap().load("file:///" + path)
                .apply(options)
                .into(v);

    }

    public static void load(Context context,String path, ImageView v) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap. ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).asBitmap().load(path)
                .apply(options)
                .into(v);

    }



    public static void loads(Context context,String path, ImageView v) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_no_data)
                .error(R.mipmap.ic_no_data)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).asBitmap().load(path)
                .apply(options)
                .into(v);

    }
    public static void load(Context context,String path, ImageView v, int res) {
        RequestOptions options = new RequestOptions()
                .placeholder(res)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).asBitmap().load(path)
                .apply(options)
                .into(v);

    }


    public static void local(Context context,int res, ImageView v) {
        RequestOptions options = new RequestOptions()
                .placeholder(res)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).asBitmap().load(res)
                .apply(options)
                .into(v);
    }



    /**
     * @author zjq
     * create at 2018/12/26 上午9:45
     * 添加加载成功的回调
     */
    //注释掉，此处加载C代码速度过慢，使用的时候咨询一下  再优化
   /* public static void loadListeren(final Context context, String path, final ImageView v, final boolean b, final String name) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with( context).asBitmap().load(path)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (b) {
                            WatermarkText watermarkText = new WatermarkText(name)
                                    .setTextColor(Color.WHITE)
                                    .setPositionX(0.5)
                                    .setTextAlpha(200)
                                    .setTextSize(11f)
                                    .setPositionY(0.8);
                            //需要添加水印
                            WatermarkBuilder
                                     .create(context,resource)
                                     .loadWatermarkText(watermarkText)
                                     .getWatermark()
                                     .setToImageView(v);


                        } else {
                            v.setImageBitmap(resource);
                        }
                    }
                });
    }*/



    }
