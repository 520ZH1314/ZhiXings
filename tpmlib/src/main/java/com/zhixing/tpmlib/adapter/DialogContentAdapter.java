package com.zhixing.tpmlib.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhixing.tpmlib.R;

import java.util.List;

public class DialogContentAdapter extends BaseAdapter{
        private List<String> contentList;// 内容列表
        private List<String> tittleList;// 标题列表
        ViewHoler viewHoler;

    public DialogContentAdapter(List<String> tittleList,List<String> contentList) {
        super();
        this.tittleList=tittleList;
        this.contentList = contentList;
    }

        class ViewHoler {
            TextView tvContent;
            TextView tvTitle;
        }

        @Override
        public int getCount() {
        return tittleList.size();
    }

        @Override
        public Object getItem(int position) {
        return tittleList.get(position);
    }

        @Override
        public long getItemId(int position) {
        return position;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planet_param, parent, false);
            viewHoler = new ViewHoler();
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        viewHoler.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
        viewHoler.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHoler.tvTitle.setText(tittleList.get(position).toString());
        viewHoler.tvContent.setText(contentList.get(position).toString());


        return convertView;
    }


}
