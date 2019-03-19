package com.zhixing.employlib.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rmondjone.locktableview.LockTableView;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.IntegralEventAdapt;
import com.zhixing.employlib.model.IntegralEventEntity;
import com.zhixing.employlib.viewmodel.fragment.PerFormanceViewModel;

import java.util.ArrayList;
import java.util.List;



/**
 *
 *@author zjq
 *create at 2019/3/13 下午3:01
 * 评分标准弹窗
 */
public class DialogFragmentGradingedStand extends DialogFragment implements View.OnClickListener {

    private ImageView iv_close;
    private RecyclerView recyclerView;
    private ImageView iv_down;
    private ArrayList<ArrayList<String>> mTableDatas;
    private ArrayList<String> mfristData;
    private LinearLayout ContentView;
    private LockTableView mLockTableView;
    private  ArrayList<String> mRowDatas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_grading_stand, null);
        iv_close = (ImageView) view.findViewById(R.id.iv_gradinged_close);
         ContentView =(LinearLayout)view.findViewById(R.id.record_detail_stand_contentView);

        iv_close.setOnClickListener(this);

        initTable();


        return view;


    }

    /**
     *
     *@author zjq
     *create at 2019/3/13 下午2:17
     * 初始化表格
     */
    private void initTable() {
        mTableDatas = new ArrayList<ArrayList<String>>();
        mfristData = new ArrayList<String>();
        mfristData.add("等级");
        mfristData.add("评定标准");

        mTableDatas.add(mfristData);

        for (int i = 0; i < 3; i++) {
            if (i==0){
                mRowDatas = new ArrayList<>();
                //数据填充
                mRowDatas.add("优");
                mRowDatas.add("得分21-30");
                mTableDatas.add(mRowDatas);
            }else if (i==1){
                mRowDatas = new ArrayList<>();
                //数据填充
                mRowDatas.add("良");
                mRowDatas.add("得分11-20");
                mTableDatas.add(mRowDatas);
            }else if (i==2){
                mRowDatas = new ArrayList<>();
                //数据填充
                mRowDatas.add("差");
                mRowDatas.add("得分0-10");
                mTableDatas.add(mRowDatas);
            }

        }
        mLockTableView = new LockTableView(getActivity(), ContentView, mTableDatas);
        mLockTableView.setLockFristColumn(false) //是否锁定第一列
                .setMaxColumnWidth(200) //列最大宽度
                .setMinColumnWidth(120) //列最小宽度
                .setMinRowHeight(20)//行最小高度
                .setMaxRowHeight(50)//行最大高度
                .setTextViewSize(16) //单元格字体大小
                .setFristRowBackGroudColor(R.color.review_bac)//表头背景色
                .setTableHeadTextColor(R.color.black)//表头字体颜色
                .setTableContentTextColor(R.color.gray)//单元格字体颜色
                .setCellPadding(10)//设置单元格内边距(dp)
                .setNullableString("N/A") //空值替换值
                .show(); //显示表格,此方法必须调用
        mLockTableView.getTableScrollView().setPullRefreshEnabled(false);
        mLockTableView.getTableScrollView().setLoadingMoreEnabled(false);


    }


    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.5f;

        window.setAttributes(windowParams);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_gradinged_close) {
            dismiss();
        }
    }
}
