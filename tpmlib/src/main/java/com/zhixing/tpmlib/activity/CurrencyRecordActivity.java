package com.zhixing.tpmlib.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeTime;

import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.view.CommonTips;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class CurrencyRecordActivity extends BaseTpmActivity {
    @BindView(R2.id.tetle_back)
    ImageView tetleBack;
    @BindView(R2.id.tetle_tv_ok)
    TextView tetleTvOk;
    @BindView(R2.id.tv_tpm_currency_record)
    TextView tvTpmCurrencyRecord;
    @BindView(R2.id.tv_tpm_currency_start_time)
    TextView tvTpmCurrencyStartTime;
    @BindView(R2.id.tv_tpm_currency_end_time)
    TextView tvTpmCurrencyEndTime;
    @BindView(R2.id.btn_tpm_currency_clear)
    Button btnTpmCurrencyClear;
    @BindView(R2.id.btn_tpm_currency_go)
    Button btnTpmCurrencyGo;
    @BindView(R2.id.tetle_text)
    TextView tetleText;
    private String StartTime;
    private String EndTime;
    private String equipmentId;
    private String equipmentName;
    @Override
    public void newIniLayout() {
        initView();
    }
    private void initView() {
        if (getIntent().hasExtra("titleName")) {
            tetleText.setText(getIntent().getStringExtra("titleName"));
             equipmentName = getIntent().getStringExtra("equipmentName");
            tvTpmCurrencyRecord.setText(equipmentName);
        }
        if (getIntent().hasExtra("equipmentId")){
             equipmentId = getIntent().getStringExtra("equipmentId");
        }
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .line(1, R.color.tpm_blue)
                .radius(3)
                .into(btnTpmCurrencyClear);
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .line(1, R.color.tpm_blue)
                .radius(3)
                .into(btnTpmCurrencyGo);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_currency_record;
    }

    @Override
    public void process(Message msg) {

    }
    @OnClick({R2.id.tetle_back, R2.id.tv_tpm_currency_start_time, R2.id.tv_tpm_currency_end_time, R2.id.btn_tpm_currency_clear, R2.id.btn_tpm_currency_go})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tetle_back) {

            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_tpm_currency_start_time) {
            ChangeTime changeTime =new ChangeTime(this,"",2);
            changeTime.showSheet();
            changeTime.setSelect(new SelectTime() {
                @Override
                public void select(String time, long timestp) {
                    if ("结束时间".equals(tvTpmCurrencyEndTime.getText().toString())){
                        StartTime="";
                        tvTpmCurrencyStartTime.setText(time);
                    }else{

                        String s2 = tvTpmCurrencyEndTime.getText().toString();
                        int timeCompareSize = TimeUtil.getTimeCompareSizes(time, s2);
                        if (timeCompareSize==2){
                            StartTime = tvTpmCurrencyStartTime.getText().toString();
                            tvTpmCurrencyStartTime.setText(time);
                        }else if (timeCompareSize==1){
                            StartTime="";
                            tvTpmCurrencyStartTime.setText("开始时间");
                            Toasty.INSTANCE.showToast(CurrencyRecordActivity.this,"开始时间不能大于结束时间");
                        }else{
                            StartTime="";
                            tvTpmCurrencyStartTime.setText("开始时间");
                            Toasty.INSTANCE.showToast(CurrencyRecordActivity.this,"开始时间不能等于结束时间");
                        }

                    }
                }
            });


        } else if (i == R.id.tv_tpm_currency_end_time) {
            ChangeTime changeTime =new ChangeTime(this,"",2);
            changeTime.showSheet();

            changeTime.setSelect(new SelectTime() {
                @Override
                public void select(String time, long timestp) {
                    if ("开始时间".equals(tvTpmCurrencyStartTime.getText().toString())){
                        EndTime="";
                        tvTpmCurrencyEndTime.setText(time);
                    }else{
                        String s1 = tvTpmCurrencyStartTime.getText().toString();

                        int timeCompareSize = TimeUtil.getTimeCompareSizes(s1, time);
                        if (timeCompareSize==2){
                            EndTime = tvTpmCurrencyEndTime.getText().toString();
                            tvTpmCurrencyEndTime.setText(time);
                        }else if (timeCompareSize==1){
                            EndTime="";
                            tvTpmCurrencyEndTime.setText("结束时间");
                            Toasty.INSTANCE.showToast(CurrencyRecordActivity.this,"结束时间不能小于开始时间");
                        }else{
                            EndTime="";
                            tvTpmCurrencyEndTime.setText("结束时间");
                            Toasty.INSTANCE.showToast(CurrencyRecordActivity.this,"结束时间不能等于开始时间");
                        }

                    }
                }
            });


        } else if (i == R.id.btn_tpm_currency_clear) {
            CommonTips tips = new CommonTips(this, getHandler());
            tips.init("取消", "确定", "是否清楚数据");
            tips.setI(new CommonTips.Tips() {
                @Override
                public void cancel() {
                }
                @Override
                public void sure() {
                    tvTpmCurrencyStartTime.setText("开始时间");
                    tvTpmCurrencyStartTime.setText("结束时间");
                }
            });
            tips.showSheet();


        } else if (i == R.id.btn_tpm_currency_go) {

              if ("开始时间".equals(tvTpmCurrencyEndTime.getText().toString())||"结束时间".equals(tvTpmCurrencyEndTime.getText().toString())){
                  Toasty.INSTANCE.showToast(this,"开始时间和结束时间不能为空");
              }else{
                  //去到详情
                  Bundle bundle =new Bundle();
                 String type="";
                  switch (tetleText.getText().toString()){
                      case "异常记录查询":
                          type="1";
                          break;
                      case "保养记录查询":
                          type="2";
                          break;
                      case "点检记录查询":
                          type="3";
                          break;
                  }
                  bundle.putString("equipmentName",equipmentName);
                  bundle.putString("equipmentId",equipmentId);
                  bundle.putString("tpm_record_type",type);
                  bundle.putString("CurrencyRecordStartTime",tvTpmCurrencyStartTime.getText().toString());
                  bundle.putString("CurrencyRecordEndTime",tvTpmCurrencyEndTime.getText().toString());
                  startActivity(CurrencyRecordDetailActivity.class,bundle);
              }


        }
    }


}
