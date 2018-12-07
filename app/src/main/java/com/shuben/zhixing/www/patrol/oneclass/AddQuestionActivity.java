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
import com.shuben.zhixing.www.util.CalendarUtil;
import com.shuben.zhixing.www.util.DateUtil;
import com.shuben.zhixing.www.util.ScrollListview;
import com.shuben.zhixing.www.util.UploadUtil;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import name.gudong.drawable.OneDrawable;

import static com.nightonke.boommenu.Util.setDrawable;

public class AddQuestionActivity extends BaseActvity implements View.OnClickListener{
    private List<String> str_list=new ArrayList<String>();
    private List<Integer> r_list=new ArrayList<Integer>();
    private EditText ed_qus;
    private RatingBar ratingBar;
    private TextView tx_back,tx_commit,tx_org,tx_user;
    private LinearLayout lay_back;
    private ImageView title_back;
    private List<String>  list;
    private  TextView tx_date;
    private Button bnt_camera;
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private  Intent intent;

    private GridView gridView,gridview_photo;
    private int columnWidth;
    private ImageAdapter gridAdapter;
    private String TAG =AddQuestionActivity.class.getSimpleName();
    private RelativeLayout lay_s01,lay_s02;
    private Map<String,String> UserMap=new HashMap<String,String>();//组员
    private Map<String,String> OrgMap=new HashMap<String,String>();//部门
    private String mlinkedTableId="";
    // 上传单线程池
    private ExecutorService pool;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_question;
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
        params.put("ApiCode", "GetProblemKeyword");
        params.put("TenantId", SharedPreferencesTool.getMStool(AddQuestionActivity.this).getTenantId());
        params.put("ClassId", getIntent().getStringExtra("ClassId"));
        params.put("WorkShopId", getIntent().getStringExtra("WorkShopId"));
        params.put("ItemType", getIntent().getStringExtra("ItemType"));
        //params.put("AreaId", getIntent().getStringExtra("AreaId"));

        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetProblemKeyword");
            myData.put("TenantId", SharedPreferencesTool.getMStool(AddQuestionActivity.this).getTenantId());
            myData.put("ClassId", getIntent().getStringExtra("ClassId"));
            myData.put("WorkShopId", getIntent().getStringExtra("WorkShopId"));
            myData.put("ItemType", getIntent().getStringExtra("ItemType"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("**********问题标准*********",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(AddQuestionActivity.this).getIp()+UrlUtil.Url,
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
                    GridAdapter adapter=new GridAdapter(AddQuestionActivity.this,str_list,r_list,ed_qus,ratingBar);
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
        requestQueue.add(newMissRequest);
    }

    private void initView() {
        bnt_camera=(Button) findViewById(R.id.bnt_camera);
        bnt_camera.setOnClickListener(this);
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
        gridView=(GridView)findViewById(R.id.gridview_add);


        gridview_photo=(GridView)findViewById(R.id.gridview_photo);

        gridview_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoPreview.init().setPhotoPaths(imagePaths).setCurrentPos(position).setPreviewOnly(true).startPreview(AddQuestionActivity.this, null);

                }

        });
        gridAdapter = new ImageAdapter(AddQuestionActivity.this,imagePaths);
        gridview_photo.setAdapter(gridAdapter);

        NiceSpinner niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        niceSpinner.setTextColor(Color.rgb(22,155,213));

        list=new ArrayList<String>();
        list.add("现场5S");
        list.add("点检");
        list.add("品质");
        list.add("清查");
        niceSpinner.attachDataSource(list);
        Button bnt_calendar=(Button)findViewById(R.id.bnt_calendar);
        tx_date=(TextView)findViewById(R.id.tx_date);
        tx_date.setText(DateUtil.getInstance().getDateOfToDay03());
        bnt_calendar.setOnClickListener(this);
        bnt_camera=(Button) findViewById(R.id.bnt_camera);
        bnt_camera.setOnClickListener(this);
        setDrawable(bnt_camera, OneDrawable.createBgDrawable(AddQuestionActivity.this, R.mipmap.patrol_camera_icon));
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
                if (requestCode == UrlUtil.AddQuestionActivity_RequstCode01) {
                    if (resultCode == UrlUtil.OrganizeActivity_ResultCode) {
                        if (userName != null) {
                            OrgMap.put(userName, userId);
                            tx_org.setText(userName);
                        }
                    }
                }else  if (requestCode == UrlUtil.AddQuestionActivity_RequstCode02) {
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

    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        imagePaths.addAll(paths);
        gridAdapter  = new ImageAdapter(AddQuestionActivity.this,imagePaths);
        gridview_photo.setAdapter(gridAdapter);
        new ScrollListview(gridview_photo);
        if(imagePaths.size()>0){
            new ScrollListview(gridview_photo);

        }

        try{
            JSONArray obj = new JSONArray(imagePaths);
        }catch (Exception e){
            e.printStackTrace();
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
            case R.id.bnt_calendar:
                setDate();
                break;
            case R.id.bnt_camera:
                camera();
                break;
            case R.id.tx_commit:
                if(ed_qus.getText().toString().equals("")||tx_org.getText().toString().equals("")||tx_user.getText().toString().equals("")){
                    Toast.makeText(AddQuestionActivity.this,"参数信息不完整",Toast.LENGTH_SHORT).show();
                }else{
                    tx_commit.setEnabled(false);
                    commit();
                }

                break;
            case R.id.lay_s01:
               /* intent=new Intent();
                intent.setClass(AddQuestionActivity.this, OrganizeActivity.class);
                intent.putExtra("Title","部门选择");
                startActivityForResult(intent,UrlUtil.AddQuestionActivity_RequstCode01);*/
                break;
            case R.id.lay_s02:
                intent=new Intent();
                intent.setClass(AddQuestionActivity.this, UserActivity.class);
                intent.putExtra("Title","人员选择");
               // intent.putExtra("OrganizeId",OrgMap.get(tx_org.getText().toString()));
                startActivityForResult(intent,UrlUtil.AddQuestionActivity_RequstCode02);
               /* if(!tx_org.getText().toString().equals("")){
                    intent.putExtra("OrganizeId",OrgMap.get(tx_org.getText().toString()));
                    startActivityForResult(intent,UrlUtil.AddQuestionActivity_RequstCode02);
                }else{
                    Toast.makeText(AddQuestionActivity.this,"请先选择部门",Toast.LENGTH_SHORT).show();
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
                //如果同意，就拨打
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
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
            PhotoPicker.init().setMaxCount(5).setShowCamera(true).setUseSystemCamera(false).startPick(AddQuestionActivity.this, new OnPhotoPickListener() {
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
                            ActivityCompat.requestPermissions(AddQuestionActivity.this,new String []{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},1);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditRecordProblem");
        params.put("TenantId", SharedPreferencesTool.getMStool(AddQuestionActivity.this).getTenantId());
        params.put("ProblemId", "");
        params.put("RecordId",getIntent().getStringExtra("RecordId"));
        params.put("ProblemDesc", ed_qus.getText().toString());//问题描述
        params.put("Severity", ratingBar.getRating()+"");//严重程度
        params.put("LiableUserId",UserMap.get(tx_user.getText().toString()));
        params.put("DueDate", tx_date.getText().toString());
        if(getIntent().getStringExtra("StandardItemId")!=null){
            params.put("StandardItemId", getIntent().getStringExtra("StandardItemId"));//巡线标准
        }else{
            params.put("StandardItemId", "");//巡线标准
        }

        params.put("CreateUserCode", SharedPreferencesTool.getMStool(AddQuestionActivity.this).getUserCode());//当前用户工号

        JSONObject data=new JSONObject();
        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditRecordProblem");
            data.put("TenantId", SharedPreferencesTool.getMStool(AddQuestionActivity.this).getTenantId());
            data.put("ProblemId", "");
            data.put("RecordId",getIntent().getStringExtra("RecordId"));
            data.put("ProblemDesc", ed_qus.getText().toString());//问题描述
            data.put("Severity", ratingBar.getRating());//严重程度
            data.put("LiableUserId", UserMap.get(tx_user.getText().toString()));
            data.put("DueDate", tx_date.getText().toString());
            if(getIntent().getStringExtra("StandardItemId")!=null){
                data.put("StandardItemId", getIntent().getStringExtra("StandardItemId"));//巡线标准
            }else{
                data.put("StandardItemId", "");//巡线标准
            }

            data.put("CreateUserCode", SharedPreferencesTool.getMStool(AddQuestionActivity.this).getUserCode());//当前用户工号


        } catch (JSONException e) {
            e.printStackTrace();
        }
       Log.e("***********",data.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(AddQuestionActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String status=jsonobj.getString("status");
                    final String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        Toast.makeText(AddQuestionActivity.this,"提交问题成功",Toast.LENGTH_SHORT).show();
                        String  msg="提交问题";
                        String from=SharedPreferencesTool.getMStool(AddQuestionActivity.this).getUserId();
                        String receivers=   UserMap.get(tx_user.getText().toString());
                        PushMessageModel push=new PushMessageModel(msg,from,receivers);
                        push.getSource(new Function1<HttpResult<String>, Unit>() {
                            @Override
                            public Unit invoke(HttpResult<String> stringHttpResult) {
                                if (stringHttpResult.getSuccess()){
                                    //服务器响应成功
                                    stringHttpResult.getData();
                                }else {
                                    Toast.makeText(AddQuestionActivity.this, stringHttpResult.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                return null;
                            }
                        });

                        if(imagePaths.size()>0){
                            for(int i=0;i<imagePaths.size();i++){
                                final File file = new File(imagePaths.get(i));
                                getUrl(file,message);
                            }
                        }else{
                            tx_commit.setEnabled(true);
                            setResult(UrlUtil.AddQuestionActivity_ResultCode,intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(AddQuestionActivity.this,message,Toast.LENGTH_SHORT).show();
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
                Request.Method.GET, SharedPreferencesTool.getMStool(AddQuestionActivity.this).getIp()+UrlUtil.SeveImageUrl,
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
                        final String url=UrlUtil.UploadImageUrl(AppUrl,"LinePatrol","EditUploadFiles",SharedPreferencesTool.getMStool(AddQuestionActivity.this).getTenantId(),linkedTableId,UrlUtil.LinkedTable01,"inepatrol_record_question");
                       // final String url=UrlUtil.UploadImageUrl(AppUrl,"LinePatrol","EditUploadFiles","00000000-0000-0000-0000-000000000001",linkedTableId,UrlUtil.LinkedTable01,"inepatrol_record_question");
                       Log.e("**********",url);
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
                setResult(UrlUtil.AddQuestionActivity_ResultCode,intent);
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
                myHandler.sendEmptyMessage(UrlUtil.SUCCESS);

            }else{
                myHandler.sendEmptyMessage(UrlUtil.SUCCESS);

                final String message=jsonobj.getString("message");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void setDate() {
        CalendarUtil.getInstance().setDate(AddQuestionActivity.this,tx_date);
    }

    Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UrlUtil.SUCCESS:
                    Toast.makeText(AddQuestionActivity.this,"上传图片成功",Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    tx_commit.setEnabled(true);
                    setResult(UrlUtil.AddQuestionActivity_ResultCode,intent);
                    finish();
                    break;
                case UrlUtil.ERRORR:
                    Toast.makeText(AddQuestionActivity.this,"上传图片失败",Toast.LENGTH_SHORT).show();
                    setResult(UrlUtil.AddQuestionActivity_ResultCode,intent);
                    tx_commit.setEnabled(true);
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };


}
