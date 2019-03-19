package com.zhixing.employlib.ui.fragment;


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

import com.base.zhixing.www.BaseFragment;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.ExcellentEmployeeAdapt;
import com.zhixing.employlib.model.ExcellentEmployeeEntity;
import com.zhixing.employlib.viewmodel.fragment.TeamViewModel;

import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/14 下午2:05
 * 优秀员工
 */
public class ExcellentEmployeeFragment  extends BaseFragment {

    private RecyclerView recyclerView;
    private TeamViewModel teamViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_excellent_employee,container,false);
        teamViewModel = ViewModelProviders.of(getActivity()).get(TeamViewModel.class);

        recyclerView  =(RecyclerView)view.findViewById(R.id.recy_excellent_employee);
           recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
          initData();
          return  view;
    }

    private void initData() {
        teamViewModel.getExcellentEmployeeData().observe(getActivity(), new Observer<List<ExcellentEmployeeEntity>>() {
            @Override
            public void onChanged(@Nullable List<ExcellentEmployeeEntity> excellentEmployeeEntities) {
                ExcellentEmployeeAdapt excellentEmployeeAdapt = new ExcellentEmployeeAdapt(R.layout.item_excellent_employee, excellentEmployeeEntities);
                recyclerView.setAdapter(excellentEmployeeAdapt);
            }
        });
    }

    @Override
    public void process(Message msg) {

    }
}
