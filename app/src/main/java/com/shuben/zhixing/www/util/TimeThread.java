package com.shuben.zhixing.www.util;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
/**
 * Copyright: Copyright (c) 2017-2025
 * Class:  实时更新时间的线程
 *
 * describe:
 */
public class TimeThread extends Thread {
    public TextView tvDate;
    private int msgKey1 = 22;
 
    public TimeThread(TextView tvDate) {
        this.tvDate = tvDate;
    }
    private Handler handler;
    public void setmHandler(Handler handler){
        this.handler = handler;
    }
    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
                Message msg = new Message();
                msg.what = msgKey1;
                mHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }
 
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 22:
 
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = sdf.format(new Date());
                    tvDate.setText(date + " "+getWeek());
                    if(handler!=null){
                        SimpleDateFormat go = new SimpleDateFormat("ss");
                        int goT = Integer.parseInt(go.format(new Date()));
                        switch (goT){
                            case 10:
                            case 20:
                            case 30:
                            case 40:
                            case 50:
                            case 0:
                                handler.sendEmptyMessage(2);
                                break;
                        }
                    }

                    break;
 
                default:
                    break;
            }
        }
    };
 
    /**
     * 获取今天星期几
     * @return
     */
    public static String getWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            default:
                return "";
        }
    }
}
