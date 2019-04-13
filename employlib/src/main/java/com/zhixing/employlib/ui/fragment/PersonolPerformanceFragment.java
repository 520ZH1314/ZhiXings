package com.zhixing.employlib.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeTime;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.NoticeBean;
import com.zhixing.employlib.model.StandScore;
import com.zhixing.employlib.model.eventbus.UpdateEmployeeEvent;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.model.performance.TotalMonthPerformanceBean;
import com.zhixing.employlib.ui.activity.AppealActivity;
import com.zhixing.employlib.ui.activity.AppealListActivity;
import com.zhixing.employlib.ui.activity.GradingActivity;
import com.zhixing.employlib.ui.activity.GradingRecordListActivity;
import com.zhixing.employlib.ui.activity.MothIntegralEventActivity;
import com.zhixing.employlib.utils.RelativeDateFormat;
import com.zhixing.employlib.view.DialogFragmentIntergralEvent;
import com.zhixing.employlib.view.DialogFragmentPersonTest;
import com.zhixing.employlib.viewmodel.activity.MonthViewModel;
import com.zhixing.employlib.viewmodel.fragment.PerFormanceViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * @author zjq
 * create at 2019/3/6 下午4:27
 * 绩效界面
 */
public class PersonolPerformanceFragment extends BaseLazyFragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {


    @BindView(R2.id.segmented2)
    SegmentedGroup segmented2;
    @BindView(R2.id.tv_time_year)
    TextView tvTimeYear;
    @BindView(R2.id.tv_time_date)
    TextView tvTimeDate;
    @BindView(R2.id.tv_look)
    TextView tvLook;
    @BindView(R2.id.tv_integral)
    TextView tvIntegral;
    @BindView(R2.id.tv_rank)
    TextView tvRank;
    @BindView(R2.id.tv_name)
    TextView tvName;
    @BindView(R2.id.tv_name_excellent1)
    TextView tvNameExcellent1;
    @BindView(R2.id.tv_name_excellent_integral1)
    TextView tvNameExcellentIntegral1;
    @BindView(R2.id.tv_name_excellent_integral2)
    TextView tvNameExcellentIntegral2;
    @BindView(R2.id.tv_name_excellent_integral3)
    TextView tvNameExcellentIntegral3;
    @BindView(R2.id.tv_name_excellent2)
    TextView tvNameExcellent2;
    @BindView(R2.id.tv_name_excellent3)
    TextView tvNameExcellent3;
    @BindView(R2.id.tv_backward_name1)
    TextView tvBackwardName1;
    @BindView(R2.id.tv_backward_name2)
    TextView tvBackwardName2;
    @BindView(R2.id.tv_backward_name3)
    TextView tvBackwardName3;
    @BindView(R2.id.tv_backward_integral1)
    TextView tvBackwardIntegral1;
    @BindView(R2.id.tv_backward_integral2)
    TextView tvBackwardIntegral2;
    @BindView(R2.id.tv_backward_integral3)
    TextView tvBackwardIntegral3;
    @BindView(R2.id.tv_backward_rank1)
    TextView tvBackwardRank1;
    @BindView(R2.id.tv_backward_rank2)
    TextView tvBackwardRank2;
    @BindView(R2.id.tv_backward_rank3)
    TextView tvBackwardRank3;
    Unbinder unbinder;
    @BindView(R2.id.textView5)//积分
            TextView textView5;
    @BindView(R2.id.textView7)//排名
            TextView textView7;
    @BindView(R2.id.radButton_person1)
    RadioButton radButtonPerson1;
    @BindView(R2.id.radButton_person2)
    RadioButton radButtonPerson2;
    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R2.id.notice_layout0)
    LinearLayout notice_layout0;

    @BindView(R2.id.notice_layout1)
    LinearLayout notice_layout1;

    @BindView(R2.id.notice0)
    TextView notice0;
    @BindView(R2.id.notice1)
    TextView notice1;

    @BindView(R2.id.notice0_t)
    TextView notice0_t;
    @BindView(R2.id.notice1_t)
    TextView notice1_t;



    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfabHelper;

    private int index = 0;
    private String[] splitDay;
    private String[] splitYear;

    private String Year;
    private String Month;
    private String Day;
    private String Years;
    private String Months;
    private String Days;
    private PerFormanceViewModel perFormanceViewModel;
    private SharedUtils sharedUtils;
    private boolean booleanValue;
    private String teamId;
    private String commonTime1;
    public List<TotalMonthPerformanceBean.ReturndayInfoBean.UserInfoBean> userDatas ;//昨日绩效
    public List<TotalMonthPerformanceBean.ReturndayInfoBean.TeamInfoBean> teamDatas ;//昨日绩效

    public List<MonthPerformanceBean.ReturndayInfoBean.UserInfoBean> userMonthData; //昨日绩效
    public List<MonthPerformanceBean.ReturndayInfoBean.TeamInfoBean> teamMonthData ;//昨日绩效
    private ACache aCache;
    private  MonthViewModel monthViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personol_performance, container, false);
        unbinder = ButterKnife.bind(this, view);
         aCache = ACache.get(getActivity(),"Performance");

          monthViewModel = ViewModelProviders.of(getActivity()).get(MonthViewModel.class);
          perFormanceViewModel = ViewModelProviders.of(getActivity()).get(PerFormanceViewModel.class);
          rfaLayout = (RapidFloatingActionLayout) view.findViewById(R.id.label_list_sample_rfal);
          rfaButton = (RapidFloatingActionButton) view.findViewById(R.id.label_list_sample_rfab);
        return view;
    }



    @Override
    public void initData() {
        initViews();
        initDatas();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    protected void onVisible() {
        super.onInvisible();

        getAllNotices();
    }

    private void initDatas() {
//        showDialog("");
        //是否是领导
        teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);
        booleanValue = sharedUtils.getBooleanValue(PerformanceApi.ISTEAMLEADER);
        if (teamId != null) {
            //初始化悬浮按钮
            initFloatingActionButton(booleanValue);
        } else {
            return;
        }

        //初始化个人和班组的昨日绩效
        perFormanceViewModel.setYesterdayTime(commonTime1);


        perFormanceViewModel.YesDayData.observe(getActivity(), totalMonthPerformanceBeans -> {
            if (totalMonthPerformanceBeans != null) {


                List<TotalMonthPerformanceBean.ReturndayInfoBean.TeamInfoBean> teamInfo = totalMonthPerformanceBeans.getRows().get(0).getReturndayInfo().getTeamInfo();
                List<TotalMonthPerformanceBean.ReturndayInfoBean.UserInfoBean> userInfo = totalMonthPerformanceBeans.getRows().get(0).getReturndayInfo().getUserInfo();

                  String teamInfoStr = GsonUtil.getGson().toJson(teamInfo);
                  String userInfoStr = GsonUtil.getGson().toJson(userInfo);
                  aCache.put("DayUserInfo",userInfoStr);
                  aCache.put("DayTeamInfo",teamInfoStr);

                  P.c(userInfoStr);
                  setYesDayData();
                //初始化月绩效


            }else{
                aCache.remove("DayUserInfo") ;
                aCache.remove("DayTeamInfo") ;
                Toasty.INSTANCE.showToast(getActivity(),"暂无新的数据");
                dismissDialog();

            }
            EventBus.getDefault().post(new UpdateEmployeeEvent("7"));

        });
        perFormanceViewModel.setMonthTime(Years+"-"+Months+"-"+"01");
        perFormanceViewModel.MonthData.observe(getActivity(),  beans ->{
            if (beans != null) {
                //强转问题
                List<MonthPerformanceBean.ReturndayInfoBean.TeamInfoBean> teamInfo1 = beans.getRows().get(0).getReturndayInfo().getTeamInfo();
                List<MonthPerformanceBean.ReturndayInfoBean.UserInfoBean> userInfo1 = beans.getRows().get(0).getReturndayInfo().getUserInfo();


                String teamInfoStr1 = GsonUtil.getGson().toJson(teamInfo1);
                String userInfoStr1 = GsonUtil.getGson().toJson(userInfo1);
                
                aCache.put("MonthUserInfo",userInfoStr1);
                aCache.put("MonthTeamInfo",teamInfoStr1);
                if (index==1){
                    setMonthDayData();
                }


                dismissDialog();

            }else{
                aCache.remove("MonthUserInfo") ;
                aCache.remove("MonthTeamInfo") ;
                Toasty.INSTANCE.showToast(getActivity(),"暂无新的数据");
                dismissDialog();
            }

        });
        getAllNotices();
    }


    private void getAllNotices(){
    Map map = new HashMap();
    map.put("Index","0");
    map.put("PageSize","2");
    if(monthViewModel!=null)
    monthViewModel.getNotices(map).observe(this, new Observer<List<NoticeBean>>() {
        @Override
        public void onChanged(@Nullable List<NoticeBean> noticeBeans) {
                if(noticeBeans!=null){
                   if(noticeBeans.size()==1){
                       notice_layout1.setVisibility(View.INVISIBLE);
                       notice0.setText(noticeBeans.get(0).getNoticeTitle());
                       notice0_t.setText(RelativeDateFormat.format(TimeUtil.parseTimeDate( noticeBeans.get(0).getCreateTime())));

                   }else if(noticeBeans.size()>=2){
                       notice0.setText(noticeBeans.get(0).getNoticeTitle());
                       notice0_t.setText(RelativeDateFormat.format(TimeUtil.parseTimeDate( noticeBeans.get(0).getCreateTime())));
                       notice1.setText(noticeBeans.get(1).getNoticeTitle());
                       notice1_t.setText(RelativeDateFormat.format(TimeUtil.parseTimeDate( noticeBeans.get(1).getCreateTime())));
                   }else {
                       relativeLayout.setVisibility(View.GONE);
                   }
                }
            }
        });
    }








    private void initViews() {
        sharedUtils = new SharedUtils(PerformanceApi.FLIESNAME);
        //设置文字加粗
        TextPaint tp = tvTimeDate.getPaint();
        tp.setFakeBoldText(true);

        TextPaint tp1 = tvIntegral.getPaint();
        tp1.setFakeBoldText(true);

        TextPaint tp2 = tvRank.getPaint();
        tp2.setFakeBoldText(true);

        ivWorkAddWork.setVisibility(View.GONE);
        tvWorkTitle.setText("绩效");
        String stringValue = sharedUtils.getStringValue(PerformanceApi.TEAMNAME);
        tvWorkSend.setText(stringValue);
        //设置前一日的时间
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //日减1
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(lastDay);
        commonTime1 = TimeUtil.getCommonTime1(format);
        String[] split = commonTime1.split("-");
        Year = split[0];
        Month = split[1];
        Day = split[2];
        Years = split[0];
        Months = split[1];
        Days = split[2];

        if (index == 0) {
            tvTimeYear.setText(Year + "年" + Month + "月");
            tvTimeDate.setText(Day + "日");
        } else if (index == 1) {
            tvTimeYear.setText(Years + "年");
            tvTimeDate.setText(Months + "月");
        }


    }


    private void initFloatingActionButton(Boolean booleanValue) {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getActivity());
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();


        if (booleanValue) {
            items.add(new RFACLabelItem<Integer>()
                    .setLabel("处理申诉")
                    .setResId(R.drawable.shensu)
                    .setIconNormalColor(Color.parseColor("#3F9E43"))
                    .setIconPressedColor(0xff0d5302)
                    .setLabelColor(0xff056f00)
                    .setWrapper(1)
            );
            items.add(new RFACLabelItem<Integer>()
                    .setLabel("我要评分")
                    .setResId(R.drawable.pinf)
                    .setIconNormalColor(Color.parseColor("#1F6DC5"))
                    .setIconPressedColor(0xff1a237e)
                    .setLabelColor(0xff283593)
                    .setWrapper(2)
            );
            items.add(new RFACLabelItem<Integer>()
                    .setLabel("评分记录")
                    .setResId(R.drawable.pinf)
                    .setIconNormalColor(Color.parseColor("#1F6DC5"))
                    .setIconPressedColor(0xff1a237e)
                    .setLabelColor(0xff283593)
                    .setWrapper(2)
            );
        } else {
            items.add(new RFACLabelItem<Integer>()
                    .setLabel("我要申诉")
                    .setResId(R.drawable.shensu1)
                    .setIconNormalColor(Color.parseColor("#EE8626"))
                    .setIconPressedColor(0xffbf360c)
                    .setWrapper(0)
            );
        }

        rfaContent
                .setItems(items)
                .setIconShadowRadius(RFABTextUtil.dip2px(getActivity(), 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(RFABTextUtil.dip2px(getActivity(), 5))
        ;

        rfabHelper = new RapidFloatingActionHelper(
                getActivity(),
                rfaLayout,
                rfaButton,
                rfaContent
        ).build();
    }

    @Override
    public void process(Message msg) {

    }


    /**
     * @author zjq
     * create at 2019/3/11 下午3:17
     * <p>
     * 悬浮按钮点击事件 label 和 icon
     */

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        if (position == 0) {
            if ("我要申诉".equals(item.getLabel().toString())) {
                Intent intent = new Intent(getActivity(), AppealActivity.class);
                startActivity(intent);
                rfabHelper.toggleContent();
            } else {
                //处理申诉
                ChangeTime changeTime = new ChangeTime(getActivity(), "", 2);
                changeTime.setPastCanendar(1);
                changeTime.setSelect(new SelectTime() {
                    @Override
                    public void select(String time, long timestp) {
                        Intent intent = new Intent(getActivity(),AppealListActivity.class);
                        intent.putExtra("CreateTime",TimeUtil.getTimeCh(timestp));
                        startActivity(intent);

                        rfabHelper.toggleContent();
                    }
                });
                changeTime.showSheet();

            }
        } else if (position == 1) {

            Intent intent = new Intent(getActivity(), GradingActivity.class);
            startActivity(intent);
            rfabHelper.toggleContent();
        } else if (position == 2) {
            Intent intent = new Intent(getActivity(), GradingRecordListActivity.class);
            startActivity(intent);
            rfabHelper.toggleContent();
        }

    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        if (position == 0) {
            if ("我要申诉".equals(item.getLabel().toString())) {
                Intent intent = new Intent(getActivity(), AppealActivity.class);
                startActivity(intent);
                rfabHelper.toggleContent();
            } else {
                //处理申诉
//                Intent intent = new Intent(getActivity(), AppealListActivity.class);
//                startActivity(intent);
//                rfabHelper.toggleContent();
                ChangeTime changeTime = new ChangeTime(getActivity(), "", 2);
                changeTime.setPastCanendar(1);
                changeTime.setSelect(new SelectTime() {
                    @Override
                    public void select(String time, long timestp) {
                        Intent intent = new Intent(getActivity(),AppealListActivity.class);
                        intent.putExtra("CreateTime",TimeUtil.getTimeCh(timestp));
                        startActivity(intent);

                        rfabHelper.toggleContent();
                    }
                });
                changeTime.showSheet();


            }
        } else if (position == 1) {

            Intent intent = new Intent(getActivity(), GradingActivity.class);
            startActivity(intent);
            rfabHelper.toggleContent();
        } else if (position == 2) {
            Intent intent = new Intent(getActivity(), GradingRecordListActivity.class);
            startActivity(intent);
            rfabHelper.toggleContent();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R2.id.tv_time_date, R2.id.tv_look, R2.id.tv_name_excellent_integral1,
            R2.id.tv_name_excellent_integral2,
            R2.id.tv_name_excellent_integral3,
            R2.id.tv_backward_integral1, R2.id.tv_backward_integral2, R2.id.tv_integral,
            R2.id.tv_backward_integral3, R2.id.radButton_person1,
            R2.id.radButton_person2, R2.id.iv_work_add_work, R2.id.tv_work_title, R2.id.tv_work_send})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_time_date) {
            if (index == 0) {

                ChangeTime changeTime = new ChangeTime(getActivity(), "", 2);
                changeTime.setSelect(new SelectTime() {
                    @Override
                    public void select(String time, long timestp) {
                        String commonTime1 = TimeUtil.getCommonTime1(time);
                        splitDay = commonTime1.split("-");
                        Year = splitDay[0];
                        Month = splitDay[1];
                        Day = splitDay[2];
                        tvTimeYear.setText(Year + "年" + Month + "月");
                        tvTimeDate.setText(Day + "日");

                        perFormanceViewModel.setYesterdayTime(commonTime1);

                    }
                });
                changeTime.showSheet();

            } else if (index == 1) {
                ChangeTime changeTime = new ChangeTime(getActivity(), "", 3);
                changeTime.setSelect(new SelectTime() {
                    @Override
                    public void select(String time, long timestp) {
                        String commonTime1 = TimeUtil.getCommonTime1(time);
                        splitYear = commonTime1.split("-");
                        Years = splitYear[0];
                        Months = splitYear[1];
                        Days = splitYear[2];
                        tvTimeYear.setText(Years + "年");
                         tvTimeDate.setText(Months + "月");
                         perFormanceViewModel.setMonthTime(Years+"-"+Months+"-"+"01");

                    }
                });
                changeTime.showSheet();
            }


        } else if (i == R.id.tv_look) {

            DialogFragmentPersonTest dialogFragmentPersonTest = new DialogFragmentPersonTest();
            dialogFragmentPersonTest.show(getChildFragmentManager(), "");


        } else if (i == R.id.tv_name_excellent_integral1) {
        } else if (i == R.id.tv_name_excellent_integral2) {
        } else if (i == R.id.tv_name_excellent_integral3) {
        } else if (i == R.id.tv_backward_integral1) {
        } else if (i == R.id.tv_backward_integral2) {
        } else if (i == R.id.tv_backward_integral3) {
        } else if (i == R.id.radButton_person1) {
            textView5.setText("我的积分");
            textView7.setText("我的排名");
            tvTimeYear.setText(Year + "年" + Month + "月");
            tvTimeDate.setText(Day + "日");
            index = 0;

            setYesDayData();


        } else if (i == R.id.radButton_person2) {
            textView5.setText("累计积分");
            textView7.setText("累计积分排名");
            tvTimeYear.setText(Years + "年");
            tvTimeDate.setText(Months + "月");

            index = 1;
            setMonthDayData();

        } else if (i == R.id.tv_integral) {


            if (index == 1) {
                showDialog("加载分值信息");
                monthViewModel.getScoreColor(null).observe(this, new Observer<List<StandScore>>() {
                    @Override
                    public void onChanged(@Nullable List<StandScore> standScores) {
                        dismissDialog();
                        Intent intent = new Intent(getActivity(), MothIntegralEventActivity.class);
                        intent.putExtra("obj", (Serializable) standScores);
                        startActivity(intent);
                    }
                });


            } else {
                String time = Year+"-"+Month+"-"+Day;
                perFormanceViewModel.getTime(time);
                DialogFragmentIntergralEvent dialogFragmentIntergralEvent = new DialogFragmentIntergralEvent(time);
                dialogFragmentIntergralEvent.show(getChildFragmentManager(), "");

            }

        } else if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();

        } else if (i == R.id.tv_work_title) {

        } else if (i == R.id.tv_work_send) {

        }
    }



    public void clealData(String msg){

        tvNameExcellent1.setText(msg);
        tvNameExcellentIntegral1.setText(msg);
        tvNameExcellent2.setText(msg);
        tvNameExcellentIntegral2.setText(msg);

        tvNameExcellent3.setText(msg);
        tvNameExcellentIntegral3.setText(msg);

        tvBackwardRank1.setText("加油");
        tvBackwardIntegral1.setText(msg);
        tvBackwardName1.setText(msg);

        tvBackwardName2.setText(msg);
        tvBackwardIntegral2.setText(msg);
        tvBackwardRank2.setText("加油");

        tvBackwardName3.setText(msg);
        tvBackwardIntegral3.setText(msg);
        tvBackwardRank3.setText("加油");


    }


    //设置昨日绩效数据
    private void setYesDayData() {
        String dayUserInfo = aCache.getAsString("DayUserInfo");
        String teamInfo=aCache.getAsString("DayTeamInfo");
        Type type = new TypeToken< List<TotalMonthPerformanceBean.ReturndayInfoBean.TeamInfoBean>>() {}.getType();
        Type type2 = new TypeToken< List<TotalMonthPerformanceBean.ReturndayInfoBean.UserInfoBean>>() {}.getType();

        List<TotalMonthPerformanceBean.ReturndayInfoBean.UserInfoBean> userData = GsonUtil.getGson().fromJson(dayUserInfo, type2);
        List<TotalMonthPerformanceBean.ReturndayInfoBean.TeamInfoBean> teamData = GsonUtil.getGson().fromJson(teamInfo, type);
        Logger.i(teamData.size()+"");
        if (userData.size()==0){
            tvIntegral.setText("暂无");
            tvRank.setText("暂无");
            tvName.setText("我的上级:" + "暂无");

        }else{
            tvIntegral.setText(userData.get(0).getScore()+"");
            tvRank.setText(userData.get(0).getSeq()+"");
            tvName.setText("我的上级:" + userData.get(0).getTeamLeaderName());
        }

        if (teamData.size()==0){

            clealData("暂无");
            Toasty.INSTANCE.showToast(getActivity(),"暂无数据");

        }

        if (teamData.size()==1){

            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");

            tvNameExcellent2.setText("暂无");
            tvNameExcellentIntegral2.setText("暂无");

            tvNameExcellent3.setText("暂无");
            tvNameExcellentIntegral3.setText("暂无");


            tvBackwardIntegral1.setText("暂无");
            tvBackwardName1.setText("暂无");

            tvBackwardName2.setText("暂无");
            tvBackwardIntegral2.setText("暂无");


            tvBackwardName3.setText("暂无");
            tvBackwardIntegral3.setText("暂无");



        }else if (teamData.size()==2) {

            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");
            tvNameExcellent2.setText(teamData.get(1).getUserName());
            tvNameExcellentIntegral2.setText(teamData.get(1).getScore()+"");
            tvNameExcellent3.setText("暂无");
            tvNameExcellentIntegral3.setText("暂无");

            tvBackwardIntegral1.setText("暂无");
            tvBackwardName1.setText("暂无");

            tvBackwardName2.setText("暂无");
            tvBackwardIntegral2.setText("暂无");


            tvBackwardName3.setText("暂无");
            tvBackwardIntegral3.setText("暂无");

        }else if (teamData.size()==3) {
            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");
            tvNameExcellent2.setText(teamData.get(1).getUserName());
            tvNameExcellentIntegral2.setText(teamData.get(1).getScore()+"");
            tvNameExcellent3.setText(teamData.get(2).getUserName());
            tvNameExcellentIntegral3.setText(teamData.get(2).getScore()+"");

            tvBackwardIntegral1.setText("暂无");
            tvBackwardName1.setText("暂无");

            tvBackwardName2.setText("暂无");
            tvBackwardIntegral2.setText("暂无");


            tvBackwardName3.setText("暂无");
            tvBackwardIntegral3.setText("暂无");

        }else if (teamData.size()==4) {
            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");

            tvNameExcellent2.setText(teamData.get(1).getUserName());
            tvNameExcellentIntegral2.setText(teamData.get(1).getScore()+"");

            tvNameExcellent3.setText(teamData.get(2).getUserName());
            tvNameExcellentIntegral3.setText(teamData.get(2).getScore()+"");


            tvBackwardIntegral1.setText(teamData.get(3).getScore()+"");
            tvBackwardName1.setText(teamData.get(3).getUserName());

            tvBackwardName2.setText("暂无");
            tvBackwardIntegral2.setText("暂无");


            tvBackwardName3.setText("暂无");
            tvBackwardIntegral3.setText("暂无");


        }else if (teamData.size()==5) {
            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");

            tvNameExcellent2.setText(teamData.get(1).getUserName());
            tvNameExcellentIntegral2.setText(teamData.get(1).getScore()+"");

            tvNameExcellent3.setText(teamData.get(2).getUserName());
            tvNameExcellentIntegral3.setText(teamData.get(2).getScore()+"");


            tvBackwardIntegral1.setText(teamData.get(3).getScore()+"");
            tvBackwardName1.setText(teamData.get(3).getUserName());


            tvBackwardName2.setText(teamData.get(4).getUserName());
            tvBackwardIntegral2.setText(teamData.get(4).getScore()+"");


            tvBackwardName3.setText("暂无");

            tvBackwardIntegral3.setText("暂无");

        }else if (teamData.size()>=6) {
            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");

            tvNameExcellent2.setText(teamData.get(1).getUserName());
            tvNameExcellentIntegral2.setText(teamData.get(1).getScore()+"");

            tvNameExcellent3.setText(teamData.get(2).getUserName());
            tvNameExcellentIntegral3.setText(teamData.get(2).getScore()+"");


            tvBackwardIntegral1.setText(teamData.get(3).getScore()+"");
            tvBackwardName1.setText(teamData.get(3).getUserName());


            tvBackwardName2.setText(teamData.get(4).getUserName());
            tvBackwardIntegral2.setText(teamData.get(4).getScore()+"");

            tvBackwardIntegral3.setText(teamData.get(5).getScore()+"");
            tvBackwardName3.setText(teamData.get(5).getUserName());


        }
    }

    //设置月绩效数据
    private void setMonthDayData() {
        String MonthUserInfo = aCache.getAsString("MonthUserInfo");
        String MonthTeamInfo=aCache.getAsString("MonthTeamInfo");

        Type type = new TypeToken< List<MonthPerformanceBean.ReturndayInfoBean.TeamInfoBean>>() {}.getType();
        Type type2 = new TypeToken< List<MonthPerformanceBean.ReturndayInfoBean.UserInfoBean>>() {}.getType();
        List<MonthPerformanceBean.ReturndayInfoBean.UserInfoBean> userData = GsonUtil.getGson().fromJson(MonthUserInfo, type2);
        List<MonthPerformanceBean.ReturndayInfoBean.TeamInfoBean> teamData = GsonUtil.getGson().fromJson(MonthTeamInfo, type);



        if (userData.size()==0){
            tvIntegral.setText("暂无");
            tvRank.setText("暂无");
            tvName.setText("我的上级:" + "暂无");

        }else{
            tvIntegral.setText(userData.get(0).getScore()+"");
            tvRank.setText(userData.get(0).getSeq()+"");
            tvName.setText("我的上级:" + userData.get(0).getTeamLeaderName());
        }

        if (teamData.size()==0){

            clealData("暂无");
            Toasty.INSTANCE.showToast(getActivity(),"暂无数据");

        }

        if (teamData.size()==1){

            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");

            tvNameExcellent2.setText("暂无");
            tvNameExcellentIntegral2.setText("暂无");

            tvNameExcellent3.setText("暂无");
            tvNameExcellentIntegral3.setText("暂无");


            tvBackwardIntegral1.setText("暂无");
            tvBackwardName1.setText("暂无");

            tvBackwardName2.setText("暂无");
            tvBackwardIntegral2.setText("暂无");


            tvBackwardName3.setText("暂无");
            tvBackwardIntegral3.setText("暂无");



        }else if (teamData.size()==2) {

            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");
            tvNameExcellent2.setText(teamData.get(1).getUserName());
            tvNameExcellentIntegral2.setText(teamData.get(1).getScore()+"");
            tvNameExcellent3.setText("暂无");
            tvNameExcellentIntegral3.setText("暂无");

            tvBackwardIntegral1.setText("暂无");
            tvBackwardName1.setText("暂无");

            tvBackwardName2.setText("暂无");
            tvBackwardIntegral2.setText("暂无");


            tvBackwardName3.setText("暂无");
            tvBackwardIntegral3.setText("暂无");

        }else if (teamData.size()==3) {
            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");
            tvNameExcellent2.setText(teamData.get(1).getUserName());
            tvNameExcellentIntegral2.setText(teamData.get(1).getScore()+"");
            tvNameExcellent3.setText(teamData.get(2).getUserName());
            tvNameExcellentIntegral3.setText(teamData.get(2).getScore()+"");

            tvBackwardIntegral1.setText("暂无");
            tvBackwardName1.setText("暂无");

            tvBackwardName2.setText("暂无");
            tvBackwardIntegral2.setText("暂无");


            tvBackwardName3.setText("暂无");
            tvBackwardIntegral3.setText("暂无");

        }else if (teamData.size()==4) {
            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");

            tvNameExcellent2.setText(teamData.get(1).getUserName());
            tvNameExcellentIntegral2.setText(teamData.get(1).getScore()+"");

            tvNameExcellent3.setText(teamData.get(2).getUserName());
            tvNameExcellentIntegral3.setText(teamData.get(2).getScore()+"");


            tvBackwardIntegral1.setText(teamData.get(3).getScore()+"");
            tvBackwardName1.setText(teamData.get(3).getUserName());

            tvBackwardName2.setText("暂无");
            tvBackwardIntegral2.setText("暂无");


            tvBackwardName3.setText("暂无");
            tvBackwardIntegral3.setText("暂无");


        }else if (teamData.size()==5) {
            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");

            tvNameExcellent2.setText(teamData.get(1).getUserName());
            tvNameExcellentIntegral2.setText(teamData.get(1).getScore()+"");

            tvNameExcellent3.setText(teamData.get(2).getUserName());
            tvNameExcellentIntegral3.setText(teamData.get(2).getScore()+"");


            tvBackwardIntegral1.setText(teamData.get(3).getScore()+"");
            tvBackwardName1.setText(teamData.get(3).getUserName());


            tvBackwardName2.setText(teamData.get(4).getUserName());
            tvBackwardIntegral2.setText(teamData.get(4).getScore()+"");


            tvBackwardName3.setText("暂无");

            tvBackwardIntegral3.setText("暂无");

        }else if (teamData.size()>=6) {
            tvNameExcellent1.setText(teamData.get(0).getUserName());
            tvNameExcellentIntegral1.setText(teamData.get(0).getScore()+"");

            tvNameExcellent2.setText(teamData.get(1).getUserName());
            tvNameExcellentIntegral2.setText(teamData.get(1).getScore()+"");

            tvNameExcellent3.setText(teamData.get(2).getUserName());
            tvNameExcellentIntegral3.setText(teamData.get(2).getScore()+"");


            tvBackwardIntegral1.setText(teamData.get(3).getScore()+"");
            tvBackwardName1.setText(teamData.get(3).getUserName());


            tvBackwardName2.setText(teamData.get(4).getUserName());
            tvBackwardIntegral2.setText(teamData.get(4).getScore()+"");

            tvBackwardIntegral3.setText(teamData.get(5).getScore()+"");
            tvBackwardName3.setText(teamData.get(5).getUserName());


        }
    }






}
