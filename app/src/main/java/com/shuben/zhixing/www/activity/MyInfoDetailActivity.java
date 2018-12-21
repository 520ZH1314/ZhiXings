package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class MyInfoDetailActivity extends BaseActvity {

    @BindView(R.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.iv_my_info_detail_head)
    CircularImage ivMyInfoDetailHead;
    @BindView(R.id.tv_my_detail_name)
    TextView tvMyDetailName;
    @BindView(R.id.tv_my_info_detail_job)
    TextView tvMyInfoDetailJob;
    @BindView(R.id.tv_my_info_detail_depart_name)
    TextView tvMyInfoDetailDepartName;
    @BindView(R.id.tv_my_info_detail_phone_number)
    TextView tvMyInfoDetailPhoneNumber;
    @BindView(R.id.tv_my_info_detail_teliphone)
    TextView tvMyInfoDetailTeliphone;
    @BindView(R.id.tv_my_info_detail_email)
    TextView tvMyInfoDetailEmail;
    @BindView(R.id.tv_my_info_detail_wechat)
    TextView tvMyInfoDetailWechat;
    @BindView(R.id.tv_my_info_detail_borthday)
    TextView tvMyInfoDetailBorthday;
    @BindView(R.id.tv_my_info_detail_sub_departments)
    TextView tvMyInfoDetailSubDepartments;
    @BindView(R.id.tv_my_info_detail_sub_superior)
    TextView tvMyInfoDetailSubSuperior;
    @BindView(R.id.tv_my_info_detail_employee_number)
    TextView tvMyInfoDetailEmployeeNumber;
    private ACache aCache;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_info_detail;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        setStatus(-1);
         aCache= ACache.get(this);
        tvWorkTitle.setText("个人资料");
        tvWorkSend.setVisibility(View.VISIBLE);
        tvWorkSend.setText("编辑");
        ivWorkAddWork.setImageResource(R.mipmap.back);
        initData();
    }

    private void initData() {
        String ip = SharedPreferencesTool.getMStool(this).getIp();
        String app_myInfo = aCache.getAsString("App_MyInfo");
        UserData userData = GsonUtil.getGson().fromJson(app_myInfo, UserData.class);
        ImageLoader.load(ip+userData.getPhotoURL(), ivMyInfoDetailHead, R.mipmap.person_icon);
        tvMyDetailName.setText(userData.getUserName());
        tvMyInfoDetailJob.setText(userData.getHeadShip());
        tvMyInfoDetailDepartName.setText(userData.getDeptName());
        tvMyInfoDetailPhoneNumber.setText(userData.getPhoneNumber());
        tvMyInfoDetailEmail.setText(userData.getEmail());

    }



    @OnClick({R.id.iv_work_add_work, R.id.tv_work_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_add_work:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_work_send:
                break;
        }
    }
}
