package com.shuben.zhixing.www.patrol.twoclass;

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
import com.shuben.zhixing.www.patrol.adapter.TreeAdapter;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignUserActivity extends BaseActvity implements View.OnClickListener{
    private List<Node> parent_list = new ArrayList<Node>();
    private SignUserActivity oThis = this;
    private ListView code_list;
    private TreeAdapter ta02;
    private OrganizeAdapter ta01;
    private TextView tx_tittle,tx_commit;
    private LinearLayout lay_back;
    private LoadingDailog dialog;//加载动画


    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_user;
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

    private void initData() {

        {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            params.put("ApiCode", "GetUserCallIn");
            params.put("TenantId",  SharedPreferencesTool.getMStool(SignUserActivity.this).getTenantId());
            params.put("RecordId", getIntent().getStringExtra("RecordId"));
            JSONObject myData = new JSONObject();

            try {
                myData.put("AppCode", "LinePatrol");
                myData.put("ApiCode", "GetUserCallIn");
                myData.put("TenantId", SharedPreferencesTool.getMStool(SignUserActivity.this).getTenantId());
                myData.put("RecordId", getIntent().getStringExtra("RecordId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("**********", myData.toString());

            JsonObjectRequest newMissRequest = new JsonObjectRequest(
                    Request.Method.POST, SharedPreferencesTool.getMStool(SignUserActivity.this).getIp()+UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonobj) {

                    try {
                        JSONArray jArray = jsonobj.getJSONArray("rows");
                        Log.e("KKKKKKKK", " " + jArray.toString());
                        dialog.dismiss();
                        JSONObject jData;
                        parent_list.clear();
                        for (int i = 0; i < jArray.length(); i++) {
                            jData = jArray.getJSONObject(i);
                            String PartInUserId =jData.getString("PartInUserId");
                            String PartInUserName =jData.getString("PartInUserName");
                            String SignStatus =jData.getString("SignStatus");
                            String leaderId=getIntent().getStringExtra("leaderId");
                            if(leaderId!=null){
                                if(!leaderId.equals(PartInUserId)){
                                    if(SignStatus.equals("1")){
                                        Node parent = new Node(PartInUserName, PartInUserId);
                                        parent_list.add(parent);
                                    }
                                }

                            }else{
                                if(SignStatus.equals("1")){
                                    Node parent = new Node(PartInUserName, PartInUserId);
                                    parent_list.add(parent);
                                }
                            }


                        }

                        Node root = new Node(getIntent().getStringExtra("Title"), "000000");
                        root.setIcon(R.drawable.icon_department);//设置图标
                        root.setCheckBox(false);//设置节点前有无复选框
                        //getList(list,root);//用于自定义

                        for (int k = 0; k < parent_list.size(); k++) {
                            parent_list.get(k).setParent(root);
                            root.add(parent_list.get(k));
                        }
                        if(getIntent().getStringExtra("Title").equals("候选组长")){
                            ta01 = new OrganizeAdapter(oThis, root);
                            // 设置整个树是否显示复选框
                            ta01.setCheckBox(true);
                            // 设置展开和折叠时图标
                            ta01.setExpandedCollapsedIcon(R.drawable.tree_ex, R.drawable.tree_ec);
                            // 设置默认展开级别
                            ta01.setExpandLevel(2);
                            code_list.setAdapter(ta01);
                        }else{
                            ta02 = new TreeAdapter(oThis, root);
                            ta02.setCheckBox(true);
                            // 设置展开和折叠时图标
                            ta02.setExpandedCollapsedIcon(R.drawable.tree_ex, R.drawable.tree_ec);
                            // 设置默认展开级别
                            ta02.setExpandLevel(2);
                            code_list.setAdapter(ta02);
                        }




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

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.tx_commit:
                if(getIntent().getStringExtra("Title").equals("候选组长")){
                    commit01();
                }else{
                    commit02();
                }
                break;
            default:
                break;
        }
    }

    private void commit01() {
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
        setResult(UrlUtil.SignUserActivity_ResultCode,intent);
        finish();

    }
    private void commit02() {
        List<Node> nodes = ((TreeAdapter) code_list.getAdapter()).getSeletedNodes();
        String userName = "";
        String userId = "";
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            if (n.isLeaf()) {
                if (!userName.equals("")){
                    userName += ",";
                    userId += ",";
                }
                userName += n.getText() ;
                userId   += n.getValue();
            }
        }
        Intent intent = new Intent();
        intent.putExtra("userName",userName);
        intent.putExtra("userId",userId);
        setResult(UrlUtil.SignUserActivity_ResultCode,intent);
        finish();
    }


}

