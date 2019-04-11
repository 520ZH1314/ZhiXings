package com.zhixing.employlib.model;

import java.io.Serializable;

public class AppealListEntity implements Serializable {


    public String eventName;
    public String eventDate;
    public String doPeople;
    public String desc;
    public String eventStatus;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getDoPeople() {
        return doPeople;
    }

    public void setDoPeople(String doPeople) {
        this.doPeople = doPeople;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public AppealListEntity(String eventName, String eventDate, String doPeople, String desc, String eventStatus) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.doPeople = doPeople;
        this.desc = desc;
        this.eventStatus = eventStatus;
    }
}
