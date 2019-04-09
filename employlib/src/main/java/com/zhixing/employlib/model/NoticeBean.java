package com.zhixing.employlib.model;

public class NoticeBean {

    /**
     * NoticeId : 186ad1d94-abb57-422e-8f60-ff8db280115c
     * TeamId : 2ea1bb97-52fe-4e96-9ad8-9fa82110d6e0
     * NoticeTitle : 00237
     * NoticeContent : 粟昌军
     * Seq : 3
     * CreateTime : 2019-03-29T23:46:14
     * TenantId : 00000000-0000-0000-0000-000000000001
     * CreateUserCode : 444
     * CreateUserName : 666
     * Files : []
     */

    private String NoticeId;
    private String TeamId;
    private String NoticeTitle;
    private String NoticeContent;
    private int Seq;
    private String CreateTime;
    private String TenantId;
    private String CreateUserCode;
    private String CreateUserName;

    public String getNoticeId() {
        return NoticeId;
    }

    public void setNoticeId(String NoticeId) {
        this.NoticeId = NoticeId;
    }

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String TeamId) {
        this.TeamId = TeamId;
    }

    public String getNoticeTitle() {
        return NoticeTitle;
    }

    public void setNoticeTitle(String NoticeTitle) {
        this.NoticeTitle = NoticeTitle;
    }

    public String getNoticeContent() {
        return NoticeContent;
    }

    public void setNoticeContent(String NoticeContent) {
        this.NoticeContent = NoticeContent;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int Seq) {
        this.Seq = Seq;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    public String getCreateUserCode() {
        return CreateUserCode;
    }

    public void setCreateUserCode(String CreateUserCode) {
        this.CreateUserCode = CreateUserCode;
    }

    public String getCreateUserName() {
        return CreateUserName;
    }

    public void setCreateUserName(String CreateUserName) {
        this.CreateUserName = CreateUserName;
    }
}
