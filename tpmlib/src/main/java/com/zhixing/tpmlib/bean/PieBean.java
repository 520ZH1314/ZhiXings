package com.zhixing.tpmlib.bean;

public class PieBean {

    public String Normal;
    public String Maintenance;
    public String Warn;

    public String getNormal() {
        return Normal;
    }

    public void setNormal(String normal) {
        Normal = normal;
    }

    public String getMaintenance() {
        return Maintenance;
    }

    public void setMaintenance(String maintenance) {
        Maintenance = maintenance;
    }

    public String getWarn() {
        return Warn;
    }

    public void setWarn(String warn) {
        Warn = warn;
    }

    public PieBean(String normal, String maintenance, String warn) {
        Normal = normal;
        Maintenance = maintenance;
        Warn = warn;
    }
}
