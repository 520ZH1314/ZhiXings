package com.zhixing.work.activity;

import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.zhixing.work.R;
import com.zhixing.work.bean.CreateTaskEntity;
import com.zhixing.work.bean.DeleteTaskEvent;
import com.zhixing.work.bean.PostTaskDetailJson;
import com.zhixing.work.bean.PostTaskReplyJson;
import com.zhixing.work.bean.TaskDetailEntity;
import com.zhixing.work.bean.TaskListItemEntity;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WorkTaskDetailActivity extends BaseActvity implements View.OnClickListener {
    private String AppCode = "CEOAssist";
    private String ApiCode = "GetTaskDetails";
    private String TaskId;
    private String tenantId;
    private RequestBody body;
    private TextView Tv_work_title;
    private TextView mTv_task_detail_content;
    private TextView mTv_task_crate_people;
    private TextView mTv_task_detail_compete_time;
    private TextView mTv_task_detail_create_time;
    private TextView mTv_task_detail_do_people;
    private TextView mTv_task_detail_status;
    private TextView mTv_task_details_status;
    private TextView mTv_task_detail_do_people_copy;
    private Button mBtn_Diss;
    private Button mBtn_Send;
    private ImageView mIv_addMore;
    private EditText mEdit;
    private ConstraintLayout mConstranint;
    private boolean IsSelect = false;
    private String CommentSourceID;//taskid
    private String CommentUserID;//当前用户id
    private String CreateUserID;//当前用户id
    private String ToUserID;//创建任务的id
    private String EnclosureUrl;//附件
    private String TenantId;//
    private RequestBody replybody;
    private RecyclerView mRecyleView;
    private CheckBox mCheckBox;
    private Button mBtnDiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tasks_detail;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        initView();
        initData();
    }

    private void initData() {

        PostTaskDetailJson jsonBean = new PostTaskDetailJson();
        jsonBean.setApiCode(ApiCode);
        jsonBean.setAppCode(AppCode);
        jsonBean.setTenantId(tenantId);
        jsonBean.setTaskId(TaskId);
        String json = GsonUtil.getGson().toJson(jsonBean);
        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

        setTaskDetailData(body);

    }

    private void setTaskDetailData(RequestBody body) {

        RetrofitClients.getInstance(this).create(WorkAPi.class)
                .getTaskDetail(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中");
                    }
                })
                .subscribe(new Consumer<TaskDetailEntity>() {
                    @Override
                    public void accept(TaskDetailEntity entity) throws Exception {
                        dismissDialog();
                        if (entity.getCurrentStep()==15||entity.getCurrentStep()==20){
                            //已经完成或者取消
                            mCheckBox.setChecked(true);
                            mCheckBox.setClickable(false);
                            mTv_task_details_status.setVisibility(View.VISIBLE);
                            mBtnDiss.setVisibility(View.GONE);
                        }else{
                            mCheckBox.setChecked(false);
                            mCheckBox.setClickable(true);
                            mTv_task_details_status.setVisibility(View.GONE);
                            mBtnDiss.setVisibility(View.VISIBLE);
                        }






                        mTv_task_detail_content.setText(entity.getTaskDesc());//任务描述
                        String[] createtime = entity.getCreateDate().split("T");
                        mTv_task_detail_create_time.setText(createtime[0].toString() + " " + createtime[1].toString()); //创建时间
                        String[] compete_time = entity.getDueDate().split("T");
                        mTv_task_detail_compete_time.setText(compete_time[0].toString() + " " + compete_time[1].toString()); //截止时间
                        mTv_task_crate_people.setText(entity.getCreateUserName());
                        String toDoUserName = entity.getToDoUserName();//执行人name
                        String[] str1 = toDoUserName.split(",");
                        mTv_task_detail_do_people.setText(str1[0].toString() + "等" + str1.length + "人");
                        String ccUserName = entity.getCcUserName();//抄送人name
                        String[] str2 = ccUserName.split(",");
                        mTv_task_detail_do_people_copy.setText(str2[0].toString() + "等" + str2.length + "人");

                        SharedPreferencesTool.getMStool(WorkTaskDetailActivity.this).setString("createId", entity.getCreateUserId());
                        TaskDetailEntity.CommentListBean commentList = entity.getCommentList();//回复消息列表
                        List<TaskDetailEntity.CommentListBean.RowsBean> rows = commentList.getRows();
                        TaskDetailListAdapter adapter = new TaskDetailListAdapter(R.layout.item_task_detail_recyle, rows);
                           mRecyleView.setAdapter(adapter);
                    }
                });

        //CommonTips tips = new CommonTips(getActivity(),getHandler());
        //                tips.init("取消","确定","是否退出账户");
        //                tips.setI(new Tips() {
        //                    @Override
        //                    public void cancel() {
        //
        //                    }
        //
        //                    @Override
        //                    public void sure() {
        //                        ChatSdk.close();
        //                        SharedPreferencesTool.getMStool(getActivity()).clear("UserId");
        //                        Intent intent=new Intent();
        //                        intent.setClass(context, LoginActivity.class);
        //                        startActivity(intent);
        //                        getActivity().finish();
        //                    }
        //                });
        //                tips.showSheet();

    }

    private void initView() {
        TaskId = getIntent().getStringExtra("TaskId");
        tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        Tv_work_title = (TextView) findViewById(R.id.tv_work_title);
        Tv_work_title.setText("任务详情");
        mTv_task_detail_content = (TextView) findViewById(R.id.tv_task_detail_content);
        mTv_task_crate_people = (TextView) findViewById(R.id.tv_task_crate_people);
        mTv_task_detail_create_time = (TextView) findViewById(R.id.tv_task_detail_create_time);
        mTv_task_detail_compete_time = (TextView) findViewById(R.id.tv_task_detail_compete_time);
        mTv_task_detail_do_people = (TextView) findViewById(R.id.tv_task_detail_do_people);
        mTv_task_detail_status = (TextView) findViewById(R.id.tv_task_detail_status);
        mTv_task_detail_do_people_copy = (TextView) findViewById(R.id.tv_task_detail_do_people_copy);
        mTv_task_details_status = (TextView) findViewById(R.id.tv_task_details_status);
        mRecyleView = (RecyclerView) findViewById(R.id.recy_task_detail);
        mCheckBox=(CheckBox) findViewById(R.id.check_task_detail);
        mBtnDiss=(Button) findViewById(R.id.btn_task_detail_diss);
        LinearLayoutManager layoutManager = new LinearLayoutManager(WorkTaskDetailActivity.this);
        mRecyleView.setLayoutManager(layoutManager);

        //发送btn
        mBtn_Send = (Button) findViewById(R.id.btn_task_detail_send);
        //iv
        mIv_addMore = (ImageView) findViewById(R.id.iv_meet_detail_add_message);

        //btn 取消
        mBtn_Diss = (Button) findViewById(R.id.btn_task_detail_diss);

        //输入回复
        mEdit = (EditText) findViewById(R.id.ed_task_detail);

        //文件,图片
        mConstranint = (ConstraintLayout) findViewById(R.id.cl_buttom);

        initListner();
    }

    private void initListner() {
        mBtn_Diss.setOnClickListener(this);
        mBtn_Send.setOnClickListener(this);
        mIv_addMore.setOnClickListener(this);
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int a;
                if (s.length() != 0) {
                    mIv_addMore.setVisibility(View.GONE);
                    mBtn_Send.setVisibility(View.VISIBLE);
                } else {
                    mIv_addMore.setVisibility(View.VISIBLE);
                    mBtn_Send.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_task_detail_send)
        {

            PostTaskReplyJson postTaskReplyJson = new PostTaskReplyJson();
            postTaskReplyJson.setApiCode("EditComment");
            postTaskReplyJson.setAppCode("CEOAssist");
            PostTaskReplyJson.RowsBean rowsBean = new PostTaskReplyJson.RowsBean();
            PostTaskReplyJson.RowsBean.ListBean ListBean = new PostTaskReplyJson.RowsBean.ListBean();
            List<PostTaskReplyJson.RowsBean.ListBean.InsertedBean> listBeans = new ArrayList<>();
            PostTaskReplyJson.RowsBean.ListBean.InsertedBean bean = new PostTaskReplyJson.RowsBean.ListBean.InsertedBean();
            bean.setCommentSourceID(TaskId);
            bean.setCommentText(mEdit.getText().toString().trim());
            bean.setCommentUserID(SharedPreferencesTool.getMStool(this).getUserId());
            bean.setCreateUserID(SharedPreferencesTool.getMStool(this).getUserId());
            bean.setTenantId(tenantId);
            bean.setToUserID(SharedPreferencesTool.getMStool(this).getString("createId"));
            listBeans.add(bean);
            ListBean.setInserted(listBeans);
            rowsBean.setList(ListBean);
            postTaskReplyJson.setRows(rowsBean);

            String json = GsonUtil.getGson().toJson(postTaskReplyJson);
            replybody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

            RetrofitClients.getInstance(this).create(WorkAPi.class)
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
                            if (entity.isStatus()){
                                //保存成功
                                initData();//重新刷新下数据
                            }


                        }
                    });



                        }else if (id==R.id.btn_task_detail_diss){


                     closeTask();

        }



    }

    private void closeTask() {
        PostTaskDetailJson jsonBean = new PostTaskDetailJson();
        jsonBean.setApiCode("EditCompletedTask");
        jsonBean.setAppCode(AppCode);
        jsonBean.setTenantId(tenantId);
        jsonBean.setTaskId(TaskId);
        String json = GsonUtil.getGson().toJson(jsonBean);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        RetrofitClients.getInstance(this).create(WorkAPi.class)
                .getTaskDetail(requestBody)
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
                          if (entity.isStatus()){
                              AppManager.getAppManager().finishActivity();
                              //发条消息通知外面刷新下界面
                              EventBus.getDefault().postSticky(new DeleteTaskEvent(true));

                          }

                    }
                });



    }

    public class TaskDetailListAdapter extends BaseQuickAdapter<TaskDetailEntity.CommentListBean.RowsBean, BaseViewHolder> {
        public TaskDetailListAdapter(int layoutResId, List<TaskDetailEntity.CommentListBean.RowsBean> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(BaseViewHolder baseViewHolder, TaskDetailEntity.CommentListBean.RowsBean menuItem) {
            baseViewHolder.setText(R.id.tv_task_detail_reply_name, menuItem.getCommentUserName());//名字
            baseViewHolder.setText(R.id.tv_task_detail_reply_time, menuItem.getCommentDate());//时间
            baseViewHolder.setText(R.id.tv_task_detail_reply_content, menuItem.getCommentText());



        }
    }

}
