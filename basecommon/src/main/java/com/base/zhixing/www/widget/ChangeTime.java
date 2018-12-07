package com.base.zhixing.www.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Handler;
import android.os.Message;
import android.service.autofill.FillEventHistory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.R;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.TimeUtil;

import java.util.Calendar;
import library.NumberPickerView;
import library.view.GregorianLunarCalendarView;

public class ChangeTime {
    private Context context;

    private IDialog dlg;

    private  LayoutInflater inflater;


    private String isShowClear ;
    private int type;

    private  TextView textView;//外部传来的textview

    /**
     *  type    是否显示时间   2只显示年月日   1只显示时间, 3只显示年月
     * @param context
     * @param isShowClear
     * @param type
     */
    public ChangeTime(Context context,String isShowClear,int type) {
        this.context = context;
        this.isShowClear = isShowClear;
        this.type = type;
       inflater  = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public ChangeTime(Context context, String isShowClear,int type,TextView textView) {
        this.context = context;
        this.isShowClear = isShowClear;
        this.type = type;
        this.textView=textView;
        inflater  = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
   /* public ChangeTime(Context context) {
        this.context = context;
        inflater  = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/
    private Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        switch (msg.what){
            case 1:

                break;
        }
        }
    };
    private SelectTime selectTime;
    public void setSelect(SelectTime select){
        this.selectTime =select;
    }

    /**
     * 设置显示的日期
     */
    private int displayCalendar = -1;
    public void setPastCanendar(int displayCalendar){
            this.displayCalendar = displayCalendar;
    }


    public Dialog showSheet() {
        dlg = new IDialog(context, R.style.head_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.change_time, null);
        TextView sure = (TextView) layout.findViewById(R.id.sure);
        TextView cancle = (TextView) layout.findViewById(R.id.cancle);
        TextView clear = layout.findViewById(R.id.clear);
        final GregorianLunarCalendarView calendarView = (GregorianLunarCalendarView) layout.findViewById(R.id.calendar_view_start);
        if(displayCalendar!=-1){
            calendarView.init(TimeUtil.getPastCalendar(displayCalendar));
        }else{
            calendarView.init();
        }


        if(isShowClear.length()==0){
            clear.setVisibility(View.GONE);
        }else{
            //
            clear.setText(isShowClear);
            clear.setVisibility(View.VISIBLE);
        }
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectTime!=null){
                    selectTime.select("",0);
                    cancle();
                }
            }
        });
        final NumberPickerView picker_minute = (NumberPickerView) layout.findViewById(R.id.picker_minute);
        final NumberPickerView picker_hour = (NumberPickerView) layout.findViewById(R.id.picker_hour);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GregorianLunarCalendarView.CalendarData calendarData0 = calendarView.getCalendarData();
                final Calendar calendar0 = calendarData0.getCalendar();
                final String show = calendar0.get(Calendar.YEAR) + "-"
                        + (calendar0.get(Calendar.MONTH) + 1) + "-"
                        + calendar0.get(Calendar.DAY_OF_MONTH)+"  "+picker_hour.getContentByCurrValue()+":"+picker_minute.getContentByCurrValue();


                if(selectTime!=null){
                    selectTime.select(show,calendar0.getTimeInMillis());
                    cancle();
                }else {
                    textView.setText(show);
                    cancle();

                }


            }
        });
        if(type==1){
            calendarView.setVisibility(View.GONE);
        }else if(type==2){
            picker_minute.setVisibility(View.GONE);
            picker_hour.setVisibility(View.GONE);
        }else if(type==3){
            picker_minute.setVisibility(View.GONE);
            picker_hour.setVisibility(View.GONE);
            calendarView.setNumberPickerDayVisibility(View.GONE);
        }


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

    public void cancle(){
        if(dlg!=null){
            dlg.cancel();
            dlg = null;
        }
    }
}
