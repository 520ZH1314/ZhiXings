package com.zhixing.employlib.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.util.GsonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;

import java.lang.reflect.Type;
import java.util.List;

/**
 *
 */
public class TypeDialog extends DialogFragment {



     private OnDialogInforCompleted mOnDialogInforCompleted;

    public static TypeDialog newInstance() {
        TypeDialog fragment = new TypeDialog();

        return fragment;
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
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.item_type, null);
         TextView tv1 =view.findViewById(R.id.tv_updata_team);
         TextView tv2 =view.findViewById(R.id.tv_go_g_gao);

         tv1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mOnDialogInforCompleted.dialogInforCompleted("1");
                 dismiss();
             }
         });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDialogInforCompleted.dialogInforCompleted("2");
                dismiss();
            }
        });



        builder.setView(view);
        return builder.create();

    }



    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.gravity=Gravity.RIGHT|Gravity.TOP;

        windowParams.y= DensityUtil.dip2px(getContext(),55);
        windowParams.width=dm.widthPixels/3;
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
