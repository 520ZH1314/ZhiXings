package com.zhixing.employlib.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.base.zhixing.www.BaseFragment;
import com.rmondjone.locktableview.DisplayUtil;
import com.rmondjone.locktableview.LockTableView;
import com.rmondjone.xrecyclerview.ProgressStyle;
import com.rmondjone.xrecyclerview.XRecyclerView;
import com.zhixing.employlib.R;

import java.util.ArrayList;

/**
 *
 *@author zjq
 *create at 2019/3/5 下午2:28
 * 个人绩效排名
 */

public class PersonRankFragment  extends BaseFragment {

    private LinearLayout mContentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_personrank,container,false);
        mContentView = (LinearLayout) view.findViewById(R.id.contentView);
        initDisplayOpinion();
        initData();
         return view;
    }

    private void initData() {
        //构造假数据
        ArrayList<ArrayList<String>> mTableDatas = new ArrayList<ArrayList<String>>();
        ArrayList<String> mfristData = new ArrayList<String>();
        mfristData.add("工号");
        mfristData.add("姓名");
        mfristData.add("评分");
        mfristData.add("排名");
        mfristData.add("等级");


        mTableDatas.add(mfristData);
        for (int i = 0; i < 10; i++) {
            ArrayList<String> mRowDatas = new ArrayList<String>();
            //数据填充
            mRowDatas.add("00"+i);
            mRowDatas.add("张三"+i);
            mRowDatas.add(i+"");
            mRowDatas.add(i+"");
            mRowDatas.add("优秀");
            mTableDatas.add(mRowDatas);
        }

        final LockTableView mLockTableView = new LockTableView(getActivity(), mContentView, mTableDatas);
        mLockTableView.setLockFristColumn(true) //是否锁定第一列
                .setMaxColumnWidth(100) //列最大宽度
                .setMinColumnWidth(60) //列最小宽度
                .setMinRowHeight(20)//行最小高度
                .setMaxRowHeight(60)//行最大高度
                .setTextViewSize(16) //单元格字体大小
                .setFristRowBackGroudColor(R.color.title_bg)//表头背景色
                .setTableHeadTextColor(R.color.white)//表头字体颜色
                .setTableContentTextColor(R.color.black)//单元格字体颜色
                .setCellPadding(10)//设置单元格内边距(dp)
                .setNullableString("N/A") //空值替换值
                .setTableViewListener(new LockTableView.OnTableViewListener() {
                    @Override
                    public void onTableViewScrollChange(int x, int y) {
//                        Log.e("滚动值","["+x+"]"+"["+y+"]");
                    }
                })//设置横向滚动回调监听
                .setTableViewRangeListener(new LockTableView.OnTableViewRangeListener() {
                    @Override
                    public void onLeft(HorizontalScrollView view) {
                    }

                    @Override
                    public void onRight(HorizontalScrollView view) {
                    }
                })//设置横向滚动边界监听
                .setOnLoadingListener(new LockTableView.OnLoadingListener() {
                    @Override
                    public void onRefresh(final XRecyclerView mXRecyclerView, final ArrayList<ArrayList<String>> mTableDatas) {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                Log.e("现有表格数据", mTableDatas.toString());
                                //构造假数据
                                //构造假数据
                                ArrayList<ArrayList<String>> mTableDatas = new ArrayList<ArrayList<String>>();
                                ArrayList<String> mfristData = new ArrayList<String>();
                                mfristData.add("工号");
                                mfristData.add("姓名");
                                mfristData.add("评分");
                                mfristData.add("排名");
                                mfristData.add("等级");


                                mTableDatas.add(mfristData);
                                for (int i = 0; i < 10; i++) {
                                    ArrayList<String> mRowDatas = new ArrayList<String>();
                                    //数据填充
                                    mRowDatas.add("00"+i);
                                    mRowDatas.add("张三"+i);
                                    mRowDatas.add(i+"");
                                    mRowDatas.add(i+"");
                                    mRowDatas.add("优秀");
                                    mTableDatas.add(mRowDatas);
                                }
                                mLockTableView.setTableDatas(mTableDatas);
                                mXRecyclerView.refreshComplete();
                            }
                        }, 1000);
                    }

                    @Override
                    public void onLoadMore(final XRecyclerView mXRecyclerView, final ArrayList<ArrayList<String>> mTableDatas) {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mTableDatas.size() <= 60) {
                                    //构造假数据

                                    for (int i = 0; i < 10; i++) {
                                        ArrayList<String> mRowDatas = new ArrayList<String>();
                                        //数据填充
                                        mRowDatas.add("00"+i);
                                        mRowDatas.add("张三"+i);
                                        mRowDatas.add("i");
                                        mRowDatas.add("i");
                                        mRowDatas.add("优秀");
                                        mTableDatas.add(mRowDatas);
                                    }
                                    mLockTableView.setTableDatas(mTableDatas);
                                } else {
                                    mXRecyclerView.setNoMore(true);
                                }
                                mXRecyclerView.loadMoreComplete();
                            }
                        }, 1000);
                    }
                })
                .setOnItemClickListenter(new LockTableView.OnItemClickListenter() {
                    @Override
                    public void onItemClick(View item, int position) {

                    }
                })
                .setOnItemLongClickListenter(new LockTableView.OnItemLongClickListenter() {
                    @Override
                    public void onItemLongClick(View item, int position) {

                    }
                })
                .setOnItemSeletor(R.color.dashline_color)//设置Item被选中颜色
                .show(); //显示表格,此方法必须调用
        mLockTableView.getTableScrollView().setPullRefreshEnabled(true);
        mLockTableView.getTableScrollView().setLoadingMoreEnabled(true);
        mLockTableView.getTableScrollView().setRefreshProgressStyle(ProgressStyle.SquareSpin);
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getActivity(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getActivity(), dm.heightPixels);
    }

    @Override
    public void process(Message msg) {

    }
}
