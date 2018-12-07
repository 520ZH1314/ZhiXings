package com.shuben.zhixing.www.reminder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.adapter.RenWuAdapter;
import com.shuben.zhixing.www.data.ReplyInfo;
import com.shuben.zhixing.www.util.ScrollListview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.XmlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/22.
 * 任务子菜单
 */

public class RenWuActivity extends BaseActvity implements View.OnClickListener {
    private ImageView tetle_back;
    private TextView tetle_text,renwu_client_name,renwu_product_name,renwu_cargo_number,renwu_cargo_time,renwu_indent_id,
            renwu_dingdan_id,renwu_zeren,renwu_chaosong,renwu_item_name,renwu_item_date;
    private RelativeLayout renwu_rl1,renwu_rl2;
    private ListView renwu_item,renwu_leader;
    private RenWuAdapter adapter;
    private List<ReplyInfo> data=new ArrayList<ReplyInfo>();
    private List<String> user_list=new ArrayList<String>();
    private RequestQueue mQueue;
    private String billNo="";
    private String type="";
    private int currentStep=0;
    boolean isShow=false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_renwu;
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
        init();
    }

    @SuppressLint("")
    private void init() {
        mQueue = Volley.newRequestQueue(this);
        billNo=getIntent().getStringExtra("BillNo");
        type=getIntent().getStringExtra("Type");

        tetle_back = (ImageView)findViewById(R.id.tetle_back);//返回
        tetle_back.setOnClickListener(this);
        tetle_text = (TextView) findViewById(R.id.tetle_text);//title
        tetle_text.setText("任务详情");

        renwu_item_name = (TextView) findViewById(R.id.renwu_item_createuser);//创建人
        renwu_item_date = (TextView) findViewById(R.id.renwu_item_date);//创建日期
        renwu_client_name = (TextView) findViewById(R.id.renwu_client_name);//客户名称
        renwu_product_name = (TextView) findViewById(R.id.renwu_product_name);//产品名称
        renwu_cargo_number = (TextView) findViewById(R.id.renwu_cargo_number);//交货数量
        renwu_cargo_time = (TextView) findViewById(R.id.renwu_cargo_time);//交货时间
        renwu_indent_id = (TextView) findViewById(R.id.renwu_indent_id);//催单编号
        renwu_dingdan_id = (TextView) findViewById(R.id.renwu_dingdan_id);//订单号
        renwu_zeren = (TextView) findViewById(R.id.renwu_zeren);//责任人
        renwu_chaosong = (TextView) findViewById(R.id.renwu_chaosong);//抄送人
        renwu_rl1 = (RelativeLayout) findViewById(R.id.renwu_rl1);//回复
        renwu_rl2 = (RelativeLayout) findViewById(R.id.renwu_rl2);//更多
        renwu_leader = (ListView) findViewById(R.id.renwu_leader);//领导意见评论--有数据才显示，无数据不显示，慎重
        renwu_item = (ListView) findViewById(R.id.renwu_item);//列表数据--回复列表
        if(type.equals("采购催单1")){
            TextView renwu_name=(TextView)findViewById(R.id.renwu_name);
            renwu_name.setText("供应商名称");
        }

        setOnclick();
        getCCUser(type);


    }



    private void setOnclick() {
        renwu_rl1.setOnClickListener(this);
        renwu_rl2.setOnClickListener(this);
    }
    private void getCCUser(final String type) {
        {
            String Url="";
            if(type.equals("内部催单")){
                 Url= UrlUtil.GetCCUserUrl01(UrlUtil.IP, UrlUtil.InnerCCUser,billNo);
            }else if(type.equals("采购催单1")||type.equals("采购催单2")||type.equals("采购催单3")||type.equals("采购催单")){
                Url= UrlUtil.GetCCUserUrl02(UrlUtil.IP, UrlUtil.OuterCCUser,billNo);
            }else {
                Log.i("获取抄送人信息Url","8888888888888888888"+type);
            }
            Log.e("获取抄送人信息Url",Url);
           StringRequest stringRequest = new StringRequest(Url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.i("获取抄送人信息Url","77777777777777777777777777");
                            response = XmlUtil.getDataByXml(response, "string", TAG);
                            Log.e("获取抄送人信息", response);
                            data.clear();
                            try {
                                JSONObject jsonData = new JSONObject(response);
                                JSONObject jsonObject;
                                JSONArray array = jsonData.getJSONArray("rows");
                                for (int i = 0; i < array.length(); i++) {

                                    jsonObject = array.getJSONObject(i);
                                    String CCUserName = jsonObject.getString("CCUserName");//任务ID
                                    user_list.add(CCUserName);
                                }
                                String user="";
                               for(int i=0;i<user_list.size();i++){
                                   if(i>0){
                                        user=","+user_list.get(i);
                                   }else{
                                       user=user_list.get(i);
                                   }

                               }
                                if(type.equals("内部催单")){
                                    getInnerData(user,type);
                                }else if(type.equals("采购催单1")||type.equals("采购催单2")||type.equals("采购催单3")||type.equals("采购催单")){
                                    getOuterData(user,type);
                                }



                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(stringRequest);


        }
    }

    private void getInnerData(final String user,final String type) {
        {
            String Url="";
            if(type.equals("内部催单")){
                 Url= UrlUtil.GetInnerUrgeOrderUrl(UrlUtil.IP, UrlUtil.InnerUrgeOrder04,billNo);
                Log.e("获取内部催单详细信息Url",Url);
            }else if(type.equals("采购催单1")||type.equals("采购催单2")||type.equals("采购催单3")||type.equals("采购催单")){
                Log.e("获取购催单详细信息Url",Url);
                 Url= UrlUtil.GetInnerUrgeOrderUrl(UrlUtil.IP, UrlUtil.OuterUrgeOrder04,billNo);
            }


            StringRequest stringRequest = new StringRequest(Url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            response = XmlUtil.getDataByXml(response, "string", TAG);
                            Log.e("获取内部催单详情信息", response);
                            data.clear();
                            try {
                                JSONObject jsonData = new JSONObject(response);
                                JSONObject jsonObject;
                                JSONArray array = jsonData.getJSONArray("rows");
                                for (int i = 0; i < array.length(); i++) {

                                    jsonObject = array.getJSONObject(i);
                                    String innerUrgeOrderId = jsonObject.getString("InnerUrgeOrderId");//任务ID
                                    String customerName = jsonObject.getString("CustomerName");//客户名称
                                    String productName = jsonObject.getString("ProductName");//产品名称
                                    String deliverDate = jsonObject.getString("DeliverDate");//交货时间
                                    String CreateDate = jsonObject.getString("CreateDate");//创建时间
                                    if(!deliverDate.equals("")||!deliverDate.equals("null")||deliverDate!=null){
                                        deliverDate= deliverDate.replaceAll("T"," ");
                                    } if(!CreateDate.equals("")||!CreateDate.equals("null")||CreateDate!=null){
                                        CreateDate= deliverDate.replaceAll("T"," ");
                                    }
                                    String deliverAddress = jsonObject.getString("DeliverAddress");//地址
                                    String createUserName = jsonObject.getString("CreateUserName");//创建人
                                    String toDoUserName = jsonObject.getString("ToDoUserName");//责任人
                                    String deliverQty = jsonObject.getString("DeliverQty");//数量
                                    String billNo = jsonObject.getString("BillNo");//任务编号
                                    String OrderNo = jsonObject.getString("OrderNo");//任务编号
                                    int CurrentStep = jsonObject.getInt("CurrentStep");//任务编号
                                    String CreateUserId= jsonObject.getString("CreateUserId");//创建人ID
                                    String ToDoUserId= jsonObject.getString("ToDoUserId");//责任人ID

                                    currentStep=CurrentStep;
                                    if(ToDoUserId.equals(SharedPreferencesTool.getMStool(RenWuActivity.this).getUserId())){
                                       isShow=true;
                                       // Toast.makeText(RenWuActivity.this,""+isShow,Toast.LENGTH_SHORT).show();
                                        Log.e("是否是责任人",""+isShow);
                                    }

                                    renwu_item_name.setText(createUserName);//创建人
                                    renwu_item_date .setText(CreateDate);//创建日期
                                    renwu_client_name.setText(customerName);//客户名称
                                    renwu_product_name.setText(productName);//产品名称
                                    renwu_cargo_number.setText(deliverQty);//交货数量
                                    renwu_cargo_time.setText(deliverDate);//交货时间
                                    renwu_indent_id.setText(billNo);//催单编号
                                    renwu_dingdan_id.setText(OrderNo);//订单号
                                    renwu_zeren.setText(toDoUserName);//责任人
                                    renwu_chaosong.setText(user);;//抄送人

                                }

                                getHandle(type);


                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(stringRequest);


        }
    }

    private void getOuterData(final String user,final String type) {
        {
            String Url="";
            Url= UrlUtil.GetInnerUrgeOrderUrl(UrlUtil.IP, UrlUtil.OuterUrgeOrder04,billNo);
            Log.e("获取抄送人信息Url",Url);

            StringRequest stringRequest = new StringRequest(Url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            response = XmlUtil.getDataByXml(response, "string", TAG);
                            Log.e("获取外部催单详情信息", response);
                            data.clear();
                            try {
                                JSONObject jsonData = new JSONObject(response);
                                JSONObject jsonObject;
                                JSONArray array = jsonData.getJSONArray("rows");
                                for (int i = 0; i < array.length(); i++) {

                                    jsonObject = array.getJSONObject(i);
                                    String outerUrgeOrderId = jsonObject.getString("OuterUrgeOrderId");//任务ID
                                    String customerName = jsonObject.getString("VendorName");//客户名称
                                    String productName = jsonObject.getString("ProductName");//产品名称
                                    String deliverDate = jsonObject.getString("DeliverDate");//交货时间
                                    String CreateDate = jsonObject.getString("CreateDate");//创建时间
                                    if(!deliverDate.equals("")||!deliverDate.equals("null")||deliverDate!=null){
                                        deliverDate= deliverDate.replaceAll("T"," ");
                                    } if(!CreateDate.equals("")||!CreateDate.equals("null")||CreateDate!=null){
                                        CreateDate= deliverDate.replaceAll("T"," ");
                                    }
                                    String deliverAddress = jsonObject.getString("DeliverAddress");//地址
                                    String createUserName = jsonObject.getString("CreateUserName");//创建人
                                    String toDoUserName = jsonObject.getString("ToDoUserName");//责任人
                                    String deliverQty = jsonObject.getString("DeliverQty");//数量
                                    String billNo = jsonObject.getString("BillNo");//任务编号
                                    String OrderNo = jsonObject.getString("OrderNo");//任务编号
                                    int CurrentStep = jsonObject.getInt("CurrentStep");//任务编号
                                    currentStep=CurrentStep;
                                    renwu_item_name.setText(createUserName);//创建人
                                    renwu_item_date .setText(CreateDate);//创建日期
                                    renwu_client_name.setText(customerName);//客户名称
                                    renwu_product_name.setText(productName);//产品名称
                                    renwu_cargo_number.setText(deliverQty);//交货数量
                                    renwu_cargo_time.setText(deliverDate);//交货时间
                                    renwu_indent_id.setText(billNo);//催单编号
                                    renwu_dingdan_id.setText(OrderNo);//订单号
                                    renwu_zeren.setText(toDoUserName);//责任人
                                    renwu_chaosong.setText(user);;//抄送人

                                }
                                getHandle(type);


                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(stringRequest);


        }
    }

    private void getHandle(final String type) {
        {
            String Url="";
            if(type.equals("内部催单")){
                 Url= UrlUtil.GetInnerUrgeOrderHandleUrl(UrlUtil.IP, UrlUtil.GetInnerUrgeOrderHandle,billNo);
                Log.e("获取抄送人信息Url",Url);
            }else if(type.equals("采购催单1")||type.equals("采购催单2")||type.equals("采购催单3")||type.equals("采购催单")){
                Url= UrlUtil.GetOuterUrgeOrderHandleUrl(UrlUtil.IP, UrlUtil.GetOuterUrgeOrderHandle,billNo);
            }
           Log.e("获取回复信息Url",Url);
            StringRequest stringRequest = new StringRequest(Url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            response = XmlUtil.getDataByXml(response, "string", TAG);
                            Log.e("回复信息 ", response);
                            data.clear();
                            try {
                                JSONObject jsonData = new JSONObject(response);
                                JSONObject jsonObject;
                                JSONArray array = jsonData.getJSONArray("rows");
                                for (int i = 0; i < array.length(); i++) {
                                   jsonObject = array.getJSONObject(i);
                                    String billNo;
                                    if(type.equals("内部催单")){
                                        billNo= jsonObject.getString("InnerUrgeBillNo");//任务ID
                                    }else{
                                        billNo= jsonObject.getString("OuterUrgeBillNo");//任务ID
                                    }

                                    String deliverDate = jsonObject.getString("DeliverDate");//处理时间
                                    if(!deliverDate.equals("")||!deliverDate.equals("null")||deliverDate!=null){
                                        deliverDate= deliverDate.replaceAll("T"," ");
                                    }
                                    String Type = jsonObject.getString("Type");//类型
                                    if(Type.equals("")||Type.equals("null")){
                                        Type="--";
                                    }
                                    String Memo = jsonObject.getString("Memo");//说明
                                    String deliverQty = jsonObject.getString("DeliverQty");//数量
                                    int myStep = jsonObject.getInt("Step");//阶段

                                    ReplyInfo replyInfo=new ReplyInfo(billNo,deliverDate,Type,Memo,deliverQty,currentStep,myStep);
                                    data.add(replyInfo);
                                }




                                adapter = new RenWuAdapter(RenWuActivity.this,data,type,isShow,renwu_item_name.getText().toString(),renwu_zeren.getText().toString());
                                renwu_item.setAdapter(adapter);
                                new ScrollListview(renwu_item);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(stringRequest);


        }
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){

            case R.id.tetle_back:
                finish();
                break;

            case R.id.renwu_rl1:
                //跳转到回复页面
                intent = new Intent(RenWuActivity.this,ReplyActivity.class);
                if(type.equals("内部催单")){
                    intent.putExtra("InnerUrgeBillNo",renwu_indent_id.getText().toString());
                }else if(type.equals("采购催单1")||type.equals("采购催单2")||type.equals("采购催单3")||type.equals("采购催单")){
                    intent.putExtra("OuterUrgeBillNo",renwu_indent_id.getText().toString());
                }
                intent.putExtra("DeliverQty",renwu_cargo_number.getText().toString());//交货数量
                intent.putExtra("DeliverDate",renwu_cargo_time.getText().toString());//交货时间
                intent.putExtra("Step",currentStep);
                intent.putExtra("Type",type);
                startActivity(intent);
                break;

            case R.id.renwu_rl2:
                finish();
                break;
        }
    }
}
