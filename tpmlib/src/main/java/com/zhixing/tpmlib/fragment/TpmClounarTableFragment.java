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
import android.widget.TextView;

import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.google.gson.reflect.TypeToken;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.TpmTableAdapt;
import com.zhixing.tpmlib.bean.StaticticalAnalAnalyEntity;
import com.zhixing.tpmlib.viewModel.ColumnarViewModel;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author zjq
 * create at 2019/1/18 上午11:04
 * 统计分析的表格布局
 */
public class TpmClounarTableFragment extends BaseFragment {
    @BindView(R2.id.tv_tpm_table_replace)
    TextView tvTpmTableReplace;
    @BindView(R2.id.tv_tpm_table_type)
    TextView tvTpmTableType;
    @BindView(R2.id.recyview_tpm_table)
    RecyclerView recyviewTpmTable;
    private String Key;;
    private Unbinder unbinder;
    private ColumnarViewModel mColumnarViewModel;
    private TpmTableAdapt adapt;
    private ACache aCache;

    @Override
    public void process(Message msg) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_tpm_table, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        mColumnarViewModel = ViewModelProviders.of(getActivity()).get(ColumnarViewModel.class);
         aCache=ACache.get(getActivity());

        initView();
        initData();

        return  inflate;

    }

    private void initData() {
      //初始化数据
        //拿到默认值

        mColumnarViewModel.getNomalKey().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s!=null){
                   Key=s;
                    tvTpmTableType.setText(Key);
                    String mapData = aCache.getAsString("mapData");
                    if (mapData!=null){
                        Type type = new TypeToken<Map<String, List<StaticticalAnalAnalyEntity>>>() {}.getType();
                        Map<String, List<StaticticalAnalAnalyEntity>> map2 = GsonUtil.getGson().fromJson(mapData, type);

                        List<StaticticalAnalAnalyEntity> staticticalAnalAnalyEntities = map2.get(Key);
                        if (adapt==null){
                            adapt =new TpmTableAdapt(R.layout.item_recy_tpm_table,staticticalAnalAnalyEntities);
                            recyviewTpmTable.setAdapter(adapt);
                        }
                        adapt.setNewData(staticticalAnalAnalyEntities);
                    }




                }
            }
        });



    }

    private void initView() {
        recyviewTpmTable.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R2.id.tv_tpm_table_replace)
    public void onViewClicked() {
        mColumnarViewModel.isReplace.setValue(false);
    }
}
