package com.zhixing.tpmlib.fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.DailyCheckIReplacetemAdapt;
import com.zhixing.tpmlib.bean.AnomalousBean;
import com.zhixing.tpmlib.bean.DailyCheckItemBean;
import com.zhixing.tpmlib.bean.EquipmentEvent;
import com.zhixing.tpmlib.view.DSVOrientation;
import com.zhixing.tpmlib.view.DiscreteScrollView;
import com.zhixing.tpmlib.view.InfiniteScrollAdapter;
import com.zhixing.tpmlib.view.RoundAngleImageView;
import com.zhixing.tpmlib.view.TopMaintenanceTypeDialog;
import com.zhixing.tpmlib.view.transform.Pivot;
import com.zhixing.tpmlib.view.transform.ScaleTransformer;
import com.zhixing.tpmlib.viewModel.MyTextActivityViewModel;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    List<AnomalousBean> anomalousBeanList = new ArrayList<>();
    private Integer position;//位置
    private List<DailyCheckItemBean> dailyCheckItemBean;
    private SharedUtils sharedUtils;
    private List<DailyCheckItemBean> dailyCheckItemBeans;
    private boolean isVisible;
    private SharedUtils sharedUtil;
    private String tpmLineid;
    private DailyCheckIReplacetemAdapt adapt;

    public static DailyCheckItemReplaceFragment newInstance() {
        DailyCheckItemReplaceFragment fragment = new DailyCheckItemReplaceFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedUtils = new SharedUtils("TPM");

    }

    @Override
    public void process(Message msg) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_check_replace_item, container, false);
        sharedUtil = new SharedUtils("TpmSetting");
        //       获取产线id
        tpmLineid = sharedUtil.getStringValue("LineListId");
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        String equipmentName = sharedUtils.getStringValue("EquipmentName");
        tvDailyCheckReplaceEquimentName.setText(equipmentName);
        mViewModel = ViewModelProviders.of(getActivity()).get(MyTextActivityViewModel.class);
        InitData();
        recyleviewDailyCheckItemReplaceOne.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                position=adapterPosition;
            }
        });

        return view;
    }
    private void InitData() {
        String checkItemJson = sharedUtils.getStringValue("checkItemJson");
        String exceptionJson = sharedUtils.getStringValue("exceptionJson");

        /*mViewModel.getData().observe(this, new Observer<List<DailyCheckItemBean>>() {
            @Override
            public void onChanged(@Nullable List<DailyCheckItemBean> dailyCheckItemBeans) {
                dailyCheckItemBean=dailyCheckItemBeans;
                if (dailyCheckItemBeans!=null){
                    DailyCheckIReplacetemAdapt adapt =new DailyCheckIReplacetemAdapt(R.layout.item_recyleview_daily_check_replace_item,dailyCheckItemBeans,getActivity());
                    recyleviewDailyCheckItemReplaceOne.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
                        @Override
                        public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

                            tvDailyCheckReplaceEquimentName.setText(dailyCheckItemBean.get(adapterPosition).getExceptionGroupName());
                            tvDailyCheckText.setText(dailyCheckItemBean.get(adapterPosition).getDescription());
                            tvDailyCheckAdress.setText(dailyCheckItemBean.get(adapterPosition).getItemCode());


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
        */
        mViewModel.Position.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer!=null){

                    position=integer;
                    recyleviewDailyCheckItemReplaceOne.scrollToPosition(integer);

                }
            }
        });
        parseEJson(exceptionJson);
        parseJson(checkItemJson);
    }
    private void parseEJson(String exceptionJson) {
        try {
            JSONObject jsonObject = new JSONObject(exceptionJson);
            JSONArray rows = jsonObject.getJSONArray("rows");
            for (int i = 0; i < rows.length(); i++) {
                String exceptionGroupName = rows.getJSONObject(i).getString("ExceptionGroupName");
                String ExceptionId = rows.getJSONObject(i).getString("ExceptionId");
                AnomalousBean anomalousBean = new AnomalousBean();
                anomalousBean.setExceptionGroupName(exceptionGroupName);
                anomalousBean.setExceptionId(ExceptionId);
                P.c("exceptionGroupName" + exceptionGroupName);
                anomalousBeanList.add(anomalousBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJson(String checkItemJson) {
        try {
            JSONObject jsonObject = new JSONObject(checkItemJson);
            JSONArray rows = jsonObject.getJSONArray("rows");
            dailyCheckItemBeans = new ArrayList<>();
            for (int i = 0; i < rows.length(); i++) {
                JSONObject jsonObject1 = rows.getJSONObject(i);
//                       获取单元
                String cell = jsonObject1.getString("Cell");
//                       获取位置
                String position = jsonObject1.getString("Position");
//                       获取点检项
                String description = jsonObject1.getString("Description");
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
                DailyCheckItemBean dailyCheckItemBean = new DailyCheckItemBean();
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
            tvDailyCheckText.setText("单元:"+dailyCheckItemBeans.get(0).getCell());
            tvDailyCheckAdress.setText("位置:"+dailyCheckItemBeans.get(0).getPosition());
             adapt = new DailyCheckIReplacetemAdapt(R.layout.item_recyleview_daily_check_replace_item, dailyCheckItemBeans, getActivity());

            recyleviewDailyCheckItemReplaceOne.setOffscreenItems(2);
            recyleviewDailyCheckItemReplaceOne.setClampTransformProgressAfter(2);
            recyleviewDailyCheckItemReplaceOne.setOrientation(DSVOrientation.HORIZONTAL);
            recyleviewDailyCheckItemReplaceOne.setAdapter(adapt);
//            recyleviewDailyCheckItemReplaceOne.scrollToPosition(integer);

            recyleviewDailyCheckItemReplaceOne.setItemTransformer(new ScaleTransformer.Builder()
                    .setMaxScale(1.05f)
                    .setMinScale(0.8f)
                    .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                    .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                    .build());
            adapt.setEList(anomalousBeanList);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EquipmentEvent event) {
        String equiptmentName = event.getEquiptmentName();
        if (equiptmentName!=null){
            RoundAngleImageView roundAngleImageView = (RoundAngleImageView)adapt.getViewByPosition(recyleviewDailyCheckItemReplaceOne, position, R.id.roundAngleImageView);
            MyImageLoader.loadSpn(getActivity(),equiptmentName,roundAngleImageView);
        }
    }

}
