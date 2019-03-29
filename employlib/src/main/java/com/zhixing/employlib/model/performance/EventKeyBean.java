package com.zhixing.employlib.model.performance;

import java.util.List;

public class EventKeyBean {
    /**
     * Status : 1
     * Message :
     * Total : 1
     * Page : 1
     * Records : 1
     * Rows : [{"ItemId":"03f00ac5-a510-452e-8e4b-c0e286e6791f","ItemCode":"111","ItemName":"setst1","Score":10,"ItemType":"集体","Description":"","Seq":1,"CreateTime":"2019-01-14T17:26:08","IsEnable":"1","TenantId":"00000000-0000-0000-0000-000000000001","TeamId":"f8cab8dd-c606-458f-8294-588ed5c772b1","TeamName":"塑胶模具班组"}]
     */





        /**
         * ItemId : 03f00ac5-a510-452e-8e4b-c0e286e6791f
         * ItemCode : 111
         * ItemName : setst1
         * Score : 10
         * ItemType : 集体
         * Description :
         * Seq : 1
         * CreateTime : 2019-01-14T17:26:08
         * IsEnable : 1
         * TenantId : 00000000-0000-0000-0000-000000000001
         * TeamId : f8cab8dd-c606-458f-8294-588ed5c772b1
         * TeamName : 塑胶模具班组
         */

        private String ItemId;
        private String ItemCode;
        private String ItemName;
        private int Score;
        private String ItemType;
        private String Description;
        private int Seq;
        private String CreateTime;
        private String IsEnable;
        private String TenantId;
        private String TeamId;
        private String TeamName;

        public String getItemId() {
            return ItemId;
        }

        public void setItemId(String ItemId) {
            this.ItemId = ItemId;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String ItemCode) {
            this.ItemCode = ItemCode;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int Score) {
            this.Score = Score;
        }

        public String getItemType() {
            return ItemType;
        }

        public void setItemType(String ItemType) {
            this.ItemType = ItemType;
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

        public String getTenantId() {
            return TenantId;
        }

        public void setTenantId(String TenantId) {
            this.TenantId = TenantId;
        }

        public String getTeamId() {
            return TeamId;
        }

        public void setTeamId(String TeamId) {
            this.TeamId = TeamId;
        }

        public String getTeamName() {
            return TeamName;
        }

        public void setTeamName(String TeamName) {
            this.TeamName = TeamName;
        }

}
