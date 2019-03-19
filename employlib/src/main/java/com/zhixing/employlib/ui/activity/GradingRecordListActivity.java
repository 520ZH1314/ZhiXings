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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.GradingedListAdapt;
import com.zhixing.employlib.model.GradingedEntity;
import com.zhixing.employlib.viewmodel.activity.GradingedListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author zjq
 * create at 2019/3/13 上午10:38
 * 评分记录列表
 */
public class GradingRecordListActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_gradinged_list_start_time)
    TextView tvGradingedListStartTime;
    @BindView(R2.id.tv_gradinged_list_end_time)
    TextView tvGradingedListEndTime;
    @BindView(R2.id.recy_gradinged_list)
    RecyclerView recyGradingedList;
    private GradingedListViewModel gradingedListViewModel;
    private Unbinder bind;
    private GradingedListAdapt gradingedListAdapt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_grading_record_list;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
         bind = ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        gradingedListViewModel = ViewModelProviders.of(this).get(GradingedListViewModel.class);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("已评分");
        tvWorkSend.setVisibility(View.GONE);
        recyGradingedList.setLayoutManager(new LinearLayoutManager(this));



        gradingedListViewModel.getDatas().observe(this, new Observer<List<GradingedEntity>>() {
            @Override
            public void onChanged(@Nullable List<GradingedEntity> gradingedEntities) {
                 gradingedListAdapt = new GradingedListAdapt(R.layout.item_gradinged_list, gradingedEntities);
                recyGradingedList.setAdapter(gradingedListAdapt);
                gradingedListAdapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        startActivity(GradingedRecordDetalActivity.class);
                    }
                });
            }
        });



    }



    @OnClick({R2.id.iv_work_add_work, R2.id.tv_gradinged_list_start_time, R2.id.tv_gradinged_list_end_time})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_gradinged_list_start_time) {
        } else if (i == R.id.tv_gradinged_list_end_time) {
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
