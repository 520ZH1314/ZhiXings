package com.zhixing.tpmlib.bean;
//点检
public class SpotCheckBean {
    public String time;
    public String type;
    public String SpotCheckPeople;
    public String date;
    public String status;

    public SpotCheckBean(String time, String type, String spotCheckPeople, String date, String status) {
        this.time = time;
        this.type = type;
        SpotCheckPeople = spotCheckPeople;
        this.date = date;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpotCheckPeople() {
        return SpotCheckPeople;
    }

    public void setSpotCheckPeople(String spotCheckPeople) {
        SpotCheckPeople = spotCheckPeople;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
