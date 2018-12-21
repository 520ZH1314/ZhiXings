package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
 * create at 2018/12/21 下午2:49 隐私界面
 */
public class SecretActivity extends BaseActvity {


    @BindView(R.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R.id.rl_contact_secret)
    RelativeLayout rlContactSecret;
    @BindView(R.id.sb_message_notification_sound)
    SwitchButton sbMessageNotificationSound;

    @Override
    public int getLayoutId() {
        return R.layout.activity_secret;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        setStatus(-1);
        tvWorkTitle.setText("隐私");
        tvWorkSend.setVisibility(View.GONE);
        ivWorkAddWork.setImageResource(R.mipmap.back);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_work_add_work, R.id.rl_contact_secret, R.id.sb_message_notification_sound})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_add_work:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.rl_contact_secret:
                startActivity(ContactSecretSettingActivity.class);
                break;
            case R.id.sb_message_notification_sound:
                break;
        }
    }
}
