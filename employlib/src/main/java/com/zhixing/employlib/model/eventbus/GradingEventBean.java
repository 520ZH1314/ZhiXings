package com.zhixing.employlib.model.eventbus;

public class GradingEventBean {

    public String  name;

    public GradingEventBean(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String  score;
}
