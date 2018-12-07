package com.shuben.zhixing.www.patrol.oneclass;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.photopicker.OnPhotoPickListener;
import com.photopicker.PhotoPicker;
import com.photopicker.PhotoPreview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.wxx.net.HttpResult;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.UserActivity;
import com.shuben.zhixing.www.patrol.adapter.GridAdapter;
import com.shuben.zhixing.www.patrol.adapter.ImageAdapter;
import com.shuben.zhixing.push.PushMessageModel;
import com.shuben.zhixing.www.util.ScrollListview;
import com.shuben.zhixing.www.util.UploadUtil;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import name.gudong.drawable.OneDrawable;

import static com.nightonke.boommenu.Util.setDrawable;

public class AddLightActivity extends BaseActvity implements View.OnClickListener{
    private GridView gridView,gridview_photo;
    private List<String> str_list;
    private List<Integer> r_list=new ArrayList<Integer>();
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private EditText ed_qus;
    private RatingBar ratingBar;
    private TextView tx_back,tx_commit,tx_org,tx_user;
    private LinearLayout lay_back;
    private ImageView title_back;
    private List<String>  list;
    private Button bnt_camera;
    private  Intent intent;

    private ImageAdapter gridAdapter;
    private String TAG =AddLightActivity.class.getSimpleName();
    private RelativeLayout lay_s01,lay_s02;
    private Map<String,String> UserMap=new HashMap<String,String>();//组员
    private Map<String,String> OrgMap=new HashMap<String,String>();//部门
    private String mlinkedTableId="";
    private String CopyTo="";
    private List<String> Copy_list=new ArrayList<String>();
    private CheckBox checkBox01,checkBox02;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_light;
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetHighLightKeyword");
        params.put("TenantId", SharedPreferencesTool.getMStool(AddLightActivity.this).getTenantId());
        params.put("ClassId", getIntent().getStringExtra("ClassId"));
        params.put("WorkShopId", getIntent().getStringExtra("WorkShopId"));
        //params.put("AreaId", getIntent().getStringExtra("AreaId"));
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetProblemKeyword");
            myData.put("TenantId", SharedPreferencesTool.getMStool(AddLightActivity.this).getTenantId());
            myData.put("ClassId", getIntent().getStringExtra("ClassId"));
            myData.put("WorkShopId", getIntent().getStringExtra("WorkShopId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*******************",myData.toString());

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(AddLightActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String Keyword=jData.getString("Keyword");//问题编号
                        int StarLevel=jData.getInt("StarLevel");//问题编号

                        r_list.add(StarLevel);
                        str_list.add(Keyword);

                    }
                    GridAdapter adapter=new GridAdapter(AddLightActivity.this,str_list,r_list,ed_qus,ratingBar);
                    gridView.setAdapter(adapter);
                    new ScrollListview(gridView);
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
    private void initView() {
        str_list=new ArrayList<String>();
        tx_back= (TextView) findViewById(R.id.tx_back);
        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        title_back= (ImageView) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        tx_commit=(TextView) findViewById(R.id.tx_commit);
        tx_commit.setOnClickListener(this);
        tx_org=(TextView) findViewById(R.id.tx_org);
        tx_user=(TextView) findViewById(R.id.tx_user);
        ed_qus=(EditText)findViewById(R.id.ed_question);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar4);
        checkBox01=(CheckBox)findViewById(R.id.checkBox);
        checkBox02=(CheckBox)findViewById(R.id.checkBox2);
        gridView=(GridView)findViewById(R.id.gridview_add);
        gridview_photo=(GridView)findViewById(R.id.gridview_photo);

        gridview_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoPreview.init().setPhotoPaths(imagePaths).setCurrentPos(position).setPreviewOnly(true).startPreview(AddLightActivity.this, null);

            }

        });
        gridAdapter = new ImageAdapter(AddLightActivity.this,imagePaths);
        gridview_photo.setAdapter(gridAdapter);

        NiceSpinner niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        niceSpinner.setTextColor(Color.rgb(22,155,213));

        list=new ArrayList<String>();
        list.add("现场5S");
        list.add("点检");
        list.add("品质");
        list.add("清查");
        niceSpinner.attachDataSource(list);

        bnt_camera=(Button) findViewById(R.id.bnt_camera);
        bnt_camera.setOnClickListener(this);

        setDrawable(bnt_camera, OneDrawable.createBgDrawable(AddLightActivity.this, R.mipmap.patrol_camera_icon));
        lay_s01=(RelativeLayout) findViewById(R.id.lay_s01);
        lay_s01.setOnClickListener(this);
        lay_s02=(RelativeLayout) findViewById(R.id.lay_s02);
        lay_s02.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != data) {
            String userName = data.getStringExtra("userName");
            String userId = data.getStringExtra("userId");
            String OrganizeName = data.getStringExtra("OrganizeName");
            String OrganizeId = data.getStringExtra("OrganizeId");
            //获取到客户的姓名和紧急催单的供应商
            if (requestCode == UrlUtil.AddLightActivity_RequstCode01) {
                if (resultCode == UrlUtil.OrganizeActivity_ResultCode) {
                    if (userName != null) {
                        OrgMap.put(userName, userId);
                        tx_org.setText(userName);
                    }
                }
            }else  if (requestCode == UrlUtil.AddLightActivity_RequstCode02) {
                if (resultCode == UrlUtil.UserActivity_ResultCode) {
                    if (userName != null) {
                        OrgMap.put(OrganizeName, OrganizeId);
                        tx_org.setText(OrganizeName);
                        UserMap.put(userName, userId);
                        tx_user.setText(userName);
                    }
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
            case R.id.title_back:
                finish();
                break;
            case R.id.tx_back:
                finish();
                break;
            case R.id.bnt_camera:
                camera();
                break;
            case R.id.tx_commit:
                if(ed_qus.getText().toString().equals("")||tx_org.getText().toString().equals("")||tx_user.getText().toString().equals("")){
                    Toast.makeText(AddLightActivity.this,"参数信息不完整",Toast.LENGTH_SHORT).show();
                }else{
                    tx_commit.setEnabled(false);
                    commit();
                }

                break;
            case R.id.lay_s01:
               /* intent=new Intent();
                intent.setClass(AddLightActivity.this, OrganizeActivity.class);
                intent.putExtra("Title","部门选择");
                startActivityForResult(intent, UrlUtil.AddLightActivity_RequstCode01);*/
                break;
            case R.id.lay_s02:
                intent=new Intent();
                intent.setClass(AddLightActivity.this, UserActivity.class);
                intent.putExtra("Title","人员选择");
                startActivityForResult(intent,UrlUtil.AddLightActivity_RequstCode02);
               /* if(!tx_org.getText().toString().equals("")){
                    intent.putExtra("OrganizeId",OrgMap.get(tx_org.getText().toString()));
                    startActivityForResult(intent,UrlUtil.AddLightActivity_RequstCode02);
                }else{
                    Toast.makeText(AddLightActivity.this,"请先选择部门",Toast.LENGTH_SHORT).show();
                }*/

                break;

            default:
                break;

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //判断请求码
        switch (requestCode){
            case 1:
                //如果同意，打开相机
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    camera();
                }else{
                    //Toast.makeText(this,"哈哈哈",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean checkWriteExternalPermission() {
        String permission = "android.permission.CAMERA"; //你要判断的权限名字
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    private void camera() {
        if(checkWriteExternalPermission()){
            PhotoPicker.init().setMaxCount(5).setShowCamera(true).setUseSystemCamera(false).startPick(AddLightActivity.this, new OnPhotoPickListener() {
                @Override
                public void onPhotoPick(boolean userCancel, List<String> list) {
                    if (userCancel) {
                        return;
                    }
                    imagePaths.clear();
                    imagePaths.addAll(list);
                    gridAdapter.notifyDataSetChanged();
                }

                @Override
                public void onPhotoCapture(String path) {
                    imagePaths.add(path);
                    gridAdapter.notifyDataSetChanged();
                }
            });
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("权限禁用提示").setIcon(R.drawable.icon)
                    .setMessage("对不起，相机访问权限未开启")
                    .setPositiveButton("马上开启", new DialogInterface.OnClickListener() {// 积极

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            // TODO Auto-generated method stub
                            ActivityCompat.requestPermissions(AddLightActivity.this,new String []{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},1);
                        }
                    }).setNegativeButton("拒绝", new DialogInterface.OnClickListener() {// 消极

                @Override
                public void onClick(DialogInterface dialog,
                                    int which) {
                    // TODO Auto-generated method stub
                }
            });
            builder.create().show();
        }






    }
    private void commit() {
        if(checkBox01.isSelected()){
            Copy_list.add(checkBox01.getText().toString());
        }
        if(checkBox02.isSelected()){
            Copy_list.add(checkBox02.getText().toString());
        }
        for (int i = 0; i < Copy_list.size(); i++) {
                if (!CopyTo.equals("")){
                    CopyTo += ",";
                }
            CopyTo += Copy_list.get(i) ;
            }
        try {
            CopyTo = URLEncoder.encode(CopyTo, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditRecordHighlight");
        params.put("TenantId", SharedPreferencesTool.getMStool(AddLightActivity.this).getTenantId());
        params.put("HighlightId", "");
        params.put("RecordId",getIntent().getStringExtra("RecordId"));
        params.put("HighlightDesc", ed_qus.getText().toString());//问题描述
        params.put("Score", ratingBar.getRating()+"");//星级
        params.put("CopyTo", CopyTo);//巡线标准
        params.put("ImproveUserId",UserMap.get(tx_user.getText().toString()));//亮点改善人
        if(getIntent().getStringExtra("StandardItemId")!=null){
            params.put("StandardItemId", getIntent().getStringExtra("StandardItemId"));//巡线标准
        }else{
            params.put("StandardItemId", "");//巡线标准
        }
        params.put("CreateUserCode", SharedPreferencesTool.getMStool(AddLightActivity.this).getUserCode());//当前用户工号
        JSONObject data=new JSONObject();
        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditRecordHighlight");
            data.put("TenantId", SharedPreferencesTool.getMStool(AddLightActivity.this).getTenantId());
            data.put("HighlightId", "");
            data.put("RecordId",getIntent().getStringExtra("RecordId"));
            data.put("HighlightDesc", ed_qus.getText().toString());//问题描述
            data.put("Score", ratingBar.getRating()+"");//星级
            data.put("CopyTo", "");//巡线标准
            data.put("ImproveUserId",UserMap.get(tx_user.getText().toString()));//亮点改善人
            if(getIntent().getStringExtra("StandardItemId")!=null){
                data.put("StandardItemId", getIntent().getStringExtra("StandardItemId"));//巡线标准
            }else{
                data.put("StandardItemId", "");//巡线标准
            }
            data.put("CreateUserCode", SharedPreferencesTool.getMStool(AddLightActivity.this).getUserCode());//当前用户工号

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******亮点提交*****",data.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(AddLightActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String status=jsonobj.getString("status");
                    String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        Toast.makeText(AddLightActivity.this,"提交亮点成功",Toast.LENGTH_SHORT).show();
                        String  msg="提交亮点";
                        String from=SharedPreferencesTool.getMStool(AddLightActivity.this).getUserId();
                        String receivers=   UserMap.get(tx_user.getText().toString());
                        PushMessageModel push=new PushMessageModel(msg,from,receivers);
                        push.getSource(new Function1<HttpResult<String>, Unit>() {
                            @Override
                            public Unit invoke(HttpResult<String> stringHttpResult) {
                                if (stringHttpResult.getSuccess()){
                                    //服务器响应成功
                                    stringHttpResult.getData();
                                }else {
                                    Toast.makeText(AddLightActivity.this, stringHttpResult.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                return null;
                            }
                        });

                        if(imagePaths.size()>0){
                            for(int i=0;i<imagePaths.size();i++){
                                final File file = new File(imagePaths.get(i));
                                Log.e("获取图片地址",imagePaths.get(i));
                                getUrl(file,message);
                            }
                        }else{
                            tx_commit.setEnabled(true);
                            setResult(UrlUtil.AddLightActivity_ResultCode,intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(AddLightActivity.this,message,Toast.LENGTH_SHORT).show();
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
    private void getUrl(final File file, final String linkedTableId){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.GET, SharedPreferencesTool.getMStool(AddLightActivity.this).getIp()+UrlUtil.SeveImageUrl,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String AppUrl=jData.getString("AppUrl");
                        final String url=UrlUtil.UploadImageUrl(AppUrl,"LinePatrol","EditUploadFiles",SharedPreferencesTool.getMStool(AddLightActivity.this).getTenantId(),linkedTableId,UrlUtil.LinkedTable03,"inepatrol_record_question");
                        Log.e("*****开始上传*****",url);
                        /**
                         * 下载线程
                         */
                        Runnable downloadRun = new Runnable(){

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                upLoadImage(file,url);
                            }
                        };

                        new Thread(downloadRun).start();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                setResult(UrlUtil.AddLightActivity_ResultCode,intent);
                finish();
            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
        requestQueue.add(newMissRequest);
    }
    private void upLoadImage(final File file, final String url) {
        String request= UploadUtil.uploadFile(file,url);
        JSONObject jsonobj= null;
        try {
            jsonobj = new JSONObject(request);
            String status=jsonobj.getString("status");
            Log.e("*********status:",status);

            if(status.equals("true")){
                myHandler.sendEmptyMessage(1);

            }else{
                myHandler.sendEmptyMessage(2);
               // final String message=jsonobj.getString("message");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(AddLightActivity.this,"上传亮点图片成功",Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    tx_commit.setEnabled(true);
                    setResult(UrlUtil.AddLightActivity_ResultCode,intent);
                    finish();
                    break;
                case 2:
                    tx_commit.setEnabled(true);
                    Toast.makeText(AddLightActivity.this,"上传图片失败",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };


}


