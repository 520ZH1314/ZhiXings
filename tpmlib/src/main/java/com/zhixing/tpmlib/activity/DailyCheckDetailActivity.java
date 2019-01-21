package com.zhixing.tpmlib.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.DailyCheckAdapter;
import com.zhixing.tpmlib.adapter.DailyCheckDetailAdapter;
import com.zhixing.tpmlib.bean.AnomalousBean;
import com.zhixing.tpmlib.bean.CheckItem;
import com.zhixing.tpmlib.bean.CheckItemBean;
import com.zhixing.tpmlib.bean.CheckItemEntity;
import com.zhixing.tpmlib.bean.EquipmentBean;
import com.zhixing.tpmlib.bean.PicEntity;
import com.zhixing.tpmlib.utils.UploadUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/*
 * @Author smart
 * @Date 2018/12/25
 * @Des 进入日常点检项界面
 */
public class DailyCheckDetailActivity extends BaseTpmActivity implements SpringView.OnFreshListener{
    @BindView(R2.id.tetle_text)
    TextView tvTite;//标题文本标签
    @BindView(R2.id.spring)
    SpringView springView;
    @BindView(R2.id.mRecyclerView)
    RecyclerView mRecyclerView;
    //    相册返回图片的列表集合
    private List<String> imageList = new ArrayList<>();
    List<CheckItemEntity> data = new ArrayList<>();
    private DailyCheckDetailAdapter dailyCheckAdapter;
    private static final int REQUEST_IMAGE =1;
    private CheckItemEntity checkItemEntity;
    private CheckItemEntity checkItemEntity1;
    private List<CheckItemBean.RowsBean> rowsBeanLists=new ArrayList<>();
    private TextView tv_cell;
    private TextView tv_position;
    List<AnomalousBean> anomalousBeanList=new ArrayList<>();
    private String equipmentName;
    private SharedUtils sharedUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_check_detail;
        //        初始化EvnentBus
    }

    @Override
    public void process(Message msg) {
    }
    @Override
    public void newIniLayout() {
        sharedUtils = new SharedUtils("TPM");
        EventBus.getDefault().register(this);
        String matcheName = getIntent().getStringExtra("matcheName");
        equipmentName = sharedUtils.getStringValue("EquipmentName");
//        获取点检项的接口
        String tenantId = SharedPreferencesTool.getMStool(DailyCheckDetailActivity.this).getTenantId();
        //        获取设备id
        String matheId=sharedUtils.getStringValue("equipmentID");
        Map<String, String> params = new HashMap<String, String>();
        params.put("TenantId", tenantId);
        params.put("AppCode", "TPM");
        params.put("ApiCode", "GetMaintananceItemInfo");
        params.put("LineId", "");
        params.put("EquipmentId", matheId);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("AppCode", "TPM");
            jsonObject.put("ApiCode", "GetMaintananceItemInfo");
            jsonObject.put("TenantId", tenantId);
            jsonObject.put("LineId", "");
            jsonObject.put("EquipmentId", matheId);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        获取异常小类的接口
       // 获取工位的id
        String lineTpmId = SharedPreferencesTool.getMStool(this).getString("lineTpmId");
//        获取异常小类的接口
//        final String tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        Map<String, String> exceptionParams = new HashMap<String, String>();
        exceptionParams.put("AppCode","Andon");
        exceptionParams.put("TenantId",tenantId);
        exceptionParams.put("ApiCode","GetStationException");
        exceptionParams.put("IsEquipmentException","1");
        exceptionParams.put("LineStationId",lineTpmId);
        try {
            jsonObject.put("AppCode", "Andon");
            jsonObject.put("TenantId", tenantId);
            jsonObject.put("ApiCode", "GetStationException");
            jsonObject.put("IsEquipmentException", "1");
            jsonObject.put("LineStationId", lineTpmId);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        SharedPreferencesTool.getMStool(DailyCheckDetailActivity.this).getIp() + UrlUtil.Url
//        "https://sale.stdlean.com/api/CMP/ApiRegistrator/PostApiGateWay"
        httpPostVolley(SharedPreferencesTool.getMStool(DailyCheckDetailActivity.this).getIp() + UrlUtil.Url, exceptionParams, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    JSONArray rows = jsonObject.getJSONArray("rows");
                    for (int i = 0; i <rows.length() ; i++) {
                        String exceptionGroupName = rows.getJSONObject(i).getString("ExceptionGroupName");
                        String ExceptionId = rows.getJSONObject(i).getString("ExceptionId");
                        AnomalousBean anomalousBean=new AnomalousBean();
                        anomalousBean.setExceptionGroupName(exceptionGroupName);
                        anomalousBean.setExceptionId(ExceptionId);
                        P.c("exceptionGroupName"+exceptionGroupName);
                        anomalousBeanList.add(anomalousBean);
                    }
                    //dailyCheckAdapter.setExceptionData(anomalousBeanList);
                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                System.out.println(error.toString()+"DetailCheckActivity111111111");
            }
        },true);
        httpPostVolley(SharedPreferencesTool.getMStool(DailyCheckDetailActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    JSONArray rows = jsonObject.getJSONArray("rows");
                    List<CheckItemBean.RowsBean> rowsBeanList = new ArrayList<>();
                    for (int i = 0; i <rows.length() ; i++) {
                        JSONObject jsonObject1 = rows.getJSONObject(i);
//                       获取单元
                        String cell = jsonObject1.getString("Cell");
//                       获取位置
                        String position = jsonObject1.getString("Position");
//                       获取点检项
                        String description=jsonObject1.getString("Description");
//                       获取itemid
                        String itemId = jsonObject1.getString("ItemId");
//                       获取
                        String maintananceId = jsonObject1.getString("MaintananceId");
                        int seq = jsonObject1.getInt("Seq");
                        String fruit = jsonObject1.getString("Fruit");
                        String standardImage = jsonObject1.getString("StandardImage");
                        String gradeId = jsonObject1.getString("GradeId");
                        String classId = jsonObject1.getString("ClassId");
                        String actuallyImage = jsonObject1.getString("ActuallyImage");
                        CheckItemBean.RowsBean rowsBean=new CheckItemBean.RowsBean();
                        rowsBean.setCell(cell);
                        rowsBean.setPosition(position);
                        rowsBean.setItemId(itemId);
                        rowsBean.setSeq(seq);
                        rowsBean.setMaintananceId(maintananceId);
                        rowsBean.setDescription(description);
                        rowsBean.setFruit(fruit);
                        rowsBean.setGradeId(gradeId);
                        rowsBean.setClassId(classId);
                        rowsBean.setActuallyImage(actuallyImage);
                        rowsBeanList.add(rowsBean);
                        rowsBeanLists.addAll(rowsBeanList);
                        dailyCheckAdapter.notifyDataSetChanged();
                    }
                    for (int i = 0; i <rowsBeanLists.size() ; i++) {
                        P.c("getCell" + rowsBeanLists.get(i).getCell());
                        tv_cell.setText(rowsBeanLists.get(i).getCell());
                        tv_position.setText(rowsBeanLists.get(i).getPosition());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                P.c("DailyCheckDetailActivity:" + jsonObject.toString());
            }

            @Override
            public void error(VolleyError error) {
                P.c("DailyCheckDetailActivity:"+error.toString());
            }
        },true);
//        初始化数据
        initData();
    }


    private void initData() {
        tvTite.setText("日常点检项");
        List<CheckItemEntity> checkItemEntityList=new ArrayList<>();
        checkItemEntity = new CheckItemEntity();
        //设置上下拉事件
        springView.setListener(this);
        //设置springview的头和尾
        //设置上下控件
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        checkItemEntity.setMacheName("注塑机3");
        checkItemEntity1=new CheckItemEntity();
        checkItemEntity.setMacheName("注塑机2");
        data.add(checkItemEntity);
        data.add(checkItemEntity1);
//        设置列表的头布局
        View headerView=getLayoutInflater().inflate(R.layout.item_check_header, null);
        TextView tv_current_matche = headerView.findViewById(R.id.tv_current_matche);
        tv_current_matche.setText(equipmentName);
        tv_cell = headerView.findViewById(R.id.tv_cell);
        tv_position = headerView.findViewById(R.id.tv_position);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(DailyCheckDetailActivity.this));
        dailyCheckAdapter = new DailyCheckDetailAdapter(rowsBeanLists);
        dailyCheckAdapter.addHeaderView(headerView);
        mRecyclerView.setAdapter(dailyCheckAdapter);
//        设置dailyCheckAdapter的item点击事件
    }

    @Override
    public void onRefresh() {
//        上拉刷新数据
        finishFreshAndLoad();
    }
    @Override
    public void onLoadmore() {
//        下拉加载更多的数据
        finishFreshAndLoad();
    }
    //来设置刷新时间的
    private void finishFreshAndLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView.onFinishFreshAndLoad();
            }
        }, 1000);
    }
    /**
     * 接收选择筛选返回
     private event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    @SuppressWarnings("UnusedDeclaration")
    public void onEvent(PicEntity event) {
        //如果是日常检查
        if (event != null) {
            String picNum=event.getPicNum();
            checkItemEntity.setPicNum(picNum);
            dailyCheckAdapter.notifyDataSetChanged();
        }
    }
}
