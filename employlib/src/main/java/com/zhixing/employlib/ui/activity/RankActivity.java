package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.ACache;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.ui.fragment.MonthDayRankFragment;
import com.zhixing.employlib.ui.fragment.ToalDayRankFragment;
import com.zhixing.employlib.viewmodel.activity.PerformanceRankViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * @author zjq
 * create at 2019/4/23 下午2:01
 * 排名界面
 */
public class RankActivity extends BaseActvity {


    @BindView(R2.id.tetle_back)
    ImageView tetleBack;
    @BindView(R2.id.tetle_text)
    TextView tetleText;
    @BindView(R2.id.tetle_tv_ok)
    TextView tetleTvOk;
    @BindView(R2.id.tetle_tv_img)
    ImageView tetleTvImg;
    @BindView(R2.id.tetle_tv1)
    TextView tetleTv1;
    @BindView(R2.id.title_rl)
    RelativeLayout titleRl;
    @BindView(R2.id.radButton_person_rank1)
    RadioButton radButtonPersonRank1;
    @BindView(R2.id.radButton_person_rank2)
    RadioButton radButtonPersonRank2;
    @BindView(R2.id.segmented2)
    SegmentedGroup segmented2;
    @BindView(R2.id.fl_rank)
    FrameLayout flRank;

    //判断位置
    private boolean isMothDay=false;
    private Fragment mToalDayFagment;
    private Fragment mMonthRankFragment;
    private String toalDay;
    private String monthDay;
    private PerformanceRankViewModel performanceRankViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {

        if (getIntent().hasExtra("ToalDay")){
             toalDay = getIntent().getStringExtra("ToalDay");
        }

        if (getIntent().hasExtra("MonthDay")){
            monthDay = getIntent().getStringExtra("MonthDay");
        }

        ACache aCache=ACache.get(this);
        aCache.put("ToalDay",toalDay);
        aCache.put("MonthDay",monthDay);
         ButterKnife.bind(this);
         performanceRankViewModel = ViewModelProviders.of(this).get(PerformanceRankViewModel.class);
         tetleText.setText("排名");
         tetleTvImg.setImageResource(R.mipmap.sort);
         tetleTvImg.setVisibility(View.VISIBLE);

         showToalDayRank();

    }

    private void showToalDayRank() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mToalDayFagment==null){
           mToalDayFagment=new ToalDayRankFragment();
            transaction.add(R.id.fl_rank,mToalDayFagment);
        }
        hideFragment(transaction);
        transaction.show(mToalDayFagment).commit();
    }
    private void showMonthRank() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mMonthRankFragment==null){
            mMonthRankFragment=new MonthDayRankFragment();
            transaction.add(R.id.fl_rank,mMonthRankFragment);
        }
        hideFragment(transaction);
        transaction.show(mMonthRankFragment).commit();
    }


    @OnClick({R2.id.tetle_back, R2.id.tetle_tv_img, R2.id.radButton_person_rank1, R2.id.radButton_person_rank2})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tetle_back) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tetle_tv_img) {
        } else if (i == R.id.radButton_person_rank1) {
            showToalDayRank();
            isMothDay=false;
        } else if (i == R.id.radButton_person_rank2) {
            showMonthRank();
            isMothDay=true;
        }
    }


    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction){
        if(mToalDayFagment != null){
            transaction.hide(mToalDayFagment);

        }
        if(mMonthRankFragment != null){
            transaction.hide(mMonthRankFragment);
        }

    }


}
