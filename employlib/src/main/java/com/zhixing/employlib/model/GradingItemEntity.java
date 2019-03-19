package com.zhixing.employlib.model;

public class GradingItemEntity {
    public String imageUrl;
    public String name;
    public String sex;
    public String position;
    public String keyEventsNums;

    public boolean isChecked;
    public boolean isSelected;

    public GradingItemEntity(String imageUrl, String name, String sex, String position, String keyEventsNums, boolean isChecked, boolean isSelected) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.sex = sex;
        this.position = position;
        this.keyEventsNums = keyEventsNums;
        this.isChecked = isChecked;
        this.isSelected = isSelected;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getKeyEventsNums() {
        return keyEventsNums;
    }

    public void setKeyEventsNums(String keyEventsNums) {
        this.keyEventsNums = keyEventsNums;
    }

    public GradingItemEntity(String imageUrl, String name, String sex, String position, String keyEventsNums) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.sex = sex;
        this.position = position;
        this.keyEventsNums = keyEventsNums;
    }
}
