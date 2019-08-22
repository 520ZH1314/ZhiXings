package com.shuben.zhixing.www.inspection;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.InGridView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.photopicker.OnPhotoPickListener;
import com.photopicker.PhotoPicker;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.bean.ItemInfo;
import com.shuben.zhixing.www.patrol.UserActivity;
import com.shuben.zhixing.www.patrol.adapter.GridAdapter;
import com.shuben.zhixing.www.patrol.adapter.ImageAdapter;
import com.shuben.zhixing.www.util.CalendarUtil;
import com.shuben.zhixing.www.util.DateUtil;
import com.shuben.zhixing.www.util.ScrollListview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.UploadUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class AddProblemActivity extends BaseActvity implements View.OnClickListener{
    private List<String> str_list=new ArrayList<String>();
    private List<Integer> r_list=new ArrayList<Integer>();
    private EditText ed_qus;
    private RatingBar ratingBar;
    private TextView tx_back,tx_commit,tx_org,tx_user;
    private List<String>  list;
    private  TextView tx_date;
    private Button bnt_camera;
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private Intent intent;

    private InGridView gridView,gridview_photo;
    private int columnWidth;
    private ImageAdapter gridAdapter;
    private String TAG =AddProblemActivity.class.getSimpleName();
    private RelativeLayout lay_s01,lay_s02;
    private Map<String,String> UserMap=new HashMap<String,String>();//组员
    private Map<String,String> OrgMap=new HashMap<String,String>();//部门
    private String mlinkedTableId="";
    // 上传单线程池
    private ExecutorService pool;
    private RelativeLayout lay_back;
    private  ImageView title_back;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_problem2;
    }



    @Override
    public void process(Message msg) {

    }
    private ItemInfo itemInfo;
    @Override
    public void initLayout() {
        Intent intent = getIntent();
        if(intent.hasExtra("obj")){
            itemInfo = (ItemInfo) intent.getSerializableExtra("obj");
        }

        initView();
//        initData();
    }

    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "GetProblemKeyword");
        params.put("TenantId", SharedPreferencesTool.getMStool(AddProblemActivity.this).getTenantId());
        params.put("ClassId", getIntent().getStringExtra("ClassId"));

        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "PatrolInspection");
            myData.put("ApiCode", "GetProblemKeyword");
            myData.put("TenantId", SharedPreferencesTool.getMStool(AddProblemActivity.this).getTenantId());
            myData.put("ClassId", getIntent().getStringExtra("ClassId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("**********问题标准*********",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(AddProblemActivity.this).getIp()+UrlUtil.Url,
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
                    GridAdapter adapter=new GridAdapter(AddProblemActivity.this,str_list,r_list,ed_qus,ratingBar);
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
        lay_back= (RelativeLayout) findViewById(R.id.lay_back);
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
        gridView=findViewById(R.id.gridview_add);


        gridview_photo=findViewById(R.id.gridview_photo);

        gridview_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }

        });
        gridAdapter = new ImageAdapter(AddProblemActivity.this,imagePaths);
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
        //  setDrawable(bnt_camera, OneDrawable.createBgDrawable(AddProblemActivity.this, R.mipmap.patrol_camera_icon));
        lay_s01=(RelativeLayout) findViewById(R.id.lay_s01);
        lay_s01.setOnClickListener(this);
        lay_s02=(RelativeLayout) findViewById(R.id.lay_s02);
        lay_s02.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (null != data) {
            if(data.hasExtra("userName")){
                String userName = data.getStringExtra("userName");
                String userId = data.getStringExtra("userId");
                String OrganizeName = data.getStringExtra("OrganizeName");
                String OrganizeId = data.getStringExtra("OrganizeId");
                //获取到客户的姓名和紧急催单的供应商
                if (requestCode == UrlUtil.AddProblemActivity_RequstCode01) {
                    if (resultCode == UrlUtil.OrganizeActivity_ResultCode) {
                        if (userName != null) {
                            OrgMap.put(userName, userId);
                            tx_org.setText(userName);
                        }
                    }
                }else  if (requestCode == UrlUtil.AddProblemActivity_RequstCode02) {
                    if (resultCode == UrlUtil.UserActivity_ResultCode) {
                        if (userName != null) {
                            OrgMap.put(OrganizeName, OrganizeId);
                            tx_org.setText(OrganizeName);
                            UserMap.put(userName, userId);
                            tx_user.setText(userName);
                        }
                    }
                }
            }else  if(requestCode==SELECT_IMAGE){
                P.c("图片~");
                // 图片、视频、音频选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                ArrayList list = new ArrayList();
                for (LocalMedia bean: selectList) {
                    String compressPath = bean.getCompressPath();
//                          ImageLoader.loadListeren(this,compressPath,ivMyInfoDetailHead);
                    if(compressPath!=null){
                        P.c(bean.getPictureType()+"地址"+compressPath);
                        list.add(compressPath);
                    }
//

                }
                imagePaths.clear();
                imagePaths.addAll(list);
                gridAdapter.notifyDataSetChanged();
            }



        }

    }

    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        imagePaths.addAll(paths);
        gridAdapter  = new ImageAdapter(AddProblemActivity.this,imagePaths);
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
    String linkId ;
    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.tx_back:
                finish();
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.bnt_calendar:
                setDate();
                break;
            case R.id.bnt_camera:
                //camera();
                selectImages();
                break;
            case R.id.tx_commit:
                if(ed_qus.getText().toString().equals("")||tx_org.getText().toString().equals("")||tx_user.getText().toString().equals("")){
                    Toast.makeText(AddProblemActivity.this,"参数信息不完整",Toast.LENGTH_SHORT).show();
                }else{
                    showDialog("数据提交中");
                    linkId = UUID.randomUUID().toString();
                    if(imagePaths.size()>0){
                        for(int i=0;i<imagePaths.size();i++){
                            final File file = new File(imagePaths.get(i));
                            getUrl(file,linkId);
                        }
                    }else{


                        commit();
                    }

                }

                break;
            case R.id.lay_s01:
               /* intent=new Intent();
                intent.setClass(AddProblemActivity.this, OrganizeActivity.class);
                intent.putExtra("Title","部门选择");
                startActivityForResult(intent,UrlUtil.AddProblemActivity_RequstCode01);*/
                break;
            case R.id.lay_s02:
                intent=new Intent();
                intent.setClass(AddProblemActivity.this, UserActivity.class);
                intent.putExtra("Title","人员选择");
                startActivityForResult(intent,UrlUtil.AddProblemActivity_RequstCode02);
                /*if(!tx_org.getText().toString().equals("")){
                    intent.putExtra("OrganizeId",OrgMap.get(tx_org.getText().toString()));
                    startActivityForResult(intent,UrlUtil.AddProblemActivity_RequstCode02);
                }else{
                    Toast.makeText(AddProblemActivity.this,"请先选择部门",Toast.LENGTH_SHORT).show();
                }
*/
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
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    selectImages();
//                    camera();
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
    private final int SELECT_IMAGE = 11;
    private void selectImages(){
        PictureSelector.create(AddProblemActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_QQ_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(5)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int

                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
//                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
//                .compressSavePath(Environment.getExternalStorageState()+"/Images")//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
//                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality(0)// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int

                .forResult(SELECT_IMAGE);//结果回调onActivityResult code
    }




    private void camera() {
        if(checkWriteExternalPermission()){
            PhotoPicker.init().setMaxCount(5).setShowCamera(true).setUseSystemCamera(false).startPick(AddProblemActivity.this, new OnPhotoPickListener() {
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
                            ActivityCompat.requestPermissions(AddProblemActivity.this,new String []{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},1);

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

        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "EditPatrolRecord");
        params.put("TenantId", SharedPreferencesTool.getMStool(AddProblemActivity.this).getTenantId());
        if(itemInfo!=null){
            params.put("PatrolRecordId", itemInfo.getPatrolRecord());
            params.put("PatrolTaskId",itemInfo.getPatrolTaskId());
        }else{
            params.put("PatrolRecordId","");
            params.put("PatrolTaskId", getIntent().getStringExtra("id"));
        }
        params.put("Result","NG");
        params.put("ProblemID",linkId);

        if(uploadPaths.size()!=0){
          StringBuffer buffer  =new StringBuffer();
         for(int i=0;i<uploadPaths.size();i++){
             if(i!=uploadPaths.size()-1){
                 buffer.append(uploadPaths.get(i)+",");
             }else{
                 buffer.append(uploadPaths.get(i));
             }
         }
            params.put("ImagePathID",buffer.toString());
        }
        params.put("ProblemDesc", ed_qus.getText().toString());//问题描述
        params.put("LiableUserId",UserMap.get(tx_user.getText().toString()));
        params.put("LiableDeptId",OrgMap.get(tx_org.getText().toString()));
        params.put("DueDate", tx_date.getText().toString());

        httpPostSONVolley(SharedPreferencesTool.getMStool(AddProblemActivity.this).getIp()+UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonobj) {
                dismissDialog();
                try {
                    String status=jsonobj.getString("status");
                    final String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        Toasty.INSTANCE.showToast(AddProblemActivity.this,"提交问题成功");


                        Intent intent = new Intent();
                        intent.putExtra("ind",getIntent().getIntExtra("ind",0));
                        setResult(100,intent);
                        AppManager.getAppManager().finishActivity();
                        /*if(imagePaths.size()>0){
                            for(int i=0;i<imagePaths.size();i++){
                                final File file = new File(imagePaths.get(i));
                                getUrl(file,"");
                            }
                        }else{
                            setResult(UrlUtil.AddProblemActivity_ResultCode,intent);
                            finish();
                        }*/
                    }else{
                        Toast.makeText(AddProblemActivity.this,message,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void error(VolleyError error) {

            }
        });
    }
    private void getUrl(final File file, final String linkedTableId){
        uploadPaths.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.GET, SharedPreferencesTool.getMStool(AddProblemActivity.this).getIp()+UrlUtil.SeveLinePatrolImageUrl,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
             P.c(jsonobj.toString());
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String AppUrl=jData.getString("AppUrl");
                        final String url=UrlUtil.UploadInspectionImageUrl(AppUrl,"PatrolInspection","EditUploadFiles",SharedPreferencesTool.getMStool(AddProblemActivity.this).getTenantId(),linkedTableId,UrlUtil.patrolinspection01,"patrolinspection_problem01");
                        // final String url=UrlUtil.UploadImageUrl(AppUrl,"LinePatrol","EditUploadFiles","00000000-0000-0000-0000-000000000001",linkedTableId,UrlUtil.LinkedTable01,"inepatrol_record_question");
                       P.c(url);
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
                setResult(UrlUtil.AddProblemActivity_ResultCode,intent);
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
            P.c("status"+status);

            if(status.equals("true")){
                JSONObject object = jsonobj.getJSONObject("message");
                uploadPaths.add(object.getString("FileID"));
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
        CalendarUtil.getInstance().setDate(AddProblemActivity.this,tx_date);
    }
    private volatile  ArrayList<String> uploadPaths  = new ArrayList<>();
    Handler myHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UrlUtil.SUCCESS:
                 /*   Toast.makeText(AddProblemActivity.this,"上传图片成功",Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    setResult(UrlUtil.AddProblemActivity_ResultCode,intent);
                    finish();*/

                      if(uploadPaths.size()==imagePaths.size()){
                          commit();
                      }
                    break;
                case UrlUtil.ERRORR:
                    Toast.makeText(AddProblemActivity.this,"上传图片失败",Toast.LENGTH_SHORT).show();
                    setResult(UrlUtil.AddProblemActivity_ResultCode,intent);
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };


}
