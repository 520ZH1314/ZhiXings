package com.shuben.zhixing.www.inspection.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
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
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.ImageAdapter;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.UploadUtil;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.gudong.drawable.OneDrawable;



public class SolvePActivity extends BaseActvity implements View.OnClickListener{
    private TextView tx_back,tx_commit;
    private EditText ed_qus;
    private Button bnt_camera;
    private LinearLayout lay_back;
    private Intent intent;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private String IsSupervise="1";
    private ImageAdapter gridAdapter;
    private GridView gridview_photo;


    @Override
    public int getLayoutId() {
        return R.layout.activity_solve_p;
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
    }
    public   void setDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            //noinspection deprecation
            view.setBackgroundDrawable(drawable);
        }
    }
    private void initView() {
        lay_back=(LinearLayout) findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);
        tx_commit=(TextView) findViewById(R.id.tx_commit);
        tx_commit.setOnClickListener(this);
        ed_qus=(EditText) findViewById(R.id.editText2);

        bnt_camera=(Button) findViewById(R.id.bnt_camera);
        bnt_camera.setOnClickListener(this);
        setDrawable(bnt_camera, OneDrawable.createBgDrawable(SolvePActivity.this, R.mipmap.patrol_camera_icon));
        gridview_photo=(GridView)findViewById(R.id.gridview_photo);

        gridview_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoPreview.init().setPhotoPaths(imagePaths).setCurrentPos(position).setPreviewOnly(true).startPreview(SolvePActivity.this, null);

            }

        });
        gridAdapter = new ImageAdapter(SolvePActivity.this,imagePaths);
        gridview_photo.setAdapter(gridAdapter);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.bnt_camera:
                camera();
                break;
            case R.id.tx_commit:
                if(ed_qus.getText().toString().equals("")){
                    Toast.makeText(SolvePActivity.this,"参数信息不完整",Toast.LENGTH_SHORT).show();
                }else{
                    commit();
                }

                break;
            default:

                break;
        }}
    private void camera() {
        if(checkWriteExternalPermission()){
            PhotoPicker.init().setMaxCount(5).setShowCamera(true).setUseSystemCamera(false).startPick(SolvePActivity.this, new OnPhotoPickListener() {
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
                            ActivityCompat.requestPermissions(SolvePActivity.this,new String []{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},1);
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
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "EditRecordProblemHandle");
        params.put("ProblemId", getIntent().getStringExtra("ProblemId"));
        params.put("Measures", ed_qus.getText().toString());//问题描述

        JSONObject data=new JSONObject();
        try {
            data.put("AppCode", "PatrolInspection");
            data.put("ApiCode", "EditRecordProblemHandle");
            data.put("ProblemId", getIntent().getStringExtra("ProblemId"));
            data.put("Measures", ed_qus.getText().toString());//问题描述



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****问题解决******",data.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(SolvePActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String status=jsonobj.getString("status");
                    final String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        Toast.makeText(SolvePActivity.this,"提交问题成功",Toast.LENGTH_SHORT).show();
                        if(imagePaths.size()>0){
                            for(int i=0;i<imagePaths.size();i++){
                                final File file = new File(imagePaths.get(i));
                                getUrl(file,getIntent().getStringExtra("ProblemId"));
                            }
                        }else{
                            setResult(UrlUtil.SolvePActivity_ResultCode,intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(SolvePActivity.this,message,Toast.LENGTH_SHORT).show();
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
        params.put("AppCode", "PatrolInspection");
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.GET, SharedPreferencesTool.getMStool(SolvePActivity.this).getIp()+UrlUtil.SeveLinePatrolImageUrl,
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
                        final String url=UrlUtil.UploadInspectionImageUrl(AppUrl,"PatrolInspection","EditUploadFiles", SharedPreferencesTool.getMStool(SolvePActivity.this).getTenantId(),linkedTableId,UrlUtil.patrolinspection02,"patrolinspection02");
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
                setResult(UrlUtil.SolvePActivity_ResultCode,intent);
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean checkWriteExternalPermission() {
        String permission = "android.permission.CAMERA"; //你要判断的权限名字
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(SolvePActivity.this,"上传图片成功",Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    setResult(UrlUtil.SolvePActivity_ResultCode,intent);
                    finish();
                    break;
                case 2:
                    Toast.makeText(SolvePActivity.this,"上传图片失败",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };



}


