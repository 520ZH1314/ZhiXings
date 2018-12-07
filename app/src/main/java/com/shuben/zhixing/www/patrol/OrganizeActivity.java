package com.shuben.zhixing.www.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.Node;
import com.shuben.zhixing.www.patrol.adapter.OrganizeAdapter;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrganizeActivity extends BaseActvity implements View.OnClickListener{
    private List<Node> parent_list = new ArrayList<Node>();
    private OrganizeActivity oThis = this;
    private ListView code_list;
    private OrganizeAdapter ta;
    private TextView tx_tittle,tx_commit;

    private LinearLayout lay_back;
    private LoadingDailog dialog;//加载动画

    @Override
    public int getLayoutId() {
        return R.layout.activity_organize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        initView();
        initData();
    }

    private void initData() {

        {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "EPS");
            params.put("ApiCode", "GetOrganizeList");
            params.put("TenantId", SharedPreferencesTool.getMStool(OrganizeActivity.this).getTenantId());


            JSONObject myData = new JSONObject();

            try {
                myData.put("AppCode", "LinePatrol");
                myData.put("ApiCode", "GetOrganizeList");
                myData.put("TenantId", SharedPreferencesTool.getMStool(OrganizeActivity.this).getTenantId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("**********", myData.toString());

            JsonObjectRequest newMissRequest = new JsonObjectRequest(
                    Request.Method.POST, SharedPreferencesTool.getMStool(OrganizeActivity.this).getIp()+UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonobj) {

                    try {
                        JSONArray jArray = jsonobj.getJSONArray("rows");
                        //Log.e("KKKKKKKK", " " + jArray.toString());
                        dialog.dismiss();
                        JSONObject jData;
                        JSONObject juser;
                        parent_list.clear();
                        for (int i = 0; i < jArray.length(); i++) {
                            jData = jArray.getJSONObject(i);
                            //String RecordId = jData.getString("RecordId");//
                            String OrganizeId = jData.getString("OrganizeId");//
                            String OrganizeName = jData.getString("OrganizeName");//
                            Node parent = new Node(OrganizeName, OrganizeId);

                            parent_list.add(parent);
                        }

                        Node root = new Node(getIntent().getStringExtra("Title").substring(0,2), "000000");
                        root.setIcon(R.drawable.icon_department);//设置图标
                        root.setCheckBox(false);//设置节点前有无复选框
                        //getList(list,root);//用于自定义

                        for (int k = 0; k < parent_list.size(); k++) {
                            parent_list.get(k).setParent(root);
                            root.add(parent_list.get(k));
                        }
                        ta = new OrganizeAdapter(oThis, root);
                        // 设置整个树是否显示复选框
                        ta.setCheckBox(true);
                        // 设置展开和折叠时图标
                        ta.setExpandedCollapsedIcon(R.drawable.tree_ex, R.drawable.tree_ec);
                        // 设置默认展开级别
                        ta.setExpandLevel(2);
                        code_list.setAdapter(ta);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();

                }
            });
            newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
            dialog.show();
            requestQueue.add(newMissRequest);
        }
    }
    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        code_list=(ListView) findViewById(R.id.listview_user);
        lay_back=(LinearLayout)findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);
        tx_tittle=(TextView) findViewById(R.id.tx_tittle);
        tx_tittle.setText(getIntent().getStringExtra("Title"));
        tx_commit=(TextView) findViewById(R.id.tx_commit);
        tx_commit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.tx_commit:
                commit();
                break;
            default:
                break;
        }
    }

    private void commit() {
        List<Node> nodes = ((OrganizeAdapter) code_list.getAdapter()).getSeletedNodes();
        String userName = "";
        String userId = "";

        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            if (n.isLeaf()) {
                userName = n.getText() ;
                userId   = n.getValue();
            }
        }
        Intent intent = new Intent();
        intent.putExtra("userName",userName);
        intent.putExtra("userId",userId);
        setResult(UrlUtil.OrganizeActivity_ResultCode,intent);
        finish();

    }
}

