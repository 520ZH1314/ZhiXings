package com.zhixing.work.activity;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeTime;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.zhixing.work.R;
import com.zhixing.work.bean.CreateTaskEntity;
import com.zhixing.work.bean.MeetStatusType;
import com.zhixing.work.bean.PostDateJson;
import com.zhixing.work.http.base.MyBaseSubscriber;
import com.zhixing.work.http.base.ResponseThrowable;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;
import com.zhixing.work.ui.MeetStatusTypeDialog;
import com.zhixing.work.utils.TextViewUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class CreateCalendarActivity extends BaseActvity  implements View.OnClickListener ,Validator.ValidationListener{

    private String ip;
    private String TenantId;
    private String CreateUserId;
    private ImageView mIvadd;
    private TextView mTvTitle;
    private TextView mTvSend;
    @NotEmpty
    private TextView mTvTimeStart;
    @NotEmpty
    private TextView mTvTimeEnd;
    private RelativeLayout mRlDateRepeatRemind;
    private RelativeLayout mRlDateRemind;
    private TextView mTvRemind;
    private TextView mTvRepeatRemind;
    private Validator validator;
    @NotEmpty
    private EditText mEditTextContext;
    @NotEmpty
    private EditText mEditTextAddMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_calendar;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
       initView();
       initListener();
       initData();
    }

    private void initListener() {
        mIvadd.setOnClickListener(this);
        mTvTimeStart.setOnClickListener(this);
        mTvTimeEnd.setOnClickListener(this);
        mRlDateRemind.setOnClickListener(this);
        mRlDateRepeatRemind.setOnClickListener(this);
        mTvSend.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        ip = SharedPreferencesTool.getMStool(this).getIp();
        TenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        CreateUserId = SharedPreferencesTool.getMStool(this).getUserId();
        mIvadd = (ImageView) findViewById(R.id.iv_work_add_work);
        mTvTitle = (TextView) findViewById(R.id.tv_work_title);
        mTvSend = (TextView) findViewById(R.id.tv_work_send);
        mTvTimeStart = (TextView) findViewById(R.id.tv_date_min);//选择开始时间
        mTvTimeEnd = (TextView) findViewById(R.id.tv_date_end_min);//选择结束时间
        mTvTitle.setText("创建日程");
        //会议提醒
        mRlDateRemind=(RelativeLayout) findViewById(R.id.rl_create_date_remind);
        mRlDateRepeatRemind=(RelativeLayout) findViewById(R.id.rl_create_repeat_remind);//重复
        mTvRemind= (TextView) findViewById(R.id.tv_create_date_remind);//提醒
        mTvRepeatRemind = (TextView) findViewById(R.id.tv_create_date_repeat_remind);//重复

        //日程内容    备注内容

        mEditTextContext=(EditText)findViewById(R.id.ed_create_date_content);

        mEditTextAddMessage=(EditText)findViewById(R.id.ed_create_meet_add_message);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
        }else if(id==R.id.tv_date_min){
            //开始时间
            ChangeTime changeTime = new ChangeTime(this, "", 0, mTvTimeStart);
            changeTime.showSheet();
        }else if(id==R.id.tv_date_end_min){
            //结束时间
            ChangeTime changeTime = new ChangeTime(this, "", 0, mTvTimeEnd);
            changeTime.showSheet();
        }else if(id==R.id.rl_create_date_remind){
            //日程提醒
            List<MeetStatusType> data=new ArrayList<>();
            //1-不提醒；2-会议开始时；3-提前五分钟；4-提前十五分钟；5-提前三十分钟；6-提前一天
            data.add(new MeetStatusType("不提醒"));
            data.add(new MeetStatusType("日程开始时"));
            data.add(new MeetStatusType("提前五分钟"));
            data.add(new MeetStatusType("提前十五分钟"));
            data.add(new MeetStatusType("提前三十分钟"));
            data.add(new MeetStatusType("提前一天"));
            String json = GsonUtil.getGson().toJson(data);
            MeetStatusTypeDialog dialog = MeetStatusTypeDialog.newInstance(json);
            dialog.setOnDialogInforCompleted(new MeetStatusTypeDialog.OnDialogInforCompleted() {
                @Override
                public void dialogInforCompleted(String name) {
                    mTvRemind.setText(name);
                }
            });
            dialog.show(getSupportFragmentManager(),"MeetStatusTypeDialog");

        }else if(id==R.id.rl_create_repeat_remind){
            //日程重复提醒
            List<MeetStatusType> data=new ArrayList<>();
            //1-不提醒；2-会议开始时；3-提前五分钟；4-提前十五分钟；5-提前三十分钟；6-提前一天
            data.add(new MeetStatusType("不重复"));
            data.add(new MeetStatusType("每天"));
            data.add(new MeetStatusType("每周"));
            data.add(new MeetStatusType("每月"));
            data.add(new MeetStatusType("每年"));
            String json = GsonUtil.getGson().toJson(data);
            MeetStatusTypeDialog dialog = MeetStatusTypeDialog.newInstance(json);
            dialog.setOnDialogInforCompleted(new MeetStatusTypeDialog.OnDialogInforCompleted() {
                @Override
                public void dialogInforCompleted(String name) {
                    mTvRepeatRemind.setText(name);
                }
            });
            dialog.show(getSupportFragmentManager(),"MeetStatusTypeDialog");

        }else if(id==R.id.tv_work_send){
            validator.validate();
        }
    }

    @Override
    public void onValidationSucceeded() {

        if (!TextViewUtils.isEmpty(this,mTvTimeStart,"开始时间")){
            return ;
        }
        if (!TextViewUtils.isEmpty(this,mTvTimeEnd,"结束时间"))
        {
            return ;
        }

        String time = mTvTimeStart.getText().toString();

        String time1 = mTvTimeEnd.getText().toString();

        if (TimeUtil.getTimeCompareSizes(time1,time)==2){
            //发送日程
            RequestBody body = setPostData();

            sendDateData(body);

        }else if (TimeUtil.getTimeCompareSizes(time1,time)==1){
            Toasty.INSTANCE.showToast(CreateCalendarActivity.this,"开始时间不能大于结束时间");

        }else{
            Toasty.INSTANCE.showToast(CreateCalendarActivity.this,"开始时间不能等于结束时间");

        }

    }

   //设置请求的json数据
    @NonNull
    private RequestBody setPostData() {








        PostDateJson postDateJson=new PostDateJson();
        postDateJson.setApiCode("EditSchedule");
        postDateJson.setAppCode("CEOAssist");
        PostDateJson.RowsBean rowsBean=new PostDateJson.RowsBean();
        PostDateJson.RowsBean.ListBean listBean=new PostDateJson.RowsBean.ListBean();
        List<PostDateJson.RowsBean.ListBean.InsertedBean> beans=new ArrayList<>();
        PostDateJson.RowsBean.ListBean.InsertedBean  bean=new PostDateJson.RowsBean.ListBean.InsertedBean();
        bean.setCreateUserID(CreateUserId);
        bean.setEndDate(mTvTimeEnd.getText().toString());
        bean.setExecutorID(CreateUserId);
//         bean.setIsRepeat(getDateRepeatRemind(mTvRepeatRemind.getText().toString()));
        bean.setIsRepeat(1);
        bean.setScheduleContent(mEditTextContext.getText().toString().trim());
//         bean.setScheduleReminder(getDateRemind(mTvRemind.getText().toString()));
        bean.setScheduleReminder(1);
        bean.setStartDate(mTvTimeStart.getText().toString());
        bean.setTenantId(TenantId);
        bean.setScheduleRemark(mEditTextAddMessage.getText().toString().trim());//备注

        beans.add(bean);
        listBean.setInserted(beans);
        rowsBean.setList(listBean);
        postDateJson.setRows(rowsBean);
        String json = GsonUtil.getGson().toJson(postDateJson);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
    }


    /**
     *
     *@author zjq
     *create at 2018/12/17 下午4:31 联网发送数据
     */
    private void sendDateData(RequestBody body) {
        RetrofitClients.getInstance(this,ip).create(WorkAPi.class)
                .CreateDate(body)
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
                 if (o.isStatus()){
                     Toasty.INSTANCE.showToast(CreateCalendarActivity.this,"创建日程成功");
                     AppManager.getAppManager().finishActivity();
                 }else{
                     Toasty.INSTANCE.showToast(CreateCalendarActivity.this,"创建日程失败");

                 }

            }

            @Override
            public void onError(ResponseThrowable e) {
                 dismissDialog();

            }
        });


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toasty.INSTANCE.showToast(this, "内容不能为空");
    }



    //重复时间转换

    public int getDateRepeatRemind(String n){
        int i = 0;
        switch (n){
            case "不重复":
                i=1;
               break;

            case "每天":
                 i=2;
                break;
            case "每周":
                i=3;
                break;
            case "每两周":
                i=4;
                break;
            case "每月":
                i=5;
                break;
            case "每年":
                i=6;
                break;


        }

            return i;
    }


    //提醒时间转换

    public int getDateRemind(String n){
        int i = 0;
        switch (n){
            case "不提醒":
                i=1;
                break;

            case "日程开始时":
              i=2;
                break;
            case "提前五分钟":
                i=3;
                break;
            case "提前十五分钟":
                i=4;
                break;
            case "提前三十分钟":
                i=5;
                break;
            case "提前一天":
                i=6;
                break;


        }

        return i;
    }
}
