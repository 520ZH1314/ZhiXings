package com.zhixing.work.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.work.R;
import com.zhixing.work.bean.DeleteTaskEvent;
import com.zhixing.work.bean.MeetStatusType;
import com.zhixing.work.bean.PostTaskCreateJsonBean;
import com.zhixing.work.bean.PostTaskListJsonBean;
import com.zhixing.work.bean.TaskListItemEntity;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;
import com.zhixing.work.ui.MeetStatusTypeDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

/**
 *任务列表
 *@author zjq
 *create at 2018/11/30 下午3:49
 */
public class TaskListActivity extends BaseActvity implements View.OnClickListener, MeetStatusTypeDialog.OnDialogInforCompleted {

    private TextView mTvTaskStatusType;
    private RelativeLayout mRelativeLayout;
    private ImageView mIvadd;
    private TextView mTvTitle;
    private TextView mTvSend;
    private RecyclerView mRecyTaskList;

    private String AppCode = "CEOAssist";

    private String ApiCode = "GetUnfinished";

    private String TenantId;
    private String userId;
    private String tenantId;
    private TaskListAdapter adapter;
    private RequestBody body;


    @Override
    public int getLayoutId() {
        return R.layout.activity_task_list;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
       initView();
    }

    private void initView() {
        userId = SharedPreferencesTool.getMStool(this).getUserId();
        tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        mTvTaskStatusType=(TextView) findViewById(R.id.tv_meet_type);
        mRelativeLayout=(RelativeLayout) findViewById(R.id.rl_meet_type);
        mIvadd=(ImageView) findViewById(R.id.iv_work_add_work);
        mTvTitle=(TextView) findViewById(R.id.tv_work_title);
        mTvSend=(TextView) findViewById(R.id.tv_work_send);

        mIvadd.setImageResource(R.mipmap.left_jian_tou);
        mTvTitle.setText("任务");
        mTvSend.setVisibility(View.GONE);
        mRelativeLayout.setOnClickListener(this);
        mRecyTaskList=(RecyclerView) findViewById(R.id.recy_task_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(TaskListActivity.this);
        mRecyTaskList.setLayoutManager(layoutManager);
        EventBus.getDefault().register(this);

        initData();
    }

    private void initData() {
        PostTaskListJsonBean jsonBean=new PostTaskListJsonBean();
        jsonBean.setApiCode(ApiCode);
        jsonBean.setAppCode(AppCode);
        jsonBean.setTenantId(tenantId);
        jsonBean.setToDoUserId(userId);
         String json = GsonUtil.getGson().toJson(jsonBean);
         body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        setTaskListData(body);


    }


    private  void  initMyCreateData(){
        PostTaskCreateJsonBean  jsonBean=new PostTaskCreateJsonBean();
        jsonBean.setApiCode(ApiCode);
        jsonBean.setAppCode(AppCode);
        jsonBean.setTenantId(tenantId);
        jsonBean.setCreateUserId(userId);
        String json = GsonUtil.getGson().toJson(jsonBean);
        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        setTaskListData(body);
    };



    private void setTaskListData(RequestBody body) {
        RetrofitClients.getInstance(this).create(WorkAPi.class)
                .getTaskList(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中");
                    }
                })
                .subscribe(new Consumer<TaskListItemEntity>() {
                    @Override
                    public void accept(TaskListItemEntity entity) throws Exception {
                        dismissDialog();
                        final List<TaskListItemEntity.RowsBean> rows = entity.getRows();
                         if (adapter!=null){
                              adapter.replaceData(rows);
                              adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                  @Override
                                  public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                      String taskId = rows.get(position).getSourceId();

                                      Intent intent =new Intent(TaskListActivity.this,WorkTaskDetailActivity.class);
                                      intent.putExtra("TaskId",taskId);
                                      startActivity(intent);

                                  }
                              });


                       }else{
                           adapter=new TaskListAdapter(R.layout.item_task_message,rows);
                             adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                     String taskId = rows.get(position).getSourceId();
                                     Intent intent =new Intent(TaskListActivity.this,WorkTaskDetailActivity.class);
                                     intent.putExtra("TaskId",taskId);
                                     intent.putExtra("CreateTaskName",rows.get(position).getCreateUserName());
                                     startActivity(intent);

                                 }
                             });
                           mRecyTaskList.setAdapter(adapter);

                       }

                    }
                });


    }




    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.rl_meet_type){
            List<MeetStatusType> data=new ArrayList<>();
            data.add(new MeetStatusType("未结束的"));
            data.add(new MeetStatusType( "已完成"));
            data.add(new MeetStatusType( "已取消"));
            data.add(new MeetStatusType("我的责任"));
            data.add(new MeetStatusType("我的参与"));
            data.add(new MeetStatusType("我的发起"));


            String json = GsonUtil.getGson().toJson(data);
            MeetStatusTypeDialog dialog = MeetStatusTypeDialog.newInstance(json);
            dialog.setOnDialogInforCompleted(this);
            dialog.show(getSupportFragmentManager(),"MeetStatusTypeDialog");
        }
    }




    @Override
    public void dialogInforCompleted(String name) {
        mTvTaskStatusType.setText(name);
        switch (name) {
            case "未结束的":
                ApiCode = "GetUnfinished";
                initData();
                break;
            case "已完成":
                ApiCode = "GetCompleted";
                initData();
                break;
            case "已取消":
                ApiCode = "GetUnfinished";
                initData();
                break;
            case "我的发起":
                ApiCode = "GetLaunch";
                initMyCreateData();
                break;
            case "我的责任":
                ApiCode = "GetDuty";
                initData();
                break;
            case "我的参与":
                ApiCode = "GetPartake";
                initData();
                break;
        }
        }

    public class TaskListAdapter extends BaseQuickAdapter<TaskListItemEntity.RowsBean, BaseViewHolder> {
        public TaskListAdapter(int layoutResId, List<TaskListItemEntity.RowsBean> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(BaseViewHolder baseViewHolder, TaskListItemEntity.RowsBean menuItem) {
            baseViewHolder.setText(R.id.tv_meet_title, menuItem.getTitle());//任务标题
            baseViewHolder.setText(R.id.tv_item_task_message_time, menuItem.getCreateDate());//任务创建时间
            baseViewHolder.setText(R.id.tv_task_message_content, menuItem.getContents());//任务内容
            baseViewHolder.setText(R.id.tv_item_task_message_open_time, menuItem.getDueDate());//任务开始时间
            baseViewHolder.setText(R.id.tv_item_task_message_originator, menuItem.getCreateUserName());//任务责任人
            baseViewHolder.setText(R.id.tv_item_task_message_status, menuItem.getTaskStatusName());//任务状态
            baseViewHolder.setText(R.id.tv_item_task_message_dynamic,menuItem.getCount()+"");//任务动态


        }
    }


    //
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Event(DeleteTaskEvent event){
        if (event.isDelete()){
            initData();
        }
        DeleteTaskEvent stickyEvent = EventBus.getDefault().getStickyEvent(DeleteTaskEvent.class);
        if (stickyEvent!=null){
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


}
