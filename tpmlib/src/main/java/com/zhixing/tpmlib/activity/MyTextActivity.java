package com.zhixing.tpmlib.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.bean.RefrshBean;
import com.zhixing.tpmlib.bean.ReplaceBean;
import com.zhixing.tpmlib.fragment.DailyCheckItemFragment;
import com.zhixing.tpmlib.fragment.DailyCheckItemReplaceFragment;
import com.zhixing.tpmlib.viewModel.MyTextActivityViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTextActivity extends BaseTpmActivity {
    @BindView(R2.id.tetle_back)
    ImageView tetleBack;
    @BindView(R2.id.tetle_text)
    TextView tetleText;
    @BindView(R2.id.tetle_tv_img)
    ImageView tetleTvImg;
    @BindView(R2.id.fl_daily_check)
    FrameLayout flDailyCheck;
    @BindView(R2.id.tetle_tv1)
    TextView tetleTv1;
    @BindView(R2.id.title_rl)
    RelativeLayout titleRl;
    private boolean isReplace = false;
    private DailyCheckItemReplaceFragment dailyCheckItemReplaceFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private DailyCheckItemFragment dailyCheckItemFragment;
    private MyTextActivityViewModel mViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_text;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    public void newIniLayout() {
        initView();
    }


    private void initView() {
        mViewModel=ViewModelProviders.of(this).get(MyTextActivityViewModel.class);
        titleRl.setVisibility(View.VISIBLE);
        tetleTvImg.setVisibility(View.VISIBLE);
        tetleTv1.setVisibility(View.GONE);
        tetleTvImg.setImageResource(R.drawable.daily_check_replace);
        tetleText.setText("日常点检项");
        EventBus.getDefault().register(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        dailyCheckItemFragment = new DailyCheckItemFragment();
        ft.add(R.id.fl_daily_check, dailyCheckItemFragment).show(dailyCheckItemFragment).commit();

    }


    @OnClick(R2.id.tetle_tv_img)
    public void onViewClicked() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (isReplace) {
            if (dailyCheckItemReplaceFragment != null) {
               transaction.hide(dailyCheckItemReplaceFragment);
            }
            if (dailyCheckItemFragment == null) {
                dailyCheckItemFragment = new DailyCheckItemFragment();
                transaction.add(R.id.fl_daily_check, dailyCheckItemFragment);
            }
            transaction.show(dailyCheckItemFragment).commit();

            isReplace = false;

        } else {
            if (dailyCheckItemFragment != null) {
              transaction.hide(dailyCheckItemFragment);
            }
            if (dailyCheckItemReplaceFragment == null) {
                dailyCheckItemReplaceFragment = DailyCheckItemReplaceFragment.newInstance();
                transaction.add(R.id.fl_daily_check, dailyCheckItemReplaceFragment);
            }
            transaction.show(dailyCheckItemReplaceFragment).commit();

            isReplace = true;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ReplaceBean bean) {
        if (bean != null) {
            Logger.d("好",bean.i+"");
            mViewModel.UpdataPosition(bean.i);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (dailyCheckItemFragment != null) {
                transaction.hide(dailyCheckItemFragment);
            }
            if (dailyCheckItemReplaceFragment == null) {
                dailyCheckItemReplaceFragment = DailyCheckItemReplaceFragment.newInstance();
                transaction.add(R.id.fl_daily_check, dailyCheckItemReplaceFragment);
            }
            transaction.show(dailyCheckItemReplaceFragment).commit();

            isReplace = true;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefrshEvent(RefrshBean bean) {
        if (bean != null) {

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (dailyCheckItemReplaceFragment != null) {
                transaction.hide(dailyCheckItemReplaceFragment);
            }
            if (dailyCheckItemFragment == null) {
                dailyCheckItemFragment = new DailyCheckItemFragment();
                transaction.add(R.id.fl_daily_check, dailyCheckItemFragment);
            }
            transaction.show(dailyCheckItemFragment).commit();

            isReplace = false;
        }

    }


}

