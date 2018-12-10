package com.zhixing.kpilib.httpbean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MenuEntitys implements Parcelable {


    /**
     * rows : [{"TypeID":"dae146e4-1c09-437a-b9dc-1f1b0cd1f4d6","TypeCode":"public","TypeName":"公共","CycleCode":"Year","TargetValue":1,"Unit":"元","TenantId":"00000000-0000-0000-0000-000000000001"},{"TypeID":"8a714439-e22b-11e8-a6f1-8c164533fc3b","TypeCode":"TurnoverRate","TypeName":"周转率","CycleCode":"Month","TargetValue":2,"Unit":"个","TenantId":"00000000-0000-0000-0000-000000000001"},{"TypeID":"7b44acc0-e22b-11e8-a6f1-8c164533fc3b","TypeCode":"Stock","TypeName":"库存","CycleCode":"Day","TargetValue":3,"Unit":"吨","TenantId":"00000000-0000-0000-0000-000000000001"},{"TypeID":"b1a0eb9d-f387-4942-8cc5-f3b736b0fbd4","TypeCode":"test","TypeName":"测试","CycleCode":"Season","TargetValue":4,"Unit":"位","TenantId":"00000000-0000-0000-0000-000000000001"},{"TypeID":"588819df-0b9d-4970-9639-d00d9003727a","TypeCode":"admin","TypeName":"管理员","CycleCode":"Week","TargetValue":5,"Unit":"斤","TenantId":"00000000-0000-0000-0000-000000000001"}]
     * total : 5
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
         * TypeID : dae146e4-1c09-437a-b9dc-1f1b0cd1f4d6
         * TypeCode : public
         * TypeName : 公共
         * CycleCode : Year
         * TargetValue : 1.0
         * Unit : 元
         * TenantId : 00000000-0000-0000-0000-000000000001
         */

        private String TypeID;
        private String TypeCode;
        private String TypeName;
        private String CycleCode;
        private double TargetValue;
        private String Unit;
        private String TenantId;

        public String getTypeID() {
            return TypeID;
        }

        public void setTypeID(String TypeID) {
            this.TypeID = TypeID;
        }

        public String getTypeCode() {
            return TypeCode;
        }

        public void setTypeCode(String TypeCode) {
            this.TypeCode = TypeCode;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
        }

        public String getCycleCode() {
            return CycleCode;
        }

        public void setCycleCode(String CycleCode) {
            this.CycleCode = CycleCode;
        }

        public double getTargetValue() {
            return TargetValue;
        }

        public void setTargetValue(double TargetValue) {
            this.TargetValue = TargetValue;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String getTenantId() {
            return TenantId;
        }

        public void setTenantId(String TenantId) {
            this.TenantId = TenantId;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeList(this.rows);
    }

    public MenuEntitys() {
    }

    protected MenuEntitys(Parcel in) {
        this.total = in.readInt();
        this.rows = new ArrayList<RowsBean>();
        in.readList(this.rows, RowsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MenuEntitys> CREATOR = new Parcelable.Creator<MenuEntitys>() {
        @Override
        public MenuEntitys createFromParcel(Parcel source) {
            return new MenuEntitys(source);
        }

        @Override
        public MenuEntitys[] newArray(int size) {
            return new MenuEntitys[size];
        }
    };
}
