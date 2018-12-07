package com.shuben.zhixing.www.patrol.management;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
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
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.UserActivity;
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

import static com.nightonke.boommenu.Util.setDrawable;

public class SolveActivity extends BaseActvity implements View.OnClickListener{
    private TextView tx_back,tx_commit,tx_user;
    private EditText ed_qus;
    private Button bnt_camera;
    private LinearLayout lay_back;
    private RelativeLayout lay_s01;
    private  Intent intent;
    private Map<String,String> UserMap=new HashMap<String,String>();//组员
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private String IsSupervise="1";
    private ImageAdapter gridAdapter;
    private GridView gridview_photo;
    private CheckBox checkBox01,checkBox02;


    @Override
    public int getLayoutId() {
        return R.layout.activity_solve;
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

    private void initView() {
        lay_back=(LinearLayout) findViewById(R.id.lay_back);
        lay_s01=(RelativeLayout) findViewById(R.id.lay_s01);
        lay_back.setOnClickListener(this);
        lay_s01.setOnClickListener(this);
        tx_commit=(TextView) findViewById(R.id.tx_commit);
        tx_commit.setOnClickListener(this);
        ed_qus=(EditText) findViewById(R.id.editText2);
        tx_user=(TextView) findViewById(R.id.tx_user);
        checkBox01=(CheckBox) findViewById(R.id.checkBox);
        checkBox02=(CheckBox) findViewById(R.id.checkBox2);
        checkBox01.setSelected(true);
        checkBox01.setChecked(true);
        checkBox01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox01.setSelected(true);
                checkBox02.setSelected(false);
                checkBox02.setChecked(false);
                IsSupervise="1";

            }
        });
        checkBox02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox01.setSelected(false);
                checkBox01.setChecked(false);
                checkBox02.setSelected(true);
                IsSupervise="0";
            }
        });

        bnt_camera=(Button) findViewById(R.id.bnt_camera);
        bnt_camera.setOnClickListener(this);
        setDrawable(bnt_camera, OneDrawable.createBgDrawable(SolveActivity.this, R.mipmap.patrol_camera_icon));
        gridview_photo=(GridView)findViewById(R.id.gridview_photo);

        gridview_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoPreview.init().setPhotoPaths(imagePaths).setCurrentPos(position).setPreviewOnly(true).startPreview(SolveActivity.this, null);

            }

        });
        gridAdapter = new ImageAdapter(SolveActivity.this,imagePaths);
        gridview_photo.setAdapter(gridAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != data) {
            String userName = data.getStringExtra("userName");
            String userId = data.getStringExtra("userId");
            //获取到客户的姓名和紧急催单的供应商
             if (requestCode == UrlUtil.SolveActivity_RequstCode) {
                if (resultCode == UrlUtil.UserActivity_ResultCode) {
                    if (userName != null) {
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
            case R.id.lay_s01:
                intent=new Intent();
                intent.setClass(SolveActivity.this, UserActivity.class);
                intent.putExtra("Title","审核人选择");
                intent.putExtra("OrganizeId","");
                startActivityForResult(intent, UrlUtil.SolveActivity_RequstCode);
                break;
            case R.id.bnt_camera:
                camera();
                break;
            case R.id.tx_commit:
                if(ed_qus.getText().toString().equals("")||tx_user.getText().toString().equals("")){
                    Toast.makeText(SolveActivity.this,"参数信息不完整",Toast.LENGTH_SHORT).show();
                }else{
                    commit();
                }

                break;
            default:

            break;
        }}
    private void camera() {
        if(checkWriteExternalPermission()){
            PhotoPicker.init().setMaxCount(5).setShowCamera(true).setUseSystemCamera(false).startPick(SolveActivity.this, new OnPhotoPickListener() {
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
                            ActivityCompat.requestPermissions(SolveActivity.this,new String []{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},1);
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
        params.put("ApiCode", "EditRecordProblemHandle");
        params.put("ProblemId", getIntent().getStringExtra("ProblemId"));
        params.put("HandleResult", ed_qus.getText().toString());//问题描述
        params.put("IsSupervise", IsSupervise);//严重程度
        params.put("Auditor",UserMap.get(tx_user.getText().toString()));

        JSONObject data=new JSONObject();
        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditRecordProblemHandle");
            data.put("ProblemId", getIntent().getStringExtra("ProblemId"));
            data.put("HandleResult", ed_qus.getText().toString());//问题描述
            data.put("IsSupervise", IsSupervise);//严重程度
            data.put("Auditor",UserMap.get(tx_user.getText().toString()));



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****问题解决******",data.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST,SharedPreferencesTool.getMStool(SolveActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String status=jsonobj.getString("status");
                    final String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        Toast.makeText(SolveActivity.this,"提交问题成功",Toast.LENGTH_SHORT).show();
                        if(imagePaths.size()>0){
                            for(int i=0;i<imagePaths.size();i++){
                                final File file = new File(imagePaths.get(i));
                                getUrl(file,getIntent().getStringExtra("ProblemId"));
                            }
                        }else{
                            setResult(UrlUtil.SolveActivity_ResultCode,intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(SolveActivity.this,message,Toast.LENGTH_SHORT).show();
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
                Request.Method.GET,SharedPreferencesTool.getMStool(SolveActivity.this).getIp()+ UrlUtil.SeveImageUrl,
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
                        final String url=UrlUtil.UploadImageUrl(AppUrl,"LinePatrol","EditUploadFiles",SharedPreferencesTool.getMStool(SolveActivity.this).getTenantId(),linkedTableId,UrlUtil.LinkedTable02,"inepatrol_record_question");
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
                setResult(UrlUtil.SolveActivity_ResultCode,intent);
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
                    Toast.makeText(SolveActivity.this,"上传图片成功",Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    setResult(UrlUtil.SolveActivity_ResultCode,intent);
                    finish();
                    break;
                case 2:
                    Toast.makeText(SolveActivity.this,"上传图片失败",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };


        
    }

