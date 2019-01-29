package com.zhixing.tpmlib.bean;

public class MaintenanceItemCheckPostBean {


    public MaintenanceItemCheckPostBean(String apiCode, String appCode, String tenantId, String classId, String gradeId, String planId, String equipmentId) {
        ApiCode = apiCode;
        AppCode = appCode;
        TenantId = tenantId;
        ClassId = classId;
        GradeId = gradeId;
        PlanId = planId;
        EquipmentId = equipmentId;
    }

    /**
     * ApiCode : GetFirstMaintanceInfo
     * AppCode : TPM
     * TenantId : 00000000-0000-0000-0000-000000000001
     * ClassId : DC100F07-62F5-4C2A-891F-2B7E3562850D
     * GradeId : 9a405a29-4906-455f-9f6e-e0643b311c8e
     * PlanId : bd21ab60-1b1b-4b3c-a480-24ba35254125
     * EquipmentId : AA3F27BE-7976-40B1-B01B-0EE8A8C26041
     */

    private String ApiCode;
    private String AppCode;
    private String TenantId;
    private String ClassId;
    private String GradeId;
    private String PlanId;
    private String EquipmentId;

    public String getApiCode() {
        return ApiCode;
    }

    public void setApiCode(String ApiCode) {
        this.ApiCode = ApiCode;
    }

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String AppCode) {
        this.AppCode = AppCode;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String ClassId) {
        this.ClassId = ClassId;
    }

    public String getGradeId() {
        return GradeId;
    }

    public void setGradeId(String GradeId) {
        this.GradeId = GradeId;
    }

    public String getPlanId() {
        return PlanId;
    }

    public void setPlanId(String PlanId) {
        this.PlanId = PlanId;
    }

    public String getEquipmentId() {
        return EquipmentId;
    }

    public void setEquipmentId(String EquipmentId) {
        this.EquipmentId = EquipmentId;
    }
}
