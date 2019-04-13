package com.zhixing.employlib.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.model.StandScore;
import com.zhixing.employlib.ui.activity.MothIntegralEventActivity;
import com.zhixing.employlib.ui.activity.MyResumeActivity;
import com.zhixing.employlib.viewmodel.activity.MonthViewModel;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 *@author zjq
 *create at 2019/3/25 上午10:15
 * 个人中心
 */
public class PerformanceMineFragment extends BaseLazyFragment {
    private MonthViewModel monthViewModel;
    @BindView(R2.id.iv_appeal_mine_head)
    CircleImageView iv_appeal_mine_head;
    @BindView(R2.id.iv_appeal_mine_bg)
    ImageView  iv_appeal_mine_bg;

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initData() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

          View view=inflater.inflate(R.layout.fragment_performance_mine,container,false);
        ButterKnife.bind(this,view);
           TextView tvResume = view.findViewById(R.id.tv_appeal_mine_right);
        TextView name = view.findViewById(R.id.tv_appeal_mine_name);
        TextView Performance = view.findViewById(R.id.tv_appeal_mine_left);
        ImageView viewById = view.findViewById(R.id.imageView26);
        ImageView imageView1=view.findViewById(R.id.imageView27);
        name.setText( SharedPreferencesTool.getMStool(getActivity()).getUserName());

        monthViewModel = ViewModelProviders.of(this).get(MonthViewModel.class);


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthViewModel.getScoreColor(null).observe(getActivity(), new Observer<List<StandScore>>() {
                    @Override
                    public void onChanged(@Nullable List<StandScore> standScores) {

                        Intent intent = new Intent(getActivity(), MothIntegralEventActivity.class);
                        intent.putExtra("obj", (Serializable) standScores);
                        startActivity(intent);
                    }
                });
            }
        });

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),MyResumeActivity.class);
                startActivity(intent);
            }
        });
           tvResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),MyResumeActivity.class);
                startActivity(intent);

            }
        });


        Performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthViewModel.getScoreColor(null).observe(getActivity(), new Observer<List<StandScore>>() {
                    @Override
                    public void onChanged(@Nullable List<StandScore> standScores) {

                        Intent intent = new Intent(getActivity(), MothIntegralEventActivity.class);
                        intent.putExtra("obj", (Serializable) standScores);
                        startActivity(intent);
                    }
                });
            }
        });
        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String path = SharedPreferencesTool.getMStool(getActivity()).getString("head_ico");//头像
        String ph = SharedPreferencesTool.getMStool(getActivity()).getIp()+path;
        P.c("头像地址"+ph);
        MyImageLoader.load(getActivity(),ph ,iv_appeal_mine_head);
        MyImageLoader.load(getActivity(),ph ,iv_appeal_mine_bg);
    }
}
