package com.zhixing.kpilib.activity;


import com.zhixing.kpilib.mphelper.chart.barchart.IBarData;

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

        if (name.contains("-")){
            String realName = name.substring(5);
            this.name=realName;
        }else{
            this.name = name;
        }

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
