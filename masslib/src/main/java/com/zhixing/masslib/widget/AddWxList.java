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

import com.base.zhixing.www.common.P;
import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.XEditText;
import com.base.zhixing.www.widget.nicespinner.NiceSpinner;
import com.base.zhixing.www.widget.IDialog;
import com.zhixing.masslib.R;
import com.zhixing.masslib.bean.MassItemBean;
import com.zhixing.masslib.bean.QC_Reason;
import com.zhixing.masslib.bean.WxItem;
import com.zhixing.masslib.dataBase.MassDB;
import com.zhixing.masslib.inter.AddNoF;
import com.zhixing.masslib.inter.AddWx;

import java.util.ArrayList;

public class AddWxList {
    private IDialog dlg;
    private LayoutInflater inflater;
    private Activity context;
    private ImageView close;
    private NiceSpinner spi0,spi1,spi2;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> list1 = new ArrayList<>();
    private ArrayList<String> list2 = new ArrayList<>();
    private ArrayList<QC_Reason> qc_reasons = new ArrayList<>();
    private ArrayList<QC_Reason> qc_reasons1 = new ArrayList<>();
    private void add(){
        new Thread(() -> {   MassDB.getInstance().getQc_reasons(qc_reasons,0);
            handler.sendEmptyMessage(1);}).start();
      new Thread(() -> {  MassDB.getInstance().getQc_reasons(qc_reasons1,1);
          handler.sendEmptyMessage(2);}).start();


    }
    private AddWx addWx;
    public void setR(AddWx addWx){
        this.addWx =addWx;
    }
    private MassItemBean b;
    public AddWxList(Activity activity,MassItemBean b){
        this.context = activity;
        this.b = b;
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
                    if(list.size()!=0)
                spi0.attachDataSource(list);


                break;
                case 2:
                    int size1 = qc_reasons1.size();
                    list1.clear();
                    for(int i=0;i<size1;i++){
                        list1.add(qc_reasons1.get(i).getName());
                        P.c("不良原因"+qc_reasons1.get(i).getName());
                    }
                    if(list1.size()!=0)
                    spi1.attachDataSource(list1);


                    break;
            }
        }
    };
    private AddNoF addNoF;
    private XEditText edit0,edit1,edit2;
    public void setAddNoF(AddNoF addNoF){
        this.addNoF = addNoF;
    }
    public IDialog showSheet(){
        dlg = new IDialog(context, R.style.meet_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.add_wx_list, null);
        int width = DensityUtil.getWindowWidth(context);
        int height = (int) (DensityUtil.getWindowHeight(context)*0.9);
        layout.setMinimumWidth(DensityUtil.getWindowWidth(context));
       // P.c("长度"+DensityUtil.getWindowHeight(context));
        layout.setMinimumHeight(DensityUtil.getWindowHeight(context));
        close = layout.findViewById(R.id.close);
        spi0 = layout.findViewById(R.id.spi0);
        spi1 = layout.findViewById(R.id.spi1);
        spi2 = layout.findViewById(R.id.spi2);
        list2.add("维修成功");
        list2.add("维修不成功");
        list2.add("报废");
        spi2.attachDataSource(list2);
        edit0 = layout.findViewById(R.id.edit0);
        edit1 = layout.findViewById(R.id.edit1);
        edit1.setEnabled(false);
        edit2 = layout.findViewById(R.id.edit2);
        TextView sure = layout.findViewById(R.id.sure);
        edit1.setTextEx(b.getProduct());
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
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num  = edit2.getTextEx().intern();
                try {
                    Integer.parseInt(num);
                    if(addWx!=null){
                        WxItem item = new WxItem();
                        item.setNo(edit0.getTextEx().intern());
                        item.setName(edit1.getTextEx().intern());
                        item.setNum(edit2.getTextEx().intern());
                        item.setOkC(qc_reasons1.get(spi1.getSelectedIndex()).getId());
                        item.setOkN(qc_reasons1.get(spi1.getSelectedIndex()).getName());
                        item.setNoC(qc_reasons.get(spi0.getSelectedIndex()).getId());
                        item.setNoN(qc_reasons.get(spi0.getSelectedIndex()).getName());
                        item.setResult(list2.get(spi2.getSelectedIndex()));
                        addWx.select(item);
                        cancle();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toasty.INSTANCE.showToast(context,"请输入正确的数量");
                }

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
