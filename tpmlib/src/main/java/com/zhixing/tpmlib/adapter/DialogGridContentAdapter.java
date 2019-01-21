package com.zhixing.tpmlib.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.AnomalousBean;

import java.util.List;

public class DialogGridContentAdapter extends BaseAdapter{
        private List<AnomalousBean> anomalousBeanList;// 内容列表
        ViewHoler viewHoler;
    private int selectorPosition;
    public DialogGridContentAdapter(List<AnomalousBean> anomalousBeanList) {
        super();
        this.anomalousBeanList = anomalousBeanList;
    }
        class ViewHoler {
            TextView tvContent;
        }

        @Override
        public int getCount() {
        return anomalousBeanList.size();
    }

        @Override
        public Object getItem(int position) {
        return anomalousBeanList.get(position);
    }

        @Override
        public long getItemId(int position) {
        return position;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planet_ng_param, parent, false);
            viewHoler = new ViewHoler();
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        viewHoler.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            if (selectorPosition == position) {
                viewHoler.tvContent.setBackgroundResource(R.drawable.btn_blue_strore);
                viewHoler.tvContent.setTextColor(Color.parseColor("#3F51B5"));
            } else {
                //其他的恢复原来的状态
                viewHoler.tvContent.setBackgroundResource(R.drawable.btn_blue_strore);
                viewHoler.tvContent.setTextColor(Color.parseColor("#666666"));
            }
        viewHoler.tvContent.setText(anomalousBeanList.get(position).getExceptionGroupName());
        return convertView;
    }

    public void changeState(int pos) {
        selectorPosition = pos;
        notifyDataSetChanged();

    }
}
