package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.view.Toasty;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.viewmodel.activity.MyRecommendViewModel;
import com.zhixing.netlib.base.BaseResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
public class MyRecommendActivity extends BaseActvity {

    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.ed_recommend_name)
    EditText edRecommendName;
    @BindView(R2.id.ed_recommend_age)
    EditText edRecommendAge;
    @BindView(R2.id.rb_recommend_man)
    RadioButton rbRecommendMan;
    @BindView(R2.id.rb_recommend_woman)
    RadioButton rbRecommendWoman;
    @BindView(R2.id.ed_recommend_num)
    EditText edRecommendNum;
    @BindView(R2.id.ed_recommend_id_num)
    EditText edRecommendIdNum;
    @BindView(R2.id.ed_recommend_education)
    EditText edRecommendEducation;
    @BindView(R2.id.ed_recommend_address)
    EditText edRecommendAddress;
    @BindView(R2.id.ed_recommend_skill)
    EditText edRecommendSkill;
    @BindView(R2.id.ed_recommend_university)
    EditText edRecommendUniversity;
    @BindView(R2.id.ed_to_recommend_phone)
    EditText edToRecommendPhone;
    @BindView(R2.id.ed_to_recommend_reason)
    EditText edToRecommendReason;
    private Unbinder bind;
    private MyRecommendViewModel myRecommendViewModel;
    private String recommendId;
    private String recommendName;
    private String age;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_recommend;

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

if (getIntent().hasExtra("RecommendId")){
     recommendId = getIntent().getStringExtra("RecommendId");
}
if (getIntent().hasExtra("RecommendName")){
     recommendName = getIntent().getStringExtra("RecommendName");
}


        tvWorkTitle.setText("推荐");
        ivWorkAddWork.setImageResource(R.mipmap.back);
         myRecommendViewModel = ViewModelProviders.of(this).get(MyRecommendViewModel.class);
    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_work_send) {
            if (TextUtils.isEmpty(edRecommendName.getText())) {
                Toasty.INSTANCE.showToast(this, "被推荐人姓名不能为空");

            } else if (TextUtils.isEmpty(edRecommendAge.getText())) {
                Toasty.INSTANCE.showToast(this, "被推荐人年龄不能为空");

            } else if (TextUtils.isEmpty(edRecommendNum.getText())) {
                Toasty.INSTANCE.showToast(this, "被推荐人手机号不能为空");

            } else if (TextUtils.isEmpty(edToRecommendPhone.getText())) {
                Toasty.INSTANCE.showToast(this, "推荐人手机号不能为空");

            } else if (TextUtils.isEmpty(edToRecommendReason.getText())) {
                Toasty.INSTANCE.showToast(this, "推荐理由不能为空");

            }else{
                if (rbRecommendMan.isChecked()){
                    age ="1";
                }else if (rbRecommendWoman.isChecked()){
                     age ="2";
                }
//                String ApplyName,
//                String ApplySex,
//                String Phone,
//                String IDCardNo,
//                String NativePlace,
//                String MaxDegree,
//                String Major,
//                String School,
//                String RefPhone,
//                String RefContent,
//                String RefferId,
//                String RefferName,String Reid,String ReName
                myRecommendViewModel.PutJob(getEdit(edRecommendName),age,getEdit(edRecommendNum),
                      getEdit(edRecommendIdNum),getEdit(edRecommendAddress),getEdit(edRecommendEducation),
                        getEdit(edRecommendSkill),getEdit(edRecommendUniversity),getEdit(edToRecommendPhone),getEdit(edToRecommendReason),SharedPreferencesTool.getMStool(this).getUserId(),SharedPreferencesTool.getMStool(this).getUserName(),recommendId,recommendName);



                myRecommendViewModel.baseResponseMutableLiveData.observe(this, new Observer<BaseResponse>() {
                    @Override
                    public void onChanged(@Nullable BaseResponse baseResponse) {
                        if (baseResponse!=null){
                            if ("success".equals(baseResponse.getStatus())){
                                //推荐成功
                                Toasty.INSTANCE.showToast(MyRecommendActivity.this,"推荐成功");
                                AppManager.getAppManager().finishActivity();
                            }else{
                                Toasty.INSTANCE.showToast(MyRecommendActivity.this,"推荐失败");

                            }



                        }
                    }
                });


            }


        }
    }


    public String getEdit(EditText text){

        return  text.getText().toString().trim();
    }

}
