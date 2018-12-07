package com.shuben.contact.lib.common;

import android.widget.ImageView;

import com.base.zhixing.www.BaseApp;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shuben.contact.lib.R;

public class ImageLoader {
        public static void loadSpn(String path, ImageView v){
            Glide.with(BaseApp.application)
                    .load(path)
                    .crossFade()
                    .placeholder(R.color.black)
                    .into(v);
        }
    public static void print(String path, ImageView v){
        Glide.with(BaseApp.application).load("file:///"+path).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT) .placeholder(R.mipmap.ic_launcher)//默认显示图片
                .error(R.mipmap.ic_launcher)//图片加载错误显示的图片
                .into(v);

    }
        public static void load(String path, ImageView v){
            Glide.with(BaseApp.application).load(path).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT) .placeholder(R.mipmap.ic_launcher)//默认显示图片
                    .error(R.mipmap.ic_launcher)//图片加载错误显示的图片
                    .into(v);

        }
    public static void load(String path, ImageView v,int res){
        Glide.with(BaseApp.application).load(path).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT) .placeholder(res)//默认显示图片
                .error(res)//图片加载错误显示的图片
                .into(v);

    }


        public static void local(int res,ImageView v){
            Glide.with(BaseApp.application).load(res).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT) .placeholder(R.mipmap.ic_launcher)//默认显示图片
                    .error(R.mipmap.ic_launcher)//图片加载错误显示的图片
                    .into(v);
        }
    }
