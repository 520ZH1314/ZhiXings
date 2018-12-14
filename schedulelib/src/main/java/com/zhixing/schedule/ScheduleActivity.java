package com.zhixing.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android.volley.VolleyError;
import com.base.zhixing.www.BaseApp;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.githang.statusbar.StatusBarCompat;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.zhixing.schedule.adapter.Article;
import com.zhixing.schedule.adapter.ArticleAdapter;
import com.zhixing.schedule.common.ARouterContants;
import com.zhixing.schedule.group.GroupItemDecoration;
import com.zhixing.schedule.group.GroupRecyclerView;
import com.base.zhixing.www.BaseActvity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 日程系统
 * @author cloor
 */
@Route(path = "/schedulelib/ScheduleActivity")
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
        articleAdapter = new ArticleAdapter(this,adapterMap,titles);
        mRecyclerView.setAdapter(articleAdapter);
        mRecyclerView.notifyDataSetChanged();

        articleAdapter.setOnItemClick(pos -> {
            String type = pos.getTypeName();
            switch (type){
                case "任务交办":
                        //进入详情
                   /* Intent intent =new Intent();
                   // intent.setPackage("com.zhixing.work");
                    intent.setAction("com.zhixing.work.outer.detail");
                    intent.putExtra("TaskId",pos.getStuId());
                    intent.putExtra("name",type);
                    intent.putExtra("ApiCode","GetUnfinishedTask");
                    startActivity(intent);*/

                    ARouter.getInstance().build(ARouterContants.WorkTaskDetailActivity)
                            .withString("TaskId",pos.getStuId())
                            .withString("name",type)
                            .withString("ApiCode","GetUnfinishedTask")
                            .navigation();

                    break;
                case "高效会议":
                    Intent intent1 =new Intent();
                    // intent.setPackage("com.zhixing.work");
                    intent1.setAction("com.zhixing.meet.outer.detail");
                    intent1.putExtra("meetingID",pos.getStuId());
                    intent1.putExtra("meetingDataID",pos.getStuId());
                    startActivity(intent1);
                    break;
            }
        });
    }
    private ArticleAdapter articleAdapter;
    private LinkedHashMap<String, List<Article>> adapterMap = new LinkedHashMap<>();
    private ArrayList<String> titles = new ArrayList<>();
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
          //  P.c("点击了日期"+TimeUtil.getTimeCh(calendar.getTimeInMillis()));

            load(TimeUtil.getTimeCh(calendar.getTimeInMillis()));

            java.util.Calendar calendar1 = java.util.Calendar.getInstance();

            calendar1.setTime(new Date(calendar.getTimeInMillis()));

            loadAllStatus(getCurrent(calendar1));
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

        }
    };
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
       // calendar.addScheme(new Calendar.Scheme());
//        calendar.addScheme(0xFF008800, "假");
//        calendar.addScheme(0xFF008800, "节");
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

    /**
     * 加载所有状态日期状态情况
     * @param prts
     */
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
                       String count = o.getString("Count");
                        Calendar c = getSchemeCalendar(Integer.parseInt(prts[0]),Integer.parseInt(prts[1]),Integer.parseInt(TimeUtil.parseTime_day(temp)),0xFFdf1356,count);
                       map.put(c.toString(),c);
                    }
                    mCalendarView.clearSchemeDate();
                    mCalendarView.setSchemeDate(map);
                    mCalendarView.update();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        });
    }

    /**
     * 加载每日的数据信息列表
     * @param StartDate
     */
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
                adapterMap.clear();
                titles.clear();
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                   // P.c("数据长度"+jsonArray.length());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        Article article = new Article();
                        article.setStuId(object.getString("ScheduleSourceID"));
                        article.setTitle(object.getString("ScheduleContent"));
                        article.setContent(object.getString("ExecutorName"));
                        article.setDesc(object.getString("ScheduleRemark"));
                        String tag = object.getString("SourceModel");
                        article.setTypeName(tag);
                        //数据分类和组装
                        if(adapterMap.containsKey(tag)){
                            //if exits
                            adapterMap.get(tag).add(article);
                         //   P.c(tag+"----"+article.getContent()+"---"+i);
                        }else{
                            List<Article> list = new ArrayList<>();
                            list.add(article);
                            adapterMap.put(tag,list);
                            titles.add(tag);
                         //   P.c(tag+"==="+article.getContent()+"---"+i);
                        }


                    }
                 /*Set set =  adapterMap.keySet();
                 Iterator it =  set.iterator();
                 while(it.hasNext()){
                     String key = it.next().toString();
                     P.c("长度"+adapterMap.get(key).size());
                     titles.add(key);
                 }*/
                 P.c(titles.toString());
                    articleAdapter.updata(adapterMap,titles);
                    mRecyclerView.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void error(VolleyError error) {

            }
        });
    }
    private static Article create(String title, String content, String imgUrl) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setImgUrl(imgUrl);
        return article;
    }
}
