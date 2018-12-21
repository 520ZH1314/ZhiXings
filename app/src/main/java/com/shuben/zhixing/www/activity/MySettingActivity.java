package com.shuben.zhixing.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.shuben.zhixing.www.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zjq
 * create at 2018/12/21 上午10:12
 * 个人中心设置界面
 */

public class MySettingActivity extends BaseActvity {


    @BindView(R.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R.id.rl_my_info)
    RelativeLayout rlMyInfo;
    @BindView(R.id.rl_business_card)
    RelativeLayout rlBusinessCard;
    @BindView(R.id.rl_account_security)
    RelativeLayout rlAccountSecurity;
    @BindView(R.id.rl_new_message)
    RelativeLayout rlNewMessage;
    @BindView(R.id.rl_privacy)
    RelativeLayout rlPrivacy;
    @BindView(R.id.rl_routine)
    RelativeLayout rlRoutine;
    @BindView(R.id.rl_company)
    RelativeLayout rlCompany;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.btn_un_login)
    Button btnUnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_setting;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        setStatus(-1);
        tvWorkTitle.setText("设置");
        tvWorkSend.setVisibility(View.GONE);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.red)
                .radius(3)
                .into(btnUnLogin);

    }




    @OnClick({R.id.iv_work_add_work, R.id.rl_my_info, R.id.rl_business_card, R.id.rl_account_security, R.id.rl_new_message, R.id.rl_privacy, R.id.rl_routine, R.id.rl_company, R.id.rl_about, R.id.btn_un_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_add_work:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.rl_my_info:
                startActivity(MyInfoDetailActivity.class);
                break;
            case R.id.rl_business_card:
                startActivity(OutSideBusinessCardActivity.class);
                break;
            case R.id.rl_account_security:
                startActivity(AccountSecurityActivity.class);
                break;
            case R.id.rl_new_message:
                startActivity(NewMessageNotificationActivity.class);
                break;
            case R.id.rl_privacy:
                startActivity(SecretActivity.class);
                break;
            case R.id.rl_routine:
                startActivity(RoutineActivity.class);
                break;
            case R.id.rl_company:
                startActivity(SettingComActivity.class);
                break;
            case R.id.rl_about:
                startActivity(AboutUsActivity.class);
                break;
            case R.id.btn_un_login:
                break;
        }
    }
}
