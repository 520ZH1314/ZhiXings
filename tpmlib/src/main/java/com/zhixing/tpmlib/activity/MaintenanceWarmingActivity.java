package com.zhixing.tpmlib.activity;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.DialogContentAdapter;
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
    private List<String> titleList;
    private List<String> contentList=new ArrayList<>();

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
        titleList= new ArrayList<String>();
        titleList.add("设备名称:");
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
                                "0" + i ,bean.getClassId(),bean.getEquipmentId(),bean.getPlanId(),bean.getGradeId(),bean.getMaintanceId())
                        );
                        i++;
                    }

                    adapt = new MaintenanceWarmingAdapt(R.layout.item_maintenance_warning, beans);
                    recyleMaintenanceWarning.setAdapter(adapt);
                    adapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                            if (contentList!=null){
                                contentList.clear();
                            }
                            contentList.add(beans.get(position).getMachineName());
                            showSexTypeDialog(titleList,contentList, beans, position);

                        }
                    });

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
                            for (MaintenanceListDataEntity bean : maintenanceListDataEntity.getRows()) {
                                beans.add(new MaintenanceWarnBean(bean.getEquipmentName(),
                                        bean.getEquipmentCode(), bean.getGradeName(),
                                        bean.getStatus(), bean.getMaintanceDate(),
                                        "0" + i ,bean.getClassId(),bean.getEquipmentId(),bean.getPlanId(),bean.getGradeId(),bean.getMaintanceId())
                                );
                                i++;
                            }
                            adapt.setNewData(beans);
                            MaintenanceWarmingActivity.this.dismissDialog();
                            adapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                                    if (contentList!=null){
                                        contentList.clear();
                                    }
                                    contentList.add(beans.get(position).getMachineName());
                                    showSexTypeDialog(titleList,contentList,beans,position);

                                }
                            });

                        } else {
                            MaintenanceWarmingActivity.this.dismissDialog();
                        }
                    }
                });

            }
        });
        commonSetSelectPop.showSheet();
    }




    private void showSexTypeDialog(List<String> titleList, List<String> contentList, List<MaintenanceWarnBean> beans, int position) {
        /* 列表弹窗 */
        final AlertDialog dialog = new AlertDialog.Builder(this,R.style.dialog_common).create();
        //透明
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager m = dialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = d.getWidth();
        dialog.getWindow().setAttributes(p);
        View view = LayoutInflater.from(this).inflate(R.layout.list_planet_dialog, null);
        ListView dialogRecyclerView = (ListView) view.findViewById(R.id.dialog_list);
//        弹出框确定的文本文件
        Button   btnSure = (Button) view.findViewById(R.id.btn_sure);
//        弹出框取消的文本文件
        ImageView  ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        DialogContentAdapter adapter = new DialogContentAdapter(titleList,contentList);
        dialogRecyclerView.setAdapter(adapter);
        dialog.setView(view);
        dialog.show();
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MaintenanceWarmingActivity.this,MyTextActivity.class);
                intent.putExtra("MaintenanceWarmType","MaintenanceWarmType");
                intent.putExtra("ClassId", beans.get(position).getClassId());
                intent.putExtra("EquipmentId",beans.get(position).getEquipmentId());
                intent.putExtra("GradeId",beans.get(position).getGradeId());
                intent.putExtra("PlanId",beans.get(position).getPlanId());
                intent.putExtra("Status",beans.get(position).getMachineStatus());
                intent.putExtra("MaintanceId",beans.get(position).getMaintanceId());

                MaintenanceWarmingActivity.this.startActivity(intent);
                dialog.dismiss();
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

}
