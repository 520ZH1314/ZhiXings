package com.zhixing.employlib.ui.activity;

import android.graphics.Color;
import android.os.Message;
import android.support.annotation.NonNull;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.BaseFragment;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.MyAdapter;
import com.zhixing.employlib.ui.fragment.BetterTeamEmployeeFragment;
import com.zhixing.employlib.ui.fragment.DeliveredFragment;
import com.zhixing.employlib.ui.fragment.ExcellentEmployeeFragment;
import com.zhixing.employlib.ui.fragment.GardenPlotFragment;
import com.zhixing.employlib.ui.fragment.PersonRankFragment;
import com.zhixing.employlib.ui.fragment.PersonUporDownFragment;
import com.zhixing.employlib.ui.fragment.PersonolPerformanceFragment;
import com.zhixing.employlib.ui.fragment.RecruitFragment;
import com.zhixing.employlib.view.BottomNavigationViewHelper;
import com.zhixing.employlib.view.CustomScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class PerformanceActivity extends BaseActvity implements BottomNavigationBar.OnTabSelectedListener {
    int lastSelectedPosition = 0;
    private TabLayout tabLayout;
    private CustomScrollViewPager viewPager;
    //写一个List集合，把每个页面，也就是Fragment,存进去
    private List<BaseFragment> list;
    private MyAdapter adapter;
    private String[] titles = {"绩效", "园地", "招聘", "我的"};
    private MenuItem menuItem;
    private BottomNavigationBar bottomNavigationBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_performance;


    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        viewPager = (CustomScrollViewPager) findViewById(R.id.viewpager_persion);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);


        bottomNavigationBar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setActiveColor("#2082D0") //选中颜色
                .setInActiveColor("#BABABA") //未选中颜色
                .setBarBackgroundColor("#FFFFFF");//导航栏背景色
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.performance, "绩效"))
                .addItem(new BottomNavigationItem(R.mipmap.team, "园地"))
                .addItem(new BottomNavigationItem(R.mipmap.boss, "招聘"))
                .addItem(new BottomNavigationItem(R.mipmap.mine, "我的"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise(); //initialise 一定要放在 所有设置的最后一项

        setDefaultFragment();//设置默认导航栏

        list = new ArrayList<>();
        list.add(new PersonolPerformanceFragment());
        list.add(new GardenPlotFragment());
        list.add(new RecruitFragment());
        list.add(new PersonolPerformanceFragment());
        adapter = new MyAdapter(getSupportFragmentManager(), list, titles);

        viewPager.setAdapter(adapter);

    }

    private void setDefaultFragment() {
        viewPager.setCurrentItem(0);
    }


    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
            case 3:
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }


    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
