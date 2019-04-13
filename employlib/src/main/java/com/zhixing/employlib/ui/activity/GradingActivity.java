package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeTime;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.GradingListAdapt;
import com.zhixing.employlib.model.GradingItemEntity;
import com.zhixing.employlib.model.eventbus.GradingEvent;
import com.zhixing.employlib.model.grading.GradListBean;
import com.zhixing.employlib.viewmodel.activity.GradingVIewModel;
import com.zhixing.netlib.base.BaseResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author zjq
 * create at 2019/3/11 下午2:51
 * <p>
 * 评分员工列表
 */
public class GradingActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_grading_list_year)
    TextView tvGradingListYear;
    @BindView(R2.id.tv_grading_list_moth)
    TextView tvGradingListMoth;
    @BindView(R2.id.tv_grading_list_day)
    TextView tvGradingListDay;
    @BindView(R2.id.recy_grading_list)
    RecyclerView recyGradingList;
    @BindView(R2.id.tv_go_grading)
    TextView tvGoGrading;
    @BindView(R2.id.rl_grading)
    RelativeLayout rlGrading;
    private Unbinder bind;
    private GradingVIewModel gradingVIewModel;

    private boolean isSelected = false;//是否编辑

    private GradingListAdapt gradingListAdapt;

    private List<GradingItemEntity> itemEntities = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_grading;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        setStatus(-1);
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();

        initData();


    }

    private void initData() {
        showDialog("");
        gradingVIewModel.ListData.observe(this, new Observer<BaseResponse<GradListBean>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<GradListBean> gradListBeanBaseResponse) {
                if (gradListBeanBaseResponse.getRows()!=null){
                    tvWorkSend.setVisibility(View.VISIBLE);
                    List<GradingItemEntity> gradingItemEntities=new ArrayList<>();
                    List<GradListBean> rows = gradListBeanBaseResponse.getRows();
                    for (GradListBean bean: rows) {
                        gradingItemEntities.add(new GradingItemEntity(bean.getPhotoURL(),bean.getUserName(),bean.getSex(),bean.getPositionName(),bean.getEventCount(),bean.getUserCode())) ;
                    }
                    dismissDialog();
                    gradingListAdapt = new GradingListAdapt(R.layout.item_grading_list, gradingItemEntities);
                    recyGradingList.setAdapter(gradingListAdapt);


                }else{
                    dismissDialog();
                    tvWorkSend.setVisibility(View.GONE);

                }
            }
        });


    }

    private void initView() {
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("我要评分");
        tvWorkSend.setText("编辑");
        recyGradingList.setLayoutManager(new LinearLayoutManager(this));
        gradingVIewModel = ViewModelProviders.of(this).get(GradingVIewModel.class);
        //默认显示昨天日子
        //设置前一日的时间
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //日减1
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(lastDay);
        String commonTime1 = TimeUtil.getCommonTime1(format);
        String[] splitDay = commonTime1.split("-");
        String Year = splitDay[0];
        String Month = splitDay[1];
        String Day = splitDay[2];
        tvGradingListYear.setText(Year + "年");
        tvGradingListMoth.setText(Month + "月");
        tvGradingListDay.setText(Day + "日");
        gradingVIewModel.setDate(commonTime1);



    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send, R2.id.tv_grading_list_day, R2.id.rl_grading})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {

            AppManager.getAppManager().finishActivity();

        } else if (i == R.id.tv_work_send) {

            if (!isSelected) {
                //点击编辑界面
                rlGrading.setVisibility(View.VISIBLE);
                tvWorkSend.setText("取消");
                gradingListAdapt.setIsSelect(true);
                gradingListAdapt.notifyDataSetChanged();
                isSelected=true;
            } else {

                rlGrading.setVisibility(View.GONE);
                tvWorkSend.setText("编辑");
                gradingListAdapt.setIsSelect(false);
                gradingListAdapt.notifyDataSetChanged();
                isSelected=false;

            }


        }


     else if(i ==R.id.tv_grading_list_day)

    {
        ChangeTime changeTime = new ChangeTime(this, "", 2);
        changeTime.setSelect(new SelectTime() {
            @Override
            public void select(String time, long timestp) {
                String commonTime1 = TimeUtil.getCommonTime1(time);
                String[] splitDay = commonTime1.split("-");
                String Year = splitDay[0];
                String Month = splitDay[1];
                String Day = splitDay[2];
                tvGradingListYear.setText(Year + "年");
                tvGradingListMoth.setText(Month + "月");
                tvGradingListDay.setText(Day + "日");
                showDialog("");
              gradingVIewModel.setDate(commonTime1);
            }
        });
        changeTime.showSheet();


    }else if(i==R.id.rl_grading){
            List<GradingItemEntity> selectData = gradingListAdapt.getSelectData();
            if (selectData.size()==0){
                Toasty.INSTANCE.showToast(this,"请选择员工!!");
            }else{
                String json = GsonUtil.getGson().toJson(selectData);
                Intent intent =new Intent(GradingActivity.this,GradingDetailActivity.class);
                intent.putExtra("type","2");
                intent.putExtra("selectData",json);
                startActivity(intent);
            }
    }
}



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(GradingEvent event){
        if (!event.isSelect){
            rlGrading.setVisibility(View.GONE);
            tvWorkSend.setText("编辑");
            gradingListAdapt.setIsSelect(false);
            gradingListAdapt.notifyDataSetChanged();
            isSelected=false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        EventBus.getDefault().unregister(this);
        dismissDialog();

    }


}






