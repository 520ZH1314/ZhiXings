package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zjq
 * create at 2018/12/21 下午2:26 新消息通知
 */
public class NewMessageNotificationActivity extends BaseActvity {


    @BindView(R.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R.id.sb_message_notification)
    SwitchButton sbMessageNotification;
    @BindView(R.id.sb_message_notification_sound)
    SwitchButton sbMessageNotificationSound;
    @BindView(R.id.sb_message_notification_shock)
    SwitchButton sbMessageNotificationShock;
    @BindView(R.id.sb_message_notification_do_not_disturb)
    SwitchButton sbMessageNotificationDoNotDisturb;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_message_notification;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        setStatus(-1);
        tvWorkTitle.setText("新消息通知");
        tvWorkSend.setVisibility(View.GONE);
        ivWorkAddWork.setImageResource(R.mipmap.back);
    }



    @OnClick({R.id.iv_work_add_work, R.id.sb_message_notification, R.id.sb_message_notification_sound, R.id.sb_message_notification_shock, R.id.sb_message_notification_do_not_disturb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_add_work:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.sb_message_notification:
                break;
            case R.id.sb_message_notification_sound:
                break;
            case R.id.sb_message_notification_shock:
                break;
            case R.id.sb_message_notification_do_not_disturb:
                break;
        }
    }
}
