package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shuben.zhixing.www.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geyan on 2018/5/31.
 */

public class ImageGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String[] fileNames;
    private String[] urls;
    private List files=new ArrayList();
    private List urlss=new ArrayList();
    private String url;
    private int mScreenWidth;
    private int mScreenHeight;
    Boolean mIsGridViewIdle;
    private List<String> icons;
    private ImageLoader imageLoader;


    public ImageGridAdapter(Context context, List<String> icons, ImageLoader imageLoader,int mScreenWidth, int mScreenHeight) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.icons=icons;
        this.inflater=LayoutInflater.from(context);
        this.mScreenHeight=mScreenHeight;
        this.mScreenWidth=mScreenWidth;
        this.imageLoader=imageLoader;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return icons.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View concertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder=null;
        if(concertView==null){
            concertView=inflater.inflate(R.layout.item_image, parent, false);
            holder=new ViewHolder();
            holder.imageView=(ImageView)concertView.findViewById(R.id.imageView);
            concertView.setTag(holder);

        }else {
            holder=(ViewHolder) concertView.getTag();
        }
        url=icons.get(position);

        //首先得到要设置的GridView
        GridView gv=(GridView) parent;
        //获得每个Item之间的间隔
        int horizontalSpacing=gv.getHorizontalSpacing();
        //获得总共的列数
        int numColums=gv.getNumColumns();
        //每个Item的宽度
        int itemWith=(gv.getWidth()-(numColums-1)*horizontalSpacing-gv.getPaddingLeft()-gv.getPaddingRight())/numColums;

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(itemWith, itemWith);
        holder.imageView.setLayoutParams(lp);
        holder.imageView.setTag(url);
        if(holder.imageView.getTag()!=null&&holder.imageView.getTag().equals(url)){
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage(url, holder.imageView);


        }
        //  bmobFile.loadImageThumbnail(context, holder.imageView, 30, 30);
        return concertView;
    }
    public class ViewHolder{
        private ImageView imageView;
    }
}
