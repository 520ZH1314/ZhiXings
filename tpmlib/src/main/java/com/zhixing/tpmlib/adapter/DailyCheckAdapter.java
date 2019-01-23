package com.zhixing.tpmlib.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.activity.DailyCheckActivity;
import com.zhixing.tpmlib.activity.DailyCheckDetailActivity;
import com.zhixing.tpmlib.activity.DailyCheckItemActivity;
import com.zhixing.tpmlib.activity.MyTextActivity;
import com.zhixing.tpmlib.bean.EquipmentBean;
import com.zhixing.tpmlib.bean.EquipmentEtity;
import com.zhixing.tpmlib.bean.ImageEntity;
import com.zhixing.tpmlib.bean.StaticticalAnalAnalyEntity;
import com.zhixing.tpmlib.view.ShapedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//f
public class DailyCheckAdapter extends BaseQuickAdapter<EquipmentEtity,BaseViewHolder> {
    private  ListMultimap<String, String> imgMap;
    private  ACache aCache;
    List<String> titleList=new ArrayList<>();
    List<String> contentList=new ArrayList<>();
    private Button btnSure;
    private ImageView ivCancel;
    private String equipmentCode;
    private SharedUtils sharedUtils;
    private String equipmentCodes;

    private List<EquipmentEtity> data;
    public DailyCheckAdapter(int layoutResId,List<EquipmentEtity> data) {
        super(layoutResId, data);
        sharedUtils = new SharedUtils("TPM");
        SharedUtils sharedUtil = new SharedUtils("TpmSetting");
        imgMap =MultimapBuilder.hashKeys().arrayListValues().build();
        String tpm_dailyCheck =  sharedUtil.getStringValue("Tpm_DailyCheck");;
        Type type = new TypeToken<List<ImageEntity>>() {}.getType();
        if (tpm_dailyCheck!=null){
            List<ImageEntity> datas = GsonUtil.getGson().fromJson(tpm_dailyCheck, type);
            for (ImageEntity bean: datas) {
                imgMap.put(bean.getClassId(),bean.getFilePath());
            }
        }


        this.data=data;
    }
    @Override
    protected void convert(BaseViewHolder helper, final EquipmentEtity entity) {
        String Url="";
        ShapedImageView ivDefault =  helper.itemView.findViewById(R.id.iv_matche_default);
        boolean hasImag = imgMap.containsKey(entity.getClassId());
        if (hasImag){
            Url=SharedPreferencesTool.getMStool(mContext).getIp()+imgMap.get(entity.getClassId()).get(0);

        }
        MyImageLoader.load(mContext,Url,ivDefault);


              helper.setText(R.id.tv_requiment,entity.getEquipmentName());
//            设置设备编号
             equipmentCode = entity.getEquipmentCode();
//             sharedUtils.setStringValue("EquipmentName",entity.getEquipmentName());
//            sharedUtils.setStringValue("equipmentCode",entity.getEquipmentCode());
//            sharedUtils.setStringValue("equipmentID",entity.getEquipmentId());
            helper.setText(R.id.tv_matche_num,entity.getEquipmentCode());
        equipmentCodes = sharedUtils.getStringValue("equipmentCode");
        helper.setOnClickListener(R.id.btn_check_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleList.clear();
                titleList.add("设备编号:");
                titleList.add("当前用户:");
                contentList.clear();
                contentList.add(equipmentCodes);
                contentList.add("est1admin/STD");
                showSexTypeDialog(titleList,contentList);
            }
        });
    }
    private void showSexTypeDialog(List<String> titleList,List<String> contentList) {
        /* 列表弹窗 */
        final AlertDialog dialog = new AlertDialog.Builder(mContext,R.style.dialog_common).create();
        //透明
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager m = dialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = d.getWidth();
        dialog.getWindow().setAttributes(p);
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_planet_dialog, null);
        ListView dialogRecyclerView = (ListView) view.findViewById(R.id.dialog_list);
//        弹出框确定的文本文件
        btnSure = (Button) view.findViewById(R.id.btn_sure);
//        弹出框取消的文本文件
        ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        DialogContentAdapter adapter = new DialogContentAdapter(titleList,contentList);
        dialogRecyclerView.setAdapter(adapter);
        dialog.setView(view);
        dialog.show();
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent= new Intent(mContext,MyTextActivity.class);
                intent.putExtra("matchName","");
                mContext.startActivity(intent);
                dialog.dismiss();
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
}
