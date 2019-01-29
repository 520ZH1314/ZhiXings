package com.zhixing.tpmlib.bean;

public class MaintenanceWarnBean {
    
    public String machineName;
    public String machineId;
    public String maintainType;
    public String machineStatus;
    public String machineDate;
    public String ClassId;
    public String EquipmentId;
    public String PlanId;
    public String MaintanceId;

    public String getMaintanceId() {
        return MaintanceId;
    }

    public void setMaintanceId(String maintanceId) {
        MaintanceId = maintanceId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getEquipmentId() {
        return EquipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        EquipmentId = equipmentId;
    }

    public String getPlanId() {
        return PlanId;
    }

    public void setPlanId(String planId) {
        PlanId = planId;
    }

    public String getGradeId() {
        return GradeId;
    }

    public void setGradeId(String gradeId) {
        GradeId = gradeId;
    }

    public String GradeId;
    public String sum;

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMaintainType() {
        return maintainType;
    }

    public void setMaintainType(String maintainType) {
        this.maintainType = maintainType;
    }

    public String getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }

    public String getMachineDate() {
        return machineDate;
    }

    public void setMachineDate(String machineDate) {
        this.machineDate = machineDate;
    }

    public MaintenanceWarnBean(String machineName, String machineId, String maintainType, String machineStatus, String machineDate,String sum) {
        this.machineName = machineName;
        this.sum=sum;
        this.machineId = machineId;
        this.maintainType = maintainType;
        this.machineStatus = machineStatus;
        this.machineDate = machineDate;

    }

    public MaintenanceWarnBean(String machineName, String machineId, String maintainType, String machineStatus, String machineDate,String sum,String classId, String equipmentId, String planId, String gradeId,String MaintanceId) {
        this.machineName = machineName;
        this.machineId = machineId;
        this.maintainType = maintainType;
        this.machineStatus = machineStatus;
        this.machineDate = machineDate;
        ClassId = classId;
        EquipmentId = equipmentId;
        PlanId = planId;
        GradeId = gradeId;
        this.sum = sum;
        this.MaintanceId=MaintanceId;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
