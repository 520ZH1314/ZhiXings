package com.zhixing.masslib.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.DialogHttp;
import com.base.zhixing.www.widget.IDialog;
import com.zhixing.masslib.R;
import com.zhixing.masslib.adapter.ShowMassItemOkAdapter;
import com.zhixing.masslib.bean.QC_NoListBean;
import com.zhixing.masslib.util.Common;
import com.zhixing.masslib.util.SyLinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 合格率列表信息展示
 */
public class ShowMassDetailOk extends DialogHttp {
    private IDialog dlg;
    private LayoutInflater inflater;
    private Activity context;
    private TextView tetle_text;
    private String titleValue;
    private ImageView tetle_back;
    private RecyclerView recycler_view;
    private ShowMassItemOkAdapter itemAdapter;
    private String CHECK_TYPE;
    private String WORKNO;
    private String PlanDate;
    public ShowMassDetailOk(Activity activity, String titleValue,String CHECK_TYPE, String WORKNO,String PlanDate){
        this.context = activity;
        this.WORKNO = WORKNO;
        this.CHECK_TYPE = CHECK_TYPE;
        this.titleValue = titleValue;
        this.PlanDate = PlanDate;
        inflater  = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initDialogHttp(context);
    }
    private String PiCiNo;
    public void setPiCiNo(String PiCiNo){
        this.PiCiNo = PiCiNo;

    }
    public IDialog showSheet(){
        dlg = new IDialog(context, R.style.meet_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.showmassdetail_ok, null);
        int width = DensityUtil.getWindowWidth(context);
        int height = DensityUtil.getWindowHeight(context);
        layout.setMinimumWidth(DensityUtil.getWindowWidth(context));
       // P.c("长度"+DensityUtil.getWindowHeight(context));
        layout.setMinimumHeight(DensityUtil.getWindowHeight(context));
        recycler_view = layout.findViewById(R.id.recycler_view);
        itemAdapter = new ShowMassItemOkAdapter(context,okListBeans);
       /* LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);*/
        SyLinearLayoutManager manager =new SyLinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        recycler_view.setLayoutManager(manager);
        recycler_view.setAdapter(itemAdapter);
        tetle_text = layout.findViewById(R.id.tetle_text);
        tetle_text.setText(titleValue);
        tetle_back = layout.findViewById(R.id.tetle_back);
        loadData();
        //此处重写关闭对话框
        tetle_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    private ArrayList<QC_NoListBean> okListBeans = new ArrayList<>();
    private  void loadData(){
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetAllProductCheckList");
        params.put("WorkNo",WORKNO);
        params.put("CheckType",CHECK_TYPE);//根据传入的类型判断是全检单还是抽检单
        params.put("ProductType","1");//良品
        params.put("WorkDate",PlanDate);
        if(PiCiNo!=null){
            params.put("PiCiNo",PiCiNo);
        }
//        params.put("TenantId",SharedPreferencesTool.getMStool(context).getTenantId());
        httpPostVolley(SharedPreferencesTool.getMStool(context).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("rows");
                    okListBeans.clear();
                    int len  = jsonArray.length();
                    for(int i=0;i<len;i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        QC_NoListBean qb = new QC_NoListBean();
                        qb.setDes(object.getString("ExceptionName"));
                        qb.setId(object.getString("ID"));
                        qb.setNo(String.valueOf(i));
                        qb.setTime(object.getString("CreateTime").split("T")[1]);
                        qb.setNum(object.getString("ItemCount"));
                        okListBeans.add(qb);
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
                    itemAdapter.updata(okListBeans);
                    break;
            }
        }
    };
}
