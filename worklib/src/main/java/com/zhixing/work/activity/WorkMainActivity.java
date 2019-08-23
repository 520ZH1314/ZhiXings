package com.zhixing.work.activity;

import android.content.Intent;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.BaseFragment;
import com.zhixing.work.R;
import com.zhixing.work.ui.AddWorkDialog;
import java.util.ArrayList;
/**
 * 工作模块的main界面
 *
 * @author zjq
 * create at 2018/11/28 下午2:56
 */
@Route(path = "/worklib/WorkMainActivity")
public class WorkMainActivity extends BaseActvity implements View.OnClickListener {

    private TabLayout mTablayout;
    private ViewPager mViewPage;
    private ImageView mImageView;
    private TextView mTvSend;
    private AddWorkDialog workDialog;
    private TextView mTvWorkMainMeet;
    private TextView mTvWorkMainTask;

    @Override
    public int getLayoutId() {
        return R.layout.activity_work_main;

    }
    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        //初始化View
        initView();

    }

    private void initView() {
//        mTablayout = (TabLayout) findViewById(R.id.tablayout_work_main);
//        mViewPage = (ViewPager) findViewById(R.id.vp_work_mian);
        mImageView=(ImageView) findViewById(R.id.iv_work_add_work);
        mTvSend=(TextView) findViewById(R.id.tv_work_send);
        mTvWorkMainMeet=(TextView) findViewById(R.id.tv_work_main_meet);//点击到会议列表的
        mTvWorkMainTask=findViewById(R.id.tv_work_main_task);
        mTvSend.setVisibility(View.GONE);
        mImageView.setOnClickListener(this);
        mImageView.setImageResource(R.drawable.add_more);
        mTvWorkMainMeet.setOnClickListener(this);
        mTvWorkMainTask.setOnClickListener(this);
//        ArrayList<String> titleDatas = new ArrayList<>();
//        titleDatas.add("我发出的");
//        titleDatas.add("我收到的");
//        ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
//        fragmentList.add(new DepartmentSendFragment());
//        fragmentList.add(new DepartmentReceiveFragment());
//        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
//        mViewPage.setAdapter(myViewPageAdapter);
//        mTablayout.setupWithViewPager(mViewPage);
    }
    /**
     *点击事件
     *@author zjq
     *create at 2018/11/29 下午1:36
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_work_add_work) {
             workDialog=new AddWorkDialog();
            workDialog.show(getSupportFragmentManager(),"AddWorkDialog");
        }else if(i==R.id.tv_work_main_meet){
            Intent intent=new Intent(this,MeetListActivity.class);
            startActivity(intent);


        } else if(i==R.id.tv_work_main_task){
            Intent intent=new Intent(this,TaskListActivity.class);
            startActivity(intent);

        }
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
