package com.shuben.zhixing.module.andon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.common.P;
import com.base.zhixing.www.widget.IDialog;
import com.shuben.zhixing.www.R;
import java.util.ArrayList;

public class DetailImageDlg {
    private Activity context;
    private IDialog dlg;
    private  LayoutInflater inflater;
    private Handler parentHandler;
    private int position;
    private ArrayList<ImageBean> imageBeans;
    private String titleV;
    int pop;
    public DetailImageDlg(Activity context,Handler parentHandler,ArrayList<ImageBean> imageBeans,String title,int pop) {
        this.context = context;
        this.imageBeans = imageBeans;
        this.parentHandler = parentHandler;
        this.titleV = title;
        this.pop = pop;
       inflater  = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private Handler handler = new Handler(){
        @Override
        public void dispatchMessage(final Message msg) {
            super.dispatchMessage(msg);
        switch (msg.what){
            case 0:

                break;

        }
        }
    };
    public void updata(ArrayList<ImageBean> imageBeans){
        dlgAdapter.updata(imageBeans);
    }

    private DetailDlgAdapter dlgAdapter;
    public Dialog showSheet() {
        dlg = new IDialog(context, R.style.head_pop_style);
        final ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.upload_images, null);
        final TextView title = (TextView) layout.findViewById(R.id.title);
        if(titleV!=null){
            title.setText(titleV);
        }
        final GridView childs_list = (GridView) layout.findViewById(R.id.childs_list);


        childs_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        dlg.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                // TODO Auto-generated method stub
                dlgAdapter = new DetailDlgAdapter(context,imageBeans,(layout.getWidth()-15)/3,parentHandler);
                childs_list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(layout.getWidth()-15)/3*2));
                dlgAdapter.setPop(pop);
                childs_list.setAdapter(dlgAdapter);
                childs_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long ll) {
                        P.c("地址"+imageBeans.size());
                        ArrayList<String> pts = new ArrayList<>();
                        for(int l=0;l<imageBeans.size();l++){
                            pts.add(imageBeans.get(l).getPath());
                            P.c("图片地址"+imageBeans.get(l).getPath());
                        }
                        if(pts.size()!=0){
                            CommonViewsPop viewsPop = new CommonViewsPop(context,handler,pts);
                            viewsPop.showSheet(i);
                        }

                    }
                });

            }
        });
        dlg.setCanceledOnTouchOutside(true);
        dlg.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
        dlg.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                // TODO Auto-generated method stub
                parentHandler.sendEmptyMessage(5);
            }
        });
        dlg.setContentView(layout);
        dlg.show();
        return dlg;
    }
    public void cancle(){
        if(dlg!=null){
            dlg.cancel();
            dlg = null;
        }
    }



}
