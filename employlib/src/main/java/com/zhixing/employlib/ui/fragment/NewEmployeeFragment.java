package com.zhixing.employlib.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.zhixing.www.BaseFragment;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.NewEmployeeAdapt;
import com.zhixing.employlib.model.NewEmployeeEntity;
import com.zhixing.employlib.viewmodel.fragment.TeamViewModel;

import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/14 下午2:05
 * 新员工
 */
public class NewEmployeeFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private TeamViewModel teamViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_new_employee,container,false);
        teamViewModel = ViewModelProviders.of(getActivity()).get(TeamViewModel.class);

        recyclerView  =(RecyclerView)view.findViewById(R.id.recy_new_employee);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
          initData();
          return  view;
    }

    private void initData() {
        teamViewModel.getNewEmployeeData().observe(getActivity(), new Observer<List<NewEmployeeEntity>>() {
            @Override
            public void onChanged(@Nullable List<NewEmployeeEntity> newEmployeeEntities) {

                NewEmployeeAdapt newEmployeeAdapt = new NewEmployeeAdapt(R.layout.item_new_employee, newEmployeeEntities);
                recyclerView.setAdapter(newEmployeeAdapt);
            }
        });
    }

    @Override
    public void process(Message msg) {

    }
}
