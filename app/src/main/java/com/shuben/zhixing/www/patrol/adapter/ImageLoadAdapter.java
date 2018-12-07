package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.tu.loadingdialog.LoadingDailog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.util.Constant;

import java.util.List;

/**
 * Created by Geyan on 2018/5/27.
 */

public class ImageLoadAdapter extends BaseAdapter {
    private Context context;
    private View layout;
    private LayoutInflater layoutInflater;
    private int[] imgIcon;
    private List<String> img_url_list;
    private DisplayImageOptions options;
    private int screenWidth; //屏幕宽度
    private LoadingDailog dialog;
    private ImageLoader imageLoader;

    public ImageLoadAdapter(Context context, int screenWidth, List<String> img_url_list, ImageLoader imageLoader, DisplayImageOptions options, LoadingDailog dialog) {
        // TODO Auto-generated constructor stub
        this.img_url_list = img_url_list;
        this.context=context;
        this.dialog=dialog;
        this.screenWidth=screenWidth;
        this.imageLoader=imageLoader;
        this.options=options;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View inflate=null;
        if(convertView==null){
            inflate = layoutInflater.inflate(R.layout.item_image, null);

        }else{
            inflate=convertView;
        }

        ImageView img_item=(ImageView) inflate.findViewById(R.id.imageView);
        img_item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        int imageWidth = screenWidth / Constant.MAX_NUM_COLUMNS-30;
        int imageHeight = imageWidth*3/4;
        LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(imageWidth, imageHeight);
        img_item.setLayoutParams(lparam);
//        BaseApplication.initImageLoader(context);
        Log.e("%%%%%%%%%%","position:"+position);
       imageLoader.displayImage(img_url_list.get(position), img_item, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        dialog.show();
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        dialog.dismiss();
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current,
                                                 int total) {
                        //holder.progressBar.setProgress(Math.round(100.0f * current / total));
                    }
                }
        );

        return inflate;


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_url_list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
}