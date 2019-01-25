package com.zhixing.tpmlib.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.PlannedDetailAdapter;
import com.zhixing.tpmlib.bean.PicturesBean;
import com.zhixing.tpmlib.bean.PlanDatailBean;
import com.zhixing.tpmlib.bean.PlanMatheBean;
import com.zhixing.tpmlib.service.RetrofitInterface;
import com.zhixing.tpmlib.utils.RetrofitUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
 * @Author smart
 * @Date 2019/1/2
 * @Des 查看明细的界面
 */
public class PlannedDetailActivity extends BaseTpmActivity implements SpringView.OnFreshListener{
    @BindView(R2.id.tetle_text)
    TextView tvTite;//标题文本标签
    @BindView(R2.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.spring)
    SpringView springView;
//    查看明细的实体类集合
    List<PlanDatailBean> plannedDetailEntityList=new ArrayList();
    private String equipmentId;
    private String classId;
    private String gradeId;
    private String planId;
    private List<PlanDatailBean> planDatailBeanList;
    private String equipmentName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_planned_detail;
    }
    @Override
    public void process(Message msg) {

    }

    @Override
    public void newIniLayout() {
//        初始化数据
       initData();
        //classId = rowsBean.getClassId();
        String tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        Map<String,String> map=new HashMap<>();
        map.put("AppCode","TPM");
        map.put("ApiCode","GetFirstMaintanceInfo");
        map.put("TenantId",tenantId);
        map.put("GradeId",gradeId);
        map.put("EquipmentId",equipmentId);
        map.put("ClassId",classId);
        map.put("PlanId",planId);
        JSONObject jsonObjects = new JSONObject();
        P.c(jsonObjects.toString()+"PlannedDetailActivity");
        try {
            jsonObjects.put("AppCode", "TPM");
            jsonObjects.put("ApiCode", "GetFirstMaintanceInfo");
            jsonObjects.put("TenantId", tenantId);
            jsonObjects.put("GradeId",gradeId);
            jsonObjects.put("EquipmentId",equipmentId);
            jsonObjects.put("ClassId",classId);
            jsonObjects.put("PlanId",planId);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpPostVolley(SharedPreferencesTool.getMStool(this).getIp() + UrlUtil.Url, map, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    JSONArray rows = jsonObject.optJSONArray("rows");
                    planDatailBeanList = new ArrayList<>();
                    for (int i = 0; i <rows.length() ; i++) {

                        PlanDatailBean planDatailBean=new PlanDatailBean();
                        String Cell = rows.getJSONObject(i).getString("Cell");
                        planDatailBean.setCell(Cell);
                        String Position = rows.getJSONObject(i).getString("Position");
                        planDatailBean.setPosition(Position);
                        String Description = rows.getJSONObject(i).getString("Description");
                        planDatailBean.setDescription(Description);
                        String Operator = rows.getJSONObject(i).getString("Operator");
                        planDatailBean.setOperator(Operator);
                        String Fruit = rows.getJSONObject(i).getString("Fruit");
                        planDatailBean.setFruit(Fruit);
                        planDatailBean.setNum("序号"+(i+1));
                        planDatailBeanList.add(planDatailBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (planDatailBeanList.size()!=0&&planDatailBeanList!=null){
                    //        实例化查看明细的适配器
                    PlannedDetailAdapter adapter=new PlannedDetailAdapter(planDatailBeanList);
                    //        设置adapter的头布局
                    View headerView=getLayoutInflater().inflate(R.layout.item_check_header, null);
                    TextView tv_current_matche = headerView.findViewById(R.id.tv_current_matche);
                    TextView tv_cell = headerView.findViewById(R.id.tv_cell);
                    TextView tv_position = headerView.findViewById(R.id.tv_position);
                    for (int i = 0; i <planDatailBeanList.size() ; i++) {
                        tv_cell.setText(planDatailBeanList.get(i).getCell());
                        tv_position.setText(planDatailBeanList.get(i).getPosition());

                    }
                    tv_current_matche.setText(equipmentName);
                    headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                    adapter.addHeaderView(headerView);
//        设置RecyclerView的适配器
                    mRecyclerView.setAdapter(adapter);
                    P.c(jsonObject.toString()+"PlannedDetailActivity");
                }
            }
            @Override
            public void error(VolleyError error) {
                P.c(error.toString()+"PlannedDetailActivity");
            }
        },true);
    }

    private void initData() {
        tvTite.setText("查看明细");
        String tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        String userCode = SharedPreferencesTool.getMStool(this).getUserCode();
        PlanMatheBean planMatheBean = (PlanMatheBean) getIntent().getSerializableExtra("planMatheBean_data");
        equipmentId = planMatheBean.getEquipmentId();
        P.c(equipmentId +"PlannedDetailActivity");
        classId = planMatheBean.getClassId();
        gradeId = planMatheBean.getGradeId();
        planId = planMatheBean.getPlanId();
        equipmentName = planMatheBean.getEquipmentName();
        Map<String,String> map=new HashMap<>();
        map.put("AppCode","TPM");
        map.put("ApiCode","GetFirstMaintanceInfo");
        map.put("TenantId",tenantId);
        map.put("GradeId",gradeId);
        map.put("EquipmentId",equipmentId);
        map.put("ClassId",classId);
        map.put("PlanId",planId);
       // getFromData(map);
        //设置上下拉事件
        springView.setListener(this);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        //        设置RecyclerView的布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getFromData(Map<String,String> map) {
        RetrofitInterface retrofitInterface = RetrofitUtil.getInstance(SharedPreferencesTool.getMStool(this).getIp()).getRetrofitInterface();
        retrofitInterface.getPlanDatailBean(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlanDatailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(PlanDatailBean planDatailBean) {
                        Log.e("Mian",planDatailBean.toString());
                        //plannedDetailEntityList.add();
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("myMessage", e.toString());
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
    //上拉刷新的时候回调该方法
    @Override
    public void onRefresh() {

    }
//加载更多的时候回调该方法
    @Override
    public void onLoadmore() {
    }
}
