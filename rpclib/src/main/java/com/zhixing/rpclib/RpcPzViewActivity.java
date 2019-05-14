package com.zhixing.rpclib;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.SyLinearLayoutManager;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.RecycleViewDivider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhixing.beans.HourBean;
import com.zhixing.beans.PzBean;
import com.zhixing.beans.TxtInfo;
import com.zhixing.common.Common;
import com.zhixing.view.ChangeNg;
import com.zhixing.view.PzViewItemAdapter;
import com.zhixing.view.WorkItemAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class RpcPzViewActivity extends BaseRpcActivity {
    private PzViewItemAdapter pzViewItemAdapter;
    private ArrayList<PzBean> pzBeans = new ArrayList<>();
    private SharedUtils sharedUtils;
    private      String[] city;
    @Override
    public void newIniLayout() {
        sharedUtils =new SharedUtils(Common.SHARED_);
        Resources res =getResources();
        city=res.getStringArray(R.array.ng_lis);
        setStatus(-1);
        tetle_text.setText("品质监控");
       // select_layout.addItemDecoration(new RecycleViewDivider(RpcPzViewActivity.this, GridLayout.HORIZONTAL, 1, getResources().getColor(R.color.content_line)));
//        manager.setOrientation(RecyclerView.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        select_layout.setLayoutManager(layoutManager);
        String temp  = sharedUtils.getStringValue("ng_lis1");
        pzViewItemAdapter = new PzViewItemAdapter(RpcPzViewActivity.this,pzBeans,getHandler(),temp.length()!=0? Integer.parseInt(temp):0);
        select_layout.setAdapter(pzViewItemAdapter);
        ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ref.isRefreshing();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ref.setRefreshing(false);
                    }
                },2000);

            }
        });
        sets.setOnClickListener((v)->{
            ChangeNg changeNg = new ChangeNg(RpcPzViewActivity.this,sharedUtils);
            changeNg.setSelect(new SelectTime() {
                @Override
                public void select(String time, long timestp) {
                    int lev = ((int)(Double.parseDouble(time)*2));
                    P.c(time+"保存"+lev);
                    sharedUtils.setStringValue("ng_lis1",String.valueOf(lev-1));

                        tetle_tv_ok.setVisibility(View.VISIBLE);
                        tetle_tv_ok.setText("不良率报警:"+city[lev-1]+"%");
                       // pzViewItemAdapter.setNg_leav(Integer.parseInt(city[lev]));

                }
            });
            changeNg.showSheet();
        });
        if(temp.length()!=0){
            tetle_tv_ok.setVisibility(View.VISIBLE);
            tetle_tv_ok.setText("不良率报警:"+city[Integer.parseInt(temp)]+"%");
        }
        getInfos();
    }
    @BindView(R2.id.tetle_text)
    TextView tetle_text;
    @BindView(R2.id.select_layout)
    RecyclerView select_layout;
    @BindView(R2.id.ref)
    SwipeRefreshLayout ref;
    @BindView(R2.id.sets)
    TextView sets;
    @BindView(R2.id.tetle_tv_ok)
    TextView tetle_tv_ok;
    @Override
    public int getLayoutId() {
        return R.layout.rpc_pz_view_activity;
    }

    @Override
    public void process(Message msg) {
            switch (msg.what){
                case 1:
                    pzViewItemAdapter.updata(pzBeans);

                    break;
            }
    }

    private void loadData(){

    }
    private void getInfos(){
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetRPCNGHoursData");
        params.put("TenantId", SharedPreferencesTool.getMStool(RpcPzViewActivity.this).getTenantId());
        params.put("WorkId", sharedUtils.getStringValue("workshop_id"));
        params.put("LineId","");

        httpPostVolley(SharedPreferencesTool.getMStool(RpcPzViewActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {

                try {
                    if(jsonObject.getString("status").equals("success")){
                        pzBeans.clear();
                        Gson gson = new Gson();
                        pzBeans =  gson.fromJson(jsonObject.getJSONArray("rows").toString(),new TypeToken<List<PzBean>>(){}.getType());
                        getHandler().sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        },"提交数据");
    }

}
