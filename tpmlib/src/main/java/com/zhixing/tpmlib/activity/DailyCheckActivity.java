package com.zhixing.tpmlib.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.JsRet;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SelectFac;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.base.zhixing.www.widget.pullrefreshlayout.PullRefreshLayout;
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
import com.orhanobut.logger.Logger;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.DailyCheckAdapter;
import com.zhixing.tpmlib.adapter.DialogContentAdapter;

import com.zhixing.tpmlib.bean.EquipmentBean;
import com.zhixing.tpmlib.bean.EquipmentEtity;
import com.zhixing.tpmlib.bean.ImageEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/*
 * @Author smart
 * @Date 2018/12/24
 * @Des 进入日常点检的界面发挥发挥发挥
 */
public class DailyCheckActivity extends BaseTpmActivity implements SpringView.OnFreshListener {
    @BindView(R2.id.tetle_text)
    TextView tvTite;//标题文本标签
    @BindView(R2.id.spring)
    SpringView springView;
    @BindView(R2.id.mRecyclerView)
    RecyclerView mRecyclerView;
    List<EquipmentBean> datas = new ArrayList<>();
    private DailyCheckAdapter dailyCheckAdapter;
    private TextView tvSure;
    private TextView tvCancel;
    private List<EquipmentBean.RowsBean> rows;
    private String equipmentId;
    private EquipmentBean equipmentBean;
    private ArrayList<EquipmentBean> equipmentBeans;
    //    private EquipmentBean.RowsBean rowsBean;
    private String classId;
    @BindView(R2.id.tv_cell)
    TextView tvCell;
    private String tenantId;
    private String tpmLinecode;
    private String tpmLineName;
    private SharedUtils shareUtil;
    private List<EquipmentEtity> equipmentEtityList;
    @BindView(R2.id.btn_select)
    LinearLayout btn_select;
    private List<ImageEntity> imgList;
    private ACache aCache;

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_check;
    }

    @Override
    public void process(Message msg) {
    }

    @Override
    public void newIniLayout() {
       //        实例化查看明细的实体类
        shareUtil = new SharedUtils("TpmSetting");
        tpmLinecode = shareUtil.getStringValue("tpmLinecode");
        tpmLineName = shareUtil.getStringValue("tpmLineName");
//    初始化数据
        initData();
    }

    private void initData() {

        //设置上下拉事件
        springView.setListener(this);
        //设置springview的头和尾
        //设置上下控件
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        tvTite.setText("日常点检");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getFroData(tpmLinecode);

    }

    private void getImgeData(List<EquipmentEtity> equipmentEtityList) {
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        Map<String, String> imageParams = new HashMap<String, String>();
        imageParams.put("TenantId", tenantId);
        imageParams.put("AppCode", "TPM");
        imageParams.put("ApiCode", "EditEquipmentImg");
        P.c((new JSONObject(imageParams)).toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(this).getIp() + UrlUtil.Url,
                new JSONObject(imageParams), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                P.c(jsonObject.toString());
                try {
                    JSONArray rows = jsonObject.getJSONArray("rows");
                    imgList = new ArrayList<>();
                    for(int i=0;i<rows.length();i++){
                        ImageEntity imageEntity=new ImageEntity();
                        JSONObject jsonObject1 = rows.getJSONObject(i);
                        String classId = jsonObject1.getString("ClassId");
                        imageEntity.setClassId(classId);
                        String filePath = jsonObject1.getString("FilePath");
                        imageEntity.setFilePath(filePath);
                        imgList.add(imageEntity);

                    }
                    Logger.d(imgList.get(0));
                    String json = GsonUtil.getGson().toJson(imgList);
                    shareUtil.setStringValue("Tpm_DailyCheck",json);
                    dailyCheckAdapter = new DailyCheckAdapter(R.layout.item_requrement,equipmentEtityList);
                    View headerView = getLayoutInflater().inflate(R.layout.item_line_select, null);
                    headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    //   LinearLayout btn_select = headerView.findViewById(R.id.btn_select);
                    // tvCell = (TextView) headerView.findViewById(R.id.tv_cell);
//                                tvCell.setText(tpmLineName);
                    // dailyCheckAdapter.addHeaderView(headerView);
                    mRecyclerView.setAdapter(dailyCheckAdapter);
                    btn_select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getWorkPosition();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                P.c(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                P.c(error.toString());
            }
        });

        requestQueue1.add(newMissRequest);
    }

    private void getFroData(String tpmLinecode) {//        获取图片的接口
        tenantId = SharedPreferencesTool.getMStool(DailyCheckActivity.this).getTenantId();
        Map<String, String> params = new HashMap<String, String>();
        params.put("TenantId", tenantId);
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetEquipmentList");
        params.put("LineCode", tpmLinecode);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("AppCode", "EPS");
            jsonObject.put("ApiCode", "GetEquipmentList");
            jsonObject.put("TenantId", tenantId);
            jsonObject.put("LineCode", tpmLinecode);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpPostVolley(SharedPreferencesTool.getMStool(DailyCheckActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                P.c(jsonObject.toString() + "---------");
                try {
                    JSONArray rows = jsonObject.getJSONArray("rows");
                    equipmentEtityList = new ArrayList<>();

                    for (int i = 0; i < rows.length(); i++) {
                        EquipmentEtity equipmentBean = new EquipmentEtity();
                        JSONObject jsonObject1 = rows.getJSONObject(i);
                        String equipmentName = jsonObject1.getString("EquipmentName");
                        String equipmentCode = jsonObject1.getString("EquipmentCode");
                        String equipmentId = jsonObject1.getString("EquipmentId");
                        String ClassId = jsonObject1.getString("ClassId");
                        equipmentBean.setEquipmentName(equipmentName);
                        equipmentBean.setEquipmentCode(equipmentCode);
                        equipmentBean.setClassId(ClassId);
                        equipmentBean.setEquipmentId(equipmentId);
                        equipmentEtityList.add(equipmentBean);


                    }
                    getImgeData(equipmentEtityList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void error(VolleyError error) {

            }

        }, true);

    }

    private void getWorkPosition() {
        CommonSetSelectPop commonSetSelectPop = new CommonSetSelectPop(this, null, "产线");
        commonSetSelectPop.setMidH(true);
        commonSetSelectPop.isDoall(true);
        commonSetSelectPop.getSet().put("ApiCode", "GetLineList");
        commonSetSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                getFroData(code);
                tvCell.setText(name);

            }
        });
        commonSetSelectPop.showSheet();

    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "上拉加载更多", Toast.LENGTH_SHORT).show();
        dailyCheckAdapter.notifyDataSetChanged();
        //这个方法就是在刷新或者加载1秒的时间后关闭
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

    @Override
    public void onLoadmore() {
        dailyCheckAdapter.notifyDataSetChanged();
        finishFreshAndLoad();
        Toast.makeText(this, "下拉加载更多", Toast.LENGTH_SHORT).show();
    }
}
