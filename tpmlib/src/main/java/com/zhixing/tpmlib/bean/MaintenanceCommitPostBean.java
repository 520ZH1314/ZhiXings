package com.zhixing.tpmlib.bean;

public class MaintenanceCommitPostBean {

 public String   AppCode;
    public String   ApiCode;
    public String   EquipmentId;
    public String   GradeId;
    public String   ItemId;
    public String   Fruit;
    public String   ActuallyImage;
    public String   PlanId;
    public String   ClassId;
    public String   TenantId;
    public MaintenanceCommitPostBean(String TenantId,String appCode, String apiCode, String equipmentId, String gradeId, String itemId, String fruit, String actuallyImage, String planId, String classId, String cell, String position, String description, String standardImage, String paramater, int seq, String operator, String maintananceId) {
        this. TenantId=TenantId;
        this. AppCode = appCode;
        this. ApiCode = apiCode;
        this. EquipmentId = equipmentId;
        this. GradeId = gradeId;
        this. ItemId = itemId;
        this. Fruit = fruit;
        this. ActuallyImage = actuallyImage;
        this. PlanId = planId;
        this. ClassId = classId;
        this. Cell = cell;
        this. Position = position;
        this. Description = description;
        this. StandardImage = standardImage;
        this. Paramater = paramater;
        this. Seq = seq;
        this. Operator = operator;
        this. MaintananceId = maintananceId;
    }

    public String   Cell;

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

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getFruit() {
        return Fruit;
    }

    public void setFruit(String fruit) {
        Fruit = fruit;
    }

    public String getActuallyImage() {
        return ActuallyImage;
    }

    public void setActuallyImage(String actuallyImage) {
        ActuallyImage = actuallyImage;
    }

    public String getPlanId() {
        return PlanId;
    }

    public void setPlanId(String planId) {
        PlanId = planId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getCell() {
        return Cell;
    }

    public void setCell(String cell) {
        Cell = cell;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStandardImage() {
        return StandardImage;
    }

    public void setStandardImage(String standardImage) {
        StandardImage = standardImage;
    }

    public String getParamater() {
        return Paramater;
    }

    public void setParamater(String paramater) {
        Paramater = paramater;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int seq) {
        Seq = seq;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getMaintananceId() {
        return MaintananceId;
    }

    public void setMaintananceId(String maintananceId) {
        MaintananceId = maintananceId;
    }

    public String   Position;
    public String   Description;
    public String   StandardImage;
    public String   Paramater;
    public int   Seq;
    public String   Operator;
    public String   MaintananceId;



}
