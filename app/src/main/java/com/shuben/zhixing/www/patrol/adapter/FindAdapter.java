package com.shuben.zhixing.www.patrol.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.photopicker.PhotoPreview;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.FindInfo;
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
 * Created by Geyan on 2018/5/16.
 */

public class FindAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<FindInfo> data;
    private String type;
    private List<String> mList = new ArrayList<String>();

    private LoadingDailog dialog;
    private int wh;
    private TestGridViewAdapter nearByInfoImgsAdapter;

    public FindAdapter(Activity mContext, List<FindInfo> data, String type, LoadingDailog dialog) {
        this.mContext = mContext;
        this.data = data;
        this.type = type;
        this.dialog = dialog;
        this.wh = (SysUtils.getScreenWidth(mContext) - SysUtils.Dp2Px(mContext, 99)) / 3;

    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.patrol_find_item, null);
            holder.name01 = (TextView) convertView.findViewById(R.id.tx_name01);//
            holder.name02 = (TextView) convertView.findViewById(R.id.tx_name02);//
            holder.name03 = (TextView) convertView.findViewById(R.id.tx_name03);//
            holder.name04 = (TextView) convertView.findViewById(R.id.tx_name04);//
            holder.name05 = (TextView) convertView.findViewById(R.id.tx_name05);//
            holder.name06 = (TextView) convertView.findViewById(R.id.tx_name06);//

            holder.item01 = (TextView) convertView.findViewById(R.id.tx_item01);//
            holder.item02 = (TextView) convertView.findViewById(R.id.tx_item02);
            holder.item03 = (TextView) convertView.findViewById(R.id.tx_item03);
            holder.item04 = (RatingBar) convertView.findViewById(R.id.r_item04);
            holder.item05 = (TextView) convertView.findViewById(R.id.tx_item05);
            holder.item06 = (TextView) convertView.findViewById(R.id.tx_item06);
            holder.item07 = (TextView) convertView.findViewById(R.id.tx_item07);
            holder.rl4 = (RelativeLayout) convertView.findViewById(R.id.rl4);//图片布局
            holder.gridView = (MyGridView) convertView.findViewById(R.id.gv_images);
            holder.lay01 = (RelativeLayout) convertView.findViewById(R.id.lay01);//图片布局
            holder.bnt_thumb=(Button) convertView.findViewById(R.id.bnt_thumb);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FindInfo findInfo = data.get(position);
        String item01 = null, item02 = null, item03 = null, item05 = null, item06 = null, item07 = null, item08 = null;
        double item04 = 0;
        if (findInfo != null) {
            item01 = findInfo.getItem01();
            item02 = findInfo.getItem02();
            item03 = findInfo.getItem03();
            item04 = findInfo.getItem04();
            item05 = findInfo.getItem05();
            item06 = findInfo.getItem06();
            item07 = findInfo.getItem07();
            item08 = findInfo.getItem08();
        }


        if (type.equals("问题")) {
            holder.name01.setText("问题描述");
        } else {
            holder.name01.setText("亮点描述:");
            holder.name02.setText("亮点发现人:");
            holder.name03.setText("改善部门:");
            holder.name04.setText("星级:");
            holder.name05.setText("亮点改善人:");
            holder.name06.setText("亮点发现时间:");
        }

        holder.item01.setText(findInfo.getItem01());
        holder.item02.setText(findInfo.getItem02());
        holder.item03.setText(findInfo.getItem03());
        holder.item04.setRating((float) findInfo.getItem04());
        holder.item05.setText(findInfo.getItem05());
        holder.item06.setText(findInfo.getItem06());
        /*adapter=new ImageGridAdapter(mContext, data.get(position).getItem08(),imageLoader, mScreenWidth, mScreenHeight);
        holder.gridView.setAdapter(adapter);
        new ScrollListview(holder.gridView);*/
        if(findInfo.getItem10().equals("现场亮点")){
            holder.lay01.setVisibility(View.VISIBLE);
            holder.item07.setText(findInfo.getItem09()+"");
        }else{
            holder.lay01.setVisibility(View.INVISIBLE);
        }



        //是否含有图片
        Log.e("*****item08****",item08);
        if (item08 != null && !item08.equals("")) {
            holder.rl4.setVisibility(View.VISIBLE);
            initInfoImages(holder.gridView, item08);
        } else {
            holder.rl4.setVisibility(View.GONE);
        }
        holder.bnt_thumb.setOnClickListener(new FindAdapter.lvButtonListener(position));

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


    class ViewHolder {
        public TextView name01, name02, name03, name04, name05, name06, item01, item02, item03, item05, item06,item07;
        private RatingBar item04;
        private MyGridView gridView;
        private RelativeLayout rl4,lay01;
        private Button bnt_thumb;
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
    private void thumb(final List<FindInfo> data, final int position) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditHighlightThumb");
        params.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
        params.put("HighlightId",data.get(position).getItem07());

        final JSONObject mData=new JSONObject();
        try {
            mData.put("AppCode", "LinePatrol");
            mData.put("ApiCode", "EditHighlightThumb");
            mData.put("TenantId", SharedPreferencesTool.getMStool(mContext).getTenantId());
            mData.put("HighlightId",data.get(position).getItem07());

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
                        data.get(position).setItem09(data.get(position).getItem09()+1);
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
    public void initInfoImages(MyGridView gv_images, final String imgspath) {
        if (imgspath != null && !imgspath.equals("")) {
            String[] imgs = imgspath.split("#");
            final List<String> mlist = new ArrayList<String>();
            for (int i = 0; i < imgs.length; i++) {
                mlist.add(imgs[i]);
            }
            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w, RelativeLayout.LayoutParams.WRAP_CONTENT);
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






