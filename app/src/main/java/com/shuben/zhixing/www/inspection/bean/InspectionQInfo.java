package com.shuben.zhixing.www.inspection.bean;

import java.io.Serializable;

/**
 * Created by Geyan on 2018/7/30.
 */

public class InspectionQInfo implements Serializable{
    private String ProblemNo;
    private String ProblemId;//问题记录ID
    private String PatrolTaskId;//巡检任务记录ID
    private String ProblemDesc;//问题描述
    private String LiableDeptId;//责任部门ID
    private String LiableDeptName;//责任部门名称
    private String LiableUserId;//责任人ID
    private String LiableUserName;//责任人姓名
    private String PatrolUserName;//责任人姓名
    private String DueDate;//要求完成时间
    private String CompleteDate;//实际完成时间
    private String Status;// 0;//未处理，1;//已处理
    private String ProductName;


    public InspectionQInfo(String problemNo, String problemId, String patrolTaskId, String problemDesc, String liableDeptId, String liableDeptName, String liableUserId, String liableUserName, String patrolUserName, String dueDate, String completeDate, String status, String productName) {
        ProblemNo = problemNo;
        ProblemId = problemId;
        PatrolTaskId = patrolTaskId;
        ProblemDesc = problemDesc;
        LiableDeptId = liableDeptId;
        LiableDeptName = liableDeptName;
        LiableUserId = liableUserId;
        LiableUserName = liableUserName;
        PatrolUserName = patrolUserName;
        DueDate = dueDate;
        CompleteDate = completeDate;
        Status = status;
        ProductName = productName;
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

    public String getLiableDeptId() {
        return LiableDeptId;
    }

    public void setLiableDeptId(String liableDeptId) {
        LiableDeptId = liableDeptId;
    }

    public String getLiableDeptName() {
        return LiableDeptName;
    }

    public void setLiableDeptName(String liableDeptName) {
        LiableDeptName = liableDeptName;
    }

    public String getLiableUserId() {
        return LiableUserId;
    }

    public void setLiableUserId(String liableUserId) {
        LiableUserId = liableUserId;
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

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }
}
