package com.zhixing.tpmlib.bean;

public class PlannListBean {

    /**
     * GradeId : 33afd649-c3d3-4678-a5ce-eec0cf6c52a5
     * GradeCode : 005
     * GradeName : 千小时保养
     * Description : 千小时保养
     * ParentId : 46b353e3-343c-4c29-9b8b-e9035a37d244
     * Period : 30
     * Seq : 5
     * CreateTime : 2018-05-24T20:19:37
     * IsEnable : 0
     * TenantId : 00000000-0000-0000-0000-000000000001
     */

    private String GradeId;
    private String GradeCode;
    private String GradeName;
    private String Description;
    private String ParentId;
    private int Period;
    private int Seq;
    private String CreateTime;
    private String IsEnable;
    private String TenantId;

    public String getGradeId() {
        return GradeId;
    }

    public void setGradeId(String GradeId) {
        this.GradeId = GradeId;
    }

    public String getGradeCode() {
        return GradeCode;
    }

    public void setGradeCode(String GradeCode) {
        this.GradeCode = GradeCode;
    }

    public String getGradeName() {
        return GradeName;
    }

    public void setGradeName(String GradeName) {
        this.GradeName = GradeName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }

    public int getPeriod() {
        return Period;
    }

    public void setPeriod(int Period) {
        this.Period = Period;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int Seq) {
        this.Seq = Seq;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getIsEnable() {
        return IsEnable;
    }

    public void setIsEnable(String IsEnable) {
        this.IsEnable = IsEnable;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }
}
