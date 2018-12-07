package com.zhixing.work.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import com.zhixing.work.R;
import com.zhixing.work.activity.CreateMeettingActivity;
import com.zhixing.work.activity.CreateTaskActivity;

/**
 * 添加任务,日程,会议的dialog
 *@author zjq
 *create at 2018/11/29 上午11:45
 */
public class AddWorkDialog extends DialogFragment implements View.OnClickListener {

    private RadioButton mRadioAddMeet;
    private RadioButton mRadioAddTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.add_work_view, null);
         mRadioAddMeet= view.findViewById(R.id.rad_work_add_meet);
         mRadioAddMeet.setOnClickListener(this);
        mRadioAddTask= view.findViewById(R.id.rad_work_add_task);
        mRadioAddTask.setOnClickListener(this);
        builder.setView(view);
        return builder.create();

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.2f;

        window.setAttributes(windowParams);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.rad_work_add_meet){

               Intent intent = new Intent(this.getContext(), CreateMeettingActivity.class);
               startActivity(intent);
               dismiss();
        }else if(id==R.id.rad_work_add_task){

            Intent intent = new Intent(this.getContext(), CreateTaskActivity.class);
            startActivity(intent);
             dismiss();
        }
    }
}
