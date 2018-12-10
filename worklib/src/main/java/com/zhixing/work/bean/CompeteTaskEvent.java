package com.zhixing.work.bean;

public class CompeteTaskEvent {

    public CompeteTaskEvent(boolean isCompete) {
        this.isCompete = isCompete;
    }

    public boolean isCompete() {
        return isCompete;
    }

    public void setisCompete(boolean delete) {
        isCompete = delete;
    }

    private boolean isCompete;

    public CompeteTaskEvent(boolean isCompete, String name, String apiCode) {
        this.isCompete = isCompete;
        this.name = name;
        ApiCode = apiCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiCode() {
        return ApiCode;
    }

    public void setApiCode(String apiCode) {
        ApiCode = apiCode;
    }

    private  String name;
    private  String ApiCode;


}
