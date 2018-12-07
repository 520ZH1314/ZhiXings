package com.shuben.zhixing.www.calendars;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.adapter.NewMissionAdapter;
import com.shuben.zhixing.www.data.NotificationData;
import com.shuben.zhixing.www.util.DataChangeUtil;
import com.shuben.zhixing.www.util.ScrollListview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.XmlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ldf on 16/11/4.
 */

public class SyllabusActivity extends BaseActvity {
    //为什么不使用ButterKnife，是不想让用户看到源码是产生疑问
    private static String TAG="SyllabusActivity";
    TextView textViewYearDisplay;
    TextView textViewMonthDisplay;
    TextView backToday;
    CoordinatorLayout content;
    MonthPager monthPager;
//    RecyclerView rvToDoList;
    TextView scrollSwitch;
    TextView themeSwitch;
    TextView nextMonthBtn;
    TextView lastMonthBtn;
    private  HashMap<String, String> markData=new HashMap<>();
    private List<String> datas = new ArrayList<>();;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private Context context;
    private CalendarDate currentDate;
    private boolean initiated = false;
    private RequestQueue mQueue;


    private NewMissionAdapter adapter;

    private ListView rili_list;

    /*当日控件*/
    private TextView calendar_title,tetle_text;
    private ImageView tetle_back;

    //假数据
    private List<NotificationData> data;

    @Override
    public int getLayoutId() {
        return R.layout.calendar_activity_syllabus;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {

       setStatus(-1);
        data = getdate();


        context = this;
        content = (CoordinatorLayout) findViewById(R.id.content);
        monthPager = (MonthPager) findViewById(R.id.calendar_view);
        //此处强行setViewHeight，毕竟你知道你的日历牌的高度
        monthPager.setViewheight(Utils.dpi2px(context, 270));
        textViewYearDisplay = (TextView) findViewById(R.id.show_year_view);
        textViewMonthDisplay = (TextView) findViewById(R.id.show_month_view);
        backToday = (TextView) findViewById(R.id.back_today_button);
        scrollSwitch = (TextView) findViewById(R.id.scroll_switch);
        themeSwitch = (TextView) findViewById(R.id.theme_switch);
        nextMonthBtn = (TextView) findViewById(R.id.next_month);
        lastMonthBtn = (TextView) findViewById(R.id.last_month);

        rili_list = (ListView) findViewById(R.id.rili_list);
        adapter = new NewMissionAdapter(SyllabusActivity.this, data);
        rili_list.setAdapter(adapter);

        //今天是否有任务
        calendar_title = (TextView) findViewById(R.id.calendar_title);
        tetle_back = (ImageView) findViewById(R.id.tetle_back);
        tetle_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tetle_text = (TextView) findViewById(R.id.tetle_text);
        tetle_text.setText("我的行程");
        mQueue = Volley.newRequestQueue(this);
        initCurrentDate();
        initCalendarView();
        initToolbarClickListener();
    }

    private List<NotificationData> getdate() {
       data = new ArrayList<>();

        for (int i = 0;i<3;i++){
            NotificationData  nd = new NotificationData();
            nd.setTitle("我的任务"+i);
            nd.setTaskNo("2017822"+i);
            nd.setCreateDept("产品部");
            nd.setSource(1);
            nd.setCreateUser("李聪老师");
            nd.setCreateDate("2017-8-22");
            data.add(nd);
        }
        return  data;
    }

    /**
     * onWindowFocusChanged回调时，将当前月的种子日期修改为今天
     *
     * @return void
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !initiated) {
            refreshMonthPager();
            initiated = true;
        }
    }

    /**
     * 初始化对应功能的listener
     *
     * @return void
     */
    private void initToolbarClickListener() {
        backToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackToDayBtn();

                Log.i("Text","11111111111111111111111111111111111111111");
            }
        });


        themeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当天的任务
            }
        });

        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
            }
        });
        lastMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
            }
        });
    }

    /**
     * 初始化currentDate
     *
     * @return void
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        textViewYearDisplay.setText(currentDate.getYear() + "年");
        textViewMonthDisplay.setText(currentDate.getMonth() + "");
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     *
     * @return void
     */
    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(context, R.layout.calendar_custom_day);
        calendarAdapter = new CalendarViewAdapter(
                context,
                onSelectDateListener,
                CalendarAttr.CalendayType.MONTH,
                customDayView);
        initMarkData();
        initMonthPager();
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     *
     * @return void
     */
    private void initMarkData() {
        // TODO Auto-generated method stub
        String Url = UrlUtil.GetMyCalendarUrl(UrlUtil.IP, UrlUtil.GetMyCalendar, 1, 20, SharedPreferencesTool.getMStool(SyllabusActivity.this).getUserId());
        Log.e("获取任务日历列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);
                        //markData.clear();
                        //datas.clear();
                        try {
                            JSONObject jsonObject;
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);
                                String TaskNo = jsonObject.getString("TaskNo");//任务编号
                                String Title = jsonObject.getString("Title");//任务内容
                                String DueDate = jsonObject.getString("DueDate");//任务日期
                                if(DueDate.length()>=10){
                                    DueDate=DueDate.substring(0,10);
                                    Log.e("DueDate",DueDate);
                                }
                                String Source= DataChangeUtil.getInstance().getSource(jsonObject.getInt("Source"));//任务来源
                                 markData.put(DueDate, Title);

                                 datas.add(DueDate);

                            }
                            /* markData.clear();

                            markData.put("2017-8-9", "1");
                            markData.put("2017-7-9", "0");
                            markData.put("2017-8-16", "1");
                            markData.put("2017-6-10", "0");*/
                            Log.e("markData",markData.toString());
                            calendarAdapter.setMarkData(markData);

                          /*  //下面列表记录接口返回的日期
                            datas = new ArrayList<>();
                            datas.add("2017-8-9");
                            datas.add("2017-7-9");
                            datas.add("2017-8-16");
                            datas.add("2017-6-10");*/

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);



       /* markData.clear();

        markData.put("2017-8-9", "1");
        markData.put("2017-7-9", "0");
        markData.put("2017-8-16", "1");
        markData.put("2017-6-10", "0");
        calendarAdapter.setMarkData(markData);

        //下面列表记录接口返回的日期
        datas = new ArrayList<>();
        datas.add("2017-8-9");
        datas.add("2017-7-9");
        datas.add("2017-8-16");
        datas.add("2017-6-10");*/
    }

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
                //通过data跟接口获取的数据对比，即可获得今天时候以后任务安排


                if (datas.toString().contains(date.toString())){
                    adapter = new NewMissionAdapter(SyllabusActivity.this, data);
                    rili_list.setAdapter(adapter);
                    new ScrollListview(rili_list);
                    Log.i("Text","11111111111111111111111111111111111111111"+datas.toString()+"777777777777:"+date);
                    rili_list.setVisibility(View.VISIBLE);
                    calendar_title.setVisibility(View.GONE);
                }else {
                    rili_list.setVisibility(View.GONE);
                    calendar_title.setVisibility(View.VISIBLE);
                    calendar_title.setText("没任务");
                    Log.i("Text","2222222222222222222222222222222222222222222222222"+datas.toString()+"66666666:"+date);
                }
            }
            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };
    }

    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        textViewYearDisplay.setText(date.getYear() + "年");
        textViewMonthDisplay.setText(date.getMonth() + "");
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) instanceof Calendar) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    textViewYearDisplay.setText(date.getYear() + "年");
                    textViewMonthDisplay.setText(date.getMonth() + "");
                    Log.i("Text","0000000000000000000000000000000000000000");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void onClickBackToDayBtn() {
        refreshMonthPager();
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        textViewYearDisplay.setText(today.getYear() + "年");
        textViewMonthDisplay.setText(today.getMonth() + "");
    }

}

