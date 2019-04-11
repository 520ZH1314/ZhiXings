package com.zhixing.employlib.model;

import java.io.Serializable;

public class GradingEventEntity implements Serializable {
    //{"AppCode":"EMS","ApiCode":"GetKeyAppeal",
    //"UserCode":"06152","TeamId":"87cfd9e0-f594-467b-9438-e0e801e32bc9",
    //"TenantId":"00000000-0000-0000-0000-000000000001","CreateTime":"2019-03-27"
    //}
    public String  name;
    public int  score;

    public String id;

    public GradingEventEntity(String name, int score,String id) {
        this.name = name;
        this.score = score;
        this.id=id;
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
}
