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


}
