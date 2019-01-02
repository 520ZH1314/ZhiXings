package com.zhixing.tpmlib.view;

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
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.util.GsonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.MaintenanceStatusType;


import java.lang.reflect.Type;
import java.util.List;

/**
 * 会议的状态
 *@author zjq
 *create at 2018/11/30 下午2:20
 */
public class TopMaintenanceTypeDialog extends DialogFragment {


    private RecyclerView mRecyleView;
    private MeetStatusListAdapter adapter;
    private String jsonData;
     private OnDialogInforCompleted mOnDialogInforCompleted;

    public static TopMaintenanceTypeDialog newInstance(String jsonString) {
        TopMaintenanceTypeDialog fragment = new TopMaintenanceTypeDialog();
        Bundle args = new Bundle();
        args.putString("jsonString", jsonString);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             jsonData = getArguments().getString("jsonString");

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
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_maintenance_status_type, null);
         mRecyleView=view.findViewById(R.id.recy_dialog_meet_status);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyleView.setLayoutManager(layoutManager);

        Type mType = new TypeToken<List<MaintenanceStatusType>>() {
        }.getType();

        final List<MaintenanceStatusType> datas = GsonUtil.getGson().fromJson(jsonData, mType);


        adapter=new MeetStatusListAdapter(R.layout.item_recy_top_dialog_maintenance_status,datas);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mOnDialogInforCompleted.dialogInforCompleted(datas.get(position).getName());
                dismiss();
            }
        });

        mRecyleView.setAdapter(adapter);
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

        windowParams.y= DensityUtil.dip2px(getContext(),50);
        windowParams.width=dm.widthPixels/3;
        windowParams.dimAmount = 0.2f;
        window.setAttributes(windowParams);


    }


    public class MeetStatusListAdapter extends BaseQuickAdapter<MaintenanceStatusType, BaseViewHolder> {

        public MeetStatusListAdapter(@LayoutRes int layoutResId, @Nullable List<MaintenanceStatusType> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MaintenanceStatusType item) {


            String name = item.getName();
            helper.setText(R.id.tv_item_recy_dialog_top_meet_status,item.getName());
        }

    }


   //接口回调
   public interface OnDialogInforCompleted {
       void dialogInforCompleted(String name);
   }


    public void setOnDialogInforCompleted(OnDialogInforCompleted mOnDialogInforCompleted) {
        this.mOnDialogInforCompleted = mOnDialogInforCompleted;
    }

}
