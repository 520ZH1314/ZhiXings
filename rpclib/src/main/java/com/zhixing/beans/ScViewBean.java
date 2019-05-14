package com.zhixing.beans;

import java.io.Serializable;

public class ScViewBean implements Serializable {
    private  String OrderNo;
    private  int State;
    private  String Utilization;
    private  String RatioQty;
    private  String StopCount;
    private  String StopTime;
    private  String RealStartDate;
    private  String ProduceTime;
    private  String Qty;
    private  String PlanQty;
    private  String PlanRatio;
    private  String DiffQty;
    private  String Ratio;
    private  String CT;
    private  String PlanCT;
    private  String CurUPH;
    private  String UPH ;
    private String ProductCode;

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getUtilization() {
        return Utilization;
    }

    public void setUtilization(String utilization) {
        Utilization = utilization;
    }

    public String getRatioQty() {
        return RatioQty;
    }

    public void setRatioQty(String ratioQty) {
        RatioQty = ratioQty;
    }

    public String getStopCount() {
        return StopCount;
    }

    public void setStopCount(String stopCount) {
        StopCount = stopCount;
    }

    public String getStopTime() {
        return StopTime;
    }

    public void setStopTime(String stopTime) {
        StopTime = stopTime;
    }

    public String getRealStartDate() {
        return RealStartDate;
    }

    public void setRealStartDate(String realStartDate) {
        RealStartDate = realStartDate;
    }

    public String getProduceTime() {
        return ProduceTime;
    }

    public void setProduceTime(String produceTime) {
        ProduceTime = produceTime;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getPlanQty() {
        return PlanQty;
    }

    public void setPlanQty(String planQty) {
        PlanQty = planQty;
    }

    public String getPlanRatio() {
        return PlanRatio;
    }

    public void setPlanRatio(String planRatio) {
        PlanRatio = planRatio;
    }

    public String getDiffQty() {
        return DiffQty;
    }

    public void setDiffQty(String diffQty) {
        DiffQty = diffQty;
    }

    public String getRatio() {
        return Ratio;
    }

    public void setRatio(String ratio) {
        Ratio = ratio;
    }

    public String getCT() {
        return CT;
    }

    public void setCT(String CT) {
        this.CT = CT;
    }

    public String getPlanCT() {
        return PlanCT;
    }

    public void setPlanCT(String planCT) {
        PlanCT = planCT;
    }

    public String getCurUPH() {
        return CurUPH;
    }

    public void setCurUPH(String curUPH) {
        CurUPH = curUPH;
    }

    public String getUPH() {
        return UPH;
    }

    public void setUPH(String UPH) {
        this.UPH = UPH;
    }
}
