package com.zhixing.employlib.model;

public class ExcellentEmployeeEntity {

    public String Title;
    public String ImageUrl;
    public String name;
    public String worker;
    public String rankNum;
    public String rank;
    public String desc;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getRankNum() {
        return rankNum;
    }

    public void setRankNum(String rankNum) {
        this.rankNum = rankNum;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ExcellentEmployeeEntity(String title, String imageUrl, String name, String worker, String rankNum, String rank, String desc) {
        Title = title;
        ImageUrl = imageUrl;
        this.name = name;
        this.worker = worker;
        this.rankNum = rankNum;
        this.rank = rank;
        this.desc = desc;
    }
}
