package com.zhixing.employlib.model;

public class PersonTestEntity {

    public String imgUrl;
    public String desc;

    public PersonTestEntity(String imgUrl, String desc, String score) {
        this.imgUrl = imgUrl;
        this.desc = desc;
        this.score = score;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String score;

}
