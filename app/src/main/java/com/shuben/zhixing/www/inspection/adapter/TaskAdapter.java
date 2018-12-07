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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.InspectionItemActivity;
import com.shuben.zhixing.www.inspection.bean.TaskInfo;
import com.shuben.zhixing.www.inspection.bean.TypeInfo;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.PopupButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/7/5.
 */

public class TaskAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private List<TaskInfo> mlist;
    private TaskInfo info;
    private ViewHolder holder;
    private Intent intent;
    private String IsRelationWorksheet;
    private EditText ed_workplace;
    private PopupButton btn;
    private int index=0;
    private ListView lv;
    private List<TypeInfo> data=null;
    private  List<String> arr=new ArrayList<String>();
    private String pName="";
    private int mposition;
    private Map<String,String> pMap=new HashMap<String,String>();
    public TaskAdapter(Activity mContext, List<TaskInfo> list,String IsRelationWorksheet) {
        this.mContext = mContext;
        this.mlist=list;
        this.IsRelationWorksheet=IsRelationWorksheet;
         inflater = LayoutInflater.from(mContext);
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局

            convertView = inflater.inflate(R.layout.inspection_type_item, null);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.tx01=(TextView) convertView.findViewById(R.id.tx_bh_value);
            holder.tx02=(TextView) convertView.findViewById(R.id.tx_line_value);
            holder.tx03=(TextView) convertView.findViewById(R.id.tx_user_value);
            holder.tx04=(TextView) convertView.findViewById(R.id.tx_date_value01);
            holder.tx05=(TextView) convertView.findViewById(R.id.tx_date_value02);
            holder.tx06=(TextView) convertView.findViewById(R.id.tx_date_value03);
            holder.tx07=(TextView) convertView.findViewById(R.id.tx_status_value);
            holder.tx08=(TextView) convertView.findViewById(R.id.tx_class_value);
            holder.tx09=(TextView) convertView.findViewById(R.id.tx_pro_value01);

            holder.bnt_start=(Button) convertView.findViewById(R.id.bnt_start);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        info=mlist.get(position);
        holder.tx01.setText(info.getFormNo());
        holder.tx02.setText(info.getLineName());
        holder.tx03.setText(info.getPatrolUserName());
        holder.tx04.setText(info.getPlanPatrolTime());
        holder.tx05.setText(info.getRealPatrolDate());
        holder.tx06.setText(info.getEndPatrolDate());
        holder.tx07.setText(info.getStatus());
        holder.tx08.setText(info.getClassName());
        holder.tx09.setText(info.getProductName());
        holder.bnt_start.setFocusable(false);
        holder.bnt_start.setOnClickListener(new TaskAdapter.lvButtonListener(position));

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
        public TextView tx01,tx02,tx03,tx04,tx05,tx06,tx07,tx08,tx09;
        private Button bnt_start;

    }
    class lvButtonListener implements View.OnClickListener {
        private int mposition;

        lvButtonListener(int pos) {
            this.mposition = pos;
        }

        @Override
        public void onClick(View v) {
            Log.e("***IsRelationWorksheet",IsRelationWorksheet);
            Log.e("***Status",mlist.get(mposition).getStatus());
            if ((v.getId()) == (holder.bnt_start.getId())){
                if(IsRelationWorksheet.equals("1")&&mlist.get(mposition).getStatus().equals("未开始")){
                    showPopWindow(mlist,mposition);
                }else{
                    intent=new Intent();
                    intent.setClass(mContext, InspectionItemActivity.class);
                    intent.putExtra("PatrolTaskId",mlist.get(mposition).getPatrolTaskId());
                    intent.putExtra("Status",mlist.get(mposition).getStatus());
                    mContext.startActivityForResult(intent,UrlUtil.InspectionFragment01_RequstCode);
                }
            }



            }


    }


    private void startPattrol(final String PatrolTaskId, String WorkSheetNo,final String Status) {
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
                        intent.putExtra("Status",Status);

                        mContext.startActivityForResult(intent,UrlUtil.InspectionFragment01_RequstCode);
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
        builder.setTitle("当前产品");
        ed_workplace= (EditText) view.findViewById(R.id.ed_workplace);
        initType(position);

        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                Log.e("***index****","Index:"+position) ;
                Log.e("***TaskId****","TaskId:"+data.get(position).getPatrolTaskId()) ;
                startPattrol(data.get(position).getPatrolTaskId(),pMap.get(ed_workplace.getText().toString()),mlist.get(position).getStatus());

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

    private void initType(final  int position) {
        if(data==null){
            data=new ArrayList<TypeInfo>();
        }else{
            data.clear();
            arr.clear();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PMS");
        params.put("ApiCode", "GetWorkSheetList");
        params.put("PlanDate", DateUtil.getInstance().getDateOfToDay());
        params.put("Status", "5");
        params.put("LineId", mlist.get(position).getLineId());
        params.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "PMS");
            myData.put("ApiCode", "GetWorkSheetList");
            myData.put("PlanDate", DateUtil.getInstance().getDateOfToDay());
            myData.put("Status", "5");
            myData.put("LineId", mlist.get(position).getLineId());
            myData.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******巡检类别********",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(mContext).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());

                //dialog.dismiss();
                try {
                    int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");

                    JSONObject jData;


                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String WorkSheetNo=jData.getString("WorkSheetNo");//问题编号
                        String ProductName=jData.getString("ProductName");//车间
                        //ed_workplace.setText(WorkSheetNo);
                        pMap.put(ProductName,WorkSheetNo);
                        ed_workplace.setText(ProductName);


                        pName=ProductName;

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //dialog.dismiss();
            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );

        //dialog.show();
        requestQueue.add(newMissRequest);
    }





}




