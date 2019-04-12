package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.view.Toasty;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.AppealListAdapt;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.AppealList;
import com.zhixing.employlib.model.AppealListEntity;
import com.zhixing.employlib.viewmodel.activity.AppealListViewModel;
import com.zhixing.employlib.viewmodel.activity.AppealPersonViewModel;
import com.zhixing.netlib.base.BaseResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 * @author zjq
 * create at 2019/3/21 下午1:58
 * 我申诉记录的列表(处理申诉列表)
 */
public class AppealListActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.recy_myappeal)
    RecyclerView recyMyappeal;
    private Unbinder bind;
    private AppealListAdapt appealListAdapt;
    private AppealPersonViewModel appealPersonViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_appeal_list;
    }

    @Override
    public void process(Message msg) {

    }
    private SharedUtils sharedUtils;
    @Override
    public void initLayout() {
        setStatus(-1);
        sharedUtils = new SharedUtils(PerformanceApi.FLIESNAME);
         bind = ButterKnife.bind(this);
        appealPersonViewModel = ViewModelProviders.of(this).get(AppealPersonViewModel.class);
        recyMyappeal.setLayoutManager(new LinearLayoutManager(this));
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("申诉记录");
        tvWorkSend.setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        Map map = new HashMap();
        Intent intent = getIntent();
        if(!intent.hasExtra("CreateTime"))
        {
            Toasty.INSTANCE.showToast(AppealListActivity.this,"请选择时间");
            return;
        }
        if(sharedUtils.getBooleanValue(PerformanceApi.ISTEAMLEADER)){
            map.put("HandleUserId", SharedPreferencesTool.getMStool(AppealListActivity.this).getUserId());
        }else {
            map.put("ApplyUserId", SharedPreferencesTool.getMStool(AppealListActivity.this).getUserId());
        }

        map.put("CreateTime",intent.getStringExtra("CreateTime"));
        appealPersonViewModel.getAppealList(map).observe(this, new Observer<List<AppealList>>() {
            @Override
            public void onChanged(@Nullable List<AppealList> appealLists) {
                appealListAdapt=new AppealListAdapt(R.layout.item_appeal_list,appealLists) ;
                recyMyappeal.setAdapter(appealListAdapt);
                appealListAdapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        startActivity(AppealDetailActivity.class);
                        Intent intent1 = new Intent(AppealListActivity.this,AppealDetailActivity.class);
                        intent1.putExtra("obj",appealLists.get(position));
                        startActivity(intent1);
                    }
                });
            }
        });
    }


    @OnClick(R2.id.iv_work_add_work)
    public void onViewClicked() {
        AppManager.getAppManager().finishActivity();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(appealListAdapt!=null){
            Map map = new HashMap();
            Intent intent = getIntent();
            if(!intent.hasExtra("CreateTime"))
            {
                Toasty.INSTANCE.showToast(AppealListActivity.this,"请选择时间");
                return;
            }
            if(sharedUtils.getBooleanValue(PerformanceApi.ISTEAMLEADER)){
                map.put("HandleUserId", SharedPreferencesTool.getMStool(AppealListActivity.this).getUserId());
            }else {
                map.put("ApplyUserId", SharedPreferencesTool.getMStool(AppealListActivity.this).getUserId());
            }

            map.put("CreateTime",intent.getStringExtra("CreateTime"));
            appealPersonViewModel.getAppealList(map);
        }

    }
}
