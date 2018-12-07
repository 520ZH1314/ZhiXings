package com.shuben.zhixing.www.inspection.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.AddProblemActivity;
import com.shuben.zhixing.www.inspection.bean.ItemInfo;
import com.shuben.zhixing.www.inspection.bean.ParamInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/7/19.
 */

public class ItemAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private List<ItemInfo> mlist;
    private ItemInfo info;
    private ViewHolder holder;
    private Intent intent;
    private EditText ed_workplace;
    private List<ParamInfo> plist=null;
    private String Status;
    private  ParamAdapter adapter;
    public ItemAdapter(Activity mContext, List<ItemInfo> list,String Status) {
        this.mContext = mContext;
        this.mlist=list;
        this.Status=Status;
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.inspection_list_item, null);


            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.tx01=(TextView) convertView.findViewById(R.id.tx_num);
            holder.tx02=(TextView) convertView.findViewById(R.id.tx_itemName);
            holder.tx03=(EditText) convertView.findViewById(R.id.ed_workplace);
            holder.bnt_ok=(TextView) convertView.findViewById(R.id.tx_ok);
            holder.bnt_ng=(TextView) convertView.findViewById(R.id.tx_ng);
            holder.lay_list=(LinearLayout) convertView.findViewById(R.id.lay_list);
            holder.listView=(ListView) convertView.findViewById(R.id.param_listView);

            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        info=mlist.get(position);
        holder.tx01.setText(position+1+"");
        holder.tx02.setText(info.getItemName());
        holder.bnt_ok.setFocusable(false);
        holder.bnt_ok.setOnClickListener(new lvButtonListener(position));
        holder.bnt_ng.setFocusable(false);
        holder.bnt_ng.setOnClickListener(new lvButtonListener(position));
        if(info.getResult().equals("OK")){
            holder.bnt_ok.setTextColor(Color.GREEN);
            holder.bnt_ng.setTextColor(Color.GRAY);
        }else if(info.getResult().equals("NG")){
            holder.bnt_ok.setTextColor(Color.GRAY);
            holder.bnt_ng.setTextColor(Color.RED);
        }else{
            holder.bnt_ok.setTextColor(Color.GRAY);
            holder.bnt_ng.setTextColor(Color.GRAY);
        }
        if(info.getList().size()>0){
            //holder.lay_list.setVisibility(View.VISIBLE);
            adapter=new ParamAdapter(mContext,info.getList(),myHandler);
            holder.listView.setAdapter(adapter);
        }


        //holder.listView.setAdapter();

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
        public TextView tx01,tx02,bnt_ok,bnt_ng,tx06,tx07,tx08;
        public EditText tx03;
        public LinearLayout lay_list;
        public ListView listView;

    }
    class lvButtonListener implements View.OnClickListener {
        private int position;

        lvButtonListener(int pos) {
            this.position = pos;
        }

        @Override
        public void onClick(View v) {


            if ((v.getId()) == (holder.bnt_ok.getId())) {
                if(Status.equals("已完成")){
                    Toast.makeText(mContext,"任务已结束",Toast.LENGTH_SHORT).show();
                }else{
                    mlist.get(position).setResult("OK");
                    saveInspection( mlist.get(position).getPatrolRecord(),"OK",mlist.get(position).getPatrolTaskId(),mlist.get(position).getItemSource(),mContext.getIntent().getStringExtra("ClassId"),mlist.get(position).getItemId());
                }

                }else if((v.getId()) == (holder.bnt_ng.getId())) {

                if(Status.equals("已完成")){
                    Toast.makeText(mContext,"任务已结束",Toast.LENGTH_SHORT).show();
                }else{
                    mlist.get(position).setResult("NG");
                    saveInspection( mlist.get(position).getPatrolRecord(),"NG", mlist.get(position).getPatrolTaskId(),mlist.get(position).getItemSource(),mContext.getIntent().getStringExtra("ClassId"),mlist.get(position).getItemId());

                }


            }
            notifyDataSetChanged();



        }
    }

    private void saveInspection(final String PatrolRecordId, final String Result,final String PatrolTaskId,final String ItemSource,final String ClassId,final String ItemId) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "EditPatrolRecord");
        params.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
        params.put("PatrolRecordId", PatrolRecordId);
        params.put("Result",Result);
        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "PatrolInspection");
            mJson.put("ApiCode", "EditPatrolRecord");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
            mJson.put("PatrolRecordId", PatrolRecordId);
            mJson.put("Result",Result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("KKKK保存巡检状态KKKK", " " + mJson.toString());



        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(mContext).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK保存巡检状态KKKK", " " + jsonobj.toString());
                try {
                    String status=jsonobj.getString("status");
                    Log.e("*********status:",status);

                    if(status.equals("true")){
                        if(Result.equals("OK")){
                        }else if(Result.equals("NG")){
                            Intent intent=new Intent();
                            intent.setClass(mContext, AddProblemActivity.class);
                            intent.putExtra("PatrolTaskId",PatrolTaskId);
                            intent.putExtra("ItemSource",ItemSource);
                            intent.putExtra("ItemId",ItemId);
                            intent.putExtra("ClassId",ClassId);
                            mContext.startActivity(intent);
                        }

                       // myHandler.sendEmptyMessage(1);



                    }else{
                        myHandler.sendEmptyMessage(2);

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

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    holder.bnt_ok.setTextColor(Color.GREEN);
                    Toast.makeText(mContext,"状态保存成功",Toast.LENGTH_SHORT).show();

                    break;
                case 2:
                    Toast.makeText(mContext,"状态保存失败",Toast.LENGTH_SHORT).show();
                    break;
                case 888:
                    Toast.makeText(mContext,"数据改变",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }



    };







}





