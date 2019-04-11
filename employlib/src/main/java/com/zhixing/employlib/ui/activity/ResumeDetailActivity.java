package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.inter.SelectDoubleTime;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeDoubleTime;
import com.google.gson.reflect.TypeToken;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.model.resume.CompanyEntity;
import com.zhixing.employlib.viewmodel.activity.ResumeDetailViewModel;
import com.zhixing.netlib.base.BaseResponse;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResumeDetailActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_my_job_end_time)
    TextView tvMyJobEndTime;
    @BindView(R2.id.edit_my_job_company_name)
    EditText editMyJobCompanyName;
    @BindView(R2.id.edit_my_job_name)
    EditText editMyJobName;
    @BindView(R2.id.tv_my_job_start_time)
    TextView tvMyJobStartTime;
    @BindView(R2.id.edit_my_job_desc)
    EditText editMyJobDesc;
    @BindView(R2.id.card_job)
    CardView cardJob;
    @BindView(R2.id.tv_my_school_end_time)
    TextView tvMySchoolEndTime;
    @BindView(R2.id.linearLayout6)
    LinearLayout linearLayout6;
    @BindView(R2.id.edit_my_school_company_name)
    EditText editMySchoolCompanyName;
    @BindView(R2.id.edit_my_school_name)
    EditText editMySchoolName;
    @BindView(R2.id.tv_my_school_start_time)
    TextView tvMySchoolStartTime;
    @BindView(R2.id.edit_my_school_desc)
    EditText editMySchoolDesc;
    @BindView(R2.id.card_shcool)
    CardView cardShcool;
    private String resumeType;
    private int resumeInt;//位置
    private String id;
    private ResumeDetailViewModel resumeDetailViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_resume_detail;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        ACache aCache = ACache.get(this);

        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkSend.setText("保存");

         resumeDetailViewModel = ViewModelProviders.of(this).get(ResumeDetailViewModel.class);
        if (getIntent().hasExtra("resumeInt")){
             resumeInt = getIntent().getIntExtra("resumeInt", 0);
        }


        if (getIntent().hasExtra("resumeType")) {
            resumeType = getIntent().getStringExtra("resumeType");
            if ("1".equals(resumeType)) {
                tvWorkTitle.setText("修改工作经历");
                //工作经历详情进来,修改工作详情
                cardJob.setVisibility(View.VISIBLE);
                cardShcool.setVisibility(View.GONE);
                String jobList = aCache.getAsString("jobList");
                if (!TextUtils.isEmpty(jobList)){
                    Type type2 = new TypeToken< List<CompanyEntity>>() {}.getType();
                    List<CompanyEntity> jobData = GsonUtil.getGson().fromJson(jobList, type2);
                    CompanyEntity companyEntity = jobData.get(resumeInt);
                     tvMyJobEndTime.setText(companyEntity.EndTime);
                     tvMyJobStartTime.setText(companyEntity.StartTime);
                     editMyJobCompanyName.setText(companyEntity.company);
                     editMyJobName.setText(companyEntity.job);
                     editMyJobDesc.setText(companyEntity.desc);
                     id = companyEntity.id;
                }

            } else if ("2".equals(resumeType)) {
                tvWorkTitle.setText("修改教育经历");
                //修改学历详情
                cardJob.setVisibility(View.GONE);
                cardShcool.setVisibility(View.VISIBLE);

                String shcoolList = aCache.getAsString("schoolList");
                if (!TextUtils.isEmpty(shcoolList)){
                    Type type2 = new TypeToken< List<CompanyEntity>>() {}.getType();
                    List<CompanyEntity> shcoolData = GsonUtil.getGson().fromJson(shcoolList, type2);

                    CompanyEntity companyEntity = shcoolData.get(resumeInt);
                    tvMySchoolEndTime.setText(companyEntity.EndTime);
                    tvMySchoolStartTime.setText(companyEntity.StartTime);
                    editMySchoolCompanyName.setText(companyEntity.company);
                    editMySchoolName.setText(companyEntity.job);
                    editMySchoolDesc.setText(companyEntity.desc);
                    id = companyEntity.id;
                }

            } else if ("3".equals(resumeType)) {
                tvWorkTitle.setText("增加工作经历");
                //增加工作经历
                cardJob.setVisibility(View.VISIBLE);
                cardShcool.setVisibility(View.GONE);


            } else if ("4".equals(resumeType)) {
                tvWorkTitle.setText("增加学历经历");
                //增加学历经历
                cardJob.setVisibility(View.GONE);
                cardShcool.setVisibility(View.VISIBLE);

            }


        }


    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send, R2.id.tv_my_job_end_time, R2.id.tv_my_job_start_time, R2.id.tv_my_school_end_time, R2.id.tv_my_school_start_time})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_work_send) {
            if ("1".equals(resumeType)) {
                //修改工作
                if ("请选择".equals(tvMyJobStartTime.getText())) {
                    Toasty.INSTANCE.showToast(this, "请选择时间");
                } else if (TextUtils.isEmpty(editMyJobCompanyName.getText().toString().trim())) {
                    Toasty.INSTANCE.showToast(this, "请输入公司名称");
                } else if (TextUtils.isEmpty(editMyJobName.getText().toString().trim())) {
                    Toasty.INSTANCE.showToast(this, "请输入职位名称");
                } else if (TextUtils.isEmpty(editMyJobDesc.getText().toString().trim())) {
                    Toasty.INSTANCE.showToast(this, "请输入工作描述");
                } else {
                    showDialog("");
                    resumeDetailViewModel.AlterJob(id, editMyJobCompanyName.getText().toString().trim(), editMyJobName.getText().toString().trim(),
                            tvMyJobStartTime.getText().toString(), tvMyJobEndTime.getText().toString(), editMyJobDesc.getText().toString().trim(),
                            "", "", "", "").observe(this, new Observer<BaseResponse>() {
                        @Override
                        public void onChanged(@Nullable BaseResponse baseResponse) {
                            if (baseResponse != null) {
                                dismissDialog();
                                if (baseResponse.getStatus().equals("success")) {
                                    Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "修改成功!!");
                                    AppManager.getAppManager().finishActivity();
                                } else {
                                    Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "修改失败!!");
                                }


                            } else {
                                dismissDialog();
                                Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "修改失败!!");
                            }
                        }
                    });

                    dismissDialog();

                }


            } else if ("2".equals(resumeType)) {
                //修改学历

                if ("请选择".equals(tvMySchoolStartTime.getText())) {
                    Toasty.INSTANCE.showToast(this, "请选择时间");
                } else if (TextUtils.isEmpty(editMySchoolCompanyName.getText().toString().trim())) {
                    Toasty.INSTANCE.showToast(this, "请输入学校名称");
                } else if (TextUtils.isEmpty(editMySchoolName.getText().toString().trim())) {
                    Toasty.INSTANCE.showToast(this, "请输入专业名称");
                } else {
                    showDialog("");
                    resumeDetailViewModel.AlterJob(id, "", "",
                            "", "", "",
                            editMySchoolCompanyName.getText().toString().trim(),
                            editMySchoolName.getText().toString(),
                            tvMySchoolStartTime.getText().toString(),
                            tvMySchoolEndTime.getText().toString()).observe(this, new Observer<BaseResponse>() {
                        @Override
                        public void onChanged(@Nullable BaseResponse baseResponse) {
                            if (baseResponse != null) {
                                dismissDialog();
                                if (baseResponse.getStatus().equals("success")) {
                                    Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "修改成功!!");
                                    AppManager.getAppManager().finishActivity();
                                } else {
                                    Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "修改失败!!");
                                }


                            } else {
                                dismissDialog();
                                Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "修改失败!!");
                            }
                        }
                    });

                    dismissDialog();

                }

            }

         else if ("3".equals(resumeType)) {
            //addjob
            if ("请选择".equals(tvMyJobStartTime.getText())) {
                Toasty.INSTANCE.showToast(this, "请选择时间");
            } else if (TextUtils.isEmpty(editMyJobCompanyName.getText().toString().trim())) {
                Toasty.INSTANCE.showToast(this, "请输入公司名称");
            } else if (TextUtils.isEmpty(editMyJobName.getText().toString().trim())) {
                Toasty.INSTANCE.showToast(this, "请输入职位名称");
            } else if (TextUtils.isEmpty(editMyJobDesc.getText().toString().trim())) {
                Toasty.INSTANCE.showToast(this, "请输入工作描述");
            } else {
                showDialog("");
                resumeDetailViewModel.addJob(editMyJobCompanyName.getText().toString().trim(), editMyJobName.getText().toString().trim(),
                        tvMyJobStartTime.getText().toString(), tvMyJobEndTime.getText().toString(), editMyJobDesc.getText().toString().trim(),
                        "", "", "", "").observe(this, new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse baseResponse) {
                        if (baseResponse != null) {
                            dismissDialog();
                            if (baseResponse.getStatus().equals("success")) {
                                Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "新增成功!!");
                                AppManager.getAppManager().finishActivity();
                            } else {
                                Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "新增失败!!");
                            }


                        } else {
                            dismissDialog();
                            Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "新增失败!!");
                        }
                    }
                });
                dismissDialog();

            }


        } else if ("4".equals(resumeType)) {
            //addshcool
            //addjob
            if ("请选择".equals(tvMySchoolStartTime.getText())) {
                Toasty.INSTANCE.showToast(this, "请选择时间");
            } else if (TextUtils.isEmpty(editMySchoolCompanyName.getText().toString().trim())) {
                Toasty.INSTANCE.showToast(this, "请输入学校名称");
            } else if (TextUtils.isEmpty(editMySchoolName.getText().toString().trim())) {
                Toasty.INSTANCE.showToast(this, "请输入专业名称");
            } else {
                showDialog("");
                resumeDetailViewModel.addJob("", "",
                        "", "", "",
                        editMySchoolCompanyName.getText().toString().trim(),
                        editMySchoolName.getText().toString(),
                        tvMySchoolStartTime.getText().toString(),
                        tvMySchoolEndTime.getText().toString()).observe(this, new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse baseResponse) {
                        if (baseResponse != null) {
                            dismissDialog();
                            if (baseResponse.getStatus().equals("success")) {
                                Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "新增成功!!");
                                AppManager.getAppManager().finishActivity();
                            } else {
                                Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "新增失败!!");
                            }


                        } else {
                            dismissDialog();
                            Toasty.INSTANCE.showToast(ResumeDetailActivity.this, "新增失败!!");
                        }
                    }
                });
                dismissDialog();

            }

        }

    }


         else if (i == R.id.tv_my_job_end_time) {

            ChangeDoubleTime changeDoubleTime =new ChangeDoubleTime(this);
            changeDoubleTime.setSelect(new SelectDoubleTime() {
                @Override
                public void select(String start, String end, long stt, long ed) {
                    String commonTime1 = TimeUtil.getCommonTime1(start);

                    String commonTime2 = TimeUtil.getCommonTime1(end);

                    tvMyJobEndTime.setText(commonTime2);
                    tvMyJobStartTime.setText(commonTime1);
                }

            });
            changeDoubleTime.showSheet();



        } else if (i == R.id.tv_my_job_start_time) {
            ChangeDoubleTime changeDoubleTime =new ChangeDoubleTime(this);
            changeDoubleTime.setSelect(new SelectDoubleTime() {
                @Override
                public void select(String start, String end, long stt, long ed) {
                    String commonTime1 = TimeUtil.getCommonTime1(start);

                    String commonTime2 = TimeUtil.getCommonTime1(end);

                    tvMyJobEndTime.setText(commonTime2);
                    tvMyJobStartTime.setText(commonTime1);
                }

            });
            changeDoubleTime.showSheet();

        } else if (i == R.id.tv_my_school_end_time) {
            ChangeDoubleTime changeDoubleTime =new ChangeDoubleTime(this);
            changeDoubleTime.setSelect(new SelectDoubleTime() {
                @Override
                public void select(String start, String end, long stt, long ed) {
                    String commonTime1 = TimeUtil.getCommonTime1(start);

                    String commonTime2 = TimeUtil.getCommonTime1(end);

                    tvMySchoolEndTime.setText(commonTime2);
                    tvMySchoolStartTime.setText(commonTime1);
                }

            });
            changeDoubleTime.showSheet();

        } else if (i == R.id.tv_my_school_start_time) {
            ChangeDoubleTime changeDoubleTime =new ChangeDoubleTime(this);
            changeDoubleTime.setSelect(new SelectDoubleTime() {
                @Override
                public void select(String start, String end, long stt, long ed) {
                    String commonTime1 = TimeUtil.getCommonTime1(start);

                    String commonTime2 = TimeUtil.getCommonTime1(end);

                    tvMySchoolEndTime.setText(commonTime2);
                    tvMySchoolStartTime.setText(commonTime1);
                }

            });
            changeDoubleTime.showSheet();
        }
    }
}
