package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.shuben.zhixing.www.R;

import java.util.ArrayList;

/**.
 * Created by Geyan on 2018/4/13.
 */

public class ImageAdapter extends BaseAdapter {
    private ArrayList<String> listUrls;
    private LayoutInflater inflater;
    private Context mContext;
    public ImageAdapter(Context context,ArrayList<String> listUrls) {
        this.listUrls = listUrls;
        this.mContext=context;
        if(listUrls.size() == 7){
            listUrls.remove(listUrls.size()-1);
        }
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount(){
        return  listUrls.size();
    }
    @Override
    public String getItem(int position) {
        return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_image, parent,false);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final String path=listUrls.get(position);
        RequestOptions options = new RequestOptions()
                .centerCrop();
        com.bumptech.glide.Glide.with(mContext)
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                 .apply(options)
                .into(holder.image);

        return convertView;
    }
    class ViewHolder {
        ImageView image;
    }
}

