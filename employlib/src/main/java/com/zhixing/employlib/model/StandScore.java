package com.zhixing.employlib.model;

import java.io.Serializable;

/**
 * 考核标准颜色
 */
public class StandScore implements Serializable {

    /**
     * GrapeId : ce9b4f2e-453f-4627-b812-620787a053b4
     * GrapeCode : 02
     * GrapeName : 蓝葡萄
     * MinScore : 6
     * Symbol : ~
     * MaxScore : 9
     * TenantId : 00000000-0000-0000-0000-000000000001
     * ColorPhoto : blue.png
     * Description : 员工正常出勤即获得基础分值 5 分
     * Seq : 2
     * CreateTime : 2018-05-29T14:25:37
     * IsEnable : 1
     * IsAPP :
     * IsDefaultColor :
     * GrapeColor : blue
     * GrapeColorValue :
     */

    private String GrapeId;
    private String GrapeCode;
    private String GrapeName;
    private int MinScore;
    private String Symbol;
    private int MaxScore;
    private String TenantId;
    private String ColorPhoto;
    private String Description;
    private int Seq;
    private String CreateTime;
    private String IsEnable;
    private String IsAPP;
    private String IsDefaultColor;
    private String GrapeColor;
    private String GrapeColorValue;

    public String getGrapeId() {
        return GrapeId;
    }

    public void setGrapeId(String GrapeId) {
        this.GrapeId = GrapeId;
    }

    public String getGrapeCode() {
        return GrapeCode;
    }

    public void setGrapeCode(String GrapeCode) {
        this.GrapeCode = GrapeCode;
    }

    public String getGrapeName() {
        return GrapeName;
    }

    public void setGrapeName(String GrapeName) {
        this.GrapeName = GrapeName;
    }

    public int getMinScore() {
        return MinScore;
    }

    public void setMinScore(int MinScore) {
        this.MinScore = MinScore;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String Symbol) {
        this.Symbol = Symbol;
    }

    public int getMaxScore() {
        return MaxScore;
    }

    public void setMaxScore(int MaxScore) {
        this.MaxScore = MaxScore;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    public String getColorPhoto() {
        return ColorPhoto;
    }

    public void setColorPhoto(String ColorPhoto) {
        this.ColorPhoto = ColorPhoto;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int Seq) {
        this.Seq = Seq;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getIsEnable() {
        return IsEnable;
    }

    public void setIsEnable(String IsEnable) {
        this.IsEnable = IsEnable;
    }

    public String getIsAPP() {
        return IsAPP;
    }

    public void setIsAPP(String IsAPP) {
        this.IsAPP = IsAPP;
    }

    public String getIsDefaultColor() {
        return IsDefaultColor;
    }

    public void setIsDefaultColor(String IsDefaultColor) {
        this.IsDefaultColor = IsDefaultColor;
    }

    public String getGrapeColor() {
        return GrapeColor;
    }

    public void setGrapeColor(String GrapeColor) {
        this.GrapeColor = GrapeColor;
    }

    public String getGrapeColorValue() {
        return GrapeColorValue;
    }

    public void setGrapeColorValue(String GrapeColorValue) {
        this.GrapeColorValue = GrapeColorValue;
    }
}
