package com.zhixing.kpilib.httpbean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MenuEntity implements Parcelable {

    /**
     * rows : [{"TypeID":"dae146e4-1c09-437a-b9dc-1f1b0cd1f4d6","TypeCode":"public","TypeName":"公共","CycleCode":"Year","TenantId":"00000000-0000-0000-0000-000000000001"},{"TypeID":"8a714439-e22b-11e8-a6f1-8c164533fc3b","TypeCode":"TurnoverRate","TypeName":"周转率","CycleCode":"Month","TenantId":"00000000-0000-0000-0000-000000000001"},{"TypeID":"7b44acc0-e22b-11e8-a6f1-8c164533fc3b","TypeCode":"Stock","TypeName":"库存","CycleCode":"Day","TenantId":"00000000-0000-0000-0000-000000000001"},{"TypeID":"b1a0eb9d-f387-4942-8cc5-f3b736b0fbd4","TypeCode":"test","TypeName":"测试","CycleCode":"Season","TenantId":"00000000-0000-0000-0000-000000000001"},{"TypeID":"588819df-0b9d-4970-9639-d00d9003727a","TypeCode":"admin","TypeName":"管理员","CycleCode":"Week","TenantId":"00000000-0000-0000-0000-000000000001"}]
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

    public void setRows(ArrayList<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * TypeID : dae146e4-1c09-437a-b9dc-1f1b0cd1f4d6
         * TypeCode : public
         * TypeName : 公共
         * CycleCode : Year
         * TenantId : 00000000-0000-0000-0000-000000000001
         */
        private float TargetValue ;

        public float getTargetValue() {
            return TargetValue;
        }

        public void setTargetValue(float targetValue) {
            TargetValue = targetValue;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String unit) {
            Unit = unit;
        }

        private String  Unit;
        private String TypeID;
        private String TypeCode;
        private String TypeName;
        private String CycleCode;
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

    public MenuEntity() {
    }

    protected MenuEntity(Parcel in) {
        this.total = in.readInt();
        this.rows = new ArrayList<RowsBean>();
        in.readList(this.rows, RowsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MenuEntity> CREATOR = new Parcelable.Creator<MenuEntity>() {
        @Override
        public MenuEntity createFromParcel(Parcel source) {
            return new MenuEntity(source);
        }

        @Override
        public MenuEntity[] newArray(int size) {
            return new MenuEntity[size];
        }
    };
}
