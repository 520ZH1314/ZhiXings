package com.zhixing.employlib.model.eventbus;

public class MessageEvent {
    private String event;

    public MessageEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
