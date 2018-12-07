package com.shuben.zhixing.www.patrol.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.MyQuseInfo;
import com.shuben.zhixing.www.patrol.management.MyQuestionDetailActivity;
import com.shuben.zhixing.www.patrol.management.SolveActivity;
import com.base.zhixing.www.util.UrlUtil;

import java.util.List;

/**
 * Created by Geyan on 2018/6/4.
 */

public class AuditAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<MyQuseInfo> data;
    private MyQuseInfo myQuseInfo;

    public AuditAdapter(Activity mContext, List<MyQuseInfo> data) {
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
            convertView = inflater.inflate(R.layout.patrol_qusestion_item, null);
            holder.bnt_start=(Button) convertView.findViewById(R.id.bnt_commit);
            holder.item01=(TextView) convertView.findViewById(R.id.tx_item01);//
            holder.item02=(TextView) convertView.findViewById(R.id.tx_item02);
            holder.item03=(TextView) convertView.findViewById(R.id.tx_item03);
            holder.item04=(TextView) convertView.findViewById(R.id.tx_item04);
            holder.item05=(TextView) convertView.findViewById(R.id.tx_item05);
            holder.item06=(TextView) convertView.findViewById(R.id.tx_item06);
            holder.ratingBar=(RatingBar) convertView.findViewById(R.id.tx_item07);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag

            convertView.setTag(holder);

        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        myQuseInfo=data.get(position);
        holder.item01.setText(myQuseInfo.getItem01());
        if(myQuseInfo.getItem12().equals("0")){
            holder.item02.setText("未处理");
        }else  if(myQuseInfo.getItem12().equals("5")){
            holder.item02.setText("待审核");
        }else  if(myQuseInfo.getItem12().equals("10")){
            holder.item02.setText("已通过");
        }

        holder.item03.setText(myQuseInfo.getItem07());
        holder.item04.setText(myQuseInfo.getItem08());
        holder.item05.setText(myQuseInfo.getItem09());
        holder.item06.setText(myQuseInfo.getItem10());
        holder.bnt_start.setFocusable(false);
        holder.bnt_start.setOnClickListener(new lvButtonListener(position));
        holder.ratingBar.setRating(myQuseInfo.getItem05());
        if(myQuseInfo.getItem12().equals("5")){
            holder.bnt_start.setText("进行审核");
        }else{
            holder.bnt_start.setText("进入查看");
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
        public TextView item01, item02, item03, item04, item05, item06;
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
                Intent intent=new Intent();
                if(data.get(position).getItem12().equals("5")){
                    intent.setClass(mContext, SolveActivity.class);
                    intent.putExtra("ProblemId",data.get(position).getItem02());
                    mContext.startActivityForResult(intent, UrlUtil.MyQuestionActivity_RequstCode01);
                }else {
                    intent.setClass(mContext, MyQuestionDetailActivity.class);
                    intent.putExtra("ProblemId",data.get(position).getItem02());
                    mContext.startActivityForResult(intent, UrlUtil.MyQuestionActivity_RequstCode02);
                }



            }
        }
    }

}


