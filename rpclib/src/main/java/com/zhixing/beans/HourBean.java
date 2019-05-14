package com.zhixing.beans;

public class HourBean {
    private String ID ;
    private String TimeDisplay ;
    private String ProductName;
    private String UPH;
    private String Qty;
    private String QtyNG;
    private String DetailedNG;
    private String Remark;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTimeDisplay() {
        return TimeDisplay;
    }

    public void setTimeDisplay(String timeDisplay) {
        TimeDisplay = timeDisplay;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getUPH() {
        return UPH;
    }

    public void setUPH(String UPH) {
        this.UPH = UPH;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getQtyNG() {
        return QtyNG;
    }

    public void setQtyNG(String qtyNG) {
        QtyNG = qtyNG;
    }

    public String getDetailedNG() {
        return DetailedNG;
    }

    public void setDetailedNG(String detailedNG) {
        DetailedNG = detailedNG;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
