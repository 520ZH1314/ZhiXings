package com.zhixing.employlib.model.eventbus;

public class UpdateTeamSelectEvent {

    public String UseName;

    public String UseCode;

    public UpdateTeamSelectEvent(String useName, String useCode) {
        UseName = useName;
        UseCode = useCode;
    }
}
