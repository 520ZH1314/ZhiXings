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

import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.activity.DailyCheckActivity;
import com.zhixing.tpmlib.activity.DailyCheckDetailActivity;
import com.zhixing.tpmlib.activity.DailyCheckItemActivity;
import com.zhixing.tpmlib.activity.MyTextActivity;
import com.zhixing.tpmlib.bean.EquipmentBean;
import com.zhixing.tpmlib.view.ShapedImageView;

import java.util.ArrayList;
import java.util.List;
public class DailyCheckAdapter extends BaseQuickAdapter<EquipmentBean,BaseViewHolder> {
    List<String> titleList=new ArrayList<>();
    List<String> contentList=new ArrayList<>();
    private Button btnSure;
    private ImageView ivCancel;
    private String equipmentCode;
    private SharedUtils sharedUtils;
    private String equipmentCodes;

    public DailyCheckAdapter(List<EquipmentBean> data) {
        super(R.layout.item_requrement, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, final EquipmentBean entity) {
        List<EquipmentBean.ImageBean> imageBeans = entity.getImageBeans();
        ShapedImageView ivDefault =  helper.itemView.findViewById(R.id.iv_matche_default);
        if(imageBeans!=null){
            for (int i = 0; i <imageBeans.size() ; i++) {
                MyImageLoader.load(mContext,imageBeans.get(i).getFilePath(),ivDefault);
            }
        }

        List<EquipmentBean.RowsBean> rowsBeans=entity.getRows();
        sharedUtils = new SharedUtils("TPM");
        for (int i = 0; i <rowsBeans.size() ; i++) {
            sharedUtils.setStringValue("EquipmentName",rowsBeans.get(i).getEquipmentName());
            helper.setText(R.id.tv_requiment,rowsBeans.get(i).getEquipmentName());
//            设置设备编号
             equipmentCode = rowsBeans.get(i).getEquipmentCode();
            sharedUtils.setStringValue("equipmentCode",rowsBeans.get(i).getEquipmentCode());
            sharedUtils.setStringValue("equipmentID",rowsBeans.get(i).getEquipmentId());
            helper.setText(R.id.tv_matche_num,rowsBeans.get(i).getEquipmentCode());
//        设置检查设备的点检人
        helper.setText(R.id.tv_machine_operator,rowsBeans.get(i).getOperator());
        }
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
                String matchName = SharedPreferencesTool.getMStool(mContext).getString("matchName");
                intent.putExtra("matchName",matchName);
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
