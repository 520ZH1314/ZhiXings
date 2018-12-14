package com.zhixing.work.activity;


import android.content.Intent;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhixing.work.R;
import com.zhixing.work.adapt.CreateMeetRecordAdapt;
import com.zhixing.work.bean.AddMeetRecordEvent;
import com.zhixing.work.bean.CopyPeopleBean;
import com.zhixing.work.bean.CreateTaskEntity;
import com.zhixing.work.bean.MeetCompeteRefrshDataEvent;
import com.zhixing.work.bean.MeetDeatilResponseEvent;
import com.zhixing.work.bean.MeetDisRefrshDataEvent;
import com.zhixing.work.bean.MeetStatusType;
import com.zhixing.work.bean.PostCompeteMeetJson;
import com.zhixing.work.bean.PostDisBeanJson;
import com.zhixing.work.bean.PostMeetDetailJson;
import com.zhixing.work.bean.PostMeetJoinJson;
import com.zhixing.work.bean.PostNewMeetDetailJson;
import com.zhixing.work.bean.PostNewMeetJoinJson;
import com.zhixing.work.bean.PostTaskReplyJson;
import com.zhixing.work.bean.ResponseMeetDetailEntity;
import com.zhixing.work.bean.UpdateMeetSureEvent;
import com.zhixing.work.fragment.DepartmentReceiveFragment;
import com.zhixing.work.fragment.DepartmentSendFragment;
import com.zhixing.work.fragment.MeetDetailIsSureFragment;
import com.zhixing.work.fragment.MeetDetailResponseFragment;
import com.zhixing.work.fragment.MyReceiveFragment;
import com.zhixing.work.http.base.BaseSubscriber;
import com.zhixing.work.http.base.MyBaseSubscriber;
import com.zhixing.work.http.base.ResponseThrowable;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;
import com.zhixing.work.ui.CloseTaskDialog;
import com.zhixing.work.ui.CommonTips;
import com.zhixing.work.ui.TopMeetStatusTypeDialog;
import com.zhixing.work.ui.WrapContentHeightViewPager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MeetDetailActivity extends BaseActvity implements View.OnClickListener, TopMeetStatusTypeDialog.OnDialogInforCompleted {

    private TabLayout mTablayout;
    private WrapContentHeightViewPager mViewPage;
    private TextView mTvContent;
    private TextView mTvOpenTime;
    private Button mButton;
    private ConstraintLayout mCon1;
    private ConstraintLayout mCon2;
    private ConstraintLayout mCon3;
    private String tenantId;
    private String userId;
    private String ip;
    private TextView mTvRemind;
    private TextView mTvHostName;
    private TextView mTvJoiner;
    private TextView mTvHostNames;
    private TextView mTvRecorder;
    private RecyclerView mRecyRecordList;
    private EditText mEdit;
    private String meetingDataID;
    private ImageView mAdd;
    private Button mSend;
    private Button mBtnJoin;
    private ImageView mIvMore;
    private boolean isJoin = false;
    private ImageView mIvBack;

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

        meetingDataID = getIntent().getStringExtra("meetingDataID");
        tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        userId = SharedPreferencesTool.getMStool(this).getUserId();
        ip = SharedPreferencesTool.getMStool(this).getIp();
        mIvBack=(ImageView) findViewById(R.id.iv_work_add_work);
        mIvMore = (ImageView) findViewById(R.id.iv_meet_detail);
        mTablayout = (TabLayout) findViewById(R.id.tablayout_meet_detail);
        mViewPage = (WrapContentHeightViewPager) findViewById(R.id.view_pager_meet_detail);
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
        mRecyRecordList = (RecyclerView) findViewById(R.id.recy_meet_record_list);
        mEdit = (EditText) findViewById(R.id.ed_meet_detail_respon);//回复
        mAdd = (ImageView) findViewById(R.id.iv_meet_detail_add_message);
        mSend = (Button) findViewById(R.id.btn_meet_detail_send);
        mBtnJoin = (Button) findViewById(R.id.btn_work_meet_detail);//参加会议
        EventBus.getDefault().register(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyRecordList.setLayoutManager(layoutManager);

        ArrayList<String> titleDatas = new ArrayList<>();
        titleDatas.add("回复");
        titleDatas.add("参加会议");
        ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new MeetDetailResponseFragment());
        fragmentList.add(new MeetDetailIsSureFragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        mViewPage.setAdapter(myViewPageAdapter);
        mTablayout.setupWithViewPager(mViewPage);
        initListener();
        initData();


    }


    //初始化数据
    private void initData() {
        PostNewMeetDetailJson jsonBean = new PostNewMeetDetailJson("CEOAssist", "GetMeetingInfo", meetingDataID, tenantId);
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
                        SharedPreferencesTool.getMStool(MeetDetailActivity.this).setString("MeetId", o.getMeetingID());


                        //判断是否有权限关闭会议和取消会议
                        if (userId.equals(o.getCreateUserID())) {

                            if (o.getMeetingStatus() == 1 || o.getMeetingStatus() == 2) {
                                mIvMore.setClickable(true);
                            } else {
                                mIvMore.setClickable(false);
                            }

                        } else {
                            mIvMore.setClickable(false);
                        }

                        String endDate[] = o.getEndDate().split("T");
                        String startDate[] = o.getStartDate().split("T");
                        mTvContent.setText(o.getMeetingContent());
                        String time = endDate[0] + " " + endDate[1];
                        String time1 = startDate[0] + " " + startDate[1];
                        mTvOpenTime.setText(TimeUtil.getFormatData(time) + TimeUtil.getFormatData(time1));
                        mTvRemind.setText(getmettingRemind(o.getMeetingReminder()));//会议提醒
                        mTvHostName.setText("主持人:" + o.getHostName());//主持人名字第一行
                        mTvHostNames.setText(o.getHostName());//主持人名字第二行
                        String[] split = o.getParticipantName().split(",");
                        mTvJoiner.setText(split[0] + "等" + split.length + "人");//参会人前两位显示+人数
                        mTvRecorder.setText(o.getRecorderName());//记录人名字
                        //设置会议纪要的数据
                        setMeetRecordListData(o);

                        //保存一些回复消息
                        List<ResponseMeetDetailEntity.CommentListBean.RowsBean> rows = o.getCommentList().getRows();
                        String json3 = GsonUtil.getGson().toJson(rows);
                        //发消息通知消息的fragment更新数据

                        EventBus.getDefault().postSticky(new MeetDeatilResponseEvent(json3));
                        SharedPreferencesTool.getMStool(MeetDetailActivity.this).setString("meetResponseData", json3);

                        SharedPreferencesTool.getMStool(MeetDetailActivity.this).setString("meetCreateID", o.getCreateUserID());
                        //保存一些联系人数据
                        List<CopyPeopleBean> list = new ArrayList<>();//主持人
                        List<CopyPeopleBean> list1 = new ArrayList<>();//记录人
                        List<CopyPeopleBean> list2 = new ArrayList<>();//参与人
                        list.add(new CopyPeopleBean(o.getHostName()));//存会议主持人
                        String json = GsonUtil.getGson().toJson(list);
                        SharedPreferencesTool.getMStool(MeetDetailActivity.this).setString("MeetHost", json);


                        list1.add(new CopyPeopleBean(o.getRecorderName()));//存记录人
                        String json1 = GsonUtil.getGson().toJson(list1);
                        SharedPreferencesTool.getMStool(MeetDetailActivity.this).setString("MeetRecord", json1);


                        for (int i = 0; i < split.length; i++) {
                            list2.add(new CopyPeopleBean(split[i]));//存会议参与人
                        }
                        String json2 = GsonUtil.getGson().toJson(list2);
                        SharedPreferencesTool.getMStool(MeetDetailActivity.this).setString("MeetJoin", json2);


                        String userName = SharedPreferencesTool.getMStool(MeetDetailActivity.this).getUserName();

                        if (userName.equals(o.getRecorderName())) {
                            //当前用户是记录人可以添加会议纪要
                            mButton.setVisibility(View.VISIBLE);

                        } else {
                            mButton.setVisibility(View.GONE);
                        }

                        //参会权限
                        List<String> ParticipantIdData = new ArrayList<>();
                        ParticipantIdData.add(o.getHostID());
                        ParticipantIdData.add(o.getRecorderID());
                        String[] split1 = o.getParticipantID().split(",");
                        for (int i = 0; i < split1.length; i++) {
                            ParticipantIdData.add(split1[i]);
                        }

                        if (ParticipantIdData.contains(userId)) {
                            //是参会人
                            isJoin = true;
                        }
                        if (o.getMeetingStatus() == 1 && isJoin) {
                            //可以参加会议
                            mBtnJoin.setClickable(true);
                            mBtnJoin.setText("参加会议");
                        } else {
                            mBtnJoin.setClickable(false);
                            mBtnJoin.setText("已参加");
                        }


                    }

                    @Override
                    public void onError(ResponseThrowable e) {
                        dismissDialog();
                        Toasty.INSTANCE.showToast(MeetDetailActivity.this, "请求失败");
                    }
                });


    }


    //设置会议纪要数据
    private void setMeetRecordListData(final ResponseMeetDetailEntity o) {
        CreateMeetRecordAdapt adapt = new CreateMeetRecordAdapt(R.layout.item_create_meet_record, o.getMeetingDetailsList().getRows());

        adapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MeetDetailActivity.this, CreateMeetingRecordActivity.class);
                intent.putExtra("Des", o.getMeetingDetailsList().getRows().get(position).getMeetingDes());
                startActivity(intent);
            }
        });
        mRecyRecordList.setAdapter(adapt);
    }


    private void initListener() {
        mCon1.setOnClickListener(this);
        mCon2.setOnClickListener(this);
        mCon3.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mBtnJoin.setOnClickListener(this);
        mIvMore.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int a;
                if (s.length() != 0) {
                    mAdd.setVisibility(View.GONE);
                    mSend.setVisibility(View.VISIBLE);
                } else {
                    mAdd.setVisibility(View.VISIBLE);
                    mSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * @author zjq List<CopyPeopleBean> ListBeans
     * create at 2018/12/11 下午1:53
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.constraintLayout) {
            //参会人
            Intent intent = new Intent(this, CopyPersonActivity.class);
            intent.putExtra("WorkDetailType", "MeetJoin");
            startActivity(intent);

        } else if (id == R.id.constraintLayout2) {
            //主持人
            Intent intent = new Intent(this, CopyPersonActivity.class);
            intent.putExtra("WorkDetailType", "MeetHost");
            startActivity(intent);
        } else if (id == R.id.constraintLayout3) {
            //记录人
            Intent intent = new Intent(this, CopyPersonActivity.class);
            intent.putExtra("WorkDetailType", "MeetRecord");
            startActivity(intent);
        } else if (id == R.id.button) {
            Intent intent = new Intent(this, CreateMeetingRecordActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_meet_detail_send) {
            //发送回复
            sendMessage();
        } else if (id == R.id.btn_work_meet_detail) {
            //确认参加
            JoinMeet();
            //发消息通知确认参加界面刷新数据

        } else if (id == R.id.iv_meet_detail) {
            //弹窗
            List<MeetStatusType> data = new ArrayList<>();
            data.add(new MeetStatusType("取消"));
            data.add(new MeetStatusType("关闭"));
            String json = GsonUtil.getGson().toJson(data);
            TopMeetStatusTypeDialog textMeetStatusTypeDialog = TopMeetStatusTypeDialog.newInstance(json);
            textMeetStatusTypeDialog.show(getSupportFragmentManager(), "");
            textMeetStatusTypeDialog.setOnDialogInforCompleted(this);

        }else if (id==R.id.iv_work_add_work){
            AppManager.getAppManager().finishActivity();
        }
    }


    /**
     * @param text
     * @author zjq
     * create at 2018/12/12 下午6:16
     * 取消会议
     */
    private void DissMeeting(String text) {
        PostDisBeanJson json = new PostDisBeanJson();
        json.setApiCode("EditConcelMeeting");
        json.setAppCode("CEOAssist");
        json.setMeetingID( SharedPreferencesTool.getMStool(this).getString("MeetId"));
        json.setMeetingRemark(text);
        String json1 = GsonUtil.getGson().toJson(json);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json1);
        RetrofitClients.getInstance(this, ip).create(WorkAPi.class)
                .DisMeet(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中");
                    }
                }).subscribe(new MyBaseSubscriber<CreateTaskEntity>(this) {
            @Override
            public void onResult(CreateTaskEntity o) {
                dismissDialog();
                Toasty.INSTANCE.showToast(MeetDetailActivity.this, "取消成功");

                EventBus.getDefault().postSticky(new MeetDisRefrshDataEvent(true));
                //发通知刷新下界面
                AppManager.getAppManager().finishActivity();
            }

            @Override
            public void onError(ResponseThrowable e) {
                dismissDialog();
            }
        });


    }


    /**
     * @author zjq
     * create at 2018/12/12 下午6:17
     * 关闭会议(完成)
     */
    private void CompeteMeeting() {
        PostCompeteMeetJson json = new PostCompeteMeetJson();
        json.setApiCode("EditCloseMeeting");
        json.setAppCode("CEOAssist");
        json.setMeetingID(SharedPreferencesTool.getMStool(this).getString("MeetId"));
        String json1 = GsonUtil.getGson().toJson(json);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json1);
        RetrofitClients.getInstance(this, ip).create(WorkAPi.class)
                .CompeteMeet(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中");
                    }
                }).subscribe(new MyBaseSubscriber<CreateTaskEntity>(this) {
            @Override
            public void onResult(CreateTaskEntity o) {
                dismissDialog();
                Toasty.INSTANCE.showToast(MeetDetailActivity.this, "成功关闭");

                //发通知刷新下界面
                EventBus.getDefault().postSticky(new MeetCompeteRefrshDataEvent(true));
                AppManager.getAppManager().finishActivity();
            }

            @Override
            public void onError(ResponseThrowable e) {
                dismissDialog();
            }
        });

    }

    /**
     * @author zjq
     * create at 2018/12/12 下午6:14
     * 参加会议
     */
    private void JoinMeet() {
        PostNewMeetJoinJson json = new PostNewMeetJoinJson();

        json.setAppCode("CEOAssist");
        json.setApiCode("EditConfirmMeeting");
        json.setMeetingDataID(meetingDataID);
        json.setSystemCurrentUserID(userId);
        String json1 = GsonUtil.getGson().toJson(json);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json1);
        RetrofitClients.getInstance(this, ip).create(WorkAPi.class)
                .JoinMeet(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中");
                    }
                }).subscribe(new MyBaseSubscriber<CreateTaskEntity>(this) {
            @Override
            public void onResult(CreateTaskEntity o) {
                dismissDialog();
                Toasty.INSTANCE.showToast(MeetDetailActivity.this, "参加成功");
                mBtnJoin.setText("已参加");
                mBtnJoin.setClickable(false);
                EventBus.getDefault().post(new UpdateMeetSureEvent(true));
            }

            @Override
            public void onError(ResponseThrowable e) {
                dismissDialog();
            }
        });


    }


    //弹窗回调
    @Override
    public void dialogInforCompleted(String name) {
        if ("取消".equals(name)) {
            CloseTaskDialog closeTaskDialog = CloseTaskDialog.newInstance("取消会议");
            closeTaskDialog.setOnCloseDialogInforCompleted(new CloseTaskDialog.OnCloseDialogInforCompleted() {
                @Override
                public void closeDialogInforCompleted(String text) {
                    DissMeeting(text);
                }
            });
            closeTaskDialog.show(getSupportFragmentManager(), "CloseTaskDialog");


        } else {
            CommonTips tips = new CommonTips(this, getHandler());
            tips.init("取消", "确定", "是否关闭会议");
            tips.setI(new CommonTips.Tips() {
                @Override
                public void cancel() {

                }

                @Override
                public void sure() {
                    CompeteMeeting();

                }
            });
            tips.showSheet();

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


    //更新会议纪要事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void RecordEvent(AddMeetRecordEvent event) {
        if (event.isSend) {
            initData();
        }

        AddMeetRecordEvent stickyEvent = EventBus.getDefault().getStickyEvent(AddMeetRecordEvent.class);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    private void sendMessage() {
        if (TextUtils.isEmpty(mEdit.getText().toString().trim())) {
            Toasty.INSTANCE.showToast(this, "内容不能为空");
        } else {
            PostTaskReplyJson postTaskReplyJson = new PostTaskReplyJson();
            postTaskReplyJson.setApiCode("EditComment");
            postTaskReplyJson.setAppCode("CEOAssist");
            PostTaskReplyJson.RowsBean rowsBean = new PostTaskReplyJson.RowsBean();
            PostTaskReplyJson.RowsBean.ListBean ListBean = new PostTaskReplyJson.RowsBean.ListBean();
            List<PostTaskReplyJson.RowsBean.ListBean.InsertedBean> listBeans = new ArrayList<>();
            PostTaskReplyJson.RowsBean.ListBean.InsertedBean bean = new PostTaskReplyJson.RowsBean.ListBean.InsertedBean();
            bean.setCommentSourceID(SharedPreferencesTool.getMStool(this).getString("MeetId"));//T
            bean.setCommentText(mEdit.getText().toString().trim());
            bean.setCommentUserID(SharedPreferencesTool.getMStool(this).getUserId());
            bean.setCreateUserID(SharedPreferencesTool.getMStool(this).getUserId());
            bean.setTenantId(tenantId);
            bean.setToUserID(SharedPreferencesTool.getMStool(this).getString("meetCreateID"));
            listBeans.add(bean);
            ListBean.setInserted(listBeans);
            rowsBean.setList(ListBean);
            postTaskReplyJson.setRows(rowsBean);
            String json = GsonUtil.getGson().toJson(postTaskReplyJson);
            RequestBody replybody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

            RetrofitClients.getInstance(this, ip).create(WorkAPi.class)
                    .SendMessage(replybody)
                    .compose(RxUtils.schedulersTransformer())  // 线程调度
                    .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showDialog("加载中");
                        }
                    })
                    .subscribe(new Consumer<CreateTaskEntity>() {
                        @Override
                        public void accept(CreateTaskEntity entity) throws Exception {

                            dismissDialog();
                            if (entity.isStatus()) {
                                //保存成功
                                initData();//重新刷新下数据
                            }


                        }
                    });
        }

    }


}
