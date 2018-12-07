package com.shuben.zhixing.www.patrol.twoclass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class AddCallActivity extends BaseActvity implements OnClickListener{
    private Intent intent;
    private LinearLayout lay_back;
    private RelativeLayout lay_add;
    private TextView tx_del;
    private List<Node> parent_list = new ArrayList<Node>();
    private AddCallActivity oThis = this;
    private ListView code_list;
    private TreeAdapter ta;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_call;
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
        code_list=(ListView) findViewById(R.id.code_list);
        lay_back=(LinearLayout) findViewById(R.id.lay_back);
        lay_add=(RelativeLayout) findViewById(R.id.lay_add);
        tx_del=(TextView) findViewById(R.id.tx_del);
        tx_del.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        lay_add.setOnClickListener(this);

    }

    private void initData() {

        {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            params.put("ApiCode", "GetUserCallIn");
            params.put("TenantId", SharedPreferencesTool.getMStool(AddCallActivity.this).getTenantId());
            params.put("RecordId",getIntent().getStringExtra("RecordId"));


            JSONObject myData = new JSONObject();

            try {
                myData.put("AppCode", "LinePatrol");
                myData.put("ApiCode", "GetUserCallIn");
                myData.put("TenantId", SharedPreferencesTool.getMStool(AddCallActivity.this).getTenantId());
                myData.put("RecordId",getIntent().getStringExtra("RecordId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("**********", myData.toString());

            JsonObjectRequest newMissRequest = new JsonObjectRequest(
                    Request.Method.POST, SharedPreferencesTool.getMStool(AddCallActivity.this).getIp()+UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonobj) {

                    try {
                        JSONArray jArray = jsonobj.getJSONArray("rows");
                        Log.e("KKKKKKKK", " " + jArray.toString());
                        JSONObject jData;
                        JSONObject juser;
                        parent_list.clear();
                        for (int i = 0; i < jArray.length(); i++) {
                            jData = jArray.getJSONObject(i);
                            String RecordId = jData.getString("RecordId");//
                            String PartInUserId = jData.getString("PartInUserId");//
                            String PartInUserName = jData.getString("PartInUserName");//
                            Node parent = new Node(PartInUserName, PartInUserId);

                            parent_list.add(parent);
                        }

                        Node root = new Node("已召集人员", "000000");
                        root.setIcon(R.drawable.icon_department);//设置图标
                        root.setCheckBox(false);//设置节点前有无复选框
                        //getList(list,root);//用于自定义

                        for (int k = 0; k < parent_list.size(); k++) {
                            parent_list.get(k).setParent(root);
                            root.add(parent_list.get(k));
                        }
                         ta = new TreeAdapter(oThis, root);
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

                }
            });
            newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
            requestQueue.add(newMissRequest);
            requestQueue.add(newMissRequest);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != data) {
            if (requestCode == UrlUtil.AddCallActivity_RequstCode) {
                if (resultCode == UrlUtil.CallUserActivity_ResultCode) {
                    initData();
                }
            }


        }

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.lay_add:
                intent=new Intent();
                intent.setClass(AddCallActivity.this,CallUserActivity.class);
                intent.putExtra("RecordId",getIntent().getStringExtra("RecordId"));
                startActivityForResult(intent,UrlUtil.AddCallActivity_RequstCode);
                break;
            case R.id.tx_del:
                del();
                break;
            default:

            break;



        }
    }

    private void del() {
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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "DeleteUserCallIn");
        params.put("TenantId", SharedPreferencesTool.getMStool(AddCallActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        params.put("PartInUserId", userId);

        JSONObject data =new JSONObject();

        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "DeleteUserCallIn");
            data.put("TenantId", SharedPreferencesTool.getMStool(AddCallActivity.this).getTenantId());
            data.put("RecordId", getIntent().getStringExtra("RecordId"));
            data.put("PartInUserId", userId);

            Log.e("***************",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalUserId = userId;
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(AddCallActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        Toast.makeText(AddCallActivity.this,"删除人员成功",Toast.LENGTH_LONG).show();
                        List<String> result =new ArrayList<String>();
                        initData();



                    }else{
                        Toast.makeText(AddCallActivity.this,message,Toast.LENGTH_SHORT).show();
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
