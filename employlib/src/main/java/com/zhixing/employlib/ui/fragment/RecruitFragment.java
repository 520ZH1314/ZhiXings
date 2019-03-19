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
import android.widget.TextView;

import com.base.zhixing.www.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.RecruitAdapt;
import com.zhixing.employlib.model.RecruitEntry;
import com.zhixing.employlib.ui.activity.JobDetailsActivity;
import com.zhixing.employlib.ui.activity.RecruitRecordActivity;
import com.zhixing.employlib.viewmodel.fragment.RecruitFragmentViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author zjq
 * create at 2019/3/19 上午10:21
 * 招聘主页
 */
public class RecruitFragment extends BaseFragment {

    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.recy_recruit)
    RecyclerView recyRecruit;

    private Unbinder bind;
    private RecruitFragmentViewModel recruitFragmentViewModel;
    private RecruitAdapt recruitAdapt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruit, container, false);
        bind = ButterKnife.bind(this, view);
        recruitFragmentViewModel = ViewModelProviders.of(getActivity()).get(RecruitFragmentViewModel.class);
        initData();

      return view;
    }

    private void initData() {
        ivWorkAddWork.setVisibility(View.GONE);
        tvWorkSend.setVisibility(View.VISIBLE);
        tvWorkSend.setText("查看记录");
        tvWorkTitle.setText("招聘");

        recyRecruit.setLayoutManager(new LinearLayoutManager(getActivity()));
        recruitFragmentViewModel.getmRecruitEntity();
        recruitFragmentViewModel.mRecruitEntity.observe(getActivity(), new Observer<List<RecruitEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecruitEntry> recruitEntries) {
                if (recruitEntries != null) {
                     recruitAdapt = new RecruitAdapt(R.layout.item_recruit, recruitEntries);
                     recyRecruit.setAdapter(recruitAdapt);
                    recruitAdapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent =new Intent(getActivity(),JobDetailsActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick(R2.id.tv_work_send)
    public void onViewClicked() {
        Intent intent =new Intent(getActivity(),RecruitRecordActivity.class);
        startActivity(intent);
    }
}
