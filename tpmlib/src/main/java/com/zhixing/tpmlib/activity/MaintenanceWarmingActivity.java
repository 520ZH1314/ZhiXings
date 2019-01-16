package com.zhixing.tpmlib.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.util.GsonUtil;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.MaintenanceWarmingAdapt;
import com.zhixing.tpmlib.bean.MaintenanceStatusType;
import com.zhixing.tpmlib.view.TopMaintenanceTypeDialog;
import com.zhixing.tpmlib.viewModel.MaintenanceWarnViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * @Author smart
 * @Date 2018/12/24
 * @Des 进入保养警告界面
 */
public class MaintenanceWarmingActivity extends BaseTpmActivity implements SpringView.OnFreshListener {
    @BindView(R2.id.tetle_text)
    TextView tvTitle;
    @BindView(R2.id.tetle_back)
    ImageView tetleBack;
    @BindView(R2.id.recyle_maintenance_warning)
    RecyclerView recyleMaintenanceWarning;
    @BindView(R2.id.springview_maintenance_warning)
    SpringView springviewMaintenanceWarning;
    @BindView(R2.id.tetle_tv_ok)
    TextView tetleTvOk;
    private MaintenanceWarnViewModel mMaintenanceWarnViewModel;
    private MaintenanceWarmingAdapt adapt;


    @Override
    public int getLayoutId() {
        return R.layout.activity_maintenance_warming;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void newIniLayout() {
        initView();

    }

    private void initView() {
        tvTitle.setText("保养警告");
        tetleTvOk.setVisibility(View.VISIBLE);
        tetleTvOk.setText("无量山车间");
        springviewMaintenanceWarning.setListener(this);
        //设置springview的头和尾
        //设置上下控件
        springviewMaintenanceWarning.setType(SpringView.Type.FOLLOW);
        springviewMaintenanceWarning.setHeader(new DefaultHeader(this));
        springviewMaintenanceWarning.setFooter(new DefaultFooter(this));
        mMaintenanceWarnViewModel = ViewModelProviders.of(this).get(MaintenanceWarnViewModel.class);
        recyleMaintenanceWarning.setLayoutManager(new LinearLayoutManager(this));
        mMaintenanceWarnViewModel.initData();
        springviewMaintenanceWarning.setHeader(new DefaultHeader(this));
        springviewMaintenanceWarning.setFooter(new DefaultFooter(this));
        adapt = new MaintenanceWarmingAdapt(R.layout.item_maintenance_warning, mMaintenanceWarnViewModel.MaintenanceWarnValue.getValue());
        recyleMaintenanceWarning.setAdapter(adapt);

    }


    private void finishFreshAndLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springviewMaintenanceWarning.onFinishFreshAndLoad();
            }
        }, 1000);
    }


    @Override
    public void onRefresh() {
        adapt.getData().clear();
        adapt.setNewData(mMaintenanceWarnViewModel.RefrshData().getValue());
        finishFreshAndLoad();
    }

    @Override
    public void onLoadmore() {
        adapt.addData(mMaintenanceWarnViewModel.lodeMoreData().getValue());
        finishFreshAndLoad();

    }



    @OnClick({R2.id.tetle_back, R2.id.tetle_tv_ok})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tetle_back) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tetle_tv_ok) {
            List<MaintenanceStatusType> datas =new ArrayList<>();
            datas.add(new MaintenanceStatusType("无量山车间"));
            datas.add(new MaintenanceStatusType("无量山车间"));
            datas.add(new MaintenanceStatusType("无量山车间"));
            datas.add(new MaintenanceStatusType("无量山车间"));
            datas.add(new MaintenanceStatusType("无量山车间"));
            datas.add(new MaintenanceStatusType("无量山车间"));
            datas.add(new MaintenanceStatusType("无量山车间"));
            String json = GsonUtil.getGson().toJson(datas);
            TopMaintenanceTypeDialog typeDialog = TopMaintenanceTypeDialog.newInstance(json);
            typeDialog.show(getSupportFragmentManager(),"");
            typeDialog.setOnDialogInforCompleted(new TopMaintenanceTypeDialog.OnDialogInforCompleted() {
                @Override
                public void dialogInforCompleted(String name) {

                }
            });
        }
    }
}
