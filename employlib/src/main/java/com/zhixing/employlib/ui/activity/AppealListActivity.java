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
import com.zhixing.employlib.adapter.AppealListAdapt;
import com.zhixing.employlib.model.AppealListEntity;
import com.zhixing.employlib.viewmodel.activity.AppealListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author zjq
 * create at 2019/3/21 下午1:58
 * 我申诉记录的列表(处理申诉列表)
 */
public class AppealListActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.recy_myappeal)
    RecyclerView recyMyappeal;
    private Unbinder bind;
    private AppealListViewModel appealListViewModel;
    private AppealListAdapt appealListAdapt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_appeal_list;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
         bind = ButterKnife.bind(this);
         appealListViewModel = ViewModelProviders.of(this).get(AppealListViewModel.class);
        recyMyappeal.setLayoutManager(new LinearLayoutManager(this));
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("申诉记录");
        tvWorkSend.setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        appealListViewModel.getAppealListEntity();
        appealListViewModel.appealListEntity.observe(this, new Observer<List<AppealListEntity>>() {
            @Override
            public void onChanged(@Nullable List<AppealListEntity> appealListEntities) {
                if (appealListEntities!=null){
                     appealListAdapt=new AppealListAdapt(R.layout.item_appeal_list,appealListEntities) ;
                    recyMyappeal.setAdapter(appealListAdapt);
                    appealListAdapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            startActivity(AppealDetailActivity.class);
                        }
                    });
                }
            }
        });
    }


    @OnClick(R2.id.iv_work_add_work)
    public void onViewClicked() {
        AppManager.getAppManager().finishActivity();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
