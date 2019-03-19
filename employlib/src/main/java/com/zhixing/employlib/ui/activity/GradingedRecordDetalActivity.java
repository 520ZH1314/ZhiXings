package com.zhixing.employlib.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.rmondjone.locktableview.LockTableView;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.view.DialogFragmentGradingedStand;

import java.util.ArrayList;

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

         initView();


    }

    private void initView() {
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("评分记录详情");
        tvWorkSend.setVisibility(View.GONE);

        tvGradingedRecordDetailWorker.getPaint().setFakeBoldText(true);
        tvGradingedRecordDetailScore.getPaint().setFakeBoldText(true);
        tvGradingedRecordDetailBetter.getPaint().setFakeBoldText(true);



        initTable();


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



        ArrayList<String> mRowDatas = new ArrayList<>();
        //数据填充
        mRowDatas.add("迟到");
        mRowDatas.add("-2");
        mRowDatas.add("9:02");
        mTableDatas.add(mRowDatas);
        mLockTableView = new LockTableView(this, recordDetailContentView, mTableDatas);
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