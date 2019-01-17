package com.zhixing.tpmlib.bean;

public class MaintenanceListDataPostBean {


    public MaintenanceListDataPostBean(String appCode, String apiCode, String tenantId, String lineCode, String stationCode, String userCode,String page,String rows) {
        AppCode = appCode;
        ApiCode = apiCode;
        TenantId = tenantId;
        LineCode = lineCode;
        StationCode = stationCode;
        UserCode = userCode;
        page=page;
        rows=rows;
    }

    /**
     * AppCode : TPM
     * ApiCode : GetPlanProtect
     * TenantId :
     * LineCode :

     * StationCode :
     * UserCode :
     */

    private String AppCode;
    private String ApiCode;
    private String TenantId;
    private String LineCode;
    private String StationCode;
    private String UserCode;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    private String page;//当前页
    private String rows;//每页显示条数

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

    public String getStationCode() {
        return StationCode;
    }

    public void setStationCode(String StationCode) {
        this.StationCode = StationCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }
}
