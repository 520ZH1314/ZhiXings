package com.zhixing.kpilib.httpbean;

import java.util.List;

public class KpiEntitys {

    public KpiEntitys(int total, List<RowsBean> rows,int i) {
        this.i=i;
        this.total = total;
        this.rows = rows;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    /**
     * rows : [{"ID":"b4ccab13-e22b-11e8-a6f1-8c164533fc3b","TypeCode":"Stock","CycleCode":"Week","Year":"2018","Parameter":"1周","Sort":1,"Data":1,"TenantId":"00000000-0000-0000-0000-000000000001","CreateDate":"2018-11-10T09:57:23"},{"ID":"b01327a6-2acb-42c1-9db7-3c7868102b79","TypeCode":"Stock","CycleCode":"Day","Year":"2018","Parameter":"2018-11-15","Sort":319,"Data":22,"TenantId":"00000000-0000-0000-0000-000000000001","CreateDate":"2018-11-21T14:07:03"},{"ID":"5f04f3c0-3039-40a8-9c40-b3db0022ce4e","TypeCode":"Stock","CycleCode":"Day","Year":"2018","Parameter":"2018-11-16","Sort":320,"Data":11,"TenantId":"00000000-0000-0000-0000-000000000001","CreateDate":"2018-11-21T14:07:03"}]
     * total : 3
     */
    private int i;//当前的index
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
         * ID : b4ccab13-e22b-11e8-a6f1-8c164533fc3b
         * TypeCode : Stock
         * CycleCode : Week
         * Year : 2018
         * Parameter : 1周
         * Sort : 1
         * Data : 1
         * TenantId : 00000000-0000-0000-0000-000000000001
         * CreateDate : 2018-11-10T09:57:23
         */

        private String ID;
        private String TypeCode;
        private String CycleCode;
        private String Year;
        private String Parameter;
        private int Sort;
        private float Data;
        private String TenantId;
        private String CreateDate;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTypeCode() {
            return TypeCode;
        }

        public void setTypeCode(String TypeCode) {
            this.TypeCode = TypeCode;
        }

        public String getCycleCode() {
            return CycleCode;
        }

        public void setCycleCode(String CycleCode) {
            this.CycleCode = CycleCode;
        }

        public String getYear() {
            return Year;
        }

        public void setYear(String Year) {
            this.Year = Year;
        }

        public String getParameter() {
            return Parameter;
        }

        public void setParameter(String Parameter) {
            this.Parameter = Parameter;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public float getData() {
            return Data;
        }

        public void setData(float Data) {
            this.Data = Data;
        }

        public String getTenantId() {
            return TenantId;
        }

        public void setTenantId(String TenantId) {
            this.TenantId = TenantId;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }
    }
}
