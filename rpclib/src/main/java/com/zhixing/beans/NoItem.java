package com.zhixing.beans;

import java.io.Serializable;

public class NoItem implements Serializable {
    private String no;
    private int status;
    private  String name;
    private String ID;
    private String kehu;
    private String time;
    private String num;
    private String BatchNo;
    private String BatchId;
    private String BatchPlanCount;
    private String BatchCompletedCount;
    private String BatchCustomer;
    private String BatchCreateDate;

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getBatchId() {
        return BatchId;
    }

    public void setBatchId(String batchId) {
        BatchId = batchId;
    }

    public String getBatchPlanCount() {
        return BatchPlanCount;
    }

    public void setBatchPlanCount(String batchPlanCount) {
        BatchPlanCount = batchPlanCount;
    }

    public String getBatchCompletedCount() {
        return BatchCompletedCount;
    }

    public void setBatchCompletedCount(String batchCompletedCount) {
        BatchCompletedCount = batchCompletedCount;
    }

    public String getBatchCustomer() {
        return BatchCustomer;
    }

    public void setBatchCustomer(String batchCustomer) {
        BatchCustomer = batchCustomer;
    }

    public String getBatchCreateDate() {
        return BatchCreateDate;
    }

    public void setBatchCreateDate(String batchCreateDate) {
        BatchCreateDate = batchCreateDate;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getKehu() {
        return kehu;
    }

    public void setKehu(String kehu) {
        this.kehu = kehu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
