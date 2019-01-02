package com.zhixing.tpmlib.bean;


import com.zhixing.tpmlib.utils.IBarData;

/**
 * @author :ChenYangYi
 * @date :2018/08/03/13:02
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class TestBarData implements IBarData {
    private String name;
    private float valueData;

    public TestBarData(float valueData, String name) {


            this.name = name;


        this.valueData = valueData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * 值
     */
    @Override
    public float getValue() {
        return  valueData;
    }

    /**
     * 对应名字
     */
    @Override
    public String getLabelName() {
        return name;
    }

    public void setValue(float value) {
        this.valueData = value;
    }
}
