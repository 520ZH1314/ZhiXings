package com.zhixing.employlib.model.eventbus;

public class SelectTeamEvent {

    public String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public SelectTeamEvent(String event) {
        this.event = event;
    }
}
