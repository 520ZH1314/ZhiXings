package com.zhixing.employlib.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.base.zhixing.www.BaseFragment;

import java.util.List;

public class MyAdapter extends FragmentPagerAdapter {

    private  String[] titles;
    private List<BaseFragment> list;
    public MyAdapter(FragmentManager fm,List<BaseFragment> list,String[] titles) {
        super(fm);
        this.list=list;
        this.titles=titles;
    }
    //获得每个页面的下标
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
    //获得List的大小
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
