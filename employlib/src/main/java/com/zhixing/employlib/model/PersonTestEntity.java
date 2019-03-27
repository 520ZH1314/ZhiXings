package com.zhixing.employlib.model;

public class PersonTestEntity {

    public String ItemName;
    public String Score;

    public PersonTestEntity( ) {

    }

    public PersonTestEntity(String itemName, String score) {
        ItemName = itemName;
        Score = score;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}
