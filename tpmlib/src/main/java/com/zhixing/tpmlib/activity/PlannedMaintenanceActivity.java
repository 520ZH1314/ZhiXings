package com.zhixing.tpmlib.activity;

import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.PlanetPagerAdapter;
import com.zhixing.tpmlib.adapter.PlannedDetailAdapter;
import com.zhixing.tpmlib.adapter.PlannedMmatenceAdapter;
import com.zhixing.tpmlib.adapter.SpinerAdapter;
import com.zhixing.tpmlib.bean.AnomalousBean;
import com.zhixing.tpmlib.bean.MaintenanceStatusType;
import com.zhixing.tpmlib.bean.PlannListBean;
import com.zhixing.tpmlib.bean.PlannetEntity;
import com.zhixing.tpmlib.utils.TabLayoutUtils;
import com.zhixing.tpmlib.view.SpinerPopWindow;
import com.zhixing.tpmlib.view.TopMaintenanceTypeDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * @Author smart
 * @Date 2019/1/2
 * @Des 计划保养的界面
 */
public class PlannedMaintenanceActivity extends BaseTpmActivity implements SpringView.OnFreshListener {
    @BindView(R2.id.tetle_text)
    TextView tvTitle;
    @BindView(R2.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.spring)
    SpringView springView;
    @BindView(R2.id.tetle_tv_ok)
    TextView tetleTvOk;
    List<PlannetEntity> plannetEntityLists = new ArrayList<>();

    private SpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;
    private String lineId;
    private String workId;
    private PlannedMmatenceAdapter adapter;
    @BindView(R2.id.tv_value)
    TextView tv_value;
    private View headerView;
    @BindView(R2.id.relativelayout)
    RelativeLayout relativeLayout;
    private String tpmLinecode;
    private String tpmStationCode;
    private String tpmLineid;
    private String tenantId;
    private List<MaintenanceStatusType> plannListBeans;
    private String json;

    @Override
    public int getLayoutId() {
        return R.layout.activity_planned_maintenance;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void newIniLayout() {
        tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
//        初始化控件
        initView();
        //        初始化数据
        initData();

    }

    private void initData() {
        tvTitle.setText("计划保养");
        tetleTvOk.setVisibility(View.VISIBLE);
        //        实例化查看明细的实体类
        SharedUtils shareUtil = new SharedUtils("TpmSetting");
        tpmLinecode = shareUtil.getStringValue("tpmLinecode");
        tpmStationCode = shareUtil.getStringValue("tpmStationCode");
        tpmLineid = shareUtil.getStringValue("tpmLineid");
        String tpmName = shareUtil.getStringValue("tpmName");
        tetleTvOk.setText(tpmName);
        getFromData(tpmLinecode, tpmStationCode,"","");
        //设置上下拉事件
        springView.setListener(this);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        实例化查看明细的适配器

    }

    private void getPlanListFromData() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "TPM");
        params.put("TenantId", tenantId);
        params.put("ApiCode", "GetGradeList");
        final JSONObject jsonObjet = new JSONObject();
        try {
            jsonObjet.put("AppCode", "TPM");
            jsonObjet.put("TenantId", tenantId);
            jsonObjet.put("ApiCode", "GetGradeList");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpPostVolley(SharedPreferencesTool.getMStool(this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                P.c(jsonObject.toString());
                try {
                    JSONArray rows = jsonObject.getJSONArray("rows");
                    plannListBeans = new ArrayList<>();
                    for(int i=0;i<rows.length();i++){
                        JSONObject jsonObject1 = rows.getJSONObject(i);
                        String gradeName = jsonObject1.getString("GradeName");
                        String GradeId = jsonObject1.getString("GradeId");
                        MaintenanceStatusType maintenanceStatusType=new MaintenanceStatusType(GradeId,gradeName);
                        PlannListBean plannListBean=new PlannListBean();
                       // plannListBean.setGradeId(GradeId);
                        plannListBeans.add(maintenanceStatusType);

                    }
                    json = GsonUtil.getGson().toJson(plannListBeans);
                    P.c(plannListBeans.size()+"");
                    TopMaintenanceTypeDialog typeDialog = TopMaintenanceTypeDialog.newInstance(json);
                    typeDialog.show(getSupportFragmentManager(), "");
                    typeDialog.setOnDialogInforCompleted(new TopMaintenanceTypeDialog.OnDialogInforCompleted() {
                        @Override
                        public void dialogInforCompleted(String name,String Graid) {

                            plannetEntityLists.clear();
                            getFromData(tpmLinecode,tpmStationCode,name,Graid);
                            tv_value.setText(name);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        },true);
    }

    private void getFromData(String tpmLinecode, String tpmStationCode,String GradeName,String GradeId) {
        String userCode = SharedPreferencesTool.getMStool(this).getUserCode();
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "TPM");
        params.put("TenantId", tenantId);
        params.put("ApiCode", "GetFirstMaintanceRecord");
        params.put("LineCode", tpmLinecode);
        params.put("GradeName", GradeName);
        params.put("GradeId", GradeId);
        params.put("StationCode", tpmStationCode);
        params.put("UserCode", userCode);
        final JSONObject jsonObjet = new JSONObject();
        try {
            jsonObjet.put("AppCode", "TPM");
            jsonObjet.put("TenantId", tenantId);
            jsonObjet.put("ApiCode", "GetFirstMaintanceRecord");
            jsonObjet.put("LineCode", tpmLinecode);
            jsonObjet.put("StationCode", tpmStationCode);
            jsonObjet.put("UserCode", userCode);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        P.c(SharedPreferencesTool.getMStool(this).getIp() + UrlUtil.Url);
        httpPostVolley(SharedPreferencesTool.getMStool(this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    JSONArray rows = jsonObject.getJSONArray("rows");
                    for (int i = 0; i < rows.length(); i++) {
                        List<PlannetEntity> plannetEntityList = new ArrayList<>();
                        PlannetEntity plannetEntity = new PlannetEntity();
//                        plannetEntityList.clear();
                        JSONObject jsonObject1 = rows.getJSONObject(i);
                        String EquipmentName = jsonObject1.getString("EquipmentName");
//                        设置设备名称
                        plannetEntity.setEquipmentName(EquipmentName);
//                        设置倒计时日期
                        String MaintanceDate = jsonObject1.getString("MaintanceDate");
                        plannetEntity.setMaintanceDate(MaintanceDate);
//                        获取设备id
                        String equipmentId = jsonObject1.getString("EquipmentId");
                        plannetEntity.setEquipmentId(equipmentId);
//                        获取设备classId
                        String ClassId = jsonObject1.getString("ClassId");
                        plannetEntity.setClassId(ClassId);
//                        获取设备PlanId
                        String PlanId = jsonObject1.getString("PlanId");
                        plannetEntity.setPlanId(PlanId);
//                        获取设备GradeId
                        String GradeId = jsonObject1.getString("GradeId");
                        plannetEntity.setGradeId(GradeId);
//上次保养时
                        String LastMaintanceDate = jsonObject1.getString("LastMaintanceDate");
                        plannetEntity.setLastMaintanceDate(LastMaintanceDate);
//                        保养人
                        String lastMaintanceUser = jsonObject1.getString("LastMaintanceUser");
                        plannetEntity.setLastMaintanceUser(lastMaintanceUser);
//                        保养设备的编号
                        String EquipmentCode = jsonObject1.getString("EquipmentCode");
                        plannetEntity.setEquipmentCode(EquipmentCode);
//                        设置设备保养的状态
                        String Status = jsonObject1.getString("Status");
                        plannetEntity.setStatus(Status);
                        plannetEntityList.add(plannetEntity);
                            plannetEntity.setNum((i+1)+"");
                        plannetEntityLists.addAll(plannetEntityList);
                    }

                    P.c(plannetEntityLists.size() + "plannetEntityLists===========");
                    //        设置列表的头布局
                    headerView = getLayoutInflater().inflate(R.layout.planned_header, null);
                    //relativeLayout = (RelativeLayout) headerView.findViewById(R.id.relativelayout);
                   // tv_value = (TextView) headerView.findViewById(R.id.tv_value);
                    adapter = new PlannedMmatenceAdapter(plannetEntityLists);
                    headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    //adapter.addHeaderView(headerView);
                    //        设置RecyclerView的适配器
                    mRecyclerView.setAdapter(adapter);
                    relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           getPlanListFromData();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                P.c(jsonObject.toString() + "PlannedMaintenanceActivity");
            }

            @Override
            public void error(VolleyError error) {
                P.c(error.toString() + "PlannedMaintenanceActivity");
            }
        }, true);
    }

    private void initView() {
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadmore() {
    }

    @OnClick({R2.id.tetle_back, R2.id.tetle_tv_ok})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tetle_back) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tetle_tv_ok) {
            getWorkPosition();
          /*  List<MaintenanceStatusType> datas = new ArrayList<>();
            datas.add(new MaintenanceStatusType("无量山车间1"));
            datas.add(new MaintenanceStatusType("无量山车间2"));
            datas.add(new MaintenanceStatusType("无量山车间3"));
            datas.add(new MaintenanceStatusType("无量山车间4"));
            datas.add(new MaintenanceStatusType("无量山车间5"));
            datas.add(new MaintenanceStatusType("无量山车间6"));
            datas.add(new MaintenanceStatusType("无量山车间7"));
            String json = GsonUtil.getGson().toJson(datas);
            TopMaintenanceTypeDialog typeDialog = TopMaintenanceTypeDialog.newInstance(json);
            typeDialog.show(getSupportFragmentManager(), "");
            typeDialog.setOnDialogInforCompleted(new TopMaintenanceTypeDialog.OnDialogInforCompleted() {
                @Override
                public void dialogInforCompleted(String name) {
                    tetleTvOk.setText(name);
                }
            });*/
        }
    }

    private void getWorkPosition() {
        getLineStationPop(tpmLineid, tpmLinecode);
    }

    private void getLineStationPop(String id, String Linecode) {
        workId = Linecode;
        CommonSetSelectPop commonSetSelectPop = new CommonSetSelectPop(this, null, "工位");
        commonSetSelectPop.setMidH(true);
        commonSetSelectPop.isDoall(false);
        commonSetSelectPop.getSet().put("ApiCode", "GetLineStationList");
        commonSetSelectPop.getSet().put("LineCode", Linecode);
        commonSetSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                SharedPreferencesTool.getMStool(PlannedMaintenanceActivity.this).setString("lineTpmId", id);
                tetleTvOk.setText(name);
                plannetEntityLists.clear();
                getFromData(tpmLinecode, code,"","");
            }
        });
        commonSetSelectPop.showSheet();
    }
}
