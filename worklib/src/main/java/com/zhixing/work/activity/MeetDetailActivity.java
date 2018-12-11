package com.zhixing.work.activity;


import android.content.Intent;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.zhixing.work.R;
import com.zhixing.work.bean.CopyPeopleBean;
import com.zhixing.work.bean.PostMeetDetailJson;
import com.zhixing.work.bean.ResponseMeetDetailEntity;
import com.zhixing.work.fragment.DepartmentReceiveFragment;
import com.zhixing.work.fragment.DepartmentSendFragment;
import com.zhixing.work.fragment.MyReceiveFragment;
import com.zhixing.work.http.base.BaseSubscriber;
import com.zhixing.work.http.base.MyBaseSubscriber;
import com.zhixing.work.http.base.ResponseThrowable;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class MeetDetailActivity extends BaseActvity implements View.OnClickListener {

    private TabLayout mTablayout;
    private ViewPager mViewPage;
    private TextView mTvContent;
    private TextView mTvOpenTime;
    private Button mButton;
    private ConstraintLayout mCon1;
    private ConstraintLayout mCon2;
    private ConstraintLayout mCon3;
    private String meetingID;
    private String tenantId;
    private String userId;
    private String ip;
    private TextView mTvRemind;
    private TextView mTvHostName;
    private TextView mTvJoiner;
    private TextView mTvHostNames;
    private TextView mTvRecorder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_meet_detail;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initLayout() {
        initView();
    }

    private void initView() {
        if (getIntent().hasExtra("meetingID")) {
            meetingID = getIntent().getStringExtra("meetingID");
        }
        tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        userId = SharedPreferencesTool.getMStool(this).getUserId();
        ip = SharedPreferencesTool.getMStool(this).getIp();
        mTablayout = (TabLayout) findViewById(R.id.tablayout_meet_detail);
        mViewPage = (ViewPager) findViewById(R.id.view_pager_meet_detail);
        mTvContent = (TextView) findViewById(R.id.tv_meet_detail_contant);//会议内容
        mTvOpenTime = (TextView) findViewById(R.id.tv_work_meet_detail_open_time);//会议时间
        mTvRemind = (TextView) findViewById(R.id.tv_meet_detail_meet_remind);//会议提醒
        mTvHostName = (TextView) findViewById(R.id.textView12);//主持人名字
        mTvJoiner = (TextView) findViewById(R.id.tv_meet_detail_attendee);//参会人前两位显示
        mTvHostNames = (TextView) findViewById(R.id.tv_meet_detail_host);//主持人名字
        mTvRecorder = (TextView) findViewById(R.id.tv_meet_detail_note_taker);//记录人名字
        mButton = (Button) findViewById(R.id.button);//添加会议纪要,记录人才可以点击//默认不显示
        mCon1 = (ConstraintLayout) findViewById(R.id.constraintLayout);//参会人layout
        mCon2 = (ConstraintLayout) findViewById(R.id.constraintLayout2);//主持人人layout
        mCon3 = (ConstraintLayout) findViewById(R.id.constraintLayout3);//记录人layout

        ArrayList<String> titleDatas = new ArrayList<>();
        titleDatas.add("我发出的");
        titleDatas.add("部门发出的");
        ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new DepartmentReceiveFragment());
        fragmentList.add(new DepartmentSendFragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        mViewPage.setAdapter(myViewPageAdapter);
        mTablayout.setupWithViewPager(mViewPage);

        initListener();

        initData();


    }


    //初始化数据
    private void initData() {

        PostMeetDetailJson jsonBean = new PostMeetDetailJson("CEOAssist", "GetMeetingInfo", meetingID, tenantId);

        String json = GsonUtil.getGson().toJson(jsonBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

        setMeetDetailData(body);


    }


    /**
     * @author zjq
     * create at 2018/12/11 下午2:34
     * 联网获取会议详情
     */
    private void setMeetDetailData(RequestBody body) {
        RetrofitClients.getInstance(this, ip).create(WorkAPi.class)
                .getMeetingDetail(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中");
                    }
                })
                .subscribe(new MyBaseSubscriber<ResponseMeetDetailEntity>(this) {
                    @Override
                    public void onResult(ResponseMeetDetailEntity o) {
                        dismissDialog();

                        //保存meetID
                        SharedPreferencesTool.getMStool(MeetDetailActivity.this).setString("MeetId",o.getMeetingID());



                        String endDate[] = o.getEndDate().split("T");
                        String startDate[] = o.getStartDate().split("T");
                        mTvContent.setText(o.getMeetingContent());
                        mTvOpenTime.setText(startDate[0]+""+startDate[1]+endDate[1]);
                        mTvRemind.setText(getmettingRemind(o.getMeetingReminder()));//会议提醒
                        mTvHostName.setText(o.getHostName());//主持人名字第一行
                        mTvHostNames.setText(o.getHostName());//主持人名字第二行
                        String[] split = o.getParticipantName().split(",");
                        mTvJoiner.setText(split[0] + "等" + split.length + "人");//参会人前两位显示+人数
                        mTvRecorder.setText(o.getRecorderName());//记录人名字
                        List<CopyPeopleBean> list=new ArrayList<>();//主持人
                        List<CopyPeopleBean> list1=new ArrayList<>();//记录人
                        List<CopyPeopleBean> list2=new ArrayList<>();//参与人
                        list.add(new CopyPeopleBean(o.getHostName()));//存会议主持人
                        String json = GsonUtil.getGson().toJson(list);
                        SharedPreferencesTool.getMStool(MeetDetailActivity.this).setString("MeetHost",json);



                        list1.add(new CopyPeopleBean(o.getRecorderName()));//存记录人
                        String json1 = GsonUtil.getGson().toJson(list1);
                        SharedPreferencesTool.getMStool(MeetDetailActivity.this).setString("MeetRecord",json1);


                        for (int i = 0; i < split.length; i++) {
                            list2.add(new CopyPeopleBean(split[i]));//存会议参与人
                        }
                        String json2 = GsonUtil.getGson().toJson(list2);
                        SharedPreferencesTool.getMStool(MeetDetailActivity.this).setString("MeetJoin",json2);


                        String userName = SharedPreferencesTool.getMStool(MeetDetailActivity.this).getUserName();

                        if (userName.equals(o.getRecorderName())){
                            //当前用户是记录人可以添加会议纪要
                            mButton.setVisibility(View.VISIBLE);

                        }else{
                            mButton.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onError(ResponseThrowable e) {
                        dismissDialog();
                        Toasty.INSTANCE.showToast(MeetDetailActivity.this, "请求失败");
                    }
                });


    }




    private void initListener() {
        mCon1.setOnClickListener(this);
        mCon2.setOnClickListener(this);
        mCon3.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }


    /**
     * @author zjq List<CopyPeopleBean> ListBeans
     * create at 2018/12/11 下午1:53
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.constraintLayout)
        {
            //参会人
            Intent intent =new Intent(this,CopyPersonActivity.class);
            intent.putExtra("WorkDetailType","MeetJoin");
            startActivity(intent);

        } else if(id==R.id.constraintLayout2){
            //主持人
            Intent intent =new Intent(this,CopyPersonActivity.class);
            intent.putExtra("WorkDetailType","MeetHost");
            startActivity(intent);
        }  else if(id==R.id.constraintLayout3){
          //记录人
            Intent intent =new Intent(this,CopyPersonActivity.class);
            intent.putExtra("WorkDetailType","MeetRecord");
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


    //会议类型转换
    public String getmettingRemind(int i) {
        String name = "";
        switch (i) {
            case 1:
                name = "不提醒";
                break;
            case 2:

                name = "会议开始时";
                break;
            case 3:
                name = "提前五分钟";
                break;
            case 4:

                name = "提前十五分钟";
                break;
            case 5:

                name = "提前三十分钟";
                break;
            case 6:
                name = "提前一天";
                break;
        }
        return name;
    }

}
