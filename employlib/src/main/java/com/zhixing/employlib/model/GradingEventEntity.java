package com.zhixing.employlib.model;

public class GradingEventEntity {
    //{"AppCode":"EMS","ApiCode":"GetKeyAppeal",
    //"UserCode":"06152","TeamId":"87cfd9e0-f594-467b-9438-e0e801e32bc9",
    //"TenantId":"00000000-0000-0000-0000-000000000001","CreateTime":"2019-03-27"
    //}
    public String  name;
    public String  score;

    public GradingEventEntity(String name, String score) {
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
}
