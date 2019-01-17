package com.zhixing.tpmlib.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.base.zhixing.www.view.Toasty;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
 * @Author smart
 * @Date 2018/12/24
 * @Des TPM管理的首界面
 */
public class TpmActivity extends BaseTpmActivity {
  @BindView(R2.id.tetle_text)
  TextView tvTtitle;//标题文本标签
  @Override
    public int getLayoutId() {
        return getcLayoutId();
    }

    public int getcLayoutId() {
        return R.layout.activity_tpm;
    }
    @Override
    public void process(Message msg) {
    }
    @Override
    public void newIniLayout() {
        initData();
    }

    private void initData() {
    tvTtitle.setText("TPM管理");
  }


//  初始化点击事件
  @OnClick(R2.id.rl_daily_check)
public void onDailyClicked(View view) {
    startActivity(new Intent(this, DailyCheckActivity.class));
}
    @OnClick(R2.id.rl_planned_maintenance)
    public void onPlanneClicked(View view) {
        startActivity(new Intent(this,MyTextActivity.class));;
    }
    @OnClick(R2.id.rl_matche_warming)
    public void onWarmingClicked(View view) {
        startActivity(new Intent(this,MaintenanceWarmingActivity.class));
    }
    @OnClick(R2.id.rl__equipment_resume)
    public void onEquipmentClicked(View view) {
        startActivity(new Intent(this,EquipmentResumeActivity.class));
    }
    @OnClick(R2.id.rl_statistical_analysis)
    public void onStatisticalClicked(View view) {
        startActivity(new Intent(this,StatisticalAnalysisActivity.class));
    }
   /* @OnClick(R2.id.tetle_back)
    public void onBackClicked(View view) {
//      结束当前界面
       finish();
    }*/
}
