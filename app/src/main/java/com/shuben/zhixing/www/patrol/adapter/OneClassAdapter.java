package com.shuben.zhixing.www.patrol.adapter;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.ThreeClassActivity;
import com.shuben.zhixing.www.patrol.TwoClassActivity;
import com.shuben.zhixing.www.patrol.bean.OneClassInfo;
import com.shuben.zhixing.www.patrol.oneclass.OneFindActivity;
import com.shuben.zhixing.www.patrol.twoclass.TwoFindActivity;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shuben.zhixing.www.util.DateUtil.getCountdown;


/**
 * Created by Geyan on 2018/3/15.
 */

public class OneClassAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private  List<OneClassInfo> data;
    private OneClassInfo oneClassInfo;

    public OneClassAdapter(Activity mContext, List<OneClassInfo> data) {
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
            convertView = inflater.inflate(R.layout.list_item_oneclass, null);
            holder.bnt_start=(Button) convertView.findViewById(R.id.bnt_start);
            holder.item01=(TextView) convertView.findViewById(R.id.tx_bh_value);
            holder.item02=(TextView) convertView.findViewById(R.id.tx_workspace_value);
            holder.item03=(TextView) convertView.findViewById(R.id.tx_area_value);
            holder.item04=(TextView) convertView.findViewById(R.id.tx_countdown_value);
            holder.item05=(TextView) convertView.findViewById(R.id.tx_patroltime_value);
            holder.item06=(TextView) convertView.findViewById(R.id.tx_status_value);
            holder.item07=(TextView) convertView.findViewById(R.id.tx_task);
            holder.item08=(TextView) convertView.findViewById(R.id.tx_class_value);
            holder.lay01=(RelativeLayout)convertView.findViewById(R.id.lay01);

            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        oneClassInfo=data.get(position);
        holder.item01.setText(oneClassInfo.getItem01());
        holder.item02.setText(oneClassInfo.getItem02());
        holder.item03.setText(oneClassInfo.getItem03());
        holder.item04.setText(oneClassInfo.getItem04());
        DateUtil.setCountdownColor(oneClassInfo.getItem05(),data.get(position).getItem09(),holder.item04);
        holder.item05.setText(oneClassInfo.getItem05().substring(5,oneClassInfo.getItem05().length()));
        holder.item06.setText(oneClassInfo.getItem06());
        if(!oneClassInfo.getItem06().equals("未启动")){
            holder.bnt_start.setText("进入巡线");
        }
        holder.bnt_start.setFocusable(false);
        holder.bnt_start.setOnClickListener(new lvButtonListener(position));
        holder.item08.setText(data.get(position).getItem09());
        Log.e("*******AAAAAAAAA******",data.get(position).getItem14()+"");
        if(!data.get(position).getItem14().equals("父任务")){
            holder.item07.setVisibility(View.VISIBLE);
        }else{
            holder.item07.setVisibility(View.INVISIBLE);
        }

        if(data.get(position).getItem02().equals("")||data.get(position).getItem02()==null){
            holder.lay01.setVisibility(View.GONE);
        }else{
            holder.lay01.setVisibility(View.VISIBLE);
        }
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
        public TextView item01, item02, item03, item04, item05, item06,item07,item08;
        private Button bnt_start;
        private  RelativeLayout lay01;
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
                Intent intent=new Intent();
                if(data.get(position).getItem09().equals("一级巡线")){
                    if(getCountdown(data.get(position).getItem05()).equals("已超期")){
                        showPopWindow(data,position);
                    }else{
                        if(data.get(position).getItem06().equals("未启动")){
                            startPattrol( data.get(position).getItem03());
                        }
                        intent.setClass(mContext, OneFindActivity.class);
                        intent.putExtra("FormNo", data.get(position).getItem01());//
                        intent.putExtra("WorkShopName", data.get(position).getItem02());//
                        intent.putExtra("RecordId", data.get(position).getItem03());//
                        intent.putExtra("WorkShopId", data.get(position).getItem10());//车间
                        intent.putExtra("AreaId", data.get(position).getItem11());//区域
                        intent.putExtra("LineId", data.get(position).getItem12());//线体
                        intent.putExtra("ClassId", data.get(position).getItem13());//巡线级别
                        intent.putExtra("LiableUserName", data.get(position).getItem07());//
                        intent.putExtra("Countdown", data.get(position).getItem05());//
                        intent.putExtra("Status", data.get(position).getItem06());//


                        mContext.startActivityForResult(intent,UrlUtil.PatrolTaskActivity_RequstCode);
                    }



                }else if(data.get(position).getItem09().equals("二级巡线")){

                        if(getCountdown(data.get(position).getItem09()).equals("已超期")){
                            showPopWindow(data,position);
                        }else{
                            if(data.get(position).getItem06().equals("未启动")){
                                startPattrol( data.get(position).getItem03());
                            }

                            if(data.get(position).getItem14().equals("父任务")){
                                intent.setClass(mContext, TwoClassActivity.class);
                            }else{
                                intent.setClass(mContext, TwoFindActivity.class);
                            }

                            intent.putExtra("FormNo", data.get(position).getItem01());//
                            intent.putExtra("WorkShopName", data.get(position).getItem02());//
                            intent.putExtra("RecordId", data.get(position).getItem03());//

                            intent.putExtra("Countdown", data.get(position).getItem04());//巡线倒计时
                            intent.putExtra("PatrolDate", data.get(position).getItem05());//巡线时间
                            intent.putExtra("Status", data.get(position).getItem06());//

                            intent.putExtra("WorkShopId", data.get(position).getItem10());//车间
                            intent.putExtra("AreaId", data.get(position).getItem11());//区域
                            intent.putExtra("LineId", data.get(position).getItem12());//线体
                            intent.putExtra("ClassId", data.get(position).getItem13());//巡线级别
                            intent.putExtra("SubjectId", data.get(position).getItem15());//巡线级别
                            intent.putExtra("SubjectName", data.get(position).getItem16());//巡线主题
                            intent.putExtra("MeetingPlace", data.get(position).getItem17());//
                            mContext.startActivityForResult(intent,UrlUtil.PatrolTaskActivity_RequstCode);

                    }

                }else if(data.get(position).getItem09().equals("三级巡线")){

                    if(getCountdown(data.get(position).getItem09()).equals("已超期")){
                        showPopWindow(data,position);
                    }else {
                        if(data.get(position).getItem06().equals("未启动")){
                            startPattrol( data.get(position).getItem03());
                        }

                        if(data.get(position).getItem14().equals("父任务")){
                            intent.setClass(mContext, ThreeClassActivity.class);
                        }else{
                            intent.setClass(mContext, OneFindActivity.class);
                        }
                        intent.putExtra("FormNo", data.get(position).getItem01());//
                        intent.putExtra("WorkShopName", data.get(position).getItem02());//
                        intent.putExtra("RecordId", data.get(position).getItem03());//
                        intent.putExtra("Countdown", data.get(position).getItem04());//巡线倒计时
                        intent.putExtra("PatrolDate", data.get(position).getItem05());//巡线时间
                        intent.putExtra("Status", data.get(position).getItem06());//
                        intent.putExtra("WorkShopId", data.get(position).getItem10());//车间
                        intent.putExtra("AreaId", data.get(position).getItem11());//区域
                        intent.putExtra("LineId", data.get(position).getItem12());//线体
                        intent.putExtra("ClassId", data.get(position).getItem13());//巡线级别
                        //intent.putExtra("LiableUserName", oneClassInfo.getItem08());//
                        intent.putExtra("MeetingPlace", data.get(position).getItem17());//
                        mContext.startActivityForResult(intent,UrlUtil.PatrolTaskActivity_RequstCode);
                    }


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
                        Toast.makeText(mContext,"启动巡线",Toast.LENGTH_SHORT).show();
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


    private void showPopWindow(final List<OneClassInfo> data,final  int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = (LinearLayout) mContext. getLayoutInflater().inflate(R.layout.task, null);
        builder.setView(view);
        builder.setTitle("提醒");

        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                Intent intent=new Intent();
                if(data.get(position).getItem09().equals("一级巡线")){

                        if(data.get(position).getItem06().equals("未启动")){
                            startPattrol( data.get(position).getItem03());
                        }
                        intent.setClass(mContext, OneFindActivity.class);
                        intent.putExtra("FormNo", data.get(position).getItem01());//
                        intent.putExtra("WorkShopName", data.get(position).getItem02());//
                        intent.putExtra("RecordId", data.get(position).getItem03());//
                        intent.putExtra("WorkShopId", data.get(position).getItem10());//车间
                        intent.putExtra("AreaId", data.get(position).getItem11());//区域
                        intent.putExtra("LineId", data.get(position).getItem12());//线体
                        intent.putExtra("ClassId", data.get(position).getItem13());//巡线级别
                        intent.putExtra("LiableUserName", data.get(position).getItem07());//
                        intent.putExtra("Countdown", data.get(position).getItem05());//
                        intent.putExtra("Status", data.get(position).getItem06());//
                        mContext.startActivityForResult(intent,UrlUtil.PatrolTaskActivity_RequstCode);




                }else if(data.get(position).getItem09().equals("二级巡线")){
                        if(data.get(position).getItem06().equals("未启动")){
                            startPattrol( data.get(position).getItem03());
                        }
                        if(data.get(position).getItem14().equals("")){
                            intent.setClass(mContext, TwoClassActivity.class);
                        }else{
                            intent.setClass(mContext, TwoFindActivity.class);
                        }

                        intent.putExtra("FormNo", data.get(position).getItem01());//
                        intent.putExtra("WorkShopName", data.get(position).getItem02());//
                        intent.putExtra("RecordId", data.get(position).getItem03());//

                        intent.putExtra("Countdown", data.get(position).getItem04());//巡线倒计时
                        intent.putExtra("PatrolDate", data.get(position).getItem05());//巡线时间
                        intent.putExtra("Status", data.get(position).getItem06());//

                        intent.putExtra("WorkShopId", data.get(position).getItem10());//车间
                        intent.putExtra("AreaId", data.get(position).getItem11());//区域
                        intent.putExtra("LineId", data.get(position).getItem12());//线体
                        intent.putExtra("ClassId", data.get(position).getItem13());//巡线级别
                        intent.putExtra("SubjectId", data.get(position).getItem15());//巡线级别
                       intent.putExtra("SubjectName", data.get(position).getItem16());//巡线主题
                    intent.putExtra("MeetingPlace", data.get(position).getItem17());//
                    mContext.startActivityForResult(intent,UrlUtil.PatrolTaskActivity_RequstCode);

                }else if(data.get(position).getItem09().equals("三级巡线")){
                        if(data.get(position).getItem06().equals("未启动")){
                            startPattrol( data.get(position).getItem03());
                        }

                        if(data.get(position).getItem14().equals("")){
                            intent.setClass(mContext, ThreeClassActivity.class);
                        }else{
                            intent.setClass(mContext, OneFindActivity.class);

                        }
                        intent.putExtra("FormNo", data.get(position).getItem01());//
                        intent.putExtra("WorkShopName", data.get(position).getItem02());//
                        intent.putExtra("RecordId", data.get(position).getItem03());//
                        intent.putExtra("Countdown", data.get(position).getItem04());//巡线倒计时
                        intent.putExtra("PatrolDate", data.get(position).getItem05());//巡线时间
                        intent.putExtra("Status", data.get(position).getItem06());//
                        intent.putExtra("WorkShopId", data.get(position).getItem10());//车间
                        intent.putExtra("AreaId", data.get(position).getItem11());//区域
                        intent.putExtra("LineId", data.get(position).getItem12());//线体
                        intent.putExtra("ClassId", data.get(position).getItem13());//巡线级别
                        //intent.putExtra("LiableUserName", oneClassInfo.getItem08());//
                    intent.putExtra("MeetingPlace", data.get(position).getItem17());//
                    mContext.startActivityForResult(intent,UrlUtil.PatrolTaskActivity_RequstCode);

                }
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

