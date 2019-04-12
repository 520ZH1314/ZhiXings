package com.zhixing.employlib.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.R;
import com.zhixing.employlib.ui.activity.MothIntegralEventActivity;
import com.zhixing.employlib.ui.activity.MyResumeActivity;

/**
 *
 *@author zjq
 *create at 2019/3/25 上午10:15
 * 个人中心
 */
public class PerformanceMineFragment extends BaseLazyFragment {
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
           TextView tvResume = view.findViewById(R.id.tv_appeal_mine_right);
        TextView name = view.findViewById(R.id.tv_appeal_mine_name);
        TextView Performance = view.findViewById(R.id.tv_appeal_mine_left);
        name.setText( SharedPreferencesTool.getMStool(getActivity()).getUserName());


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
                Intent intent =new Intent(getContext(),MothIntegralEventActivity.class);
                startActivity(intent);
            }
        });
        return  view;
    }
}
