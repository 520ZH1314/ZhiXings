package com.zhixing.tpmlib.bean;

public class MaintenanceStatusType {

    public MaintenanceStatusType(String statusType, String name) {
        StatusType = statusType;
        this.name = name;
    }

    public String getStatusType() {
        return StatusType;
    }

    public void setStatusType(String statusType) {
        StatusType = statusType;
    }

    private  String StatusType;
    public MaintenanceStatusType(String name) {
        this.name = name;

    }
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



}
