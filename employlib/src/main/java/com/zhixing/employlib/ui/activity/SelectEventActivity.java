package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.view.Toasty;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.GradingEventAdapt;
import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.model.GradingEventEntity;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.eventbus.GradingEventBean;
import com.zhixing.employlib.model.performance.EventKeyBean;
import com.zhixing.employlib.viewmodel.activity.GradingEventViewModel;
import com.zhixing.employlib.viewmodel.fragment.PerFormanceViewModel;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author zjq
 * create at 2019/3/12 下午2:01
 * <p>
 * 选择评分事件
 */

public class SelectEventActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.recy_grading_event_list)
    RecyclerView recyGradingEventList;
    private Unbinder bind;
    private PerFormanceViewModel gradingEventViewModel;
    private GradingEventAdapt gradingEventAdapt;
    private List<GradingEventEntity> mList;
    private GradingEventViewModel gradingEventViewModels;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_event;
    }

    @Override
    public void process(Message msg) {

    }


    @Override
    public void initLayout() {
        bind = ButterKnife.bind(this);
        setStatus(-1);
        initView();

    }


    private void initData() {
        gradingEventViewModels = ViewModelProviders.of(this).get(GradingEventViewModel.class);

         recyGradingEventList.setLayoutManager(new LinearLayoutManager(this));

             gradingEventViewModels.getEventDatas().observe(this, eventKeyBeanDBaseResponse -> {
                if (eventKeyBeanDBaseResponse != null) {



                    gradingEventAdapt = new GradingEventAdapt(R.layout.item_grading_event, eventKeyBeanDBaseResponse);

                    recyGradingEventList.setAdapter(gradingEventAdapt);
                }


            });
        }




    private void initView() {
                tvWorkTitle.setText("评分事件");
                ivWorkAddWork.setImageResource(R.mipmap.back);
                tvWorkSend.setText("完成");
                tvWorkTitle.setText("评分事件");
                initData();
    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {

            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_work_send) {

                EventBus.getDefault().post(new GradingEventBean(gradingEventAdapt.getSelectedPosData().getItemName(), gradingEventAdapt.getSelectedPosData().getScore(), gradingEventAdapt.getSelectedPosData().getItemId()));
                //关闭
                AppManager.getAppManager().finishActivity();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
