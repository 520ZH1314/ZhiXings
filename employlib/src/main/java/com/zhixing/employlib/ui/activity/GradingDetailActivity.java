package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeTime;
import com.google.gson.reflect.TypeToken;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.rmondjone.locktableview.DisplayUtil;
import com.rmondjone.locktableview.LockTableView;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.model.GradingItemEntity;
import com.zhixing.employlib.model.eventbus.GradingEvent;
import com.zhixing.employlib.model.eventbus.GradingEventBean;
import com.zhixing.employlib.model.grading.GoGradingPostBean;
import com.zhixing.employlib.model.grading.GradingListDetailBean;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.viewmodel.activity.GradListDetailViewModel;
import com.zhixing.netlib.base.BaseResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class GradingDetailActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_grading_list_detail_day)
    TextView tvGradingListDetailDay;
    @BindView(R2.id.tv_grading_detail_event)
    TextView tvGradingDetailEvent;
    @BindView(R2.id.cl_grading_detail_event)
    ConstraintLayout clGradingDetailEvent;
    @BindView(R2.id.tv_grading_detail_time)
    TextView tvGradingDetailTime;
    @BindView(R2.id.cl_grading_detail_time)
    ConstraintLayout clGradingDetailTime;
    @BindView(R2.id.btn_grading_detail_commit)
    Button btnGradingDetailCommit;
    @BindView(R2.id.contentView)
    LinearLayout contentView;
    @BindView(R2.id.circleImageView_detail)
    CircleImageView circleImageViewDetail;
    @BindView(R2.id.cl_grading_detail_single_people)
    ConstraintLayout clGradingDetailSinglePeople;
    @BindView(R2.id.circleImageView_detail_many_people)
    CircleImageView circleImageViewDetailManyPeople;
    @BindView(R2.id.tv_grading_detail_modify)
    TextView tvGradingDetailModify;
    @BindView(R2.id.cl_grading_detail_many_people)
    ConstraintLayout clGradingDetailManyPeople;
    @BindView(R2.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R2.id.tv_grading_list_detail_year)
    TextView tvGradingListDetailYear;
    @BindView(R2.id.tv_grading_list_detail_moth)
    TextView tvGradingListDetailMoth;
    @BindView(R2.id.tv_grading_item_list_detail_name)
    TextView tvGradingItemListDetailName;
    @BindView(R2.id.tv_grading_item_list_detail_sex)
    TextView tvGradingItemListDetailSex;
    @BindView(R2.id.tv_grading_item_list_detail_worker)
    TextView tvGradingItemListDetailWorker;
    @BindView(R2.id.tv_grading_item_list_detail_desc)
    TextView tvGradingItemListDetailDesc;
    @BindView(R2.id.tv_grading_event_many_people_name)
    TextView tvGradingEventManyPeopleName;


    private Unbinder bind;
    private String name;
    private int score;
    private ArrayList<ArrayList<String>> mTableDatas;
    private ArrayList<String> mfristData;
    private LockTableView mLockTableView;
    private String Tvtime;
    private List<GoGradingPostBean.EventInfoBean> Eventdatas;
    private String position;//单选模式下的个人code
    private String Itemid;//事件id
    private String selectData;//多人评分的时候带过来的多人ID以及数据
    private String type;//多人or单
    private GradListDetailViewModel gradListDetailViewModel;
    private String dateTime;
    private ACache aCache;
    private  boolean isCommit=false;
    private String standTime;
    private List<GradingItemEntity> userDatas=new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_grading_detail;
    }

    @Override
    public void process(Message msg) {

    }


    @Override
    public void initLayout() {
        bind = ButterKnife.bind(this);
        aCache = ACache.get(this);
        EventBus.getDefault().register(this);
        initDisplayOpinion();
        initView();


    }

    private void initData() {

        //默认显示昨天日子
        //设置前一日的时间
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //日减1
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(lastDay);
         standTime = TimeUtil.getCommonTime1(format);
        String[] splitDay = standTime.split("-");
        String Year = splitDay[0];
        String Month = splitDay[1];
        String Day = splitDay[2];

        tvGradingListDetailYear.setText(Year + "年");
        tvGradingListDetailMoth.setText(Month + "月");
        tvGradingListDetailDay.setText(Day + "日");

        if ("1".equals(type)) {

            showDialog("");
            //单人
            gradListDetailViewModel.setDate(standTime, position);
            gradListDetailViewModel.DetailData.observe(this, new Observer<BaseResponse<GradingListDetailBean>>() {
                @Override
                public void onChanged(@Nullable BaseResponse<GradingListDetailBean> gradingListDetailBeanBaseResponse) {
                    if (gradingListDetailBeanBaseResponse.getRows() != null) {


                            String userName = gradingListDetailBeanBaseResponse.getRows().get(0).getUserInfo().getUserName();
                            String sex = gradingListDetailBeanBaseResponse.getRows().get(0).getUserInfo().getSex();
                            String positionName = gradingListDetailBeanBaseResponse.getRows().get(0).getUserInfo().getPositionName();
                            int eventCount = gradingListDetailBeanBaseResponse.getRows().get(0).getUserInfo().getEventCount();
                            aCache.put("eventCount", eventCount);
                            aCache.put("userName",userName);
                            tvGradingItemListDetailName.setText(userName);
                            tvGradingItemListDetailSex.setText(sex);
                            tvGradingItemListDetailWorker.setText(positionName);
                            tvGradingItemListDetailDesc.setText("关键事件录入:" + eventCount + "条");


                        List<GradingListDetailBean.EventInfoBean> eventInfo = gradingListDetailBeanBaseResponse.getRows().get(1).getEventInfo();
                        if (eventInfo!=null){
                            for (int i = 0; i < eventInfo.size(); i++) {
                                String shiftDate = eventInfo.get(i).getShiftDate();
                                String[] ts = shiftDate.split("T");
                                ArrayList<String> mRowDatas = new ArrayList<>();
                                //数据填充
                                mRowDatas.add(eventInfo.get(i).getItemName());
                                mRowDatas.add(eventInfo.get(i).getScore() + "");
                                mRowDatas.add(ts[1]);
                                mTableDatas.add(mRowDatas);

                            }
                            mLockTableView.setTableDatas(mTableDatas);
                            dismissDialog();
                        }else{
                            dismissDialog();
                        }


                    }else{
                       dismissDialog();
                    }

                }
            });

        }else if ("2".equals(type)){
            //批量

            Type type2 = new TypeToken< List<GradingItemEntity>>() {}.getType();
            userDatas = GsonUtil.getGson().fromJson(selectData, type2);
            tvGradingEventManyPeopleName.setText(userDatas.get(0).name+"等"+userDatas.size()+"人");






        }

    }

    private void initView() {

        if (getIntent().hasExtra("type")) {
            type = getIntent().getStringExtra("type");
            if ("1".equals(type)) {
                clGradingDetailSinglePeople.setVisibility(View.VISIBLE);
                clGradingDetailManyPeople.setVisibility(View.GONE);

            } else {
                clGradingDetailSinglePeople.setVisibility(View.GONE);
                clGradingDetailManyPeople.setVisibility(View.VISIBLE);

            }

            if (getIntent().hasExtra("position")) {
                position = getIntent().getStringExtra("position");

            }

            if (getIntent().hasExtra("selectData")) {
                selectData = getIntent().getStringExtra("selectData");

            }


        }

        Eventdatas = new ArrayList<>();

        gradListDetailViewModel = ViewModelProviders.of(this).get(GradListDetailViewModel.class);
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.item_grading_btn)
                .radius(15)
                .into(btnGradingDetailCommit);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("评分详情");
        tvWorkSend.setText("完成");
        mTableDatas = new ArrayList<ArrayList<String>>();
        mfristData = new ArrayList<String>();
        mfristData.add("事件");
        mfristData.add("加/减分");
        mfristData.add("记录时间");
        mTableDatas.add(mfristData);
        mLockTableView = new LockTableView(this, contentView, mTableDatas);
        mLockTableView.setLockFristColumn(false) //是否锁定第一列
                .setLockFristRow(true) //是否锁定第一行
                .setMaxColumnWidth(80) //列最大宽度
                .setMinColumnWidth(80) //列最小宽度
                .setMinRowHeight(20)//行最小高度
                .setMaxRowHeight(50)//行最大高度
                .setTextViewSize(14) //单元格字体大小
                .setFristRowBackGroudColor(R.color.review_bac)//表头背景色
                .setTableHeadTextColor(R.color.black)//表头字体颜色
                .setTableContentTextColor(R.color.gray)//单元格字体颜色
                .setCellPadding(10)//设置单元格内边距(dp)
                .setNullableString("N/A") //空值替换值
                .show(); //显示表格,此方法必须调用
        mLockTableView.getTableScrollView().setPullRefreshEnabled(false);
        mLockTableView.getTableScrollView().setLoadingMoreEnabled(false);


        initData();

    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send, R2.id.cl_grading_detail_event, R2.id.cl_grading_detail_time,
            R2.id.btn_grading_detail_commit, R2.id.tv_grading_detail_modify, R2.id.tv_grading_list_detail_day})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {

            AppManager.getAppManager().finishActivity();

        } else if (i == R.id.tv_work_send) {

            showDialog("");
            if (!isCommit){
                Toasty.INSTANCE.showToast(this, "关键事件或记录时间不能为空");

            }else{

                if ("1".equals(type)){

                     if (dateTime==null){
                         dateTime=standTime;
                     }
                    GoGradingPostBean.EventInfoBean eventInfoBean = new GoGradingPostBean.EventInfoBean();
                    eventInfoBean.setItemId(Itemid);
                    eventInfoBean.setShiftDate(dateTime);
                    Eventdatas.add(eventInfoBean);
                    GoGradingPostBean.UserInfoBean bean=new GoGradingPostBean.UserInfoBean();
                    bean.setUserCode(position);
                    bean.setUserName(aCache.getAsString("userName"));
                    List< GoGradingPostBean.UserInfoBean> userInfoBeans=new ArrayList<>();
                    userInfoBeans.add(bean);
                    gradListDetailViewModel.GoGrading(userInfoBeans,Eventdatas).observe(GradingDetailActivity.this, new Observer<BaseResponse>() {
                        @Override
                        public void onChanged(@Nullable BaseResponse baseResponse) {
                              dismissDialog();
                            if (baseResponse!=null){
                                if (baseResponse.getStatus().equals("error")){

                                    Toasty.INSTANCE.showToast(GradingDetailActivity.this,"提交失败");
                                    dismissDialog();
                                }else{
                                    isCommit=false;
                                    Toasty.INSTANCE.showToast(GradingDetailActivity.this,"提交成功");
                                     AppManager.getAppManager().finishActivity();
                                }

                            }
                        }
                    });

                }else if ("2".equals(type)){

                    if (dateTime==null){
                        dateTime=standTime;
                    }
                    //批量
                    GoGradingPostBean.EventInfoBean eventInfoBean = new GoGradingPostBean.EventInfoBean();
                    eventInfoBean.setItemId(Itemid);
                    eventInfoBean.setShiftDate(dateTime);
                    Eventdatas.add(eventInfoBean);

                    List< GoGradingPostBean.UserInfoBean> userInfoBeans=new ArrayList<>();
                    for (int j = 0; j < userDatas.size(); j++) {
                        GoGradingPostBean.UserInfoBean bean=new GoGradingPostBean.UserInfoBean();

                        bean.setUserCode(userDatas.get(j).useCode);
                        bean.setUserName(userDatas.get(j).getName());
                        userInfoBeans.add(bean);
                    }
                    gradListDetailViewModel.GoGrading(userInfoBeans,Eventdatas).observe(GradingDetailActivity.this, new Observer<BaseResponse>() {
                        @Override
                        public void onChanged(@Nullable BaseResponse baseResponse) {

                            if (baseResponse!=null){
                                if (baseResponse.getStatus().equals("error")){
                                    dismissDialog();
                                    EventBus.getDefault().post(new GradingEvent(false));
                                    Toasty.INSTANCE.showToast(GradingDetailActivity.this,"提交失败");



                                }else{
                                    dismissDialog();
                                    isCommit=false;
                                    EventBus.getDefault().post(new GradingEvent(false));
                                    Toasty.INSTANCE.showToast(GradingDetailActivity.this,"提交成功");
                                    AppManager.getAppManager().finishActivity();
                                }

                            }
                        }
                    });


                }

            }





        } else if (i == R.id.cl_grading_detail_event) {
            Intent intent = new Intent(GradingDetailActivity.this, SelectEventActivity.class);
            intent.putExtra("TypeList", "1");
            startActivity(intent);


        } else if (i == R.id.cl_grading_detail_time) {
            ChangeTime changeTime = new ChangeTime(this, "", 1);
            changeTime.setSelect(new SelectTime() {
                @Override
                public void select(String time, long timestp) {
                    String commonTime2 = TimeUtil.getCommonTime2(time);
//                    String Hour = splitDay[3];
//                    String Min = splitDay[4];
//                    Tvtime=Hour + ":" + Min;Tvtime
                    Tvtime = commonTime2;
                    tvGradingDetailTime.setVisibility(View.VISIBLE);
                    tvGradingDetailTime.setText(commonTime2);

                }
            });
            changeTime.showSheet();


        } else if (i == R.id.btn_grading_detail_commit) {

            if (TextUtils.isEmpty(tvGradingDetailEvent.getText()) || TextUtils.isEmpty(tvGradingDetailTime.getText())) {

                Toasty.INSTANCE.showToast(this, "关键事件或记录时间不能为空");


            } else {
                ArrayList<String> mRowDatas = new ArrayList<>();

                //数据填充
                mRowDatas.add(name);
                mRowDatas.add(score + "");
                mRowDatas.add(Tvtime);
                mTableDatas.add(mRowDatas);
                isCommit=true;
                mLockTableView.setTableDatas(mTableDatas);
                tvGradingDetailTime.setText("");
                tvGradingDetailEvent.setText("");
                tvGradingDetailEvent.setVisibility(View.GONE);
                tvGradingDetailTime.setVisibility(View.GONE);
            }


        } else if (i == R.id.tv_grading_detail_modify) {

            AppManager.getAppManager().finishActivity();

        } else if (i == R.id.tv_grading_list_detail_day) {

            ChangeTime changeTime = new ChangeTime(this, "", 2);
            changeTime.setSelect(new SelectTime() {
                @Override
                public void select(String time, long timestp) {
                    dateTime = TimeUtil.getCommonTime1(time);
                    String[] splitDay = dateTime.split("-");
                    String Year = splitDay[0];
                    String Month = splitDay[1];
                    String Day = splitDay[2];
                    tvGradingListDetailYear.setText(Year + "年");
                    tvGradingListDetailMoth.setText(Month + "月");
                    tvGradingListDetailDay.setText(Day + "日");

                }
            });
            changeTime.showSheet();

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        bind.unbind();
        dismissDialog();
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(this, dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(this, dm.heightPixels);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(GradingEventBean eventBean) {
        name = eventBean.getName();
        score = eventBean.getScore();
        Itemid = eventBean.getId();
        tvGradingDetailEvent.setVisibility(View.VISIBLE);
        tvGradingDetailEvent.setText(eventBean.getName());
         int eventCount = (int) aCache.getAsObject("eventCount");
         eventCount=eventCount+1;
         aCache.put("eventCount",eventCount);
        tvGradingItemListDetailDesc.setText("关键事件录入:" + eventCount + "条");

    }



}
