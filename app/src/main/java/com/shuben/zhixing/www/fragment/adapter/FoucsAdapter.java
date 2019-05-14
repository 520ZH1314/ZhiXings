package com.shuben.zhixing.www.fragment.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.common.FileUtils;
import com.shuben.zhixing.www.common.ImageLoader;
import com.base.zhixing.www.util.DensityUtil;

import java.util.ArrayList;
import java.util.Map;

public class FoucsAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<Map<String,Integer>> items;
    public FoucsAdapter(Activity context,ArrayList<Map<String,Integer>> items){
        this.context =context;
        inflater = LayoutInflater.from(context);
        this.items = items;
   }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder {
        ImageView item0 ;
        TextView item1;
        RelativeLayout re_jiaoban;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null
                || convertView.getTag(R.mipmap.ic_launcher + position) == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment01_item, null);
            viewHolder.item0 = convertView.findViewById(R.id.item0);
            viewHolder.item1 = convertView.findViewById(R.id.item1);
            viewHolder.re_jiaoban = convertView.findViewById(R.id.re_jiaoban);
            int width = (DensityUtil.getWindowWidth(context)-FileUtils.dip2px(context,3))/4;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,width);
            viewHolder.re_jiaoban.setLayoutParams(params);
            convertView.setTag(R.mipmap.ic_launcher + position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.mipmap.ic_launcher
                    + position);
        }
        ImageLoader.local(items.get(position).get("img").intValue(),viewHolder.item0);
        viewHolder.item1.setText(context.getString(items.get(position).get("txt").intValue()));
        return  convertView;
    }
}
