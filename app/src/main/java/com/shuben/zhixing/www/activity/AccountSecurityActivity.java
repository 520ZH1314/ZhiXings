package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.ACache;
import com.shuben.zhixing.www.R;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author zjq
 * create at 2018/12/21 下午2:03
 * 账号安全
 */
public class AccountSecurityActivity extends BaseActvity {


    @BindView(R.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R.id.tv_account_security_phone)
    TextView tvAccountSecurityPhone;
    @BindView(R.id.sb_account_secuity)
    SwitchButton sbAccountSecuity;
    private ACache aCache;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_security;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        setStatus(-1);
        aCache= ACache.get(this);
        String userPhone = aCache.getAsString("UserPhone");
        tvAccountSecurityPhone.setText(userPhone);
        tvWorkTitle.setText("账号安全");
        tvWorkSend.setVisibility(View.GONE);
        ivWorkAddWork.setImageResource(R.mipmap.back);
    }



    @OnClick({R.id.iv_work_add_work, R.id.sb_account_secuity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_add_work:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.sb_account_secuity:
                break;
        }
    }
}
