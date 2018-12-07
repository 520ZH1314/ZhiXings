package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuben.zhixing.www.R;

import java.util.List;

/**
 * Created by Geyan on 2018/5/28.
 */

public class PhotoAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<String> mlist;
    private ViewHolder holder;

    private LoadingDailog dialog;
    protected ImageLoader imageLoader;
    private DisplayImageOptions options;
    private int screenWidth = 0; //屏幕宽度

    public PhotoAdapter(Context mContext, List<String> list,LoadingDailog dialog,ImageLoader imageLoader,DisplayImageOptions options,int screenWidth) {
        this.mContext = mContext;
        this.mlist=list;
        this.dialog=dialog;
        this.imageLoader=imageLoader;
        this.options=options;
        this.screenWidth=screenWidth;
    }



    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.patrol_photo, null);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.imageView=(ImageView) convertView.findViewById(R.id.imageView);
            holder.title=(TextView) convertView.findViewById(R.id.tx_title);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText("合影"+(position+1));
//        BaseApplication.initImageLoader(mContext);
        imageLoader.displayImage(mlist.get(position),  holder.imageView, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        //holder.progressBar.setProgress(0);
                        //holder.progressBar.setVisibility(View.VISIBLE);
                        // bitmap_list.removeAll(bitmap_list);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {
                        //holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        //holder.progressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current,
                                                 int total) {
                        //holder.progressBar.setProgress(Math.round(100.0f * current / total));
                    }
                }
        );




        return convertView;
    }
    @Override
    //在此适配器中所代表的数据集中的条目数
    public int getCount() {
        return mlist.size();
    }

    @Override
    //获取数据集中与指定索引对应的数据项
    public Object getItem(int position) {
        return null;
    }

    @Override
    //获取在列表中与指定索引对应的行id
    public long getItemId(int position) {
        return position;
    }



    class ViewHolder
    {
        public ImageView imageView;
        public TextView title;

    }

}

