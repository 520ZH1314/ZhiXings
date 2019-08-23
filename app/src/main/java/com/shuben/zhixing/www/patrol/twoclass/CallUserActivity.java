package com.shuben.zhixing.www.patrol.twoclass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.www.patrol.adapter.Node;
import com.shuben.zhixing.www.patrol.adapter.TreeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class CallUserActivity extends BaseActvity implements AdapterView.OnItemClickListener,View.OnClickListener{
    private LinearLayout lay_back;
    private TextView tx_send;
    private  ListView code_list;
    private LinearLayout toolBar;
    CallUserActivity oThis = this;
    private List<Node> parent_list = new ArrayList<Node>();
    private LoadingDailog dialog;//加载动画
    private String User="";

    @Override
    public int getLayoutId() {
        return R.layout.activity_call_user;
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
        lay_back=(LinearLayout) findViewById(R.id.lay_back);
        tx_send=(TextView) findViewById(R.id.tx_send);
        lay_back.setOnClickListener(this);
        tx_send.setOnClickListener(this);
        code_list = (ListView) findViewById(R.id.call_user_listview);
        code_list.setOnItemClickListener(this);
    }

    private void initData() {

        {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "EPS");
            params.put("ApiCode", "GetOrganizeAndUserList");
            params.put("TenantId",  SharedPreferencesTool.getMStool(CallUserActivity.this).getTenantId());
            JSONObject myData = new JSONObject();
            try {
                myData.put("AppCode", "EPS");
                myData.put("ApiCode", "GetOrganizeAndUserList");
                myData.put("TenantId", SharedPreferencesTool.getMStool(CallUserActivity.this).getTenantId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("**********", myData.toString());

            JsonObjectRequest newMissRequest = new JsonObjectRequest(
                    Request.Method.POST, SharedPreferencesTool.getMStool(CallUserActivity.this).getIp()+UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonobj) {
                    P.c("召集人员:"+jsonobj.toString());
                    dialog.dismiss();
                    try {
                        JSONArray jArray = jsonobj.getJSONArray("rows");
                        Log.e("KKKKKKKK", " " + jArray.toString());

                        JSONObject jData;
                        JSONObject juser;
                        parent_list.clear();
                        for (int i = 0; i < jArray.length(); i++) {
                            jData = jArray.getJSONObject(i);
                            Log.e("BBBBBBBBBB", " " + jData.toString());
                            String OrganizeId = jData.getString("OrganizeId");//
                            String OrganizeName = jData.getString("OrganizeName");//
                            Node parent = new Node(OrganizeName, OrganizeId);

                            JSONArray UserList = jData.getJSONArray("UserList");
                            for (int j = 0; j < UserList.length(); j++) {
                                juser = UserList.getJSONObject(j);
                                String UserId = juser.getString("UserId");
                                String UserCode = juser.getString("UserCode");
                                String UserName = juser.getString("UserName");
                                Node child = new Node(UserName, UserId);
                                child.setParent(parent);
                                parent.add(child);

                            }
                            parent_list.add(parent);
                        }

                        Node root = new Node("组织架构", "000000");
                        root.setIcon(R.drawable.icon_department);//设置图标
                        root.setCheckBox(false);//设置节点前有无复选框
                        //getList(list,root);//用于自定义

                        for (int k = 0; k < parent_list.size(); k++) {
                            parent_list.get(k).setParent(root);
                            root.add(parent_list.get(k));
                        }
                        TreeAdapter ta = new TreeAdapter(oThis, root);
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

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        //Toast.makeText(this, "您选择的是:" + position, Toast.LENGTH_SHORT).show();

        // 这句话写在最后面
        ((TreeAdapter) parent.getAdapter()).ExpandOrCollapse(position);
    }



    public void getList(List<Node> listName, Node root) {
        for(Node node:listName){
            Node n11 = new Node(node.getText(),node.getValue());
            n11.setParent(root);
            root.add(n11);
            if(node.getChildren().size()>0){
                getList(node.getChildren(),n11);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.tx_send:
                send();
                break;
            default:

            break;



        }

    }

    private void send() {

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
        User=userId;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditUserCallIn");
        params.put("TenantId", SharedPreferencesTool.getMStool(CallUserActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        params.put("PartInUserId", userId);
        params.put("CreateUserCode", SharedPreferencesTool.getMStool(CallUserActivity.this).getUserCode());

        JSONObject data =new JSONObject();
        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditUserCallIn");
            data.put("TenantId", SharedPreferencesTool.getMStool(CallUserActivity.this).getTenantId());
            data.put("RecordId", getIntent().getStringExtra("RecordId"));
            data.put("PartInUserId", userId);
            data.put("CreateUserCode", SharedPreferencesTool.getMStool(CallUserActivity.this).getUserCode());

            Log.e("***************",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(CallUserActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        Toast.makeText(CallUserActivity.this,"召集人员成功",Toast.LENGTH_LONG).show();
                        String  msg="人员召集";
                        String from=SharedPreferencesTool.getMStool(CallUserActivity.this).getUserId();
                        String receivers=   User;
                        P.c("召集"+from+"=="+receivers);



                        Intent intent=new Intent();
                        setResult(UrlUtil.CallUserActivity_ResultCode,intent);
                        finish();
                    }else{
                        Toast.makeText(CallUserActivity.this,message,Toast.LENGTH_SHORT).show();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
        requestQueue.add(newMissRequest);
    }
}

