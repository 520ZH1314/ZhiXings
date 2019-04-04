package com.zhixing.employlib.model.performance;

public class PersonDayEventPostBean {

    public PersonDayEventPostBean(String appCode, String apiCode, String userCode, String shiftDate, String tenantId) {
        AppCode = appCode;
        ApiCode = apiCode;
        UserCode = userCode;
        ShiftDate = shiftDate;
        TenantId = tenantId;
    }

    /**
     * AppCode : EMS
     * ApiCode : GetDayKeyEvent
     * UserCode : 06153
     * ShiftDate : 2019-02-15
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
    private String UserCode;
    private String ShiftDate;
    private String TenantId;

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

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getShiftDate() {
        return ShiftDate;
    }

    public void setShiftDate(String ShiftDate) {
        this.ShiftDate = ShiftDate;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }
}
