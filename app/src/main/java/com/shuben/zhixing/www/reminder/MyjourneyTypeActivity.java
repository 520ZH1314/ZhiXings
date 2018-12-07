package com.shuben.zhixing.www.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.adapter.TypeAdapterAdapter;
import com.shuben.zhixing.www.data.MyTypesDate;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.XmlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XianBo.Geng on 2016/12/16.
 * 行程类型
 */
public class MyjourneyTypeActivity extends BaseActvity implements View.OnClickListener {
    private static String TAG="MyjourneyTypeActivity";
    private ListView type_listv;
    private String item_name="";
    private String item_id;
    private ImageView tetle_back;
    private TextView tetle_text,tetle_tv_ok;
    private TypeAdapterAdapter adapter;
    private RequestQueue mQueue;
    private  List<MyTypesDate> data_id;
    private String  name;
    private List<String> list_name=new ArrayList<String>();
    private List<String> list_id=new ArrayList<String>();
    private  EditText tx_content;

    @Override
    public int getLayoutId() {
        return R.layout.activity_types;
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
        initview();
        name=getIntent().getStringExtra("TAG");
        String ToDoUserId=getIntent().getStringExtra("ToDoUserId");
        if(name.equals("caigou_zeren")||name.equals("nei_zeren")){
            getdate01(name,ToDoUserId);
            tetle_text.setText("责任人选择");
        }else if(name.equals("nei_chaosong")||name.equals("caigou_chaosong")){
            getdate01(name,ToDoUserId);
            tetle_text.setText("抄送人选择");
        }else if(name.equals("gongyingshang")){
            tetle_text.setText("供应商选择");
            getdate02();
        }
    }

    private void initview() {
        mQueue = Volley.newRequestQueue(this);
        tetle_back = (ImageView) findViewById(R.id.tetle_back);
        tetle_back.setOnClickListener(this);
        tetle_text = (TextView) findViewById(R.id.tetle_text);
        tetle_text.setText("人员选择");
        tetle_tv_ok=(TextView) findViewById(R.id.tetle_tv_ok);
        tetle_tv_ok.setVisibility(View.VISIBLE);
        tetle_tv_ok.setOnClickListener(this);
        type_listv = (ListView)findViewById(R.id.type_listv);
        tx_content=(EditText)findViewById(R.id.tx_content);

    }

    private void  getdate01(String res,String ToDoUserId) {
        String Url="";
         data_id = new ArrayList<>();
        if(res.equals("nei_chaosong")){
            Url= UrlUtil.GetInnerUrgeUserUrl(UrlUtil.IP, UrlUtil.GetInnerUrgeUser, SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).getUserId()+","+ToDoUserId,SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).getTenantId());
        }else if(res.equals("nei_zeren")){
            Url= UrlUtil.GetInnerUrgeUserUrl(UrlUtil.IP, UrlUtil.GetInnerUrgeUser, SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).getUserId(),SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).getTenantId());
        }else if(res.equals("caigou_zeren")){
            Url= UrlUtil.GetInnerUrgeUserUrl(UrlUtil.IP, UrlUtil.GetOuterUrgeUser, SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).getUserId(),SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).getTenantId02());
        }else if(res.equals("caigou_chaosong")){
            Url= UrlUtil.GetInnerUrgeUserUrl(UrlUtil.IP, UrlUtil.GetOuterUrgeUser, SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).getUserId()+","+ToDoUserId,SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).getTenantId()+","+SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).getTenantId02());
        }

        Log.e("获取责任人列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);
                        data_id.clear();
                        try {
                            JSONObject jsonData = new JSONObject(response);
                            JSONObject jsonObject;
                            JSONArray array = jsonData.getJSONArray("rows");
                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);
                                String userId = jsonObject.getString("UserId");//通知ID
                                String UserName = jsonObject.getString("UserName");//任务内容
                                MyTypesDate myTypesDate=new  MyTypesDate(userId,UserName,"0");
                                data_id.add(myTypesDate);
                            }
                            if(data_id.size()>0){

                            }else{
                                for (int i =0;i< 10;i++){
                                    MyTypesDate nd = new MyTypesDate("11","田巧","");
                                    data_id.add(nd);
                                }
                            }
                            adapter = new TypeAdapterAdapter(MyjourneyTypeActivity.this,data_id);
                            type_listv.setAdapter(adapter);
                            type_listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    if(name.equals("nei_zeren")||name.equals("caigou_zeren")){
                                        item_id = data_id.get(i).getId();
                                        item_name=data_id.get(i).getName();
                                    }else {
                                        if(data_id.get(i).getTnantId().equals("0")){
                                            data_id.get(i).setTnantId("1");
                                            Log.e("","0000000000000000000000000000000000000");
                                            // view.setBackgroundColor(Color.rgb(0, 153, 219));

                                            list_name.add(data_id.get(i).getName());
                                            list_id.add(data_id.get(i).getId());
                                            //Toast.makeText(MyjourneyTypeActivity.this, list_name.toString(), Toast.LENGTH_SHORT).show();
                                            String name="";
                                            String id="";
                                            for(int j=0;j<list_name.size();j++){

                                                if(j==list_name.size()-1){
                                                    name=name+list_name.get(j);
                                                    id=id+list_id.get(j);
                                                }else{
                                                    name=name+list_name.get(j)+",";
                                                    id=id+list_id.get(j)+",";
                                                }

                                            }
                                            item_name=name;
                                            item_id=id;

                                        }else  if(data_id.get(i).getTnantId().equals("1")){
                                            Log.e("","111111111111111111111111111111111111");
                                            data_id.get(i).setTnantId("0");
                                            //view.setBackgroundColor(Color.rgb(255, 255, 255));
                                            list_name.remove(data_id.get(i).getName());
                                            list_id.remove(data_id.get(i).getId());
                                            String name="";
                                            String id="";
                                            for(int j=0;j<list_name.size();j++){

                                                if(j==list_name.size()-1){
                                                    name=name+list_name.get(j);
                                                    id=id+list_id.get(j);
                                                }else{
                                                    name=name+list_name.get(j)+",";
                                                    id=id+list_id.get(j)+",";
                                                }

                                            }

                                            item_name=name;
                                            item_id=id;
                                           }

                                      /*  if(item_name.equals("")){
                                            item_id = data_id.get(i).getId();
                                            item_name=data_id.get(i).getName();
                                            data_id.get(i).setTnantId("1");
                                            //view.setBackgroundColor(Color.rgb(0, 153, 219));
                                        }else{
                                            if(data_id.get(i).getTnantId().equals("0")){
                                                data_id.get(i).setTnantId("1");
                                                Log.e("","0000000000000000000000000000000000000");
                                                // view.setBackgroundColor(Color.rgb(0, 153, 219));


                                                item_name =item_name+"," +data_id.get(i).getName();
                                                item_id = item_id+"," +data_id.get(i).getId();



                                            }else  if(data_id.get(i).getTnantId().equals("1")){
                                                Log.e("","111111111111111111111111111111111111");
                                                data_id.get(i).setTnantId("0");
                                                //view.setBackgroundColor(Color.rgb(255, 255, 255));
                                                if(item_name.length()>data_id.get(i).getName().length()){

                                                    item_name=item_name.replace(","+data_id.get(i).getName().length(),"");
                                                    //item_name=item_name.substring(0,item_name.length()-(data_id.get(i).getName().length()+1));
                                                    item_id=item_id.substring(0,item_id.length()-(data_id.get(i).getId().length()+1));

                                                }else{
                                                    item_name=item_name.substring(0,item_name.length()-(data_id.get(i).getName().length()));
                                                    item_id=item_id.substring(0,item_id.length()-(data_id.get(i).getId().length()));

                                                }


                                            }

                                        }*/
                                    }


                                    tx_content.setText(item_name);

                                    /*if(view.isSelected()){
                                        view.setSelected(false);

                                    }else{
                                        view.setBackgroundColor(Color.rgb(0, 153, 219));
                                        view.setSelected(true);
                                    }*/



                                }
                            });

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
    private void  getdate02() {
        data_id = new ArrayList<>();
        String Url= UrlUtil.GetVendorUrl(UrlUtil.IP, UrlUtil.GetVendor,SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).getTenantId());
        Log.e("获取供应商列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);
                        data_id.clear();
                        try {
                            JSONObject jsonData = new JSONObject(response);
                            JSONObject jsonObject;
                            JSONArray array = jsonData.getJSONArray("rows");
                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);
                                String TenantId = jsonObject.getString("VendorTenantId");//租户ID
                                String VendorId = jsonObject.getString("VendorId");//通知ID
                                String VendorName = jsonObject.getString("VendorName");//任务内容

                                MyTypesDate myTypesDate=new  MyTypesDate(VendorId,VendorName,TenantId);
                                data_id.add(myTypesDate);
                            }
                            if(data_id.size()>0){

                            }/*else{
                                for (int i =0;i< 10;i++){
                                    MyTypesDate nd = new MyTypesDate("11","田巧","");
                                    data_id.add(nd);
                                }
                            }*/
                            adapter = new TypeAdapterAdapter(MyjourneyTypeActivity.this,data_id);
                            type_listv.setAdapter(adapter);

                            type_listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    item_name = data_id.get(i).getName();
                                    item_id = data_id.get(i).getId();
                                    //Toast.makeText(MyjourneyTypeActivity.this,data_id.get(i).getTnantId().toString(),Toast.LENGTH_SHORT).show();
                                   // view.setBackgroundColor(Color.rgb(0, 153, 219));
                                    SharedPreferencesTool.getMStool(MyjourneyTypeActivity.this).saveTenantId02(data_id.get(i).getTnantId());

                                    tx_content.setText(item_name);
                                }
                            });

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;
            case R.id.tetle_tv_ok:
                Intent intent = new Intent();
                intent.putExtra("item_name",item_name);
                intent.putExtra("item_id",item_id);
                setResult(10,intent);
                finish();
                break;
        }
    }

}
