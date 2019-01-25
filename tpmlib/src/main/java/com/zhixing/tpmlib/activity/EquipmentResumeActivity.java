package com.zhixing.tpmlib.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.bean.EquipmentBaseDateEntity;
import com.zhixing.tpmlib.bean.LineStationResponEntity;
import com.zhixing.tpmlib.viewModel.EquipmentEntityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 *
 *@author zjq
 *create at 2019/1/22 下午4:02
 */
public class EquipmentResumeActivity extends BaseTpmActivity {
    @BindView(R2.id.tetle_text)
    TextView tvTitle;
    @BindView(R2.id.tetle_back)
    ImageView tetleBack;
    @BindView(R2.id.tetle_tv_ok)
    TextView tetleTvOk;
    @BindView(R2.id.tv_tpm_eqipment_select)
    TextView tvTpmEqipmentSelect;//设备名称
    @BindView(R2.id.tv_tpm_equipment_id)
    TextView tvTpmEquipmentId;//设备编号
    @BindView(R2.id.tv_tpm_equipment_name)
    TextView tvTpmEquipmentName;//设备名称
    @BindView(R2.id.tv_tpm_equipment_model)
    TextView tvTpmEquipmentModel;//设备型号
    @BindView(R2.id.tv_tpm_equipment_manufacturer)
    TextView tvTpmEquipmentManufacturer;//设备厂家
    @BindView(R2.id.tv_tpm_equipment_type)
    TextView tvTpmEquipmentType;//设备类型
    @BindView(R2.id.tv_tpm_equipment_control_type)
    TextView tvTpmEquipmentControlType;//控制器型号
    @BindView(R2.id.tv_tpm_equipment_buy_date)
    TextView tvTpmEquipmentBuyDate;//购置日期
    @BindView(R2.id.tv_tpm_equipment_start_date)
    TextView tvTpmEquipmentStartDate;//启用日期
    @BindView(R2.id.tv_tpm_equipment_money)
    TextView tvTpmEquipmentMoney;//价格
    @BindView(R2.id.tv_tpm_equipment_guarantee)
    TextView tvTpmEquipmentGuarantee;//保修
    @BindView(R2.id.tv_tpm_equipment_power)
    TextView tvTpmEquipmentPower;//动力
    @BindView(R2.id.tv_tpm_equipment_voltage)
    TextView tvTpmEquipmentVoltage;//电压
    @BindView(R2.id.tv_tpm_equipment_pressure)
    TextView tvTpmEquipmentPressure;//气压
    @BindView(R2.id.tv_tpm_equipment_products)
    TextView tvTpmEquipmentProducts;//生产能力
    @BindView(R2.id.tv_tpm_equipment_stop_date)
    TextView tvTpmEquipmentStopDate;//停机日期
    @BindView(R2.id.tv_tpm_equipment_manger)
    TextView tvTpmEquipmentManger;//资产管理人

    @BindView(R2.id.radio_btn_tpm_warn)
    RadioButton radioBtnTpmWarn;
    @BindView(R2.id.radio_btn_tpm_maintenance)
    RadioButton radioBtnTpmMaintenance;
    @BindView(R2.id.radio_btn_tpm_dj)
    RadioButton radioBtnTpmDj;
    private SharedUtils sharedUtils;



    private EquipmentEntityViewModel mEquipmentViewModel;
    private String equipmentName;
    private String equipmentId;
    private String tpmStationCode;
    private String tpmStationName;
    private String LineListCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_equipment_resume;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void newIniLayout() {

        initView();

        //初始化数据
       initData();
    }

    private void initView() {
        tvTitle.setText("设备履历");
        tetleTvOk.setVisibility(View.VISIBLE);
        sharedUtils = new SharedUtils("TpmSetting");
        LineListCode = sharedUtils.getStringValue("LineListCode");
        tpmStationCode = sharedUtils.getStringValue("tpmStationCode");
        tpmStationName = sharedUtils.getStringValue("tpmStationName");
        tetleTvOk.setText(tpmStationName);
        mEquipmentViewModel = ViewModelProviders.of(EquipmentResumeActivity.this).get(EquipmentEntityViewModel.class);
    }


    private void initData() {
        showDialog("加载中..");
        mEquipmentViewModel.getEquipmentBaseData(tpmStationCode).observe(EquipmentResumeActivity.this, EquipmentBaseDateEntity-> {
            if (EquipmentBaseDateEntity.getRows() != null) {
                for (EquipmentBaseDateEntity  bean : EquipmentBaseDateEntity.getRows()) {

                    setData(bean);
                }
                 dismissDialog();
            }else{
                dismissDialog();
            }
        });


    }

    //设置数据到view上
    private void setData(EquipmentBaseDateEntity bean) {
         equipmentName = bean.getEquipmentName();
         equipmentId = bean.getEquipmentId();
        tvTpmEqipmentSelect.setText(bean.getEquipmentName());
        tvTpmEquipmentId.setText(bean.getEquipmentCode());
        tvTpmEquipmentName.setText(bean.getEquipmentName());
        tvTpmEquipmentModel.setText(bean.getSPEC());
        tvTpmEquipmentManufacturer.setText(bean.getFactoryName());
        tvTpmEquipmentType.setText(bean.getClassName());
        tvTpmEquipmentControlType.setText(bean.getControlStyle());

        tvTpmEquipmentBuyDate.setText(getFormartTime(bean.getBuyDate()));
        tvTpmEquipmentStartDate.setText(getFormartTime(bean.getEnableDate()));
        tvTpmEquipmentMoney.setText(String.valueOf(bean.getPrice()));
        tvTpmEquipmentGuarantee.setText(String.valueOf(bean.getRemain()));
        tvTpmEquipmentPower.setText(String.valueOf(bean.getPower()));
        tvTpmEquipmentVoltage.setText(String.valueOf(bean.getTension()));
        tvTpmEquipmentPressure.setText(String.valueOf(bean.getAirPressure()));
        tvTpmEquipmentProducts.setText(String.valueOf(bean.getProductionCapacity()));
        tvTpmEquipmentStopDate.setText(getFormartTime(bean.getDisableDate()));
        tvTpmEquipmentManger.setText(String.valueOf(bean.getCaretaker()));


    }




    @OnClick({R2.id.tetle_back, R2.id.tetle_tv_ok, R2.id.tv_tpm_eqipment_select, R2.id.radio_btn_tpm_warn, R2.id.radio_btn_tpm_maintenance, R2.id.radio_btn_tpm_dj})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tetle_back) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tetle_tv_ok) {
            CommonSetSelectPop commonSetSelectPop = new CommonSetSelectPop(this, null, "产线");
            commonSetSelectPop.setMidH(true);
            commonSetSelectPop.isDoall(true);
            commonSetSelectPop.getSet().put("ApiCode", "GetLineList");
            commonSetSelectPop.setSelect(new SetSelect() {
                @Override
                public void select(String id, String code, String name) {
                    LineListCode = code;
                    getLineStationPop(id, code);
                }
            });
            commonSetSelectPop.showSheet();


        } else if (i == R.id.tv_tpm_eqipment_select) {

        } else if (i == R.id.radio_btn_tpm_warn) {

            Bundle b = new Bundle();
            b.putString("titleName", "异常记录查询");
            b.putString("equipmentId",equipmentId);
            b.putString("equipmentName",equipmentName);
            startActivity(CurrencyRecordActivity.class, b);

        } else if (i == R.id.radio_btn_tpm_maintenance) {
            Bundle b = new Bundle();
            b.putString("titleName", "保养记录查询");
            b.putString("equipmentId",equipmentId);
            b.putString("equipmentName",equipmentName);
            startActivity(CurrencyRecordActivity.class, b);
        } else if (i == R.id.radio_btn_tpm_dj) {
            Bundle b = new Bundle();
            b.putString("titleName", "点检记录查询");
            b.putString("equipmentId",equipmentId);
            b.putString("equipmentName",equipmentName);
            startActivity(CurrencyRecordActivity.class, b);
        }
    }

    private void getLineStationPop(String id, String Linecode) {
        CommonSetSelectPop commonSetSelectPop = new CommonSetSelectPop(this, null, "工位");
        commonSetSelectPop.setMidH(true);
        commonSetSelectPop.isDoall(false);
        commonSetSelectPop.getSet().put("ApiCode", "GetLineStationList");
        commonSetSelectPop.getSet().put("LineCode", Linecode);
        commonSetSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                tetleTvOk.setText(name);
                EquipmentResumeActivity.this.showDialog("加载中...");

                //这边请求设备列表数据
                mEquipmentViewModel.getEquipmentBaseData(code).observe(EquipmentResumeActivity.this, equipmentBaseDateEntity -> {
                    if (equipmentBaseDateEntity.getRows() != null) {

                        for (EquipmentBaseDateEntity  bean : equipmentBaseDateEntity.getRows()) {

                            setData(bean);
                        }
                        EquipmentResumeActivity.this.dismissDialog();

                    } else {
                        EquipmentResumeActivity.this.dismissDialog();
                    }


                });

            }
        });
        commonSetSelectPop.showSheet();
    }


}
