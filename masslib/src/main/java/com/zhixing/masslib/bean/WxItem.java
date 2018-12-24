package com.zhixing.masslib.bean;

import java.io.Serializable;

public class WxItem implements Serializable {
    private String no;
    private String num;
    private String name;
    private String okC;
    private String okN;
    private String noC;
    private String noN;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOkC() {
        return okC;
    }

    public void setOkC(String okC) {
        this.okC = okC;
    }

    public String getOkN() {
        return okN;
    }

    public void setOkN(String okN) {
        this.okN = okN;
    }

    public String getNoC() {
        return noC;
    }

    public void setNoC(String noC) {
        this.noC = noC;
    }

    public String getNoN() {
        return noN;
    }

    public void setNoN(String noN) {
        this.noN = noN;
    }
}
