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
import com.base.zhixing.www.util.TimeUtil;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.BetterTeamEmployeeAdapt;
import com.zhixing.employlib.model.BetterTeamEmployeeEntity;
import com.zhixing.employlib.model.eventbus.UpdateEmployeeEvent;
import com.zhixing.employlib.model.gardenplot.TeamDemeanorBean;
import com.zhixing.employlib.viewmodel.fragment.TeamViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjq
 * create at 2019/3/14 下午2:05
 * 班组天地
 */
public class BetterTeamEmployeeFragment extends BaseLazyFragment {

    private RecyclerView recyclerView;
    private TeamViewModel teamViewModel;
    private String filePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_better_team_employee, container, false);

        teamViewModel = ViewModelProviders.of(getActivity()).get(TeamViewModel.class);
        EventBus.getDefault().register(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_better_team_employee);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return view;
    }

    private void initDatas() {

//        teamViewModel.getBetterTeamData().observe(getActivity(), new Observer<List<BetterTeamEmployeeEntity>>() {
//            @Override
//            public void onChanged(@Nullable List<BetterTeamEmployeeEntity> betterTeamEmployeeEntities) {
//                BetterTeamEmployeeAdapt betterTeamEmployeeAdapt = new BetterTeamEmployeeAdapt(R.layout.item_better_team, betterTeamEmployeeEntities);
//                recyclerView.setAdapter(betterTeamEmployeeAdapt);
//            }
//        }
// );
          teamViewModel.getBetterTeamData();
        teamViewModel.listLiveData.observe(getActivity(), new Observer<List<TeamDemeanorBean>>() {
            @Override
            public void onChanged(@Nullable List<TeamDemeanorBean> teamDemeanorBeans) {
                if (teamDemeanorBeans != null) {
                    List<BetterTeamEmployeeEntity> datas = new ArrayList<>();

                    for (int i = 0; i < teamDemeanorBeans.size(); i++) {

                        if (teamDemeanorBeans.get(i).getFiles().size() == 0) {
                            filePath="";
                        } else {
                            filePath = teamDemeanorBeans.get(i).getFiles().get(0).getFilePath();
                        }


                        datas.add(new BetterTeamEmployeeEntity(teamDemeanorBeans.get(i).getDemeanorTitle(), filePath,
                                teamDemeanorBeans.get(i).getTeamName(),
                                clearTime(teamDemeanorBeans.get(i).getCreateTime()),
                                teamDemeanorBeans.get(i).getDemeanorContent()));

                    }
                    BetterTeamEmployeeAdapt betterTeamEmployeeAdapt = new BetterTeamEmployeeAdapt(R.layout.item_better_team, datas);
                    recyclerView.setAdapter(betterTeamEmployeeAdapt);


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
        String[] ts = date.split("T");
        String t = ts[0];
        String commonTime1 = TimeUtil.getCommonTime1(t);

        return commonTime1;

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Update(UpdateEmployeeEvent employeeEvent){
        if ("5".equals(employeeEvent.EmPloyeeType)){
            teamViewModel.getBetterTeamData();

            teamViewModel.listLiveData.observe(getActivity(), new Observer<List<TeamDemeanorBean>>() {
                @Override
                public void onChanged(@Nullable List<TeamDemeanorBean> teamDemeanorBeans) {
                    if (teamDemeanorBeans != null) {
                        List<BetterTeamEmployeeEntity> datas = new ArrayList<>();

                        for (int i = 0; i < teamDemeanorBeans.size(); i++) {

                            if (teamDemeanorBeans.get(i).getFiles().size() == 0) {
                                filePath="";
                            } else {
                                filePath = teamDemeanorBeans.get(i).getFiles().get(0).getFilePath();
                            }


                            datas.add(new BetterTeamEmployeeEntity(teamDemeanorBeans.get(i).getDemeanorTitle(), filePath,
                                    teamDemeanorBeans.get(i).getTeamName(),
                                    clearTime(teamDemeanorBeans.get(i).getCreateTime()),
                                    teamDemeanorBeans.get(i).getDemeanorContent()));

                        }
                        BetterTeamEmployeeAdapt betterTeamEmployeeAdapt = new BetterTeamEmployeeAdapt(R.layout.item_better_team, datas);
                        recyclerView.setAdapter(betterTeamEmployeeAdapt);


                    }
                }
            });

        }


    }
}
