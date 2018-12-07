package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.GroupTaskInfo;
import com.shuben.zhixing.www.patrol.twoclass.TaskDetailActivity;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Geyan on 2018/5/21.
 */

public class GroupTaskAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<GroupTaskInfo> data;
    private GroupTaskInfo info;

    public GroupTaskAdapter(Context mContext, List<GroupTaskInfo> data) {
        this.mContext = mContext;
        this.data=data;
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.patrol_group_task_item, null);
            holder.bnt_start=(Button) convertView.findViewById(R.id.bnt_commit);
            holder.item01=(TextView) convertView.findViewById(R.id.tx_user);//
            holder.item02=(TextView) convertView.findViewById(R.id.tx_date);
            holder.item03=(TextView) convertView.findViewById(R.id.tx_item03);
            holder.item04=(TextView) convertView.findViewById(R.id.tx_item04);
            holder.item05=(TextView) convertView.findViewById(R.id.tx_item05);
            holder.item06=(TextView) convertView.findViewById(R.id.tx_item06);
            holder.tx_countdown=(TextView) convertView.findViewById(R.id.tx_countdown);
            holder.bnt_start=(Button) convertView.findViewById(R.id.bnt_commit);

            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag

            convertView.setTag(holder);

        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        info=data.get(position);
        holder.item01.setText(info.getItem04());
        holder.item02.setText(info.getItem05());
        holder.item03.setText(info.getItem06());
        holder.item04.setText(info.getItem07());
        holder.item05.setText(info.getItem08());
        holder.item06.setText(info.getItem09());
        holder.tx_countdown.setText(info.getItem10());
        return convertView;
    }
    @Override
    //在此适配器中所代表的数据集中的条目数
    public int getCount() {
        return data.size();
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
        public TextView item01, item02, item03, item04, item05, item06,tx_countdown;
        private Button bnt_start;
        private RatingBar ratingBar;
    }
    class lvButtonListener implements View.OnClickListener {
        private int position;

        lvButtonListener(int pos) {
            this.position = pos;
        }


        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            if ((v.getId()) == (holder.bnt_start.getId())) {

                if(data.get(position).getItem10().equals("未处理")){
                    startPattrol(data.get(position).getItem01());
                }else{
                    Intent intent=new Intent();
                    intent.setClass(mContext, TaskDetailActivity.class);
                    mContext.startActivity(intent);
                }
            }
        }
    }

    private void startPattrol(String recordId) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditPatrolLineStart");
        params.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
        params.put("RecordId", recordId);

        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "EditPatrolLineStart");
            myData.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
            myData.put("RecordId", recordId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("***************",myData.toString());



        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST,SharedPreferencesTool.getMStool(mContext).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {

                    String status=jsonobj.getString("status");
                    String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        Toast.makeText(mContext,"启动巡线",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        intent.setClass(mContext, TaskDetailActivity.class);
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




}
