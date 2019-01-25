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

import com.android.volley.VolleyError;
import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.activity.MyTextActivity;
import com.zhixing.tpmlib.adapter.DailyCheckItemAdapt;
import com.zhixing.tpmlib.bean.DailyCheckItemBean;
import com.zhixing.tpmlib.bean.ReplaceBean;
import com.zhixing.tpmlib.viewModel.MyTextActivityViewModel;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DailyCheckItemFragment extends BaseFragment {

    @BindView(R2.id.recyleview_daily_check_item_one)
    RecyclerView recyleviewDailyCheckItemOne;
    Unbinder unbinder;
    private MyTextActivityViewModel mMyTextActivityViewModel;
    private DailyCheckItemAdapt dailyCheckAdapter;
    private SharedUtils sharedUtils;
    private SharedUtils sharedUtil;
    private String tpmLineid;
    private boolean isVisible;

    private static void onChanged(DailyCheckItemBean o) {
    }

    @Override
    public void process(Message msg) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedUtils = new SharedUtils("TPM");
        View view = inflater.inflate(R.layout.fragment_daily_check_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyleviewDailyCheckItemOne.setLayoutManager(new LinearLayoutManager(getContext()));
        initData();
        return view;
    }

    private void initData() {
        sharedUtil = new SharedUtils("TpmSetting");
        //       获取产线id
        tpmLineid = sharedUtil.getStringValue("LineListId");
        String[] tpmLineids = tpmLineid.split(",");
        tpmLineid=tpmLineids[0];
        String checkItemJson = sharedUtils.getStringValue("checkItemJson");
        if (dailyCheckAdapter!=null){
            dailyCheckAdapter.notifyDataSetChanged();
        }
        getFromData();
        //parseJson(checkItemJson);
        P.c(checkItemJson);
        mMyTextActivityViewModel =ViewModelProviders.of(getActivity()).get(MyTextActivityViewModel.class);


       /* mMyTextActivityViewModel.getData().observe(getActivity(), new Observer<List<DailyCheckItemBean>>() {
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
        });*/


       /* mMyTextActivityViewModel.getSelected().observe(getActivity(), new Observer<List<DailyCheckItemBean>>() {
            @Override
            public void onChanged(@Nullable List<DailyCheckItemBean> dailyCheckItemBeans) {
                if (dailyCheckAdapter!=null){
                    dailyCheckAdapter.setNewData(dailyCheckItemBeans);
                }else{
                    dailyCheckAdapter = new DailyCheckItemAdapt(R.layout.item_recyleview_daily_check_item_one, dailyCheckItemBeans);
                    recyleviewDailyCheckItemOne.setAdapter(dailyCheckAdapter);
                }
            }
        });*/

    }

    private void getFromData() {
        //        获取点检项的接口
        String tenantId = SharedPreferencesTool.getMStool(getContext()).getTenantId();
        //        获取设备id
        String matheId = sharedUtils.getStringValue("equipmentID");
        Map<String, String> params = new HashMap<String, String>();
        params.put("TenantId", tenantId);
        params.put("AppCode", "TPM");
        params.put("ApiCode", "GetMaintananceItemInfo");
        params.put("LineId", tpmLineid);
        params.put("EquipmentId", matheId);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("AppCode", "TPM");
            jsonObject.put("ApiCode", "GetMaintananceItemInfo");
            jsonObject.put("TenantId", tenantId);
            jsonObject.put("LineId", tpmLineid);
            jsonObject.put("EquipmentId", matheId);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpPostVolley(SharedPreferencesTool.getMStool(getContext()).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                parseJson(jsonObject.toString());
                sharedUtils.setStringValue("checkItemJson", jsonObject.toString());
                P.c("DailyCheckDetailActivity:" + jsonObject.toString());
            }

            @Override
            public void error(VolleyError error) {
                P.c("DailyCheckDetailActivity:" + error.toString());
            }
        }, true);
    }

    private void parseJson(String checkItemJson) {

         try {
             JSONObject jsonObject=new  JSONObject(checkItemJson);
                    JSONArray rows = jsonObject.getJSONArray("rows");
                    List<DailyCheckItemBean> dailyCheckItemBeans = new ArrayList<>();
                    for (int i = 0; i <rows.length() ; i++) {
                        JSONObject jsonObject1 = rows.getJSONObject(i);
//                       获取单元
                        String cell = jsonObject1.getString("Cell");
//                       获取位置
                        String position = jsonObject1.getString("Position");
//                       获取点检项
                        String description=jsonObject1.getString("Description");
//                       获取itemid
                        String itemId = jsonObject1.getString("ItemId");
//                       获取
                        String maintananceId = jsonObject1.getString("MaintananceId");
                        int seq = jsonObject1.getInt("Seq");
                        String fruit = jsonObject1.getString("Fruit");
                        String standardImage = jsonObject1.getString("StandardImage");
                        String gradeId = jsonObject1.getString("GradeId");
                        String classId = jsonObject1.getString("ClassId");
                        String actuallyImage = jsonObject1.getString("ActuallyImage");
                        DailyCheckItemBean dailyCheckItemBean=new DailyCheckItemBean();
                        dailyCheckItemBean.setCell(cell);
                        dailyCheckItemBean.setPosition(position);
                        dailyCheckItemBean.setItemId(itemId);
                        dailyCheckItemBean.setSeq(seq);
                        dailyCheckItemBean.setMaintananceId(maintananceId);
                        dailyCheckItemBean.setDescription(description);
                        dailyCheckItemBean.setFruit(fruit);
                        dailyCheckItemBean.setGradeId(gradeId);
                        dailyCheckItemBean.setClassId(classId);
                        dailyCheckItemBean.setActuallyImage(actuallyImage);
                        dailyCheckItemBeans.add(dailyCheckItemBean);
                    }
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
            getFromData();
        }else{
            isVisible=false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getFromData();
    }
}
