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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.inter.SelectDoubleTime;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeDoubleTime;
import com.base.zhixing.www.widget.ChangeTime;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.stateviewlibrary.StateView;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.GradingedListAdapt;
import com.zhixing.employlib.model.GradingedEntity;
import com.zhixing.employlib.model.grading.GradListBean;
import com.zhixing.employlib.viewmodel.activity.GradingedListViewModel;
import com.zhixing.netlib.base.BaseResponse;

import java.text.ParseException;
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
 * create at 2019/3/13 上午10:38
 * 评分记录列表
 */
public class GradingRecordListActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_gradinged_list_start_time)
    TextView tvGradingedListStartTime;
    @BindView(R2.id.tv_gradinged_list_end_time)
    TextView tvGradingedListEndTime;
    @BindView(R2.id.recy_gradinged_list)
    RecyclerView recyGradingedList;

    private GradingedListViewModel gradingedListViewModel;
    private Unbinder bind;
    private GradingedListAdapt gradingedListAdapt;
    private String endTime;
    private String startTime;
    private LinearLayout mLinearLayout;
    private StateView mStateView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_grading_record_list;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        bind = ButterKnife.bind(this);
        mLinearLayout=(LinearLayout) findViewById(R.id.ll_gradinged_record_list);
        mStateView=StateView.inject(mLinearLayout);
        setStatus(-1);
        initView();

    }

    private void initView() {
        //默认显示前七天的事件
        //设置当日日的时间
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        Date lastDay = ca.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(lastDay);
         endTime = TimeUtil.getCommonTime1(format);
        String[] split = endTime.split("-");
        tvGradingedListEndTime.setText(split[0]+"年"+split[1]+"月"+split[2]+"日");


        Calendar ca1 = Calendar.getInstance();//得到一个Calendar的实例
        ca1.setTime(new Date()); //设置时间为当前时间
        ca1.add(Calendar.DATE, -7); //日减1
        Date lastDay1 = ca1.getTime(); //结果
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = sf1.format(lastDay1);
         startTime = TimeUtil.getCommonTime1(format1);
        String[] split1 = startTime.split("-");
        tvGradingedListStartTime.setText(split1[0]+"年"+split1[1]+"月"+split1[2]+"日");






        gradingedListViewModel = ViewModelProviders.of(this).get(GradingedListViewModel.class);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("已评分");
        tvWorkSend.setVisibility(View.GONE);
        recyGradingedList.setLayoutManager(new LinearLayoutManager(this));

        mStateView.showLoading();
        gradingedListViewModel.setDate(startTime,endTime);

        gradingedListViewModel.ListData.observe(this, new Observer<BaseResponse<GradListBean>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<GradListBean> gradListBeanBaseResponse) {

                if (gradListBeanBaseResponse.getRows()!=null){
                    mStateView.showContent();
                    List<GradingedEntity> entities=new ArrayList<>();
                    List<GradListBean> rows = gradListBeanBaseResponse.getRows();
                    for (GradListBean bean:rows) {
                        entities.add(new GradingedEntity(bean.getPhotoURL(),bean.getUserName(),bean.getPositionName(),bean.getScore(),bean.getGrapeName(),bean.getUserCode()));
                    }
                    if (gradingedListAdapt==null){
                        gradingedListAdapt = new GradingedListAdapt(R.layout.item_gradinged_list, entities);
                        recyGradingedList.setAdapter(gradingedListAdapt);
                    }else{
                        gradingedListAdapt.setNewData(entities);
                        recyGradingedList.setAdapter(gradingedListAdapt);
                    }

                    gradingedListAdapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent =new Intent(GradingRecordListActivity.this,GradingedRecordDetalActivity.class);
                            intent.putExtra("GradingedRecordDetalUseCode",entities.get(position).useCode);
                            intent.putExtra("GradingedRecordDetalUseName",entities.get(position).name);
                            intent.putExtra("GradingedRecordDetalStartTime",startTime);
                            intent.putExtra("GradingedRecordDetalEndTime",endTime);

                            startActivity(intent);
                        }
                    });



                }else if ("404".equals(gradListBeanBaseResponse.getStatus())){
                    mStateView.showRetry();
                    mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                        @Override
                        public void onRetryClick() {
                            gradingedListViewModel.setDate(startTime,endTime);
                        }
                    });

                }else{
                    mStateView.showEmpty();
                }






            }
        });
//        gradingedListViewModel.getDatas().observe(this, new Observer<List<GradingedEntity>>() {
//            @Override
//            public void onChanged(@Nullable List<GradingedEntity> gradingedEntities) {
//                gradingedListAdapt = new GradingedListAdapt(R.layout.item_gradinged_list, gradingedEntities);
//                recyGradingedList.setAdapter(gradingedListAdapt);

//            }
//        });


    }

    @OnClick({R2.id.iv_work_add_work, R2.id.tv_gradinged_list_start_time, R2.id.tv_gradinged_list_end_time})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_gradinged_list_start_time) {
            ChangeDoubleTime  changeDoubleTime =new ChangeDoubleTime(this);
            changeDoubleTime.setSelect(new SelectDoubleTime() {
                @Override
                public void select(String start, String end, long stt, long ed) {



                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


                    try
                    {
                        Date endTimeDate = format.parse(end);
                        Date startTimeDate = format.parse(start);

                        if (TimeUtil.differentDays(startTimeDate,endTimeDate)<=30){

                            String commonTime1 = TimeUtil.getCommonTime1(start);
                            startTime=commonTime1;
                            String[] splitDay = commonTime1.split("-");
                            String Year = splitDay[0];
                            String Month = splitDay[1];
                            String Day = splitDay[2];
                            tvGradingedListStartTime.setText(Year+"年"+Month+"月"+Day+"日");
                            String commonTime2 = TimeUtil.getCommonTime1(end);
                            endTime=commonTime2;
                            String[] splitDay1 = commonTime2.split("-");
                            String Year1 = splitDay1[0];
                            String Month1 = splitDay1[1];
                            String Day1 = splitDay1[2];
                            tvGradingedListEndTime.setText(Year1+"年"+Month1+"月"+Day1+"日");
                            gradingedListViewModel.setDate(startTime,endTime);
                            mStateView.showLoading();
                        }else{
                            Toasty.INSTANCE.showToast(GradingRecordListActivity.this,"查询天数太多,请重试");
                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }

            });
            changeDoubleTime.showSheet();





        } else if (i == R.id.tv_gradinged_list_end_time) {

            ChangeDoubleTime  changeDoubleTime =new ChangeDoubleTime(this);
            changeDoubleTime.setSelect(new SelectDoubleTime() {
                @Override
                public void select(String start, String end, long stt, long ed) {
                    String commonTime1 = TimeUtil.getCommonTime1(start);
                    startTime=commonTime1;
                    String[] splitDay = commonTime1.split("-");
                    String Year = splitDay[0];
                    String Month = splitDay[1];
                    String Day = splitDay[2];
                    tvGradingedListStartTime.setText(Year+"年"+Month+"月"+Day+"日");
                    String commonTime2 = TimeUtil.getCommonTime1(end);
                    endTime=commonTime2;
                    String[] splitDay1 = commonTime2.split("-");
                    String Year1 = splitDay1[0];
                    String Month1 = splitDay1[1];
                    String Day1 = splitDay1[2];
                    tvGradingedListEndTime.setText(Year1+"年"+Month1+"月"+Day1+"日");
                    mStateView.showLoading();
                    gradingedListViewModel.setDate(startTime,endTime);
                }

            });
            changeDoubleTime.showSheet();



        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
       dismissDialog();
    }


}
