package com.shuben.zhixing.www.patrol.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.photopicker.PhotoPreview;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.RecordInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.SysUtils;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.MyGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/3/29.
 */

public class RecordAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<RecordInfo> data;
    private RecordInfo recordInfo;
    private int wh;
    private TestGridViewAdapter nearByInfoImgsAdapter;

    public RecordAdapter(Activity mContext, List<RecordInfo> data) {
        this.mContext = mContext;
        this.data=data;
        this.wh = (SysUtils.getScreenWidth(mContext) - SysUtils.Dp2Px(mContext, 99)) / 3;
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.patrol_record_item, null);
            holder.item01=(TextView) convertView.findViewById(R.id.tx_item01);//
            holder.status=(TextView) convertView.findViewById(R.id.tx_staus);//
            holder.item02=(TextView) convertView.findViewById(R.id.tx_item02);
            holder.item03=(TextView) convertView.findViewById(R.id.tx_item03);
            holder.item04=(TextView) convertView.findViewById(R.id.tx_item04);
            holder.item05=(TextView) convertView.findViewById(R.id.tx_item05);
            holder.item06=(TextView) convertView.findViewById(R.id.tx_item06);
            holder.item07=(TextView) convertView.findViewById(R.id.tx_item07);
            holder.item08=(TextView) convertView.findViewById(R.id.tx_item08);
            holder.item10=(TextView) convertView.findViewById(R.id.tx_item10);
            holder.item11=(TextView) convertView.findViewById(R.id.tx_item11);
            holder.item12=(TextView) convertView.findViewById(R.id.tx_item12);
            holder.item13=(TextView) convertView.findViewById(R.id.tx_item13);
            holder.bnt_thumb=(Button) convertView.findViewById(R.id.bnt_thumb);


            holder.ratingBar=(RatingBar) convertView.findViewById(R.id.tx_item09);
            holder.gridView01=(MyGridView)convertView.findViewById(R.id.grid01);
            holder.gridView02=(MyGridView)convertView.findViewById(R.id.grid02);
            holder.lay01=(LinearLayout) convertView.findViewById(R.id.lay01); 
            holder.lay02=(LinearLayout) convertView.findViewById(R.id.lay02);
            holder.lay03=(LinearLayout) convertView.findViewById(R.id.lay03);
            holder.lay04=(LinearLayout) convertView.findViewById(R.id.lay04);
            holder.lay05=(RelativeLayout) convertView.findViewById(R.id.lay05);

            
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag

            convertView.setTag(holder);

        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        recordInfo=data.get(position);
        String item09 = null, item10 = null;
        if (recordInfo != null) {
            item09 = recordInfo.getR_item15();
            item10 = recordInfo.getR_item16();
        }
        
        holder.item01.setText(recordInfo.getR_item03());
        if(recordInfo.getR_item14().equals("0")){
            holder.status.setText("未处理");
        }else if(recordInfo.getR_item14().equals("5")){
            holder.status.setText("待审核");
        }else if(recordInfo.getR_item14().equals("10")){
            holder.status.setText("已通过");
        }

        holder.item02.setText(recordInfo.getR_item02());
        holder.item03.setText(recordInfo.getR_item01());
        holder.item04.setText(recordInfo.getR_item09());
        holder.item05.setText(recordInfo.getR_item11());
        holder.item06.setText(recordInfo.getR_item12().substring(5,recordInfo.getR_item12().length()));
        if(recordInfo.getR_item13().equals("")||recordInfo.getR_item13().equals("null")){
            holder.item07.setVisibility(View.INVISIBLE);
        }else {
            holder.item07.setText(recordInfo.getR_item13().substring(5,recordInfo.getR_item13().length()));
        }

        holder.item08.setText(recordInfo.getR_item06());
        holder.ratingBar.setRating(recordInfo.getR_item07());
        //是否含有图片
        if (item09 != null && !item09.equals("")) {
            holder.lay01.setVisibility(View.VISIBLE);
            initInfoImages01(holder.gridView01, item09);
        } else {
            holder.lay01.setVisibility(View.GONE);
        }

        if(!recordInfo.getR_item08().equals("null")&&recordInfo.getR_item08()!=null){
            holder.lay02.setVisibility(View.VISIBLE);
            holder.item10.setText(recordInfo.getR_item08());
            if (item10 != null && !item10.equals("")) {
                initInfoImages02(holder.gridView02, item10);
            }

        }else{
            holder.lay02.setVisibility(View.GONE);
        }
        if(!recordInfo.getR_item10().equals("null")&&recordInfo.getR_item10()!=null){

            holder.lay03.setVisibility(View.VISIBLE);
            if(recordInfo.getR_item17().equals("0")){
                holder.item11.setText("不通过");
            }else{
                holder.item11.setText("通过");
            }
            holder.item12.setText(recordInfo.getR_item10());
        }else{
            holder.lay03.setVisibility(View.GONE);
        }
        if(recordInfo.getR_item19().equals("案例库")){
            holder.lay05.setVisibility(View.VISIBLE);
            holder.item13.setText(recordInfo.getR_item18()+"");
        }else{
            holder.lay05.setVisibility(View.INVISIBLE);
        }
        holder.bnt_thumb.setOnClickListener(new RecordAdapter.lvButtonListener(position));
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
        public TextView item01, status,item02, item03, item04, item05, item06,item07,item08, item10,item11,item12,item13;
        private MyGridView gridView01,gridView02;
        private RatingBar ratingBar;
        private Button bnt_thumb;
        private LinearLayout lay01,lay02,lay03,lay04;
        private RelativeLayout lay05;
    }
    class lvButtonListener implements View.OnClickListener {
        private int position;

        lvButtonListener(int pos) {
            this.position = pos;
        }


        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            if ((v.getId()) == (holder.bnt_thumb.getId())) {
                  thumb(data,position);

            }
        }
    }

    private void thumb(final List<RecordInfo> data, final int position) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditProblemThumb");
        params.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
        params.put("ProblemId",data.get(position).getR_item04());

        final JSONObject mData=new JSONObject();
        try {
            mData.put("AppCode", "LinePatrol");
            mData.put("ApiCode", "EditProblemThumb");
            mData.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
            mData.put("ProblemId",data.get(position).getR_item04());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******亮点提交*****",data.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(mContext).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String status=jsonobj.getString("status");
                    String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        Toast.makeText(mContext,"点赞成功",Toast.LENGTH_SHORT).show();
                        data.get(position).setR_item18(data.get(position).getR_item18()+1);
                        notifyDataSetChanged();
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
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
        requestQueue.add(newMissRequest);
    }


    /**
     * 加载信息中包含的图片内容
     *
     * @param
     */
    public void initInfoImages01(MyGridView gv_images, final String imgspath) {
        if (imgspath != null && !imgspath.equals("")) {
            String[] imgs = imgspath.split("#");
            final List<String> mlist = new ArrayList<String>();
            for (int i = 0; i < imgs.length; i++) {
                mlist.add(imgs[i]);
            }
            holder.gridView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PhotoPreview.init().setPhotoPaths(mlist).setCurrentPos(position).setPreviewOnly(true).startPreview(mContext, null);

                }

            });

            int w = 0;
            switch (mlist.size()) {
                case 1:
                    w = wh;
                    gv_images.setNumColumns(1);
                    break;
                case 2:
                case 4:
                    w = 2 * wh + SysUtils.Dp2Px(mContext, 2);
                    gv_images.setNumColumns(2);
                    break;
                case 3:
                case 5:
                case 6:
                    w = wh * 3 + SysUtils.Dp2Px(mContext, 2) * 2;
                    gv_images.setNumColumns(3);
                    break;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, RelativeLayout.LayoutParams.WRAP_CONTENT);
            gv_images.setLayoutParams(lp);
            nearByInfoImgsAdapter = new TestGridViewAdapter(mContext, mlist);
            gv_images.setAdapter(nearByInfoImgsAdapter);
           /* gv_images.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Toast.makeText(mContext, "点击了第"+(arg2+1)+"张图片", Toast.LENGTH_LONG).show();
                }
            });*/
        }

    }
    public void initInfoImages02(MyGridView gv_images, final String imgspath) {
        if (imgspath != null && !imgspath.equals("")) {
            String[] imgs = imgspath.split("#");
            final List<String> mlist = new ArrayList<String>();
            for (int i = 0; i < imgs.length; i++) {
                mlist.add(imgs[i]);
            }
            holder.gridView02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PhotoPreview.init().setPhotoPaths(mlist).setCurrentPos(position).setPreviewOnly(true).startPreview(mContext, null);

                }

            });

            int w = 0;
            switch (mlist.size()) {
                case 1:
                    w = wh;
                    gv_images.setNumColumns(1);
                    break;
                case 2:
                case 4:
                    w = 2 * wh + SysUtils.Dp2Px(mContext, 2);
                    gv_images.setNumColumns(2);
                    break;
                case 3:
                case 5:
                case 6:
                    w = wh * 3 + SysUtils.Dp2Px(mContext, 2) * 2;
                    gv_images.setNumColumns(3);
                    break;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, RelativeLayout.LayoutParams.WRAP_CONTENT);
            gv_images.setLayoutParams(lp);
            nearByInfoImgsAdapter = new TestGridViewAdapter(mContext, mlist);
            gv_images.setAdapter(nearByInfoImgsAdapter);
           /* gv_images.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Toast.makeText(mContext, "点击了第"+(arg2+1)+"张图片", Toast.LENGTH_LONG).show();
                }
            });*/
        }

    }




}


