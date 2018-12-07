package com.shuben.zhixing.www.inspection.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.bean.ItemInfo;
import com.shuben.zhixing.www.inspection.bean.ParamInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/7/31.
 */

public class ParamAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private List<ParamInfo> plist;
    private List<ItemInfo> mlist;
    private ParamInfo pInfo;
    private ParamAdapter.ViewHolder holder;
    private Intent intent;
    private EditText ed_workplace;
    private int index=0;
    private int dex=0;
    private Handler handler;
    private  ItemInfo info;
    private HashMap<Integer, String> saveMap;//这个集合用来存储对应位置上Editext中的文本内容
    public ParamAdapter(Activity mContext, List<ParamInfo> list, Handler handler) {
        this.mContext = mContext;
        this.plist=list;
        this.handler=handler;
        saveMap = new HashMap<Integer, String>();
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            holder = new ParamAdapter.ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.inspection_param_item, null);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.tx01=(TextView) convertView.findViewById(R.id.param_item);
            holder.tx02=(TextView) convertView.findViewById(R.id.param_name);
            holder.param_vlaue=(EditText) convertView.findViewById(R.id.param_vlaue);
            holder.tx03=(TextView) convertView.findViewById(R.id.param_type);
            convertView.setTag(holder);
        }else
        {
            holder = (ParamAdapter.ViewHolder)convertView.getTag();
        }







        pInfo=plist.get(position);
        holder.tx01.setText(position+1+"");
        holder.tx02.setText(pInfo.getParamName());
        //holder.param_vlaue.setText(plist.get(position).getPatrolValue());

        holder.param_vlaue.setTag(position);//设置editext一个标记
        holder.param_vlaue.clearFocus();//清除焦点  不清除的话因为item复用的原因   多个Editext同时改变
        holder.param_vlaue.setText(saveMap.get(position));//将对应保存在集合中的文本内容取出来  并显示上去
        notifyDataSetChanged();

        if(pInfo.getCategoryName().equals("标准数值型")){
            holder.tx03.setText(pInfo.getParamUnit()+"("+"标准值:"+pInfo.getStandValue()+")");
        }else  if(pInfo.getCategoryName().equals("上限型")){
            holder.tx03.setText(pInfo.getParamUnit()+"("+"上限值:"+pInfo.getStandMaxValue()+")");
        }else  if(pInfo.getCategoryName().equals("下限型")){
            holder.tx03.setText(pInfo.getParamUnit()+"("+"下限值:"+pInfo.getStandMinValue()+")");
        }else  if(pInfo.getCategoryName().equals("区间内OK型")){
            holder.tx03.setText(pInfo.getParamUnit()+"("+"区间内值:"+pInfo.getStandMinValue()+"-"+pInfo.getStandMaxValue()+")");
        }else  if(pInfo.getCategoryName().equals("区间外OK型")){
            holder.tx03.setText(pInfo.getParamUnit()+"("+"区间外值:"+"大于"+pInfo.getStandMinValue()+"或小于"+pInfo.getStandMaxValue()+")");
        }


       /* holder.param_vlaue.setTag(position);
        holder.param_vlaue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index = (Integer) v.getTag();
                }
                return false;
            }
        });
        holder.param_vlaue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if(index==position){

                    Log.e("%%%%%%%%%", plist.get(position).getPatrolValue());

                    if(ParamUtil.getInstance(mContext).paramCheck(holder.param_vlaue.getText().toString(),plist.get(position))){
                         //plist.get(position).setStatus("true");
                        holder.param_vlaue.setTextColor(Color.GREEN);
                        Log.e("判断：","合格");
                    }else{
                       // plist.setStatus("false");
                        holder.param_vlaue.setTextColor(Color.RED);
                        Log.e("判断：","不合格");
                    }
                    notifyDataSetChanged();
                }
                   handler.sendEmptyMessage(63);*//*
                }


        });*/

        final EditText tempEditText=holder.param_vlaue;
     /*   holder.param_vlaue.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                Integer tag= (Integer) tempEditText.getTag();
                saveMap.put(tag, s.toString());//在这里根据position去保存文本内容
                handler.sendEmptyMessage(888);
                notifyDataSetChanged();
            }
        });
        Log.e("TAG", saveMap.toString());*/

      holder.param_vlaue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==66){
                    Integer tag= (Integer) tempEditText.getTag();
                    saveMap.put(tag,  holder.param_vlaue.getText().toString());//在这里根据position去保存文本内容
                       /* JSONArray jsonArray=new JSONArray();
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("PatrolResultId",plist.get(position).getPatrolResultId());
                            jsonObject.put("PatrolValue",holder.param_vlaue.getText().toString());
                            plist.get(position).setPatrolValue(holder.param_vlaue.getText().toString());
                            // handler.sendEmptyMessage(IPQCHANDLE02);
                            jsonArray.put(0,jsonObject);
                            commit(jsonArray,index,"OK");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }*/

                }

                return false;
            }
        });



        return convertView;
    }
    @Override
    //在此适配器中所代表的数据集中的条目数
    public int getCount() {
        return plist.size();
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
        public TextView tx01,tx02,tx03;
        public EditText param_vlaue;

    }
    class lvButtonListener implements View.OnClickListener {
        private int position;

        lvButtonListener(int pos) {
            this.position = pos;
        }

        @Override
        public void onClick(View v) {




        }
    }


    private void commit(JSONArray jsonArray,int index,String result) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "EditPatrolRecord");
        params.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
        params.put("PatrolRecordId",plist.get(index).getPatrolRecordId());
        params.put("Result", result);
        params.put("PatrolResult", jsonArray.toString());
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "PatrolInspection");
            myData.put("ApiCode", "EditPatrolRecord");
            params.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
            myData.put("PatrolRecordId",plist.get(index).getPatrolRecordId());
            myData.put("Result", result);
            myData.put("PatrolResult", jsonArray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("********带参数提交*******",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(mContext).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String status=jsonobj.getString("status");
                    Log.e("*********status:",status);

                    if(status.equals("true")){
                        //myHandler.sendEmptyMessage(1);

                    }else{
                        //myHandler.sendEmptyMessage(1);

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

