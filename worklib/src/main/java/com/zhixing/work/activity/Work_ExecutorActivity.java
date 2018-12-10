package com.zhixing.work.activity;

import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.BaseFragment;
import com.zhixing.work.R;
import com.zhixing.work.fragment.DepartmentReceiveFragment;
import com.zhixing.work.fragment.ExecutprCompeteFragment;
import com.zhixing.work.fragment.MyReceiveFragment;
import com.zhixing.work.fragment.UnFinishFragment;

import java.util.ArrayList;

/**
 *
 *@author zjq
 *create at 2018/12/10 下午1:37
 * 执行人的list
 */
public class Work_ExecutorActivity extends BaseActvity {


    private ImageView mImage;
    private TextView mTvSend;
    private TextView mTvTitle;
    private TabLayout mTabExecutor;
    private ViewPager mViewPage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_work__executor;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        initView();
    }

    private void initView() {
        mImage=(ImageView)findViewById(R.id.iv_work_add_work);
        mTvSend=(TextView)findViewById(R.id.tv_work_send);
        mTvTitle=(TextView)findViewById(R.id.tv_work_title);
        mTvTitle.setText("执行人");
        mTvSend.setVisibility(View.GONE);
        mImage.setImageResource(R.drawable.task_left);
        mTabExecutor=(TabLayout) findViewById(R.id.tabLayout_executor);
        mViewPage=(ViewPager) findViewById(R.id.vp_tabLayout_executor);
        ArrayList<String> titleDatas = new ArrayList<>();
        titleDatas.add("未完成的");
        titleDatas.add("已完成的");
        ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new UnFinishFragment());
        fragmentList.add(new ExecutprCompeteFragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        mViewPage.setAdapter(myViewPageAdapter);
        mTabExecutor.setupWithViewPager(mViewPage);

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
