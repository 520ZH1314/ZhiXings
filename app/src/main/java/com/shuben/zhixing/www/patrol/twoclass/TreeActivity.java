package com.shuben.zhixing.www.patrol.twoclass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.shuben.zhixing.www.patrol.adapter.ToolbarAdapter;
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

public class TreeActivity extends BaseActvity implements AdapterView.OnItemClickListener {
    public static String URL = "http://10.6.24.237:8080/api/CMP/ApiRegistrator/PostApiGateWay";

    ListView code_list;
    private LinearLayout toolBar;
    TreeActivity oThis = this;

    private List<Node> parent_list = new ArrayList<Node>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_tree;
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
        toolBar = (LinearLayout) findViewById(R.id.ll_toolBar);
        code_list = (ListView) findViewById(R.id.list_tree_code_list);
        code_list.setOnItemClickListener(this);

        setToolBar(new String[]{"选中结果", "", "", "退出"}, new int[]{0, 3});

        //setPreson();
        initData();
    }

    private void initData() {

        {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "EPS");
            params.put("ApiCode", "GetOrganizeAndUserList");
            params.put("TenantId", SharedPreferencesTool.getMStool(TreeActivity.this).getTenantId());


            JSONObject myData = new JSONObject();

            try {
                myData.put("AppCode", "EPS");
                myData.put("ApiCode", "GetOrganizeAndUserList");
                myData.put("TenantId", SharedPreferencesTool.getMStool(TreeActivity.this).getTenantId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("**********", myData.toString());

            JsonObjectRequest newMissRequest = new JsonObjectRequest(
                    Request.Method.POST, SharedPreferencesTool.getMStool(TreeActivity.this).getIp()+ UrlUtil.Url,
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

                        Node root = new Node("新希望天香技术有限公司", "000000");
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

                }
            });
            newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
            requestQueue.add(newMissRequest);
            requestQueue.add(newMissRequest);
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast.makeText(this, "您选择的是:" + position, Toast.LENGTH_SHORT).show();

        // 这句话写在最后面
        ((TreeAdapter) parent.getAdapter()).ExpandOrCollapse(position);
    }


    // 设置底部工具栏
    private void setToolBar(String[] name_array, int[] pos_array) {
        toolBar.removeAllViews();

        GridView toolbarGrid = new GridView(this);
        toolbarGrid.setNumColumns(4);// 设置每行列数
        toolbarGrid.setGravity(Gravity.CENTER);// 位置居中
        ToolbarAdapter adapter = new ToolbarAdapter(this, name_array, null, pos_array);
        toolbarGrid.setAdapter(adapter);
        toolbarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                switch (position) {
                    case 0://显示选中结果
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
                        if (userName.equals("")) {
                            Toast.makeText(oThis, "没有选中任何项", Toast.LENGTH_SHORT).show();
                        } else {

                            Intent intent = new Intent();
                            intent.putExtra("userName",userName);
                            intent.putExtra("userId",userId);
                            setResult(200,intent);
                            finish();
                            //Toast.makeText(oThis, userName+"/n"+userId, Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case 3://返回
                        oThis.finish();
                        System.exit(0);
                        break;
                }
            }
        });
        toolBar.addView(toolbarGrid);
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
}
