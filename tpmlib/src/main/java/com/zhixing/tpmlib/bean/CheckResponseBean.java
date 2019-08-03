package com.zhixing.tpmlib.bean;

import java.util.List;

public class CheckResponseBean {


    /**
     * rows : [{"ID":"f04a50fa-f6e7-45b4-89bc-2aa3b49fc57b","LineId":"5701f6a0-46d4-4569-9088-a2f61ce524d7","LineStationId":"d57cd732-daac-4804-94e5-8b421e4f84fa","ExceptionGroupId":"3ce7b796-868d-4d0c-8331-bf846e2aa240","TenantId":"43ec274c-7004-4414-a06d-c1dfbda0c488","CreateTime":"2019-03-19T14:31:19","ExceptionGroupCode":"01","ExceptionGroupName":"变压器车间异常","ParentId":"","ParentCode":null,"ParentName":null,"ExceptionId":"cbce1805-f6e9-4e2e-81a1-36f846698f20","ExceptionCode":"001","ExceptionName":"生产异常","Seq":1},{"ID":"f04a50fa-f6e7-45b4-89bc-2aa3b49fc57b","LineId":"5701f6a0-46d4-4569-9088-a2f61ce524d7","LineStationId":"d57cd732-daac-4804-94e5-8b421e4f84fa","ExceptionGroupId":"3ce7b796-868d-4d0c-8331-bf846e2aa240","TenantId":"43ec274c-7004-4414-a06d-c1dfbda0c488","CreateTime":"2019-03-19T14:31:19","ExceptionGroupCode":"01","ExceptionGroupName":"变压器车间异常","ParentId":"cbce1805-f6e9-4e2e-81a1-36f846698f20","ParentCode":"001","ParentName":"生产异常","ExceptionId":"7532261f-630a-4aba-aeb1-a14301659fc1","ExceptionCode":"00101","ExceptionName":"生产首件确认","Seq":1},{"ID":"f04a50fa-f6e7-45b4-89bc-2aa3b49fc57b","LineId":"5701f6a0-46d4-4569-9088-a2f61ce524d7","LineStationId":"d57cd732-daac-4804-94e5-8b421e4f84fa","ExceptionGroupId":"3ce7b796-868d-4d0c-8331-bf846e2aa240","TenantId":"43ec274c-7004-4414-a06d-c1dfbda0c488","CreateTime":"2019-03-19T14:31:19","ExceptionGroupCode":"01","ExceptionGroupName":"变压器车间异常","ParentId":"cbce1805-f6e9-4e2e-81a1-36f846698f20","ParentCode":"001","ParentName":"生产异常","ExceptionId":"3edb4f91-a23f-401c-b383-5d2174487216","ExceptionCode":"00102","ExceptionName":"会议","Seq":1}]
     * total : 3
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
         * ID : f04a50fa-f6e7-45b4-89bc-2aa3b49fc57b
         * LineId : 5701f6a0-46d4-4569-9088-a2f61ce524d7
         * LineStationId : d57cd732-daac-4804-94e5-8b421e4f84fa
         * ExceptionGroupId : 3ce7b796-868d-4d0c-8331-bf846e2aa240
         * TenantId : 43ec274c-7004-4414-a06d-c1dfbda0c488
         * CreateTime : 2019-03-19T14:31:19
         * ExceptionGroupCode : 01
         * ExceptionGroupName : 变压器车间异常
         * ParentId :
         * ParentCode : null
         * ParentName : null
         * ExceptionId : cbce1805-f6e9-4e2e-81a1-36f846698f20
         * ExceptionCode : 001
         * ExceptionName : 生产异常
         * Seq : 1
         */

        private String ID;
        private String LineId;
        private String LineStationId;
        private String ExceptionGroupId;
        private String TenantId;
        private String CreateTime;
        private String ExceptionGroupCode;
        private String ExceptionGroupName;
        private String ParentId;
        private Object ParentCode;
        private Object ParentName;
        private String ExceptionId;
        private String ExceptionCode;
        private String ExceptionName;
        private int Seq;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getLineId() {
            return LineId;
        }

        public void setLineId(String LineId) {
            this.LineId = LineId;
        }

        public String getLineStationId() {
            return LineStationId;
        }

        public void setLineStationId(String LineStationId) {
            this.LineStationId = LineStationId;
        }

        public String getExceptionGroupId() {
            return ExceptionGroupId;
        }

        public void setExceptionGroupId(String ExceptionGroupId) {
            this.ExceptionGroupId = ExceptionGroupId;
        }

        public String getTenantId() {
            return TenantId;
        }

        public void setTenantId(String TenantId) {
            this.TenantId = TenantId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getExceptionGroupCode() {
            return ExceptionGroupCode;
        }

        public void setExceptionGroupCode(String ExceptionGroupCode) {
            this.ExceptionGroupCode = ExceptionGroupCode;
        }

        public String getExceptionGroupName() {
            return ExceptionGroupName;
        }

        public void setExceptionGroupName(String ExceptionGroupName) {
            this.ExceptionGroupName = ExceptionGroupName;
        }

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String ParentId) {
            this.ParentId = ParentId;
        }

        public Object getParentCode() {
            return ParentCode;
        }

        public void setParentCode(Object ParentCode) {
            this.ParentCode = ParentCode;
        }

        public Object getParentName() {
            return ParentName;
        }

        public void setParentName(Object ParentName) {
            this.ParentName = ParentName;
        }

        public String getExceptionId() {
            return ExceptionId;
        }

        public void setExceptionId(String ExceptionId) {
            this.ExceptionId = ExceptionId;
        }

        public String getExceptionCode() {
            return ExceptionCode;
        }

        public void setExceptionCode(String ExceptionCode) {
            this.ExceptionCode = ExceptionCode;
        }

        public String getExceptionName() {
            return ExceptionName;
        }

        public void setExceptionName(String ExceptionName) {
            this.ExceptionName = ExceptionName;
        }

        public int getSeq() {
            return Seq;
        }

        public void setSeq(int Seq) {
            this.Seq = Seq;
        }
    }
}
