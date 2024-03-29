package com.zhixing.masslib.bean;

/**
* @author cloor
* @time   2018-12-26 10:24
* @describe  :
*/

public class BaseExpandableBean {
    /**
     * 组别
     */
    public static final int GROUP = 1;
    /**
     * 组内成员
     */
    public static final int FIRST_ITEM = 2;
    /**
     * 是组别还是成员
     */
    private int type;
    /**
     * 属于那组的
     */
    private int groupPosition;
    /**
     * 点击位置的item在改组中的位置
     */
    private int itemNumber;
    /**
     * 组别是不是展开
     */
    private boolean status;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
