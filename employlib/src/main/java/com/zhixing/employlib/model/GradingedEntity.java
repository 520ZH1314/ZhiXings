package com.zhixing.employlib.model;

import java.io.Serializable;

/**
 *
 *@author zjq
 *create at 2019/3/13 上午11:28
 * 已评分model
 */
public class GradingedEntity implements Serializable {

    public String imagUrl;
    public String name;
    public String worker;
    public int score;
    public String rank;
    public  String useCode;

    public GradingedEntity(String imagUrl, String name, String worker, int score, String rank,String useCode) {
        this.imagUrl = imagUrl;
        this.name = name;
        this.worker = worker;
        this.score = score;
        this.rank = rank;
        this.useCode=useCode;
    }

    public String getUseCode() {
        return useCode;
    }

    public void setUseCode(String useCode) {
        this.useCode = useCode;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
