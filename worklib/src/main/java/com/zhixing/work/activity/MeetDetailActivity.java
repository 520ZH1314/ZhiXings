package com.zhixing.work.activity;

import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.BaseFragment;
import com.zhixing.work.R;
import com.zhixing.work.fragment.DepartmentReceiveFragment;
import com.zhixing.work.fragment.DepartmentSendFragment;
import com.zhixing.work.fragment.MyReceiveFragment;

import java.util.ArrayList;

public class MeetDetailActivity extends BaseActvity {

    private TabLayout mTablayout;
    private ViewPager mViewPage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_meet_detail;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
       initView();
    }

    private void initView() {
        mTablayout = (TabLayout) findViewById(R.id.tablayout_meet_detail);
        mViewPage = (ViewPager) findViewById(R.id.view_pager_meet_detail);
        ArrayList<String> titleDatas = new ArrayList<>();
        titleDatas.add("我发出的");
        titleDatas.add("部门发出的");
        ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new DepartmentReceiveFragment());
        fragmentList.add(new DepartmentSendFragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        mViewPage.setAdapter(myViewPageAdapter);
        mTablayout.setupWithViewPager(mViewPage);
    }

    public class MyViewPageAdapter extends FragmentPagerAdapter {
        private ArrayList<String> titleList;
        private ArrayList<BaseFragment> fragmentList;

        public MyViewPageAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<BaseFragment> fragmentList) {
            super(fm);
            this.titleList = titleList;
            this.fragmentList = fragmentList;
        }

        @Override
        public BaseFragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
