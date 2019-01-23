package com.base.zhixing.www.widget;

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

import com.base.zhixing.www.R;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.SelectDoubleTime;
import com.base.zhixing.www.view.Toasty;
import java.util.Calendar;
import library.NumberPickerView;
import library.view.GregorianLunarCalendarView;

public class ChangeDoubleTime {
    private Context context;

    private IDialog dlg;

    private  LayoutInflater inflater;




    public ChangeDoubleTime(Context context) {
        this.context = context;

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
    private SelectDoubleTime selectTime;
    public void setSelect(SelectDoubleTime select){
        this.selectTime =select;
    }

    public Dialog showSheet() {
        dlg = new IDialog(context, R.style.head_pop_style);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.change_double_time, null);
        TextView sure = (TextView) layout.findViewById(R.id.sure);
        TextView cancle = (TextView) layout.findViewById(R.id.cancle);
        TextView clear = layout.findViewById(R.id.clear);
        final GregorianLunarCalendarView calendarView = (GregorianLunarCalendarView) layout.findViewById(R.id.calendar_view_start);
       int x = 5;
        P.c(String.valueOf(x*=x/=x+x)+"几个");
        calendarView.init();


        final GregorianLunarCalendarView calendarView1 = (GregorianLunarCalendarView) layout.findViewById(R.id.calendar_view_end);
        calendarView1.init();



        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GregorianLunarCalendarView.CalendarData calendarData0 = calendarView.getCalendarData();
                Calendar calendar0 = calendarData0.getCalendar();
                GregorianLunarCalendarView.CalendarData calendarData1 = calendarView1.getCalendarData();
                Calendar calendar1 = calendarData1.getCalendar();
                //相同是0   第一个大于第二个是1   第一个小于第二个是-1
//                String showToast = calendar0.getTime().compareTo(calendar1.getTime())+"start : " + calendar0.get(Calendar.YEAR) + "-"
//                        + (calendar0.get(Calendar.MONTH) + 1) + "-"
//                        + calendar0.get(Calendar.DAY_OF_MONTH)+"---end"+calendar1.get(Calendar.YEAR) + "-"
//                        + (calendar1.get(Calendar.MONTH) + 1) + "-"
//                        + calendar1.get(Calendar.DAY_OF_MONTH);

                String begin =   calendar0.get(Calendar.YEAR) + "-"
                        + (calendar0.get(Calendar.MONTH) + 1) + "-"
                        + calendar0.get(Calendar.DAY_OF_MONTH);
                String end = calendar1.get(Calendar.YEAR) + "-"
                        + (calendar1.get(Calendar.MONTH) + 1) + "-"
                        + calendar1.get(Calendar.DAY_OF_MONTH);
                if(begin.compareTo(end)==1){
                    Toasty.INSTANCE.showToast(context,"结束时间小于起始时间");
                    return;
                }

                if(selectTime!=null){
                    selectTime.select(begin,end,calendar0.getTimeInMillis(),calendar1.getTimeInMillis());
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

    public void cancle(){
        if(dlg!=null){
            dlg.cancel();
            dlg = null;
        }
    }
}
