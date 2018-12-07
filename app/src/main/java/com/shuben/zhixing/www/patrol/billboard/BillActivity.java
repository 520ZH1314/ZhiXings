package com.shuben.zhixing.www.patrol.billboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.shuben.zhixing.www.R;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends FragmentActivity
{
    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mDatas = new ArrayList<String>();
//	private List<String> mDatas = Arrays.asList("短信", "收藏", "推荐");

    private ViewPagerIndicator01 mIndicator;
    private TextView tx_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bill);

       /* mViewPager = (ViewPager) findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);*/
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
        mDatas.add("美丽工厂");
        mDatas.add("美丽现场");
        mDatas.add("人气榜");
        for (String data : mDatas)
        {
            /*VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
            mTabContents.add(fragment);*/
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
