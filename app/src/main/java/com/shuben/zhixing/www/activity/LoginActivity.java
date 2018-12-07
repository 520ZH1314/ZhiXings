package com.shuben.zhixing.www.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.sdk.chat.ChatSdk;
import com.sdk.chat.callback.IConnectListener;
import com.sdk.chat.contact.ErrorCode;
import com.sdk.chat.server.SdkConfig;
import com.shuben.zhixing.push.UrlConfig;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.xdandroid.hellodaemon.IntentWrapper;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.BaseApplication;
import com.shuben.zhixing.www.NavigationActivity;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.www.dataBase.DB_L;
import com.base.zhixing.www.inter.VolleyResult;
import com.shuben.zhixing.push.LoginServer;
import com.shuben.zhixing.www.service.TraceServiceImpl;
import com.shuben.zhixing.www.util.CustomToast;
import com.shuben.zhixing.www.util.PackageUtils;
import com.shuben.zhixing.www.util.UpdateManager;

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
    private ImageView tetle_back, login_user_clear,login_psw_clear;
    private TextView tetle_text,tx_fwq;
    private String  PHONE,pwd,IP;
    private EditText login_pwd_et,http_pwd_et;
    private AutoCompleteTextView login_phone_et;
    private CustomToast customToast;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> list=new ArrayList<String>();
    private DB_L db;
    private LoadingDailog dialog;//加载动画

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  File file = new File("/storage/emulated/0/UpApkPath/shuben_updata.apk");
        AppInstall.openFile(LoginActivity.this, file);*/
       startKeep();
        if(SharedPreferencesTool.getMStool(LoginActivity.this).getUserId().length()!=0){
            Intent intent=new Intent();
            intent.setClass(LoginActivity.this , NavigationActivity.class);
            startActivity(intent);
            AppManager.getAppManager().finishActivity();

        }
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

    }

    @Override
    public void initLayout() {
        customToast = new CustomToast(this);
        ActivityCompat.requestPermissions(LoginActivity.this,new String []{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE},1);
        init();
        checkUpdate();
    }

    private void checkUpdate() {
        UpdateManager updateManager=new UpdateManager(LoginActivity.this);
        String updateUrl=SharedPreferencesTool.getMStool(LoginActivity.this).getIp()+UrlUtil.UpdateUrl;
        updateManager.checkUpdateInfo(updateUrl);
    }

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

                        ChatSdk.close();
                        ChatSdk.init(LoginActivity.this);
                        ChatSdk.setConnectListener(new IConnectListener() {
                            @Override
                            public void onConnectSuccess() {
                                //123是用户的Id
                                P.c("发送"+new LoginServer(from));
                                ChatSdk.INSTANCE.sendDataBuf(new LoginServer(from), null);
                            }

                            @Override
                            public void onConnectError(ErrorCode code) {

                            }
                        });


                        Intent intent=new Intent();
                        intent.setClass(LoginActivity.this , NavigationActivity.class);
                        startActivity(intent);
                        AppManager.getAppManager().finishActivity();
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

    private void init() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();

        tx_fwq= (TextView) findViewById(R.id.tx_fwq);
        tx_fwq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });

        tetle_back = (ImageView)findViewById(R.id.tetle_back);//返回图片已隐藏
        tetle_back.setVisibility(View.GONE);


        login_forgetpwd = (TextView)findViewById(R.id.login_forgetpwd);//忘记密码
        login_forgetpwd.setOnClickListener(this);

        tetle_text = (TextView)findViewById(R.id.tetle_text);//标题
        tetle_text.setText("登录页面");
        tx_Ver=(TextView)findViewById(R.id.tx_Ver);
        String v=PackageUtils.getVersionName(LoginActivity.this);
        int n=PackageUtils.getCurrVersion(LoginActivity.this);
        tx_Ver.setText("版本号:"+v+"("+n+")");

        login_login = (TextView)findViewById(R.id.login_login);//登录
        login_user_clear= (ImageView) findViewById(R.id.login_clear);
        login_psw_clear= (ImageView) findViewById(R.id.psw_clear);

        login_user_clear.setOnClickListener(this);
        login_psw_clear.setOnClickListener(this);

        login_phone_et = (AutoCompleteTextView) findViewById(R.id.login_phone_et);//手机号码输入框
        login_pwd_et = (EditText) findViewById(R.id.login_pwd_et);//密码输入
        http_pwd_et = (EditText) findViewById(R.id.http_pwd_et);//IP地址
        login_phone_et.setText(SharedPreferencesTool.getMStool(this).getPhone());
        //login_pwd_et.setText(SharedPreferencesTool.getMStool(this).getPassword());
        http_pwd_et.setText("http://www.m3lean.com:8080/login/doAction");
        setOnClick();//添加监听方法
        login_phone_et.setOnClickListener(this);
        db = DB_L.getInstance(this);
    }

    private void setOnClick() {
        login_forgetpwd.setOnClickListener(this);
        login_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        PHONE = login_phone_et.getText().toString().trim();
        pwd = login_pwd_et.getText().toString().trim();
        IP = http_pwd_et.getText().toString().trim();
        switch (v.getId()){
            case R.id.login_forgetpwd:
                //忘记密码
               /* intent = new Intent(LoginActivity.this, ForgetActivity.class);
                intent.putExtra("PHONE",PHONE);
                startActivity(intent);*/



                break;
            case R.id.login_phone_et:
                list.clear();
                List<String> getlist = db.loadInput();
                for(String str:getlist){
                    list.add(str);
                }
                arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
                login_phone_et.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                break;


            case R.id.login_login:
                //登录跳转
                if (PHONE.isEmpty()||pwd.isEmpty()||IP.isEmpty()){
                    customToast.showToast("输入内容不能为空");
                }else {
                    List<String> getlist0 = db.loadInput();
                    String str = login_phone_et.getText().toString();
                    if(getlist0.contains(str)){
                    }else{
                        Log.e("写入数据",str);
                        db.saveInput(str);
                    }
                    initData();//调用登录接口，进行跳转
                }
                break;

            case R.id.login_clear:
                //用户名清除
                Toast.makeText(this,"用户名清除",Toast.LENGTH_SHORT).show();
                login_phone_et.setText("");
                break;
            case R.id.psw_clear:
                //密码清除
                Toast.makeText(this,"密码清除",Toast.LENGTH_SHORT).show();
                login_pwd_et.setText("");
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
    private void showPopWindow() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        View view = (LinearLayout)LoginActivity.this. getLayoutInflater().inflate(R.layout.zxl_set, null);
         EditText ed_place= (EditText) view.findViewById(R.id.ed_place);
        String tempIP = SharedPreferencesTool.getMStool(LoginActivity.this).getString("IP");
       String tempPort = SharedPreferencesTool.getMStool(LoginActivity.this).getString("PORT");
       P.c("tempPort"+tempPort);
       if(tempPort.length()==0){
           ed_place.setText(tempIP);
       }else{
           ed_place.setText(SharedPreferencesTool.getMStool(LoginActivity.this).getIp());
       }


        builder.setView(view);
        builder.setTitle("服务器配置");


        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                show();
                String add = ed_place.getText().toString();
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
                } else if(add.split(":").length==2){
                    if(add.startsWith("http")){
                        SharedPreferencesTool.getMStool(LoginActivity.this).setString("IP",add);
                        SharedPreferencesTool.getMStool(LoginActivity.this).clear("PORT");
                    }else{
                        int o = add.lastIndexOf(":");
                        //是IP加端口
                        String ip = add.substring(0,o);
                        String port = add.substring(o+1,add.length());
                        try{
                            Integer.parseInt(port);
                        }catch (Exception e){
                            Toast.makeText(LoginActivity.this,"端口不合法！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SharedPreferencesTool.getMStool(LoginActivity.this).setString("IP",ip);
                        SharedPreferencesTool.getMStool(LoginActivity.this).setString("PORT",port);
                    }
                }
                cancel();

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
    private void cancel(){
        try
        {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);                     //设置mShowing值，欺骗android系统
            field.set(dialog, true);
        }catch(Exception e) {

            e.printStackTrace();
        }
    }
    private void show(){
        try
        {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);                     //设置mShowing值，欺骗android系统
            field.set(dialog, false);
        }catch(Exception e) {

            e.printStackTrace();
        }
    }
    
    

}
