package com.zhixing.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.util.SyLinearLayoutManager;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.DialogHttp;
import com.base.zhixing.www.widget.IDialog;
import com.base.zhixing.www.widget.RecycleViewDivider;
import com.zhixing.beans.NoItem;
import com.zhixing.rpclib.NoSelectAdapter;
import com.zhixing.rpclib.R;

import java.util.ArrayList;


public class CommonStatetPop extends DialogHttp {
    private Activity context;
    private Handler handler;
    private IDialog dlg;


    private LayoutInflater inflater;

    private int STATUE;
    public CommonStatetPop(Activity context, Handler handler,int STATUE) {

        this.context = context;
        this.handler = handler;
        this.STATUE = STATUE;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initDialogHttp(context);
    }




    private boolean isMid;

    public void setMidH(boolean isMid) {
        this.isMid = isMid;
    }

    private SetSelect select;

    public void setSelect(SetSelect select) {
        this.select = select;
    }

//    private RecyclerView meeting_layout;

    public Dialog showSheet() {

        dlg = new IDialog(context, R.style.meet_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
               R.layout.rpc_state_pop, null);
       /* meeting_layout = layout.findViewById(R.id.select_layout);
        meeting_layout.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 1, context.getResources().getColor(R.color.content_line)));
//        manager.setOrientation(RecyclerView.VERTICAL);
        SyLinearLayoutManager manager = new SyLinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        meeting_layout.setLayoutManager(manager);*/
        /* meeting_layout.setAdapter(new MeetMenuAdapter(context,meetMenuBeans,handler));*/
      /*  View view = layout.findViewById(R.id.dis);
        int height = DensityUtil.getWindowWidth(context)*155/400;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        view.setLayoutParams(params);*/
//        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(DensityUtil.getWindowWidth(context));

        CardView la = layout.findViewById(R.id.layout);
        int h0 = DensityUtil.getWindowWidth(context) * 450 / 400;
        if (isMid) {
            h0 = DensityUtil.getWindowWidth(context) * 245 / 318;
        }
        LinearLayout.LayoutParams ps0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, h0);
        ps0.setMargins(10,10,10,10);
        la.setLayoutParams(ps0);
        int h1 = DensityUtil.getWindowWidth(context) * 50 / 400;
        FrameLayout.LayoutParams ps1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, h1);
//        TextView title = layout.findViewById(R.id.title);
//        title.setText(titleV);
        init(layout);



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
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        dlg.setContentView(layout);
        dlg.show();


        /*  dlg.full();*/
        return dlg;
    }
    public void close(){
        if(dlg!=null){
            dlg.cancel();
            dlg = null;
        }
    }
    private RelativeLayout layout0,layout1,layout2,layout3;
    private CheckBox layout00,layout11,layout22,layout33;
    private void init(View view){
        layout0 = view.findViewById(R.id.layout0);
        layout1 = view.findViewById(R.id.layout1);
        layout2 = view.findViewById(R.id.layout2);
        layout3 = view.findViewById(R.id.layout3);
        layout00 = view.findViewById(R.id.layout00);
        layout11 = view.findViewById(R.id.layout11);
        layout22 = view.findViewById(R.id.layout22);
        layout33 = view.findViewById(R.id.layout33);

        switch (STATUE){
            case 0:
                layout00.setEnabled(true);
                layout11.setEnabled(false);
                layout22.setEnabled(false);
                layout33.setEnabled(false);

                layout0.setEnabled(true);
                layout1.setEnabled(false);
                layout2.setEnabled(false);
                layout3.setEnabled(false);

                break;
            case 1:
                layout00.setEnabled(false);
                layout11.setEnabled(true);
                layout22.setEnabled(false);
                layout33.setEnabled(true);

                layout0.setEnabled(false);
                layout1.setEnabled(true);
                layout2.setEnabled(false);
                layout3.setEnabled(true);
                break;
            case 2:
                layout00.setEnabled(false);
                layout11.setEnabled(false);
                layout22.setEnabled(true);
                layout33.setEnabled(false);

                layout0.setEnabled(false);
                layout1.setEnabled(false);
                layout2.setEnabled(true);
                layout3.setEnabled(false);
                break;
            case 3:
                layout00.setEnabled(false);
                layout11.setEnabled(false);
                layout22.setEnabled(false);
                layout33.setEnabled(false);

                layout0.setEnabled(false);
                layout1.setEnabled(false);
                layout2.setEnabled(false);
                layout3.setEnabled(false);
                break;
        }

        layout0.setOnClickListener((v)->{
            layout00.setChecked(true);
                if(select!=null){
                    select.select("","","1");
                    close();
                }
        });
        layout00.setOnClickListener((v)->{
            layout00.setChecked(true);
            if(select!=null){
                select.select("","","1");
                close();
            }
        });

        layout1.setOnClickListener((v)->{
            layout11.setChecked(true);
            if(select!=null){
                select.select("","","2");
                close();
            }
        });
        layout11.setOnClickListener((v)->{
            layout11.setChecked(true);
            if(select!=null){
                select.select("","","2");
                close();
            }
        });
        layout2.setOnClickListener((v)->{
            layout22.setChecked(true);
            if(select!=null){
                select.select("","","1");
                close();
            }
        });
        layout22.setOnClickListener((v)->{
            layout22.setChecked(true);
            if(select!=null){
                select.select("","","1");
                close();
            }
        });
        layout3.setOnClickListener((v)->{
            layout33.setChecked(true);
            if(select!=null){
                select.select("","","3");
                close();

            }
        });
        layout33.setOnClickListener((v)->{
            layout33.setChecked(true);
            if(select!=null){
                select.select("","","3");
                close();

            }
        });

    }




    public void cancle() {
        if (dlg != null) {
            dlg.cancel();
            dlg = null;
        }
    }
}
