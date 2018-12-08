package com.zhixing.work.ui;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.base.zhixing.www.util.GsonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.shuben.contact.lib.event.TypeBean;
import com.zhixing.work.R;
import com.zhixing.work.bean.MeetStatusType;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 会议的状态
 *@author zjq
 *create at 2018/11/30 下午2:20
 */
public class MeetStatusTypeDialog extends DialogFragment {


    private RecyclerView mRecyleView;
    private MeetStatusListAdapter adapter;
    private String jsonData;
     private OnDialogInforCompleted mOnDialogInforCompleted;

    public static MeetStatusTypeDialog newInstance(String jsonString) {
        MeetStatusTypeDialog fragment = new MeetStatusTypeDialog();
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
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_meet_status_type, null);
         mRecyleView=view.findViewById(R.id.recy_dialog_meet_status);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyleView.setLayoutManager(layoutManager);

        Type mType = new TypeToken<List<MeetStatusType>>() {
        }.getType();

        final List<MeetStatusType> datas = GsonUtil.getGson().fromJson(jsonData, mType);


        adapter=new MeetStatusListAdapter(R.layout.item_recy_dialog_meet_status,datas);

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

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.2f;

        window.setAttributes(windowParams);


    }


    public class MeetStatusListAdapter extends BaseQuickAdapter<MeetStatusType, BaseViewHolder> {

        public MeetStatusListAdapter(@LayoutRes int layoutResId, @Nullable List<MeetStatusType> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MeetStatusType item) {

            String name = item.getName();
            helper.setText(R.id.tv_item_recy_dialog_meet_status,item.getName());
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
