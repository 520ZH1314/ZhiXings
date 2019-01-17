package com.zhixing.tpmlib.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.zhixing.www.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.DailyCheckItemAdapt;
import com.zhixing.tpmlib.bean.DailyCheckItemBean;
import com.zhixing.tpmlib.bean.ReplaceBean;
import com.zhixing.tpmlib.viewModel.MyTextActivityViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DailyCheckItemFragment extends BaseFragment {

    @BindView(R2.id.recyleview_daily_check_item_one)
    RecyclerView recyleviewDailyCheckItemOne;
    Unbinder unbinder;
    private MyTextActivityViewModel mMyTextActivityViewModel;
    private DailyCheckItemAdapt dailyCheckAdapter;

    private static void onChanged(DailyCheckItemBean o) {
    }

    @Override
    public void process(Message msg) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_check_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyleviewDailyCheckItemOne.setLayoutManager(new LinearLayoutManager(getContext()));
        initData();
        return view;
    }

    private void initData() {
        mMyTextActivityViewModel =ViewModelProviders.of(getActivity()).get(MyTextActivityViewModel.class);


        mMyTextActivityViewModel.getData().observe(getActivity(), new Observer<List<DailyCheckItemBean>>() {
            @Override
            public void onChanged(@Nullable List<DailyCheckItemBean> dailyCheckItemBeans) {

                if (dailyCheckAdapter!=null){
                    dailyCheckAdapter.setNewData(dailyCheckItemBeans);
                }else{
                    dailyCheckAdapter = new DailyCheckItemAdapt(R.layout.item_recyleview_daily_check_item_one, dailyCheckItemBeans);
                    recyleviewDailyCheckItemOne.setAdapter(dailyCheckAdapter);
                }
                dailyCheckAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                        //发消息通知Activity改变布局
                        EventBus.getDefault().post(new ReplaceBean(true,position));

                    }
                });

            }
        });


        mMyTextActivityViewModel.getSelected().observe(getActivity(), new Observer<List<DailyCheckItemBean>>() {
            @Override
            public void onChanged(@Nullable List<DailyCheckItemBean> dailyCheckItemBeans) {
                if (dailyCheckAdapter!=null){
                    dailyCheckAdapter.setNewData(dailyCheckItemBeans);
                }else{
                    dailyCheckAdapter = new DailyCheckItemAdapt(R.layout.item_recyleview_daily_check_item_one, dailyCheckItemBeans);
                    recyleviewDailyCheckItemOne.setAdapter(dailyCheckAdapter);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
