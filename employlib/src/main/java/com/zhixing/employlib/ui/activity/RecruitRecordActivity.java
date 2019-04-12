package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.BaseFragment;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.MyAdapter;
import com.zhixing.employlib.ui.fragment.DeliveredFragment;
import com.zhixing.employlib.ui.fragment.RecruitRecommendFragment;
import com.zhixing.employlib.viewmodel.activity.RecruitRecordActivityViewModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author zjq
 * create at 2019/3/18 下午2:18
 * 招聘记录
 */
public class RecruitRecordActivity extends BaseActvity {
    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tab_recuit_record)
    TabLayout tabRecuitRecord;
    @BindView(R2.id.vp_recuit_record)
    ViewPager vpRecuitRecord;
    private Unbinder bind;
    private List<BaseFragment> list;
    private MyAdapter adapter;
    private String[] titles={"已投递","已推荐"} ;
    @Override
    public int getLayoutId() {
        return R.layout.activity_recruitrecordlist;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {

         bind = ButterKnife.bind(this);
        setStatus(-1);
        RecruitRecordActivityViewModel recruitRecordViewModel = ViewModelProviders.of(this).get(RecruitRecordActivityViewModel.class);
        recruitRecordViewModel.getJobList();
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("记录");
        tvWorkSend.setVisibility(View.GONE);
        list = new ArrayList<>();
        list.add(new DeliveredFragment());
        list.add(new RecruitRecommendFragment());

        adapter = new MyAdapter(getSupportFragmentManager(),list,titles);

        vpRecuitRecord.setAdapter(adapter);
        tabRecuitRecord.setupWithViewPager(vpRecuitRecord);

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
