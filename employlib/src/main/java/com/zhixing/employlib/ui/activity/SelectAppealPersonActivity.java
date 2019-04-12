package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.orhanobut.logger.Logger;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.AppealPersonEventAdapt;
import com.zhixing.employlib.adapter.GradingEventAdapt;
import com.zhixing.employlib.model.AppealPersonEntity;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.eventbus.GradingEventBean;
import com.zhixing.employlib.repertory.AppealRepertory;
import com.zhixing.employlib.viewmodel.activity.AppealPersonViewModel;
import com.zhixing.employlib.viewmodel.fragment.PerFormanceViewModel;
import com.zhixing.netlib.base.BaseResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 个人关键事件
 * @author cloor
 */

public class SelectAppealPersonActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.recy_grading_event_list)
    RecyclerView recyGradingEventList;
    private Unbinder bind;
    private AppealPersonViewModel appealPersonViewModel;
    private AppealPersonEventAdapt appealPersonEventAdapt;
//    private List<GradingEventEntity> mList;
    @Override
    public int getLayoutId() {
        return R.layout.activity_select_event;
    }

    @Override
    public void process(Message msg) {

    }
    String time = null;
    @Override
    public void initLayout() {

         bind = ButterKnife.bind(this);
        setStatus(-1);
        if(getIntent().hasExtra("time"))
            time = getIntent().getStringExtra("time");
        initView();
        initData();

    }

    private void initData() {
       // mList=new ArrayList<>();
//        PerFormanceViewModel integralEventViewModel = ViewModelProviders.of(this).get(PerFormanceViewModel.class);

        appealPersonViewModel.getAppealPersonData(time).observe(this, new Observer<List<AppealPersonEntity>>() {
            @Override
            public void onChanged(@Nullable List<AppealPersonEntity> appealPersonEntities) {
                if(appealPersonEntities!=null)
                {
                    appealPersonEventAdapt = new AppealPersonEventAdapt(R.layout.item_grading_event, appealPersonEntities);
                    recyGradingEventList.setAdapter(appealPersonEventAdapt);
                }
            }
        });

    }

    private void initView() {
        appealPersonViewModel = ViewModelProviders.of(this).get(AppealPersonViewModel.class);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkSend.setText("完成");
        tvWorkTitle.setText("个人关键事件");
        recyGradingEventList.setLayoutManager(new LinearLayoutManager(this));

    }



    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {

            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_work_send) {
          //  EventBus.getDefault().post(new GradingEventBean(appealPersonEventAdapt.getSelectedPosData().getItemName(),appealPersonEventAdapt.getSelectedPosData().getScore()));
            Intent intent = new Intent();
            intent.putExtra("KeyId", appealPersonEventAdapt.getSelectedPosData().getItemId());
            intent.putExtra("KeyName",     appealPersonEventAdapt.getSelectedPosData().getItemName());
            intent.putExtra("time",appealPersonEventAdapt.getSelectedPosData().getCreateTime());
//            P.c("选择的是"+appealPersonEventAdapt.getSelectedPosData().getItemName());
            setResult(RESULT_OK,intent);
            //关闭
            AppManager.getAppManager().finishActivity();


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
