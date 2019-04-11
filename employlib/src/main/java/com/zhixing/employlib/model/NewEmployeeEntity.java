package com.zhixing.employlib.model;

import java.io.Serializable;

public class NewEmployeeEntity implements Serializable {

    public String address;
    public String imageUrl;
    public String name;
    public String worker;
    public String teamName;
    public String goTime;
    public String desc;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getGoTime() {
        return goTime;
    }

    public void setGoTime(String goTime) {
        this.goTime = goTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public NewEmployeeEntity(String address, String imageUrl, String name, String worker, String teamName, String goTime, String desc) {
        this.address = address;
        this.imageUrl = imageUrl;
        this.name = name;
        this.worker = worker;
        this.teamName = teamName;
        this.goTime = goTime;
        this.desc = desc;
    }
}
