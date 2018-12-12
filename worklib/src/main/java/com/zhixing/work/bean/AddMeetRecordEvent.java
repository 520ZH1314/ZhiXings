package com.zhixing.work.bean;

public class AddMeetRecordEvent {

    public AddMeetRecordEvent(boolean isSend) {
        this.isSend = isSend;
    }

    public boolean isSend;

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
