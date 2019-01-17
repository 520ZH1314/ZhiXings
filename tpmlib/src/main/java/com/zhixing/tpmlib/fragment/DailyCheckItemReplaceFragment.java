package com.zhixing.tpmlib.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.zhixing.www.BaseFragment;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.DailyCheckIReplacetemAdapt;
import com.zhixing.tpmlib.bean.DailyCheckItemBean;

import com.zhixing.tpmlib.view.DSVOrientation;
import com.zhixing.tpmlib.view.DiscreteScrollView;
import com.zhixing.tpmlib.view.InfiniteScrollAdapter;

import com.zhixing.tpmlib.view.TopMaintenanceTypeDialog;
import com.zhixing.tpmlib.view.transform.Pivot;
import com.zhixing.tpmlib.view.transform.ScaleTransformer;
import com.zhixing.tpmlib.viewModel.MyTextActivityViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DailyCheckItemReplaceFragment extends BaseFragment {
    @BindView(R2.id.tv_daily_check_replace_equiment_namess)
    TextView tvDailyCheckReplaceEquimentName;
    @BindView(R2.id.tv_daily_check_text)
    TextView tvDailyCheckText;
    @BindView(R2.id.tv_daily_check_adress)
    TextView tvDailyCheckAdress;
    @BindView(R2.id.recyleview_daily_check_item_replace_one)
    DiscreteScrollView recyleviewDailyCheckItemReplaceOne;
    Unbinder unbinder;
    private MyTextActivityViewModel mViewModel;


    private List<DailyCheckItemBean> dailyCheckItemBean;
    public static DailyCheckItemReplaceFragment newInstance() {
        DailyCheckItemReplaceFragment fragment = new DailyCheckItemReplaceFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void process(Message msg) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_check_replace_item, container, false);

         unbinder = ButterKnife.bind(this, view);
         mViewModel=ViewModelProviders.of(getActivity()).get(MyTextActivityViewModel.class);
         InitData();

        return view;
    }
    private void InitData() {
        mViewModel.getData().observe(this, new Observer<List<DailyCheckItemBean>>() {
            @Override
            public void onChanged(@Nullable List<DailyCheckItemBean> dailyCheckItemBeans) {
                dailyCheckItemBean=dailyCheckItemBeans;
                if (dailyCheckItemBeans!=null){
                    DailyCheckIReplacetemAdapt adapt =new DailyCheckIReplacetemAdapt(R.layout.item_recyleview_daily_check_replace_item,dailyCheckItemBeans,getActivity());
                    recyleviewDailyCheckItemReplaceOne.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
                        @Override
                        public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

                            tvDailyCheckReplaceEquimentName.setText(dailyCheckItemBean.get(adapterPosition).equipmentName);
                            tvDailyCheckText.setText(dailyCheckItemBean.get(adapterPosition).equipmentText);
                            tvDailyCheckAdress.setText(dailyCheckItemBean.get(adapterPosition).equipmentAdress);


                        }
                    });
                    recyleviewDailyCheckItemReplaceOne.setOffscreenItems(2);
                    recyleviewDailyCheckItemReplaceOne.setClampTransformProgressAfter(2);
                    recyleviewDailyCheckItemReplaceOne.setOrientation(DSVOrientation.HORIZONTAL);

                    recyleviewDailyCheckItemReplaceOne.setAdapter(adapt);
                    Logger.d( recyleviewDailyCheckItemReplaceOne.getAdapter().getItemCount());
                    recyleviewDailyCheckItemReplaceOne.setItemTransformer(new ScaleTransformer.Builder()
                            .setMaxScale(1.05f)
                            .setMinScale(0.8f)
                            .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                            .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                            .build());


                    // mRecyclerView绑定scale效果

                }
            }

        });
        mViewModel.Position.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer!=null){
                    recyleviewDailyCheckItemReplaceOne.scrollToPosition(integer);
                    tvDailyCheckReplaceEquimentName.setText(dailyCheckItemBean.get(integer).equipmentName);
                    tvDailyCheckText.setText(dailyCheckItemBean.get(integer).equipmentText);
                    tvDailyCheckAdress.setText(dailyCheckItemBean.get(integer).equipmentAdress);



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
