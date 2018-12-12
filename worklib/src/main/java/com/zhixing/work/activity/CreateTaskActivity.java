package com.zhixing.work.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.shuben.contact.lib.common.ConstantS;
import com.shuben.contact.lib.event.ConstantDataEvent;
import com.shuben.contact.lib.event.TypeBean;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeTime;
import com.google.gson.reflect.TypeToken;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.orhanobut.logger.Logger;
import com.shuben.contact.lib.ConstantActivity;
import com.zhixing.work.R;
import com.zhixing.work.bean.CreateTaskEntity;
import com.zhixing.work.bean.CreateTaskJsonBean;

import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;

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
 * 创建任务actvivity
 *
 * @author zjq
 * create at 2018/11/30 下午1:38
 */
public class CreateTaskActivity extends BaseActvity implements View.OnClickListener, Validator.ValidationListener {

    private ImageView mIvadd;
    private TextView mTvTitle;
    private TextView mTvSend;
    @NotEmpty
    private EditText mEditContent;
    private static final String SEPARATOR = ",";

    private RelativeLayout mReTaskSend;
    @NotEmpty
    private TextView mTvTaskSend;
    @NotEmpty
    private TextView mTvTaskSendCopy;
    @NotEmpty
    private TextView mTvTaskEndTime;

    private RelativeLayout mReTaskSendCopy;

    private RelativeLayout mReTaskEndTime;
    private String CCUserId;//执行人oid
    private String ToDoUserId;//抄送人oid

    private String TenantId;
    private String userId;
    private Validator validator;
    private String ip;
    private RelativeLayout mReTaskStartTime;
    private TextView mTvTaskStartTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_task;
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
        InitView();
    }

    private void InitView() {
        ip = SharedPreferencesTool.getMStool(this).getIp();
        TenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        userId = SharedPreferencesTool.getMStool(this).getUserId();
        mIvadd = (ImageView) findViewById(R.id.iv_work_add_work);
        mTvTitle = (TextView) findViewById(R.id.tv_work_title);
        mTvSend = (TextView) findViewById(R.id.tv_work_send);

        mEditContent = (EditText) findViewById(R.id.ed_create_task_content);//内容
        mReTaskSend = (RelativeLayout) findViewById(R.id.rl_create_task_send);//执行人
        mTvTaskSend = (TextView) findViewById(R.id.tv_create_task_send);
        mTvTaskSendCopy = (TextView) findViewById(R.id.tv_create_task_send_copy);
        mTvTaskEndTime = (TextView) findViewById(R.id.tv_create_task_end_time);
        mReTaskSendCopy = (RelativeLayout) findViewById(R.id.rl_create_task_send_copy);//抄送人
        mReTaskStartTime = (RelativeLayout) findViewById(R.id.rl_create_task_start_time);//开始时间

        mTvTaskStartTime = (TextView) findViewById(R.id.tv_create_task_start_time);

        mIvadd.setImageResource(R.mipmap.left_jian_tou);
        mTvTitle.setText("发起任务");
        mTvSend.setVisibility(View.VISIBLE);
        initListener();

        EventBus.getDefault().register(this);
    }

    private void initListener() {
        mReTaskSend.setOnClickListener(this);
        mReTaskSendCopy.setOnClickListener(this);
        mReTaskEndTime.setOnClickListener(this);
        mTvSend.setOnClickListener(this);
        mIvadd.setOnClickListener(this);
        mReTaskStartTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_create_task_send) {
            Intent mRlMeetOrganizingIntent = new Intent(this, ConstantActivity.class);
            mRlMeetOrganizingIntent.putExtra(ConstantS.ISEDIT, true);
            mRlMeetOrganizingIntent.putExtra(ConstantS.ISSINGLE, false);
            mRlMeetOrganizingIntent.putExtra(ConstantS.TYPE, "1");
            startActivity(mRlMeetOrganizingIntent);
        } else if (i == R.id.rl_create_task_send_copy) {
            Intent mRlMeetOrganizingIntent = new Intent(this, ConstantActivity.class);
            mRlMeetOrganizingIntent.putExtra(ConstantS.ISEDIT, true);
            mRlMeetOrganizingIntent.putExtra(ConstantS.ISSINGLE, false);
            mRlMeetOrganizingIntent.putExtra(ConstantS.TYPE, "2");
            startActivity(mRlMeetOrganizingIntent);
        } else if (i == R.id.rl_create_task_end_time) {

            ChangeTime changeTime = new ChangeTime(this, "", 0, mTvTaskEndTime);
            changeTime.showSheet();


        } else if (i == R.id.tv_work_send) {
            //发送任务
            //TaskDesc：任务描述；DueDate：结束时间；
            //CreateUserId：创建人（当前登录用户）；
            //TenantId：租户ID（登录返回的）http://192.168.2.253:6001/api/CMP/ApiRegistrator/PostApiGateWay
            //CurrentOperateUserId：当前操作人当前登录用户）
            validator.validate();


        } else if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.rl_create_task_start_time) {
            ChangeTime changeTime = new ChangeTime(this, "", 0, mTvTaskStartTime);
            changeTime.showSheet();

        }
    }

    //发送任务
    private void SendTask() {
        long l = TimeUtil.parseTime(mTvTaskEndTime.getText().toString());
        String time = TimeUtil.getTime(l);
        long l1 = TimeUtil.parseTime(mTvTaskStartTime.getText().toString());
        String time1 = TimeUtil.getTime(l1);
        CreateTaskJsonBean createTaskJsonBean = new CreateTaskJsonBean();

        CreateTaskJsonBean.RowsBean rowsBean = new CreateTaskJsonBean.RowsBean();

        CreateTaskJsonBean.RowsBean.ListBean.InsertedBean insertedBean =
                new CreateTaskJsonBean.RowsBean.ListBean.InsertedBean();
        insertedBean.setCreateUserId(userId);
        insertedBean.setDueDate(time);
        insertedBean.setTaskDesc(mEditContent.getText().toString().trim());
        insertedBean.setTenantId(TenantId);
        insertedBean.setCreateDate(time1);
        insertedBean.setCurrentOperateUserId(userId);
        List<CreateTaskJsonBean.RowsBean.ListBean.InsertedBean> list = new ArrayList<>();
        list.add(insertedBean);
        rowsBean.setList(new CreateTaskJsonBean.RowsBean.ListBean(list));
        createTaskJsonBean.setApiCode(ConstantS.EDITTASK);
        createTaskJsonBean.setAppCode(ConstantS.CEOASSIST);
        createTaskJsonBean.setUser(new CreateTaskJsonBean.UserBean(CCUserId, ToDoUserId));
        createTaskJsonBean.setRows(rowsBean);
        String json = GsonUtil.getGson().toJson(createTaskJsonBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

        RetrofitClients.getInstance(this, ip).create(WorkAPi.class)
                .CreateTask(body)
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
                            Toasty.INSTANCE.showToast(CreateTaskActivity.this, entity.getMessage());
                        }
                    }
                });
    }


    @TargetApi(Build.VERSION_CODES.O)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void TaskEvent(ConstantDataEvent event) {

        String type = SharedPreferencesTool.getMStool(CreateTaskActivity.this).getString("type");
        String datas = SharedPreferencesTool.getMStool(CreateTaskActivity.this).getString(ConstantS.CONSTANTDATA);

        if ("1".equals(type)) {
            //执行人
            Type mType = new TypeToken<List<TypeBean>>() {
            }.getType();
            List<TypeBean> ListBeans = GsonUtil.getGson().fromJson(datas, mType);
            mTvTaskSend.setText(ListBeans.get(0).getName() + "等" + ListBeans.size() + "人");

            StringBuilder csvBuilder = new StringBuilder();
            for (TypeBean bean : ListBeans) {
                csvBuilder.append(bean.getId());
                csvBuilder.append(SEPARATOR);
            }
            String csv = csvBuilder.toString();
            ToDoUserId = csv.substring(0, csv.length() - SEPARATOR.length());

        } else if ("2".equals(type)) {
            //抄送人
            Type mType = new TypeToken<List<TypeBean>>() {
            }.getType();
            List<TypeBean> ListBeans = GsonUtil.getGson().fromJson(datas, mType);
            StringBuilder csvBuilder = new StringBuilder();
            for (TypeBean bean : ListBeans) {
                csvBuilder.append(bean.getId());
                csvBuilder.append(SEPARATOR);
            }
            String csv = csvBuilder.toString();
            CCUserId = csv.substring(0, csv.length() - SEPARATOR.length());

            Logger.d(CCUserId);
            mTvTaskSendCopy.setText(ListBeans.size() + "人");
        }
    }

    @Override
    public void onValidationSucceeded() {

        SendTask();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        Toasty.INSTANCE.showToast(this, "内容不能为空");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
