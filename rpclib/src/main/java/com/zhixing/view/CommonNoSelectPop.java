package com.zhixing.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.adapter.SetSelectAdapter;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.SyLinearLayoutManager;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.DialogHttp;
import com.base.zhixing.www.widget.IDialog;
import com.base.zhixing.www.widget.RecycleViewDivider;
import com.zhixing.beans.NoItem;
import com.zhixing.rpclib.NoSelectAdapter;
import com.zhixing.rpclib.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;


public class CommonNoSelectPop extends DialogHttp {
    private Activity context;
    private Handler handler;
    private IDialog dlg;


    private LayoutInflater inflater;
    private String titleV;

    public CommonNoSelectPop(Activity context, Handler handler, String title) {

        this.context = context;
        this.handler = handler;
        this.titleV = title;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initDialogHttp(context);
    }
    private  ArrayList<NoItem> foodsBeans;
    public void setData(ArrayList<NoItem> foodsBeans){
        this.foodsBeans = foodsBeans;
    }


    private NoSelectAdapter setSelectAdapter;
    private boolean isMid;

    public void setMidH(boolean isMid) {
        this.isMid = isMid;
    }

    private SetSelect select;

    public void setSelect(SetSelect select) {
        this.select = select;
    }

    private RecyclerView meeting_layout;

    public Dialog showSheet() {

        dlg = new IDialog(context, R.style.meet_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
               R.layout.rpc_select_pop, null);
        meeting_layout = layout.findViewById(R.id.select_layout);
        meeting_layout.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 1, context.getResources().getColor(R.color.content_line)));
//        manager.setOrientation(RecyclerView.VERTICAL);
        SyLinearLayoutManager manager = new SyLinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        meeting_layout.setLayoutManager(manager);
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
        TextView title = layout.findViewById(R.id.title);
        title.setText(titleV);
        TextView cancel = layout.findViewById(R.id.cancel);
        TextView sure = layout.findViewById(R.id.sure);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = setSelectAdapter.nowIndex();
                if (index == -1) {
                    Toasty.INSTANCE.showToast(context, "请选择");
                    return;
                }

                if (select != null) {

                    select.select(null,foodsBeans.get(index).getID(), foodsBeans.get(index).getNo());
                    cancle();
                }

            }
        });
        setSelectAdapter = new NoSelectAdapter(context, foodsBeans, handler);
        meeting_layout.setAdapter(setSelectAdapter);

        setSelectAdapter.setOnItemClick(new NoSelectAdapter.onItemClick() {
            @Override
            public void clickItem(int pos) {
                     /*   if(select!=null){
                            select.select( );
                            cancle();
                        }*/

                    setSelectAdapter.selectPosition(pos);


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
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






    public void cancle() {
        if (dlg != null) {
            dlg.cancel();
            dlg = null;
        }
    }
}
