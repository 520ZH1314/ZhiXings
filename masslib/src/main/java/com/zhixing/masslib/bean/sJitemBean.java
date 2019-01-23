package com.zhixing.masslib.bean;

public class sJitemBean {
    int item;

    public int getItem() {
        return item;
    }
    private String id;
    public void setItem(int item) {
        this.item = item;
    }
    private boolean NeedRecord;//输入框
    private boolean  NeedCheck;//ok\ng
    private int ItemType;
    private String name;
    private String value;
    private String editValue;//备注记录
//    private String recordValue;
    private int rbc = -1;//单选位置
    private boolean[] che;//多选位置组
    private int ok_ng = -1;
    private String delTag;
    private int itemIndex;
    private int autoIndex;//动态行的真实
    private String workdate;
    private String WorkNo;

    public int getItemIndex() {
        return itemIndex;
    }

    public int getAutoIndex() {
        return autoIndex;
    }

    public void setAutoIndex(int autoIndex) {
        this.autoIndex = autoIndex;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public String getWorkdate() {
        return workdate;
    }

    public void setWorkdate(String workdate) {
        this.workdate = workdate;
    }

    public String getWorkNo() {
        return WorkNo;
    }

    public void setWorkNo(String workNo) {
        WorkNo = workNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDelTag() {
        return delTag;
    }

    public void setDelTag(String delTag) {
        this.delTag = delTag;
    }

    public int getOk_ng() {
        return ok_ng;
    }

    public void setOk_ng(int ok_ng) {
        this.ok_ng = ok_ng;
    }

    public String getEditValue() {
        return editValue;
    }

    public void setEditValue(String editValue) {
        this.editValue = editValue;
    }

    public int getRbc() {
        return rbc;
    }

    public void setRbc(int rbc) {
        this.rbc = rbc;
    }

    public boolean[] getChe() {
        return che;
    }

    public void setChe(boolean[] che) {
        this.che = che;
    }

    /*public String getRecordValue() {
        return recordValue;
    }

    public void setRecordValue(String recordValue) {
        this.recordValue = recordValue;
    }*/



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isNeedRecord() {
        return NeedRecord;
    }

    public void setNeedRecord(boolean needRecord) {
        NeedRecord = needRecord;
    }

    public boolean isNeedCheck() {
        return NeedCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        NeedCheck = needCheck;
    }

    public int getItemType() {
        return ItemType;
    }

    public void setItemType(int itemType) {
        ItemType = itemType;
    }
}