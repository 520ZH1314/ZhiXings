package com.zhixing.tpmlib.bean;

public class MaintenanceRecordPostBean {


    /**
     * TenantId : 00000000-0000-0000-0000-000000000001
     * CreateDate : 2018-5-1
     * EndDate : 2019-1-15
     * EquipmentId : AA3F27BE-7976-40B1-B01B-0EE8A8C26041
     */
    private String AppCode;
    private String ApiCode;

    private String TenantId;
    private String CreateDate;
    private String EndDate;
    private String EquipmentId;

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


    public MaintenanceRecordPostBean(String tenantId, String createDate, String endDate, String equipmentId,String AppCode,String ApiCode)
    {
        this.TenantId = tenantId;
        this. CreateDate = createDate;
        this.EndDate = endDate;
        this.EquipmentId = equipmentId;
        this.AppCode=AppCode;
        this.ApiCode=ApiCode;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public String getEquipmentId() {
        return EquipmentId;
    }

    public void setEquipmentId(String EquipmentId) {
        this.EquipmentId = EquipmentId;
    }
}
