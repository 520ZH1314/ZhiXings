package com.zhixing.rpclib;

import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.SyLinearLayoutManager;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.RecycleViewDivider;
import com.google.gson.Gson;
import com.zhixing.beans.WorkBean;
import com.zhixing.common.Common;
import com.zhixing.view.WorkItemAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class WorkViewActivity extends BaseRpcActivity {
    private WorkItemAdapter workItemAdapter;
    @BindView(R2.id.tetle_text)
    TextView tetle_text;
    private ArrayList<WorkBean> workBeans = new ArrayList<>();
    private SharedUtils sharedUtils;
    private Disposable heartDisposable;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(heartDisposable!=null)heartDisposable.dispose();
    }
    @Override
    public void newIniLayout() {
        setStatus(-1);
        sharedUtils = new SharedUtils(Common.SHARED_);
        select_layout.addItemDecoration(new RecycleViewDivider(WorkViewActivity.this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.content_line)));
//        manager.setOrientation(RecyclerView.VERTICAL);
        SyLinearLayoutManager manager = new SyLinearLayoutManager(WorkViewActivity.this,LinearLayoutManager.VERTICAL,false);
        select_layout.setLayoutManager(manager);
        workItemAdapter = new WorkItemAdapter(WorkViewActivity.this,workBeans,getHandler());
        select_layout.setAdapter(workItemAdapter);
        tetle_text.setText("车间看板");

        rbs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb0) {
                    workBeans = workBeans0;

                }else if(i==R.id.rb1){
                    workBeans = workBeans1;
                }else if(i==R.id.rb2){
                    workBeans = workBeans2;
                }else if(i==R.id.rb3){
                    workBeans = workBeans3;
                }
                workItemAdapter.updata(workBeans);
            }
        });
        rb0.setChecked(true);
        load("数据获取中");
        heartDisposable = Observable.interval(8, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()). observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        load(null);
                    }
                });

    }
    @BindView(R2.id.select_layout)
    RecyclerView select_layout;
    @BindView(R2.id.rbs)
    RadioGroup rbs;
    @BindView(R2.id.rb0)
    RadioButton rb0;
    @Override
    public int getLayoutId() {
        return R.layout.rpc_work_layout;
    }

    @Override
    public void process(Message msg) {
        switch (msg.what){
            case 1:
                    int i = rbs.getCheckedRadioButtonId();
                if (i == R.id.rb0) {
                    workBeans = workBeans0;

                }else if(i==R.id.rb1){
                    workBeans = workBeans1;
                }else if(i==R.id.rb2){
                    workBeans = workBeans2;
                }else if(i==R.id.rb3){
                    workBeans = workBeans3;
                }
                workItemAdapter.updata(workBeans);

                break;
        }
    }
    private ArrayList<WorkBean> workBeans0 = new ArrayList<>();
    private ArrayList<WorkBean> workBeans1 = new ArrayList<>();
    private ArrayList<WorkBean> workBeans2 = new ArrayList<>();
    private ArrayList<WorkBean> workBeans3 = new ArrayList<>();
    private void load(String load){
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetRPCShopBoard");
        //params.put("UserId", SharedPreferencesTool.getMStool(HourViewActivity.this).getUserId());
        params.put("TenantId", SharedPreferencesTool.getMStool(WorkViewActivity.this).getTenantId());
        params.put("WorkShopId", sharedUtils.getStringValue("workshop_id"));
        //params.put("State")
        httpPostVolley(SharedPreferencesTool.getMStool(WorkViewActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                workBeans0.clear();
                workBeans1.clear();
                workBeans2.clear();
                workBeans3.clear();

                try {
                    Gson gson = new Gson();
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for(int i=0;i<jsonArray.length();i++){

                        WorkBean workBean  =  gson.fromJson(jsonArray.getJSONObject(i).toString(),WorkBean.class);
                        if(workBean.getStateName().equals("未切单")){
                            workBeans0.add(workBean);
                        }else if(workBean.getStateName().equals("生产中")){
                            workBeans1.add(workBean);
                        }else if(workBean.getStateName().equals("停机中")){
                            workBeans2.add(workBean);
                        }else if(workBean.getStateName().equals("已完成")){
                            workBeans3.add(workBean);
                        }
                    }
                    getHandler().sendEmptyMessage(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(VolleyError error) {

            }
        },load);
    }

}
