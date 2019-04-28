package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.model.AppealList;
import com.zhixing.employlib.view.AppealEditDialog;
import com.zhixing.employlib.view.CustomDialog;
import com.zhixing.employlib.viewmodel.activity.AppealPersonViewModel;
import com.zhixing.netlib.base.BaseResponse;

import java.util.HashMap;
import java.util.Map;

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
    private AppealPersonViewModel appealPersonViewModel;
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

        setStatus(-1);
         bind = ButterKnife.bind(this);
        appealPersonViewModel = ViewModelProviders.of(this).get(AppealPersonViewModel.class);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("申诉记录详情");
        tvWorkSend.setVisibility(View.GONE);
         initData();

    }
    AppealList appealList;
    private void initData() {
        Intent intent = getIntent();
        if(intent.hasExtra("obj")){
            appealList = (AppealList) intent.getSerializableExtra("obj");
        }
        vie();
        //申诉记录详情

        //处理记录详情



    }
    private void vie(){
        if(appealList!=null){
            if(appealList.getStatus()==1){
                // clAppeal.setVisibility(View.VISIBLE);
                clAppealDetailButton.setVisibility(View.GONE);
                tvAppealDetailStatus.setText("已通过");
                tvAppealDetailStatus.setTextColor(getResources().getColor(R.color.green));
                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .line(1, R.color.green)
                        .radius(10)
                        .into(tvAppealDetailStatus);
                tvAppealDetailPeopleName.setTextColor(getResources().getColor(R.color.green));
                tvAppealDetailReuit.setText("通过!");
            }else if(appealList.getStatus()==0){
                clAppeal.setVisibility(View.GONE);
                clAppealDetailButton.setVisibility(View.GONE);
                tvAppealDetailStatus.setText("未审核");
                tvAppealDetailStatus.setTextColor(getResources().getColor(R.color.txt_bor));
                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .line(1, R.color.txt_bor)
                        .radius(10)
                        .into(tvAppealDetailStatus);
                tvAppealDetailPeopleName.setTextColor(getResources().getColor(R.color.txt_bor));
                tvAppealDetailStatus.setOnClickListener((v)->{
                    //提交审核
                    AppealEditDialog dialog =AppealEditDialog.newInstance("审核当前申诉?","请填写理由","不通过","通过");
                    dialog.setOnDialogInforCompleted(new AppealEditDialog.OnDialogInforCompleted() {
                        @Override
                        public void dialogInforCompleted(String name) {
                            // P.c("测试"+name);
                            showDialog("提交审核结果");
                            Map map = new HashMap();
                            map.put("Status",name);
                            map.put("AppealId",appealList.getAppealId());
                            map.put("HandleUserId", SharedPreferencesTool.getMStool(AppealDetailActivity.this).getUserId());
                            map.put("HandleUserName", SharedPreferencesTool.getMStool(AppealDetailActivity.this).getUserName());
                            map.put("HandleOpinion", dialog.getRes());
                            appealPersonViewModel.sendAppealRes(map).observe(AppealDetailActivity.this, new Observer<BaseResponse>() {
                                @Override
                                public void onChanged(@Nullable BaseResponse baseResponse) {
                                    dismissDialog();
                                    P.c(baseResponse.getMessage());
                                    appealList.setStatus(Integer.parseInt(name));

                                 vie();


                                }
                            });
                        }
                    });
                    dialog.show(getSupportFragmentManager(),"");
                });
//                tvAppealDetailReuit.setText("不通过!");
            }else if(appealList.getStatus()==2){
                clAppealDetailButton.setVisibility(View.GONE);
                tvAppealDetailStatus.setText("未通过");
                tvAppealDetailStatus.setTextColor(getResources().getColor(R.color.orange));
                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .line(1, R.color.orange)
                        .radius(10)
                        .into(tvAppealDetailStatus);
                tvAppealDetailPeopleName.setTextColor(getResources().getColor(R.color.orange));
                tvAppealDetailReuit.setText("不通过!");
            }
            tvAppealDetailEventName.setText("申诉事件:"+appealList.getKeyName());
            tvAppealDetailPeopleName.setText("处理人:"+appealList.getHandleUserName());
            tvAppealDetailEventDate.setText("异常时间:"+ TimeUtil.getTime(TimeUtil.parseTimeC(appealList.getKeyDate())));
            tvAppealDetailDesc.setText(appealList.getOpinion());
            tvAppealDetailDoDesc.setText(appealList.getHandleOpinion());
            if(appealList.getHandleTime()!=null)
                tvAppealDetailEventDate2.setText("处理时间:"+TimeUtil.getTime(TimeUtil.parseTimeC(appealList.getHandleTime())));
        }
    }


    @OnClick({R2.id.iv_work_add_work, R2.id.btn_appeal_detail_ok, R2.id.btn_appeal_detail_no})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
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
