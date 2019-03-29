package com.zhixing.employlib.model.performance;

import java.io.Serializable;
import java.util.List;

public class MonthPerformanceBean implements Serializable {


    /**
     * returndayInfo : {"userInfo":[{"UserCode":"06153","UserName":"彭敏","TeamLeaderName":"全小鹏","Score":5,"Seq":5}],"teamInfo":[{"UserCode":"04405","UserName":"纪真真","Score":5},{"UserCode":"03318","UserName":"赵文杰","Score":5},{"UserCode":"06111","UserName":"莫龙娇","Score":5},{"UserCode":"05881","UserName":"候宁宁","Score":5},{"UserCode":"06153","UserName":"彭敏","Score":5}]}
     */

    private ReturndayInfoBean returndayInfo;

    public ReturndayInfoBean getReturndayInfo() {
        return returndayInfo;
    }

    public void setReturndayInfo(ReturndayInfoBean returndayInfo) {
        this.returndayInfo = returndayInfo;
    }

    public static class ReturndayInfoBean  implements Serializable{
        private List<UserInfoBean> userInfo;
        private List<TeamInfoBean> teamInfo;

        public List<UserInfoBean> getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(List<UserInfoBean> userInfo) {
            this.userInfo = userInfo;
        }

        public List<TeamInfoBean> getTeamInfo() {
            return teamInfo;
        }

        public void setTeamInfo(List<TeamInfoBean> teamInfo) {
            this.teamInfo = teamInfo;
        }

        public static class UserInfoBean  implements Serializable{
            /**
             * UserCode : 06153
             * UserName : 彭敏
             * TeamLeaderName : 全小鹏
             * Score : 5
             * Seq : 5
             */

            private String UserCode;
            private String UserName;
            private String TeamLeaderName;
            private int Score;
            private int Seq;

            public String getUserCode() {
                return UserCode;
            }

            public void setUserCode(String UserCode) {
                this.UserCode = UserCode;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public String getTeamLeaderName() {
                return TeamLeaderName;
            }

            public void setTeamLeaderName(String TeamLeaderName) {
                this.TeamLeaderName = TeamLeaderName;
            }

            public int getScore() {
                return Score;
            }

            public void setScore(int Score) {
                this.Score = Score;
            }

            public int getSeq() {
                return Seq;
            }

            public void setSeq(int Seq) {
                this.Seq = Seq;
            }
        }

        public static class TeamInfoBean  implements Serializable{
            /**
             * UserCode : 04405
             * UserName : 纪真真
             * Score : 5
             */

            private String UserCode;
            private String UserName;
            private int Score;

            public String getUserCode() {
                return UserCode;
            }

            public void setUserCode(String UserCode) {
                this.UserCode = UserCode;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public int getScore() {
                return Score;
            }

            public void setScore(int Score) {
                this.Score = Score;
            }
        }
    }
}

