package com.zhixing.tpmlib.bean;

public class CheckRecordDataEntity {


    /**
     * ClassId : DC100F07-62F5-4C2A-891F-2B7E3562850D
     * UserName : STD
     * EquipmentName : 卧式注塑机3#
     * EquipmentId : AA3F27BE-7976-40B1-B01B-0EE8A8C26041
     * GradeId : EB0FD77C-2C49-4547-B914-C993336DFBFA
     * CreateDate : 2018/12/25
     * CompleteTime :
     */

    private String ClassId;
    private String UserName;
    private String EquipmentName;
    private String EquipmentId;
    private String GradeId;
    private String CreateDate;
    private String CompleteTime;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    private String Status;
    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String ClassId) {
        this.ClassId = ClassId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String EquipmentName) {
        this.EquipmentName = EquipmentName;
    }

    public String getEquipmentId() {
        return EquipmentId;
    }

    public void setEquipmentId(String EquipmentId) {
        this.EquipmentId = EquipmentId;
    }

    public String getGradeId() {
        return GradeId;
    }

    public void setGradeId(String GradeId) {
        this.GradeId = GradeId;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public String getCompleteTime() {
        return CompleteTime;
    }

    public void setCompleteTime(String CompleteTime) {
        this.CompleteTime = CompleteTime;
    }
}
