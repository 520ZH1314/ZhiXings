package com.zhixing.masslib.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.widget.IDialog;
import com.base.zhixing.www.widget.XEditText;
import com.base.zhixing.www.widget.nicespinner.NiceSpinner;
import com.zhixing.masslib.R;
import com.zhixing.masslib.bean.QC_Reason;
import com.zhixing.masslib.dataBase.MassDB;
import com.zhixing.masslib.inter.AddNoF;

import java.util.ArrayList;

public class AddNoList {
    private IDialog dlg;
    private LayoutInflater inflater;
    private Activity context;
    private ImageView close;
    private NiceSpinner spil;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<QC_Reason> qc_reasons = new ArrayList<>();
    private void add(){
       new Thread(){
           @Override
           public void run() {
               super.run();
               MassDB.getInstance().getQc_reasons(qc_reasons,0);
               handler.sendEmptyMessage(1);
           }
       }.start();

    }

    public AddNoList(Activity activity){
        this.context = activity;

        inflater  = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        add();
    }
    private Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {

            super.dispatchMessage(msg);
            switch (msg.what){
            case 1:
                    int size = qc_reasons.size();
                     list.clear();
                    for(int i=0;i<size;i++){
                        list.add(qc_reasons.get(i).getName());
                        P.c("不良"+qc_reasons.get(i).getName());
                    }
                spil.attachDataSource(list);

                break;
        }
        }
    };
    private AddNoF addNoF;
    public void setAddNoF(AddNoF addNoF){
        this.addNoF = addNoF;
    }
    public IDialog showSheet(){
        dlg = new IDialog(context, R.style.meet_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.add_no_list, null);
        int width = DensityUtil.getWindowWidth(context);
        int height = (int) (DensityUtil.getWindowHeight(context)*0.7);
        layout.setMinimumWidth(DensityUtil.getWindowWidth(context));
       // P.c("长度"+DensityUtil.getWindowHeight(context));
        layout.setMinimumHeight(DensityUtil.getWindowHeight(context));
        close = layout.findViewById(R.id.close);
        spil = layout.findViewById(R.id.spil);
        TextView sure = layout.findViewById(R.id.sure);
        final XEditText edit = layout.findViewById(R.id.edit);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addNoF!=null){

                    addNoF.addNo(edit.getTextEx().toLowerCase(),qc_reasons.get(spil.getSelectedIndex()).getId());
                    cancle();
                }
            }
        });
        dlg.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancle();
            }
        });
        dlg.setCanceledOnTouchOutside(false);
        dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
        dlg.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        ViewGroup.LayoutParams layoutParams = new  ViewGroup.LayoutParams(width, height);
        dlg.setCanceledOnTouchOutside(true);
        dlg.setContentView(layout,layoutParams);
        dlg.show();
        /*  dlg.full();*/
        return dlg;
    }
    public void cancle(){
        if(dlg!=null){
            dlg.cancel();
            dlg = null;
        }
    }
}
