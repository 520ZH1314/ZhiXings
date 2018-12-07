package com.shuben.zhixing.www.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/22.
 */

public class FragmentTwoDate implements Serializable{
    private String InnerUrgeOrderId;//内部催单ID
    private String BillNo;//编号
    private String CustomerName;//客户名称
    private String ToDoUserName;//责任人姓名
    private String CreateDate;//创建时间
    private String TaskStatusName;//进度状态

    public FragmentTwoDate(String innerUrgeOrderId, String billNo, String customerName, String toDoUserName, String createDate, String taskStatusName) {
        InnerUrgeOrderId = innerUrgeOrderId;
        BillNo = billNo;
        CustomerName = customerName;
        ToDoUserName = toDoUserName;
        CreateDate = createDate;
        TaskStatusName = taskStatusName;
    }

    public String getInnerUrgeOrderId() {
        return InnerUrgeOrderId;
    }

    public void setInnerUrgeOrderId(String innerUrgeOrderId) {
        InnerUrgeOrderId = innerUrgeOrderId;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getToDoUserName() {
        return ToDoUserName;
    }

    public void setToDoUserName(String toDoUserName) {
        ToDoUserName = toDoUserName;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getTaskStatusName() {
        return TaskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        TaskStatusName = taskStatusName;
    }
}


