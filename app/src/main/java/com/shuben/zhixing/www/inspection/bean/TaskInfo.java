package com.shuben.zhixing.www.inspection.bean;

import java.io.Serializable;

/**
 * Created by Geyan on 2018/7/10.
 */

public class TaskInfo implements Serializable {

    private String PatrolTaskId;
    private String LineName;//产线名称
    private String LineId;//产线名称
    private String FormNo;//巡检单号
    private String WorkSheetNo;//工单号
    private String PatrolUserId;//巡检人ID
    private String PatrolUserName;//巡线人姓名
    private String PlanPatrolTime;//计划巡检时间
    private String RealPatrolDate;//实际巡检时间
    private String EndPatrolDate;//结束巡检时间
    private String Status;//巡线状态，－5;//未完成；0;//未开始；5;//进行中；10;//已完成
    private String ClassName;//巡线类别
    private String ProductName;//巡线类别

    public TaskInfo(String patrolTaskId, String lineName, String lineId, String formNo, String workSheetNo, String patrolUserId, String patrolUserName, String planPatrolTime, String realPatrolDate, String endPatrolDate, String status, String className, String productName) {
        PatrolTaskId = patrolTaskId;
        LineName = lineName;
        LineId = lineId;
        FormNo = formNo;
        WorkSheetNo = workSheetNo;
        PatrolUserId = patrolUserId;
        PatrolUserName = patrolUserName;
        PlanPatrolTime = planPatrolTime;
        RealPatrolDate = realPatrolDate;
        EndPatrolDate = endPatrolDate;
        Status = status;
        ClassName = className;
        ProductName = productName;
    }

    public TaskInfo(String patrolTaskId, String lineName, String lineId, String formNo, String workSheetNo, String patrolUserId, String patrolUserName, String planPatrolTime, String realPatrolDate, String endPatrolDate, String status, String className) {
        PatrolTaskId = patrolTaskId;
        LineName = lineName;
        LineId = lineId;
        FormNo = formNo;
        WorkSheetNo = workSheetNo;
        PatrolUserId = patrolUserId;
        PatrolUserName = patrolUserName;
        PlanPatrolTime = planPatrolTime;
        RealPatrolDate = realPatrolDate;
        EndPatrolDate = endPatrolDate;
        Status = status;
        ClassName = className;
    }

    public String getPatrolTaskId() {
        return PatrolTaskId;
    }

    public void setPatrolTaskId(String patrolTaskId) {
        PatrolTaskId = patrolTaskId;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String lineName) {
        LineName = lineName;
    }

    public String getFormNo() {
        return FormNo;
    }

    public void setFormNo(String formNo) {
        FormNo = formNo;
    }

    public String getWorkSheetNo() {
        return WorkSheetNo;
    }

    public void setWorkSheetNo(String workSheetNo) {
        WorkSheetNo = workSheetNo;
    }

    public String getPatrolUserId() {
        return PatrolUserId;
    }

    public void setPatrolUserId(String patrolUserId) {
        PatrolUserId = patrolUserId;
    }

    public String getPatrolUserName() {
        return PatrolUserName;
    }

    public void setPatrolUserName(String patrolUserName) {
        PatrolUserName = patrolUserName;
    }

    public String getPlanPatrolTime() {
        return PlanPatrolTime;
    }

    public void setPlanPatrolTime(String planPatrolTime) {
        PlanPatrolTime = planPatrolTime;
    }

    public String getRealPatrolDate() {
        return RealPatrolDate;
    }

    public void setRealPatrolDate(String realPatrolDate) {
        RealPatrolDate = realPatrolDate;
    }

    public String getEndPatrolDate() {
        return EndPatrolDate;
    }

    public void setEndPatrolDate(String endPatrolDate) {
        EndPatrolDate = endPatrolDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getLineId() {
        return LineId;
    }

    public void setLineId(String lineId) {
        LineId = lineId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }
}
