package com.zhixing.rpclib;

import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.SyLinearLayoutManager;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.ChangeTime;
import com.base.zhixing.www.widget.RecycleViewDivider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.beans.HourBean;
import com.zhixing.beans.ScViewBean;
import com.zhixing.common.Common;
import com.zhixing.view.HourItemAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HourViewActivity extends BaseRpcActivity {
    @BindView(R2.id.select_layout)
    RecyclerView select_layout;
    private HourItemAdapter itemAdapter;
    private ArrayList<HourBean> hourBeans = new ArrayList<>();
    @BindView(R2.id.tetle_text)
    TextView tetle_text;
    @BindView(R2.id.time_select)
    TextView time_select;
    private SharedUtils sharedUtils;
    private Disposable heartDisposable;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(heartDisposable!=null)heartDisposable.dispose();
    }
    @Override
    public void newIniLayout() {
        sharedUtils = new SharedUtils(Common.SHARED_);
        select_layout.addItemDecoration(new RecycleViewDivider(HourViewActivity.this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.content_line)));
//        manager.setOrientation(RecyclerView.VERTICAL);
        SyLinearLayoutManager manager = new SyLinearLayoutManager(HourViewActivity.this,LinearLayoutManager.VERTICAL,false);
        select_layout.setLayoutManager(manager);
        itemAdapter = new HourItemAdapter(HourViewActivity.this,hourBeans,getHandler());
        select_layout.setAdapter(itemAdapter);
        tetle_text.setText("小时看板");
        time_select.setOnClickListener((v)->{
            ChangeTime changeTime = new ChangeTime(HourViewActivity.this,"",2);
            changeTime.setSelect((time, timestp) -> {
                load(TimeUtil.getTimeCh(timestp),"数据获取中");
                time_select.setText(TimeUtil.getTimeCh(timestp));
                sharedUtils.setStringValue("hour_timestp",TimeUtil.getTimeCh(timestp));
            });
            changeTime.showSheet();
        });
        if(sharedUtils.getStringValue("hour_timestp").length()!=0){
            time_select.setText(sharedUtils.getStringValue("hour_timestp"));
            load(sharedUtils.getStringValue("hour_timestp"),"数据获取中");
        }
        heartDisposable = Observable.interval(8, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()). observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                    if(sharedUtils.getStringValue("hour_timestp").length()!=0){
                        load(sharedUtils.getStringValue("hour_timestp"),null);
                     }
                    }
                });

    }

    @Override
    public int getLayoutId() {
        return R.layout.rpc_hour_view_activity;
    }

    @Override
    public void process(Message msg) {
            switch (msg.what){
                case 2:
                    String id = (String) msg.obj;
                    int index = msg.arg1;
                    Intent intent = new Intent(HourViewActivity.this,RpcHourResActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("index",index);
                    intent.putExtra("v1",hourBeans.get(index).getDetailedNG());
                    intent.putExtra("v2",hourBeans.get(index).getRemark());
                    startActivityForResult(intent,100);
                    break;
                case 1:
                    itemAdapter.updata(hourBeans);
                    break;
            }

    }
    /*
    {"AppCode":"SMES-S","TenantId":"00000000-0000-0000-0000-000000000001","LineId":"731feceb-a52d-41b6-a776-76a0498ebd86","Date":"2019-03-21",
"ApiCode":"GetRPCPHoursQty"}
     */
    private void load(String time,String txt){
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetRPCPHoursQty");
        //params.put("UserId", SharedPreferencesTool.getMStool(HourViewActivity.this).getUserId());
        params.put("TenantId", SharedPreferencesTool.getMStool(HourViewActivity.this).getTenantId());
        params.put("LineId", sharedUtils.getStringValue("line_id"));
        params.put("Date",time);
        httpPostVolley(SharedPreferencesTool.getMStool(HourViewActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                dismissDialog();
                hourBeans.clear();
                try {
                    Gson gson = new Gson();
                   hourBeans =  gson.fromJson(jsonObject.getJSONArray("rows").toString(),new TypeToken<List<HourBean>>(){}.getType());

                    getHandler().sendEmptyMessage(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        },txt);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==1000){
            String i0 = data.getStringExtra("i0");
            String i1 = data.getStringExtra("i1");
            loadI(data.getStringExtra("id"),data.getIntExtra("index",-1),i0,i1);

        }
    }
    private void loadI(String id,int index,String i0,String i1){
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "EditRPCPHoursRemark");
        //params.put("UserId", SharedPreferencesTool.getMStool(HourViewActivity.this).getUserId());
        params.put("TenantId", SharedPreferencesTool.getMStool(HourViewActivity.this).getTenantId());
        params.put("LineId", sharedUtils.getStringValue("line_id"));
        params.put("Date",sharedUtils.getStringValue("hour_timestp"));
        params.put("ID",id);
        params.put("DetailedNG",i0);
        params.put("Remark",i1);
        httpPostVolley(SharedPreferencesTool.getMStool(HourViewActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                dismissDialog();
                hourBeans.get(index).setRemark(i1);
                getHandler().sendEmptyMessage(1);
            }

            @Override
            public void error(VolleyError error) {

            }
        },"刷新数据中");
    }

}
