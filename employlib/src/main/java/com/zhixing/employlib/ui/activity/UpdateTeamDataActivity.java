package com.zhixing.employlib.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UpdateTeamDataActivity extends BaseActvity {
    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_updata_team_select_type)
    TextView tvUpdataTeamSelectType;
    @BindView(R2.id.cl_update_select_type)
    ConstraintLayout clUpdateSelectType;
    @BindView(R2.id.tv_updata_team_select_people)
    TextView tvUpdataTeamSelectPeople;
    @BindView(R2.id.cl_update_select_people)
    ConstraintLayout clUpdateSelectPeople;
    @BindView(R2.id.editText)
    EditText editText;
    @BindView(R2.id.cl_update_team)
    ConstraintLayout clUpdateTeam;
    @BindView(R2.id.tv_updata_team_select_work)
    TextView tvUpdataTeamSelectWork;
    @BindView(R2.id.cl_update_select_work)
    ConstraintLayout clUpdateSelectWork;
    @BindView(R2.id.tv_updata_team_select_address)
    TextView tvUpdataTeamSelectAddress;
    @BindView(R2.id.cl_update_select_address)
    ConstraintLayout clUpdateSelectAddress;
    @BindView(R2.id.tv_updata_team_select_score)
    TextView tvUpdataTeamSelectScore;
    @BindView(R2.id.cl_update_select_score)
    ConstraintLayout clUpdateSelectScore;
    @BindView(R2.id.tv_updata_team_select_rank)
    TextView tvUpdataTeamSelectRank;
    @BindView(R2.id.cl_update_select_rank)
    ConstraintLayout clUpdateSelectRank;
    @BindView(R2.id.tv_updata_team_select_team_name)
    TextView tvUpdataTeamSelectTeamName;
    @BindView(R2.id.cl_update_select_team_name)
    ConstraintLayout clUpdateSelectTeamName;
    @BindView(R2.id.tv_updata_team_select_team_go_time)
    TextView tvUpdataTeamSelectTeamGoTime;
    @BindView(R2.id.cl_update_select_team_go_time)
    ConstraintLayout clUpdateSelectTeamGoTime;
    @BindView(R2.id.iv_out_carme_photo)
    ImageView ivOutCarmePhoto;
    @BindView(R2.id.cardView_iv_out_ad)
    CardView cardViewIvOutAd;
    @BindView(R2.id.iv_out_carme_delete)
    ImageView ivOutCarmeDelete;
    @BindView(R2.id.iv_out_carme)
    ImageView ivOutCarme;
    @BindView(R2.id.ed_out_ad_desc)
    EditText edOutAdDesc;
    private Unbinder bind;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_team_data;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
         bind = ButterKnife.bind(this);
    }



    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send, R2.id.cl_update_select_type, R2.id.cl_update_select_people, R2.id.cardView_iv_out_ad, R2.id.iv_out_carme_delete, R2.id.iv_out_carme})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();

        } else if (i == R.id.tv_work_send) {
        } else if (i == R.id.cl_update_select_type) {

            startActivity(SelectTeamTypeActivity.class);

        } else if (i == R.id.cl_update_select_people) {
        } else if (i == R.id.cardView_iv_out_ad) {
        } else if (i == R.id.iv_out_carme_delete) {
        } else if (i == R.id.iv_out_carme) {
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    public  void selectType(String name){
        if ("优秀员工".equals(name)){
            
        }






    }








}
