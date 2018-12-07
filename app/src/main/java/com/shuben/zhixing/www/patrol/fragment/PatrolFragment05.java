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
import com.shuben.zhixing.www.patrol.analysis.AnalysisFragment01;
import com.shuben.zhixing.www.patrol.analysis.AnalysisFragment02;
import com.shuben.zhixing.www.patrol.analysis.AnalysisFragment03;
import com.shuben.zhixing.www.patrol.billboard.ViewPagerIndicator02;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geyan on 2018/3/13.
 */

public class PatrolFragment05 extends Fragment {
    private View view_layout;
    private Context context;
    private List<Fragment> mTabContents ;
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mDatas ;
    private FragmentManager fragmentManager;

    private ViewPagerIndicator02 mIndicator;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_patrol05,container,false);
        context = getActivity();
      /*  Intent intent=new Intent();
        intent.setClass(context, AnalysisActivity.class);
        context.startActivity(intent);
        getActivity().finish();*/
        Log.e("***onCreateView***","onCreateView");
        initView();
        initDatas();
        //设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager,0);
        return view_layout;
    }


    private void initDatas()
    {
        mTabContents=new ArrayList<Fragment>();
        mDatas=new ArrayList<String>();
        mDatas.add("巡线状况");
        mDatas.add("月度报告");
        mDatas.add("个人报告");

        for (String data : mDatas)
        {
            if(data.equals("巡线状况")){
                AnalysisFragment01 fragmentBill01=new AnalysisFragment01();
                mTabContents.add(fragmentBill01);
            }else  if(data.equals("月度报告")){
                AnalysisFragment02 fragmentBill02=new AnalysisFragment02();
                mTabContents.add(fragmentBill02);
            }else  if(data.equals("个人报告")){
                AnalysisFragment03 fragmentBill03=new AnalysisFragment03();
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
        mViewPager = (ViewPager) view_layout.findViewById(R.id.id_vp05);
        mIndicator = (ViewPagerIndicator02) view_layout.findViewById(R.id.id_indicator05);
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