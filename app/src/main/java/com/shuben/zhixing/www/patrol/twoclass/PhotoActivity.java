package com.shuben.zhixing.www.patrol.twoclass;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.photopicker.OnPhotoPickListener;
import com.photopicker.PhotoPicker;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.ImageAdapter;
import com.shuben.zhixing.www.patrol.adapter.PhotoAdapter;
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

public class PhotoActivity extends BaseActvity implements View.OnClickListener{
    private LinearLayout lay_back;
    private ListView photo_listview;
    private Button bnt_camera;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ImageAdapter gridAdapter;
    private LoadingDailog dialog;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private int screenWidth = 0; //屏幕宽度
    private List<String> mList=new ArrayList<String>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo;
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
        loadImage(getIntent().getStringExtra("RecordId"));
    }


    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();

        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        screenWidth = dm.widthPixels;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_stub)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        lay_back=(LinearLayout) findViewById(R.id.lay_back);
        bnt_camera=(Button) findViewById(R.id.bnt_camera);
        photo_listview=(ListView) findViewById(R.id.photo_listview);
        lay_back.setOnClickListener(this);
        bnt_camera.setOnClickListener(this);

    }

    private void loadImage(String LinkedTableId) {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(PhotoActivity.this);
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            params.put("ApiCode", "GetFiles");
            params.put("TenantId", SharedPreferencesTool.getMStool(PhotoActivity.this).getTenantId());
            params.put("LinkedTableId", LinkedTableId);
            params.put("LinkedTable", UrlUtil.LinkedTable04);
            JSONObject myData=new JSONObject();
            try {
                myData.put("AppCode", "LinePatrol");
                myData.put("ApiCode", "GetFiles");
                myData.put("TenantId", SharedPreferencesTool.getMStool(PhotoActivity.this).getTenantId());
                myData.put("LinkedTableId", LinkedTableId);
                myData.put("LinkedTable", UrlUtil.LinkedTable04);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("**************",myData.toString());

            JsonObjectRequest newMissRequest = new JsonObjectRequest(
                    Request.Method.POST, SharedPreferencesTool.getMStool(PhotoActivity.this).getIp()+UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonobj) {
                    Log.e("KKKKKKKK", " " + jsonobj.toString());
                    try {
                        JSONArray jArray=jsonobj.getJSONArray("rows");
                        JSONObject jData;
                        mList.clear();
                        for (int i=0;i<jArray.length();i++){
                            jData=jArray.getJSONObject(i);
                            String FilePath=jData.getString("FilePath");//问题编号
                            Log.e("*****FilePath******",FilePath);
                            mList.add(FilePath);
                        }
                        PhotoAdapter photoAdapter=new PhotoAdapter(PhotoActivity.this,mList,dialog,imageLoader,options,screenWidth);
                        photo_listview.setAdapter(photoAdapter);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.bnt_camera:
                camera();
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
            PhotoPicker.init().setMaxCount(5).setShowCamera(true).setUseSystemCamera(false).startPick(PhotoActivity.this, new OnPhotoPickListener() {
                @Override
                public void onPhotoPick(boolean userCancel, List<String> list) {
                    if (userCancel) {
                        return;
                    }
                    imagePaths.clear();
                    imagePaths.addAll(list);
                    gridAdapter=new ImageAdapter(PhotoActivity.this,imagePaths);
                    gridAdapter.notifyDataSetChanged();
                }

                @Override
                public void onPhotoCapture(String path) {
                    imagePaths.add(path);
                    for(int i=0;i<imagePaths.size();i++){
                        final File file = new File(imagePaths.get(i));
                        getUrl(file,getIntent().getStringExtra("RecordId"));
                    }
                    gridAdapter=new ImageAdapter(PhotoActivity.this,imagePaths);
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
                            ActivityCompat.requestPermissions(PhotoActivity.this,new String []{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},1);
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


    private void getUrl(final File file, final String linkedTableId){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.GET, SharedPreferencesTool.getMStool(PhotoActivity.this).getIp()+UrlUtil.SeveImageUrl,
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
                        final String url=UrlUtil.UploadImageUrl(AppUrl,"LinePatrol","EditUploadFiles",SharedPreferencesTool.getMStool(PhotoActivity.this).getTenantId(),linkedTableId,UrlUtil.LinkedTable04,"inepatrol_photos");
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


    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(PhotoActivity.this,"上传图片成功",Toast.LENGTH_SHORT).show();
                    loadImage(getIntent().getStringExtra("RecordId"));
                    break;
                case 2:
                    Toast.makeText(PhotoActivity.this,"上传图片失败",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };



}
