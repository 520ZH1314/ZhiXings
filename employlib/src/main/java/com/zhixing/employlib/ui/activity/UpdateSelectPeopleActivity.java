package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.base.zhixing.www.util.TimeUtil;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.GradingEventAdapt;
import com.zhixing.employlib.adapter.GradingListAdapt;
import com.zhixing.employlib.model.GradingItemEntity;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.eventbus.SelectTeamEvent;
import com.zhixing.employlib.model.eventbus.UpdateTeamSelectEvent;
import com.zhixing.employlib.model.grading.GradListBean;
import com.zhixing.employlib.viewmodel.activity.GradingVIewModel;
import com.zhixing.netlib.base.BaseResponse;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateSelectPeopleActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.recy_update_select_people)
    RecyclerView recyUpdateSelectPeople;
    private GradingVIewModel gradingVIewModel;
    private GradingEventAdapt gradingEventAdapt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_select_people;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        ivWorkAddWork.setImageResource(R
                .mipmap.back);
        tvWorkTitle.setText("员工选择");
        gradingVIewModel = ViewModelProviders.of(this).get(GradingVIewModel.class);


        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //日减1
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(lastDay);
        String commonTime1 = TimeUtil.getCommonTime1(format);

        gradingVIewModel.setDate(commonTime1);

        recyUpdateSelectPeople.setLayoutManager(new LinearLayoutManager(this));

        gradingVIewModel.ListData.observe(this, new Observer<BaseResponse<GradListBean>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<GradListBean> gradListBeanBaseResponse) {
                if (gradListBeanBaseResponse.getRows() != null) {

                    List<GradListBean> rows = gradListBeanBaseResponse.getRows();
                    List<PersonTestEntity> datas = new ArrayList<>();
                    for (GradListBean bean : rows) {
                        datas.add(new PersonTestEntity(bean.getUserName(), 0, bean.getUserCode()));
                    }
                    gradingEventAdapt = new GradingEventAdapt(R.layout.item_grading_event, datas);
                    recyUpdateSelectPeople.setAdapter(gradingEventAdapt);


                }
            }
        });
    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_work_send) {
            if (gradingEventAdapt.isSelect()) {
                PersonTestEntity selectedPosData = gradingEventAdapt.getSelectedPosData();
                EventBus.getDefault().post(new UpdateTeamSelectEvent(selectedPosData.ItemName, selectedPosData.ItemId));
                AppManager.getAppManager().finishActivity();
            } else {
                AppManager.getAppManager().finishActivity();
            }

        }
    }
}
