package com.zhixing.employlib.model.grading;

public class GradListDetailPostBean {

    public GradListDetailPostBean(String appCode, String apiCode, String tenantId, String startDate, String endDate, String userCode) {
        AppCode = appCode;
        ApiCode = apiCode;
        TenantId = tenantId;
        StartDate = startDate;
        EndDate = endDate;
        UserCode = userCode;
    }

    /**
     * AppCode : EMS
     * ApiCode : GetUserEventInfo
     * TenantId : 00000000-0000-0000-0000-000000000001
     * StartDate : 2019-03-27
     * EndDate : 2019-03-27
     * UserCode : 06153
     */

    private String AppCode;
    private String ApiCode;
    private String TenantId;
    private String StartDate;
    private String EndDate;
    private String UserCode;

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

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }
}
