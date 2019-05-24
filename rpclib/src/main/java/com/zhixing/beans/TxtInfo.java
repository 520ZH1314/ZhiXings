package com.zhixing.beans;

import java.io.Serializable;

public class TxtInfo implements Serializable {
    private int State;
    private String ProductCode;
    private String ProductName;
    private String LineName;
    private int PlanQty;
    private String PlanStartDate;
    private String RealStartDate;
    private String PlanEndDate;
    private String RealEndDate;
    private String RecoverDate;
    private String  OrderNo;
    private String BatchCustomer;
    private String RealStopDate;
    private String CreateDate;
    private int Qty;
    private int QtyNG;
    private int UPH;
    private int Ratio;
    private String ID;
    private String BatchNo;
    private String BatchWorkNo;

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getBatchWorkNo() {
        return BatchWorkNo;
    }

    public void setBatchWorkNo(String batchWorkNo) {
        BatchWorkNo = batchWorkNo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBatchCustomer() {
        return BatchCustomer;
    }

    public void setBatchCustomer(String batchCustomer) {
        BatchCustomer = batchCustomer;
    }

    public int getUPH() {
        return UPH;
    }

    public void setUPH(int UPH) {
        this.UPH = UPH;
    }

    public int getRatio() {
        return Ratio;
    }

    public void setRatio(int ratio) {
        Ratio = ratio;
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

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String lineName) {
        LineName = lineName;
    }

    public int getPlanQty() {
        return PlanQty;
    }

    public void setPlanQty(int planQty) {
        PlanQty = planQty;
    }

    public String getPlanStartDate() {
        return PlanStartDate;
    }

    public void setPlanStartDate(String planStartDate) {

        PlanStartDate = planStartDate;
    }

    public String getRealStartDate() {
        return RealStartDate;
    }

    public void setRealStartDate(String realStartDate) {
        RealStartDate = realStartDate;
    }

    public String getPlanEndDate() {
        return PlanEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        PlanEndDate = planEndDate;
    }

    public String getRealEndDate() {
        return RealEndDate;
    }

    public void setRealEndDate(String realEndDate) {
        RealEndDate = realEndDate;
    }

    public String getRecoverDate() {
        return RecoverDate;
    }

    public void setRecoverDate(String recoverDate) {
        RecoverDate = recoverDate;
    }

    public String getRealStopDate() {
        return RealStopDate;
    }

    public void setRealStopDate(String realStopDate) {
        RealStopDate = realStopDate;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getQtyNG() {
        return QtyNG;
    }

    public void setQtyNG(int qtyNG) {
        QtyNG = qtyNG;
    }
}
