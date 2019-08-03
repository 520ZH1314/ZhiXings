package com.zhixing.tpmlib.bean;

public class CheckltemPostBean {


    /**
     * AppCode : Andon
     * ApiCode : GetStationException
     * TenantId : 43ec274c-7004-4414-a06d-c1dfbda0c488
     * LineStationId : d57cd732-daac-4804-94e5-8b421e4f84fa
     * IsEquipmentException : 1
     */

    private String AppCode;
    private String ApiCode;
    private String TenantId;
    private String LineStationId;
    private String IsEquipmentException;

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String AppCode) {
        this.AppCode = AppCode;
    }

    public String getApiCode() {
        return ApiCode;
    }

    public void setApiCode(String ApiCode) {
        this.ApiCode = ApiCode;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    public String getLineStationId() {
        return LineStationId;
    }

    public void setLineStationId(String LineStationId) {
        this.LineStationId = LineStationId;
    }

    public String getIsEquipmentException() {
        return IsEquipmentException;
    }

    public void setIsEquipmentException(String IsEquipmentException) {
        this.IsEquipmentException = IsEquipmentException;
    }
}
