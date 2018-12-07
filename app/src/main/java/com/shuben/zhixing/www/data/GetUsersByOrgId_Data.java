package com.shuben.zhixing.www.data;

/**
 * Created by Administrator on 2017/9/8.
 * 2.2.16	我的组织->获取部门人员
 *字段说明：
 UserId：用户ID
 UserCode：用户编号
 UserName：姓名

 "UserId": "f74ebd3d-c520-4a72-b9e8-0a3fc2586eb5",
 "UserCode": "guyx",
 "UserName": "辜弈星"
 */

public class GetUsersByOrgId_Data {
    private  String  UserId;
    private  String  UserCode;
    private  String  UserName;



    public GetUsersByOrgId_Data() {
    }

    public GetUsersByOrgId_Data(String userId, String userCode, String userName) {
        UserId = userId;
        UserCode = userCode;
        UserName = userName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
