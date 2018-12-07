package com.shuben.zhixing.www.patrol.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.billboard.FragmentBill01;
import com.shuben.zhixing.www.patrol.billboard.FragmentBill02;
import com.shuben.zhixing.www.patrol.billboard.FragmentBill03;
import com.shuben.zhixing.www.patrol.billboard.ViewPagerIndicator01;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geyan on 2018/3/12.
 */

public class PatrolFragment04 extends Fragment {
    private View view_layout;
    private Context context;
    private List<Fragment> mTabContents ;
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mDatas;
    private int index=0;
    private ViewPagerIndicator01 mIndicator;
    private FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_patrol04,container,false);
        context = getActivity();
        Log.e("***onCreateView04***","onCreateView");
        initView();
        initDatas();
        //设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager,index);
        return view_layout;
    }


    private void initDatas()
    {
        mTabContents=new ArrayList<Fragment>();
        mDatas=new ArrayList<String>();
        mDatas.add("美丽工厂");
        mDatas.add("美丽现场");
        mDatas.add("人气榜");
        mTabContents=new ArrayList<Fragment>();
        for (String data : mDatas)
        {

            if(data.equals("美丽工厂")){
                FragmentBill01 fragmentBill01=new FragmentBill01();
                mTabContents.add(fragmentBill01);
            }else  if(data.equals("美丽现场")){
                FragmentBill02 fragmentBill02=new FragmentBill02();
                mTabContents.add(fragmentBill02);
            }else  if(data.equals("人气榜")){
                FragmentBill03 fragmentBill03=new FragmentBill03();
                mTabContents.add(fragmentBill03);
            }


        }

        fragmentManager=getChildFragmentManager();
        mAdapter = new FragmentPagerAdapter(fragmentManager)
        {
            @Override
            public int getCount()
            {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position)
            {
                return mTabContents.get(position);
            }
        };
    }


    private void initView()
    {
        mViewPager = (ViewPager) view_layout.findViewById(R.id.id_vp04);
        mIndicator = (ViewPagerIndicator01) view_layout.findViewById(R.id.id_indicator04);
        mIndicator.setOnPageChangeListener(new ViewPagerIndicator01.PageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // Toast.makeText(context,"position:"+position,Toast.LENGTH_SHORT).show();
                mIndicator.setViewPager(mViewPager,position);
                //initDatas();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("**onStart**","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("**onResume**","onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("**onStop**","onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("**onPause**","onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("**onDestroy**","onDestroy");
        mTabContents=null;
        mAdapter=null;
        mViewPager=null;
        mDatas=null;
    }
}