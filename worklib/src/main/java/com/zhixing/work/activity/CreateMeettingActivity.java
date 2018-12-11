package com.zhixing.work.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.shuben.contact.lib.common.ConstantS;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.widget.ChangeTime;
import com.google.gson.reflect.TypeToken;
import com.shuben.contact.lib.ConstantActivity;
import com.shuben.contact.lib.event.ConstantDataEvent;
import com.shuben.contact.lib.event.TypeBean;
import com.zhixing.work.R;
import com.zhixing.work.bean.CreateTaskEntity;
import com.zhixing.work.bean.MeetStatusType;
import com.zhixing.work.bean.PostCreateMeetJson;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;
import com.zhixing.work.ui.MeetStatusTypeDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

/**
 * 创建会议
 *
 * @author zjq
 * create at 2018/11/29 下午3:44
 */
public class CreateMeettingActivity extends BaseActvity implements View.OnClickListener, Validator.ValidationListener, MeetStatusTypeDialog.OnDialogInforCompleted {
    private static final String SEPARATOR = ",";
    private ImageView mIvadd;
    private TextView mTvTitle;
    private TextView mTvSend;
    @NotEmpty
    private TextView mTvTimeStart;
    @NotEmpty
    private TextView mTvTimeEnd;
    private RelativeLayout mRlMeetOrganizing;
    @NotEmpty
    private TextView mTvMeetOrganizingPeople;
    private RelativeLayout mRlMeetRecord;
    @NotEmpty
    private TextView mTvMeetRecordPeople;
    private RelativeLayout mRlMeetJoin;
    @NotEmpty
    private TextView mTvMeetJoinPeople;
    private String meetOrganizingPeople = "1";//组织人
    private String meetRecordPeople = "2";//记录人
    private String meetJoinPeople = "3";//参与者
    private String Names = "";//参与者名字拼

    private String AppCode = "CEOAssist";

    private String ApiCode = "EditMeeting";

    private String TenantId;
    private Validator validator;
    private String HostID;
    private String CreateUserId;
    private String RecorderID;
    private String ParticipantID;
    @NotEmpty
    private EditText mEditAddress;
    @NotEmpty
    private EditText mEditContent;
    private PostCreateMeetJson postCreateMeetJson;
    private RelativeLayout mRlMeetRemind;
    private TextView mTvMeetRemind;
    private int MeetingReminder;//会议提醒类型
    private String ip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_meetting;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void initLayout() {
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
         ip = SharedPreferencesTool.getMStool(this).getIp();
        TenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        CreateUserId = SharedPreferencesTool.getMStool(this).getUserId();
        mIvadd = (ImageView) findViewById(R.id.iv_work_add_work);
        mTvTitle = (TextView) findViewById(R.id.tv_work_title);
        mTvSend = (TextView) findViewById(R.id.tv_work_send);
        mTvTimeStart = (TextView) findViewById(R.id.tv_meet_min);//选择开始时间
        mTvTimeEnd = (TextView) findViewById(R.id.tv_meet_end_min);//选择结束时间
        mEditAddress = (EditText) findViewById(R.id.ed_create_address);

        mEditContent = (EditText) findViewById(R.id.ed_create_meet_content);
        mRlMeetOrganizing = (RelativeLayout) findViewById(R.id.rl_meet_organizing_people);  //会议组织人
        mTvMeetOrganizingPeople = (TextView) findViewById(R.id.tv_meet_organizing_people);


        mRlMeetJoin = (RelativeLayout) findViewById(R.id.rl_meet_join_people);  //会议参与人
        mTvMeetJoinPeople = (TextView) findViewById(R.id.tv_meet_join_people);


        mRlMeetRecord = (RelativeLayout) findViewById(R.id.rl_meet_record_people);  //会议记录人
        mTvMeetRecordPeople = (TextView) findViewById(R.id.tv_meet_record_people);


        //会议提醒
        mRlMeetRemind=(RelativeLayout) findViewById(R.id.rl_create_meet_remind);
        mTvMeetRemind=(TextView) findViewById(R.id.tv_create_meet_remind);

        mIvadd.setImageResource(R.mipmap.left_jian_tou);
        mTvTitle.setText("发起会议");
        mTvSend.setVisibility(View.VISIBLE);

        initListener();

    }

    private void initListener() {
        mTvTimeStart.setOnClickListener(this);
        mTvTimeEnd.setOnClickListener(this);
        mRlMeetOrganizing.setOnClickListener(this);
        mRlMeetJoin.setOnClickListener(this);
        mRlMeetRecord.setOnClickListener(this);
        mTvSend.setOnClickListener(this);
        mRlMeetRemind.setOnClickListener(this);
        mIvadd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_meet_min) {
//            CalendarUtil.getInstance().setDate02(this, mTvTimeStart);
            ChangeTime changeTime = new ChangeTime(this, "", 0, mTvTimeStart);
            changeTime.showSheet();

        } else if (i == R.id.tv_meet_end_min) {
            ChangeTime changeTime = new ChangeTime(this, "", 0, mTvTimeEnd);
            changeTime.showSheet();
        } else if (i == R.id.rl_meet_organizing_people) {
            //HostID 主持人id
            Intent mRlMeetOrganizingIntent = new Intent(this, ConstantActivity.class);
            mRlMeetOrganizingIntent.putExtra(ConstantS.ISEDIT, true);
            mRlMeetOrganizingIntent.putExtra(ConstantS.ISSINGLE, true);
            mRlMeetOrganizingIntent.putExtra(ConstantS.TYPE, "1");
            startActivity(mRlMeetOrganizingIntent);
        } else if (i == R.id.rl_meet_join_people) {
            //HostID 参会人id
            Intent mRlMeetOrganizingIntent = new Intent(this, ConstantActivity.class);
            mRlMeetOrganizingIntent.putExtra(ConstantS.ISEDIT, true);
            mRlMeetOrganizingIntent.putExtra(ConstantS.ISSINGLE, false);
            mRlMeetOrganizingIntent.putExtra(ConstantS.TYPE, "3");
            startActivity(mRlMeetOrganizingIntent);


        } else if (i == R.id.rl_meet_record_people) {
            //记录人id
            Intent mRlMeetOrganizingIntent = new Intent(this, ConstantActivity.class);
            mRlMeetOrganizingIntent.putExtra(ConstantS.ISEDIT, true);
            mRlMeetOrganizingIntent.putExtra(ConstantS.ISSINGLE, true);
            mRlMeetOrganizingIntent.putExtra(ConstantS.TYPE, "2");
            startActivity(mRlMeetOrganizingIntent);


        } else if (i == R.id.tv_work_send) {
            validator.validate();
        }else if(i==R.id.rl_create_meet_remind){

            List<MeetStatusType> data=new ArrayList<>();
            //1-不提醒；2-会议开始时；3-提前五分钟；4-提前十五分钟；5-提前三十分钟；6-提前一天
            data.add(new MeetStatusType("不提醒"));
            data.add(new MeetStatusType("会议开始时"));
            data.add(new MeetStatusType("提前五分钟"));
            data.add(new MeetStatusType("提前十五分钟"));
            data.add(new MeetStatusType("提前三十分钟"));
            data.add(new MeetStatusType("提前一天"));
            String json = GsonUtil.getGson().toJson(data);
            MeetStatusTypeDialog dialog = MeetStatusTypeDialog.newInstance(json);
            dialog.setOnDialogInforCompleted(this);
            dialog.show(getSupportFragmentManager(),"MeetStatusTypeDialog");


        }else if(i==R.id.iv_work_add_work){
            AppManager.getAppManager().finishActivity();
        }

        //
    }


    //接受通讯模块发过来的数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventOrgan(ConstantDataEvent event) {
        String type = SharedPreferencesTool.getMStool(CreateMeettingActivity.this).getString("type");
        String datas = SharedPreferencesTool.getMStool(CreateMeettingActivity.this).getString(ConstantS.CONSTANTDATA);

        if ("1".equals(type)) {
            //组织人
            Type mType = new TypeToken<List<TypeBean>>() {
            }.getType();
            List<TypeBean> ListBeans = GsonUtil.getGson().fromJson(datas, mType);

            for (TypeBean bean : ListBeans) {
                mTvMeetOrganizingPeople.setText(bean.getName());
                HostID = bean.getId();
            }
        } else if ("2".equals(type)) {
            //记录人
            Type mType = new TypeToken<List<TypeBean>>() {
            }.getType();
            List<TypeBean> ListBeans = GsonUtil.getGson().fromJson(datas, mType);

            for (TypeBean bean : ListBeans) {
                mTvMeetRecordPeople.setText(bean.getName());
                RecorderID = bean.getId();
            }
        } else {
            //参与人
            Type mType = new TypeToken<List<TypeBean>>() {
            }.getType();

            //
            List<TypeBean> ListBeans = GsonUtil.getGson().fromJson(datas, mType);
            StringBuilder csvBuilder = new StringBuilder();
            for (TypeBean bean : ListBeans) {
                csvBuilder.append(bean.getId());
                csvBuilder.append(SEPARATOR);
            }
            String csv = csvBuilder.toString();
            ParticipantID = csv.substring(0, csv.length() - SEPARATOR.length());


            mTvMeetJoinPeople.setText(ListBeans.size() + "人");
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onValidationSucceeded() {
        SendMeet();
    }

    private void SendMeet() {
        //
        setBeanData();

        String json = GsonUtil.getGson().toJson(postCreateMeetJson);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        RetrofitClients.getInstance(this,ip).create(WorkAPi.class)
                .CreateMeet(body)
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
                            //成功
                            AppManager.getAppManager().finishActivity();
                            //发消息通知任务列表刷新数据

                        } else {
                            Toasty.INSTANCE.showToast(CreateMeettingActivity.this, entity.getMessage());
                        }
                    }
                });



    }
     //设置bean数据
    private void setBeanData() {
        long l = TimeUtil.parseTime(mTvTimeStart.getText().toString());
        String startTime = TimeUtil.getTime(l);
        long l1 = TimeUtil.parseTime(mTvTimeEnd.getText().toString());
        String endTime = TimeUtil.getTime(l1);
         MeetingReminder = 1;
        postCreateMeetJson = new PostCreateMeetJson();
        PostCreateMeetJson.UserBean userBean = new PostCreateMeetJson.UserBean();
        userBean.setParticipantID(ParticipantID);
        userBean.setHostID(HostID);
        userBean.setRecorderID(RecorderID);
        PostCreateMeetJson.RowsBean rowsBean = new PostCreateMeetJson.RowsBean();
        List<PostCreateMeetJson.RowsBean.ListBean.InsertedBean> list = new ArrayList<>();
        PostCreateMeetJson.RowsBean.ListBean.InsertedBean listBeans = new PostCreateMeetJson.RowsBean.ListBean.InsertedBean();
        listBeans.setCreateUserID(CreateUserId);
        listBeans.setEndDate(endTime);
        listBeans.setStartDate(startTime);
        listBeans.setMeetingContent(mEditContent.getText().toString().trim());
        listBeans.setMeetingPlace(mEditAddress.getText().toString().trim());
        listBeans.setTenantId(TenantId);
        listBeans.setMeetingReminder(MeetingReminder);
        list.add(listBeans);
        rowsBean.setList(new PostCreateMeetJson.RowsBean.ListBean(list));
        postCreateMeetJson.setApiCode(ApiCode);
        postCreateMeetJson.setAppCode(AppCode);
        postCreateMeetJson.setRows(rowsBean);
        postCreateMeetJson.setUser(userBean);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toasty.INSTANCE.showToast(this, "内容不能为空");
    }
    //1-不提醒；2-会议开始时；3-提前五分钟；4-提前十五分钟；5-提前三十分钟；6-提前一天

    @Override
    public void dialogInforCompleted(String name) {
        mTvMeetRemind.setText(name);
        switch (name){
            case "不提醒":
                MeetingReminder=1;
                break;
            case "会议开始时":
                MeetingReminder=2;
                break;
            case "提前五分钟":
                MeetingReminder=3;
                break;
            case "提前十五分钟":
                MeetingReminder=4;
                break;
            case "提前三十分钟":
                MeetingReminder=5;
                break;
            case "提前一天":
                MeetingReminder=6;
                break;
        }

    }
}










