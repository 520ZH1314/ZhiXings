package com.shuben.zhixing.www.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.shuben.zhixing.www.util.NetUtils;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Geyan on 2017/8/29.
 */

public class ReminderAsyncTask extends AsyncTask<String, Void, String> {
    public static String TAG="ReminderAsyncTask";
    private JSONObject jsonObject;
    private String userId="";
    private Activity context;
    private SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    public ReminderAsyncTask(Activity context, JSONObject jsonObject, String userId) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.jsonObject=jsonObject;
        this.userId=userId;
        initPref(context);
    }
    private void initPref(Context context) {
        // TODO Auto-generated method stub
        pref = context.getSharedPreferences("myActivityName", 0);
        editor=pref.edit();
    }
    @Override
    protected String doInBackground(String... params) {
        String str = "";
        try {
            URL url = new URL(params[0]);
            String content = String.valueOf(jsonObject);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(20000);
            // 设置允许输出
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            // 设置User-Agent: Fiddler
            conn.setRequestProperty("ser-Agent", "Fiddler");
            // 设置contentType
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(content.getBytes());
            os.close();
            //服务器返回的响应码
            int code = conn.getResponseCode();
            if (code == 200) {

                InputStream is = conn.getInputStream();
                str = NetUtils.readString(is);
                //然后我们把json转换成JSONObject类型得到{"Person": //{"username":"zhangsan","age":"12"}}

            } else {

                Log.e("","#####################################"+code);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
    @Override
    protected void onPostExecute(String result) {
        Log.e(TAG, result);
        try {
            JSONObject jsonObject=new JSONObject(result);
            String status=jsonObject.getString("status");
            String message=jsonObject.getString("message");

           // String TenantId=jsonObject.getString("TenantId");


            if(status.equals("true")){
                //SharedPreferencesTool.getMStool(context).saveUserId(userId);
                Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
                insert(message);

            }else{
                Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void insert(String innerUrgeBillNo){
        {
            //Map<String, String> map = new HashMap<String, String>();
            JSONObject result=new JSONObject();
            JSONObject list=new JSONObject();
            JSONObject data=new JSONObject();
            JSONArray deleted=new JSONArray();
            JSONArray inserted=new JSONArray();
            JSONArray updated=new JSONArray();


            try {
                data.put("InnerUrgeBillNo", innerUrgeBillNo);
                data.put("CCUserId", this.userId);
                inserted.put(0, data);
                list.put("deleted", deleted);
                list.put("inserted", inserted);
                list.put("updated", updated);
                list.put("_changed", true);
                result.put("list", list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PostAsyncTask postAsyncTask=new PostAsyncTask(context,result,2);
            String postUrl= UrlUtil.GetPostUrl(UrlUtil.IP,UrlUtil.InnerUrgeOrderCC);
            Log.e("提交抄送人", result.toString());
            postAsyncTask.execute(postUrl);
        }


    }



}
