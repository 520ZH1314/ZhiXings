package com.zhixing.employlib.model.resume;

public class GetResumePostBean {


    /**
     * AppCode : EMS
     * ApiCode : GetResumeList
     * UserId : 123456005
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String AppCode;
    private String ApiCode;
    private String UserId;
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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }
}
