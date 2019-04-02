package com.zhixing.employlib.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.employlib.R;

/**
 *
 */
public class AppealEditDialog extends DialogFragment {


     private OnDialogInforCompleted mOnDialogInforCompleted;
    private TextView mTvTitle;
    private EditText mTvTitleDesc;
    private Button mDiss;
    private Button mSuccess;
    private String title;
    private String titileDesc;
    private String btn1;
    private String btn2;

    public static AppealEditDialog newInstance(String titile, String titileDesc, String btn1, String btn2) {
        AppealEditDialog fragment = new AppealEditDialog();
        Bundle args = new Bundle();
        args.putString("title", titile);
        args.putString("titileDesc", titileDesc);
        args.putString("btn1", btn1);
        args.putString("btn2", btn2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            titileDesc = getArguments().getString("titileDesc");
            btn1 = getArguments().getString("btn1");
            btn2 = getArguments().getString("btn2");

        }

    }

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
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.appeal_custom_dialog, null);

        mTvTitle=(TextView)view.findViewById(R.id.tv_performance_custom_title);
        mTvTitleDesc= view.findViewById(R.id.tv_performance_custom_title_desc);
        mDiss=(Button)view.findViewById(R.id.btn_performance_custom_diss);
        mSuccess=(Button)view.findViewById(R.id.btn_performance_custom_scuss);

        mTvTitle.setText(title);
        mTvTitleDesc.setHint(titileDesc);
        mDiss.setText(btn1);
        mSuccess.setText(btn2);
        Drawable pressedDrawable = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.white)
                .radius(25)
                .build();
        Drawable normalDrawable = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.event_center)
                .radius(25)
                .build();

        DevShapeUtils
                .selectorPressed(pressedDrawable,normalDrawable)
                .selectorTextColor(R.color.title_bg, R.color.white)
                .into(mDiss);

        Drawable pressedDrawable1 = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.white)
                .radius(25)
                .build();
        Drawable normalDrawable1 = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.title_bg)
                .radius(25)
                .build();

        DevShapeUtils
                .selectorPressed(pressedDrawable1,normalDrawable1)
                .selectorTextColor(R.color.title_bg, R.color.white)
                .into(mSuccess);



        mDiss.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mOnDialogInforCompleted.dialogInforCompleted("2");
                 dismiss();
             }
         });
        mSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDialogInforCompleted.dialogInforCompleted("1");
                dismiss();
            }
        });


        builder.setView(view);
        return builder.create();

    }
    public String getRes(){
       return mTvTitleDesc.getText().toString();
    }


    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.gravity=Gravity.CENTER;
        windowParams.dimAmount = 0.2f;
        window.setAttributes(windowParams);


    }





   //接口回调
   public interface OnDialogInforCompleted {
       void dialogInforCompleted(String name);
   }


    public void setOnDialogInforCompleted(OnDialogInforCompleted mOnDialogInforCompleted) {
        this.mOnDialogInforCompleted = mOnDialogInforCompleted;
    }

}
