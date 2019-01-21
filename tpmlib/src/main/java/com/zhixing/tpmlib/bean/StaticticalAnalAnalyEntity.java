package com.zhixing.tpmlib.bean;

public class StaticticalAnalAnalyEntity {


    /**
     * GradeName : 一级保养
     * Month : 2018-11
     * TotalSum : 0
     * OKRecord : 0
     * OnTimeRate : 0
     */

    private String GradeName;
    private String Month;
    private String TotalSum;
    private String OKRecord;
    private float OnTimeRate;

    public String getGradeName() {
        return GradeName;
    }

    public void setGradeName(String GradeName) {
        this.GradeName = GradeName;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String Month) {
        this.Month = Month;
    }

    public String getTotalSum() {
        return TotalSum;
    }

    public void setTotalSum(String TotalSum) {
        this.TotalSum = TotalSum;
    }

    public String getOKRecord() {
        return OKRecord;
    }

    public void setOKRecord(String OKRecord) {
        this.OKRecord = OKRecord;
    }

    public float getOnTimeRate() {
        return OnTimeRate;
    }

    public void setOnTimeRate(float OnTimeRate) {
        this.OnTimeRate = OnTimeRate;
    }
}
