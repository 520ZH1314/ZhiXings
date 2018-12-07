package com.shuben.zhixing.www.data;

import java.io.Serializable;

/**
 * Created by Geyan on 2017/9/6.
 */

public class ReplyInfo implements Serializable{
    private String billNo = "";//任务编号
    private String handleDate = "";//处理时间
    private String Type = "";//类型
    private String Memo = "";//说明
    private String deliverQty = "";//数量
    int currentStep = 0;//阶段
    int myStep = 0;//阶段

    public ReplyInfo(String billNo, String handleDate, String type, String memo, String deliverQty, int currentStep, int myStep) {
        this.billNo = billNo;
        this.handleDate = handleDate;
        Type = type;
        Memo = memo;
        this.deliverQty = deliverQty;
        this.currentStep = currentStep;
        this.myStep = myStep;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getDeliverQty() {
        return deliverQty;
    }

    public void setDeliverQty(String deliverQty) {
        this.deliverQty = deliverQty;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public int getMyStep() {
        return myStep;
    }

    public void setMyStep(int myStep) {
        this.myStep = myStep;
    }
}
