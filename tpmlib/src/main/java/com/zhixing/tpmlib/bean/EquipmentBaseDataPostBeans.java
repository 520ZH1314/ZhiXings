package com.zhixing.tpmlib.bean;

public class EquipmentBaseDataPostBeans {


    public EquipmentBaseDataPostBeans(String appCode, String apiCode, String tenantId, String lineCode) {
        AppCode = appCode;
        ApiCode = apiCode;
        TenantId = tenantId;
        LineCode = lineCode;
    }

    /**

     * AppCode : EPS
     * ApiCode : GetEquipmentList
     * TenantId : 00000000-0000-0000-0000-000000000001
     * LineCode : SJ01A01
     */

    private String AppCode;
    private String ApiCode;
    private String TenantId;
    private String LineCode;

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

    public String getLineCode() {
        return LineCode;
    }

    public void setLineCode(String LineCode) {
        this.LineCode = LineCode;
    }
}
