package com.zhixing.rpclib;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.WaveSideBarView;
import com.base.zhixing.www.widget.XEditText;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollAbleFragment;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollableHelper;
import com.liulishuo.magicprogresswidget.MagicProgressBar;
import com.zhixing.beans.TxtInfo;
import com.zhixing.common.Common;

import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RpcFrame0 extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer,Serializable {


    private Handler handler;

    public static RpcFrame0 newInstance( ) {
        RpcFrame0 listFragment = new RpcFrame0();
        return listFragment;
    }



    public void setHandler( Handler handler ){
        this.handler = handler;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHandler(handler);
    }

      ScrollView scrool_view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rpc_edit_item1, container, false);
        ButterKnife.bind(this, view);
        scrool_view = view.findViewById(R.id.scrool_view);


        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        item16.setOnClickListener((view) ->{
            String i0 = item16_edit.getTextEx().intern();
            String i1 = item17_edit.getTextEx().intern();
            Message msg = new Message();
            msg.what = 3;
            msg.obj = new String[]{i0,i1};
            handler.sendMessage(msg);
        });

    }

    public void resetNg(){
        item16_edit.setTextEx("");
        item17_edit.setTextEx("");
    }
    @BindView(R2.id.item5)
    TextView item5;
    @BindView(R2.id.item6)
    TextView item6;
    @BindView(R2.id.item7_)
    TextView item7_;
    @BindView(R2.id.item8)
    TextView item8;
    @BindView(R2.id.item7)
    TextView item7;
    @BindView(R2.id.item10)
    TextView item10;
    @BindView(R2.id.item11)
    TextView item11;

    @BindView(R2.id.item9)
    TextView item9;
    @BindView(R2.id.item12)
    TextView item12;
    @BindView(R2.id.item13)
    TextView item13;
    @BindView(R2.id.edit_rp)
    LinearLayout edit_rp;
    @BindView(R2.id.item14)
    XEditText item14;
    @BindView(R2.id.item15)
    XEditText item15;
    @BindView(R2.id.item18)
    TextView item18;
    @BindView(R2.id.item19)
    TextView item19;
    @BindView(R2.id.item151)
    TextView item151;

    @BindView(R2.id.item141)
    TextView item141;
    @BindView(R2.id.item18_pro)
    MagicProgressBar item18_pro;
    public String getItem14(){
        return  item14.getTextEx().intern();
    }
    public String getItem15(){
        return  item15.getTextEx().intern();
    }
    public void setItem14(String i14){
        item14.setTextEx(i14);
        item14.setSelection(i14.length());
    }
    public void setItem15(String i14){
        item15.setTextEx(i14);
        item15.setSelection(i14.length());

    }
    public void setEdit_rpV(int i){
        edit_rp.setVisibility(i);
    }
    private final String TIME_TAG = "0001-01-01 00:00:00";
    private void initTxt(){
        item5.setText(txtInfo.getProductCode());
        item6.setText(txtInfo.getBatchCustomer());
        item7_.setText(txtInfo.getLineName());
        item7.setText(String.valueOf(txtInfo.getPlanQty()));

        long pt = TimeUtil.parseTimeC(txtInfo.getPlanStartDate());
        item8.setText(pt==0?"NAN":TimeUtil.getTime(pt));
        String time0= TimeUtil.getTime(TimeUtil.parseTimeC(txtInfo.getPlanEndDate()));
        item10.setText(time0.equals(TIME_TAG)?"暂无时间":time0);
        String time1 = TimeUtil.getTime(TimeUtil.parseTimeC(txtInfo.getRealStartDate()));
        item9.setText(time1.equals(TIME_TAG)?"暂无时间":time1);
        String time2 = TimeUtil.getTime(TimeUtil.parseTimeC(txtInfo.getRealEndDate()));
        item11.setText(time2.equals(TIME_TAG)?"暂无时间":time2);
        String time3 = TimeUtil.getTime(TimeUtil.parseTimeC(txtInfo.getRealStopDate()));
        item12.setText(time3.equals(TIME_TAG)?"暂无时间":time3);
        String time4 = TimeUtil.getTime(TimeUtil.parseTimeC(txtInfo.getRecoverDate()));
        item13.setText(time4.equals(TIME_TAG)?"暂无时间":time4);
        if(txtInfo.getState()==2){
            item13.setText("");
        }
        item14.setTextEx(String.valueOf(txtInfo.getRatio()));
        item15.setTextEx(String.valueOf(txtInfo.getUPH()));
        item16_v.setText(String.valueOf(txtInfo.getQty()));
        item17_v.setText(String.valueOf(txtInfo.getQtyNG()));
//        P.c("累计产出"+txtInfo.getQty());
        if(txtInfo.getQty()!=0){
            item18.setText(formatDouble((Double.parseDouble(item17_v.getText().toString())/Double.parseDouble(item16_v.getText().toString())*100))+"%");
        }
        if(txtInfo.getQtyNG()!=0){
            double jindu = formatDouble(Double.parseDouble(item16_v.getText().toString())/txtInfo.getPlanQty());
            // P.c(Float.parseFloat(item16_v.getText().toString())+"=="+txtInfo.getPlanQty());
            item18_pro.setPercent(Float.parseFloat(String.valueOf(jindu)));
            item19.setText(String.valueOf(formatDouble(jindu*100))+"%");
        }
        item141.setOnClickListener((v)->{
            handler.sendEmptyMessage(5);

        });
        item151.setOnClickListener((view -> {
            item141.performClick();
        }));


    }



    public void setGoT(){
        scrool_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrool_view.smoothScrollTo(0,0);
            }
        },500);
    }
    /**
     * 格式化double数据
     */
    public   double formatDouble(double fromDouble){
        if(fromDouble==0){
            return 0;
        }
        BigDecimal b = new BigDecimal(fromDouble);
        // 保留2位小数
        double targetDouble = b.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return targetDouble;
    }
    @BindView(R2.id.item16)
     TextView item16;
    @BindView(R2.id.item16_edit)
     XEditText item16_edit;
    @BindView(R2.id.item16_v)
    TextView item16_v;

    @BindView(R2.id.item17_edit)
    XEditText item17_edit;
    @BindView(R2.id.item17_v)
    TextView item17_v;


    TxtInfo txtInfo;

    public void changeTextInfo(TxtInfo txtInfo){
        this.txtInfo = txtInfo;
        initTxt();
    }
    @Override
    public View getScrollableView() {
        return scrool_view;
    }


}
