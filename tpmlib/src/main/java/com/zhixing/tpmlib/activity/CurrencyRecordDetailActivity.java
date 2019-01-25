package com.zhixing.tpmlib.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.CurrencyAdapt;
import com.zhixing.tpmlib.bean.CheckRecordDataEntity;
import com.zhixing.tpmlib.bean.MaintenanceBean;
import com.zhixing.tpmlib.bean.MaintenanceRecordEntity;
import com.zhixing.tpmlib.bean.SpotCheckBean;
import com.zhixing.tpmlib.bean.WarnBean;
import com.zhixing.tpmlib.bean.WarnRecordDataEntity;
import com.zhixing.tpmlib.viewModel.CurrencyRecordDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author zjq
 * create at 2019/1/8 下午2:36
 * 公共的设备履历详情
 */
public class CurrencyRecordDetailActivity extends BaseTpmActivity  {


    @BindView(R2.id.tetle_back)
    ImageView tetleBack;
    @BindView(R2.id.tetle_text)
    TextView tetleText;
    @BindView(R2.id.tv_tpm_currency_detail_name)
    TextView tvTpmCurrencyDetailName;
    @BindView(R2.id.recy_tpm_currency_record_detail)
    RecyclerView recyTpmCurrencyRecordDetail;
//    @BindView(R2.id.springview_currency)
//    SpringView springviewCurrency;
    @BindView(R2.id.tv_tpm_currency_detail_time)
    TextView tvTpmCurrencyDetailTime;
    private CurrencyRecordDetailViewModel mCurrencyRecordDetailViewModel;
    private String type;


    private CurrencyAdapt adapt;
    private String equipmentId;
    private String currencyRecordStartTime;
    private String currencyRecordEndTime;
    private String equipmentName;

    @Override
    public void newIniLayout() {
        initView();
        initData();
    }

    private void initData() {
        showDialog("加载中");
        if ("3".equals(type)) {
            //点检记录
            mCurrencyRecordDetailViewModel.getSpotCheckData(equipmentId, currencyRecordStartTime, currencyRecordEndTime).observe(this,  spotCheckBeans -> {
                if (spotCheckBeans.getRows()!=null){
                    List<SpotCheckBean> list = new ArrayList<>();
                    for (CheckRecordDataEntity bean: spotCheckBeans.getRows()) {
                        list.add(new SpotCheckBean(bean.getCreateDate(),"",bean.getUserName(),bean.getCompleteTime(),bean.getStatus()));
                    }
                    adapt = new CurrencyAdapt(R.layout.item_tpm_currency_details, list);
                    recyTpmCurrencyRecordDetail.setAdapter(adapt);
                    dismissDialog();
                }else{
                    dismissDialog();
                }



            });

        } else if ("2".equals(type)) {
            //保养
            mCurrencyRecordDetailViewModel.getMaintenanceData(equipmentId, currencyRecordStartTime, currencyRecordEndTime).observe(this, new Observer<BaseResponse<MaintenanceRecordEntity>>() {
                @Override
                public void onChanged(@Nullable BaseResponse<MaintenanceRecordEntity> maintenanceRecordEntityBaseResponse) {
                    if (maintenanceRecordEntityBaseResponse.getMessage() == null) {
                        List<MaintenanceBean> list = new ArrayList<>();
                        if (maintenanceRecordEntityBaseResponse.getRows() != null) {
                            List<MaintenanceRecordEntity> rows = maintenanceRecordEntityBaseResponse.getRows();
                            for (int i = 0; i < rows.size(); i++) {
                                MaintenanceBean bean = new MaintenanceBean(rows.get(i).getCompleteTime(), "",
                                        rows.get(i).getGradeName(),
                                        rows.get(i).getUserCode(), rows.get(i).getDelayday() + "");
                                list.add(bean);
                            }
                            adapt = new CurrencyAdapt(R.layout.item_tpm_currency_detail, list);
                            recyTpmCurrencyRecordDetail.setAdapter(adapt);
                            dismissDialog();
                        }
                    } else {
                        dismissDialog();
                    }


                }
            });


        } else {
            //异常
            mCurrencyRecordDetailViewModel.getWarnData(equipmentId, currencyRecordStartTime, currencyRecordEndTime).observe(this,  warnBeans -> {

                if (warnBeans.getRows()!=null){
                    List<WarnBean> list = new ArrayList<>();
                    for (WarnRecordDataEntity bean: warnBeans.getRows()) {
                        list.add(new WarnBean(bean.getCreateTime(),"",bean.getDescription(),bean.getUserInfo(),bean.getMeasure(),"未关闭"));
                    }
                    adapt = new CurrencyAdapt(R.layout.item_tpm_currency_detail, list);
                    recyTpmCurrencyRecordDetail.setAdapter(adapt);
                    dismissDialog();
                }else{
                    dismissDialog();
                }

            });
        }
    }

    private void initView() {
        getTitleData();
//        springviewCurrency.setType(SpringView.Type.FOLLOW);
//        springviewCurrency.setHeader(new DefaultHeader(this));
//        springviewCurrency.setFooter(new DefaultFooter(this));
        mCurrencyRecordDetailViewModel = ViewModelProviders.of(this).get(CurrencyRecordDetailViewModel.class);
        recyTpmCurrencyRecordDetail.setLayoutManager(new LinearLayoutManager(this));
//        springviewCurrency.setListener(this);


    }

    private void getTitleData() {
        if (getIntent().hasExtra("tpm_record_type")) {

            type = getIntent().getStringExtra("tpm_record_type");
            if ("1".equals(getIntent().getStringExtra("tpm_record_type"))) {
                //异常记录
                tetleText.setText("异常记录");
            } else if ("2".equals(getIntent().getStringExtra("tpm_record_type"))) {
                //保养
                tetleText.setText("保养记录");
            } else {
                tetleText.setText("点检记录");
            }

        }

        if (getIntent().hasExtra("CurrencyRecordStartTime")) {
            currencyRecordStartTime = getIntent().getStringExtra("CurrencyRecordStartTime");
            currencyRecordEndTime = getIntent().getStringExtra("CurrencyRecordEndTime");
            tvTpmCurrencyDetailTime.setText(currencyRecordStartTime + " " + "至" + " " + currencyRecordEndTime);

        }

        if (getIntent().hasExtra("equipmentId")) {
            equipmentId = getIntent().getStringExtra("equipmentId");
        }

        if (getIntent().hasExtra("equipmentName")) {
            equipmentName = getIntent().getStringExtra("equipmentName");
            tvTpmCurrencyDetailName.setText(equipmentName);

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_currency_record_detail;
    }

    @Override
    public void process(Message msg) {

    }


    @OnClick(R2.id.tetle_back)
    public void onViewClicked() {
        AppManager.getAppManager().finishActivity();
    }

//    @Override
//    public void onRefresh() {
//        adapt.getData().clear();
//
//        if ("3".equals(type)) {
//            //点检
//            mCurrencyRecordDetailViewModel.RefreshSpotCheckData().observe(this, new Observer<List<SpotCheckBean>>() {
//                @Override
//                public void onChanged(@Nullable List<SpotCheckBean> spotCheckBeans) {
//                    adapt.setNewData(spotCheckBeans);
//
//                }
//            });
//            finishFreshAndLoad();
//
//        } else if ("2".equals(type)) {
//
//            //保养
//            mCurrencyRecordDetailViewModel.RefreshMaintenanceData().observe(this, new Observer<List<MaintenanceBean>>() {
//                @Override
//                public void onChanged(@Nullable List<MaintenanceBean> maintenanceBeans) {
//                    adapt.setNewData(maintenanceBeans);
//                }
//            });
//            finishFreshAndLoad();
//
//        } else {
//            //异常
//
//            mCurrencyRecordDetailViewModel.RefreshWarnData().observe(this, new Observer<List<WarnBean>>() {
//                @Override
//                public void onChanged(@Nullable List<WarnBean> warnBeans) {
//                    adapt.setNewData(warnBeans);
//                }
//            });
//            finishFreshAndLoad();
//
//        }
//
//
//    }
//
//    @Override
//    public void onLoadmore() {
//
//        if ("3".equals(type)) {
//            mCurrencyRecordDetailViewModel.LoadSpotCheckData().observe(this, new Observer<List<SpotCheckBean>>() {
//                @Override
//                public void onChanged(@Nullable List<SpotCheckBean> spotCheckBeans) {
//                    adapt.addData(spotCheckBeans);
//                }
//            });
//            finishFreshAndLoad();
//
//
//        } else if ("2".equals(type)) {
//            mCurrencyRecordDetailViewModel.LoadMaintenanceData().observe(this, new Observer<List<MaintenanceBean>>() {
//                @Override
//                public void onChanged(@Nullable List<MaintenanceBean> maintenanceBeans) {
//                    adapt.addData(maintenanceBeans);
//                }
//            });
//            finishFreshAndLoad();
//        } else {
//
//            mCurrencyRecordDetailViewModel.LoadWarnData().observe(this, new Observer<List<WarnBean>>() {
//                @Override
//                public void onChanged(@Nullable List<WarnBean> warnBeans) {
//                    adapt.addData(warnBeans);
//                }
//            });
//            finishFreshAndLoad();
//        }
//
//
//    }


//    private void finishFreshAndLoad() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                springviewCurrency.onFinishFreshAndLoad();
//            }
//        }, 1000);
//    }


}
