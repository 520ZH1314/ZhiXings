package com.shuben.zhixing.www.inspection.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.InspectionItemActivity;
import com.shuben.zhixing.www.inspection.ProblemRecordActivity;
import com.shuben.zhixing.www.inspection.bean.InspectionQInfo;
import com.shuben.zhixing.www.inspection.bean.TaskInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/7/28.
 */

public class ProblemAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private List<InspectionQInfo> mlist;
    private InspectionQInfo info;
    private ProblemAdapter.ViewHolder holder;
    private Intent intent;
    private String IsRelationWorksheet;
    private EditText ed_workplace;
    public ProblemAdapter(Activity mContext, List<InspectionQInfo> list,String IsRelationWorksheet) {
        this.mContext = mContext;
        this.mlist=list;
        this.IsRelationWorksheet=IsRelationWorksheet;
    }
    private int des;
    public void setDes(int des){
        this.des = des;
        notifyDataSetChanged();
    }
    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            holder = new ProblemAdapter.ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.inspection_problem_item, null);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.tx01=(TextView) convertView.findViewById(R.id.tx_item01);
            holder.tx02=(TextView) convertView.findViewById(R.id.tx_item02);
            holder.tx03=(TextView) convertView.findViewById(R.id.tx_item03);
            holder.tx04=(TextView) convertView.findViewById(R.id.tx_item04);
            holder.tx05=(TextView) convertView.findViewById(R.id.tx_item05);
            holder.tx06=(TextView) convertView.findViewById(R.id.tx_item06);
            holder.tx07=(TextView) convertView.findViewById(R.id.tx_item07);
            holder.tx08=(TextView) convertView.findViewById(R.id.tx_item08);
            holder.bnt_start=(Button) convertView.findViewById(R.id.bnt_commit);
            convertView.setTag(holder);
        }else
        {
            holder = (ProblemAdapter.ViewHolder)convertView.getTag();
        }
        info=mlist.get(position);
        holder.bnt_start.setOnClickListener(new ProblemAdapter.lvButtonListener(position));
        holder.tx01.setText(info.getProblemNo());
        holder.tx02.setText(info.getStatus());
        holder.tx03.setText(info.getLiableDeptName());
        holder.tx04.setText(info.getLiableUserName());
        holder.tx05.setText(info.getDueDate());
        if(info.getCompleteDate()==null||info.getCompleteDate().equals("null")){
            holder.tx06.setText("无");
        }else{
            holder.tx06.setText(info.getCompleteDate());
        }

        holder.tx07.setText(info.getProblemDesc());
        holder.tx08.setText(info.getProductName());
        if(info.getStatus().equals("未开始")){
            if(des==1){
                holder.bnt_start.setText("查看详情");
            }else{
                holder.bnt_start.setText("进入处理");
            }

        }else{
            holder.bnt_start.setText("查看详情");
        }

        return convertView;
    }
    @Override
    //在此适配器中所代表的数据集中的条目数
    public int getCount() {
        return mlist.size();
    }

    @Override
    //获取数据集中与指定索引对应的数据项
    public Object getItem(int position) {
        return null;
    }

    @Override
    //获取在列表中与指定索引对应的行id
    public long getItemId(int position) {
        return position;
    }



    class ViewHolder
    {
        public TextView tx01,tx02,tx03,tx04,tx05,tx06,tx07,tx08;
        private Button bnt_start;

    }
    class lvButtonListener implements View.OnClickListener {
        private int position;

        lvButtonListener(int pos) {
            this.position = pos;
        }

        @Override
        public void onClick(View v) {
            if ((v.getId()) == (holder.bnt_start.getId())) {
                Intent intent=new Intent();
                intent.putExtra("ProblemId",mlist.get(position).getProblemId());
                intent.putExtra("Status",mlist.get(position).getStatus());
                intent.putExtra("LiableUserName",mlist.get(position).getLiableUserName());
                intent.setClass(mContext, ProblemRecordActivity.class);
                intent.putExtra("des",des);
                P.c("des"+des);
                mContext.startActivity(intent);
//                mContext.startActivityForResult(intent,UrlUtil.InspectionFragment02_RequstCode);
            }


        }
    }

    private void startPattrol(final String PatrolTaskId, String WorkSheetNo) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "EditPatrolInspectionStart");
        params.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
        params.put("PatrolTaskId", PatrolTaskId);
        params.put("WorkSheetNo", WorkSheetNo);

        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "PatrolInspection");
            mJson.put("ApiCode", "EditPatrolInspectionStart");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
            mJson.put("PatrolTaskId", PatrolTaskId);
            mJson.put("WorkSheetNo", WorkSheetNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("KKKK开始巡检参数KKKK", " " + mJson.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(mContext).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {

                    String status=jsonobj.getString("status");
                    String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        Toast.makeText(mContext,"启动巡检",Toast.LENGTH_SHORT).show();
                        intent=new Intent();
                        intent.setClass(mContext, InspectionItemActivity.class);
                        intent.putExtra("PatrolTaskId",PatrolTaskId);
                        mContext.startActivity(intent);


                    }else{
                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(newMissRequest);
    }


    private void showPopWindow(final List<TaskInfo> data, final  int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = (LinearLayout) mContext. getLayoutInflater().inflate(R.layout.inspection_task, null);
        builder.setView(view);
        builder.setTitle("工单录入");
        ed_workplace= (EditText) view.findViewById(R.id.ed_workplace);

        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                Intent intent=new Intent();
                startPattrol(data.get(position).getPatrolTaskId(),ed_workplace.getText().toString());

            }
        });
        builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();

    }

}


