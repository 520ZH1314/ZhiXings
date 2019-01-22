package com.zhixing.masslib.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.zhixing.www.common.P;
import com.zhixing.masslib.bean.BaseExpandableBean;
import com.zhixing.masslib.inter.OnExpandableClickListener;

import java.util.LinkedList;
import java.util.List;


/**
* @author cloor
* @time   2018-12-26 10:46
* @describe  :  
*/

public abstract class BaseExpandableRecycleAdapter<A> extends BaseRecycleAdapter {

    /**
     * 对应的所有item保存到该类中，保存所有数据
     */
    private List<BaseExpandableBean> saveListBean;
    /**
     * 显示的数据，点击后增删都是在这个list中处理
     */
    private List<BaseExpandableBean> changeListBean;

    public BaseExpandableRecycleAdapter(Context context, List<A> list) {
        super(context, list);
        saveListBean = new LinkedList<>();
        //因为会在不同位置增删，用linkedlist增删快
        changeListBean = new LinkedList<>();
    }


    /**
     * 自己封装的写法，也简单，看看就懂了
     */
    public abstract class BaseExpandableViewHolder<T> extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        protected List<T> data;
        protected int position;
        protected BaseExpandableBean expandableBean;

        public void setData(List<T> data, int position) {

            this.position = position;
            this.data = data;
            //放置重用item时候显示数据出错
            expandableBean = changeListBean.get(position);
            //当为group的item的时候不需要调用该方法
            if (groupSelecltView != null) {
                groupSelecltView.setSelected(expandableBean.isStatus());
            }

            showGroupAndItemBean(data.get(expandableBean.getGroupPosition()), expandableBean,position);
        }
        public void updataT(List<T> dat){
            this.data = dat;
            notifyDataSetChanged();
        }

        /**
         * 集成该类后，只需要在这个方法里面执行数据操作既可以
         *
         * @param groupBean
         * @param expandableBean
         */
        public abstract void showGroupAndItemBean(T groupBean, BaseExpandableBean expandableBean,int position);


        public BaseExpandableViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 需要改变group的view，有可能是箭头符号
         */
        private View groupSelecltView;

        /**
         * 设置该view，这个是关键，不写将没有效果在构造方法调用
         *
         * @param groupSelecltView
         */
        protected void setGroupSelectView(View groupSelecltView) {
            this.groupSelecltView = groupSelecltView;
        }

        /**
         * 点击事件调用，这里是点击后对组别人员的增删的关键，实现点击位置的view位置触发，在点击事件onClick里面调用
         */
        protected void setGroupSelecltViewOnClick() {
            //改变需要动画view状态
            groupSelecltView.setSelected(!groupSelecltView.isSelected());
            //改变view的状态
            expandableBean.setStatus(groupSelecltView.isSelected());
            if (onExpandableClickListener != null) {
                //将位置展示给调用着
                onExpandableClickListener.onExpandableClick(
                        groupSelecltView,
                        expandableBean
                );
            }
            //这里是关键，增删的算法

            changeData(groupSelecltView.isSelected(), expandableBean.getGroupPosition());
        }


    }


    public List<BaseExpandableBean> getChangeListBean() {
        return changeListBean;
    }


    protected OnExpandableClickListener onExpandableClickListener;

    public void setOnExpandableClickListener(OnExpandableClickListener onExpandableClickListener) {
        this.onExpandableClickListener = onExpandableClickListener;
    }


    @Override
    public int getItemCount() {
        //这里是显示所有item的个数
        return changeListBean.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (changeListBean.get(position).getType() == BaseExpandableBean.GROUP) {
            return BaseExpandableBean.GROUP;
        }
        else {
            return BaseExpandableBean.FIRST_ITEM;
        }
    }

    public void changeData(boolean check, int groupPosition) {

        if (check) {
//            for (int i = 0; i < changeListBean.size(); i++) {
//                BaseExpandableBean score = changeListBean.get(i);
//                if (score.getGroupPosition() == groupPosition
//                        && score.getType() == BaseExpandableBean.FIRST_ITEM) {
//                    changeListBean.remove(i);
//                    i--;
//                    notifyItemRemoved(i);
//                }
//            }
//            this.notifyDataSetChanged();
            for (BaseExpandableBean score : saveListBean) {
                if (score.getGroupPosition() == groupPosition
                        && score.getType() == BaseExpandableBean.FIRST_ITEM) {
                    changeListBean.remove(score);
                }
            }
//            this.notifyDataSetChanged();
        }
        else {

            for (int i = 0; i < changeListBean.size(); i++) {
                BaseExpandableBean score = changeListBean.get(i);

                if (score.getGroupPosition() == groupPosition) {

                    for (BaseExpandableBean scoreTemp : saveListBean) {
                        if (scoreTemp.getGroupPosition() == groupPosition
                                && scoreTemp.getType() == BaseExpandableBean.FIRST_ITEM) {


                            i++;
                            changeListBean.add(i, scoreTemp);
//                            notifyItemInserted(i);

                        }

                    }
                    break;
                }

            }

        }

        this.notifyDataSetChanged();
    }


    private int groupPosition = 0;

    /**
     * 添加分组和各组item的显示
     *
     * @param itemSize
     */
    public void addGroupAndItem(int itemSize) {
        BaseExpandableBean group = new BaseExpandableBean();
        group.setType(BaseExpandableBean.GROUP);
        group.setGroupPosition(groupPosition);
        changeListBean.add(group);
        saveListBean.add(group);

        for (int j = 0; j < itemSize; j++) {
            BaseExpandableBean item = new BaseExpandableBean();
            item.setType(BaseExpandableBean.FIRST_ITEM);
            item.setGroupPosition(groupPosition);
            item.setItemNumber(j);
            changeListBean.add(item);
            saveListBean.add(item);
        }
        groupPosition++;
    }
    /**
    * @author cloor
    * @time   2018-12-29 11:00
    * @describe  :  只需要插入位置和数量   无需起始位置
     * 两种方式，第一种是将cha移除cot次    第二种是移除一个区间
    */
    public void changeDynamicRow(int cha,int cot){
     /*   for(BaseExpandableBean s :changeListBean){
            P.c("原始"+s.getGroupPosition()+"=="+s.getItemNumber()+"=="+s.getType());
        }*/
        int TAG = -1;//记录移除的是属于哪一个
        for (int j = cha; j < cha+cot; j++) {
            changeListBean.remove(cha);
            saveListBean.remove(cha);
            TAG =  changeListBean.get(cha).getGroupPosition();
        }
        int index   = 0;
        for(int i=0;i<saveListBean.size();i++){
            if(saveListBean.get(i).getGroupPosition()==TAG&&changeListBean.get(i).getType()==BaseExpandableBean.FIRST_ITEM){
                //进行重新排序
                saveListBean.get(i).setItemNumber(index);
                changeListBean.get(i).setItemNumber(index);
                index++;
            }
        }

        notifyDataSetChanged();
       /* for(BaseExpandableBean s :changeListBean){
            P.c("更新后"+s.getGroupPosition()+"=="+s.getItemNumber()+"=="+s.getType());
        }*/
    }

    /**
    * @author cloor
    * @time   2018-12-28 15:33
    * @describe   调整动态行  :cha插入位置  itemnumber位置
    */
    public void changeDynamicRow(int group,int cha,int start,int cot){

     /*  for(BaseExpandableBean s :changeListBean){
            P.c("原始"+s.getGroupPosition()+"=="+s.getItemNumber()+"=="+s.getType());
        }*/


    //    int fom = start+cot-1;
        for (int j = cha; j < cha+cot; j++) {
            //由于增加动态行只存在于子项,那么只需要操作子项即可
            BaseExpandableBean item = new BaseExpandableBean();
            item.setType(BaseExpandableBean.FIRST_ITEM);
            item.setGroupPosition(group);
            item.setItemNumber(start++);
            changeListBean.add(j,item);
            saveListBean.add(j,item);

        }
     /*  for(BaseExpandableBean s :changeListBean){
            P.c("更新后"+s.getGroupPosition()+"=="+s.getItemNumber()+"=="+s.getType());
        }*/
            notifyDataSetChanged();
    //  notifyItemRangeChanged(start,cot);
     //updataB(changeListBean);
//        notifyItemRangeInserted(start,cot);

    }

    public void removeAll() {
        changeListBean.clear();
        saveListBean.clear();
        groupPosition = 0;
    }



}
