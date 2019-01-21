package com.zhixing.tpmlib.bean;

public class PlanDatailBean {
    /**
     * EquipmentId : AA3F27BE-7976-40B1-B01B-0EE8A8C26041
     * ClassId : DC100F07-62F5-4C2A-891F-2B7E3562850D
     * ItemId : 48c833c8-6b39-47d1-b8d2-8bc67fd93138
     * Cell : 塑胶机台
     * Position : 塑胶车间
     * Description : 设备有无异常声音
     * Operator : 保养员
     * StandardImage :
     * ActuallyImage :
     * Fruit :
     */

    private String EquipmentId;
    private String ClassId;
    private String ItemId;
    private String Cell;
    private String Position;
    private String Description;
    private String Operator;
    private String StandardImage;
    private String ActuallyImage;
    private String Fruit;
    private String num;
    public String getEquipmentId() {
        return EquipmentId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setEquipmentId(String EquipmentId) {
        this.EquipmentId = EquipmentId;
    }
    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String ClassId) {
        this.ClassId = ClassId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String ItemId) {
        this.ItemId = ItemId;
    }

    public String getCell() {
        return Cell;
    }

    public void setCell(String Cell) {
        this.Cell = Cell;
    }

    public String getPosition() {
        return Position;
    }
    public void setPosition(String Position) {
        this.Position = Position;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String Operator) {
        this.Operator = Operator;
    }

    public String getStandardImage() {
        return StandardImage;
    }

    public void setStandardImage(String StandardImage) {
        this.StandardImage = StandardImage;
    }

    public String getActuallyImage() {
        return ActuallyImage;
    }

    public void setActuallyImage(String ActuallyImage) {
        this.ActuallyImage = ActuallyImage;
    }

    public String getFruit() {
        return Fruit;
    }
    public void setFruit(String Fruit) {
        this.Fruit = Fruit;
    }
}
