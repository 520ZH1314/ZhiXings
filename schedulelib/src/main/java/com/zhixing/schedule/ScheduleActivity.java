package com.zhixing.schedule;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.githang.statusbar.StatusBarCompat;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.zhixing.schedule.adapter.Article;
import com.zhixing.schedule.adapter.ArticleAdapter;
import com.zhixing.schedule.group.GroupItemDecoration;
import com.zhixing.schedule.group.GroupRecyclerView;
import com.base.zhixing.www.BaseActvity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日程系统
 * @author cloor
 */
public class ScheduleActivity extends BaseActvity {
    @Override
    public void process(Message msg) {

    }
    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;
    GroupRecyclerView mRecyclerView;
    @Override
    public void initLayout() {
        setStatus(-1);
        mTextMonthDay =   findViewById(R.id.tv_month_day);
        mTextYear =  findViewById(R.id.tv_year);
        mTextLunar =   findViewById(R.id.tv_lunar);
        mRelativeTool =  findViewById(R.id.rl_tool);
        mCalendarView =   findViewById(R.id.calendarView);
        mTextCurrentDay = findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarView.showYearSelectLayout(mYear);
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout =   findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(selectListener);
        mCalendarView.setOnYearChangeListener(changeListener);
        mCalendarView.setOnMonthChangeListener(onMonthChangeListener);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        initData();
    }
    Map<String, Calendar> map = new HashMap<>();
    private void initData(){
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

/*
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFFe69138, "你"));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
   */
        mRecyclerView = (GroupRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new GroupItemDecoration<String, Article>());
        mRecyclerView.setAdapter(new ArticleAdapter(this));
        mRecyclerView.notifyDataSetChanged();
    }

    @Override
    public int getLayoutId() {
        return R.layout.schedule_layout;
    }



    private CalendarView.OnCalendarSelectListener selectListener = new CalendarView.OnCalendarSelectListener() {
        @Override
        public void onCalendarOutOfRange(Calendar calendar) {
                P.c("这是什么");
        }

        @Override
        public void onCalendarSelect(Calendar calendar, boolean isClick) {
            mTextLunar.setVisibility(View.VISIBLE);
            mTextYear.setVisibility(View.VISIBLE);
            mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
            mTextYear.setText(String.valueOf(calendar.getYear()));
            mTextLunar.setText(calendar.getLunar());
            mYear = calendar.getYear();
            P.c("点击了日期"+TimeUtil.getTimeCh(calendar.getTimeInMillis()));

            load(TimeUtil.getTimeCh(calendar.getTimeInMillis()));

        }
    };
    private CalendarView.OnYearChangeListener changeListener = new CalendarView.OnYearChangeListener() {
        @Override
        public void onYearChange(int year) {
            mTextMonthDay.setText(String.valueOf(year));
        }
    };
    private CalendarView.OnMonthChangeListener onMonthChangeListener = new CalendarView.OnMonthChangeListener() {
        @Override
        public void onMonthChange(int year, int month) {
            java.util.Calendar calendar1 = java.util.Calendar.getInstance();

            calendar1.setTime(new Date(mCalendarView.getSelectedCalendar().getTimeInMillis()));

            loadAllStatus(getCurrent(calendar1));
        }
    };
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    private String [] getCurrent(java.util.Calendar cal){

        // 当前月+1,即下个月
        cal.add(cal.MONTH, 1); //得到下个月的月份
        // 将下个月1号作为日期初始zhii
        cal.set(cal.DATE, 1);
        // 下个月1号减去一天,即得到当前月最后一天
        cal.add(cal.DATE, -1);

        String day_end = TimeUtil.getDateByDate(cal.getTime());
//        java.util.Calendar c = java.util.Calendar.getInstance();
        cal.set(cal.DATE, 1);
        String day_start = TimeUtil.getDateByDate(cal.getTime());

        return  new String[]{TimeUtil.getYear(cal.getTime()),TimeUtil.getMonth(cal.getTime()),day_start,day_end};
    }

    private void loadAllStatus(final String prts[]){
        final Map<String,String> params = new HashMap<>();
        params.put("AppCode","CEOAssist");
        params.put("ApiCode","GetCalendar");
        params.put("SystemCurrentUserID",SharedPreferencesTool.getMStool(ScheduleActivity.this).getUserId());
        params.put("DateRange_Start",prts[2]);
        params.put("DateRange_End",prts[3]);
        params.put("TenantId",SharedPreferencesTool.getMStool(ScheduleActivity.this).getTenantId());
        httpPostSONVolley(SharedPreferencesTool.getMStool(ScheduleActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                map.clear();
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                       String temp =  o.getString("CalendarDate");
                        Calendar c = getSchemeCalendar(Integer.parseInt(prts[0]),Integer.parseInt(prts[1]),Integer.parseInt(TimeUtil.parseTime_day(temp)),0xFFdf1356,"");
                       map.put(c.toString(),c);

                    }
                    mCalendarView.setSchemeDate(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        });
    }


    private void load(String StartDate){
        Map<String,String> params = new HashMap<>();
        params.put("AppCode","CEOAssist");
        params.put("ApiCode","GetSchedule");
        params.put("SystemCurrentUserID",SharedPreferencesTool.getMStool(ScheduleActivity.this).getUserId());
        params.put("StartDate",StartDate);
        params.put("TenantId",SharedPreferencesTool.getMStool(ScheduleActivity.this).getTenantId());
        httpPostSONVolley(SharedPreferencesTool.getMStool(ScheduleActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {

            }
            @Override
            public void error(VolleyError error) {

            }
        });
    }
}
