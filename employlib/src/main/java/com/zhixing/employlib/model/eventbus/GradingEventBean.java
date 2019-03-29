package com.zhixing.employlib.model.eventbus;

public class GradingEventBean {

    private  String id;
    public String  name;
    public String date;

    public String stringId;

    public GradingEventBean(String name, int score,String id) {
        this.name = name;
        this.score = score;
        this.id=id;
    }

    public String getDate() {
        return date;
    }

    public GradingEventBean(String id, String name, String date, int score) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.score = score;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int  score;
}
