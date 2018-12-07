package com.shuben.zhixing.www.data;

/**
 * Created by Geyan on 2017/9/5.
 */

public class MyTaskDetailInfo {
    private String InnerUrgeOrderId="";
    private String BillNo="";//单据编号
    private String CustomerName="";//客户名称
    private String ProductName="";//产品名称
    private String DeliverQty="";//数量
    private String DeliverDate="";//交货日期
    private String CreateDate="";//创建日期
    private String DeliverAddress="";//地址
    private String CreateUserName="";//创建人
    private String ToDoUserName="";//创建人或责任人
    private String CCUser="";//抄送人

    public MyTaskDetailInfo(String innerUrgeOrderId, String billNo, String customerName, String productName, String deliverQty, String deliverDate, String createDate, String deliverAddress, String createUserName, String toDoUserName, String CCUser) {
        InnerUrgeOrderId = innerUrgeOrderId;
        BillNo = billNo;
        CustomerName = customerName;
        ProductName = productName;
        DeliverQty = deliverQty;
        DeliverDate = deliverDate;
        CreateDate = createDate;
        DeliverAddress = deliverAddress;
        CreateUserName = createUserName;
        ToDoUserName = toDoUserName;
        this.CCUser = CCUser;
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

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDeliverQty() {
        return DeliverQty;
    }

    public void setDeliverQty(String deliverQty) {
        DeliverQty = deliverQty;
    }

    public String getDeliverDate() {
        return DeliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        DeliverDate = deliverDate;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getDeliverAddress() {
        return DeliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        DeliverAddress = deliverAddress;
    }

    public String getCreateUserName() {
        return CreateUserName;
    }

    public void setCreateUserName(String createUserName) {
        CreateUserName = createUserName;
    }

    public String getToDoUserName() {
        return ToDoUserName;
    }

    public void setToDoUserName(String toDoUserName) {
        ToDoUserName = toDoUserName;
    }

    public String getCCUser() {
        return CCUser;
    }

    public void setCCUser(String CCUser) {
        this.CCUser = CCUser;
    }
}
