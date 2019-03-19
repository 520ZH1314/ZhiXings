package com.zhixing.employlib.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeTime;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.rmondjone.locktableview.DisplayUtil;
import com.rmondjone.locktableview.LockTableView;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.model.eventbus.GradingEventBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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
    private Unbinder bind;
    private String name;
    private String score;
    private ArrayList<ArrayList<String>> mTableDatas;
    private ArrayList<String> mfristData;
    private LockTableView mLockTableView;
    private String Tvtime;
    private List<GradingEventBean> Eventdatas;


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
        EventBus.getDefault().register(this);
        initDisplayOpinion();
        initView();
    }

    private void initView() {
        Eventdatas = new ArrayList<>();


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
                .setMaxColumnWidth(100) //列最大宽度
                .setMinColumnWidth(60) //列最小宽度
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

    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send, R2.id.cl_grading_detail_event, R2.id.cl_grading_detail_time, R2.id.btn_grading_detail_commit,R2.id.tv_grading_detail_modify})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {

            AppManager.getAppManager().finishActivity();

        } else if (i == R.id.tv_work_send) {
        } else if (i == R.id.cl_grading_detail_event) {
            startActivity(SelectEventActivity.class);

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
                mRowDatas.add(score);
                mRowDatas.add(Tvtime);
                mTableDatas.add(mRowDatas);
                mLockTableView.setTableDatas(mTableDatas);
                tvGradingDetailTime.setText("");
                tvGradingDetailEvent.setText("");
                tvGradingDetailEvent.setVisibility(View.GONE);
                tvGradingDetailTime.setVisibility(View.GONE);
            }


        }else if(i==R.id.tv_grading_detail_modify){

            AppManager.getAppManager().finishActivity();

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        bind.unbind();
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
        Eventdatas.add(eventBean);
        tvGradingDetailEvent.setVisibility(View.VISIBLE);
        tvGradingDetailEvent.setText(eventBean.getName());


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( getIntent().hasExtra("type")){
           if ("1".equals(getIntent().getStringExtra("type"))){
               clGradingDetailSinglePeople.setVisibility(View.VISIBLE);
               clGradingDetailManyPeople.setVisibility(View.GONE);


           }else{
               clGradingDetailSinglePeople.setVisibility(View.GONE);
               clGradingDetailManyPeople.setVisibility(View.VISIBLE);

           }



        }



    }
}
