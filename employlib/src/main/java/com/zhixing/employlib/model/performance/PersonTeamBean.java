package com.zhixing.employlib.model.performance;

import java.io.Serializable;
import java.util.List;

public class PersonTeamBean  implements Serializable {




        /**
         * IsTeamLeader : false
         * TeamId : c08aeff2-9754-4e54-ad37-655a33390492
         * TeamName : 塑胶生产班组
         * TeamLeaderUserId : 7565cdbd-8025-11e8-b8e8-507b9d9a63b9
         * TeamLeaderUserName : 全小鹏
         */

        private boolean IsTeamLeader;
        private String TeamId;
        private String TeamName;
        private String TeamLeaderUserId;
        private String TeamLeaderUserName;

        public boolean isIsTeamLeader() {
            return IsTeamLeader;
        }

        public void setIsTeamLeader(boolean IsTeamLeader) {
            this.IsTeamLeader = IsTeamLeader;
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

        public String getTeamLeaderUserId() {
            return TeamLeaderUserId;
        }

        public void setTeamLeaderUserId(String TeamLeaderUserId) {
            this.TeamLeaderUserId = TeamLeaderUserId;
        }

        public String getTeamLeaderUserName() {
            return TeamLeaderUserName;
        }

        public void setTeamLeaderUserName(String TeamLeaderUserName) {
            this.TeamLeaderUserName = TeamLeaderUserName;
        }

}
