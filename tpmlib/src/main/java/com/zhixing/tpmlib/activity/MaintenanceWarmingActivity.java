package com.zhixing.tpmlib.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.orhanobut.logger.Logger;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.MaintenanceWarmingAdapt;
import com.zhixing.tpmlib.bean.MaintenanceListDataEntity;
import com.zhixing.tpmlib.bean.MaintenanceWarnBean;
import com.zhixing.tpmlib.viewModel.MaintenanceWarnViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * @Author zjq
 * @Date 2018/12/24
 * @Des 进入保养警告界面
 */
public class MaintenanceWarmingActivity extends BaseTpmActivity  {
    @BindView(R2.id.tetle_text)
    TextView tvTitle;
    @BindView(R2.id.tetle_back)
    ImageView tetleBack;
    @BindView(R2.id.recyle_maintenance_warning)
    RecyclerView recyleMaintenanceWarning;
//    @BindView(R2.id.springview_maintenance_warning)
//    SpringView springviewMaintenanceWarning;
    @BindView(R2.id.tetle_tv_ok)
    TextView tetleTvOk;
    private MaintenanceWarnViewModel mMaintenanceWarnViewModel;
    private MaintenanceWarmingAdapt adapt;
    private SharedUtils sharedUtils;
    private String WorkShopId;
    private String LineListCode;
    private String LineStationCode;
    private int Total;//总数量
    private int page = 1;//默认页
    private int TotalPage;
    private int rows = 8;//每页显示多少条
    private String tpmStationCode;
    private String tpmStationName;

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
        initData();

    }

    private void initData() {
        showDialog("加载中");
        mMaintenanceWarnViewModel.initData(page, rows, Total, LineListCode, tpmStationCode, this).observe(MaintenanceWarmingActivity.this, new Observer<BaseResponse<MaintenanceListDataEntity>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<MaintenanceListDataEntity> maintenanceListDataEntityBaseResponse) {
                if (maintenanceListDataEntityBaseResponse.getMessage() == null) {
                    Total = maintenanceListDataEntityBaseResponse.getTotal();
                    List<MaintenanceWarnBean> beans = new ArrayList<>();
                    List<MaintenanceListDataEntity> rows = maintenanceListDataEntityBaseResponse.getRows();
                    int i = 1;
                    for (MaintenanceListDataEntity bean : rows) {
                        beans.add(new MaintenanceWarnBean(bean.getEquipmentName(),
                                bean.getEquipmentCode(), bean.getGradeName(),
                                bean.getStatus(), bean.getMaintanceDate(),
                                "0" + i )
                        );
                        i++;
                    }

                    adapt = new MaintenanceWarmingAdapt(R.layout.item_maintenance_warning, beans);
                    recyleMaintenanceWarning.setAdapter(adapt);
                    dismissDialog();
                }else{
                    dismissDialog();
                }
            }
        });
    }

    private void initView() {
        tvTitle.setText("保养警告");
        tetleTvOk.setVisibility(View.VISIBLE);
        sharedUtils = new SharedUtils("TpmSetting");
        LineListCode = sharedUtils.getStringValue("LineListCode");
        tpmStationCode = sharedUtils.getStringValue("tpmStationCode");
        tpmStationName = sharedUtils.getStringValue("tpmStationName");
        tetleTvOk.setText(tpmStationName);

//        springviewMaintenanceWarning.setListener(this);
//        //设置springview的头和尾
//        //设置上下控件
//        springviewMaintenanceWarning.setType(SpringView.Type.FOLLOW);
//        springviewMaintenanceWarning.setHeader(new DefaultHeader(this));
//        springviewMaintenanceWarning.setFooter(new DefaultFooter(this));
        mMaintenanceWarnViewModel = ViewModelProviders.of(this).get(MaintenanceWarnViewModel.class);
        recyleMaintenanceWarning.setLayoutManager(new LinearLayoutManager(this));


//


    }


//    private void finishFreshAndLoad() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                springviewMaintenanceWarning.onFinishFreshAndLoad();
//            }
//        }, 1000);
//    }


//    @Override
//    public void onRefresh() {
//        TotalPage = Total / rows;//总页数
//        if (page <= TotalPage) {
//            mMaintenanceWarnViewModel.RefrshData(LineListCode, LineStationCode, page, rows, Total).observe(this, (BaseResponse<MaintenanceListDataEntity> maintenanceListDataEntity) -> {
//                if (maintenanceListDataEntity.getRows() != null) {
//                    Total = maintenanceListDataEntity.getTotal();
//                    List<MaintenanceWarnBean> beans = new ArrayList<>();
//                    for (MaintenanceListDataEntity  rows : maintenanceListDataEntity.getRows()) {
//                        int i = 0;
//                        beans.add(new MaintenanceWarnBean(rows.getEquipmentName(),
//                                rows.getEquipmentCode(), rows.getGradeName(),
//                                rows.getStatus(), rows.getMaintanceDate(),
//                                "0" + i + 1));
//                    }
//                    adapt.setNewData(beans);
//
//                }
//
//            });
//            page++;
//
//        } else {
//            Toast.makeText(this, "暂无更多数据", Toast.LENGTH_SHORT).show();
//        }
//
//        finishFreshAndLoad();
//
//
//    }
//
//    @Override
//    public void onLoadmore() {
//        TotalPage = Total / rows;//总页数
//        if (page <= TotalPage) {
//            mMaintenanceWarnViewModel.RefrshData(LineListCode, LineStationCode, page, rows, Total).observe(this, maintenanceListDataEntity -> {
//                if (maintenanceListDataEntity.getRows() != null) {
//                    Total = maintenanceListDataEntity.getTotal();
//                    List<MaintenanceWarnBean> beans = new ArrayList<>();
//                    for (MaintenanceListDataEntity rows : maintenanceListDataEntity.getRows()) {
//                        int i = 0;
//                        beans.add(new MaintenanceWarnBean(rows.getEquipmentName(),
//                                rows.getEquipmentCode(), rows.getGradeName(),
//                                rows.getStatus(), rows.getMaintanceDate(),
//                                "0" + i + 1));
//                    }
//                    adapt.addData(beans);
//
//                }
//
//            });
//            page++;
//
//        } else {
//            Toast.makeText(this, "暂无更多数据", Toast.LENGTH_SHORT).show();
//        }
//        finishFreshAndLoad();
//
//    }


    @OnClick({R2.id.tetle_back, R2.id.tetle_tv_ok})
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
                MaintenanceWarmingActivity.this.showDialog("加载中");
                mMaintenanceWarnViewModel.initData(page, rows, Total, Linecode, code, MaintenanceWarmingActivity.this).observe(MaintenanceWarmingActivity.this, new Observer<BaseResponse<MaintenanceListDataEntity>>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse<MaintenanceListDataEntity> maintenanceListDataEntity) {

                        if (maintenanceListDataEntity.getRows() != null) {
                            Total = maintenanceListDataEntity.getTotal();
                            List<MaintenanceWarnBean> beans = new ArrayList<>();
                            int i = 0;
                            for (MaintenanceListDataEntity  rows : maintenanceListDataEntity.getRows()) {

                                beans.add(new MaintenanceWarnBean(rows.getEquipmentName(),
                                        rows.getEquipmentCode(), rows.getGradeName(),
                                        rows.getStatus(), rows.getMaintanceDate(),
                                        "0" + i ));
                                i++;
                            }

                            adapt.setNewData(beans);
                            MaintenanceWarmingActivity.this.dismissDialog();

                        } else {
                            MaintenanceWarmingActivity.this.dismissDialog();
                        }
                    }
                });

            }
        });
        commonSetSelectPop.showSheet();
    }
}
