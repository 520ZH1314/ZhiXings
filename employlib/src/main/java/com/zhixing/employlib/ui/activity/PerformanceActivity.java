package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.example.stateviewlibrary.Shimmer;
import com.example.stateviewlibrary.StateView;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.MyAdapter;
import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.ui.fragment.GardenPlotFragment;
import com.zhixing.employlib.ui.fragment.PerformanceMineFragment;
import com.zhixing.employlib.ui.fragment.PersonolPerformanceFragment;
import com.zhixing.employlib.ui.fragment.RecruitFragment;
import com.zhixing.employlib.view.CustomScrollViewPager;
import com.zhixing.employlib.viewmodel.activity.PerformanceMainViewModel;
import java.util.ArrayList;
import java.util.List;
import android.support.annotation.Nullable;

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
    private StateView mStateView;
    @Autowired (name ="permission")
    String permissions;
    @Autowired(name ="test")
    Bundle buddle;
    @Override
    public int getLayoutId() {
        return R.layout.activity_person_performance;


    }

    @Override
    public void process(Message msg) {

    }

    /**
     * 获得模块权限
     * parent 来自于activity传递的permission
     * //map 替换成buddle  提高一定的效率
     */
    private Bundle getModuleQxs(String parent){
        Uri uri = Uri.parse("content://com.zhixing.provider/permission/"+parent);//这么使用
        //在插入之前先清空表数据
        Cursor cursor = getContentResolver().query(uri,null,null,new String[]{parent},null);
        P.c("是否为空"+cursor.getCount());

        Bundle bundle =new Bundle();
        if( cursor.moveToFirst()){
            do{
                String key = cursor.getString(cursor.getColumnIndex("permissionCode"));
                P.c(key+"子模块");
                bundle.putString(key,"");
            }while (cursor.moveToNext());

        }
        return bundle;
    }

    RelativeLayout relativeLayout;
    @Override
    public void initLayout() {
        ARouter.getInstance().inject(this);
        P.c(buddle.size()+"开始。。。"+permissions);

//        P.c(permissions.maps.size()+"~~223");




        performanceMainViewModel = ViewModelProviders.of(this).get(PerformanceMainViewModel.class);




        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.ll_performance);
        mStateView=StateView.inject(linearLayout);
        sharedUtils = new SharedUtils(PerformanceApi.FLIESNAME);

      //  if (sharedUtils.getBooleanValue(PerformanceApi.ISFITIST) == null || !sharedUtils.getBooleanValue(PerformanceApi.ISFITIST)) {


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

              goNext();
          /*  Observable.timer(5, TimeUnit.SECONDS)
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



                        }
                    });
*/
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

       /* } else {
            relativeLayout.setVisibility(View.GONE);
            relativeLayout1.setVisibility(View.VISIBLE);
            goNext();
        }*/


    }

    private void goNext() {
        setStatus(-1);
        setDefaultFragment();//设置默认导航栏
        mStateView.showLoading();
        //获取个人班组信息以及权限

        Bundle moduleQxs = getModuleQxs(permissions);
        if (moduleQxs.containsKey("jxpf")){
            sharedUtils.setBooleanValue(PerformanceApi.ISTEAMLEADER, true);
        }
        performanceMainViewModel.getData(true);
        performanceMainViewModel.personTeamInfo.observe(this, new Observer<DBaseResponse<PersonTeamBean>>() {
            @Override
            public void onChanged(@Nullable DBaseResponse<PersonTeamBean> personTeamBeanDBaseResponse) {
                    if (personTeamBeanDBaseResponse.getRows()!=null){
                        if (personTeamBeanDBaseResponse.getRows().size()!=0){
                            mStateView.showContent();
                            for (PersonTeamBean bean :  personTeamBeanDBaseResponse.getRows()) {
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
                        }else if ("404".equals(personTeamBeanDBaseResponse.getStatus())){
                            mStateView.showRetry();
                            mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                                @Override
                                public void onRetryClick() {
                                    performanceMainViewModel.getData(true);
                                }
                            });
                        }else{
                            mStateView.showRetry();
                            mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                                @Override
                                public void onRetryClick() {
                                    performanceMainViewModel.getData(true);
                                }
                            });
                        }
                    }else if ("404".equals(personTeamBeanDBaseResponse.getStatus())){
                        mStateView.showRetry();
                        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                            @Override
                            public void onRetryClick() {
                                performanceMainViewModel.getData(true);
                            }
                        });
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
