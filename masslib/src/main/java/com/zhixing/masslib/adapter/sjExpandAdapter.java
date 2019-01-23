package com.zhixing.masslib.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.base.zhixing.www.common.P;
import com.base.zhixing.www.widget.XEditText;
import com.zhixing.masslib.R;
import com.zhixing.masslib.bean.GroupBean;
import com.zhixing.masslib.bean.sJitemBean;
import com.zhixing.masslib.widget.AutoLinefeedLayout;
import com.zhixing.masslib.widget.MyTextView;
import com.zhixing.masslib.widget.MyXeditView;
import com.zhixing.masslib.widget.RadioGroupEx;
import com.zhixing.masslib.widget.SwitchView;

import java.util.ArrayList;
import java.util.List;

public class sjExpandAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    private List<GroupBean> groupBeans;
    private Activity activity;
    public sjExpandAdapter(Activity activity,List<GroupBean> groupBeans){
        this.activity = activity;
        this.groupBeans = groupBeans;
        inflater = LayoutInflater.from(activity);
    }
    public void updata(   List<GroupBean> groupBeans){
        this.groupBeans = groupBeans;
        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount() {
        return groupBeans.size();
    }

    @Override
    public int getChildrenCount(int i) {

        List<sJitemBean> list = groupBeans.get(i).getItemBeanList();
        return list!=null?list.size():0;
    }

    @Override
    public Object getGroup(int i) {
        return groupBeans.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupBeans.get(groupPosition).getItemBeanList().get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    private class ParentHolder{
        TextView tv_group;
    }
    @Override
    public View getGroupView(int position, boolean b, View convertView, ViewGroup viewGroup) {

        ParentHolder viewHolder;
        if (convertView == null
                || convertView.getTag(R.mipmap.ic_launcher + position) == null) {
            viewHolder = new ParentHolder();
            convertView = inflater.inflate(R.layout.adatper_group, null);
            viewHolder.tv_group = convertView.findViewById(R.id.tv_group);

            convertView.setTag(R.mipmap.ic_launcher + position);
        } else {
            viewHolder = (ParentHolder) convertView.getTag(R.mipmap.ic_launcher
                    + position);
        }
        viewHolder.tv_group.setText(groupBeans.get(position).getGroupName());

        return convertView;
    }
    private class ChildHolder{
        private LinearLayout ll_title;
        private SwitchView view3;
        private TextView view0;
        private AutoLinefeedLayout view1;
        private XEditText view2;
    }
    @Override
    public View getChildView(int parent, int child, boolean b0, View convertView, ViewGroup viewGroup) {
        ChildHolder viewHolder;
        if (convertView == null
                || convertView.getTag(R.mipmap.ic_launcher + child) == null) {
            viewHolder = new ChildHolder();
            convertView = inflater.inflate(R.layout.adatper_group_item, null);
            viewHolder. ll_title =  convertView.findViewById(R.id.ll_title);
            viewHolder. view1 = convertView.findViewById(R.id.view1);
            viewHolder.view3 = convertView.findViewById(R.id.view3);
            viewHolder. view0 = convertView.findViewById(R.id.view0);
            viewHolder. view2  = convertView.findViewById(R.id.view2);

            convertView.setTag(R.mipmap.ic_launcher + child);
        } else {
            viewHolder = (ChildHolder) convertView.getTag(R.mipmap.ic_launcher
                    + child);
        }


        GroupBean groupBean = groupBeans.get(parent);
        sJitemBean b = groupBean.getItemBeanList().get(child);

        P.c("测试==============="+parent+"=="+child);

        if(groupBean.isDynamicRow()){
            //动态行
            if( groupBean.isCheckRecord()){
                viewHolder.view2.setVisibility(View.VISIBLE);

            }else{
                viewHolder.view2.setVisibility(View.GONE);
            }
            if(groupBean.isDetermine()){
                viewHolder.view3.setVisibility(View.VISIBLE);
            }else{
                viewHolder. view3.setVisibility(View.GONE);
            }


        }else{
            //非动态行
           viewHolder. view0.setText(b.getName());

            if(b.isNeedRecord()){
                viewHolder.view2.setVisibility(View.VISIBLE);
            }else{
                viewHolder. view2.setVisibility(View.GONE);
            }
            if(b.isNeedCheck()){
                viewHolder.view3.setVisibility(View.VISIBLE);
            }else{
                viewHolder.view3.setVisibility(View.GONE);
            }

            switch (b.getItemType()){
                case 0:
                    //只读
                    MyTextView t = getView0();
                    viewHolder.view1.addView(t);
                    t.setText(b.getValue());
                    break;
                case 1:
                    //文本
                    XEditText x = getView1();
                    viewHolder.view1.addView(x);
                    x.setTextEx(b.getValue());
                    break;
                case 2:
                    //单选
                    RadioGroup ex = new RadioGroup(activity);
                    ex.setOrientation(RadioGroupEx.VERTICAL);
                    String temp2[] = b.getValue().split(",");

                    for(int i=0;i<temp2.length;i++){
                        RadioButton rb = new RadioButton(activity);
                        rb.setText(temp2[i]);
                        ex.addView(rb);
                    }
                    viewHolder.view1.addView(ex);

                    break;
                case 3:
                    //多选
                    String temp3[] = b.getValue().split(",");
                    for(int i=0;i<temp3.length;i++){
                        CheckBox checkBox = new CheckBox(activity);
                        checkBox.setText(temp3[i]);
                        viewHolder. view1.addView(checkBox);
                    }

                    break;
                case 4:
                    //系统

                    break;
            }

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private MyTextView getView0(){
        MyTextView t = (MyTextView) inflater.inflate(R.layout.wd_txt,null);
        t.setTextColor(activity.getResources().getColor(R.color.txt_nor));
        t.setTextSize(15);
        return t;
    }
    private MyXeditView getView1(){
        MyXeditView t = (MyXeditView) inflater.inflate(R.layout.wd_edit,null);
        t.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        t.setHint("请输入");
        return t;
    }
}
