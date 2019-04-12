package com.zhixing.employlib.model.eventbus;

public class UpdateEmployeeEvent {
    public String EmPloyeeType;

    public UpdateEmployeeEvent(String emPloyeeType) {
        EmPloyeeType = emPloyeeType;
    }
}
