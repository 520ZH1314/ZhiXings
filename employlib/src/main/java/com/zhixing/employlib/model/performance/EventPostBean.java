package com.zhixing.employlib.model.performance;

public class EventPostBean {

    public EventPostBean(String appCode, String apiCode, String tenantId, String teamId) {
        AppCode = appCode;
        ApiCode = apiCode;
        TenantId = tenantId;
        TeamId = teamId;
    }

    /**
     * AppCode : EMS
     * ApiCode : GetCheckItemByTeamId
     * TenantId : 00000000-0000-0000-0000-000000000001
     * TeamId : f8cab8dd-c606-458f-8294-588ed5c772b1
     */

    private String AppCode;
    private String ApiCode;
    private String TenantId;
    private String TeamId;

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

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String TeamId) {
        this.TeamId = TeamId;
    }
}
