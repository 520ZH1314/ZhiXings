package com.zhixing.employlib.model.grading;

import java.io.Serializable;
import java.util.List;

public class RankBean  implements Serializable {




        /**
         * GrapeId : 4eb29d45-e6fc-4b58-9591-ad446e803f81
         * GrapeCode : 13
         * GrapeName : 差
         * MinScore : null
         * Symbol : <=
         * MaxScore : 10.0
         * TenantId : 00000000-0000-0000-0000-000000000001
         * ColorPhoto : green.png
         * Description : 得分0-10
         * Seq : 1
         * CreateTime : 2019-03-27T18:44:20
         * IsEnable : 1
         * IsDefaultColor : 0
         * IsAPP : 1
         */

        private String GrapeId;
        private String GrapeCode;
        private String GrapeName;
        private Object MinScore;
        private String Symbol;
        private double MaxScore;
        private String TenantId;
        private String ColorPhoto;
        private String Description;
        private int Seq;
        private String CreateTime;
        private String IsEnable;
        private String IsDefaultColor;
        private String IsAPP;

        public String getGrapeId() {
            return GrapeId;
        }

        public void setGrapeId(String GrapeId) {
            this.GrapeId = GrapeId;
        }

        public String getGrapeCode() {
            return GrapeCode;
        }

        public void setGrapeCode(String GrapeCode) {
            this.GrapeCode = GrapeCode;
        }

        public String getGrapeName() {
            return GrapeName;
        }

        public void setGrapeName(String GrapeName) {
            this.GrapeName = GrapeName;
        }

        public Object getMinScore() {
            return MinScore;
        }

        public void setMinScore(Object MinScore) {
            this.MinScore = MinScore;
        }

        public String getSymbol() {
            return Symbol;
        }

        public void setSymbol(String Symbol) {
            this.Symbol = Symbol;
        }

        public double getMaxScore() {
            return MaxScore;
        }

        public void setMaxScore(double MaxScore) {
            this.MaxScore = MaxScore;
        }

        public String getTenantId() {
            return TenantId;
        }

        public void setTenantId(String TenantId) {
            this.TenantId = TenantId;
        }

        public String getColorPhoto() {
            return ColorPhoto;
        }

        public void setColorPhoto(String ColorPhoto) {
            this.ColorPhoto = ColorPhoto;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
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

        public String getIsEnable() {
            return IsEnable;
        }

        public void setIsEnable(String IsEnable) {
            this.IsEnable = IsEnable;
        }

        public String getIsDefaultColor() {
            return IsDefaultColor;
        }

        public void setIsDefaultColor(String IsDefaultColor) {
            this.IsDefaultColor = IsDefaultColor;
        }

        public String getIsAPP() {
            return IsAPP;
        }

        public void setIsAPP(String IsAPP) {
            this.IsAPP = IsAPP;
        }

}
