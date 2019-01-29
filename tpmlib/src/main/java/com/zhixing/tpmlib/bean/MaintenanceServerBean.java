package com.zhixing.tpmlib.bean;

public class MaintenanceServerBean {
    public String classId;
    public String EquipmentId;
    public String GradeId;
    public String PlanId;
    public String Status;
    public String MaintanceId;

    public String getMaintanceId() {
        return MaintanceId;
    }

    public void setMaintanceId(String maintanceId) {
        MaintanceId = maintanceId;
    }


    public MaintenanceServerBean(String classId, String equipmentId, String gradeId, String planId, String Status, String MaintanceId) {
        this.classId = classId;
        this.EquipmentId = equipmentId;
        this.GradeId = gradeId;
        this.PlanId = planId;
        this.Status = Status;
        this.MaintanceId = MaintanceId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getEquipmentId() {
        return EquipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        EquipmentId = equipmentId;
    }

    public String getGradeId() {
        return GradeId;
    }

    public void setGradeId(String gradeId) {
        GradeId = gradeId;
    }

    public String getPlanId() {
        return PlanId;
    }

    public void setPlanId(String planId) {
        PlanId = planId;
    }
}
