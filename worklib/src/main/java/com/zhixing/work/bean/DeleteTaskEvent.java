package com.zhixing.work.bean;

public class DeleteTaskEvent {

    public DeleteTaskEvent(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    private boolean isDelete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAPiCode() {
        return APiCode;
    }

    public void setAPiCode(String APiCode) {
        this.APiCode = APiCode;
    }

    private  String name;

    public DeleteTaskEvent(boolean isDelete, String name, String APiCode) {
        this.isDelete = isDelete;
        this.name = name;
        this.APiCode = APiCode;
    }

    private String APiCode;


}
