package com.zhixing.employlib.model;

public class IntegralEventEntity {
    public IntegralEventEntity(String number, String score, String des) {
        Number = number;
        Score = score;
        Des = des;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String Number;
    public String Score;
    public String Des;



}
