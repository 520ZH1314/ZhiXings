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
import com.zhixing.employlib.viewmodel.fragment.RecruitRecordViewModel;

import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/18 下午2:35
 * 已投递记录界面
 */
public class RecruitRecommendFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RecruitRecordViewModel recruitRecordViewModel;
    private RecruitDeiveredAdapt adapt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_recruit_recommend,container,false);
         recruitRecordViewModel = ViewModelProviders.of(getActivity()).get(RecruitRecordViewModel.class);
        recyclerView = (RecyclerView)view.findViewById(R.id.recy_recommend);
         recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

         initData();

         return  view;
    }

    private void initData() {

        recruitRecordViewModel.getRecommendDatas();
        recruitRecordViewModel.recommendDatas.observe(getActivity(), new Observer<List<RecruitDeiveredEntity>>() {
            @Override
            public void onChanged(@Nullable List<RecruitDeiveredEntity> recruitDeiveredEntities) {
                if (recruitDeiveredEntities!=null){
                     adapt =new RecruitDeiveredAdapt(R.layout.item_delivered,recruitDeiveredEntities);
                     recyclerView.setAdapter(adapt);
                }


            }
        });



    }

    @Override
    public void process(Message msg) {

    }
}
