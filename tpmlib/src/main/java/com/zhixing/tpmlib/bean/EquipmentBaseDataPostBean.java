package com.zhixing.tpmlib.bean;

public class EquipmentBaseDataPostBean {


    public EquipmentBaseDataPostBean(String appCode, String apiCode, String tenantId, String stationCode) {
        AppCode = appCode;
        ApiCode = apiCode;
        TenantId = tenantId;
        StationCode = stationCode;
    }

    public String AppCode;

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

    public String getStationCode() {
        return StationCode;
    }

    public void setStationCode(String stationCode) {
        StationCode = stationCode;
    }

    public String ApiCode;
    public String TenantId;
    public String StationCode;
}
