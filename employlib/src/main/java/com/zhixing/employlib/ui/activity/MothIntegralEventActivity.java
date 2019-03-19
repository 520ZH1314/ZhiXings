package com.zhixing.employlib.ui.activity;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.zhixing.employlib.R;

import java.util.HashMap;
import java.util.Map;

public class MothIntegralEventActivity extends BaseActvity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener {


    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;


    @Override
    public int getLayoutId() {
        return R.layout.activity_moth_integral_event;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        initView();

        initData();
    }

    @SuppressLint("SetTextI18n")

    protected void initView() {

        mTextMonthDay = (TextView) findViewById(R.id.tv_color_month_day);
        mTextYear = (TextView) findViewById(R.id.tv_color_year);
        mTextLunar = (TextView) findViewById(R.id.tv_color_lunar);
        mRelativeTool = (RelativeLayout) findViewById(R.id.rl_color_tool);
        mCalendarView = (CalendarView) findViewById(R.id.color_calendarView);
        mTextCurrentDay = (TextView) findViewById(R.id.tv_color_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        findViewById(R.id.fl_color_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout = (CalendarLayout) findViewById(R.id.color_calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }


    protected void initData( ) {

        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();

        for (int i = 1; i <15 ; i++) {
            map.put(getSchemeCalendar(year, month, i, 0xFF40db25, "假").toString(),
                    getSchemeCalendar(year, month, i, 0xFF40db25, "假"));

        }
        for (int i = 15; i <31 ; i++) {
            map.put(getSchemeCalendar(year, month, i, 0xFFbc13f0, "假").toString(),
                    getSchemeCalendar(year, month, i, 0xFFbc13f0, "假"));

        }
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);


    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }
}
