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
import com.zhixing.employlib.adapter.RecruitDeiveredAdapt;
import com.zhixing.employlib.model.RecruitDeiveredEntity;
import com.zhixing.employlib.model.recrui.RecruitListBean;
import com.zhixing.employlib.viewmodel.activity.RecruitRecordActivityViewModel;
import com.zhixing.employlib.viewmodel.fragment.RecruitRecordViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/18 下午2:35
 * 已投递记录界面
 */
public class RecruitRecommendFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RecruitRecordActivityViewModel recruitRecordViewModel;
    private RecruitDeiveredAdapt adapt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_recruit_recommend,container,false);
        recruitRecordViewModel = ViewModelProviders.of(getActivity()).get(RecruitRecordActivityViewModel.class);
        recyclerView = (RecyclerView)view.findViewById(R.id.recy_recommend);
         recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

         initData();

         return  view;
    }

    private void initData() {

        recruitRecordViewModel.JobList.observe(getActivity(), new Observer<List<RecruitListBean>>() {
            @Override
            public void onChanged(@Nullable List<RecruitListBean> recruitListBeans) {
                if (recruitListBeans!=null){

                    List<RecruitDeiveredEntity> entities=new ArrayList<>();
                    for (int i = 0; i < recruitListBeans.size(); i++) {

                        if ("1".equals(recruitListBeans.get(i).getApplyType()+"")){
                            entities.add(new RecruitDeiveredEntity(recruitListBeans.get(i).getJobPost(),
                                    clearTime(recruitListBeans.get(i).getCreateDate()),
                                    recruitListBeans.get(i).getJobSkills(),
                                    recruitListBeans.get(i).getJobSalaryMin()/1000+"K"+"-"+recruitListBeans.get(i).getJobSalarMax()/1000+"K",
                                    recruitListBeans.get(i).getState()+"","1"));
                        }


                    }
                    adapt =new RecruitDeiveredAdapt(R.layout.item_delivered,entities);
                    recyclerView.setAdapter(adapt);
                }
            }
        });



    }

    @Override
    public void process(Message msg) {

    }
    public String clearTime(String date) {
//        2007-11-14T00:00:00
        String[] ts = date.split("T");
        String t = ts[0];
        String[] split = t.split("-");
        return split[1] + "月" + split[2] + "日";

    }
}
