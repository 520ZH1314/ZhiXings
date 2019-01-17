package com.zhixing.tpmlib.bean;
/**
 *
 *@author zjq
 *create at 2019/1/8 下午2:52
 * 异常
 */
public class WarnBean {
    public String time;
    public String type;

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

    public String getWarnType() {
        return warnType;
    }

    public void setWarnType(String warnType) {
        this.warnType = warnType;
    }

    public String getDoPeople() {
        return doPeople;
    }

    public void setDoPeople(String doPeople) {
        this.doPeople = doPeople;
    }

    public String getHowDo() {
        return howDo;
    }

    public void setHowDo(String howDo) {
        this.howDo = howDo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WarnBean(String time, String type, String warnType, String doPeople, String howDo, String status) {
        this.time = time;
        this.type = type;
        this.warnType = warnType;
        this.doPeople = doPeople;
        this.howDo = howDo;
        this.status = status;
    }

    public String warnType;
    public String doPeople;
    public String howDo;
    public String status;
}
