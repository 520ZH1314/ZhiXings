package com.zhixing.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.widget.IDialog;
import com.zhixing.rpclib.R;

import library.NumberPickerView;

public class ChangeNg {
    private Context context;
    private IDialog dlg;
    private LayoutInflater inflater;



    private SharedUtils sharedUtils;
    public ChangeNg(Context context,SharedUtils sharedUtils) {
        this.context = context;
        this.sharedUtils = sharedUtils;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 1:

                    break;
            }
        }
    };
    private SelectTime selectTime;

    public void setSelect(SelectTime select) {
        this.selectTime = select;
    }


    public Dialog showSheet() {
        dlg = new IDialog(context, R.style.head_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.change_ng, null);
        TextView sure = (TextView) layout.findViewById(R.id.sure);
        TextView cancle = (TextView) layout.findViewById(R.id.cancle);


        final NumberPickerView picker_minute = (NumberPickerView) layout.findViewById(R.id.chang_ng);
        if(sharedUtils.getStringValue("ng_lis1").length()!=0){
            picker_minute.setPickedIndexRelativeToMin(Integer.parseInt(sharedUtils.getStringValue("ng_lis1")));
        }else{
            picker_minute.setPickedIndexRelativeToMin(5);
        }

       /* String []ars = new String[100];
        for(int i=0;i<100;i++){
            ars[i] = String.valueOf(i+1);
            P.c(ars[i]);
        }
        picker_minute.setDisplayedValues(ars);*/
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(selectTime!=null){
                selectTime.select(picker_minute.getContentByCurrValue(),0);
                cancle();
            }


            }
        });



        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancle();
            }
        });
        dlg.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                // TODO Auto-generated method stub

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

            }
        });
        dlg.setContentView(layout);
        dlg.show();
        return dlg;
    }

    public void cancle() {
        if (dlg != null) {
            dlg.cancel();
            dlg = null;
        }
    }
}
