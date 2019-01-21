package com.zhixing.tpmlib.bean;

import java.util.List;

public class EquipmentBean{

    /**
     * rows : [{"EquipmentId":"db52899b-9224-4b4f-b6ab-cd0c11bba51d","EquipmentCode":"999","EquipmentName":"灌装机","SPEC":"","ClassId":"201B627F-D31D-42F9-AF70-02AAD3E9DAD9","LineStationId":"3B4651CA-CE03-4422-B30A-904C471CD772","LineId":"3062bb19-cade-4f60-b3ba-57b08d304667","BuyDate":"0001-01-01T00:00:00","Vendor":"","Price":0,"CreateTime":"2018-03-12T13:49:51","AgeS":null,"Used":null,"Remain":null,"Caretaker":null,"ControlStyle":"","ComPort":null,"EnableDate":"0001-01-01T00:00:00","DisableDate":"0001-01-01T00:00:00","RepairYear":0,"power":0,"Tension":0,"AirPressure":0,"ProductionCapacity":0,"TenantId":"00000000-0000-0000-0000-000000000001","ClassCode":"root","ClassName":"机械设备","FactoryId":"8efdbeec-f0db-4d57-bd21-d5cb823ef67d","FactoryName":"河北新希望天香乳业有限公司","WorkShopId":"36b41d8b-7be8-44f4-a1e3-6866929e83bd","WorkShopCode":"NHF01P01","WorkShopName":"前处理","AreaId":"070baa17-b6c5-4c83-a642-2167b88de9e6","AreaName":"前处理","LineCode":"NHF01P0104","LineName":"收奶间","StationName":"收奶1"}]
     * total : 13
     */

    private int total;
    private List<RowsBean> rows;
    private List<ImageBean> imageBeans;
    public EquipmentBean() {
    }

    public List<ImageBean> getImageBeans() {
        return imageBeans;
    }

    public void setImageBeans(List<ImageBean> imageBeans) {
        this.imageBeans = imageBeans;
    }

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
         * EquipmentId : db52899b-9224-4b4f-b6ab-cd0c11bba51d
         * EquipmentCode : 999
         * EquipmentName : 灌装机
         * SPEC :
         * ClassId : 201B627F-D31D-42F9-AF70-02AAD3E9DAD9
         * LineStationId : 3B4651CA-CE03-4422-B30A-904C471CD772
         * LineId : 3062bb19-cade-4f60-b3ba-57b08d304667
         * BuyDate : 0001-01-01T00:00:00
         * Vendor :
         * Price : 0.0
         * CreateTime : 2018-03-12T13:49:51
         * AgeS : null
         * Used : null
         * Remain : null
         * Caretaker : null
         * ControlStyle :
         * ComPort : null
         * EnableDate : 0001-01-01T00:00:00
         * DisableDate : 0001-01-01T00:00:00
         * RepairYear : 0
         * power : 0.0
         * Tension : 0.0
         * AirPressure : 0.0
         * ProductionCapacity : 0.0
         * TenantId : 00000000-0000-0000-0000-000000000001
         * ClassCode : root
         * ClassName : 机械设备
         * FactoryId : 8efdbeec-f0db-4d57-bd21-d5cb823ef67d
         * FactoryName : 河北新希望天香乳业有限公司
         * WorkShopId : 36b41d8b-7be8-44f4-a1e3-6866929e83bd
         * WorkShopCode : NHF01P01
         * WorkShopName : 前处理
         * AreaId : 070baa17-b6c5-4c83-a642-2167b88de9e6
         * AreaName : 前处理
         * LineCode : NHF01P0104
         * LineName : 收奶间
         * StationName : 收奶1
         * Operator; 点检人
         */

        private String EquipmentId;
        private String Operator;
        private String c;
        private String EquipmentCode;
        private String EquipmentName;
        private String SPEC;
        private String ClassId;
        private String LineStationId;
        private String LineId;
        private String BuyDate;
        private String Vendor;
        private double Price;
        private String FilePath;
        private String CreateTime;
        private Object AgeS;
        private Object Used;
        private Object Remain;
        private Object Caretaker;
        private String ControlStyle;
        private Object ComPort;
        private String EnableDate;
        private String DisableDate;
        private int RepairYear;
        private double power;
        private double Tension;
        private double AirPressure;
        private double ProductionCapacity;
        private String TenantId;
        private String ClassCode;
        private String ClassName;
        private String FactoryId;
        private String FactoryName;
        private String WorkShopId;
        private String WorkShopCode;
        private String WorkShopName;
        private String AreaId;
        private String AreaName;
        private String LineCode;
        private String LineName;
        private String StationName;

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String filePath) {
            FilePath = filePath;
        }

        public String getOperator() {
            return Operator;
        }

        public void setOperator(String operator) {
            Operator = operator;
        }

        public String getEquipmentId() {
            return EquipmentId;
        }

        public void setEquipmentId(String EquipmentId) {
            this.EquipmentId = EquipmentId;
        }

        public String getEquipmentCode() {
            return EquipmentCode;
        }

        public void setEquipmentCode(String EquipmentCode) {
            this.EquipmentCode = EquipmentCode;
        }

        public String getEquipmentName() {
            return EquipmentName;
        }

        public void setEquipmentName(String EquipmentName) {
            this.EquipmentName = EquipmentName;
        }

        public String getSPEC() {
            return SPEC;
        }

        public void setSPEC(String SPEC) {
            this.SPEC = SPEC;
        }

        public String getClassId() {
            return ClassId;
        }

        public void setClassId(String ClassId) {
            this.ClassId = ClassId;
        }

        public String getLineStationId() {
            return LineStationId;
        }

        public void setLineStationId(String LineStationId) {
            this.LineStationId = LineStationId;
        }

        public String getLineId() {
            return LineId;
        }

        public void setLineId(String LineId) {
            this.LineId = LineId;
        }

        public String getBuyDate() {
            return BuyDate;
        }

        public void setBuyDate(String BuyDate) {
            this.BuyDate = BuyDate;
        }

        public String getVendor() {
            return Vendor;
        }

        public void setVendor(String Vendor) {
            this.Vendor = Vendor;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public Object getAgeS() {
            return AgeS;
        }

        public void setAgeS(Object AgeS) {
            this.AgeS = AgeS;
        }

        public Object getUsed() {
            return Used;
        }

        public void setUsed(Object Used) {
            this.Used = Used;
        }

        public Object getRemain() {
            return Remain;
        }

        public void setRemain(Object Remain) {
            this.Remain = Remain;
        }

        public Object getCaretaker() {
            return Caretaker;
        }

        public void setCaretaker(Object Caretaker) {
            this.Caretaker = Caretaker;
        }

        public String getControlStyle() {
            return ControlStyle;
        }

        public void setControlStyle(String ControlStyle) {
            this.ControlStyle = ControlStyle;
        }

        public Object getComPort() {
            return ComPort;
        }

        public void setComPort(Object ComPort) {
            this.ComPort = ComPort;
        }

        public String getEnableDate() {
            return EnableDate;
        }

        public void setEnableDate(String EnableDate) {
            this.EnableDate = EnableDate;
        }

        public String getDisableDate() {
            return DisableDate;
        }

        public void setDisableDate(String DisableDate) {
            this.DisableDate = DisableDate;
        }

        public int getRepairYear() {
            return RepairYear;
        }

        public void setRepairYear(int RepairYear) {
            this.RepairYear = RepairYear;
        }

        public double getPower() {
            return power;
        }

        public void setPower(double power) {
            this.power = power;
        }

        public double getTension() {
            return Tension;
        }

        public void setTension(double Tension) {
            this.Tension = Tension;
        }

        public double getAirPressure() {
            return AirPressure;
        }

        public void setAirPressure(double AirPressure) {
            this.AirPressure = AirPressure;
        }

        public double getProductionCapacity() {
            return ProductionCapacity;
        }

        public void setProductionCapacity(double ProductionCapacity) {
            this.ProductionCapacity = ProductionCapacity;
        }

        public String getTenantId() {
            return TenantId;
        }

        public void setTenantId(String TenantId) {
            this.TenantId = TenantId;
        }

        public String getClassCode() {
            return ClassCode;
        }

        public void setClassCode(String ClassCode) {
            this.ClassCode = ClassCode;
        }

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String ClassName) {
            this.ClassName = ClassName;
        }

        public String getFactoryId() {
            return FactoryId;
        }

        public void setFactoryId(String FactoryId) {
            this.FactoryId = FactoryId;
        }

        public String getFactoryName() {
            return FactoryName;
        }

        public void setFactoryName(String FactoryName) {
            this.FactoryName = FactoryName;
        }

        public String getWorkShopId() {
            return WorkShopId;
        }

        public void setWorkShopId(String WorkShopId) {
            this.WorkShopId = WorkShopId;
        }

        public String getWorkShopCode() {
            return WorkShopCode;
        }

        public void setWorkShopCode(String WorkShopCode) {
            this.WorkShopCode = WorkShopCode;
        }

        public String getWorkShopName() {
            return WorkShopName;
        }

        public void setWorkShopName(String WorkShopName) {
            this.WorkShopName = WorkShopName;
        }

        public String getAreaId() {
            return AreaId;
        }

        public void setAreaId(String AreaId) {
            this.AreaId = AreaId;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public String getLineCode() {
            return LineCode;
        }

        public void setLineCode(String LineCode) {
            this.LineCode = LineCode;
        }

        public String getLineName() {
            return LineName;
        }

        public void setLineName(String LineName) {
            this.LineName = LineName;
        }

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }


        }
    public static class ImageBean{
        private String FilePath;
        private String FileTitle;

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String filePath) {
            FilePath = filePath;
        }

        public String getFileTitle() {
            return FileTitle;
        }

        public void setFileTitle(String fileTitle) {
            FileTitle = fileTitle;
        }
    }

}
