package com.zhixing.work.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.zhixing.www.BaseFragment;
import com.zhixing.work.R;


/**
 *我收到
 *@author zjq
 *create at 2018/11/28 下午3:01
 */
public class MyReceiveFragment  extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_receive, container, false);

        return view;
    }
    @Override
    public void process(Message msg) {

    }
}
