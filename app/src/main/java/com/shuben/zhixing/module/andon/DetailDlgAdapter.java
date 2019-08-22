package com.shuben.zhixing.module.andon;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.util.MyImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuben.zhixing.www.R;

import java.util.ArrayList;

public class DetailDlgAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ImageBean> rbs;
    private int HEIGHT ;
    private Handler handler;

    public DetailDlgAdapter(Context context, ArrayList<ImageBean> rbs, int HEIGHT,Handler handler ){
        this.context = context;
        this.rbs = rbs;
        this.HEIGHT = HEIGHT;
        this.handler = handler;
        inflater = LayoutInflater.from(context);
    }
    public void updata(ArrayList<ImageBean> rbs){
        this.rbs = rbs;
        notifyDataSetChanged();
    }
    int pop = 0;//是1的时候是上传
    public void setPop(int pop){
        this.pop = pop;
    }
    @Override
    public int getCount() {
        return pop==0?rbs.size():rbs.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return rbs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        ImageView item0;
        TextView txt;
        ConstraintLayout layout;
    }
    private boolean show =false;
    public void change(boolean show){
        this.show = show;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null
                || convertView.getTag(R.mipmap.ic_launcher + position) == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.upload_image_item, null);
            viewHolder.layout = convertView.findViewById(R.id.layout);
            viewHolder.item0 =  convertView.findViewById(R.id.item0);
            viewHolder.txt = convertView.findViewById(R.id.item1);
            convertView.setTag(R.mipmap.ic_launcher + position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.mipmap.ic_launcher
                    + position);
        }
        viewHolder.layout.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,HEIGHT));
        if(position==0&&pop==1){
//            viewHolder.item0.setImageResource(R.mipmap.btn_add);
//            viewHolder.item0.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoader.getInstance().displayImage("drawable://"+R.mipmap.btn_add,viewHolder.item0);
            viewHolder.txt.setVisibility(View.GONE);
            viewHolder.item0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  handler.sendEmptyMessage(3);
                }
            });
        }else{
            if(pop==1){
                viewHolder.txt.setVisibility(View.VISIBLE);
            }else{
                viewHolder.txt.setVisibility(View.GONE);
            }

            final ImageBean it = rbs.get(pop==0?position:position-1);
            if(it.getType()==1){//网络图片
                MyImageLoader.load(context,it.getPath(),viewHolder.item0);

//                ImageLoader.getInstance().displayImage(it.getPath(),viewHolder.item0);
            }else{
                MyImageLoader.load(context,"file://"+it.getPath(),viewHolder.item0);
//                ImageLoader.getInstance().displayImage("file://"+it.getPath(),viewHolder.item0);
            }
            viewHolder.txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message ms = new Message();
                    ms.what = 2;
                    ms.arg1 = position;
                    handler.sendMessage(ms);
                }
            });
            /*viewHolder.txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            viewHolder.item0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/
        }

        return  convertView;
    }
}
