package com.zhixing.masslib.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public   class GroupBean {
    private int step;
     //备份对象,动态行使用
         private String copyBean;
        //动态行数据，叠加
        List<sJitemBean> itemBeanList;
        //组名
        private String groupName;
        //是否是动态行
        private boolean IsDynamicRow;
    //是否显示记录框
        private boolean IsCheckRecord;
    //是否ok  ng
        private boolean IsDetermine;
    //记录动态行的可显位置,关于子项
        private Map<String,Integer>showGs;

        public List<sJitemBean> getItemBeanList() {
            return itemBeanList;
        }
        //存放动态行的
        private LinkedHashMap<Integer,Integer> itemV = new LinkedHashMap();
        private int rowLi ;


    public int getStep() {
        return step;
    }

    public LinkedHashMap<Integer, Integer> getItemV() {
        return itemV;
    }



    public void setStep(int step) {
        this.step = step;
    }

    public String getCopyBean() {
        return copyBean;
    }

    public void setCopyBean(String copyBean) {
        this.copyBean = copyBean;
    }

    /**
     * 获得该动态行的行数
     * @return
     */
    public int getRowLi() {
        return rowLi;
    }

    public void setRowLi(int rowLi) {
        this.rowLi = rowLi;
    }

//    private List<List<sJitemBean>> RowLists;
        public void setItemBeanList(List<sJitemBean> itemBeanList) {
            this.itemBeanList = itemBeanList;
        }


    public Map<String, Integer> getShowGs() {
        return showGs;
    }

    public void setShowGs(Map<String, Integer> showGs) {
        this.showGs = showGs;
    }



    public boolean isDynamicRow() {
        return IsDynamicRow;
    }

    public void setDynamicRow(boolean dynamicRow) {
        IsDynamicRow = dynamicRow;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isCheckRecord() {
        return IsCheckRecord;
    }

    public void setCheckRecord(boolean checkRecord) {
        IsCheckRecord = checkRecord;
    }

    public boolean isDetermine() {
        return IsDetermine;
    }

    public void setDetermine(boolean determine) {
        IsDetermine = determine;
    }
}
