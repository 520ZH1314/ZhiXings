package com.zhixing.employlib.model;

public class BetterTeamEmployeeEntity {

    public String Title;
    public String ImageUrl;
    public String teamName;
    public String time;

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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BetterTeamEmployeeEntity(String title, String imageUrl, String teamName, String time, String desc) {
        Title = title;
        ImageUrl = imageUrl;
        this.teamName = teamName;
        this.time = time;
        this.desc = desc;

    }
}
