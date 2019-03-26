package com.zhixing.employlib.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author zjq
 * create at 2019/3/21 下午2:44
 * 我申诉记录详情(处理记录详情)
 */
public class AppealDetailActivity extends BaseActvity {

    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_appeal_detail_event_name)
    TextView tvAppealDetailEventName;
    @BindView(R2.id.tv_appeal_detail_people_name)
    TextView tvAppealDetailPeopleName;
    @BindView(R2.id.tv_appeal_detail_event_date)
    TextView tvAppealDetailEventDate;
    @BindView(R2.id.tv_appeal_detail_status)
    TextView tvAppealDetailStatus;
    @BindView(R2.id.tv_appeal_detail_desc)
    TextView tvAppealDetailDesc;
    @BindView(R2.id.recy_appeal)
    RecyclerView recyAppeal;
    @BindView(R2.id.tv_appeal_detail_event_date2)
    TextView tvAppealDetailEventDate2;
    @BindView(R2.id.tv_appeal_detail_do_desc)
    TextView tvAppealDetailDoDesc;
    @BindView(R2.id.tv_appeal_detail_reuit)
    TextView tvAppealDetailReuit;
    @BindView(R2.id.cl_appeal)
    ConstraintLayout clAppeal;
    @BindView(R2.id.btn_appeal_detail_ok)
    Button btnAppealDetailOk;
    @BindView(R2.id.btn_appeal_detail_no)
    Button btnAppealDetailNo;
    @BindView(R2.id.cl_appeal_detail_button)
    ConstraintLayout clAppealDetailButton;
    private Unbinder bind;

    private  String status="1";


    @Override
    public int getLayoutId() {
        return R.layout.activity_appeal_detail;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
         bind = ButterKnife.bind(this);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("申诉记录详情");
        tvWorkSend.setVisibility(View.GONE);
         initData();

    }

    private void initData() {

        //申诉记录详情
           if ("1".equals(status)){
               clAppeal.setVisibility(View.VISIBLE);
               clAppealDetailButton.setVisibility(View.GONE);
               //已审核
               tvAppealDetailEventName.setText("申诉事件:迟到");
               tvAppealDetailPeopleName.setText("处理人:李四");
               tvAppealDetailPeopleName.setTextColor(this.getColor(R.color.green));
               tvAppealDetailStatus.setText("已审核");
               tvAppealDetailStatus.setTextColor(this.getColor(R.color.green));
               DevShapeUtils
                       .shape(DevShape.RECTANGLE)
                       .line(1, R.color.green)
                       .radius(10)
                       .into(tvAppealDetailStatus);
               tvAppealDetailEventDate.setText("异常时间:2019-03-06");
               tvAppealDetailDesc.setText("打卡上了解到来看撒娇地拉开圣诞节啦是看得见阿拉丁打卡上了解到来看撒娇地拉开圣诞节啦是看得见阿拉丁打卡上了解到来看撒娇地拉开圣诞节啦是看得见阿拉丁");
               tvAppealDetailEventDate2.setText("2019-03-09");
               tvAppealDetailDoDesc.setText("确实迟到了");
               tvAppealDetailReuit.setText("不通过!");
               tvAppealDetailReuit.setTextColor(this.getColor(R.color.red));
           }else{
               //未审核
               clAppeal.setVisibility(View.GONE);
               clAppealDetailButton.setVisibility(View.GONE);
               //已审核
               tvAppealDetailEventName.setText("申诉事件:迟到");
               tvAppealDetailPeopleName.setText("处理人:李四");
               tvAppealDetailPeopleName.setTextColor(this.getColor(R.color.orange));
               tvAppealDetailStatus.setText("未审核");
               tvAppealDetailStatus.setTextColor(this.getColor(R.color.orange));
               DevShapeUtils
                       .shape(DevShape.RECTANGLE)
                       .line(1, R.color.orange)
                       .radius(10)
                       .into(tvAppealDetailStatus);
               tvAppealDetailEventDate.setText("异常时间:2019-03-06");

           }
        //处理记录详情



    }


    @OnClick({R2.id.iv_work_add_work, R2.id.btn_appeal_detail_ok, R2.id.btn_appeal_detail_no})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
        } else if (i == R.id.btn_appeal_detail_ok) {
        } else if (i == R.id.btn_appeal_detail_no) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
