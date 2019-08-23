package com.shuben.zhixing.www.inspection.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.shuben.zhixing.module.andon.CommonViewsPop;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.bean.ProblemDetailInfo;
import com.shuben.zhixing.www.patrol.adapter.TestGridViewAdapter;
import com.shuben.zhixing.www.util.SysUtils;
import com.shuben.zhixing.www.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geyan on 2018/7/28.
 */

public class ProblemDetailAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<ProblemDetailInfo> data;
    private ProblemDetailInfo recordInfo;
    private int wh;
    private TestGridViewAdapter nearByInfoImgsAdapter;

    public ProblemDetailAdapter(Activity mContext, List<ProblemDetailInfo> data) {
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
            convertView = inflater.inflate(R.layout.inspection_problem_detail, null);
            holder.tx_pitem=(TextView) convertView.findViewById(R.id.tx_pitem);//
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
            item09 = recordInfo.getFilePath01();
            item10 = recordInfo.getFilePath02();
        }

        holder.tx_pitem.setText(recordInfo.getProblemNo());
        holder.item01.setText(recordInfo.getProductName());

        if(recordInfo.getStatus().equals("0")){
            holder.status.setText("未处理");
        }else if(recordInfo.getStatus().equals("1")){
            holder.status.setText("已处理");
        }

        holder.item02.setText(recordInfo.getPatrolUserName());
        holder.item03.setText(recordInfo.getItemType());
        holder.item04.setText(recordInfo.getLiableDeptName());
        holder.item05.setText(recordInfo.getLiableUserName());
        holder.item06.setText(recordInfo.getDueDate().substring(5,recordInfo.getDueDate().length()));
        if(recordInfo.getCompleteDate().equals("")||recordInfo.getCompleteDate().equals("null")||recordInfo.getCompleteDate()==null){
            holder.item07.setVisibility(View.INVISIBLE);
        }else {
//            holder.item07.setText(TimeUtil.getTimeCh(TimeUtil.parseTimeC(recordInfo.getCompleteDate())));
            holder.item07.setText(recordInfo.getCompleteDate());
        }

        holder.item08.setText(recordInfo.getProblemDesc());
        //是否含有图片
        if (item09 != null && !item09.equals("")) {
            holder.lay01.setVisibility(View.VISIBLE);
            initInfoImages01(holder.gridView01, item09);
        } else {
            holder.lay01.setVisibility(View.GONE);
        }

        if(!recordInfo.getMeasures().equals("null")&&recordInfo.getMeasures()!=null){
            holder.lay02.setVisibility(View.VISIBLE);
            holder.item10.setText(recordInfo.getMeasures());
            if (item10 != null && !item10.equals("")) {
                initInfoImages02(holder.gridView02, item10);
            }

        }else{
            holder.lay02.setVisibility(View.GONE);
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
        public TextView tx_pitem,item01, status,item02, item03, item04, item05, item06,item07,item08, item10,item11,item12,item13;
        private MyGridView gridView01,gridView02;
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
        }
    }

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
                  //  PhotoPreview.init().setPhotoPaths(mlist).setCurrentPos(position).setPreviewOnly(true).startPreview(mContext, null);
                   //Toasty.INSTANCE.showToast(mContext,"待接入");

                    CommonViewsPop viewsPop = new CommonViewsPop(mContext,null,mlist);
                    viewsPop.showSheet(position);

                }

            });

          /*  int w = 0;
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
            gv_images.setLayoutParams(lp);*/
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
//                    PhotoPreview.init().setPhotoPaths(mlist).setCurrentPos(position).setPreviewOnly(true).startPreview(mContext, null);
                    CommonViewsPop viewsPop = new CommonViewsPop(mContext,null,mlist);
                    viewsPop.showSheet(position);
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



