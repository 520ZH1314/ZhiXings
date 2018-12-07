package com.shuben.zhixing.www.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.shuben.zhixing.www.reminder.ReminderActivity;
import com.shuben.zhixing.www.reminder.Reminder_CaiGouActivity;
import com.shuben.zhixing.www.util.NetUtils;

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

public class PostAsyncTask extends AsyncTask<String, Void, String> {
    public static String TAG="PostAsyncTask";
    private JSONObject jsonObject;
    private Activity context;
    private SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private int type=0;
    public PostAsyncTask(Activity context,JSONObject jsonObject,int type) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.jsonObject=jsonObject;
        this.type=type;
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
            if(status.equals("true")){
                if(type==0){
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.setClass(context, ReminderActivity.class);
                    intent.putExtra("tag",1);
                    context.startActivity(intent);
                    context.finish();
                }else  if(type==1){
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.setClass(context, Reminder_CaiGouActivity.class);
                    intent.putExtra("tag",1);
                    context.startActivity(intent);
                    context.finish();
                }

            }else{
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
