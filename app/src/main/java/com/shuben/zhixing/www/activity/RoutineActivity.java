package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zjq
 * create at 2018/12/21 下午3:38 常规 界面
 */
public class RoutineActivity extends BaseActvity {


    @BindView(R.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.rl_set_text_size)
    RelativeLayout rlSetTextSize;
    @BindView(R.id.rl_clear_cache)
    RelativeLayout rlClearCache;
    @BindView(R.id.sb_routine)
    SwitchButton sbRoutine;

    @Override
    public int getLayoutId() {
        return R.layout.activity_routine;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        setStatus(-1);
        tvWorkTitle.setText("常规");
        tvWorkSend.setVisibility(View.GONE);
        ivWorkAddWork.setImageResource(R.mipmap.back);
    }


    @OnClick({R.id.iv_work_add_work, R.id.relativeLayout, R.id.rl_set_text_size, R.id.rl_clear_cache, R.id.sb_routine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_work_add_work:
                break;
            case R.id.relativeLayout:
                break;
            case R.id.rl_set_text_size:
                break;
            case R.id.rl_clear_cache:
                break;
            case R.id.sb_routine:
                break;
        }
    }
}
