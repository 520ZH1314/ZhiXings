package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zjq
 * create at 2018/12/21 下午2:56
 * 通讯录隐私界面
 */
public class ContactSecretSettingActivity extends BaseActvity {


    @BindView(R.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R.id.cb_public)
    CheckBox cbPublic;
    @BindView(R.id.cb_private)
    CheckBox cbPrivate;
    @BindView(R.id.cb_protect)
    CheckBox cbProtect;
    @BindView(R.id.cb_protect_little)
    CheckBox cbProtectLittle;
    @BindView(R.id.cl_select_look)
    ConstraintLayout clSelectLook;
    @BindView(R.id.cb_protect_little_no_look)
    CheckBox cbProtectLittleNoLook;
    @BindView(R.id.cl_select_no_look)
    ConstraintLayout clSelectNoLook;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact_secret_setting;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        setStatus(-1);
        tvWorkTitle.setText("通讯录隐私设置");
        tvWorkSend.setVisibility(View.GONE);
        ivWorkAddWork.setImageResource(R.mipmap.back);
    }



    @OnClick({R.id.iv_work_add_work, R.id.cb_public, R.id.cb_private, R.id.cb_protect, R.id.cb_protect_little, R.id.cl_select_look, R.id.cb_protect_little_no_look, R.id.cl_select_no_look})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_add_work:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.cb_public:
                break;
            case R.id.cb_private:
                break;
            case R.id.cb_protect:
                break;
            case R.id.cb_protect_little:
                break;
            case R.id.cl_select_look:
                break;
            case R.id.cb_protect_little_no_look:
                break;
            case R.id.cl_select_no_look:
                break;
        }
    }
}
