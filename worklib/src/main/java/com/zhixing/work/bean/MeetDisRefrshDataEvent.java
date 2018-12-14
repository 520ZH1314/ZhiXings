package com.zhixing.work.bean;

public class MeetDisRefrshDataEvent {

    public boolean isRefrsh=false;

    public MeetDisRefrshDataEvent(boolean isRefrsh) {
        this.isRefrsh = isRefrsh;
    }

    public boolean isRefrsh() {
        return isRefrsh;
    }

    public void setRefrsh(boolean refrsh) {
        isRefrsh = refrsh;
    }
}
