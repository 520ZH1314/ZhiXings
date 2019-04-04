package com.zhixing.employlib.model.recrui;

import java.util.List;

public class RecruitListBean {


    /**
     * status : success
     * message : 读取信息成功
     * rows : [{"RecruitId":"123456000","JobPost":"销售助理","JobSkills":"2-5年经验|熟悉硬件|","JobDetail":"产品销售实施","JobRequire":"熟悉销售技巧和流程","JobSalaryMin":5000,"JobSalarMax":50000,"OffDate":"2020-05-01T00:00:00","CreateDate":"2019-04-02T15:51:27","HandleId":"6663fa4b-22f0-a200-a4fd-6b747e09cfdb","HandleName":"曾志亮","TenantId":"00000000-0000-0000-0000-000000000001","ApplyId":"456789001","ApplyType":0,"State":1,"StateName":"已查看","ApplyDate":"2019-04-02T15:51:27"}]
     */


        /**
         * RecruitId : 123456000
         * JobPost : 销售助理
         * JobSkills : 2-5年经验|熟悉硬件|
         * JobDetail : 产品销售实施
         * JobRequire : 熟悉销售技巧和流程
         * JobSalaryMin : 5000
         * JobSalarMax : 50000
         * OffDate : 2020-05-01T00:00:00
         * CreateDate : 2019-04-02T15:51:27
         * HandleId : 6663fa4b-22f0-a200-a4fd-6b747e09cfdb
         * HandleName : 曾志亮
         * TenantId : 00000000-0000-0000-0000-000000000001
         * ApplyId : 456789001
         * ApplyType : 0
         * State : 1
         * StateName : 已查看
         * ApplyDate : 2019-04-02T15:51:27
         */

        private String RecruitId;
        private String JobPost;
        private String JobSkills;
        private String JobDetail;
        private String JobRequire;
        private int JobSalaryMin;
        private int JobSalarMax;
        private String OffDate;
        private String CreateDate;
        private String HandleId;
        private String HandleName;
        private String TenantId;
        private String ApplyId;
        private int ApplyType;
        private int State;
        private String StateName;
        private String ApplyDate;

        public String getRecruitId() {
            return RecruitId;
        }

        public void setRecruitId(String RecruitId) {
            this.RecruitId = RecruitId;
        }

        public String getJobPost() {
            return JobPost;
        }

        public void setJobPost(String JobPost) {
            this.JobPost = JobPost;
        }

        public String getJobSkills() {
            return JobSkills;
        }

        public void setJobSkills(String JobSkills) {
            this.JobSkills = JobSkills;
        }

        public String getJobDetail() {
            return JobDetail;
        }

        public void setJobDetail(String JobDetail) {
            this.JobDetail = JobDetail;
        }

        public String getJobRequire() {
            return JobRequire;
        }

        public void setJobRequire(String JobRequire) {
            this.JobRequire = JobRequire;
        }

        public int getJobSalaryMin() {
            return JobSalaryMin;
        }

        public void setJobSalaryMin(int JobSalaryMin) {
            this.JobSalaryMin = JobSalaryMin;
        }

        public int getJobSalarMax() {
            return JobSalarMax;
        }

        public void setJobSalarMax(int JobSalarMax) {
            this.JobSalarMax = JobSalarMax;
        }

        public String getOffDate() {
            return OffDate;
        }

        public void setOffDate(String OffDate) {
            this.OffDate = OffDate;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getHandleId() {
            return HandleId;
        }

        public void setHandleId(String HandleId) {
            this.HandleId = HandleId;
        }

        public String getHandleName() {
            return HandleName;
        }

        public void setHandleName(String HandleName) {
            this.HandleName = HandleName;
        }

        public String getTenantId() {
            return TenantId;
        }

        public void setTenantId(String TenantId) {
            this.TenantId = TenantId;
        }

        public String getApplyId() {
            return ApplyId;
        }

        public void setApplyId(String ApplyId) {
            this.ApplyId = ApplyId;
        }

        public int getApplyType() {
            return ApplyType;
        }

        public void setApplyType(int ApplyType) {
            this.ApplyType = ApplyType;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getStateName() {
            return StateName;
        }

        public void setStateName(String StateName) {
            this.StateName = StateName;
        }

        public String getApplyDate() {
            return ApplyDate;
        }

        public void setApplyDate(String ApplyDate) {
            this.ApplyDate = ApplyDate;
        }

}
