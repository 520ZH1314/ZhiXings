package com.zhixing.tpmlib.bean;

import java.io.Serializable;

public class PlanMatheBean implements Serializable {
    private String EquipmentId;
    private String EquipmentName;
    private String ClassId;
    private String GradeId;
    private String PlanId;
    public String getEquipmentId() {
        return EquipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        EquipmentId = equipmentId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        EquipmentName = equipmentName;
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
