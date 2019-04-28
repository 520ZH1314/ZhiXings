package com.zhixing.employlib.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.widget.ChangeTime;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.stateviewlibrary.StateView;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.RankListAdapt;
import com.zhixing.employlib.model.GradingedEntity;
import com.zhixing.employlib.model.performance.PerformanceRankBean;
import com.zhixing.employlib.ui.activity.GradingedRecordDetalActivity;
import com.zhixing.employlib.viewmodel.activity.PerformanceRankViewModel;
import com.zhixing.netlib.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class MonthDayRankFragment extends BaseFragment {

    private RecyclerView mRecylerView;
    private LinearLayout mLinerLayout;
    private TextView mTvtime;
    private ImageView mIv;
    private String toalDay;
    private PerformanceRankViewModel performanceRankViewModel;
    private String sort = "ASC";//DESC
    private StateView mStateView;
    private String photoURL;
    private String name;
    private String useCode;
    private LinearLayout mLinerLayout1;
    private boolean isClick = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month_rank, container, false);
        mRecylerView = (RecyclerView) view.findViewById(R.id.recy_month_rank);
        ACache aCache = ACache.get(getActivity());
        toalDay = aCache.getAsString("MonthDay");

        mLinerLayout = (LinearLayout) view.findViewById(R.id.ll_month_rank);
        mLinerLayout1 = (LinearLayout) view.findViewById(R.id.ll_month_day);
        mTvtime = (TextView) view.findViewById(R.id.tv_month_rank);

        LinearLayout constraintLayout = view.findViewById(R.id.cl_month_rank);
        mStateView = StateView.inject(mLinerLayout1);
        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        performanceRankViewModel = ViewModelProviders.of(getActivity()).get(PerformanceRankViewModel.class);
        String[] splitDay = toalDay.split("-");
        String Year = splitDay[0];
        String Month = splitDay[1];

        mTvtime.setText(Year + "年" + Month + "月");

        mIv = (ImageView) view.findViewById(R.id.iv_month_rank);
        mLinerLayout.setEnabled(false);
        mIv.setEnabled(false);
        initData();

        mLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinerLayout.setEnabled(false);

                ChangeTime changeTime = new ChangeTime(getActivity(), "", 3);
                changeTime.setSelect(new SelectTime() {
                    @Override
                    public void select(String time, long timestp) {
                        String toalDays = TimeUtil.getCommonTime1(time);
                        String[] splitDay = toalDays.split("-");
                        String Year = splitDay[0];
                        String Month = splitDay[1];
                        mTvtime.setText(Year + "年" + Month + "月");
                        mStateView.showLoading();
                        toalDay = Year + "-" + Month;
                        performanceRankViewModel.setRankMonthDay(toalDay, toalDay, sort);

                    }
                });
                changeTime.showSheet();


            }
        });


        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIv.setEnabled(false);
                if ("ASC".equals(sort)) {
                    sort = "DESC";
                    mStateView.showLoading();
                    performanceRankViewModel.setRankMonthDay(toalDay, toalDay, sort);

                } else {
                    sort = "ASC";
                    mStateView.showLoading();
                    performanceRankViewModel.setRankMonthDay(toalDay, toalDay, sort);

                }
            }
        });


        return view;
    }

    public void initData() {
        mStateView.showLoading();
        performanceRankViewModel.setRankMonthDay(toalDay, toalDay, sort);
        performanceRankViewModel.responseMonthLiveData.observe(getActivity(), new Observer<BaseResponse<PerformanceRankBean>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<PerformanceRankBean> performanceRankBeanBaseResponse) {

                if (performanceRankBeanBaseResponse.getRows() != null) {

                    if (performanceRankBeanBaseResponse.getRows().get(0).getTeamInfo().size() != 0) {
                        mStateView.showContent();
                        mLinerLayout.setEnabled(true);
                        mIv.setEnabled(true);
                        List<GradingedEntity> entityList = new ArrayList<>();
                        List<PerformanceRankBean.TeamInfoBean> rows = performanceRankBeanBaseResponse.getRows().get(0).getTeamInfo();

                        for (PerformanceRankBean.TeamInfoBean bean : rows) {
                            if (bean.getUserInfo().getPhotoURL() != null) {
                                photoURL = bean.getUserInfo().getPhotoURL();
                            }
                            if (bean.getUserInfo().getUserName() != null) {
                                name = bean.getUserInfo().getUserName();
                            }

                            if (bean.getUserCode() != null) {
                                useCode = bean.getUserCode();
                            }
                            // this.name = name;
                            //        this.worker = worker;
                            //        this.score = score;
                            //        this.rank = rank;
                            //        this.useCode=useCode;
                            entityList.add(new GradingedEntity(photoURL, name, "", bean.getScore(), String.valueOf(bean.getSeq()), bean.getUserCode()));

                        }

                        RankListAdapt adapt = new RankListAdapt(R.layout.item_rank_list, entityList);
                        mRecylerView.setAdapter(adapt);


                        adapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(getActivity(), GradingedRecordDetalActivity.class);
                                intent.putExtra("GradingedRecordDetalUseCode", entityList.get(position).useCode);
                                intent.putExtra("GradingedRecordDetalUseName", entityList.get(position).name);
                                intent.putExtra("GradingedRecordDetalStartTime", toalDay);
                                intent.putExtra("GradingedRecordDetalEndTime", toalDay);
                                startActivity(intent);
                            }
                        });


                    } else if ("404".equals(performanceRankBeanBaseResponse.getStatus())) {
                        mLinerLayout.setEnabled(true);
                        mIv.setEnabled(true);
                        mStateView.showRetry();
                        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                            @Override
                            public void onRetryClick() {
                                performanceRankViewModel.setRankMonthDay(toalDay, toalDay, sort);
                            }
                        });

                    } else {
                        mLinerLayout.setEnabled(true);
                        mIv.setEnabled(true);
                        mStateView.showEmpty();
                    }
                } else {
                    mLinerLayout.setEnabled(true);
                    mIv.setEnabled(true);
                    mStateView.showRetry();
                    mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                        @Override
                        public void onRetryClick() {
                            performanceRankViewModel.setRankMonthDay(toalDay, toalDay, sort);
                        }
                    });
                }

            }
        });
    }

    @Override
    public void process(Message msg) {

    }
}
