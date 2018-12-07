package com.shuben.zhixing.www.util;

import android.app.Activity;

import com.android.tu.loadingdialog.LoadingDailog;

/**
 * Created by Geyan on 2018/7/5.
 */

public class MyTool {
    private  static  MyTool myTool=null;
    public static synchronized MyTool getInstance() {

        if (myTool==null){
            myTool=new MyTool();
        }
        return myTool;
    }
    //网络延时动画加载
    public  void showLoadingDailog(LoadingDailog dialog, Activity activity){
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(activity)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();

    }




}
