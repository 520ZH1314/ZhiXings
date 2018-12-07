package com.shuben.zhixing.module.mass;

import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.util.DensityUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.liulishuo.magicprogresswidget.MagicProgressBar;
import com.shuben.zhixing.module.mass.bean.MassItemBean;
import com.shuben.zhixing.module.mass.bean.QC_NoListBean;
import com.shuben.zhixing.module.mass.chart.PieManager;
import com.shuben.zhixing.module.mass.inter.AddNoF;
import com.shuben.zhixing.module.mass.widget.AddNoList;
import com.shuben.zhixing.module.mass.widget.ShowMassDetailNo;
import com.shuben.zhixing.module.mass.widget.ShowMassDetailOk;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.inter.Tips;
import com.base.zhixing.www.view.Toasty;
import com.shuben.zhixing.www.widget.CommonTips;
import com.shuben.zhixing.www.widget.XEditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class _SomeCheckActivity extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout._some_check_layout;
    }
    @Override
    public void process(Message msg) {
        switch (msg.what){
            case 1:
                //不良品
                int count = 0;
                for(int i=0;i<noListBeans.size();i++){
                    count+=Integer.parseInt(noListBeans.get(i).getNum());
                }
                edit01.setText(String.valueOf(count));
                count(true);
                break;
            case 2:
                //良品
                int coun = 0;
                for(int i=0;i<okListBeans.size();i++){
                    coun+=Integer.parseInt(okListBeans.get(i).getNum());
                }
                edit00.setText(String.valueOf(coun));
                count(false);
                break;
        }
    }
    private void count(boolean printPie){
        float ok = 0;
        float no  = 0;
        int total = 0;
        try {
            ok = Float.parseFloat(edit00.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            no = Float.parseFloat(edit01.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            total = Integer.parseInt(itemBean.getpNum());
        }catch (NumberFormatException e){}
        float all = (ok+no)/total;
        P.c("是多少"+all);
        process_all.setPercent(FileUtils.formatFloat(all));

        process_all_v.setText("已检数量: "+((int)(ok+no))+"/ 检验进度:"+FileUtils.formatFloat(all*100)+"%");
        float ook = ok/(ok+no);
        process_ok.setPercent(ook);
        process_ok_v.setText("良品数量:"+(int)ok+" / 合格率:"+FileUtils.formatFloat((ook*100))+"%");
        if(printPie){
            processNoPie();
        }
        if((ok+no)==total){
            //就不能操作抽检了
            edit2.setEnabled(false);
            add_no.setEnabled(false);
        }
    }
    private void processNoPie(){
        chart1.post(new Runnable() {
            @Override
            public void run() {
                int w = DensityUtil.getWindowWidth(_SomeCheckActivity.this)-DensityUtil.dip2px(_SomeCheckActivity.this,40);
                chart1.setLayoutParams(new LinearLayout.LayoutParams(w,w));
            }
        });

        if(noListBeans.size()!=0){
            Map<String,QC_NoListBean> temp = new HashMap<>();
            for(int i=0;i<noListBeans.size();i++){
                String key = noListBeans.get(i).getDes();
                P.c(key+"==="+noListBeans.get(i).getDes());
                if(temp.containsKey(key)){
                    QC_NoListBean b = temp.get(key);
                    b.setNum(String.valueOf(Integer.parseInt(b.getNum())+Integer.parseInt(noListBeans.get(i).getNum())));
                }else{
                    temp.put(key,noListBeans.get(i));
                }
            }
            Set it = temp.keySet();

            Iterator i = it.iterator();
            pieEntries.clear();
            while(i.hasNext()){
                String key  = i.next().toString();

                pieEntries.add(new PieEntry(Float.parseFloat(temp.get(key).getNum()),temp.get(key).getDes()));
            }
            pieManager.showPieChart("不良类型数",pieEntries);

        }
    }
    private PieManager pieManager;
    private PieChart chart1;
    private TextView tetle_text,process_ok_v,process_all_v;
    private TextView edit00,edit0,edit2,get_detail_no,get_detail_ok,add_no,edit01;
    private XEditText edit1;
    private TextView item0,item1,item2,item3,item4,item5,item6;
    private MagicProgressBar process_all,process_ok;
    private MassItemBean itemBean;
    private ArrayList<PieEntry> pieEntries = new ArrayList<>();
    private LinearLayout old_1,old_0;
    private TextView sure;
    @Override
    public void initLayout() {
        if(getIntent().hasExtra("obj")){
            itemBean = (MassItemBean) getIntent().getSerializableExtra("obj");
        }
        sure  = findViewById(R.id.sure);
        if( getIntent().hasExtra("old")){
            old_0 = findViewById(R.id.old_0);
            old_1 = findViewById(R.id.old_1);

            old_0.setVisibility(View.GONE);
            old_1.setVisibility(View.GONE);
            sure.setVisibility(View.GONE);
        }
        setStatus(-1);
        chart1 = findViewById(R.id.chart1);
        pieManager  = new PieManager(_SomeCheckActivity.this,chart1);
        process_ok = findViewById(R.id.process_ok);
        process_all = findViewById(R.id.process_all);
        process_ok_v = findViewById(R.id.process_ok_v);
        process_all_v = findViewById(R.id.process_all_v);
        get_detail_no = findViewById(R.id.get_detail_no);
        get_detail_ok = findViewById(R.id.get_detail_ok);
        tetle_text = findViewById(R.id.tetle_text);
        add_no = findViewById(R.id.add_no);

        tetle_text.setText("抽检");
        edit00 = findViewById(R.id.edit00);
        edit01 = findViewById(R.id.edit01);
        edit0 = findViewById(R.id.edit0);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit00.setText("0");
        edit1.setTextEx("1");//default values
        edit0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //减少
                Integer v = Integer.parseInt(edit00.getText().toString());
                Integer v0 = Integer.parseInt(edit1.getTextEx().toString());
                edit00.setText(String.valueOf(v-v0<0?0:v-v0));
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonTips commonTips = new CommonTips(_SomeCheckActivity.this,getHandler());
                commonTips.init("NG","OK","抽检最终判定");
                commonTips.setI(new Tips() {
                    @Override
                    public void cancel() {
                        CommonTips c = new CommonTips(_SomeCheckActivity.this,getHandler());
                        c.init("取消","确定","检验单NG,是否完成");
                        c.setI(new Tips() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void sure() {
                                commitOkNo(0);
                            }
                        });
                        c.showSheet();
                    }

                    @Override
                    public void sure() {
                        CommonTips c = new CommonTips(_SomeCheckActivity.this,getHandler());
                        c.init("取消","确定","检验单OK,是否完成");

                        c.setI(new Tips() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void sure() {
                                commitOkNo(1);
                            }
                        });
                        c.showSheet();
                    }
                });
                commonTips.showSheet();
            }
        });
        //合格增加
        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Integer v = Integer.parseInt(edit00.getText().toString());
                    String count = edit1.getTextEx().toString();
                    Integer v0 = Integer.parseInt(count);
                    addOkOrNo(count,"1","");
                }catch (Exception e){

                }

                //edit00.setText(String.valueOf(v+v0));
            }
        });
        get_detail_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMassDetailNo detail =new ShowMassDetailNo(_SomeCheckActivity.this,"抽检不良数明细",itemBean.getNo(),"2");
                detail.showSheet();
            }
        });
        get_detail_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMassDetailOk detailOk = new ShowMassDetailOk(_SomeCheckActivity.this,"抽检合格数明细","2",itemBean.getNo());
                detailOk.showSheet();
            }
        });
        //不良增加
        add_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoList noList = new AddNoList(_SomeCheckActivity.this);
                noList.setAddNoF((count, reson) ->   addOkOrNo(count,"0",reson) );
                noList.showSheet();
            }
        });
        item0 = findViewById(R.id.item0);
        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        item3 = findViewById(R.id.item3);
        item4 = findViewById(R.id.item4);
        item5 = findViewById(R.id.item5);
        item6 = findViewById(R.id.item6);
        CHECK_TYPE = getIntent().getIntExtra("type",-1);
        if(getIntent().hasExtra("isRight")&&CHECK_TYPE==2){
            //
            item0.setText("工单号:"+itemBean.getNo() );
            item1.setVisibility(View.GONE);//临时占用，后面可以放型号
            item4.setText("抽检状态:"+itemBean.getState());
            item2.setText("批次号:"+itemBean.getpNo());
            item3.setText("抽检数量:"+itemBean.getCount());
            item5.setText("送检时间:"+itemBean.getData()+" "+itemBean.getTime());
            item6.setText("抽检人:"+itemBean.getCreatePerson());
        }else{
            item0.setText("工单号:"+itemBean.getNo());
            item1.setText("车间/线体:"+itemBean.getWork()+"/"+itemBean.getLine());
            item2.setText("产品名称:"+itemBean.getProduct());
            item4.setText("送检时间:"+itemBean.getData()+" "+itemBean.getTime());
            item5.setText("批次号:"+itemBean.getpNo());
            item6.setText("批次数量:"+itemBean.getpNum());
        }

        loadDataNo();
        loadDataOk();
    }
    private int CHECK_TYPE = -1;
    //增加不良
    private void addNo(){

    }

    /**
     * 提交OK或者NG结果
     */
    private void commitOkNo(int state){
        /*
        productCode：产品型号
workNO：工单号
picNO：批次号
state：状态 1、合格 0、不合格
TenantId：租列号
picCount：批次数量
createPerson：添加人

         */
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", "QC");
        params.put("ApiCode", "EditAddProReport");
        params.put("productCode",itemBean.getProductCode());
        params.put("workNO",itemBean.getNo());
        params.put("picNO",itemBean.getpNo());
        params.put("state",String.valueOf(state));
        params.put("TenantId",SharedPreferencesTool.getMStool(_SomeCheckActivity.this).getTenantId());
        params.put("picCount",itemBean.getpNum());
        params.put("createPerson",SharedPreferencesTool.getMStool(_SomeCheckActivity.this).getUserName());
        httpPostSONVolley(SharedPreferencesTool.getMStool(_SomeCheckActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    if(jsonObject.getBoolean("status")){
                        Toasty.INSTANCE.showToast(_SomeCheckActivity.this,"已完成提交");
                        SharedPreferencesTool.getMStool(_SomeCheckActivity.this).clear("mass_cache2");
                        SharedPreferencesTool.getMStool(_SomeCheckActivity.this).clear("mass_cache2PC_NO");
                        SharedPreferencesTool.getMStool(_SomeCheckActivity.this).clear("mass_cache2PC_NUM");
                        AppManager.getAppManager().finishActivity();
                     }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        });


    }


    //增加合格

    /**
     * 1 合格，0不良
     * @param count
     */
    private void addOkOrNo(String count,String type,String reson){
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", "QC");
        params.put("ApiCode", "EditCheckInfo");
        params.put("count",count);
        params.put("noreason",reson);
        params.put("tCount",String.valueOf(itemBean.getCount()));
        params.put("WorkNO",itemBean.getNo());
        params.put("LineId",itemBean.getLineId());
        params.put("LineCode",itemBean.getLineCode());
        params.put("productCode",itemBean.getProductCode());//产品型号
        params.put("CheckType","2");//根据传入的类型判断是全检单还是抽检单
        params.put("PiCiNo",itemBean.getpNo());
        params.put("workPicCount",itemBean.getpNum());
        params.put("ProductType",type);//不良品
        params.put("CreatePerson", SharedPreferencesTool.getMStool(_SomeCheckActivity.this).getString("UserName"));
        params.put("TenentId",SharedPreferencesTool.getMStool(_SomeCheckActivity.this).getTenantId());
        params.put("PlanDate",itemBean.getAll_t());
        httpPostVolley(SharedPreferencesTool.getMStool(_SomeCheckActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                JSONObject o = null;
                try {
                    o = jsonObject.getJSONObject("result");
                    if(o.getBoolean("status")){
                        if(type.equals("0")){
                            loadDataNo();
                        }else{
                            loadDataOk();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void error(VolleyError error) {

            }
        },true);
    }


    private ArrayList<QC_NoListBean> noListBeans = new ArrayList<>();
    private ArrayList<QC_NoListBean> okListBeans = new ArrayList<>();
    //查询不良
    private  void loadDataNo(){
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", "QC");
        params.put("ApiCode", "GetAllProductCheckList");
        params.put("WorkNo",itemBean.getNo());
        params.put("CheckType","2");//根据传入的类型判断是全检单还是抽检单
        params.put("ProductType","0");//不良品
        params.put("PiCiNo",itemBean.getpNo());
//        params.put("TenantId",SharedPreferencesTool.getMStool(context).getTenantId());
        httpPostVolley(SharedPreferencesTool.getMStool(_SomeCheckActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("rows");
                    noListBeans.clear();
                    int len  = jsonArray.length();
                    for(int i=0;i<len;i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        QC_NoListBean qb = new QC_NoListBean();
                        qb.setDes(object.getString("ExceptionName"));
                        qb.setId(object.getString("ID"));
                        qb.setNo(String.valueOf(i));
                        qb.setTime(object.getString("CreateTime").split("T")[1]);
                        qb.setNum(object.getString("ItemCount"));
                        noListBeans.add(qb);
                    }
                    getHandler().sendEmptyMessage(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void error(VolleyError error) {

            }
        },false);
    }
    //查询合格
    private  void loadDataOk(){
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", "QC");
        params.put("ApiCode", "GetAllProductCheckList");
        params.put("WorkNo",itemBean.getNo());
        params.put("CheckType","2");//根据传入的类型判断是全检单还是抽检单
        params.put("ProductType","1");//良品
        params.put("PiCiNo",itemBean.getpNo());
//        params.put("TenantId",SharedPreferencesTool.getMStool(context).getTenantId());
        httpPostVolley(SharedPreferencesTool.getMStool(_SomeCheckActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("rows");
                    okListBeans.clear();
                    int len  = jsonArray.length();
                    for(int i=0;i<len;i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        QC_NoListBean qb = new QC_NoListBean();
                        qb.setDes(object.getString("ExceptionName"));
                        qb.setId(object.getString("ID"));
                        qb.setNo(String.valueOf(i));
                        qb.setTime(object.getString("CreateTime").split("T")[1]);
                        qb.setNum(object.getString("ItemCount"));
                        okListBeans.add(qb);
                    }
                    getHandler().sendEmptyMessage(2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void error(VolleyError error) {

            }
        },false);
    }

}
