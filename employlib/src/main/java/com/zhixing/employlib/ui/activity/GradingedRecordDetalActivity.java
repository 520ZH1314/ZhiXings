package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.example.stateviewlibrary.StateView;
import com.rmondjone.locktableview.LockTableView;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.model.grading.GradingListDetailBean;
import com.zhixing.employlib.view.DialogFragmentGradingedStand;
import com.zhixing.employlib.viewmodel.activity.GradingedListViewModel;
import com.zhixing.netlib.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class GradingedRecordDetalActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.circleImageView_gradinged_detail)
    CircleImageView circleImageViewGradingedDetail;
    @BindView(R2.id.tv_grading_record_detail_name)
    TextView tvGradingRecordDetailName;
    @BindView(R2.id.tv_grading_record_detail_time)
    TextView tvGradingRecordDetailTime;
    @BindView(R2.id.tv_grading_record_detail_people_name)
    TextView tvGradingRecordDetailPeopleName;
    @BindView(R2.id.tv_gradinged_record_detail_worker)
    TextView tvGradingedRecordDetailWorker;
    @BindView(R2.id.tv_gradinged_record_detail_score)
    TextView tvGradingedRecordDetailScore;
    @BindView(R2.id.tv_gradinged_record_detail_better)
    TextView tvGradingedRecordDetailBetter;
    @BindView(R2.id.tv_grading_record_detail_stand)
    TextView tvGradingRecordDetailStand;
    @BindView(R2.id.record_detail_contentView)
    LinearLayout recordDetailContentView;
    private Unbinder bind;
    private ArrayList<ArrayList<String>> mTableDatas;
    private ArrayList<String> mfristData;
    private LockTableView mLockTableView;
    private GradingedListViewModel gradingedListViewModel;
    private String useCode;
    private String useName;
    private String startTime;
    private String endTime;
    private ScrollView linearLayout;
    private StateView mStateView;
    private String ip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gradinged_record_detal;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
         bind = ButterKnife.bind(this);
         linearLayout=(ScrollView) findViewById(R.id.sl_new_gradinged_record_detail);
        mStateView=StateView.inject(linearLayout);
         ip = SharedPreferencesTool.getMStool(this).getIp();
        setStatus(-1);
         initView();


    }

    private void initView() {

        if (getIntent().hasExtra("GradingedRecordDetalUseCode")){
            useCode=getIntent().getStringExtra("GradingedRecordDetalUseCode");
        }
        if (getIntent().hasExtra("GradingedRecordDetalUseName")){
            useName=getIntent().getStringExtra("GradingedRecordDetalUseName");
        }

        if (getIntent().hasExtra("GradingedRecordDetalStartTime")){
            startTime=getIntent().getStringExtra("GradingedRecordDetalStartTime");
        }
        if (getIntent().hasExtra("GradingedRecordDetalEndTime")){
            endTime=getIntent().getStringExtra("GradingedRecordDetalEndTime");
        }


        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("评分记录详情");
        tvWorkSend.setVisibility(View.GONE);
        tvGradingRecordDetailName.setText(useName);
        tvGradingedRecordDetailWorker.getPaint().setFakeBoldText(true);
        tvGradingedRecordDetailScore.getPaint().setFakeBoldText(true);
        tvGradingedRecordDetailBetter.getPaint().setFakeBoldText(true);

        gradingedListViewModel = ViewModelProviders.of(this).get(GradingedListViewModel.class);


        initTable();

        initData();


    }

    private void initData() {
        mStateView.showLoading();
        gradingedListViewModel.setDates(startTime,endTime,useCode);
        gradingedListViewModel.RecordDetailData.observe(this, new Observer<BaseResponse<GradingListDetailBean>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<GradingListDetailBean> gradingListDetailBeanBaseResponse) {

                   if (gradingListDetailBeanBaseResponse.getRows()!=null){
                       mStateView.showContent();
                       if (gradingListDetailBeanBaseResponse.getRows().size()!=0){
                           GradingListDetailBean.UserInfoBean userInfo = gradingListDetailBeanBaseResponse.getRows().get(0).getUserInfo();
                           tvGradingedRecordDetailWorker.setText(userInfo.getPositionName());
                           tvGradingedRecordDetailScore.setText(userInfo.getScore()+"");
                           if (!TextUtils.isEmpty(userInfo.getPhotoURL())){
                               MyImageLoader.loads(GradingedRecordDetalActivity.this,userInfo.getPhotoURL(),circleImageViewGradingedDetail,R.mipmap.standard_head);
                           }

                           if ("".equals(userInfo.getGrapeName())){
                               tvGradingedRecordDetailBetter.setText("待提升");
                           }else{
                               tvGradingedRecordDetailBetter.setText(userInfo.getGrapeName());
                           }
                       }


                       List<GradingListDetailBean.EventInfoBean> eventInfo = gradingListDetailBeanBaseResponse.getRows().get(1).getEventInfo();
                       if (eventInfo!=null){

                           tvGradingRecordDetailPeopleName.setText("评分人:"+eventInfo.get(0).getTeamLeaderName());
                           for (int i = 0; i < eventInfo.size(); i++) {
                               String shiftDate = eventInfo.get(i).getShiftDate();
                               String[] ts = shiftDate.split("T");
                               ArrayList<String> mRowDatas = new ArrayList<>();
                               //数据填充
                               mRowDatas.add(eventInfo.get(i).getItemName());
                               mRowDatas.add(eventInfo.get(i).getScore() + "");
                               mRowDatas.add(ts[0]);
                               mTableDatas.add(mRowDatas);

                           }
                           mLockTableView.setTableDatas(mTableDatas);
                       }

                   }else if ("404".equals(gradingListDetailBeanBaseResponse.getStatus())){
                       mStateView.showRetry();
                       mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                           @Override
                           public void onRetryClick() {
                               gradingedListViewModel.setDates(startTime,endTime,useCode);
                           }
                       });

                   }else{
                       mStateView.showEmpty();
                   }
            }
        });
    }


    /**
     *
     *@author zjq
     *create at 2019/3/13 下午2:17
     * 初始化表格
     */
    private void initTable() {
        mTableDatas = new ArrayList<ArrayList<String>>();
        mfristData = new ArrayList<String>();
        mfristData.add("事件");
        mfristData.add("加/减分");
        mfristData.add("记录时间");
        mTableDatas.add(mfristData);




        mLockTableView = new LockTableView(this, recordDetailContentView, mTableDatas);
        mLockTableView.setLockFristColumn(false) //是否锁定第一列
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


    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_grading_record_detail_stand})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_grading_record_detail_stand) {
            DialogFragmentGradingedStand dialogFragmentGradingedStand =new DialogFragmentGradingedStand();
            dialogFragmentGradingedStand.show(getSupportFragmentManager(),"");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}
