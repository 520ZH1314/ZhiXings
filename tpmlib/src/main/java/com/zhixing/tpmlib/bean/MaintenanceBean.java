package com.zhixing.tpmlib.bean;

public class MaintenanceBean {
    public String time;
    public String type;
    public String MaintenanceType;
    public String MaintenancePeople;
    public String delayDay;

    public MaintenanceBean(String time, String type, String maintenanceType, String maintenancePeople, String delayDay) {
        this.time = time;
        this.type = type;
        MaintenanceType = maintenanceType;
        MaintenancePeople = maintenancePeople;
        this.delayDay = delayDay;
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

    public String getMaintenanceType() {
        return MaintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        MaintenanceType = maintenanceType;
    }

    public String getMaintenancePeople() {
        return MaintenancePeople;
    }

    public void setMaintenancePeople(String maintenancePeople) {
        MaintenancePeople = maintenancePeople;
    }

    public String getDelayDay() {
        return delayDay;
    }

    public void setDelayDay(String delayDay) {
        this.delayDay = delayDay;
    }
}
