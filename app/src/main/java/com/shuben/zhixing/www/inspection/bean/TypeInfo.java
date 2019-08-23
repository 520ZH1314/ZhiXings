package com.shuben.zhixing.www.inspection.bean;

import java.io.Serializable;

/**
 * Created by Geyan on 2018/7/5.
 */

public class TypeInfo implements Serializable{
    private String ClassId;//巡检类别ID
    private String ClassName;//巡检类别名称
    private String Period;//巡检周期，取值有“日、周、月”三种
    private String Frequency;//巡检频率
    private String IsRelationWorksheet;//是否关联工单，1;//是，0;//否
    private String IsCreateTaskByStart;//开工自动产生任务，1;//是，0;//否
    private String FilePath;
    private String ClassCode;
    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public TypeInfo(String classId, String className, String period, String frequency, String isRelationWorksheet, String isCreateTaskByStart,String FilePath,String ClassCode) {
        ClassId = classId;
        ClassName = className;
        Period = period;
        Frequency = frequency;
        IsRelationWorksheet = isRelationWorksheet;
        IsCreateTaskByStart = isCreateTaskByStart;
        this.FilePath = FilePath;
        this.ClassCode = ClassCode;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String period) {
        Period = period;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }

    public String getIsRelationWorksheet() {
        return IsRelationWorksheet;
    }

    public void setIsRelationWorksheet(String isRelationWorksheet) {
        IsRelationWorksheet = isRelationWorksheet;
    }

    public String getIsCreateTaskByStart() {
        return IsCreateTaskByStart;
    }

    public void setIsCreateTaskByStart(String isCreateTaskByStart) {
        IsCreateTaskByStart = isCreateTaskByStart;
    }
}
