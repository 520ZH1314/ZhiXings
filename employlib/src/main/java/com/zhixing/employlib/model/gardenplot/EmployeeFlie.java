package com.zhixing.employlib.model.gardenplot;

public class EmployeeFlie {


    /**
     * FileID : 图片ID
     * FileTitle : 图片名称
     * FilePath : 图片地址
     * FileType : 图片类型
     * FileSize : 图片大小
     * FileDesc : 图片描述
     * LinkedTable : 图片链接表
     * LinkedTableId : 图片链接表ID
     * UploadDate : 上传时间
     * TenantId : 租号
     */

    private String FileID;
    private String FileTitle;
    private String FilePath;
    private String FileType;
    private String FileSize;
    private String FileDesc;
    private String LinkedTable;
    private String LinkedTableId;
    private String UploadDate;
    private String TenantId;

    public String getFileID() {
        return FileID;
    }

    public void setFileID(String FileID) {
        this.FileID = FileID;
    }

    public String getFileTitle() {
        return FileTitle;
    }

    public void setFileTitle(String FileTitle) {
        this.FileTitle = FileTitle;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String FileType) {
        this.FileType = FileType;
    }

    public String getFileSize() {
        return FileSize;
    }

    public void setFileSize(String FileSize) {
        this.FileSize = FileSize;
    }

    public String getFileDesc() {
        return FileDesc;
    }

    public void setFileDesc(String FileDesc) {
        this.FileDesc = FileDesc;
    }

    public String getLinkedTable() {
        return LinkedTable;
    }

    public void setLinkedTable(String LinkedTable) {
        this.LinkedTable = LinkedTable;
    }

    public String getLinkedTableId() {
        return LinkedTableId;
    }

    public void setLinkedTableId(String LinkedTableId) {
        this.LinkedTableId = LinkedTableId;
    }

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String UploadDate) {
        this.UploadDate = UploadDate;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }
}
