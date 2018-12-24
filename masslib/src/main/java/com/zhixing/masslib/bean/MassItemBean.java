package com.zhixing.masslib.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class MassItemBean implements Serializable {
    private String no;
    private String work;
    private String line;
    private String product;
    private int count;
    private String lineCode;//产线代码
    private String lineId;
    private String ProductCode;
    private String all_t;//时间
    private String pNo;//批次号
    private String pNum;//批次数量
    private String createPerson;
    private int state;
    private ArrayList<WxItem> wxItems;

    public ArrayList<WxItem> getWxItems() {
        return wxItems;
    }

    public void setWxItems(ArrayList<WxItem> wxItems) {
        this.wxItems = wxItems;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getpNo() {
        return pNo;
    }

    public void setpNo(String pNo) {
        this.pNo = pNo;
    }

    public String getpNum() {
        return pNum;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }

    public String getAll_t() {
        return all_t;
    }

    public void setAll_t(String all_t) {
        this.all_t = all_t;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    private String time;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
