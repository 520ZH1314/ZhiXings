package com.zhixing.work.bean;

public class MeetJoinStatusBean {

    public String getJoinName() {
        return JoinName;
    }

    public void setJoinName(String joinName) {
        JoinName = joinName;
    }

    public MeetJoinStatusBean(String joinName, String joinStatus, String joinDate) {
        JoinName = joinName;
        JoinStatus = joinStatus;
        JoinDate = joinDate;
    }

    public String getJoinStatus() {
        return JoinStatus;
    }

    public void setJoinStatus(String joinStatus) {
        JoinStatus = joinStatus;
    }

    public String getJoinDate() {
        return JoinDate;
    }

    public void setJoinDate(String joinDate) {
        JoinDate = joinDate;
    }

    public String JoinName;


    public String JoinStatus;
    public String JoinDate;


}
