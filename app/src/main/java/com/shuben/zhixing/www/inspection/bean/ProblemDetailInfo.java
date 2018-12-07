package com.shuben.zhixing.www.inspection.bean;

/**
 * Created by Geyan on 2018/8/2.
 */

public class ProblemDetailInfo {
    private String ProblemNo;
    private String ProblemId;//问题记录ID
    private String PatrolTaskId;//巡检任务记录ID
    private String ProblemDesc;//问题描述
    private String Measures;//
    private String LiableDeptName;//责任部门名称
    private String LiableUserName;//责任人姓名
    private String PatrolUserName;//责任人姓名

    private String ItemType;//
    private String DueDate;//要求完成时间
    private String CompleteDate;//实际完成时间
    private String Status;// 0;//未处理，1;//已处理
    private String ProductName;
    private String FilePath01;
    private String FilePath02;

    public ProblemDetailInfo(String problemNo, String problemId, String patrolTaskId, String problemDesc, String measures, String liableDeptName, String liableUserName, String patrolUserName, String itemType, String dueDate, String completeDate, String status, String productName, String filePath01, String filePath02) {
        ProblemNo = problemNo;
        ProblemId = problemId;
        PatrolTaskId = patrolTaskId;
        ProblemDesc = problemDesc;
        Measures = measures;
        LiableDeptName = liableDeptName;
        LiableUserName = liableUserName;
        PatrolUserName = patrolUserName;
        ItemType = itemType;
        DueDate = dueDate;
        CompleteDate = completeDate;
        Status = status;
        ProductName = productName;
        FilePath01 = filePath01;
        FilePath02 = filePath02;
    }

    public String getProblemNo() {
        return ProblemNo;
    }

    public void setProblemNo(String problemNo) {
        ProblemNo = problemNo;
    }

    public String getProblemId() {
        return ProblemId;
    }

    public void setProblemId(String problemId) {
        ProblemId = problemId;
    }

    public String getPatrolTaskId() {
        return PatrolTaskId;
    }

    public void setPatrolTaskId(String patrolTaskId) {
        PatrolTaskId = patrolTaskId;
    }

    public String getProblemDesc() {
        return ProblemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        ProblemDesc = problemDesc;
    }

    public String getMeasures() {
        return Measures;
    }

    public void setMeasures(String measures) {
        Measures = measures;
    }

    public String getLiableDeptName() {
        return LiableDeptName;
    }

    public void setLiableDeptName(String liableDeptName) {
        LiableDeptName = liableDeptName;
    }

    public String getLiableUserName() {
        return LiableUserName;
    }

    public void setLiableUserName(String liableUserName) {
        LiableUserName = liableUserName;
    }

    public String getPatrolUserName() {
        return PatrolUserName;
    }

    public void setPatrolUserName(String patrolUserName) {
        PatrolUserName = patrolUserName;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getCompleteDate() {
        return CompleteDate;
    }

    public void setCompleteDate(String completeDate) {
        CompleteDate = completeDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFilePath01() {
        return FilePath01;
    }

    public void setFilePath01(String filePath01) {
        FilePath01 = filePath01;
    }

    public String getFilePath02() {
        return FilePath02;
    }

    public void setFilePath02(String filePath02) {
        FilePath02 = filePath02;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }
}
