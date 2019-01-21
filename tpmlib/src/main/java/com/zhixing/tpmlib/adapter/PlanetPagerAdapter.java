package com.zhixing.tpmlib.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PlanetPagerAdapter extends PagerAdapter {
    List<View> list;

    public PlanetPagerAdapter(List<View> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list!=null&&list.size()>0){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(list.get(position));
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }
    public void resetItem(ViewGroup container, int position, View view){
        container.removeView(list.get(position));
        container.addView(view, position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
