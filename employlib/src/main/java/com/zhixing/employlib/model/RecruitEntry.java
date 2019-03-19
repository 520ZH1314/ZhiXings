package com.zhixing.employlib.model;

public class RecruitEntry {

    public String workName;
    public String date;
    public String workYear;
    public String workDesc;
    public String money;
    public String status;

    public RecruitEntry(String workName, String date, String workYear, String workDesc, String money, String status) {
        this.workName = workName;
        this.date = date;
        this.workYear = workYear;
        this.workDesc = workDesc;
        this.money = money;
        this.status = status;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWorkYear() {
        return workYear;
    }

    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
