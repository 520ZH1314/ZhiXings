package com.zhixing.work.bean;

public class UpdateMeetSureEvent {

    public boolean isTrue;

    public UpdateMeetSureEvent(boolean isTrue) {
        this.isTrue = isTrue;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }
}
