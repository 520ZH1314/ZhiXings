package com.zhixing.employlib.model.grading;

public class GradingListPostBean {


    public GradingListPostBean(String appCode, String apiCode, String tenantId, String startDate, String endDate, String teamId) {
        AppCode = appCode;
        ApiCode = apiCode;
        TenantId = tenantId;
        StartDate = startDate;
        EndDate = endDate;
        TeamId = teamId;
    }

    /**
     * AppCode : EMS
     * ApiCode : GetWorkTeamUserEventInfo
     * TenantId : 00000000-0000-0000-0000-000000000001
     * StartDate : 2019-03-27
     * EndDate : 2019-03-27

     * TeamId : c08aeff2-9754-4e54-ad37-655a33390492
     */

    private String AppCode;
    private String ApiCode;
    private String TenantId;
    private String StartDate;
    private String EndDate;
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

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String TeamId) {
        this.TeamId = TeamId;
    }
}
