package com.shuben.zhixing.module.mass.adapter;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuben.zhixing.module.mass.bean.WxItem;
import com.shuben.zhixing.www.R;

import java.util.ArrayList;
import java.util.Map;

/**
 */

public class EnterMass3ChildAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity context;
    private ArrayList<WxItem> rbs;
    private Handler handler;
    public EnterMass3ChildAdapter(Activity context, ArrayList<WxItem> rbs, Handler handler){
        this.context = context;
        this.rbs = rbs;
        this.handler = handler;

        inflater = LayoutInflater.from(context);


    }
    public void updata(ArrayList<WxItem> rbs){
        this.rbs = rbs;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return rbs.size();
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
              TextView item0,item1,item2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null
                || convertView.getTag(R.mipmap.ic_launcher + position) == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.enter_mass3_child, null);

            convertView.setTag(R.mipmap.ic_launcher + position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.mipmap.ic_launcher
                    + position);
        }

        return  convertView;
    }
}
