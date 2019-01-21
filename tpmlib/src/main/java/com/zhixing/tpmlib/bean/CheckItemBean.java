package com.zhixing.tpmlib.bean;

import java.util.List;

public class CheckItemBean {
    /**
     * rows : [{"ItemCode":"000027","Operator":"保养员","Period":1,"Cell":"注塑机台","Position":"塑胶车间","Description":"保持机台干净无油污","StandardImage":"","ItemId":"85d24eef-6cef-40f6-8071-4450121867fe","Paramater":1,"GradeId":"EB0FD77C-2C49-4547-B914-C993336DFBFA","ClassId":"DC100F07-62F5-4C2A-891F-2B7E3562850D","ActuallyImage":null,"Fruit":null,"ExceptionStatus":null,"Seq":1,"MaintananceId":null},{"ItemCode":"000027","Operator":"保养员","Period":1,"Cell":"注塑机台","Position":"塑胶车间","Description":"马达正常运转，电动机无异音","StandardImage":"","ItemId":"6356fa67-fd32-494a-b4c2-10569358fbac","Paramater":1,"GradeId":"EB0FD77C-2C49-4547-B914-C993336DFBFA","ClassId":"DC100F07-62F5-4C2A-891F-2B7E3562850D","ActuallyImage":null,"Fruit":null,"ExceptionStatus":null,"Seq":2,"MaintananceId":null},{"ItemCode":"000054","Operator":"保养员","Period":1,"Cell":"注塑机台","Position":"塑胶车间","Description":"保持锁模射台机构油泵等活动部件有润滑油脂无渣","StandardImage":"","ItemId":"16e05e95-c316-4263-aa17-1871cd9defe3","Paramater":1,"GradeId":"EB0FD77C-2C49-4547-B914-C993336DFBFA","ClassId":"DC100F07-62F5-4C2A-891F-2B7E3562850D","ActuallyImage":null,"Fruit":null,"ExceptionStatus":null,"Seq":16,"MaintananceId":null},{"ItemCode":"000054","Operator":"保养员","Period":1,"Cell":"注塑机台","Position":"塑胶车间","Description":"三大安全装置灵活有效前后安全门正常安全螺丝紧","StandardImage":"","ItemId":"f0e109d3-1a15-47f2-867f-d43ea83f8bdc","Paramater":1,"GradeId":"EB0FD77C-2C49-4547-B914-C993336DFBFA","ClassId":"DC100F07-62F5-4C2A-891F-2B7E3562850D","ActuallyImage":null,"Fruit":null,"ExceptionStatus":null,"Seq":17,"MaintananceId":null},{"ItemCode":"000054","Operator":"保养员","Period":1,"Cell":"注塑机台","Position":"塑胶车间","Description":"检查油泵无异响，油缸油管油路畅通且不漏。","StandardImage":"","ItemId":"0c772a3d-9dbb-491f-a784-5dccc016cb49","Paramater":1,"GradeId":"EB0FD77C-2C49-4547-B914-C993336DFBFA","ClassId":"DC100F07-62F5-4C2A-891F-2B7E3562850D","ActuallyImage":null,"Fruit":null,"ExceptionStatus":null,"Seq":18,"MaintananceId":null},{"ItemCode":"000054","Operator":"保养员","Period":1,"Cell":"注塑机台","Position":"塑胶车间","Description":"电热圈和电偶正常","StandardImage":"","ItemId":"1e093268-a57d-4773-9615-008e79d674e0","Paramater":1,"GradeId":"EB0FD77C-2C49-4547-B914-C993336DFBFA","ClassId":"DC100F07-62F5-4C2A-891F-2B7E3562850D","ActuallyImage":null,"Fruit":null,"ExceptionStatus":null,"Seq":19,"MaintananceId":null},{"ItemCode":"000054","Operator":"保养员","Period":1,"Cell":"注塑机台","Position":"塑胶车间","Description":"冷却水路畅通且不漏","StandardImage":"","ItemId":"71e6d330-2c2f-4cbc-8611-a1bec1025cfd","Paramater":1,"GradeId":"EB0FD77C-2C49-4547-B914-C993336DFBFA","ClassId":"DC100F07-62F5-4C2A-891F-2B7E3562850D","ActuallyImage":null,"Fruit":null,"ExceptionStatus":null,"Seq":20,"MaintananceId":null}]
     * total : 7
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
        private String ExceptionGroupName;
        /**
         * ItemCode : 000027
         * Operator : 保养员
         * Period : 1
         * Cell : 注塑机台
         * Position : 塑胶车间
         * Description : 保持机台干净无油污
         * StandardImage : 
         * ItemId : 85d24eef-6cef-40f6-8071-4450121867fe
         * Paramater : 1
         * GradeId : EB0FD77C-2C49-4547-B914-C993336DFBFA
         * ClassId : DC100F07-62F5-4C2A-891F-2B7E3562850D
         * ActuallyImage : null
         * Fruit : null
         * ExceptionStatus : null
         * Seq : 1
         * MaintananceId : null
         */

        private String ItemCode;
        private String Operator;
        private int Period;
        private String Cell;
        private String Position;
        private String Description;
        private String StandardImage;
        private String ItemId;
        private int Paramater;
        private String GradeId;
        private String ClassId;
        private String ActuallyImage;
        private String Fruit;
        private String ExceptionStatus;
        private int Seq;
        private String MaintananceId;

        public String getExceptionGroupName() {
            return ExceptionGroupName;
        }

        public void setExceptionGroupName(String exceptionGroupName) {
            ExceptionGroupName = exceptionGroupName;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String ItemCode) {
            this.ItemCode = ItemCode;
        }

        public String getOperator() {
            return Operator;
        }

        public void setOperator(String Operator) {
            this.Operator = Operator;
        }

        public int getPeriod() {
            return Period;
        }

        public void setPeriod(int Period) {
            this.Period = Period;
        }

        public String getCell() {
            return Cell;
        }

        public void setCell(String Cell) {
            this.Cell = Cell;
        }

        public String getPosition() {
            return Position;
        }

        public void setPosition(String Position) {
            this.Position = Position;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getStandardImage() {
            return StandardImage;
        }

        public void setStandardImage(String StandardImage) {
            this.StandardImage = StandardImage;
        }

        public String getItemId() {
            return ItemId;
        }

        public void setItemId(String ItemId) {
            this.ItemId = ItemId;
        }

        public int getParamater() {
            return Paramater;
        }

        public void setParamater(int Paramater) {
            this.Paramater = Paramater;
        }

        public String getGradeId() {
            return GradeId;
        }

        public void setGradeId(String GradeId) {
            this.GradeId = GradeId;
        }

        public String getClassId() {
            return ClassId;
        }

        public void setClassId(String ClassId) {
            this.ClassId = ClassId;
        }

        public String getActuallyImage() {
            return ActuallyImage;
        }

        public void setActuallyImage(String ActuallyImage) {
            this.ActuallyImage = ActuallyImage;
        }

        public String getFruit() {
            return Fruit;
        }

        public void setFruit(String Fruit) {
            this.Fruit = Fruit;
        }

        public String getExceptionStatus() {
            return ExceptionStatus;
        }

        public void setExceptionStatus(String ExceptionStatus) {
            this.ExceptionStatus = ExceptionStatus;
        }

        public int getSeq() {
            return Seq;
        }

        public void setSeq(int Seq) {
            this.Seq = Seq;
        }

        public String getMaintananceId() {
            return MaintananceId;
        }

        public void setMaintananceId(String MaintananceId) {
            this.MaintananceId = MaintananceId;
        }
    }
}
