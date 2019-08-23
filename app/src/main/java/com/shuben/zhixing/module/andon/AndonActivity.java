package com.shuben.zhixing.module.andon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.Tips;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CommonTips;
import com.google.gson.JsonObject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.shuben.zhixing.module.jsf.JavaScriptAndon;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.util.MultiPartStringRequest;
import com.zc.http.okhttp.OkHttpUtils;
import com.zc.http.okhttp.callback.StringCallback;
import com.zhixing.masslib._SomeCheckActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

public class AndonActivity extends BaseActvity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            switch (requestCode) {
                case SELECT_IMAGE:
                    //归0，重新计数
                    UPLOAD_COUNT = 0;
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia bean: selectList) {
                        String compressPath = bean.getCompressPath();
//                          ImageLoader.loadListeren(this,compressPath,ivMyInfoDetailHead);
                        if(compressPath!=null){
                            ImageBean b = new ImageBean();
                            b.setPath(compressPath);
                            b.setType(0);
                            imageBeans.add(b);
                            if(detailImageDlg!=null){
                                detailImageDlg.updata(imageBeans);
                                Map<String,String> pa = new HashMap<>();
                                pa.put("LinkedTableId",LinkedTableId);
                                pa.put("LinkedTable",LinkedTable);
                                pa.put("TenantId",TenantId);
                                pa.put("FileDesc","1");
                                FileUtils.parms(pa);
                                Map<String,File> file = new HashMap<>();
                                file.put("file",new File(compressPath));
                                String url = SharedPreferencesTool.getMStool(AndonActivity.this).getIp();
                                if(url.contains(":")&&url.split(":").length==3){
                                    //安灯专用端口
                                    String sp[] = url.split(":");
                                    url = sp[0]+":"+sp[1]+":8002/";
                                }else{
                                    url = url+":8002/";
                                }
                                addPutUploadFileRequest(url,pa,new File(compressPath),selectList.size());
                            }
                        }
//                         P.c(compressPath);

                    }
                    break;
            }
        }

    }
    private volatile  int UPLOAD_COUNT = 0;
    String andon_up = "/api/Andon/Files/EditUploadFiles";
    public   void addPutUploadFileRequest(String url,Map<String,String> map,File file,int size){
        P.c(url+andon_up);
        OkHttpUtils.post().params(map).addFile("file","1.png",file).url(url+andon_up).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                P.c("上传结果"+response);
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getString("status").equals("success")){

                        UPLOAD_COUNT++;
                        if(UPLOAD_COUNT==size){
                            Toasty.INSTANCE.showToast(AndonActivity.this,"上传成功!");
                        }
                        getImages();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    
    private final int SELECT_IMAGE = 11;
    private void selectImages(){
        PictureSelector.create(AndonActivity.this)
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
    private void getImages(){
        Map<String,String> params = new HashMap<>();
        //AppCode":"Andon","ApiCode":"GetAndonfiles
        params.put("AppCode","Andon");
        params.put("ApiCode","GetAndonfiles");
        params.put("LinkedTableId",LinkedTableId);
        params.put("LinkedTable",LinkedTable);
        params.put("TenantId",TenantId);
        httpPostSONVolley(SharedPreferencesTool.getMStool(AndonActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    if(jsonObject.getBoolean("status")){
                        imageBeans.clear();
                        JSONArray array = jsonObject.getJSONArray("rows");
                        for(int i=0;i<array.length();i++){
                            JSONObject obj = array.getJSONObject(i);
                            ImageBean b = new ImageBean();
                            b.setId(obj.getString("FileID"));
                            b.setPath(obj.getString("FilePath"));
                            b.setType(1);
                            imageBeans.add(b);
                        }

                        if(detailImageDlg!=null){
                            detailImageDlg.updata(imageBeans);
                        }
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
    private void deleteImage(String id,int ind){
        Map<String,String> params = new HashMap<>();
        params.put("AppCode","Andon");
        params.put("ApiCode","EditDeleteFile");
        params.put("FileId",id);
        httpPostSONVolley(SharedPreferencesTool.getMStool(AndonActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult(){

            @Override
            public void success(JSONObject jsonObject) {
                try {
                    if(jsonObject.getBoolean("status")){
                        imageBeans.remove(ind);
                        getImages();
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


    private String LinkedTableId,LinkedTable,TenantId;
    @Override
    public void process(Message msg) {
        switch (msg.what){
            case 2:

                int ind = msg.arg1-1;
                //删除图片
                CommonTips tips = new CommonTips(AndonActivity.this,null);
                tips.init("否","删除","是否删除?");
                tips.setI(new Tips() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void sure() {

                     try {
                         if(imageBeans.get(ind).getType()==0){
                             imageBeans.remove(ind);
                             detailImageDlg.updata(imageBeans);
                         }else{
                             deleteImage(imageBeans.get(ind).getId(),  ind);

                         }
                     }catch (IndexOutOfBoundsException e){
                         
                     }
                    }
                });
                tips.showSheet();
                break;
            case 3:
                //选择图片
                selectImages();
                break;
            case 4:
                String json  = (String) msg.obj;
                int pop  = msg.arg1;
                JSONObject object = null;
                try {
                    object = new JSONObject(json);
                    LinkedTableId=  object.getString("LinkedTableId");
                    TenantId = object.getString("TenantId");
                    LinkedTable = object.getString("LinkedTable");
                    P.c("pop值"+pop);
                    detailImageDlg = new DetailImageDlg(AndonActivity.this,getHandler(),imageBeans,"案例库图片上传",pop);
                    detailImageDlg.showSheet();
                    getImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void backActivity(View v) {

        super.backActivity(v);

    }

    private ProgressBar process_bar;
    private WebView commonView;
    private JavaScriptAndon scriptObject;

    public void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            commonView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            commonView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            commonView.getSettings().setLoadsImagesAutomatically(false);
        }
    }
    ArrayList<ImageBean> imageBeans = new ArrayList<>();
    DetailImageDlg detailImageDlg;
    private boolean INIT = false;
    @SuppressLint("WrongViewCast")
    @Override
    public void initLayout() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        process_bar = findViewById(R.id.process_bar);
        commonView = findViewById(R.id.common_web);
      //  init();
        commonView.getSettings().setBuiltInZoomControls(false);
        commonView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        WebSettings webSettings = commonView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSettings.setDatabasePath(this.getDir("databases", 0).getPath());
        webSettings.setBuiltInZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        scriptObject = new JavaScriptAndon(AndonActivity.this,getHandler(),commonView);
        commonView.addJavascriptInterface(scriptObject, "jsDo");//本地快速播货


        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                commonView.loadUrl(url);

               // commonView.loadUrl("javascript:callJS()");
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (!commonView.getSettings().getLoadsImagesAutomatically()) {
                    commonView.getSettings().setLoadsImagesAutomatically(true);
                }
//
                process_bar.setVisibility(View.GONE);
              /*  if(!INIT){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        commonView.evaluateJavascript(getIntent().getStringExtra("js"), new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {

                                INIT = true;
                            }
                        });
                    }else{
                        commonView.loadUrl(getIntent().getStringExtra("js"));
                    }
                }*/
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                process_bar.setVisibility(View.VISIBLE);
            }
        };
        WebChromeClient webChromeClient = new WebChromeClient() {
            public void onReceivedTitle(WebView view, String tl) {
//



            }

            @Override
            public void onProgressChanged(WebView webView, int newProgress) {

                if (newProgress == 100) {
                    process_bar.setVisibility(View.GONE);
                } else {
                    if (process_bar.getVisibility() == View.GONE)
                        process_bar.setVisibility(View.GONE);
                    process_bar.setProgress(newProgress);
                }
                super.onProgressChanged(webView, newProgress);

            }

            ;
        };
        commonView.setWebChromeClient(new PaxWebChromeClient(this,process_bar,null));
        commonView.setWebViewClient(webViewClient);


        if (getIntent().hasExtra("url")) {
            //getIntent().getStringExtra("url")
            P.c("存在"+getIntent().getStringExtra("url"));
            commonView.loadUrl(getIntent().getStringExtra("url"));

        } else if (getIntent().hasExtra("content")) {

        } else if (getIntent().hasExtra("file")) {
            commonView.loadUrl("file://"+getIntent().getStringExtra("file"));
        }

    
    }

    private String params(String params) throws UnsupportedEncodingException {
       return params;
    }

    @Override
    public int getLayoutId() {
        return R.layout.andon_web;
    }
    private String INIT_VALUE ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().hasExtra("init_value")){
            P.c("正常接收"+getIntent().getStringExtra("init_value"));
            INIT_VALUE = getIntent().getStringExtra("init_value");
            commonView.setTag(INIT_VALUE);
        }
        sendInfo();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if(getIntent().hasExtra("init_value")){
            P.c("通知接收"+getIntent().getStringExtra("init_value"));
            scriptObject.loadJs(getIntent().getStringExtra("init_value"));
        }
    }
    /*
    {"AppCode":"Andon","ApiCode":"GetSaveAppIp","TenantId":"ed37a619-d6ba-4e2f-9486-c28f3f2c2978","IP":"15936424949","UserId":"44bc1450-d4ee-2022-36b3-df255a1aed53","UserCode":"00000","UserName":"15936424949","UserHostAddress":"1234","Port":"1234"}
     */
    private void sendInfo(){
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", "Andon");
        params.put("ApiCode", "GetSaveAppIp");
        params.put("UserName",SharedPreferencesTool.getMStool(AndonActivity.this).getUserName());
        params.put("UserId", SharedPreferencesTool.getMStool(AndonActivity.this).getUserId());
        params.put("UserCode", SharedPreferencesTool.getMStool(AndonActivity.this).getUserCode());
        params.put("TenantId", SharedPreferencesTool.getMStool(AndonActivity.this).getTenantId());

        try {
            params.put("UserHostAddress",getLocalHostLANAddress().getHostAddress());
            params.put("IP",getLocalHostLANAddress().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            params.put("IP","");
        }
        params.put("Port","");
        httpPostVolley(SharedPreferencesTool.getMStool(AndonActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {


            }

            @Override
            public void error(VolleyError error) {

            }
        },"");
    }
    private   InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

}
