package com.zhixing.work.bean;

public class MeetCompeteRefrshDataEvent {

    public boolean isRefrsh=false;

    public MeetCompeteRefrshDataEvent(boolean isRefrsh) {
        this.isRefrsh = isRefrsh;
    }

    public boolean isRefrsh() {
        return isRefrsh;
    }

    public void setRefrsh(boolean refrsh) {
        isRefrsh = refrsh;
    }
}
