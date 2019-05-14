package com.zhixing.beans;

public class PzBean {

    /**
     * ProductCode : HS-ST0319-1APH
     * ProductName : HS-ST0319-1APH
     * LineName : 塑胶生产1号机
     * OrderNO : WORK018334
     * SumQty : 8020
     * SumNG : 15
     * NGRatio : 0.0019
     */

    private String ProductCode;
    private String ProductName;
    private String LineName;
    private String OrderNO;
    private int SumQty;
    private int SumNG;
    private double NGRatio;

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String ProductCode) {
        this.ProductCode = ProductCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String LineName) {
        this.LineName = LineName;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String OrderNO) {
        this.OrderNO = OrderNO;
    }

    public int getSumQty() {
        return SumQty;
    }

    public void setSumQty(int SumQty) {
        this.SumQty = SumQty;
    }

    public int getSumNG() {
        return SumNG;
    }

    public void setSumNG(int SumNG) {
        this.SumNG = SumNG;
    }

    public double getNGRatio() {
        return NGRatio;
    }

    public void setNGRatio(double NGRatio) {
        this.NGRatio = NGRatio;
    }
}
