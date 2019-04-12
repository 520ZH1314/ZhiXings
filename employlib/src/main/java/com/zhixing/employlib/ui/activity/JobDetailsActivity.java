package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.view.Toasty;
import com.google.gson.reflect.TypeToken;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.model.GradingItemEntity;
import com.zhixing.employlib.model.RecruitEntry;
import com.zhixing.employlib.model.recrui.RecruitListBean;
import com.zhixing.employlib.view.CustomDialog;
import com.zhixing.employlib.viewmodel.activity.JobDetailsViewModel;
import com.zhixing.netlib.base.BaseResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author zjq
 * create at 2019/3/19 下午2:51
 * 岗位详情
 */
public class JobDetailsActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_recruit_detail_job_name)
    TextView tvRecruitDetailJobName;
    @BindView(R2.id.tv_recruit_detail_job_money)
    TextView tvRecruitDetailJobMoney;
    @BindView(R2.id.tv_recruit_detail_job_work_year)
    TextView tvRecruitDetailJobWorkYear;
    @BindView(R2.id.tv_recruit_detail_job_date)
    TextView tvRecruitDetailJobDate;
    @BindView(R2.id.tv_recruit_detail_job_desc)
    TextView tvRecruitDetailJobDesc;
    @BindView(R2.id.tv_recruit_detail_money_desc)
    TextView tvRecruitDetailMoneyDesc;
    @BindView(R2.id.btn_recruit_detail_recomond)
    Button btnRecruitDetailRecomond;
    @BindView(R2.id.btn_recruit_detail_delivered)
    Button btnRecruitDetailDelivered;
    private Unbinder bind;
    private int recruitPosition;
    private String RecruitData;
    private  List<RecruitEntry> datas=new ArrayList<>();
    private  List<RecruitListBean>  rows=new ArrayList<>();
    private JobDetailsViewModel jobDetailsViewModel;
    private String userName;
    private ACache aCache;

    @Override
    public int getLayoutId() {
        return R.layout.activity_job_details;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
         bind = ButterKnife.bind(this);
        setStatus(-1);
         initView();
    }

    private void initView() {

        aCache =ACache.get(this);

         userName = SharedPreferencesTool.getMStool(JobDetailsActivity.this).getUserName();

        if (getIntent().hasExtra("RecruitPosition")){
             recruitPosition = getIntent().getIntExtra("RecruitPosition", 0);
        }
        if (getIntent().hasExtra("RecruitData")){
            RecruitData = getIntent().getStringExtra("RecruitData");

            Type type2 = new TypeToken< List<RecruitEntry>>() {}.getType();
            datas = GsonUtil.getGson().fromJson(RecruitData, type2);


            tvRecruitDetailJobName.setText(datas.get(recruitPosition).workName);
            tvRecruitDetailJobMoney.setText(datas.get(recruitPosition).money);
            tvRecruitDetailJobWorkYear.setText(datas.get(recruitPosition).workYear);

            tvRecruitDetailJobDate.setText(datas.get(recruitPosition).date);
            tvRecruitDetailJobDesc.setText(datas.get(recruitPosition).workDesc);
        }

        if (getIntent().hasExtra("SendData")){
            String sendData = getIntent().getStringExtra("SendData");

            Type type2 = new TypeToken< List<RecruitListBean>>() {}.getType();
            rows = GsonUtil.getGson().fromJson(sendData, type2);
        }


         jobDetailsViewModel = ViewModelProviders.of(this).get(JobDetailsViewModel.class);

        if ("0".equals(rows.get(recruitPosition).getState()+"")){
            //未投递
            Drawable pressedDrawable = DevShapeUtils
                    .shape(DevShape.RECTANGLE)
                    .solid(R.color.white)
                    .radius(25)
                    .build();
            Drawable normalDrawable = DevShapeUtils
                    .shape(DevShape.RECTANGLE)
                    .solid(R.color.title_bg)
                    .radius(25)
                    .build();

            DevShapeUtils
                    .selectorPressed(pressedDrawable,normalDrawable)
                    .selectorTextColor(R.color.title_bg, R.color.white)
                    .into(btnRecruitDetailDelivered);
            btnRecruitDetailDelivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog dialog =CustomDialog.newInstance("您确定投递此职位吗?","投递后不可撤回","取消","确定");
                    dialog.setOnDialogInforCompleted(new CustomDialog.OnDialogInforCompleted() {
                        @Override
                        public void dialogInforCompleted(String name) {

                            if ("2".equals(name)){
                                jobDetailsViewModel.SendJob(userName,rows.get(recruitPosition).getRecruitId(),
                                        rows.get(recruitPosition).getJobPost(),
                                        rows.get(recruitPosition).getHandleId(), rows.get(recruitPosition).getHandleName(),"0","1","已投递").observe(JobDetailsActivity.this, new Observer<BaseResponse>() {
                                    @Override
                                    public void onChanged(@Nullable BaseResponse baseResponse) {
                                        if (baseResponse!=null){
                                            if ("success".equals(baseResponse.getStatus())){
                                                //投递成功
                                                Toasty.INSTANCE.showToast(JobDetailsActivity.this,"投递成功");
                                                AppManager.getAppManager().finishActivity();
                                            }else{
                                                Toasty.INSTANCE.showToast(JobDetailsActivity.this,"投递失败");

                                            }

                                        }
                                    }
                                });
                            }

                        }
                    });
                    dialog.show(getSupportFragmentManager(),"");
                }
            });
        }else{
            DevShapeUtils.shape(DevShape.RECTANGLE)
                    .solid(R.color.gray01)
                    .radius(25)
                    .into(btnRecruitDetailDelivered);
            btnRecruitDetailDelivered.setEnabled(false);
            btnRecruitDetailDelivered.setClickable(false);


        }


        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("岗位详情");
        tvWorkSend.setVisibility(View.GONE);


        Drawable pressedDrawable1 = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.white)
                .radius(25)
                .build();
        Drawable normalDrawable1 = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.event_center)
                .radius(25)
                .build();

        DevShapeUtils
                .selectorPressed(pressedDrawable1,normalDrawable1)
                .selectorTextColor(R.color.title_bg, R.color.white)
                .into(btnRecruitDetailRecomond);





        btnRecruitDetailRecomond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog =CustomDialog.newInstance("您确定推荐此职位吗?","推荐后不可撤回","取消","确定");
                dialog.setOnDialogInforCompleted(new CustomDialog.OnDialogInforCompleted() {
                    @Override
                    public void dialogInforCompleted(String name) {
                        if ("2".equals(name)){
                            Intent  intent =new Intent(JobDetailsActivity.this,MyRecommendActivity.class);

                            intent.putExtra("RecommendId",rows.get(recruitPosition).getRecruitId());
                            intent.putExtra("RecommendName",rows.get(recruitPosition).getJobPost());

                            startActivity(intent);
                        }


                    }
                });
                dialog.show(getSupportFragmentManager(),"");


            }
        });

    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_title, R2.id.btn_recruit_detail_recomond, R2.id.btn_recruit_detail_delivered})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_work_title) {

        } else if (i == R.id.btn_recruit_detail_recomond) {
            Toasty.INSTANCE.showToast(JobDetailsActivity.this,"点击了");



        } else if (i == R.id.btn_recruit_detail_delivered) {


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
