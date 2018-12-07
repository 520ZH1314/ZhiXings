package com.shuben.zhixing.www.inspection.bean;

import java.io.Serializable;

/**
 * Created by Geyan on 2018/7/31.
 */

public class ParamInfo implements Serializable{
    private String PatrolResultId;
    private String PatrolRecordId;
    private String PatrolTaskId;
    private String ItemId;
    private String MappingId;
    private String PatrolValue;
    private String ItemSource;
    private String CategoryCode;
    private String CategoryName;
    private String StandText;
    private String StandValue;
    private String StandMaxValue;
    private String StandMinValue;
    private String ParamName;
    private String ParamUnit;

    public ParamInfo(String patrolResultId, String patrolRecordId, String patrolTaskId, String itemId, String mappingId, String patrolValue, String itemSource, String categoryCode, String categoryName, String standText, String standValue, String standMaxValue, String standMinValue, String paramName, String paramUnit) {
        PatrolResultId = patrolResultId;
        PatrolRecordId = patrolRecordId;
        PatrolTaskId = patrolTaskId;
        ItemId = itemId;
        MappingId = mappingId;
        PatrolValue = patrolValue;
        ItemSource = itemSource;
        CategoryCode = categoryCode;
        CategoryName = categoryName;
        StandText = standText;
        StandValue = standValue;
        StandMaxValue = standMaxValue;
        StandMinValue = standMinValue;
        ParamName = paramName;
        ParamUnit = paramUnit;
    }

    public String getPatrolResultId() {
        return PatrolResultId;
    }

    public void setPatrolResultId(String patrolResultId) {
        PatrolResultId = patrolResultId;
    }

    public String getPatrolRecordId() {
        return PatrolRecordId;
    }

    public void setPatrolRecordId(String patrolRecordId) {
        PatrolRecordId = patrolRecordId;
    }

    public String getPatrolTaskId() {
        return PatrolTaskId;
    }

    public void setPatrolTaskId(String patrolTaskId) {
        PatrolTaskId = patrolTaskId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getMappingId() {
        return MappingId;
    }

    public void setMappingId(String mappingId) {
        MappingId = mappingId;
    }

    public String getPatrolValue() {
        return PatrolValue;
    }

    public void setPatrolValue(String patrolValue) {
        PatrolValue = patrolValue;
    }

    public String getItemSource() {
        return ItemSource;
    }

    public void setItemSource(String itemSource) {
        ItemSource = itemSource;
    }

    public String getCategoryCode() {
        return CategoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        CategoryCode = categoryCode;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getStandText() {
        return StandText;
    }

    public void setStandText(String standText) {
        StandText = standText;
    }

    public String getStandValue() {
        return StandValue;
    }

    public void setStandValue(String standValue) {
        StandValue = standValue;
    }

    public String getStandMaxValue() {
        return StandMaxValue;
    }

    public void setStandMaxValue(String standMaxValue) {
        StandMaxValue = standMaxValue;
    }

    public String getStandMinValue() {
        return StandMinValue;
    }

    public void setStandMinValue(String standMinValue) {
        StandMinValue = standMinValue;
    }

    public String getParamName() {
        return ParamName;
    }

    public void setParamName(String paramName) {
        ParamName = paramName;
    }

    public String getParamUnit() {
        return ParamUnit;
    }

    public void setParamUnit(String paramUnit) {
        ParamUnit = paramUnit;
    }
}
