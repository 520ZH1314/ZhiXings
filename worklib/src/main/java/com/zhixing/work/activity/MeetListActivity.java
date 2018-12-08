package com.zhixing.work.activity;

import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.work.R;
import com.zhixing.work.bean.MeetStatusType;
import com.zhixing.work.bean.PostTaskListJsonBean;
import com.zhixing.work.bean.TaskListItemEntity;
import com.zhixing.work.http.base.RetrofitClients;
import com.zhixing.work.http.base.RxUtils;
import com.zhixing.work.http.httpapi.WorkAPi;
import com.zhixing.work.ui.MeetStatusTypeDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

/**
 *会议列表
 *@author zjq
 *create at 2018/11/30 下午2:05
 */
public class MeetListActivity extends BaseActvity implements View.OnClickListener,MeetStatusTypeDialog.OnDialogInforCompleted {

    private TextView mTvMeetStatusType;
    private RelativeLayout mRelativeLayout;
    private ImageView mIvadd;
    private TextView mTvTitle;
    private TextView mTvSend;
    private String AppCode="CEOAssist";
    private String ApiCode="GetUnfinished";//默认未完成
    private String userId;
    private String tenantId;
    private TextView mTvTaskTitle;
    private TextView mTvTaskCreateTime;
    private TextView mTvTaskContent;
    private TextView mTvTaskOpenTime;
    private TextView mTvTaskAddress;
    private TextView mTvTaskOriginator;
    private TextView mTvTaskStatus;
    private TextView mTvTaskDynamic;
    private CheckBox mCheckBox;


    @Override
    public int getLayoutId() {
        return R.layout.activity_meet_list;
    }



    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {

         userId = SharedPreferencesTool.getMStool(this).getUserId();
         tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        mTvMeetStatusType=(TextView) findViewById(R.id.tv_meet_type);
        mRelativeLayout=(RelativeLayout) findViewById(R.id.rl_meet_type);
        mIvadd=(ImageView) findViewById(R.id.iv_work_add_work);
        mTvTitle=(TextView) findViewById(R.id.tv_work_title);
        mTvSend=(TextView) findViewById(R.id.tv_work_send);


        mIvadd.setImageResource(R.mipmap.left_jian_tou);
        mTvTitle.setText("会议");
        mTvSend.setVisibility(View.GONE);
        mRelativeLayout.setOnClickListener(this);

//        initData();
    }

    private void initData() {
        //初始化数据

        PostTaskListJsonBean jsonBean=new PostTaskListJsonBean();
        jsonBean.setApiCode(ApiCode);
        jsonBean.setAppCode(AppCode);
        jsonBean.setTenantId(tenantId);
        jsonBean.setToDoUserId(userId);

        String json = GsonUtil.getGson().toJson(jsonBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);


//        setMeetListData(body);



    }

//    private void setMeetListData(RequestBody body) {
//        RetrofitClients.getInstance(this).create(WorkAPi.class)
//                .getTaskList(body)
//                .compose(RxUtils.schedulersTransformer())  // 线程调度
//                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        showDialog("加载中");
//                    }
//                })
//                .subscribe(new Consumer<TaskListItemEntity>() {
//                    @Override
//                    public void accept(TaskListItemEntity entity) throws Exception {
//                        dismissDialog();
//
//                        List<TaskListItemEntity.RowsBean> rows = entity.getRows();
//                         adapter=new MeetListAdapter(R.layout.item_task_message,rows);
//                    }
//                });
//
//                 mRecyMeetList.setAdapter(adapter);
//
//
//
//    }


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





    //弹窗返回数据
    @Override
    public void dialogInforCompleted(String name) {
        mTvMeetStatusType.setText(name);




    }



//    //adapt
//    public class MeetListAdapter extends BaseQuickAdapter<TaskListItemEntity.RowsBean, BaseViewHolder> {
//        public MeetListAdapter(int layoutResId, List<TaskListItemEntity.RowsBean> data) {
//            super(layoutResId, data);
//        }
//
//
//        @Override
//        protected void convert(BaseViewHolder baseViewHolder, TaskListItemEntity.RowsBean menuItem) {
//
//            //        mTvTaskTitle=(TextView) findViewById();
////        mTvTaskCreateTime=(TextView) findViewById(R.id.tv_item_task_message_time);//任务创建事件
////        mTvTaskContent=(TextView) findViewById(R.id.tv_task_message_content);//任务内容
////        mTvTaskOpenTime=(TextView) findViewById(R.id.tv_item_task_message_open_time);//任务开始时间
////        mTvTaskAddress=(TextView) findViewById(R.id.tv_item_task_message_address);//任务地址
////        mTvTaskOriginator=(TextView) findViewById(R.id.tv_item_task_message_originator);//任务发起人
////        mTvTaskStatus=(TextView) findViewById(R.id.tv_item_task_message_status);//任务状态
////        mTvTaskDynamic=(TextView) findViewById(R.id.tv_item_task_message_dynamic);//任务动态
////        mCheckBox=(CheckBox) findViewById(R.id.item_work_task_check);
//
//            baseViewHolder.setText(R.id.tv_meet_title, menuItem.getTitle());//任务标题
//            baseViewHolder.setText(R.id.tv_item_task_message_time, menuItem.getCreateDate());//任务创建时间
//            baseViewHolder.setText(R.id.tv_task_message_content, menuItem.getContents());//任务内容
//            baseViewHolder.setText(R.id.tv_item_task_message_open_time, menuItem.getDueDate());//任务开始时间
//            baseViewHolder.setText(R.id.tv_item_task_message_originator, menuItem.getToDoUserName());//任务责任人
//            baseViewHolder.setText(R.id.tv_item_task_message_status, menuItem.getTaskStatusName());//任务状态
//
//
//
//
//
//
//        }
//    }
//



}
