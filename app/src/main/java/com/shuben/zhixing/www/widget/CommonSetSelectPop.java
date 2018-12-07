package com.shuben.zhixing.www.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.widget.IDialog;
import com.shuben.zhixing.www.DialogHttp;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.adapter.SetSelectAdapter;
import com.shuben.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CommonSetSelectPop extends DialogHttp {
    private Activity context;
    private Handler handler;
    private IDialog dlg;
    private Handler popHandler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
                switch (msg.what){
                    case 1:
                        setSelectAdapter.updata(maps);


                        break;
                }
        }
    };




    private LayoutInflater inflater;
    private String titleV;

    public CommonSetSelectPop(Activity context , Handler handler, String title) {
        this.context = context;
        this.handler = handler;
        this.titleV = title;
        inflater  = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        params.clear();
        initDialogHttp(context);
    }
    private boolean isDo = true;
    public void isDoall( boolean isDo){
        this.isDo = isDo;
    }
    private SetSelectAdapter setSelectAdapter;
    private boolean isMid;
    public void setMidH(boolean isMid){
        this.isMid = isMid;
    }
    private SetSelect select;
    public void setSelect(SetSelect select){
        this.select = select;
    }

    private RecyclerView meeting_layout;
    private ArrayList<Map<String,String>> maps = new ArrayList<>();
    public Dialog showSheet() {
        dlg = new IDialog(context, R.style.meet_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.common_setselect_layout, null);
          meeting_layout = layout.findViewById(R.id.select_layout);
        meeting_layout.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL,1,context.getResources().getColor(R.color.content_line)));
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        meeting_layout.setLayoutManager(manager);
       /* meeting_layout.setAdapter(new MeetMenuAdapter(context,meetMenuBeans,handler));*/
      /*  View view = layout.findViewById(R.id.dis);
        int height = DensityUtil.getWindowWidth(context)*155/400;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        view.setLayoutParams(params);*/
//        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(DensityUtil.getWindowWidth(context));

        LinearLayout la = layout.findViewById(R.id.layout);
        int h0 = DensityUtil.getWindowWidth(context)*450/400;
        if(isMid){
            h0 = DensityUtil.getWindowWidth(context)*245/318;
        }
        LinearLayout.LayoutParams ps0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,h0);
        la.setLayoutParams(ps0);
        int h1 = DensityUtil.getWindowWidth(context)*50/400;
        LinearLayout.LayoutParams ps1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,h1);
        TextView title = layout.findViewById(R.id.title);
        title.setText(titleV);
        TextView cancel = layout.findViewById(R.id.cancel);
        TextView sure = layout.findViewById(R.id.sure);
        if(isDo){
            sure.setVisibility(View.GONE);
        }else{
            sure.setVisibility(View.VISIBLE);
        }
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = setSelectAdapter.nowIndex();
                if(index==-1){
                    Toasty.INSTANCE.showToast(context,"请选择");
                    return;
                }

                if(select!=null){
                    Map<String,String> bean = maps.get(index);
                    select.select(bean.get("id"),bean.get("code"),bean.get("name"));
                    cancle();
                }

            }
        });
        setSelectAdapter = new SetSelectAdapter(context,maps,handler);
        meeting_layout.setAdapter(setSelectAdapter);

        setSelectAdapter.setOnItemClick(new SetSelectAdapter.onItemClick() {
            @Override
            public void clickItem(int pos) {
                     /*   if(select!=null){
                            select.select( );
                            cancle();
                        }*/
                     if(isDo){
                         if(select!=null){
                             Map<String,String> bean = maps.get(pos);
                             select.select(bean.get("id"),bean.get("code"),bean.get("name"));
                             cancle();
                         }
                     }else{
                         setSelectAdapter.selectPosition(pos);
                     }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancle();
            }
        });
        loadData();
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
    Map<String,String> params = new HashMap<>();
    public Map<String,String> getSet(){
        return params;
    }

    private void loadData(){

        params.put("AppCode", "EPS");
        params.put("TenantId",SharedPreferencesTool.getMStool(context).getTenantId());


            httpPostVolley(SharedPreferencesTool.getMStool(context).getIp() + UrlUtil.Url, params, new VolleyResult() {
                @Override
                public void success(JSONObject jsonObject) {

                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        int len  = jsonArray.length();
                        maps.clear();
                        for(int i=0;i<len;i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Map<String,String> map = new HashMap<>();

                            pats(map,object);


                            maps.add(map);
                        }
                        popHandler.sendEmptyMessage(1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void error(VolleyError error) {

                }
            },false);

    }
    private void parse(Map<String,String> map,JSONObject object,String tag) throws JSONException {
        map.put("id",object.getString(tag+"Id"));
        map.put("name",object.getString(tag+"Name"));
        map.put("code",object.getString(tag+"Code"));
    }
private void pats(Map<String,String> map,JSONObject object) throws JSONException {
        if(titleV.equals("工厂")){
            parse(map,object,"Factory");
        }else if(titleV.equals("产线")){
            parse(map,object,"Line");
        }else if(titleV.equals("工位")){
            map.put("id",object.getString("LineStationId"));
            map.put("name",object.getString("StationName"));
            map.put("code",object.getString("StationCode"));
        }else if(titleV.equals("车间")){
            parse(map,object,"WorkShop");
        }

}


    public void cancle(){
        if(dlg!=null){
            dlg.cancel();
            dlg = null;
        }
    }
}
