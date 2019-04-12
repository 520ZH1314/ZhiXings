package com.zhixing.employlib.ui.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.model.MonthViewBean;
import com.zhixing.employlib.model.StandScore;
import com.zhixing.employlib.repertory.MonthRepertory;
import com.zhixing.employlib.view.DialogFragmentIntergralEvent;
import com.zhixing.employlib.viewmodel.activity.AppealPersonViewModel;
import com.zhixing.employlib.viewmodel.activity.MonthViewModel;
import com.zhixing.employlib.viewmodel.fragment.PerFormanceViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 绩效月视图
 */
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
    private PerFormanceViewModel perFormanceViewModel;
    TextView tetle_text;

    @Override
    public int getLayoutId() {
        return R.layout.activity_moth_integral_event;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        setStatus(-1);
        tempScores = (List<StandScore>) getIntent().getSerializableExtra("obj");
        monthViewModel = ViewModelProviders.of(this).get(MonthViewModel.class);
        perFormanceViewModel = ViewModelProviders.of(this).get(PerFormanceViewModel.class);
        initView();



    }

    @SuppressLint("SetTextI18n")
    private MonthViewModel monthViewModel;
    protected void initView() {
        tetle_text = findViewById(R.id.tetle_text);
        tetle_text.setText("个人月视图");
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
       /* mRelativeTool.setOnClickListener((b)->{
            int currentMonth = mCalendarView.getCurMonth();
            P.c("当前月"+currentMonth+"=="+mCalendarView.getSelectedCalendar().getMonth());
        });*/


    }
    List<StandScore> tempScores = new ArrayList<>();
    private String getCV(int score, List<StandScore> standScores){
                for(int i=0;i<standScores.size();i++){
                    StandScore s = standScores.get(i);
                    if(s.getIsEnable().equals("1")){
                       if(s.getSymbol().equals(">")&&score>s.getMaxScore()){

                           return s.getGrapeColorValue();
                       }
                       if(s.getSymbol().equals("=")&&score==s.getMaxScore()){

                           return s.getGrapeColorValue();
                       }
                       if(s.getSymbol().equals("~")&&(s.getMinScore()<score&&s.getMaxScore()>score)){
                           return s.getGrapeColorValue();
                       }

                       if(s.getSymbol().equals("<")&&score<s.getMaxScore()){
                           return s.getGrapeColorValue();
                       }
                    }
                }
                return "#00000000";
    }

    private void connet(){
        int currentMonth = mCalendarView.getCurMonth();
        Calendar calendar =  mCalendarView.getSelectedCalendar();
        int viewMonth = calendar.getMonth();//视图月
        int viewYear = calendar.getYear();


        Map map  =new HashMap();
        map.put("StartDate",TimeUtil.getFirstDayOfMonth1(viewYear,viewMonth));
        if(viewMonth!=currentMonth){

            map.put("EndDate", TimeUtil.getLastDayOfMonth1(viewYear,viewMonth));
        }else{
            //当天
            map.put("EndDate",mCalendarView.getCurYear()+"-"+mCalendarView.getCurMonth()+"-"+mCalendarView.getCurDay());
        }
        map.put("UserCode", SharedPreferencesTool.getMStool(MothIntegralEventActivity.this).getUserCode());
        monthViewModel.getMonthViews(map).observe(this, new Observer<List<MonthViewBean>>() {
            @Override
            public void onChanged(@Nullable List<MonthViewBean> monthViewBeans) {
                mCalendarView.clearSchemeDate();
//
                Map month = new HashMap();
               for(int i=0;i<monthViewBeans.size();i++){

                   Date date = new Date(TimeUtil.parseTime_y(monthViewBeans.get(i).getEventDate()));

                   Calendar c = getSchemeCalendar(Integer.parseInt(TimeUtil.getYear(date)),Integer.parseInt(TimeUtil.getMonth(date)),Integer.parseInt(TimeUtil.getDay(date)), Color.parseColor(getCV(monthViewBeans.get(i).getScore(),tempScores)),"");
                   month.put(c.toString(),c);
               }

                mCalendarView.setSchemeDate(month);
                mCalendarView.update();
            }
        });

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
        P.c("calendar"+calendar.getMonth());
    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        P.c("isClick"+isClick);
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
//        initData();

        if(isClick){
            String time = calendar.getYear()+"-"+calendar.getMonth()+"-"+calendar.getDay();
            perFormanceViewModel.getTime(time);
            DialogFragmentIntergralEvent dialogFragmentIntergralEvent = new DialogFragmentIntergralEvent(time);
            dialogFragmentIntergralEvent.show(getSupportFragmentManager(), "");
        }else{
            connet();
        }
    }
}
