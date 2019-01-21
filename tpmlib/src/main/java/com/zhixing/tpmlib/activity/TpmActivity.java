package com.zhixing.tpmlib.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.base.zhixing.www.view.Toasty;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.bean.CheckItem;

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
      setStatus(R.color.title_bg);
        initData();
    }

    private void initData() {

      //initWindow();
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
        startActivity(new Intent(this,PlannedMaintenanceActivity.class));
    }
    @OnClick(R2.id.rl__equipment_resume)
    public void onEquipmentClicked(View view) {
        startActivity(new Intent(this,PlannedMaintenanceActivity.class));
    }
    @OnClick(R2.id.rl_statistical_analysis)
    public void onStatisticalClicked(View view) {
        startActivity(new Intent(this,PlannedMaintenanceActivity.class));
    }
}
