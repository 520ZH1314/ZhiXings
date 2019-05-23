package com.shuben.zhixing.www.activity;
import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.VolleyError;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.XEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.sdk.chat.server.SdkConfig;
import com.shuben.common.IPush;
import com.shuben.zhixing.provider.PermissionBean;
import com.shuben.zhixing.www.BaseApplication;
import com.shuben.zhixing.www.NavigationActivity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.dataBase.DB_L;
import com.shuben.zhixing.www.service.TraceServiceImpl;
import com.shuben.zhixing.www.util.CustomToast;
import com.shuben.zhixing.www.util.PackageUtils;
import com.shuben.zhixing.www.util.UpdateManager;
import com.xdandroid.hellodaemon.DaemonEnv;
import com.xdandroid.hellodaemon.IntentWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2017/8/22.
 * 登录界面
 */

public class LoginActivity extends BaseActvity implements View.OnClickListener{
    private TextView login_login,login_forgetpwd,tx_Ver;
    private String  PHONE,pwd,IP;
    private XEditText login_pwd_et,http_pwd_et;
    private XEditText login_phone_et;
    private CustomToast customToast;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> list=new ArrayList<String>();
    private DB_L db;
    private LoadingDailog dialog;//加载动画

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_loading;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        
      /*  File file = new File("/storage/emulated/0/UpApkPath/shuben_updata.apk");
        AppInstall.openFile(LoginActivity.this, file);*/
        P.c("登录凌乱了");
        blind();
        startKeep();
        if(SharedPreferencesTool.getMStool(LoginActivity.this).getUserId().length()!=0){
            Intent intent=new Intent();
            intent.setClass(LoginActivity.this , NavigationActivity.class);
            startActivity(intent);
            AppManager.getAppManager().finishActivity();



        }
    }
    IPush iPush;
    ServiceConnection pushConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iPush = IPush.Stub.asInterface(iBinder);
            P.c("服务端"+componentName);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iPush = null;
            P.c("服务端断开");
        }
    };
    private void blind(){
        Intent intent = new Intent(this,IPush.class);
        bindService(intent,pushConnection,Context.BIND_AUTO_CREATE);

    }

    private boolean isServiceRunning(String ServicePackageName) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ServicePackageName.equals(service.service.getClassName())) {
                P.c("包名"+service.service.getClassName());
                return true;
            }
            P.c("包名333"+BaseApplication.application.getPackageName()+"."+TraceServiceImpl.class.getName());
        }
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();

        http_pwd_et.setTextEx(SharedPreferencesTool.getMStool(LoginActivity.this).getIp());
    }
    private void startKeep(){

      /*  if(!isServiceRunning(TraceServiceImpl.class.getName())){

        }*/
        if(!SharedPreferencesTool.getMStool(LoginActivity.this).getString("isWrap").equals("1")){
            IntentWrapper.whiteListMatters(this, "智行力消息服务的持续运行");
            SharedPreferencesTool.getMStool(LoginActivity.this).setString("isWrap","1");
        }
    }
    @Override
    public void process(Message msg) {
            switch (msg.what){
                case 1:
                    Uri uri = Uri.parse("content://com.zhixing.provider/permission");//这么使用
                    //在插入之前先清空表数据
                    getContentResolver().delete(uri,null,null);
                    for(int i=0;i<permissionBeans.size();i++){
                        PermissionBean pb = permissionBeans.get(i);
                        ContentValues values = new ContentValues();
                        values.put("appCode",pb.getAppCode());
                        values.put("permissionCode",pb.getPermissionCode());
                        values.put("permissionRemark",pb.getPermissionName());
                        values.put("parentCode",pb.getParentCode());
                        values.put("seq",pb.getSeq());
                        getContentResolver().insert(uri,values);
                    }
                    enterMainCon();
                    break;
            }
    }

    @Override
    public void initLayout() {
        customToast = new CustomToast(this);
        ActivityCompat.requestPermissions(LoginActivity.this,new String []{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE},1);
        init();
        EventBus.getDefault().register(this);
        checkUpdate();
    }
    @Subscribe (threadMode =  ThreadMode.MAIN)
    public void even(String i){

    }

    @Subscribe (threadMode =  ThreadMode.MAIN)
    public void even1(String i){

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    private void checkUpdate() {
        UpdateManager updateManager=new UpdateManager(LoginActivity.this);
        String updateUrl=SharedPreferencesTool.getMStool(LoginActivity.this).getIp()+UrlUtil.UpdateUrl;
        updateManager.checkUpdateInfo(updateUrl);
    }



    private List<PermissionBean> permissionBeans;
    private void initData() {

        //  RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "Login");
        params.put("usercode", login_phone_et.getText().toString());
        params.put("password", login_pwd_et.getText().toString());

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("AppCode", "EPS");
            jsonObject.put("ApiCode", "Login");
            jsonObject.put("usercode", login_phone_et.getText().toString());
            jsonObject.put("password", login_pwd_et.getText().toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        P.c("登录地址:"+SharedPreferencesTool.getMStool(LoginActivity.this).getIp() + UrlUtil.Url);
        httpPostVolley(SharedPreferencesTool.getMStool(LoginActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    String status=jsonObject.getString("status");
                    String message=jsonObject.getString("message");
                    if(status.equals("success")){
                        SharedPreferencesTool.getMStool(LoginActivity.this).savePhone(login_phone_et.getText().toString());
                        SharedPreferencesTool.getMStool(LoginActivity.this).savePassword(login_pwd_et.getText().toString());
                        String userCode=jsonObject.getString("UserCode");
                        String userName=jsonObject.getString("UserName");
                        String userId=jsonObject.getString("UserId");
                        String TenantId=jsonObject.getString("TenantId");
                        SharedPreferencesTool.getMStool(LoginActivity.this).saveUserCode(userCode);
                        SharedPreferencesTool.getMStool(LoginActivity.this).saveUserName(userName);
                        SharedPreferencesTool.getMStool(LoginActivity.this).saveUserId(userId);
                        SharedPreferencesTool.getMStool(LoginActivity.this).saveTenantId(TenantId);
                        final String from = userId;
                        if(from.equals("")){
                            Toast.makeText(LoginActivity.this, "必须输入from", Toast.LENGTH_SHORT).show();
                        }
                        String IP = SharedPreferencesTool.getMStool(LoginActivity.this).getString("IP");

                        if(IP.startsWith("http")){
                            //无论是http还是https开头都这么认为
                            IP = IP.split("://")[1];
                        }
                        SdkConfig.setIP(IP);

                        EventBus.getDefault().postSticky("");

                        startServiceKeep();


                            if(jsonObject.has("funcData")){
                                //保证不出错
                                JSONArray jsonArray = jsonObject.getJSONArray("funcData");
                                Gson gson = new Gson();
//                                hourBeans =  gson.fromJson(jsonObject.getJSONArray("rows").toString(),new TypeToken<List<HourBean>>(){}.getType());
                                if(permissionBeans!=null){
                                    permissionBeans.clear();
                                }
                                permissionBeans = gson.fromJson(jsonArray.toString(),new TypeToken<List<PermissionBean>>(){}.getType());
                                getHandler().sendEmptyMessage(1);
                            }else{
                              enterMainCon();
                            }





                    }else{
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        },true);

//        Log.e("**********",jsonObject.toString());
//        JsonObjectRequest newMissRequest = new JsonObjectRequest(
//                Request.Method.POST, SharedPreferencesTool.getMStool(LoginActivity.this).getIp()+UrlUtil.Url,
//                new JSONObject(params), new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                Log.e("KKKKKKKK", " " + jsonObject.toString());
//                dialog.dismiss();
//
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
//
//            }
//        });
//        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );

        // dialog.show();
        //   requestQueue.add(newMissRequest);
    }
    private void enterMainCon(){
        Intent intent=new Intent();
        intent.setClass(LoginActivity.this , NavigationActivity.class);
        startActivity(intent);
        AppManager.getAppManager().finishActivity();
    }

    //启动保活服务
    public void startServiceKeep(){
        DaemonEnv.initialize(this, TraceServiceImpl.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        TraceServiceImpl.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(TraceServiceImpl.class);

    }

    private void init() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();



        login_forgetpwd = (TextView)findViewById(R.id.tv_forget_pwd);//忘记密码
        login_forgetpwd.setOnClickListener(this);


        tx_Ver=(TextView)findViewById(R.id.tv_login_version);
        String v=PackageUtils.getVersionName(LoginActivity.this);
        int n=PackageUtils.getCurrVersion(LoginActivity.this);
        tx_Ver.setText("版本号:"+v+"("+n+")");

        login_login = (TextView)findViewById(R.id.btn_login);//登录

        Drawable pressedDrawable = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.item_grading_btn)
                .radius(25)
                .build();
        Drawable normalDrawable = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.title_bg)
                .radius(25)
                .build();

        DevShapeUtils
                .selectorPressed(pressedDrawable,normalDrawable)
                .selectorTextColor(R.color.white, R.color.white)
                .into(login_login);


        login_phone_et =  findViewById(R.id.et_login_ip2);//手机号码输入框
        login_pwd_et =  findViewById(R.id.et_login_ip3);//密码输入
        http_pwd_et =  findViewById(R.id.et_login_ip);//IP地址
        login_phone_et.setText(SharedPreferencesTool.getMStool(this).getPhone());
        //login_pwd_et.setText(SharedPreferencesTool.getMStool(this).getPassword());
//        http_pwd_et.setText("http://www.m3lean.com:8080/login/doAction");
        setOnClick();//添加监听方法
//        login_phone_et.setOnClickListener(this);
        db = DB_L.getInstance(this);
    }

    private void setOnClick() {
        login_forgetpwd.setOnClickListener(this);
        login_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        PHONE = login_phone_et.getText().toString().trim();
        pwd = login_pwd_et.getText().toString().trim();
        IP = http_pwd_et.getText().toString().trim();
        switch (v.getId()){
            case R.id.tv_forget_pwd:
                //忘记密码
               /* intent = new Intent(LoginActivity.this, ForgetActivity.class);
                intent.putExtra("PHONE",PHONE);
                startActivity(intent);*/



                break;



            case R.id.btn_login:
                //登录跳转
                if (PHONE.isEmpty()||pwd.isEmpty()||IP.isEmpty()){
                    customToast.showToast("输入内容不能为空");
                }else {
                    List<String> getlist0 = db.loadInput();
                    String str = login_phone_et.getText().toString();
                    String add = http_pwd_et.getText().toString();
                    P.c(add+"--"+add.split(":").length);
                    if( add.split(":").length==3&&add.startsWith("http")){
                        int o = add.lastIndexOf(":");

                        //是IP加端口
                        String ip = add.substring(0,o);
                        String port = add.substring(o+1,add.length());
                        P.c(ip+"---"+port);
                        try{
                            Integer.parseInt(port);
                        }catch (Exception e){
                            Toast.makeText(LoginActivity.this,"端口不合法！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SharedPreferencesTool.getMStool(LoginActivity.this).setString("IP",ip);
                        SharedPreferencesTool.getMStool(LoginActivity.this).setString("PORT",port);
                    } else if(add.split(":").length==2) {
                        if (add.startsWith("http")) {
                            SharedPreferencesTool.getMStool(LoginActivity.this).setString("IP", add);
                            SharedPreferencesTool.getMStool(LoginActivity.this).clear("PORT");
                        } else {
                            int o = add.lastIndexOf(":");
                            //是IP加端口
                            String ip = add.substring(0, o);
                            String port = add.substring(o + 1, add.length());
                            try {
                                Integer.parseInt(port);
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, "端口不合法！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            SharedPreferencesTool.getMStool(LoginActivity.this).setString("IP", ip);
                            SharedPreferencesTool.getMStool(LoginActivity.this).setString("PORT", port);
                        }
                    }


                    if(getlist0.contains(str)){
                    }else{
                        Log.e("写入数据",str);
                        db.saveInput(str);
                    }
                    initData();//调用登录接口，进行跳转
                }
                break;



        }
    }
    public boolean isIP(String addr)
    {
        if(addr.length() < 7  || "".equals(addr))
        {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }
    private void ToLogin() {
        //调用登录接口...
        Intent  intent = new Intent(LoginActivity.this, NavigationActivity.class);
        startActivity(intent);
    }




}
