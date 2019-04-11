package com.zhixing.employlib.api;

import java.io.Serializable;
import java.util.List;

public class DBaseResponse<T> implements Serializable {

    public String Status;
    public String Message;
    public String Total;
    private String Page;
    private String Records;
    private List<T> Rows;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getPage() {
        return Page;
    }

    public void setPage(String page) {
        Page = page;
    }

    public String getRecords() {
        return Records;
    }

    public void setRecords(String records) {
        Records = records;
    }

    public List<T> getRows() {
        return Rows;
    }

    public void setRows(List<T> rows) {
        Rows = rows;
    }
}
