package com.zhixing.tpmlib.bean;

public class LineStationPostBean {

    public String AppCode;
    public String ApiCode;
    public String TenantId;
    public String StationCode;
    public String getStationCode() {
        return StationCode;
    }

    public void setStationCode(String stationCode) {
        StationCode = stationCode;
    }


    public String getLineCode() {
        return LineCode;
    }

    public void setLineCode(String lineCode) {
        LineCode = lineCode;
    }

    public String LineCode;
    public LineStationPostBean(String appCode, String apiCode, String tenantId,String LineCode) {
        this.AppCode = appCode;
        this. ApiCode = apiCode;
        this.TenantId = tenantId;
        this. LineCode =LineCode;

    }

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String appCode) {
        AppCode = appCode;
    }

    public String getApiCode() {
        return ApiCode;
    }

    public void setApiCode(String apiCode) {
        ApiCode = apiCode;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String tenantId) {
        TenantId = tenantId;
    }
}
