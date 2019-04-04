package com.zhixing.employlib.model.gardenplot;

import java.util.List;

public class TeamDemeanorBean {


        /**
         * DemeanorId : 186add94-abb57-422e-8f60-ff8db280115c
         * TeamId : 2ea1bb97-52fe-4e96-9ad8-9fa82110d6e0
         * DemeanorTitle : 00237
         * DemeanorContent : 粟昌军
         * Seq : 444
         * CreateTime : 2019-03-29T23:42:30
         * TenantId : 00000000-0000-0000-0000-000000000001
         * TopSeq : null
         * CreateUserCode : 444
         * CreateUserName : 666
         * Files : []
         */

        private String DemeanorId;
        private String TeamId;
        private String DemeanorTitle;
        private String DemeanorContent;
        private int Seq;
        private String CreateTime;
        private String TenantId;
        private Object TopSeq;
        private String CreateUserCode;
        private String CreateUserName;

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    private  String TeamName;
        private List<EmployeeFlie> Files;

        public String getDemeanorId() {
            return DemeanorId;
        }

        public void setDemeanorId(String DemeanorId) {
            this.DemeanorId = DemeanorId;
        }

        public String getTeamId() {
            return TeamId;
        }

        public void setTeamId(String TeamId) {
            this.TeamId = TeamId;
        }

        public String getDemeanorTitle() {
            return DemeanorTitle;
        }

        public void setDemeanorTitle(String DemeanorTitle) {
            this.DemeanorTitle = DemeanorTitle;
        }

        public String getDemeanorContent() {
            return DemeanorContent;
        }

        public void setDemeanorContent(String DemeanorContent) {
            this.DemeanorContent = DemeanorContent;
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

        public String getTenantId() {
            return TenantId;
        }

        public void setTenantId(String TenantId) {
            this.TenantId = TenantId;
        }

        public Object getTopSeq() {
            return TopSeq;
        }

        public void setTopSeq(Object TopSeq) {
            this.TopSeq = TopSeq;
        }

        public String getCreateUserCode() {
            return CreateUserCode;
        }

        public void setCreateUserCode(String CreateUserCode) {
            this.CreateUserCode = CreateUserCode;
        }

        public String getCreateUserName() {
            return CreateUserName;
        }

        public void setCreateUserName(String CreateUserName) {
            this.CreateUserName = CreateUserName;
        }

        public List<EmployeeFlie> getFiles() {
            return Files;
        }

        public void setFiles(List<EmployeeFlie> Files) {
            this.Files = Files;
        }

}
