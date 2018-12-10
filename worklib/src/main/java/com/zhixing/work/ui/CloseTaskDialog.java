package com.zhixing.work.ui;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.base.zhixing.www.view.Toasty;
import com.zhixing.work.R;

//关闭dialog
public class CloseTaskDialog  extends DialogFragment implements View.OnClickListener {


    private String title;
    private OnCloseDialogInforCompleted  mOnCloseDialogInforCompleted;
    private TextView mTvTitle;
    private EditText mEditText;
    private Button mBtnDis;
    private Button mBtnSure;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.close_task_dialog, null);
        mTvTitle= view.findViewById(R.id.tv_close_title);//标题
         mEditText= view.findViewById(R.id.ed_close_reason);//输入原因
         mBtnDis=view.findViewById(R.id.btn_close_task_dis);//取消
         mBtnSure=view.findViewById(R.id.btn_close_task_sure);//确定
         mBtnDis.setOnClickListener(this);
         mBtnSure.setOnClickListener(this);
           mTvTitle.setText(title);
          builder.setView(view);
        return builder.create();
    }

    public static CloseTaskDialog newInstance(String jsonString) {
        CloseTaskDialog fragment = new CloseTaskDialog();
        Bundle args = new Bundle();
        args.putString("title", jsonString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);


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
        if (id==R.id.btn_close_task_sure){
            String trim = mEditText.getText().toString().trim();
            if (TextUtils.isEmpty(trim)){
                Toasty.INSTANCE.showToast(getActivity(),"原因不能为空");
            }else{
                mOnCloseDialogInforCompleted.closeDialogInforCompleted(trim);
                dismiss();
            }
        }else if (id==R.id.btn_close_task_dis)
        {

            dismiss();
        }


    }


    //接口回调
    public interface OnCloseDialogInforCompleted {
        void closeDialogInforCompleted(String text);
    }


    public void setOnCloseDialogInforCompleted(OnCloseDialogInforCompleted mOnCloseDialogInforCompleted) {
        this.mOnCloseDialogInforCompleted = mOnCloseDialogInforCompleted;
    }


}
