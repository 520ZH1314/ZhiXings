package com.shuben.zhixing.module.mass.widget;

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
import com.android.volley.VolleyError;
import com.base.zhixing.www.util.DensityUtil;
import com.github.mikephil.charting.data.PieEntry;
import com.shuben.zhixing.module.mass.chart.PieManager;
import com.shuben.zhixing.www.DialogHttp;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inter.DialogClose;
import com.base.zhixing.www.inter.VolleyResult;

import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.IDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 不良记录列表
 */
public class ShowMassPie extends DialogHttp {
    private IDialog dlg;
    private LayoutInflater inflater;
    private Activity context;
    private TextView tetle_text;
    private String titleValue;
    private ImageView tetle_back;
    /**
     * 产品类型：1 合格品 0 不良品
     */
    private String ProductType;
    /**
     * 检验类型：1 全检 2 抽检
     */
    private String CheckType;
    private String beginDate;
    private String endDate;
    private PieManager pieManager;
    private int type = 0;
    public ShowMassPie(Activity activity, String titleValue,String ProductType, String CheckType,String beginDate,String endDate,int type){
        this.context = activity;
        this.ProductType = ProductType;
        this.CheckType = CheckType;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.type = type;
        this.titleValue = titleValue;
        inflater  = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initDialogHttp(context);
    }
    private DialogClose close;
    public void setClose(DialogClose close){
        this.close =close;
    }
    private com.github.mikephil.charting.charts.PieChart pieChart;
    public IDialog showSheet(){
        dlg = new IDialog(context, R.style.meet_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.showmasspie, null);
        int width = DensityUtil.getWindowWidth(context);
        int height = DensityUtil.getWindowHeight(context);
        layout.setMinimumWidth(DensityUtil.getWindowWidth(context));
       // P.c("长度"+DensityUtil.getWindowHeight(context));
        layout.setMinimumHeight(DensityUtil.getWindowHeight(context));
        tetle_text = layout.findViewById(R.id.tetle_text);
        tetle_text.setText(titleValue);
        tetle_back = layout.findViewById(R.id.tetle_back);
        pieChart = layout.findViewById(R.id.chart1);
        pieManager = new PieManager(context,pieChart);

        loadData();
        //此处重写关闭对话框
        tetle_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(close!=null){
                    close.close();
                }
                cancle();
            }
        });
        dlg.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });
        dlg.setCanceledOnTouchOutside(false);
        dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface arg0) {
                // TODO Auto-generated method stub
                if(close!=null){
                    close.close();
                }
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
    private ArrayList<PieEntry> pieEntries = new ArrayList<>();
    private  void loadData(){
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", "QC");
        if(type==0){

            params.put("NoName",titleValue);
            params.put("partNumber","");
        }else{
            params.put("NoName","");
            params.put("partNumber",titleValue);
        }
        params.put("ApiCode", "GetRejectsCodePieChart");
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);
        params.put("CheckType",CheckType);//根据传入的类型判断是全检单还是抽检单
        params.put("ProductType",ProductType);//不良品

//        params.put("TenantId",SharedPreferencesTool.getMStool(context).getTenantId());
            httpPostVolley(SharedPreferencesTool.getMStool(context).getIp() + UrlUtil.Url, params, new VolleyResult() {
                @Override
                public void success(JSONObject jsonObject) {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = jsonObject.getJSONArray("utArrt");

                        int len  = jsonArray.length();
                        for(int i=0;i<len;i++){
                            JSONObject o = jsonArray.getJSONObject(i);
                            PieEntry entry = new PieEntry(o.getInt("value"),o.getString("name"));
                            pieEntries.add(entry);
                        }

                        handler.sendEmptyMessage(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void error(VolleyError error) {

                }
            },false);
    }
    private Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case 1:
                    pieManager.showPieChart(titleValue,pieEntries);
                    break;
            }
        }
    };
}
