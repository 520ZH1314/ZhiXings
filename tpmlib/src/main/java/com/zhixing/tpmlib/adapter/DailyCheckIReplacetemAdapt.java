package com.zhixing.tpmlib.adapter;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.activity.PictureListActivity;
import com.zhixing.tpmlib.bean.AnomalousBean;
import com.zhixing.tpmlib.bean.DailyCheckItemBean;

import com.zhixing.tpmlib.bean.EquipmentEvent;
import com.zhixing.tpmlib.view.RoundAngleImageView;
import com.zhixing.tpmlib.viewModel.MyTextActivityViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyCheckIReplacetemAdapt extends BaseQuickAdapter<DailyCheckItemBean, BaseViewHolder> {
    private MyTextActivityViewModel mViewModel;
    private String status;
    private List<DailyCheckItemBean> data;
    private Button btnSure;
    private ImageView ivCancel;
    private String strContent;
    private Map<String, String> params;
    private EditText etResult;
    private SharedUtils sharedUtils;
    private String equipmentID;
    private String standardImage;
    private String gradeId;
    private String equipmentName;
    private String imgUrl;
    private String classId;
    private String cell;
    private String description;
    private String position;
    private String itemId;
    private String fruit;
    private int paramater;
    private int seq;
    private String exceptionRecordId;
    List<AnomalousBean> anomalousBeanLists = new ArrayList<>();
    private String exceptionId;
    private String maintananceId;
    private RoundAngleImageView roundAngleImageView;
    private String imgPath;

    public DailyCheckIReplacetemAdapt(int layoutResId, @Nullable List<DailyCheckItemBean> data, FragmentActivity context) {
        super(layoutResId, data);

        this.data = data;
        mViewModel = ViewModelProviders.of(context).get(MyTextActivityViewModel.class);
    }


    @Override
    protected void convert(BaseViewHolder helper, DailyCheckItemBean item) {
        sharedUtils = new SharedUtils("TPM");
        helper.setText(R.id.tv_daily_check_replace_body, item.getDescription());
        Button btn2 = helper.itemView.findViewById(R.id.btn_ng);
        roundAngleImageView =(RoundAngleImageView) helper.itemView.findViewById(R.id.roundAngleImageView);
            MyImageLoader.loads(mContext, UrlUtil.BaseImgUrl+ item.getActuallyImage(), roundAngleImageView);
      /*  String path="storage/emulated/0/Android/data/com.shuben.zhixing.www/cache/luban_disk_cache/154840046858340.png";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        roundAngleImageView.setImageBitmap(BitmapFactory.decodeFile(path,options));*/
        Button btn1 = helper.itemView.findViewById(R.id.btn_ok);
        ImageView iv_add = (ImageView) helper.itemView.findViewById(R.id.iv_add);
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.total8)
                .radius(10)
                .into(btn1);
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.total7)
                .radius(10)
                .into(btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        设备id
                equipmentID = sharedUtils.getStringValue("equipmentID");
//        参考图片
                standardImage = item.getStandardImage();
//         保养类型ID
                gradeId = item.getGradeId();
//        设备异常id
                //exceptionRecordId = item.getExceptionRecordId();
//         设备分类ID
                classId = item.getClassId();
                //        图片路径
                imgUrl = sharedUtils.getStringValue("imgPicUrl");
                String equipmentName = sharedUtils.getStringValue("EquipmentName");
//                单元
                cell = item.getCell();
//                点检项
                description = item.getDescription();
//                位置
                position = item.getPosition();
//                点检项目id
                itemId = item.getItemId();
//                结果
                fruit = item.getFruit();
//                是否为参数记录
                paramater = item.getParamater();
//                排序号
                seq = item.getSeq();
//保养记录id
                maintananceId = item.getMaintananceId();
                params = new HashMap<String, String>();
                params.put("GradeId", gradeId);
                params.put("AppCode", "TPM");
                params.put("ApiCode", "EditEveryDayRecordForAndroid");
                params.put("EquipmentId", equipmentID);
                params.put("ClassId", classId);
                params.put("ActuallyImage", imgUrl);
                params.put("ItemId", itemId);
                params.put("Fruit", "1");
                params.put("Cell", cell);
                params.put("Position", position);
                params.put("Description", description);
                params.put("StandardImage", standardImage);
                params.put("Paramater", paramater + "");
                params.put("Seq", seq + "");
                params.put("MaintananceId", maintananceId);
                params.put("ExceptionRecordId", exceptionId);
            /*    data.get(helper.getLayoutPosition()).setFruit("2");
                mViewModel.RefrshBean(data);
                EventBus.getDefault().post(new RefrshBean(data));*/
                RequestQueue requestQueue1 = Volley.newRequestQueue(mContext);
                helper.setText(R.id.editText2, "OK");
                System.out.println("==========" + new JSONObject(params).toString());
                JsonObjectRequest newMissRequest = new JsonObjectRequest(
                        Request.Method.POST, SharedPreferencesTool.getMStool(mContext).getIp() + UrlUtil.Url,
                        new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonobj) {
                        try {
                            if (jsonobj.getString("result").equals("1")) {
                                item.setFruit("1");
                                sharedUtils.setStringValue("imgPicUrl", "");
                                Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString() + "---------------------");
                    }
                });
                requestQueue1.add(newMissRequest);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        设备id
                equipmentID = sharedUtils.getStringValue("equipmentID");
//        参考图片
                standardImage = item.getStandardImage();
//         保养类型ID
                gradeId = item.getGradeId();
//        设备异常id
                //exceptionRecordId = item.getExceptionRecordId();
//         设备分类ID
                classId = item.getClassId();
                //        图片路径
                imgUrl = sharedUtils.getStringValue("imgPicUrl");
                String equipmentName = sharedUtils.getStringValue("EquipmentName");
//                单元
                cell = item.getCell();
//                点检项
                description = item.getDescription();
//                位置
                position = item.getPosition();
//                点检项目id
                itemId = item.getItemId();
//                结果
                fruit = item.getFruit();
//                是否为参数记录
                paramater = item.getParamater();
//                排序号
                seq = item.getSeq();
//保养记录id
                maintananceId = item.getMaintananceId();
                params = new HashMap<String, String>();
                params.put("GradeId", gradeId);
                params.put("AppCode", "TPM");
                params.put("ApiCode", "EditEveryDayRecordForAndroid");
                params.put("EquipmentId", equipmentID);
                params.put("ClassId", classId);
                params.put("ActuallyImage", imgUrl);
                params.put("ItemId", itemId);
                params.put("Fruit", "0");
                params.put("Cell", cell);
                params.put("Position", position);
                params.put("Description", description);
                params.put("StandardImage", standardImage);
                params.put("Paramater", paramater + "");
                params.put("Seq", seq + "");
                params.put("MaintananceId", maintananceId);
                params.put("ExceptionRecordId", exceptionId);
                etResult = (EditText) helper.itemView.findViewById(R.id.editText2);
                String checkParam = etResult.getText().toString();
                if (!TextUtils.isEmpty(checkParam)) {
                    RequestQueue requestQueue1 = Volley.newRequestQueue(mContext);
                    System.out.println("+++++++" + new JSONObject(params).toString());
                    JsonObjectRequest newMissRequest = new JsonObjectRequest(
                            Request.Method.POST, SharedPreferencesTool.getMStool(mContext).getIp() + UrlUtil.Url,
                            new JSONObject(params), new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonobj) {
                            try {
                                if (jsonobj.getString("result").equals("1")) {
                                    item.setFruit("0");
                                    sharedUtils.setStringValue("imgPicUrl", "");
                                    Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                                    System.out.println(jsonobj.toString() + "5555555");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.toString() + "---------------------");
                        }
                    });
                    requestQueue1.add(newMissRequest);
                } else {
                    showSexTypeDialog(anomalousBeanLists);
                }

            }

        });
        iv_add.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PictureListActivity.class));
            }
        });
    }

    private void showSexTypeDialog(List<AnomalousBean> anomalousBeanLists) {/* 列表弹窗 */
        final AlertDialog dialog = new AlertDialog.Builder(mContext, R.style.dialog_common).create();
        //透明
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager m = dialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = d.getWidth();
        dialog.getWindow().setAttributes(p);
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_planet_ng_dialog, null);
        GridView dialogRecyclerView = (GridView) view.findViewById(R.id.dialog_ng_list);
//        弹出框确定的文本文件
        btnSure = (Button) view.findViewById(R.id.btn_sure);
//        弹出框取消的文本文件
        ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        DialogGridContentAdapter adapter = new DialogGridContentAdapter(anomalousBeanLists);
        dialogRecyclerView.setAdapter(adapter);
        setItemClickListener(dialogRecyclerView, adapter);
        dialog.setView(view);
        dialog.show();
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent= new Intent(mContext,DailyCheckDetailActivity.class);
                String matchName = SharedPreferencesTool.getMStool(mContext).getString("matchName");
                intent.putExtra("matchName",matchName);
                mContext.startActivity(intent);*/
                if (TextUtils.isEmpty(strContent)) {
                    Toast.makeText(mContext, "请选择异常小类", Toast.LENGTH_SHORT).show();
                    return;
                }
                etResult.setText(strContent);
                P.c("setOnClickListener" + strContent);
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

    int selectorPosition = 0;

    private void setItemClickListener(GridView gridView, final DialogGridContentAdapter mAdapter) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                exceptionId = anomalousBeanLists.get(position).getExceptionId();
                                                params.put("ExceptionRecordId", exceptionId);
                                                //把点击的position传递到adapter里面去
                                                TextView tvContent = view.findViewById(R.id.tv_content);
                                                strContent = tvContent.getText().toString();
                                                mAdapter.changeState(position);
                                                selectorPosition = position;
                                            }
                                        }
        );
    }

    public void setEList(List<AnomalousBean> anomalousBeanList) {
        anomalousBeanLists.addAll(anomalousBeanList);
    }

}
