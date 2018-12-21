package com.shuben.zhixing.www.activity;

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
 * create at 2018/12/21 下午3:46
 * 关于我们
 */

public class AboutUsActivity extends BaseActvity {

    @BindView(R.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R.id.rl_new_version)
    RelativeLayout rlNewVersion;
    @BindView(R.id.btn_check_new_version)
    Button btnCheckNewVersion;
    @BindView(R.id.rl_check_new_version)
    RelativeLayout rlCheckNewVersion;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        tvWorkTitle.setText("关于");
        tvWorkSend.setVisibility(View.GONE);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.red)
                .radius(3)
                .into(btnCheckNewVersion);
    }



    @OnClick({R.id.iv_work_add_work, R.id.rl_new_version, R.id.rl_check_new_version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_add_work:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.rl_new_version:
                break;
            case R.id.rl_check_new_version:
                break;
        }
    }
}
