package com.zhixing.employlib.model;
/**
 *
 *@author zjq
 *create at 2019/3/13 上午11:28
 * 已评分model
 */
public class GradingedEntity {

    public String imagUrl;
    public String name;
    public String worker;
    public String score;
    public String rank;

    public GradingedEntity(String imagUrl, String name, String worker, String score, String rank) {
        this.imagUrl = imagUrl;
        this.name = name;
        this.worker = worker;
        this.score = score;
        this.rank = rank;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
