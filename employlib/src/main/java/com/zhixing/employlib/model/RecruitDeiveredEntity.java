package com.zhixing.employlib.model;

public class RecruitDeiveredEntity {
    public String workName;

    public  String type;
    public String date;

    public String workYear;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public RecruitDeiveredEntity(String workName, String date, String workYear, String money, String status,String type) {
        this.workName = workName;
        this.date = date;
        this.workYear = workYear;
        this.money = money;
        this.status = status;
        this.type=type;
    }

    public String money;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RecruitDeiveredEntity(String workName, String date, String workYear, String status) {
        this.workName = workName;
        this.date = date;
        this.workYear = workYear;
        this.status = status;
    }

    public String status;


}
