package com.shuben.zhixing.www.inspection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.view.Toasty;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.adapter.ItemAdapter;
import com.shuben.zhixing.www.inspection.bean.ItemInfo;
import com.shuben.zhixing.www.inspection.bean.ParamInfo;
import com.shuben.zhixing.www.listener.InspectionItemOnScrollListener;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.SizeHelper;
import com.shuben.zhixing.www.util.SysUtils;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.UploadUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InspectionItemActivity extends BaseActvity implements View.OnClickListener, InspectionItemOnScrollListener.OnloadDataListener{
    private HorizontalScrollView hs_activity_tabbar;
    private RadioGroup myRadioGroup;
    private List<String> titleList;
    private LinearLayout ll_activity_tabbar_content,lay_back;

    private TextView tx_close,tx_back;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private String  type="人";
    private String  PatrolTaskId="";
    private List<ParamInfo> plist=null;
    private String channel;
    private LoadingDailog dialog=null;//加载动画
    private ListView standar_listview;
    //底部加载更多布局
    View footer;
    private ItemAdapter adapter;
    private ListView mList;
    private  boolean isAdd=false;
    private List<ItemInfo> data;
    private Intent intent;
    private ImageView title_back;


    @Override
    public int getLayoutId() {
        return R.layout.activity_inspection_item;
    }



    @Override
    public void process(Message msg) {
        switch (msg.what){
            case 2:
                int ind = msg.arg1;
                Intent intent=new Intent();
                intent.setClass(InspectionItemActivity.this, AddProblemActivity.class);
                intent.putExtra("obj",data.get(ind));
                intent.putExtra("ind",ind);
                startActivityForResult(intent,102);
                break;
            case 3:
                //OK
                int ind1 = msg.arg1;
                linkId = UUID.randomUUID().toString();
                if(data.get(ind1).getPath()!=null){
                    uploadImage(new File(data.get(ind1).getPath()),linkId,ind1);
                }else{
                    sendC(ind1,"");

                }


                break;
            case 4:
                //
                int ind2 = msg.arg1;
                OK_INDEX = ind2;
                selectImages();
                break;
            case 5:
                        int ind3 = msg.arg1;
                    String path = (String) msg.obj;
                commit(ind3,path);
                break;
        }
    }

    private void commit(int ind,String path) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "EditPatrolRecord");
        params.put("TenantId", SharedPreferencesTool.getMStool(InspectionItemActivity.this).getTenantId());
        params.put("PatrolRecordId", data.get(ind).getPatrolRecord());
        params.put("Result","OK");
        params.put("PatrolTaskId",data.get(ind).getPatrolTaskId());
        params.put("ProblemID",linkId);

        params.put("ImagePathID",path);
        params.put("ProblemDesc", "");//问题描述
        params.put("LiableUserId","");
        params.put("LiableDeptId","");
        params.put("DueDate", "");
        String temp = data.get(ind).getTx03V();
        if(temp==null||temp.length()==0){
            temp = data.get(ind).getTx04V();
        }
        params.put("EditPatrolRecord",temp);
        httpPostSONVolley(SharedPreferencesTool.getMStool(InspectionItemActivity.this).getIp()+UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonobj) {
                try {
                    String status=jsonobj.getString("status");
                    final String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        data.remove(ind);
                        adapter.updata(data);
                        Toasty.INSTANCE.showToast(InspectionItemActivity.this,"数据已提交");
                    }else{
                        Toast.makeText(InspectionItemActivity.this,message,Toast.LENGTH_SHORT).show();
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

    private String linkId;
    private int OK_INDEX  = 0;
    private final int SELECT_IMAGE = 11;
    private void selectImages(){
        PictureSelector.create(InspectionItemActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_QQ_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dat) {
        super.onActivityResult(requestCode, resultCode, dat);
        if(requestCode==102&&resultCode==100){
            //问题提交
            if(dat.hasExtra("ind")){
                data.remove(dat.getIntExtra("ind",0));
                adapter.updata(data);
            }
        }else if(requestCode==SELECT_IMAGE){
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(dat);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

            for (LocalMedia bean: selectList) {
                String compressPath = bean.getCompressPath();
//                          ImageLoader.loadListeren(this,compressPath,ivMyInfoDetailHead);
                if(compressPath!=null){
                    P.c(bean.getPictureType()+"地址"+compressPath);
                  data.get(OK_INDEX).setPath(compressPath);
                  adapter.updata(data);
                }
//

            }
        }
    }

    String clsId,scopeID,scopeName;

    @Override
    public void initLayout() {
        Intent intent = getIntent();
        clsId = intent.getStringExtra("ClassId");
        scopeID = intent.getStringExtra("scopeID");
        scopeName = intent.getStringExtra("scopeName");
        initView();
        initData(PatrolTaskId,type);
    }


    public void uploadImage(final File file, final String linkedTableId,int ind1){


            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            final Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            JsonObjectRequest newMissRequest = new JsonObjectRequest(
                    Request.Method.GET, SharedPreferencesTool.getMStool(InspectionItemActivity.this).getIp()+UrlUtil.SeveLinePatrolImageUrl,
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
                            final String url=UrlUtil.UploadInspectionImageUrl(AppUrl,"PatrolInspection","EditUploadFiles",SharedPreferencesTool.getMStool(InspectionItemActivity.this).getTenantId(),linkedTableId,UrlUtil.patrolinspection01,"patrolinspection_problem01");
                            // final String url=UrlUtil.UploadImageUrl(AppUrl,"LinePatrol","EditUploadFiles","00000000-0000-0000-0000-000000000001",linkedTableId,UrlUtil.LinkedTable01,"inepatrol_record_question");
                            P.c(url);
                            /**
                             * 下载线程
                             */
                            Runnable downloadRun = new Runnable(){
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    upLoadImage(file,url,ind1);
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

    private void upLoadImage(final File file, final String url,int ind1) {
        String request= UploadUtil.uploadFile(file,url);
        JSONObject jsonobj= null;
        try {
            jsonobj = new JSONObject(request);
            String status=jsonobj.getString("status");
            P.c("status"+status);

            if(status.equals("true")){
                JSONObject object = jsonobj.getJSONObject("message");
               sendC(ind1,object.getString("FileID"));


            }else{

                final String message=jsonobj.getString("message");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void sendC(int ind1,String obj){
        Message msg  = new Message();
        msg.what = 5;
        msg.arg1 = ind1;
        msg.obj = obj;
        getHandler().sendMessage(msg);
    }
    private void initData(final String PatrolTaskId, final String ItemType) {
       // RequestQueue requestQueue = Volley.newRequestQueue(InspectionItemActivity.this);
       /* Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "GetAllCheckItemByTaskId");
        params.put("TenantId", SharedPreferencesTool.getMStool(InspectionItemActivity.this).getTenantId());
        params.put("PatrolTaskId", PatrolTaskId);*/

        Map<String,String> params = new HashMap<>();
        params.put("AppCode","PatrolInspection");
        params.put("ApiCode","EditHandleGenerateTask");
        params.put("TenantId",SharedPreferencesTool.getMStool(this).getTenantId());
        params.put("ClassId",clsId);
        params.put("scopeID",scopeID);
        params.put("scopeName",scopeName);
        params.put("UserName",SharedPreferencesTool.getMStool(this).getUserName());
        params.put("UserId",SharedPreferencesTool.getMStool(this).getUserId());

//        params.put("ItemType", ItemType);


        httpPostSONVolley(SharedPreferencesTool.getMStool(InspectionItemActivity.this).getIp()+UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonobj) {

                Log.e("KKKK巡检项KKKK", " " + jsonobj.toString());
                    if(dialog!=null)
                dialog.dismiss();
                try {
                    int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    data=new ArrayList<ItemInfo>();
                    JSONObject jData;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String PatrolRecord=jData.getString("PatrolRecordId");//巡检记录ID
                        String PatrolTaskId=jData.getString("PatrolTaskId");//巡检任务ID
                        String ItemId=jData.getString("ItemId");//巡检项ID
                        if(ItemId.equals("null")){
                            continue;
                        }
                        String ItemName=jData.getString("ItemName");//巡线项描述
                       // String ItemType=jData.getString("ItemType");//类型，人、机、料、法、环
                        String PatrolFashion=jData.getString("PatrolFashion");//巡检方式，取值有;//数量、拍照、说明
                        String Result=jData.getString("Result");//取值有OK/NG/空值
                        String CreateTime=jData.getString("PlanPatrolTime").replaceAll("T"," ");
                        /*String ItemSource=jData.getString("ItemSource");//取值有OK/NG/空值
                        JSONObject PatrolResult=jData.getJSONObject("PatrolResult");
                        JSONArray ss=PatrolResult.getJSONArray("rows");
                        JSONObject jsonObject;
                        if(plist==null){
                            plist=new ArrayList<ParamInfo>();
                        }else{
                            plist.clear();
                        }

                        try {
                            for(int j=0;j<ss.length();j++){
                                jsonObject=ss.getJSONObject(j);
                                String PatrolResultId=jsonObject.getString("PatrolResultId");
                                String PatrolRecordId=jsonObject.getString("PatrolRecordId");
                                String PatrolTaskId02=jsonObject.getString("PatrolTaskId");
                                String ItemId02=jsonObject.getString("ItemId");
                                String MappingId=jsonObject.getString("MappingId");
                                String PatrolValue=jsonObject.getString("PatrolValue");
                                String ItemSource02=jsonObject.getString("ItemSource");
                                String CategoryCode=jsonObject.getString("CategoryCode");
                                String CategoryName=jsonObject.getString("CategoryName");
                                String StandText=jsonObject.getString("StandText");
                                String StandValue=jsonObject.getString("StandValue");
                                String StandMaxValue=jsonObject.getString("StandMaxValue");
                                String StandMinValue=jsonObject.getString("StandMinValue");
                                String ParamName=jsonObject.getString("ParamName");
                                String ParamUnit=jsonObject.getString("ParamUnit");
                                ParamInfo  paramInfo=new ParamInfo(PatrolResultId,PatrolRecordId,PatrolTaskId02,ItemId02,MappingId,PatrolValue,ItemSource02,CategoryCode,CategoryName,StandText,StandValue,StandMaxValue,StandMinValue,ParamName,ParamUnit);
                                plist.add(paramInfo);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();




                        }*/
//                        去掉了ItemSource
                        ItemInfo info=new ItemInfo(PatrolRecord,PatrolTaskId,ItemId,ItemName,ItemType,PatrolFashion,Result,CreateTime,"",plist);
                        data.add(info);
                    }
                    adapter=null;
                    showListView(data);
                    //自定义的滚动监听事件
                    InspectionItemOnScrollListener onScrollListener = new InspectionItemOnScrollListener(footer,InspectionItemActivity.this,PatrolTaskId,ItemType,total,plist);
                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(InspectionItemActivity.this);
                    //设置ListView的滚动监听事件
                  //  mList.setOnScrollListener(onScrollListener);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                if(dialog!=null)
                    dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void initView() {
        PatrolTaskId=getIntent().getStringExtra("PatrolTaskId");
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(InspectionItemActivity.this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        initGroup();
        tx_back= (TextView) findViewById(R.id.tx_back);
        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        title_back= (ImageView) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        tx_close= (TextView) findViewById(R.id.tx_close);
        tx_close.setOnClickListener(this);
        standar_listview= (ListView) findViewById(R.id.standar_listview);
       /* if(getIntent().getStringExtra("Status").equals("已完成")){
            tx_close.setVisibility(View.INVISIBLE);
        }*/




    }
    private void initGroup() {
        titleList = new ArrayList<String>();
        titleList.add("人");
        titleList.add("机");
        titleList.add("料");
        titleList.add("法");
        titleList.add("环");
        hs_activity_tabbar= (HorizontalScrollView) this.findViewById(R.id.hs_activity_tabbar);
        ll_activity_tabbar_content= (LinearLayout) this.findViewById(R.id.ll_activity_tabbar_content);
        //选项卡布局
        myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        ll_activity_tabbar_content.addView(myRadioGroup);
        for (int i = 0; i < titleList.size(); i++) {
            String channel = titleList.get(i);
            RadioButton radio = new RadioButton(this);
            radio.setButtonDrawable(android.R.color.transparent);
            radio.setBackgroundResource(R.drawable.radiobtn_selector);
            ColorStateList csl = getResources().getColorStateList(R.color.radiobtn_text_color);
            radio.setTextColor(csl);
            WindowManager manager = this.getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            int w = SysUtils.getScreenWidth(InspectionItemActivity.this)/5;
            // LinearLayout.LayoutParams l = new LinearLayout.LayoutParams((int) SizeHelper.dp2px(this, w/3), ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setTextSize(15);
            radio.setGravity(Gravity.CENTER);
            radio.setText(channel);
            radio.setTag(channel);
            myRadioGroup.addView(radio);

        }


        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(radioButtonId);
                channel = (String) rb.getTag();
                mCurrentCheckedRadioLeft = rb.getLeft();//更新当前按钮距离左边的距离
                int width=(int) SizeHelper.dp2px(InspectionItemActivity.this, 150);
                hs_activity_tabbar.smoothScrollTo((int) mCurrentCheckedRadioLeft - width, 0);
                if(channel.equals("人")){
                    //加载未处理数据
                    if(!type.equals("人")){
                        type="人";
                        initData(PatrolTaskId,type);
                    }

                }else if(channel.equals("机")){
                    //加载待审核数据
                    type="机";
                    initData(PatrolTaskId,type);

                } else if(channel.equals("料")){
                    //加载待审核数据
                    type="料";
                    initData(PatrolTaskId,type);

                }else if(channel.equals("法")){
                    //加载待审核数据
                    type="法";
                    initData(PatrolTaskId,type);

                }else if(channel.equals("环")){
                    //加载待审核数据
                    type="环";
                    initData(PatrolTaskId,type);

                }


            }
        });
        //设定默认被选中的选项卡为第一项
        if (!titleList.isEmpty()) {
            myRadioGroup.check(myRadioGroup.getChildAt(0).getId());
        }

    }

    /**
     * 将数据加载到ListView上
     *
     * @param data
     */
    private void showListView(List<ItemInfo> data) {
        //首先判断适配器是否为空，首次运行肯定是为空的
        if (adapter == null) {
            //查到ListView控件
            mList = (ListView) findViewById(R.id.standar_listview);
            //将底部加载一个加载更多的布局
            footer = LayoutInflater.from(this).inflate(R.layout.foot_boot, null);
            //初始状态为隐藏
            footer.setVisibility(View.GONE);
            //加入到ListView的底部
            if(!isAdd){
                isAdd=true;
                mList.addFooterView(footer);
            }

            //创建adpter数据
            adapter = new ItemAdapter(InspectionItemActivity.this,data,"",getHandler());
            //设置adapter
            mList.setAdapter(adapter);
        } else {
            //不为空，则刷新数据
            this.data.addAll(data);
            //提醒ListView重新更新数据
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onLoadData(List<ItemInfo> data) {
        showListView(data);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                 intent=new Intent();
                setResult(UrlUtil.InspectionItemActivity_ResultCode,intent);
                finish();
                break;
            case R.id.tx_back:
                 intent=new Intent();
                setResult(UrlUtil.InspectionItemActivity_ResultCode,intent);
                finish();
                break;
            case R.id.title_back:
                 intent=new Intent();
                setResult(UrlUtil.InspectionItemActivity_ResultCode,intent);
                finish();
                break;

            case R.id.tx_close:
                showPopWindow();
                break;


            
            default:
            
            break;
        
        
        
        }
    }

    private void close() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "EditPatrolInspectionEnd");
        params.put("TenantId", SharedPreferencesTool.getMStool(InspectionItemActivity.this).getTenantId());
        params.put("PatrolTaskId", getIntent().getStringExtra("PatrolTaskId"));


        JSONObject data =new JSONObject();

        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditPatrolLineEnd");
            data.put("TenantId", SharedPreferencesTool.getMStool(InspectionItemActivity.this).getTenantId());
            data.put("PatrolTaskId", getIntent().getStringExtra("PatrolTaskId"));

            Log.e("***************",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(InspectionItemActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        Toast.makeText(InspectionItemActivity.this,"结束巡检",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(InspectionItemActivity.this,message,Toast.LENGTH_SHORT).show();
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
        requestQueue.add(newMissRequest);
    }

    private void showPopWindow()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(InspectionItemActivity.this);
        View view = (LinearLayout) InspectionItemActivity.this.getLayoutInflater().inflate(R.layout.task, null);
        TextView tx_content=(TextView) view.findViewById(R.id.tx_content);
        tx_content.setText("是否关闭当前巡检任务");
        builder.setView(view);
        builder.setTitle("提醒");

        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                close();
                dialog.cancel();

            }
        });
        builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();

    }


    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 63:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        };
    };
}
