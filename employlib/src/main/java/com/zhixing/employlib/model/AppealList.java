package com.zhixing.employlib.model;

import java.io.Serializable;

public class AppealList implements Serializable {


    /**
     * AppealId : 4407a687-2c6d-4311-914d-b77886ae4265
     * ApplyUserId : 62c45ed1-503d-7805-47f3-31137809d5a5
     * ApplyUserName : 彭敏
     * PhoneNumber : 123
     * CreateTime : 2019-03-29T16:46:16
     * Opinion : 回老家了
     * IsAnonymous : 0
     * Status : 0
     * HandleUserId : null
     * HandleUserName : null
     * KeyId : 3ef7b2fd-ea70-4301-89f4-c886e32df6f9
     * KeyName : 正常出勤
     * HandleTime : null
     * HandleOpinion : null
     * TenantId : 00000000-0000-0000-0000-000000000001
     */
    private String KeyDate;
    private String AppealId;
    private String ApplyUserId;
    private String ApplyUserName;
    private String PhoneNumber;
    private String CreateTime;
    private String Opinion;
    private int IsAnonymous;
    private int Status;
    private String HandleUserId;
    private String HandleUserName;
    private String KeyId;
    private String KeyName;
    private String HandleTime;
    private String HandleOpinion;
    private String TenantId;
    private String FilePath;

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getKeyDate() {
        return KeyDate;
    }

    public void setKeyDate(String keyDate) {
        KeyDate = keyDate;
    }

    public String getAppealId() {
        return AppealId;
    }

    public void setAppealId(String appealId) {
        AppealId = appealId;
    }

    public String getApplyUserId() {
        return ApplyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        ApplyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return ApplyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        ApplyUserName = applyUserName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getOpinion() {
        return Opinion;
    }

    public void setOpinion(String opinion) {
        Opinion = opinion;
    }

    public int getIsAnonymous() {
        return IsAnonymous;
    }

    public void setIsAnonymous(int isAnonymous) {
        IsAnonymous = isAnonymous;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getHandleUserId() {
        return HandleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        HandleUserId = handleUserId;
    }

    public String getHandleUserName() {
        return HandleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        HandleUserName = handleUserName;
    }

    public String getKeyId() {
        return KeyId;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public String getKeyName() {
        return KeyName;
    }

    public void setKeyName(String keyName) {
        KeyName = keyName;
    }

    public String getHandleTime() {
        return HandleTime;
    }

    public void setHandleTime(String handleTime) {
        HandleTime = handleTime;
    }

    public String getHandleOpinion() {
        return HandleOpinion;
    }

    public void setHandleOpinion(String handleOpinion) {
        HandleOpinion = handleOpinion;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String tenantId) {
        TenantId = tenantId;
    }
}
