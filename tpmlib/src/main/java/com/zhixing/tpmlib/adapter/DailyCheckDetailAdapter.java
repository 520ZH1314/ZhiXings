package com.zhixing.tpmlib.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.activity.DailyCheckActivity;
import com.zhixing.tpmlib.activity.DailyCheckDetailActivity;
import com.zhixing.tpmlib.activity.PictureListActivity;
import com.zhixing.tpmlib.bean.AnomalousBean;
import com.zhixing.tpmlib.bean.CheckItemBean;
import com.zhixing.tpmlib.bean.CheckItemEntity;
import com.zhixing.tpmlib.utils.Base64Utils;
import com.zhixing.tpmlib.utils.UploadUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @Author smart
 * @Date 2018/12/25
 * @Des 日常点检项的适配器
 */
public class DailyCheckDetailAdapter extends BaseQuickAdapter<CheckItemBean.RowsBean,BaseViewHolder> {

    private String checkParam;
    private EditText etResult;
    private ImageView ivTipClose;
    private String imgUrl;
    private String cell;
    private String description;
    private String position;
    private String itemId;
    private String fruit;
    private int paramater;
    private int seq;
    private String maintananceId;
    private String equipmentID;
    private String standardImage;
    private  String classId;
    private String gradeId;
    private String exceptionRecordId;
//    异常小类名称的集合
    List<String> contentList=new ArrayList<>();
//    异常小类记录id的集合
    List<String> recordList=new ArrayList<>();
    private Button btnSure;
    private ImageView ivCancel;
    private String strContent;
    private AnomalousBean anomalousBean;
    List<AnomalousBean> anomalousBeanLists=new ArrayList<>();
    private String exceptionId;
    private Map<String, String> params;
    private SharedUtils sharedUtils;

    public DailyCheckDetailAdapter( List<CheckItemBean.RowsBean> data) {
        super(R.layout.item_check_item,data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CheckItemBean.RowsBean entity) {
        sharedUtils = new SharedUtils("TPM");
//        设备id
        equipmentID  = sharedUtils.getStringValue("equipmentID");
//        参考图片
         standardImage = entity.getStandardImage();
//         保养类型ID
        gradeId = entity.getGradeId();
//        设备异常id
         //exceptionRecordId = entity.getExceptionRecordId();
//         设备分类ID
         classId = entity.getClassId();
//        设置点检项
        helper.setText(R.id.tv_check_item, entity.getDescription());
        //        图片路径
        imgUrl = sharedUtils.getStringValue("imgPicUrl");
        String equipmentName =sharedUtils.getStringValue("EquipmentName");
//                单元
        cell = entity.getCell();
//                点检项
        description = entity.getDescription();
//                位置
        position = entity.getPosition();
//                点检项目id
        itemId = entity.getItemId();
//                结果
        fruit = entity.getFruit();
//                是否为参数记录
        paramater = entity.getParamater();
//                排序号
        seq = entity.getSeq();
//保养记录id
        maintananceId = entity.getMaintananceId();
//        if(!TextUtils.isEmpty(entity.getPicNum())){
//            helper.setText(R.id.tv_pic_num, "1"+"/"+entity.getPicNum());
//        }else{
//
//            helper.setText(R.id.tv_pic_num, "0"+"/"+0);
//        }
//        上传图片的点击事件
        helper.setOnClickListener(R.id.btn_upload_pic, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // mContext.startActivity(new Intent(mContext,PictureListActivity.class));

            }
        });
//        查看更多的点击事件
        helper.setOnClickListener(R.id.btn_look_more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,PictureListActivity.class));
            }
        });

        //Ok提交的接口
        params = new HashMap<String, String>();
        params.put("GradeId", gradeId);
        params.put("AppCode","TPM");
        params.put("ApiCode","EditEveryDayRecordForAndroid");
        params.put("EquipmentId", equipmentID);
        params.put("ClassId", classId);
        params.put("ActuallyImage",imgUrl);
        params.put("ItemId", itemId);
        params.put("Fruit","1");
        params.put("Cell", cell);
        params.put("Position", position);
        params.put("Description", description);
        params.put("StandardImage",standardImage);
        params.put("Paramater", paramater+"");
        params.put("Seq", seq+"");
        params.put("MaintananceId", maintananceId);
        System.out.println(exceptionId+"exceptionId---------");
        params.put("ExceptionRecordId",exceptionId);
        //点击OK的点击事件
        helper.setOnClickListener(R.id.btn_ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue1 = Volley.newRequestQueue(mContext);
        helper.setText(R.id.et_hint_result,"OK");
                System.out.println("=========="+new JSONObject(params).toString());
                JsonObjectRequest newMissRequest = new JsonObjectRequest(
                        Request.Method.POST, SharedPreferencesTool.getMStool(mContext).getIp() + UrlUtil.Url,
                        new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonobj) {
                        try {
                            if(jsonobj.getString("result").equals("1")){
                                Toast.makeText(mContext,"提交成功",Toast.LENGTH_SHORT).show();
                            }else{

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println(jsonobj.toString()+"5555555");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext,"提交失败",Toast.LENGTH_SHORT).show();
                        System.out.println(error.toString()+"---------------------");
                    }
                });
                requestQueue1.add(newMissRequest);
            }
        });

        helper.setOnClickListener(R.id.btn_ng, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etResult =(EditText) helper.itemView.findViewById(R.id.et_hint_result);
               ivTipClose =(ImageView) helper.itemView.findViewById(R.id.iv_tip_close);
                checkParam = etResult.getText().toString().trim();
                if(TextUtils.isEmpty(checkParam)){
                    ivTipClose.setVisibility(View.VISIBLE);
                    etResult.setHint("请先记录参数");
                    TextChange(ivTipClose);
                    ivTipClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ivTipClose.setVisibility(View.GONE);
                        }
                    });
                }else{
                    ivTipClose.setVisibility(View.GONE);
                }
                if(!TextUtils.isEmpty(checkParam)){
                    RequestQueue requestQueue1 = Volley.newRequestQueue(mContext);
                    System.out.println("+++++++"+new JSONObject(params).toString());
                    JsonObjectRequest newMissRequest = new JsonObjectRequest(
                            Request.Method.POST, SharedPreferencesTool.getMStool(mContext).getIp() + UrlUtil.Url,
                            new JSONObject(params), new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonobj) {
                            System.out.println(jsonobj.toString()+"5555555");
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.toString()+"---------------------");
                        }
                    });
                    requestQueue1.add(newMissRequest);

                    Toast.makeText(mContext,"提交成功",Toast.LENGTH_SHORT).show();
                }else{
                    showSexTypeDialog(anomalousBeanLists);
                }

            }


        });
    }

    private void showSexTypeDialog(List<AnomalousBean> contentList) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_planet_ng_dialog,null);
        GridView dialogRecyclerView = (GridView) view.findViewById(R.id.dialog_ng_list);
//        弹出框确定的文本文件
        btnSure = (Button) view.findViewById(R.id.btn_sure);
//        弹出框取消的文本文件
        ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        DialogGridContentAdapter adapter = new DialogGridContentAdapter(contentList);
        dialogRecyclerView.setAdapter(adapter);
        setItemClickListener(dialogRecyclerView,adapter);
        dialog.setView(view);
        dialog.show();
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent= new Intent(mContext,DailyCheckDetailActivity.class);
                String matchName = SharedPreferencesTool.getMStool(mContext).getString("matchName");
                intent.putExtra("matchName",matchName);
                mContext.startActivity(intent);*/
                if(TextUtils.isEmpty(strContent)){
                    Toast.makeText(mContext,"请选择异常小类",Toast.LENGTH_SHORT).show();
                    return;
                }
                etResult.setText(strContent);
                P.c("setOnClickListener"+strContent);
                dialog.dismiss();
            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,DailyCheckDetailActivity.class));
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
                                                params.put("ExceptionRecordId",exceptionId);
                                                //把点击的position传递到adapter里面去
                                                TextView tvContent = view.findViewById(R.id.tv_content);
                                                strContent = tvContent.getText().toString();
                                                mAdapter.changeState(position);
                                                selectorPosition = position;
                                            }
                                        }
        );
    }

    private void TextChange(final ImageView ivTipClose) {
        etResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s)){
                    ivTipClose.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setExceptionData(List<AnomalousBean> anomalousBeanList) {
        anomalousBeanLists.addAll(anomalousBeanList);
    }
}
