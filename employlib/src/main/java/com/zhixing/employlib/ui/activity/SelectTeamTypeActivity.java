package com.zhixing.employlib.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.GradingEventAdapt;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.eventbus.SelectTeamEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zjq
 * create at 2019/3/16 上午11:21
 * <p>
 * 更新园地选择分类
 */
public class SelectTeamTypeActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.recy_update_select_type)
    RecyclerView recyUpdateSelectType;
    private GradingEventAdapt gradingEventAdapt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_people;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("选择类型");
        recyUpdateSelectType.setLayoutManager(new LinearLayoutManager(this));
        List<PersonTestEntity> datas=new ArrayList<>();

        datas.add(new PersonTestEntity("月度优秀员工",0,"1"));
        datas.add(new PersonTestEntity("季度优秀员工",0,"2"));
        datas.add(new PersonTestEntity("年度优秀员工",0,"3"));
        datas.add(new PersonTestEntity("新员工",0,"4"));
        datas.add(new PersonTestEntity("班组风采",0,"5"));

        gradingEventAdapt = new GradingEventAdapt(R.layout.item_grading_event, datas);

        recyUpdateSelectType.setAdapter(gradingEventAdapt);



    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();

        } else if (i == R.id.tv_work_send) {

            if (gradingEventAdapt.isSelect()){
                PersonTestEntity selectedPosData = gradingEventAdapt.getSelectedPosData();
                EventBus.getDefault().post(new SelectTeamEvent(selectedPosData.ItemId));
                AppManager.getAppManager().finishActivity();
            }else{
                AppManager.getAppManager().finishActivity();
            }

        }

    }
}
