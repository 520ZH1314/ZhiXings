package com.zhixing.tpmlib.bean;

public class CheckItemEntity {
    private String picNum;
    private String macheName;

    public String getPicNum() {
        return picNum;
    }

    public void setPicNum(String picNum) {
        this.picNum = picNum;
    }

    public String getMacheName() {
        return macheName;
    }

    public void setMacheName(String macheName) {
        this.macheName = macheName;
    }

    @Override
    public String toString() {
        return "CheckItemEntity{" +
                "picNum='" + picNum + '\'' +
                ", macheName='" + macheName + '\'' +
                '}';
    }
}
