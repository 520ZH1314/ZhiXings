package com.zhixing.work.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.zhixing.work.R;
import com.zhixing.work.bean.MeetStatusType;
import com.zhixing.work.ui.MeetStatusTypeDialog;
import com.zhixing.work.ui.TaskStatusTypeDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 *任务列表
 *@author zjq
 *create at 2018/11/30 下午3:49
 */
public class TaskListActivity extends BaseActvity implements View.OnClickListener {

    private TextView mTvTaskStatusType;
    private RelativeLayout mRelativeLayout;
    private ImageView mIvadd;
    private TextView mTvTitle;
    private TextView mTvSend;

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
        mTvTaskStatusType=(TextView) findViewById(R.id.tv_meet_type);
        mRelativeLayout=(RelativeLayout) findViewById(R.id.rl_meet_type);
        mIvadd=(ImageView) findViewById(R.id.iv_work_add_work);
        mTvTitle=(TextView) findViewById(R.id.tv_work_title);
        mTvSend=(TextView) findViewById(R.id.tv_work_send);

        mIvadd.setImageResource(R.mipmap.left_jian_tou);
        mTvTitle.setText("任务");
        mTvSend.setVisibility(View.GONE);
        EventBus.getDefault().register(this);
        mRelativeLayout.setOnClickListener(this);
    }

    //弹窗返回的数据

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void DialogEvent(MeetStatusType meetStatusType){
        mTvTaskStatusType.setText(meetStatusType.getName());
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.rl_meet_type){
            TaskStatusTypeDialog dialog=new TaskStatusTypeDialog();
            dialog.show(getSupportFragmentManager(),"taskStatusTypeDialog");
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
