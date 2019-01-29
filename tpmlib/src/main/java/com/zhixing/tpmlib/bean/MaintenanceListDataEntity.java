package com.zhixing.tpmlib.bean;

import java.io.Serializable;
import java.util.List;

public class MaintenanceListDataEntity implements Serializable {

    private String EquipmentId;
    private String ClassId;
    private String GradeId;
    private String MaintanceId;
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

    public String getGradeId() {
        return GradeId;
    }

    public void setGradeId(String gradeId) {
        GradeId = gradeId;
    }

    public String getGradeName() {
        return GradeName;
    }

    public void setGradeName(String gradeName) {
        GradeName = gradeName;
    }

    public String getEquipmentCode() {
        return EquipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        EquipmentCode = equipmentCode;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        EquipmentName = equipmentName;
    }

    public String getPlanId() {
        return PlanId;
    }

    public void setPlanId(String planId) {
        PlanId = planId;
    }

    public String getMaintanceId() {
        return MaintanceId;
    }

    public void setMaintanceId(String maintanceId) {
        MaintanceId = maintanceId;
    }

    public String getMaintanceDate() {
        return MaintanceDate;
    }

    public void setMaintanceDate(String maintanceDate) {
        MaintanceDate = maintanceDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String GradeName;
    private String EquipmentCode;
    private String EquipmentName;
    private String PlanId;

    private String MaintanceDate;
    private String Status;

}
