package com.zhixing.employlib.model.performance;

import java.io.Serializable;

public class YesterdayPerformancePostBean  implements Serializable {


    public YesterdayPerformancePostBean(String appCode, String apiCode, String userCode, String shiftDate, String tenantId, String teamId) {
        AppCode = appCode;
        ApiCode = apiCode;
        UserCode = userCode;
        ShiftDate = shiftDate;
        TenantId = tenantId;
        TeamId = teamId;
    }

    /**
     * AppCode : EMS
     * ApiCode : GetUserInfoOfDay
     * UserCode : 06153
     * ShiftDate : 2019-02-15
     * TenantId : 00000000-0000-0000-0000-000000000001
     * TeamId : c08aeff2-9754-4e54-ad37-655a33390492
     */

    private String AppCode;
    private String ApiCode;
    private String UserCode;
    private String ShiftDate;
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

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String TeamId) {
        this.TeamId = TeamId;
    }
}
