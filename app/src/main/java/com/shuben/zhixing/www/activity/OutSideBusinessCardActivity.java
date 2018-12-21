package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.widget.CircularImage;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.common.ImageLoader;
import com.shuben.zhixing.www.data.UserData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zjq
 * create at 2018/12/21 下午1:49 外部名片
 */
public class OutSideBusinessCardActivity extends BaseActvity {

    @BindView(R.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R.id.iv_my_info_businesscard_head)
    CircularImage ivMyInfoBusinesscardHead;
    @BindView(R.id.tv_my_info_businesscard_name)
    TextView tvMyInfoBusinesscardName;
    @BindView(R.id.tv_my_info_businesscard_job)
    TextView tvMyInfoBusinesscardJob;
    @BindView(R.id.tv_my_info_businesscard_depart_name)
    TextView tvMyInfoBusinesscardDepartName;
    @BindView(R.id.tv_my_info_businesscard_phone_number)
    TextView tvMyInfoBusinesscardPhoneNumber;
    @BindView(R.id.tv_my_info_businesscard_email)
    TextView tvMyInfoBusinesscardEmail;
    @BindView(R.id.tv_my_info_businesscard_job_ind)
    TextView tvMyInfoBusinesscardJobInd;
    private ACache aCache;

    @Override
    public int getLayoutId() {
        return R.layout.activity_out_side_business_card;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        aCache = ACache.get(this);
        tvWorkTitle.setText("外部名片");
        tvWorkSend.setVisibility(View.VISIBLE);
        tvWorkSend.setText("编辑");
        ivWorkAddWork.setImageResource(R.mipmap.back);
        initData();

    }

    private void initData() {
        String ip = SharedPreferencesTool.getMStool(this).getIp();

        String app_myInfo = aCache.getAsString("App_MyInfo");
        UserData userData = GsonUtil.getGson().fromJson(app_myInfo, UserData.class);
        ImageLoader.load(ip + userData.getPhotoURL(), ivMyInfoBusinesscardHead, R.mipmap.person_icon);
        tvMyInfoBusinesscardName.setText(userData.getUserName());
        tvMyInfoBusinesscardJob.setText(userData.getHeadShip());
        tvMyInfoBusinesscardDepartName.setText(userData.getDeptName());
        tvMyInfoBusinesscardPhoneNumber.setText(userData.getPhoneNumber());
        tvMyInfoBusinesscardEmail.setText(userData.getEmail());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_work_add_work)
    public void onViewClicked() {
        AppManager.getAppManager().finishActivity();
    }
}
