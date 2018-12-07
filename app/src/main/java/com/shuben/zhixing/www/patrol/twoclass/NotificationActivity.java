package com.shuben.zhixing.www.patrol.twoclass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.billboard.ViewPagerIndicator01;

import java.util.ArrayList;
import java.util.List;

//巡线通报
public class NotificationActivity extends FragmentActivity
{
    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mDatas = new ArrayList<String>();
    private ViewPagerIndicator01 mIndicator;
    private TextView tx_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notification2);
        initView();
        initDatas();
        //设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager,getIntent().getIntExtra("index",0));

    }

    private void initDatas()
    {
        if(getIntent().getStringExtra("class").equals("two")){
            mDatas.add("过程复盘");
            mDatas.add("巡线结果");
            mDatas.add("亮点点评");
            for (String data : mDatas)
            {
            /*VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
            mTabContents.add(fragment);*/
                if(data.equals("过程复盘")){
                    FragmentNotice01 fragmentNotice01=new FragmentNotice01();
                    mTabContents.add(fragmentNotice01);
                }else  if(data.equals("巡线结果")){
                    FragmentNotice02 fragmentNotice02=new FragmentNotice02();
                    mTabContents.add(fragmentNotice02);
                }else  if(data.equals("亮点点评")){
                    FragmentNotice03 fragmentNotice03=new FragmentNotice03(getIntent().getStringExtra("class"));
                    mTabContents.add(fragmentNotice03);
                }
        }


        }else{
            mDatas.add("过程复盘");
            mDatas.add("亮点点评");
            for (String data : mDatas)
            {
            /*VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
            mTabContents.add(fragment);*/
                if(data.equals("过程复盘")){
                    FragmentNotice01 fragmentNotice01=new FragmentNotice01();
                    mTabContents.add(fragmentNotice01);
                }else  if(data.equals("亮点点评")){
                    FragmentNotice03 fragmentNotice03=new FragmentNotice03(getIntent().getStringExtra("class"));
                    mTabContents.add(fragmentNotice03);
                }
            }

        }



        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
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
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator01) findViewById(R.id.id_indicator);
        tx_back= (TextView) findViewById(R.id.tx_back);
        tx_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
