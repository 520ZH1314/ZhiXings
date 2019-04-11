package com.zhixing.employlib.model;

import java.io.Serializable;

public class PersonTestEntity implements Serializable {

    public String ItemName;

    public String ItemId;
    public String ItemCode;

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public int Score;
    public PersonTestEntity( ) {

    }

    public PersonTestEntity(String itemName, int score,String ItemId) {
        this.ItemId=ItemId;
        this.ItemName = itemName;
        this. Score = score;

    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
