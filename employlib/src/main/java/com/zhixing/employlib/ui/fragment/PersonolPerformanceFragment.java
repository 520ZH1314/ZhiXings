package com.zhixing.employlib.ui.fragment;

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
import android.widget.RadioButton;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.widget.ChangeTime;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.ui.activity.GradingActivity;
import com.zhixing.employlib.ui.activity.GradingRecordListActivity;
import com.zhixing.employlib.ui.activity.MothIntegralEventActivity;
import com.zhixing.employlib.ui.activity.RecruitRecordActivity;
import com.zhixing.employlib.view.DialogFragmentIntergralEvent;
import com.zhixing.employlib.view.DialogFragmentPersonTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
public class PersonolPerformanceFragment extends BaseFragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {


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

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfabHelper;

    private int index = 0;
    private String[] splitDay;
    private String[] splitYear;

    private String Year;
    private String Month;
    private String Day;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personol_performance, container, false);
        unbinder = ButterKnife.bind(this, view);
        rfaLayout = (RapidFloatingActionLayout) view.findViewById(R.id.label_list_sample_rfal);
        rfaButton = (RapidFloatingActionButton) view.findViewById(R.id.label_list_sample_rfab);
        initView();

        return view;
    }

    private void initView() {
        //设置文字加粗
        TextPaint tp = tvTimeDate.getPaint();
        tp.setFakeBoldText(true);

        TextPaint tp1 = tvIntegral.getPaint();
        tp1.setFakeBoldText(true);

        TextPaint tp2 = tvRank.getPaint();
        tp2.setFakeBoldText(true);

        ivWorkAddWork.setVisibility(View.GONE);
        tvWorkTitle.setText("绩效");
        tvWorkSend.setText("注塑塑胶班组");
        //设置前一日的时间
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //日减1
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(lastDay);
        String commonTime1 = TimeUtil.getCommonTime1(format);
        String[] split = commonTime1.split("-");
        Year = split[0];
        Month = split[1];
        Day = split[2];
        if (index == 0) {
            tvTimeYear.setText(Year + "年" + Month + "月");
            tvTimeDate.setText(Day + "日");
        } else if (index == 1) {
            tvTimeYear.setText(Year + "年");
            tvTimeDate.setText(Month + "月");
        }


        initFloatingActionButton();//初始化悬浮按钮
    }


    private void initFloatingActionButton() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getActivity());
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("我要申诉")
                .setResId(R.drawable.shensu1)
                .setIconNormalColor(Color.parseColor("#EE8626"))
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );


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
        if (position == 2) {


            Intent intent = new Intent(getActivity(), GradingActivity.class);
            startActivity(intent);
            rfabHelper.toggleContent();
        } else if (position == 1) {

            Intent intent = new Intent(getActivity(), GradingRecordListActivity.class);
            startActivity(intent);
            rfabHelper.toggleContent();
        } else {

            Intent intent = new Intent(getActivity(), RecruitRecordActivity.class);
            startActivity(intent);
            rfabHelper.toggleContent();
        }

    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        if (position == 2) {


            Intent intent = new Intent(getActivity(), GradingActivity.class);
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
            R2.id.radButton_person2,R2.id.iv_work_add_work, R2.id.tv_work_title, R2.id.tv_work_send})
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
                        Year = splitYear[0];
                        Month = splitYear[1];
                        Day = splitYear[2];
                        tvTimeYear.setText(Year + "年");
                        tvTimeDate.setText(Month + "月");

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


        } else if (i == R.id.radButton_person2) {
            textView5.setText("累计积分");
            textView7.setText("累计积分排名");
            tvTimeYear.setText(Year + "年");
            tvTimeDate.setText(Month + "月");

            index = 1;

        } else if (i == R.id.tv_integral) {


            if (index == 1) {
                Intent intent = new Intent(getActivity(), MothIntegralEventActivity.class);
                startActivity(intent);
            } else {
                DialogFragmentIntergralEvent dialogFragmentIntergralEvent = new DialogFragmentIntergralEvent();
                dialogFragmentIntergralEvent.show(getChildFragmentManager(), "");

            }

        }else if(i == R.id.iv_work_add_work){
            AppManager.getAppManager().finishActivity();

        }else if(i == R.id.tv_work_title){

        }else if(i == R.id.tv_work_send){

        }
    }


}
