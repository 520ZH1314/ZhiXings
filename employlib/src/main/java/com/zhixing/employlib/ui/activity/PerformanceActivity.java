package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.text.TextPaint;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.view.Toasty;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.MyAdapter;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.ui.fragment.GardenPlotFragment;
import com.zhixing.employlib.ui.fragment.PerformanceMineFragment;
import com.zhixing.employlib.ui.fragment.PersonolPerformanceFragment;
import com.zhixing.employlib.ui.fragment.RecruitFragment;
import com.zhixing.employlib.view.Animation;
import com.zhixing.employlib.view.AnimationTextView;
import com.zhixing.employlib.view.CustomScrollViewPager;
import com.zhixing.employlib.view.Shimmer;
import com.zhixing.employlib.view.ShimmerTextView;
import com.zhixing.employlib.viewmodel.activity.PerformanceMainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;

@Route(path = "/employlib/PerformanceActivity")
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
    private PerformanceMainViewModel performanceMainViewModel;
    private SharedUtils sharedUtils;
    private Shimmer shimmer;
    private int positions;

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_performance;


    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        performanceMainViewModel = ViewModelProviders.of(this).get(PerformanceMainViewModel.class);
        RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.rl_performance);
        RelativeLayout relativeLayout1=(RelativeLayout) findViewById(R.id.rl_performance1);
        ShimmerTextView animationTextView= (ShimmerTextView) findViewById(R.id.tv_ani);
        TextPaint tp = animationTextView.getPaint();
        tp.setFakeBoldText(true);
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.ll_performance);
        sharedUtils = new SharedUtils(PerformanceApi.FLIESNAME);

        if (sharedUtils.getBooleanValue(PerformanceApi.ISFITIST) == null || !sharedUtils.getBooleanValue(PerformanceApi.ISFITIST)) {
            relativeLayout.setVisibility(View.VISIBLE);
            relativeLayout1.setVisibility(View.GONE);
            shimmer = new Shimmer();
            shimmer.start(animationTextView);


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

            Observable.timer(5, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new io.reactivex.Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Long aLong) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            shimmer.cancel();
                            relativeLayout.setVisibility(View.GONE);
                            relativeLayout1.setVisibility(View.VISIBLE);
                            goNext();
                        }
                    });

//            new Thread(){
//                public void run(){
//                    try {
//                        sleep(3000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                }
//            }.start();

        } else {
            relativeLayout.setVisibility(View.GONE);
            relativeLayout1.setVisibility(View.VISIBLE);
            goNext();
        }


    }

    private void goNext() {
        setStatus(-1);
        setDefaultFragment();//设置默认导航栏
        showDialog("");
        //获取个人班组信息以及权限
        performanceMainViewModel.getTeamBeans();
        performanceMainViewModel.TeamBeans.observe(this, new Observer<List<PersonTeamBean>>() {
            @Override
            public void onChanged(@Nullable List<PersonTeamBean> personTeamBeans) {
                if (personTeamBeans != null) {
                    for (PersonTeamBean bean : personTeamBeans) {
                        sharedUtils.setBooleanValue(PerformanceApi.ISTEAMLEADER, bean.isIsTeamLeader());
                        sharedUtils.setStringValue(PerformanceApi.TEAMID, bean.getTeamId());
                        sharedUtils.setStringValue(PerformanceApi.TEAMLEADERUSERID, bean.getTeamLeaderUserId());
                        sharedUtils.setStringValue(PerformanceApi.TEAMNAME, bean.getTeamName());
                    }
                    list = new ArrayList<>();
                    list.add(new PersonolPerformanceFragment());
                    list.add(new GardenPlotFragment());
                    list.add(new RecruitFragment());
                    list.add(new PerformanceMineFragment());
                    adapter = new MyAdapter(getSupportFragmentManager(), list, titles);

                    viewPager.setAdapter(adapter);
                    dismissDialog();

                } else {
                    dismissDialog();
                    Toasty.INSTANCE.showToast(PerformanceActivity.this, "请求失败");

                }
            }
        });
    }

    private void setDefaultFragment() {
        viewPager.setCurrentItem(positions);
    }


    @Override
    public void onTabSelected(int position) {
         positions=position;
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
