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

import com.android.volley.VolleyError;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.orhanobut.logger.Logger;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.bean.AnomalousBean;
import com.zhixing.tpmlib.bean.CheckItemBean;
import com.zhixing.tpmlib.bean.EquipmentEvent;
import com.zhixing.tpmlib.bean.RefrshBean;
import com.zhixing.tpmlib.bean.ReplaceBean;
import com.zhixing.tpmlib.fragment.DailyCheckItemFragment;
import com.zhixing.tpmlib.fragment.DailyCheckItemReplaceFragment;
import com.zhixing.tpmlib.viewModel.MyTextActivityViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private SharedUtils sharedUtils;
    private SharedUtils sharedUtil;
    private String tpmLineid;
    private String lineTpmId;
    private String tpmPosiId;

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
        // mViewModel=ViewModelProviders.of(this).get(MyTextActivityViewModel.class);
        String equipmentName=getIntent().getStringExtra("matchName");
        EventBus.getDefault().post(new EquipmentEvent(equipmentName));
        sharedUtils = new SharedUtils("TPM");
        sharedUtil = new SharedUtils("TpmSetting");
        tpmLineid = sharedUtil.getStringValue("tpmLineid");//       获取产线id
        //        获取工位id
        tpmPosiId = sharedUtil.getStringValue("tpmPosiId");
        getExceptionFromData();
//        获取点检项列表的接口
        getFromData();
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

    private void getExceptionFromData() {

        String tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        //        获取设备id
        String matheId = sharedUtils.getStringValue("equipmentID");
        Map<String, String> params = new HashMap<String, String>();
        params.put("TenantId", tenantId);
        params.put("AppCode", "TPM");
        params.put("ApiCode", "GetMaintananceItemInfo");
        params.put("LineId", tpmLineid);
        params.put("EquipmentId", matheId);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("AppCode", "TPM");
            jsonObject.put("ApiCode", "GetMaintananceItemInfo");
            jsonObject.put("TenantId", tenantId);
            jsonObject.put("LineId", tpmLineid);
            jsonObject.put("EquipmentId", matheId);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        获取异常小类的接口
        Map<String, String> exceptionParams = new HashMap<String, String>();
        exceptionParams.put("AppCode", "Andon");
        exceptionParams.put("TenantId", tenantId);
        exceptionParams.put("ApiCode", "GetStationException");
        exceptionParams.put("IsEquipmentException", "1");
        exceptionParams.put("LineStationId", tpmPosiId);
        try {
            jsonObject.put("AppCode", "Andon");
            jsonObject.put("TenantId", tenantId);
            jsonObject.put("ApiCode", "GetStationException");
            jsonObject.put("IsEquipmentException", "1");
            jsonObject.put("LineStationId", tpmPosiId);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpPostVolley(SharedPreferencesTool.getMStool(this).getIp() + UrlUtil.Url, exceptionParams, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                String exceptionUrl = jsonObject.toString();
                sharedUtils.setStringValue("exceptionJson", jsonObject.toString());
                P.c(jsonObject.toString() + "exceptionUtrl");
            }

            @Override
            public void error(VolleyError error) {
                System.out.println(error.toString() + "DetailCheckActivity111111111");
            }
        }, true);
    }

    private void getFromData() {
        //        获取点检项的接口
        String tenantId = SharedPreferencesTool.getMStool(MyTextActivity.this).getTenantId();
        //        获取设备id
        String matheId = sharedUtils.getStringValue("equipmentID");
        Map<String, String> params = new HashMap<String, String>();
        params.put("TenantId", tenantId);
        params.put("AppCode", "TPM");
        params.put("ApiCode", "GetMaintananceItemInfo");
        params.put("LineId", tpmLineid);
        params.put("EquipmentId", matheId);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("AppCode", "TPM");
            jsonObject.put("ApiCode", "GetMaintananceItemInfo");
            jsonObject.put("TenantId", tenantId);
            jsonObject.put("LineId", tpmLineid);
            jsonObject.put("EquipmentId", matheId);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpPostVolley(SharedPreferencesTool.getMStool(MyTextActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                sharedUtils.setStringValue("checkItemJson", jsonObject.toString());
                P.c("DailyCheckDetailActivity:" + jsonObject.toString());
            }

            @Override
            public void error(VolleyError error) {
                P.c("DailyCheckDetailActivity:" + error.toString());
            }
        }, true);
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
    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }*/


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ReplaceBean bean) {
        if (bean != null) {
            Logger.d("好", bean.i + "");
            // mViewModel.UpdataPosition(bean.i);
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

