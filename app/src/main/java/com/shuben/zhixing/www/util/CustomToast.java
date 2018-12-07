package com.shuben.zhixing.www.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shuben.zhixing.www.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/5/9 0009.
 */
public class CustomToast {
    private Toast toast;
    private TextView TVshowMessage;
    private Context context;


    public CustomToast() {

    }
    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0,1000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, 500 );
    }
    /**
     * 自定义默认样式
     * */

   public CustomToast(Activity mContext) {
       context = mContext;
     LayoutInflater inflater = mContext.getLayoutInflater();
     View layout = inflater.inflate(R.layout.mycustom_toast, (ViewGroup) mContext.findViewById(R.id.llToast));
     TVshowMessage = (TextView) layout.findViewById(R.id.tvTitleToast);
     toast = new Toast(mContext.getApplicationContext());
     toast.setGravity(Gravity.CENTER, 0, 0);
     toast.setDuration(Toast.LENGTH_SHORT);
     toast.setView(layout);
 }
    /**
     * 自定义非默认样式
     * */
    public CustomToast(Activity mContext, int layoutID) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View layout = inflater.inflate(layoutID, (ViewGroup) mContext.findViewById(R.id.llToast));
        TVshowMessage = (TextView) layout.findViewById(R.id.tvTitleToast);
        toast = new Toast(mContext.getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
    }

    /**
     *  显示Toast
     * */
    public void showToast(String showMessage){
        if(toast!=null &&TVshowMessage!=null){
            TVshowMessage.setText(showMessage);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
