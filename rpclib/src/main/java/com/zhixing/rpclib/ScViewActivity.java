package com.zhixing.rpclib;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.google.gson.Gson;
import com.zhixing.beans.NoItem;
import com.zhixing.beans.ScViewBean;
import com.zhixing.common.Common;
import com.zhixing.common.DateTimeUtil;
import com.zhixing.view.CommonNoSelectPop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ScViewActivity extends BaseRpcActivity {
    @BindView(R2.id.tetle_text)
    TextView tetle_text;
    @BindView(R2.id.item0)
    TextView item0;
    @BindView(R2.id.item00)
    TextView item00;
    @BindView(R2.id.layout4)
    RelativeLayout layout4;
    @BindView(R2.id.sc_tip)
    TextView sc_tip;
    private SharedUtils sharedUtils;
    private ArrayList<NoItem> noItems = new ArrayList<>();

    private void loadNos() {

        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetRPCOrderNoData");
        params.put("UserId", SharedPreferencesTool.getMStool(ScViewActivity.this).getUserId());
        params.put("TenantId", SharedPreferencesTool.getMStool(ScViewActivity.this).getTenantId());
        params.put("LineId", sharedUtils.getStringValue("line_id"));
        params.put("IsToday","2");
        httpPostVolley(SharedPreferencesTool.getMStool(ScViewActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("status").equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        int len = jsonArray.length();
                        noItems.clear();
                        for (int i = 0; i < len; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            NoItem noItem = new NoItem();
                            noItem.setNo(object.getString("OrderNo"));
                            noItem.setName(object.getString("ProductName"));
                            noItem.setStatus(object.getInt("State"));
                            noItems.add(noItem);
                        }
                        getHandler().sendEmptyMessage(2);
                        dismissDialog();
                    }else{
                        Toasty.INSTANCE.showToast(ScViewActivity.this,jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        }, "获取工单列表");

    }

    private void selectLine(){
        if (sharedUtils.getStringValue("workshop_id").length() == 0) {
            Toasty.INSTANCE.showToast(ScViewActivity.this, "请先选择车间");
            return;
        }
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(ScViewActivity.this, getHandler(), "产线");
        setSelectPop.getSet().put("ApiCode", "GetLineList");
        setSelectPop.getSet().put("WorkShopId", sharedUtils.getStringValue("workshop_id"));
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                load(id,name,"获取工单列表");
              //  item0.setText(name);
            }


        });
        setSelectPop.showSheet();
    }

    @Override
    public void newIniLayout() {
        setState(-1);
        sharedUtils = new SharedUtils(Common.SHARED_);
        tetle_text.setText("生产看板");
        sc_tip.setVisibility(View.INVISIBLE);
        item0.setText("加载中...");
        item0.setOnClickListener((v)->{
            selectLine();
        });
        layout4.setOnClickListener((v)->{
            //切换成直接选择工单
//            Intent intent = new Intent(ScViewActivity.this,ScViewSelectActivity.class);
//            startActivityForResult(intent,100);
          // loadNos();
        });
      /*  if(sharedUtils.getStringValue("sc_nos").length()!=0){
             item00.setText(sharedUtils.getStringValue("sc_nos"));
        }*/
        load(sharedUtils.getStringValue("line_id"),sharedUtils.getStringValue("line_name"),"获取工单列表");
        heartDisposable = Observable.interval(8, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()). observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        load(sharedUtils.getStringValue("line_id"),sharedUtils.getStringValue("line_name"),null);
                        P.c("~~~~~~~~~");
                    }
                });

    }
    @BindView(R2.id.ch3)
    CheckBox ch3;
    @BindView(R2.id.ch2)
    CheckBox ch2;
    @BindView(R2.id.ch1)
    CheckBox ch1;
    @BindView(R2.id.ch0)
    CheckBox ch0;
    private void setState(int STATU){
        switch (STATU) {
            case 0:

                ch0.setChecked(true);
                ch1.setChecked(false);
                ch2.setChecked(false);
                ch3.setChecked(false);

                break;
            case 1:


                ch0.setChecked(false);
                ch1.setChecked(true);
                ch2.setChecked(false);
                ch3.setChecked(false);
                break;
            case 2:

                ch0.setChecked(false);
                ch1.setChecked(false);
                ch2.setChecked(true);
                ch3.setChecked(false);
                break;
            case 3:
                ch0.setChecked(false);
                ch1.setChecked(false);
                ch2.setChecked(false);
                ch3.setChecked(true);
                break;
        }
    }
    Disposable heartDisposable;
    @Override
    public int getLayoutId() {
        return R.layout.rpc_sc_view_activity;
    }

    @Override
    public void process(Message msg) {
            switch (msg.what){
                case 2:
                   /* CommonNoSelectPop selectPop = new CommonNoSelectPop(ScViewActivity.this, getHandler(), "请选择工单");
                    selectPop.setData(noItems);
                    selectPop.setSelect(new SetSelect() {
                        @Override
                        public void select(String id, String code, String name) {


                            String no = name;
                            sharedUtils.setStringValue("sc_nos",no);
                            item00.setText(sharedUtils.getStringValue("sc_nos"));
                            load(sharedUtils.getStringValue("sc_nos"),sharedUtils.getStringValue("sc_uph"),sharedUtils.getStringValue("sc_num"));
                        }
                    });
                    selectPop.showSheet();*/
                    break;
                case 1:
                    String txt  = (String) msg.obj;
                    item0.setText(txt);

                    setState(viewBean.getState());
                    sc_tip.setVisibility(View.VISIBLE);
                    switch (viewBean.getState()){
                        case 0:
                            sc_tip.setText(getResources().getString(R.string.state0));
                            break;
                        case 1:
                            sc_tip.setText(getResources().getString(R.string.state1));
                            break;
                        case 2:
                            sc_tip.setText(getResources().getString(R.string.state2));
                            break;
                        case 3:
                            sc_tip.setText(getResources().getString(R.string.state3));
                            break;
                    }
                    item00.setText(viewBean.getProductCode());
                    item1.setText(viewBean.getStopCount());
                    item2.setText(DateTimeUtil.minConvertDayHourMin(Double.parseDouble(viewBean.getStopTime())));
                    item3.setText(TimeUtil.getTime(TimeUtil.parseTimeC(viewBean.getRealStartDate())));
                    item4.setText(DateTimeUtil.minConvertDayHourMin(Double.parseDouble(viewBean.getProduceTime())));
                    item5.setText(viewBean.getPlanQty());
                    item6.setText(viewBean.getQty());
                    item7.setText(viewBean.getDiffQty());
                    item8.setText(viewBean.getRatioQty());
                    item9.setText(viewBean.getPlanRatio());

                    item10.setText(viewBean.getPlanCT());
                    item11.setText(viewBean.getCT());
                    item12.setText(viewBean.getUPH());
                    item13.setText(viewBean.getCurUPH());
//                    item14.setText(viewBean.getDiffQty());
                    break;
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(requestCode==100&&resultCode==1000){
            String no = data.getStringExtra("no");
            String uph = data.getStringExtra("uph");
            String num = data.getStringExtra("num");
            sharedUtils.setStringValue("sc_nos",no);
            sharedUtils.setStringValue("sc_uph",uph);
            sharedUtils.setStringValue("sc_num",num);
            item00.setText(sharedUtils.getStringValue("sc_nos"));
            load(sharedUtils.getStringValue("sc_nos"),sharedUtils.getStringValue("sc_uph"),sharedUtils.getStringValue("sc_num"));
        }*/
    }

    @BindView(R2.id.item1)
    TextView item1;
    @BindView(R2.id.item2)
    TextView item2;
    @BindView(R2.id.item3)
    TextView item3;
    @BindView(R2.id.item4)
    TextView item4;
    @BindView(R2.id.item5)
    TextView item5;
    @BindView(R2.id.item6)
    TextView item6;
    @BindView(R2.id.item7)
    TextView item7;
    @BindView(R2.id.item8)
    TextView item8;
    @BindView(R2.id.item9)
    TextView item9;
    @BindView(R2.id.item10)
    TextView item10;
    @BindView(R2.id.item11)
    TextView item11;
    @BindView(R2.id.item12)
    TextView item12;
    @BindView(R2.id.item13)
    TextView item13;
    @BindView(R2.id.item14)
    TextView item14;
    private ScViewBean viewBean;
    private void load(String line,String linName,String is){
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetRPCProductBoard");
//        params.put("OrderNo",no);
//        params.put("UPH",uph);
//        params.put("Rato",num);
        params.put("UserId", SharedPreferencesTool.getMStool(ScViewActivity.this).getUserId());
        params.put("TenantId", SharedPreferencesTool.getMStool(ScViewActivity.this).getTenantId());
        params.put("LineId", line);
        httpPostVolley(SharedPreferencesTool.getMStool(ScViewActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                dismissDialog();
                try {
                if(jsonObject.getString("status").equals("success")){
                    Gson gson = new Gson();
                    viewBean =   gson.fromJson( jsonObject.getJSONArray("rows").getJSONObject(0).toString(), ScViewBean.class);

                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = linName;
                    getHandler().sendMessage(msg);
                }else{
                    Toasty.INSTANCE.showToast(ScViewActivity.this,jsonObject.getString("message"));
                }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        }, is);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(heartDisposable!=null)heartDisposable.dispose();
    }
}
