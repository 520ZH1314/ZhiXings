package com.zhixing.employlib.ui.fragment;


import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.tu.loadingdialog.LoadingDailog;
import com.base.zhixing.www.BaseFragment;
import com.example.stateviewlibrary.StateView;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.ExcellentEmployeeAdapt;
import com.zhixing.employlib.model.ExcellentEmployeeEntity;
import com.zhixing.employlib.model.eventbus.UpdateEmployeeEvent;
import com.zhixing.employlib.model.gardenplot.ExcellentEmployeeBean;
import com.zhixing.employlib.utils.AppUtils;
import com.zhixing.employlib.viewmodel.fragment.TeamViewModel;
import com.zhixing.netlib.base.BaseResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/14 下午2:05
 * 优秀员工
 */
public class ExcellentEmployeeFragment  extends BaseLazyFragment {

    private RecyclerView recyclerView;
    private TeamViewModel teamViewModel;
    private String urlimg;
    private String PositionName;
    private ProgressDialog dialog;
    private LinearLayout mLinearLayout;
    private StateView mStateView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_excellent_employee,container,false);
         teamViewModel = ViewModelProviders.of(getActivity()).get(TeamViewModel.class);
         mLinearLayout= (LinearLayout) view.findViewById(R.id.ll_recy_excellent_employee);
        mStateView=StateView.inject(mLinearLayout);
         EventBus.getDefault().register(this);
         recyclerView  =(RecyclerView)view.findViewById(R.id.recy_excellent_employee);
           recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

          return  view;
    }

    private void initDatas() {
//        teamViewModel.getExcellentEmployeeData().observe(getActivity(), new Observer<List<ExcellentEmployeeEntity>>() {
//            @Override
//            public void onChanged(@Nullable List<ExcellentEmployeeEntity> excellentEmployeeEntities) {
//                ExcellentEmployeeAdapt excellentEmployeeAdapt = new ExcellentEmployeeAdapt(R.layout.item_excellent_employee, excellentEmployeeEntities);
//                recyclerView.setAdapter(excellentEmployeeAdapt);
//            }
//        });

        mStateView.showLoading();
        teamViewModel.getRefrshExcellentEmployeeData(true);
        teamViewModel.excellentEmployee.observe(getActivity(), new Observer<BaseResponse<ExcellentEmployeeBean>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<ExcellentEmployeeBean> excellentEmployeeBeanBaseResponse) {
                if (excellentEmployeeBeanBaseResponse.getRows()!=null){
                    if (excellentEmployeeBeanBaseResponse.getRows().size()!=0){
                        List<ExcellentEmployeeBean> excellentEmployeeBeans = excellentEmployeeBeanBaseResponse.getRows();
                        mStateView.showContent();
                        List<ExcellentEmployeeEntity> data=new ArrayList<>();
                        for (int i = 0; i <excellentEmployeeBeans.size(); i++) {
                            if (excellentEmployeeBeans.get(i).getFiles().size()==0){
                                urlimg="";
                            }else{
                                urlimg=excellentEmployeeBeans.get(i).getFiles().get(0).getFilePath();
                            }
                            if (excellentEmployeeBeans.get(i).getUserInfo()!=null){
                                PositionName=excellentEmployeeBeans.get(i).getUserInfo().getPositionName();
                            }



                            data.add(new ExcellentEmployeeEntity(
                                    excellentEmployeeBeans.get(i).getExcellentType(),
                                    urlimg,excellentEmployeeBeans.get(i).getUserName(),
                                    AppUtils.isNull(PositionName),
                                    excellentEmployeeBeans.get(i).getEventScore()+"",
                                    excellentEmployeeBeans.get(i).getSeq()+"",
                                    excellentEmployeeBeans.get(i).getExcellentDeeds()));

                        }
                        ExcellentEmployeeAdapt excellentEmployeeAdapt = new ExcellentEmployeeAdapt(R.layout.item_excellent_employee, data);
                        recyclerView.setAdapter(excellentEmployeeAdapt);

                    }else{
                        mStateView.showEmpty();
                    }
                }else if ("404".equals(excellentEmployeeBeanBaseResponse.getStatus())){
                    mStateView.showRetry();
                    mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                        @Override
                        public void onRetryClick() {
                            teamViewModel.getRefrshExcellentEmployeeData(true);
                        }
                    });
                }
            }
        });


    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initData() {

        initDatas();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Update(UpdateEmployeeEvent employeeEvent){
        if ("3".equals(employeeEvent.EmPloyeeType)){
            if (mStateView==null){
                mStateView=StateView.inject(mLinearLayout);
            }
            mStateView.showLoading();
            teamViewModel.getRefrshExcellentEmployeeData(true);
            teamViewModel.excellentEmployee.observe(getActivity(), new Observer<BaseResponse<ExcellentEmployeeBean>>() {
                @Override
                public void onChanged(@Nullable BaseResponse<ExcellentEmployeeBean> excellentEmployeeBeanBaseResponse) {
                    if (excellentEmployeeBeanBaseResponse.getRows()!=null){
                        if (excellentEmployeeBeanBaseResponse.getRows().size()!=0){
                            List<ExcellentEmployeeBean> excellentEmployeeBeans = excellentEmployeeBeanBaseResponse.getRows();
                            mStateView.showContent();
                            List<ExcellentEmployeeEntity> data=new ArrayList<>();
                            for (int i = 0; i <excellentEmployeeBeans.size(); i++) {
                                if (excellentEmployeeBeans.get(i).getFiles().size()==0){
                                    urlimg="";
                                }else{
                                    urlimg=excellentEmployeeBeans.get(i).getFiles().get(0).getFilePath();
                                }
                                if (excellentEmployeeBeans.get(i).getUserInfo()!=null){
                                    PositionName=excellentEmployeeBeans.get(i).getUserInfo().getPositionName();
                                }



                                data.add(new ExcellentEmployeeEntity(
                                        excellentEmployeeBeans.get(i).getExcellentType(),
                                        urlimg,excellentEmployeeBeans.get(i).getUserName(),
                                        AppUtils.isNull(PositionName),
                                        excellentEmployeeBeans.get(i).getEventScore()+"",
                                        excellentEmployeeBeans.get(i).getSeq()+"",
                                        excellentEmployeeBeans.get(i).getExcellentDeeds()));

                            }
                            ExcellentEmployeeAdapt excellentEmployeeAdapt = new ExcellentEmployeeAdapt(R.layout.item_excellent_employee, data);
                            recyclerView.setAdapter(excellentEmployeeAdapt);

                        }else{
                            mStateView.showEmpty();
                        }
                    }else if ("404".equals(excellentEmployeeBeanBaseResponse.getStatus())){
                        mStateView.showRetry();
                        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                            @Override
                            public void onRetryClick() {
                                teamViewModel.getRefrshExcellentEmployeeData(true);
                            }
                        });
                    }
                }
            });


        }


    }
}
