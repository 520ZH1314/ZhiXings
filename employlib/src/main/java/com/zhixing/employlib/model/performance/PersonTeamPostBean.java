package com.zhixing.employlib.model.performance;

public class PersonTeamPostBean {
    public PersonTeamPostBean(String appCode, String apiCode, String tenantId, String userId) {
        AppCode = appCode;
        ApiCode = apiCode;
        TenantId = tenantId;
        UserId = userId;
    }

    /**
     * AppCode : EMS
     * ApiCode : GetTeamIDAndLeader
     * TenantId : 00000000-0000-0000-0000-000000000001
     * UserId : 7565d0f8-8025-11e8-b8e8-507b9d9a63b9
     */

    private String AppCode;
    private String ApiCode;
    private String TenantId;
    private String UserId;

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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }
}
