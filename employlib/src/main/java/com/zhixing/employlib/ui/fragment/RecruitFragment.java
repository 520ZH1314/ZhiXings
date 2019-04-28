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
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.RecruitAdapt;
import com.zhixing.employlib.model.RecruitEntry;
import com.zhixing.employlib.model.eventbus.JobEventBeans;
import com.zhixing.employlib.model.recrui.RecruitListBean;
import com.zhixing.employlib.ui.activity.JobDetailsActivity;
import com.zhixing.employlib.ui.activity.RecruitRecordActivity;
import com.zhixing.employlib.viewmodel.fragment.RecruitFragmentViewModel;
import com.zhixing.netlib.base.BaseResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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
public class RecruitFragment extends BaseLazyFragment {

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
    private ACache aCache;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruit, container, false);
        bind = ButterKnife.bind(this, view);
         aCache =ACache.get(getActivity());
         EventBus.getDefault().register(this);
        recruitFragmentViewModel = ViewModelProviders.of(getActivity()).get(RecruitFragmentViewModel.class);


      return view;
    }

    private void initDatas() {
        ivWorkAddWork.setVisibility(View.GONE);
        tvWorkSend.setVisibility(View.VISIBLE);
        tvWorkSend.setText("查看记录");
        tvWorkTitle.setText("招聘");

        recyRecruit.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recruitFragmentViewModel.mRecruitEntity.observe(getActivity(), new Observer<List<RecruitEntry>>() {
//            @Override
//            public void onChanged(@Nullable List<RecruitEntry> recruitEntries) {
//                if (recruitEntries != null) {
//                     recruitAdapt = new RecruitAdapt(R.layout.item_recruit, recruitEntries);
//                     recyRecruit.setAdapter(recruitAdapt);
//                    recruitAdapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                            Intent intent =new Intent(getActivity(),JobDetailsActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                }
//            }
//        });

         recruitFragmentViewModel.getmRecruitEntity();
        recruitFragmentViewModel.recruitList.observe(getActivity(), new Observer<BaseResponse<RecruitListBean>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<RecruitListBean> recruitListBeanBaseResponse) {
                 if (recruitListBeanBaseResponse.getRows()!=null){
                     List<RecruitListBean> rows = recruitListBeanBaseResponse.getRows();
                     String json1 = GsonUtil.getGson().toJson(rows);

                     List<RecruitEntry> datas=new ArrayList<>();
                     for (int i = 0; i < recruitListBeanBaseResponse.getRows().size(); i++) {

                         datas.add(new RecruitEntry(recruitListBeanBaseResponse.getRows().get(i).getJobPost(),clearTime(recruitListBeanBaseResponse.getRows().get(i).getCreateDate()),recruitListBeanBaseResponse.getRows().get(i).getJobSkills(),recruitListBeanBaseResponse.getRows().get(i).getJobDetail(),recruitListBeanBaseResponse.getRows().get(i).getJobSalaryMin()/1000+"K"+"-"+recruitListBeanBaseResponse.getRows().get(i).getJobSalarMax()/1000+"K",
                                 recruitListBeanBaseResponse.getRows().get(i).getState()+""));
                     }
                     String json = GsonUtil.getGson().toJson(datas);
                     recruitAdapt = new RecruitAdapt(R.layout.item_recruit, datas);
                     recyRecruit.setAdapter(recruitAdapt);

                     recruitAdapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent =new Intent(getActivity(),JobDetailsActivity.class);
                            intent.putExtra("RecruitPosition",position);
                            intent.putExtra("RecruitData",json);
                            intent.putExtra("SendData",json1);
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
    public void initData() {
        initDatas();
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




    public String clearTime(String date) {
//        2007-11-14T00:00:00
        String[] ts = date.split("T");
        String t = ts[0];
        String[] split = t.split("-");
        return split[1] + "月" + split[2] + "日";

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(JobEventBeans eventBeans){
        if (eventBeans.isTrue){
            recruitFragmentViewModel.getmRecruitEntity();
            recruitFragmentViewModel.recruitList.observe(getActivity(), new Observer<BaseResponse<RecruitListBean>>() {
                @Override
                public void onChanged(@Nullable BaseResponse<RecruitListBean> recruitListBeanBaseResponse) {
                    if (recruitListBeanBaseResponse.getRows()!=null){
                        List<RecruitListBean> rows = recruitListBeanBaseResponse.getRows();
                        String json1 = GsonUtil.getGson().toJson(rows);

                        List<RecruitEntry> datas=new ArrayList<>();
                        for (int i = 0; i < recruitListBeanBaseResponse.getRows().size(); i++) {

                            datas.add(new RecruitEntry(recruitListBeanBaseResponse.getRows().get(i).getJobPost(),clearTime(recruitListBeanBaseResponse.getRows().get(i).getCreateDate()),recruitListBeanBaseResponse.getRows().get(i).getJobSkills(),recruitListBeanBaseResponse.getRows().get(i).getJobDetail(),recruitListBeanBaseResponse.getRows().get(i).getJobSalaryMin()/1000+"K"+"-"+recruitListBeanBaseResponse.getRows().get(i).getJobSalarMax()/1000+"K",
                                    recruitListBeanBaseResponse.getRows().get(i).getState()+""));
                        }
                        String json = GsonUtil.getGson().toJson(datas);
                        recruitAdapt = new RecruitAdapt(R.layout.item_recruit, datas);
                        recyRecruit.setAdapter(recruitAdapt);

                        recruitAdapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent =new Intent(getActivity(),JobDetailsActivity.class);
                                intent.putExtra("RecruitPosition",position);
                                intent.putExtra("RecruitData",json);
                                intent.putExtra("SendData",json1);
                                startActivity(intent);
                            }
                        });

                    }
                }
            });
        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
