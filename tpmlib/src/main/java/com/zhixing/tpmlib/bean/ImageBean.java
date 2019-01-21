package com.zhixing.tpmlib.bean;

import java.util.List;

public class ImageBean {

    /**
     * rows : [{"FileID":"643dff65-fb61-4c93-886a-36533c690954","FileTitle":"振力龙门冲床.png","FilePath":"/upload/EPS/201808/6b873c97-9c13-475e-b050-e8ff551f301d.png","FileSize":304343,"FileType":"image/png","FileDesc":"","LinkedTable":"sys_equipmentclass","LinkedTableId":"aee52637-3eaa-463e-b3b6-c766db24261a","UploadDate":"2018-08-28T16:08:21","TenantId":"00000000-0000-0000-0000-000000000001"},{"FileID":"fdc04473-4c4e-44c6-9c09-948918907b77","FileTitle":"泰基山冲床.png","FilePath":"/upload/EPS/201808/8708498e-7a75-4c27-8b1d-2612fff036a0.png","FileSize":286715,"FileType":"image/png","FileDesc":"","LinkedTable":"sys_equipmentclass","LinkedTableId":"aee52637-3eaa-463e-b3b6-c766db24261a","UploadDate":"2018-08-28T16:04:06","TenantId":"00000000-0000-0000-0000-000000000001"},{"FileID":"e5da38b8-eef6-49c4-9cd9-f61f17cdc0e1","FileTitle":"196963427711927231.jpg","FilePath":"/upload/EPS/201808/4a0ab013-c574-47bf-afc2-1da863c8f738.jpg","FileSize":241438,"FileType":"image/jpeg","FileDesc":"","LinkedTable":"sys_equipmentclass","LinkedTableId":"9aeadf76-4a2b-44dd-a15c-316de76d675d","UploadDate":"2018-08-09T21:32:25","TenantId":"00000000-0000-0000-0000-000000000001"},{"FileID":"f08e33b8-1f94-4548-a8d4-0f0adf15a2ca","FileTitle":"微信图片_20180807192806.png","FilePath":"/upload/EPS/201808/f8b06422-16c5-470e-a499-8c8d5023fe68.png","FileSize":165166,"FileType":"image/png","FileDesc":"","LinkedTable":"sys_equipmentclass","LinkedTableId":"dc100f07-62f5-4c2a-891f-2b7e3562850d","UploadDate":"2018-08-07T19:28:35","TenantId":"00000000-0000-0000-0000-000000000001"}]
     * total : 4
     */

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * FileID : 643dff65-fb61-4c93-886a-36533c690954
         * FileTitle : 振力龙门冲床.png
         * FilePath : /upload/EPS/201808/6b873c97-9c13-475e-b050-e8ff551f301d.png
         * FileSize : 304343
         * FileType : image/png
         * FileDesc :
         * LinkedTable : sys_equipmentclass
         * LinkedTableId : aee52637-3eaa-463e-b3b6-c766db24261a
         * UploadDate : 2018-08-28T16:08:21
         * TenantId : 00000000-0000-0000-0000-000000000001
         */

        private String FileID;
        private String FileTitle;
        private String FilePath;
        private int FileSize;
        private String FileType;
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

        public int getFileSize() {
            return FileSize;
        }

        public void setFileSize(int FileSize) {
            this.FileSize = FileSize;
        }

        public String getFileType() {
            return FileType;
        }

        public void setFileType(String FileType) {
            this.FileType = FileType;
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
}
