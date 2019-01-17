package com.zhixing.tpmlib.bean;

public class DailyCheckItemBean {
    public  int  imageUrl;
    public String checkBody;

    public String  checkStatus;

    public  String equipmentName;

    public  String equipmentAdress;
    public  String equipmentText;

    public DailyCheckItemBean(int imageUrl, String checkBody, String checkStatus, String equipmentName, String equipmentAdress, String equipmentText) {
        this.imageUrl = imageUrl;
        this.checkBody = checkBody;
        this.checkStatus = checkStatus;
        this.equipmentName = equipmentName;
        this.equipmentAdress = equipmentAdress;
        this.equipmentText = equipmentText;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCheckBody() {
        return checkBody;
    }

    public void setCheckBody(String checkBody) {
        this.checkBody = checkBody;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentAdress() {
        return equipmentAdress;
    }

    public void setEquipmentAdress(String equipmentAdress) {
        this.equipmentAdress = equipmentAdress;
    }

    public String getEquipmentText() {
        return equipmentText;
    }

    public void setEquipmentText(String equipmentText) {
        this.equipmentText = equipmentText;
    }
}
