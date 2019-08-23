package com.shuben.zhixing.www.inspection.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.view.Toasty;
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

public class ItemAdapter extends BaseAdapter  {
    private Activity mContext;
    private LayoutInflater inflater;
    private List<ItemInfo> mlist;


    private Intent intent;
    private EditText ed_workplace;
    private List<ParamInfo> plist=null;
    private String Status;
    private  ParamAdapter adapter;
    private Handler handler;


    public ItemAdapter(Activity mContext, List<ItemInfo> list,String Status,Handler handler) {
        this.mContext = mContext;
        this.mlist=list;
        this.Status=Status;
        this.handler = handler;
    }
    public void updata(List<ItemInfo> list){
        this.mlist = list;
        notifyDataSetChanged();
    }
    ViewHolder holder;
    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null
                || convertView.getTag(R.mipmap.ic_launcher + position) == null)  {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.inspection_list_item, null);


            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.tx01=(TextView) convertView.findViewById(R.id.tx_num);
            holder.tx02=(TextView) convertView.findViewById(R.id.tx_itemName);
            holder.tx03= convertView.findViewById(R.id.ed_workplace);
            holder.tx04 = convertView.findViewById(R.id.ed_shuzhi);//数值
            holder.tx09 = convertView.findViewById(R.id.ed_paizhao);
            holder.bnt_ok=(TextView) convertView.findViewById(R.id.tx_ok);
            holder.bnt_ng=(TextView) convertView.findViewById(R.id.tx_ng);
            holder.lay_list=(LinearLayout) convertView.findViewById(R.id.lay_list);
            holder.listView=(ListView) convertView.findViewById(R.id.param_listView);
            holder.root_li = convertView.findViewById(R.id.root_li);
            convertView.setTag(R.mipmap.ic_launcher + position);
        }else
        {
            holder = (ViewHolder) convertView.getTag(R.mipmap.ic_launcher
                    + position);
        }
        ItemInfo  info=mlist.get(position);
        holder.tx01.setText(position+1+"");
        holder.tx02.setText(info.getItemName());
        holder.bnt_ok.setFocusable(false);
        holder.bnt_ok.setOnClickListener(new lvButtonListener(position,info));
        holder.bnt_ng.setFocusable(false);
        holder.bnt_ng.setOnClickListener(new lvButtonListener(position,info));
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



        switch (info.getPatrolFashion()){
            case "001":
                //拍照
                setViewHidden(holder,0,0,1,info);
                break;
            case "002":
                //文本
                setViewHidden(holder,1,0,0,info);

                break;
            case "003":
                //数值
                setViewHidden(holder,0,1,0,info);

                break;
            case "004":
                //文本+拍照
                setViewHidden(holder,1,0,1,info);

                break;
            case "005":
                //数值+拍照
                setViewHidden(holder,0,1,1,info);

                break;
                
        }
        holder.tx09.setOnClickListener((v)->{
            Message msg = new Message();
            msg.what = 4;
            msg.arg1 = position;
            handler.sendMessage(msg);
        });
        holder.tx03.setText(info.getTx03V());
        holder.tx03.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    P.c("输入"+editable.toString());
                    mlist.get(position).setTx03V(editable.toString());

            }
        });
        holder.tx04.setText(info.getTx04V());
        holder.tx04.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mlist.get(position).setTx04V(editable.toString());

            }
        });
        /*if(info.getList().size()>0){
            //holder.lay_list.setVisibility(View.VISIBLE);
//            info.
            adapter=new ParamAdapter(mContext,info.getList(),myHandler);
            holder.listView.setAdapter(adapter);
        }*/


        //holder.listView.setAdapter();

      /*  holder.tx04.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    curPosition = position;
                }
                return false;
            }
        });
        holder.tx04.clearFocus();
        if (curPosition != -1 && curPosition == position) {
            P.c("记录"+curPosition);
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            holder.tx04.requestFocus();
        }*/
        P.c("刷新"+position);

        return convertView;
    }
    private int curPosition = -1;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //itemList.get(curPosition).setValue(s.toString()); // 把当前输入的内容保存进当前位置的数组内
        }
    };
    private void setViewHidden(ViewHolder holder,int i0,int i1,int i2,ItemInfo info){
        if(i0==1){
            //说明
            holder.tx03.setVisibility(View.VISIBLE);
        }else {
            holder.tx03.setVisibility(View.GONE);
        }
        if(i1==1){
            holder.tx04.setVisibility(View.VISIBLE);
        }else {
            holder.tx04.setVisibility(View.GONE);
        }
        if(i2==1){
            holder.tx09.setVisibility(View.VISIBLE);
            if(info.getPath()==null){
                //是空值
            }else{
                MyImageLoader.load(mContext,info.getPath(),holder.tx09);
            }


        }else {
            holder.tx09.setVisibility(View.GONE);
        }
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
        public EditText tx03,tx04;
        public ImageView tx09;
        public LinearLayout lay_list,root_li;
        public ListView listView;

    }
    class lvButtonListener implements View.OnClickListener {
        private int position;
        private ItemInfo info;
        lvButtonListener(int pos,ItemInfo info) {
            this.position = pos;
            this.info  = info;
        }

        @Override
        public void onClick(View v) {


            if (v.getId() == R.id.tx_ok) {

                notifyDataSetChanged();
                if(Status.equals("已完成")){
                    Toast.makeText(mContext,"任务已结束",Toast.LENGTH_SHORT).show();
                }else {
                    //mlist.get(position).setResult("OK");
//                    saveInspection( mlist.get(position).getPatrolRecord(),"OK",mlist.get(position).getPatrolTaskId(),mlist.get(position).getItemSource(),mContext.getIntent().getStringExtra("ClassId"),mlist.get(position).getItemId());


                    //显示的

                    String temp;
                    switch (info.getPatrolFashion()) {
                        case "001":
                            //拍照
                            if(info.getPath()==null){
                                Toasty.INSTANCE.showToast(mContext, "请拍照");
                                return;
                            }
                            break;
                        case "002":
                            //文本
                            if (info.getTx03V() == null) {
                                Toasty.INSTANCE.showToast(mContext, "请输入说明");
                                return;
                            }

                            break;
                        case "003":
                            //数值
                            if (info.getTx04V() == null) {
                                Toasty.INSTANCE.showToast(mContext, "请输入数值");
                                return;
                            }

                            break;
                        case "004":
                            //文本+拍照
                            if (info.getTx03V() == null) {
                                Toasty.INSTANCE.showToast(mContext, "请输入说明");

                                return;
                            }
                            if(info.getPath()==null){
                                Toasty.INSTANCE.showToast(mContext, "请拍照");
                                return;
                            }

                            break;
                        case "005":
                            //数值+拍照
                            if (info.getTx04V() == null) {
                                Toasty.INSTANCE.showToast(mContext, "请输入数值");
                                return;
                            }
                            if(info.getPath()==null){
                                Toasty.INSTANCE.showToast(mContext, "请拍照");
                                return;
                            }
                            break;


                    }

                    Message msg = new Message();
                    msg.what = 3;
                    msg.arg1 = position;
                    handler.sendMessage(msg);
                }

                }else if((v.getId()) == R.id.tx_ng) {

                    Message msg = new Message();
                    msg.what = 2;
                    msg.arg1 = position;
                    handler.sendMessage(msg);


               /* if(Status.equals("已完成")){
                    Toast.makeText(mContext,"任务已结束",Toast.LENGTH_SHORT).show();
                }else{
                    mlist.get(position).setResult("NG");
                    saveInspection( mlist.get(position).getPatrolRecord(),"NG", mlist.get(position).getPatrolTaskId(),mlist.get(position).getItemSource(),mContext.getIntent().getStringExtra("ClassId"),mlist.get(position).getItemId());

                }*/


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





