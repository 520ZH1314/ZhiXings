package com.zhixing.tpmlib.utils;


import com.android.tu.loadingdialog.LoadingDailog;
import com.base.zhixing.www.AppManager;

public class DialogUtils {


    private volatile static DialogUtils dialogUtils;
    private LoadingDailog dialog;


    private DialogUtils(){};

    public static DialogUtils getInsteance() {
        if (dialogUtils == null) {


            synchronized (DialogUtils.class) {


                if (dialogUtils == null) {


                    dialogUtils = new DialogUtils();


                }


            }


        }
        return  dialogUtils;
    }







    public  void Show(String title){
        if (dialog != null) {
            dialog.show();
        } else {
            LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(AppManager.getAppManager().currentActivity())
                    .setMessage(title)
                    .setCancelable(true)
                    .setCancelOutside(true);
            dialog=loadBuilder.create();
            dialog.show();
        }
    }



    public  void diss(String title){
        if (dialog != null) {
            dialog.dismiss();
        }
    }



}