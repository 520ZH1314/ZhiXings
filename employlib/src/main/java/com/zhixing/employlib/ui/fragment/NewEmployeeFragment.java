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
import com.zhixing.employlib.model.eventbus.UpdateEmployeeEvent;
import com.zhixing.employlib.model.gardenplot.NewEmployeeBean;
import com.zhixing.employlib.viewmodel.fragment.TeamViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjq
 * create at 2019/3/14 下午2:05
 * 新员工
 */
public class NewEmployeeFragment extends BaseLazyFragment {

    private RecyclerView recyclerView;
    private TeamViewModel teamViewModel;
    private String imgPath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_employee, container, false);
        teamViewModel = ViewModelProviders.of(getActivity()).get(TeamViewModel.class);
        EventBus.getDefault().register(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_new_employee);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private void initDatas() {
//        teamViewModel.getNewEmployeeData().observe(getActivity(), new Observer<List<NewEmployeeEntity>>() {
//            @Override
//            public void onChanged(@Nullable List<NewEmployeeEntity> newEmployeeEntities) {
//
//                NewEmployeeAdapt newEmployeeAdapt = new NewEmployeeAdapt(R.layout.item_new_employee, newEmployeeEntities);
//                recyclerView.setAdapter(newEmployeeAdapt);
//            }
//        });

        teamViewModel.getNewEmployeeData();
        teamViewModel.NewEmployeeDatas.observe(getActivity(), new Observer<List<NewEmployeeBean>>() {
            @Override
            public void onChanged(@Nullable List<NewEmployeeBean> newEmployeeBeans) {
                if (newEmployeeBeans != null) {


                    List<NewEmployeeEntity> datas = new ArrayList<>();
                    for (int i = 0; i < newEmployeeBeans.size(); i++) {
                        if (newEmployeeBeans.get(i).getFiles().size() == 0) {
                            imgPath="";
                        } else {
                            imgPath = newEmployeeBeans.get(i).getFiles().get(0).getFilePath();
                        }


                        datas.add(new NewEmployeeEntity(newEmployeeBeans.get(i).getUserInfo().getNativePlace(), imgPath, newEmployeeBeans.get(i).getUserName(),
                                newEmployeeBeans.get(i).getUserInfo().getPositionName(),
                                newEmployeeBeans.get(i).getUserInfo().getOrganizeName(), clearTime(newEmployeeBeans.get(i).getUserInfo().getJoinWorkDate()), newEmployeeBeans.get(i).getNewDeeds()));
                    }
                    NewEmployeeAdapt newEmployeeAdapt = new NewEmployeeAdapt(R.layout.item_new_employee, datas);
                    recyclerView.setAdapter(newEmployeeAdapt);

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

    public String clearTime(String date) {
//        2007-11-14T00:00:00
        String[] ts = date.split("T");
        String t = ts[0];
        String[] split = t.split("-");
        return split[1] + "月" + split[2] + "日";

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Update(UpdateEmployeeEvent employeeEvent){
        if ("4".equals(employeeEvent.EmPloyeeType)){
            teamViewModel.getNewEmployeeData();
            teamViewModel.NewEmployeeDatas.observe(getActivity(), new Observer<List<NewEmployeeBean>>() {
                @Override
                public void onChanged(@Nullable List<NewEmployeeBean> newEmployeeBeans) {
                    if (newEmployeeBeans != null) {


                        List<NewEmployeeEntity> datas = new ArrayList<>();
                        for (int i = 0; i < newEmployeeBeans.size(); i++) {
                            if (newEmployeeBeans.get(i).getFiles().size() == 0) {
                                imgPath="";
                            } else {
                                imgPath = newEmployeeBeans.get(i).getFiles().get(0).getFilePath();
                            }


                            datas.add(new NewEmployeeEntity(newEmployeeBeans.get(i).getUserInfo().getNativePlace(), imgPath, newEmployeeBeans.get(i).getUserName(),
                                    newEmployeeBeans.get(i).getUserInfo().getPositionName(),
                                    newEmployeeBeans.get(i).getUserInfo().getOrganizeName(), clearTime(newEmployeeBeans.get(i).getUserInfo().getJoinWorkDate()), newEmployeeBeans.get(i).getNewDeeds()));
                        }
                        NewEmployeeAdapt newEmployeeAdapt = new NewEmployeeAdapt(R.layout.item_new_employee, datas);
                        recyclerView.setAdapter(newEmployeeAdapt);

                    }

                }
            });

        }


    }


}
