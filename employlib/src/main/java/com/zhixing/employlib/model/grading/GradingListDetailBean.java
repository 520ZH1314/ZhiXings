package com.zhixing.employlib.model.grading;

import java.io.Serializable;
import java.util.List;

public class GradingListDetailBean implements Serializable {


    /**
     * status : success
     * message : 保存状态成功
     * rows : [{"userInfo":{"UserId":"62c45ed1-503d-7805-47f3-31137809d5a5","TenantId":"00000000-0000-0000-0000-000000000001","UserCode":"06153","UserSeq":null,"UserName":"彭敏","IsAdministrator":null,"Password":"81DC9BDB52D04DC20036DBD8313ED055","LineId":"","ShiftId":"","Sex":"女","PhoneNumber":"15019077735","Email":"","HeadShip":"","CardNo":"","RoleName":null,"ConfigJSON":null,"IsEnable":"1","LoginCount":1,"LastLoginDate":"2019-03-18T16:25:47","CreatePerson":"全小鹏","CreateDate":"2018-10-09T15:03:51","UpdatePerson":"数本云管理员","UpdateDate":"2019-03-18T16:25:47","Description":"","PhotoURL":"/upload/UserPhoto/201810/ff84e76f-0e14-437d-b31b-48fbb9fbd3e6.jpg","BPAddress":"","Nationality":"汉族","NativePlace":"四川","RegisteredResidence":"农村户口","IDCardNo":"","Birthday":null,"Ages":null,"Married":"","Party":"","IsSoldier":"0","Residence":"","Contacts":"","ContactsPhone":"","MaxEducation":"","MaxDegree":"","School":"","Studied":"","WokDate":null,"JoinWorkDate":null,"StartWorkDate":null,"Recruit":"","ContractType":"","ContractStartDate":null,"ContractEndDate":null,"EmployeeType":"","GroupType":"","HeadShipGrade":"","LineName":null,"ShiftName":null,"OrganizeId":"c6482b1e-9458-11e8-ba3c-3440b58fff11","OrganizeCode":"TW010102","OrganizeName":"塑胶生产","PositionId":null,"PositionCode":null,"PositionName":null,"EventCount":2,"Score":5}},{"eventInfo":[{"EventId":"12345604","UserCode":"06153","UserName":"彭敏","TeamId":"c08aeff2-9754-4e54-ad37-655a33390492","TeamName":"","Description":"","TeamLeaderId":"7565cdbd-8025-11e8-b8e8-507b9d9a63b9","TeamLeaderName":"全小鹏","ItemId":"休息","ItemName":"休息","Attributer":"","Score":null,"ShiftId":"","ShiftName":"","ShiftDate":"2019-03-27T00:00:00","TenantId":"00000000-0000-0000-0000-000000000001","CreateTime":"2019-03-27T15:40:35"},{"EventId":"12345603","UserCode":"06153","UserName":"彭敏","TeamId":"c08aeff2-9754-4e54-ad37-655a33390492","TeamName":"","Description":"","TeamLeaderId":"7565cdbd-8025-11e8-b8e8-507b9d9a63b9","TeamLeaderName":"全小鹏","ItemId":"3ef7b2fd-ea70-4301-89f4-c886e32df6f9","ItemName":"正常出勤","Attributer":"正面","Score":5,"ShiftId":"","ShiftName":"","ShiftDate":"2019-03-27T00:00:00","TenantId":"00000000-0000-0000-0000-000000000001","CreateTime":"2019-03-27T16:03:29"}]}]
     */




        /**
         * userInfo : {"UserId":"62c45ed1-503d-7805-47f3-31137809d5a5","TenantId":"00000000-0000-0000-0000-000000000001","UserCode":"06153","UserSeq":null,"UserName":"彭敏","IsAdministrator":null,"Password":"81DC9BDB52D04DC20036DBD8313ED055","LineId":"","ShiftId":"","Sex":"女","PhoneNumber":"15019077735","Email":"","HeadShip":"","CardNo":"","RoleName":null,"ConfigJSON":null,"IsEnable":"1","LoginCount":1,"LastLoginDate":"2019-03-18T16:25:47","CreatePerson":"全小鹏","CreateDate":"2018-10-09T15:03:51","UpdatePerson":"数本云管理员","UpdateDate":"2019-03-18T16:25:47","Description":"","PhotoURL":"/upload/UserPhoto/201810/ff84e76f-0e14-437d-b31b-48fbb9fbd3e6.jpg","BPAddress":"","Nationality":"汉族","NativePlace":"四川","RegisteredResidence":"农村户口","IDCardNo":"","Birthday":null,"Ages":null,"Married":"","Party":"","IsSoldier":"0","Residence":"","Contacts":"","ContactsPhone":"","MaxEducation":"","MaxDegree":"","School":"","Studied":"","WokDate":null,"JoinWorkDate":null,"StartWorkDate":null,"Recruit":"","ContractType":"","ContractStartDate":null,"ContractEndDate":null,"EmployeeType":"","GroupType":"","HeadShipGrade":"","LineName":null,"ShiftName":null,"OrganizeId":"c6482b1e-9458-11e8-ba3c-3440b58fff11","OrganizeCode":"TW010102","OrganizeName":"塑胶生产","PositionId":null,"PositionCode":null,"PositionName":null,"EventCount":2,"Score":5}
         * eventInfo : [{"EventId":"12345604","UserCode":"06153","UserName":"彭敏","TeamId":"c08aeff2-9754-4e54-ad37-655a33390492","TeamName":"","Description":"","TeamLeaderId":"7565cdbd-8025-11e8-b8e8-507b9d9a63b9","TeamLeaderName":"全小鹏","ItemId":"休息","ItemName":"休息","Attributer":"","Score":null,"ShiftId":"","ShiftName":"","ShiftDate":"2019-03-27T00:00:00","TenantId":"00000000-0000-0000-0000-000000000001","CreateTime":"2019-03-27T15:40:35"},{"EventId":"12345603","UserCode":"06153","UserName":"彭敏","TeamId":"c08aeff2-9754-4e54-ad37-655a33390492","TeamName":"","Description":"","TeamLeaderId":"7565cdbd-8025-11e8-b8e8-507b9d9a63b9","TeamLeaderName":"全小鹏","ItemId":"3ef7b2fd-ea70-4301-89f4-c886e32df6f9","ItemName":"正常出勤","Attributer":"正面","Score":5,"ShiftId":"","ShiftName":"","ShiftDate":"2019-03-27T00:00:00","TenantId":"00000000-0000-0000-0000-000000000001","CreateTime":"2019-03-27T16:03:29"}]
         */

        private UserInfoBean userInfo;
        private List<EventInfoBean> eventInfo;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public List<EventInfoBean> getEventInfo() {
            return eventInfo;
        }

        public void setEventInfo(List<EventInfoBean> eventInfo) {
            this.eventInfo = eventInfo;
        }

        public static class UserInfoBean {
            /**
             * UserId : 62c45ed1-503d-7805-47f3-31137809d5a5
             * TenantId : 00000000-0000-0000-0000-000000000001
             * UserCode : 06153
             * UserSeq : null
             * UserName : 彭敏
             * IsAdministrator : null
             * Password : 81DC9BDB52D04DC20036DBD8313ED055
             * LineId :
             * ShiftId :
             * Sex : 女
             * PhoneNumber : 15019077735
             * Email :
             * HeadShip :
             * CardNo :
             * RoleName : null
             * ConfigJSON : null
             * IsEnable : 1
             * LoginCount : 1
             * LastLoginDate : 2019-03-18T16:25:47
             * CreatePerson : 全小鹏
             * CreateDate : 2018-10-09T15:03:51
             * UpdatePerson : 数本云管理员
             * UpdateDate : 2019-03-18T16:25:47
             * Description :
             * PhotoURL : /upload/UserPhoto/201810/ff84e76f-0e14-437d-b31b-48fbb9fbd3e6.jpg
             * BPAddress :
             * Nationality : 汉族
             * NativePlace : 四川
             * RegisteredResidence : 农村户口
             * IDCardNo :
             * Birthday : null
             * Ages : null
             * Married :
             * Party :
             * IsSoldier : 0
             * Residence :
             * Contacts :
             * ContactsPhone :
             * MaxEducation :
             * MaxDegree :
             * School :
             * Studied :
             * WokDate : null
             * JoinWorkDate : null
             * StartWorkDate : null
             * Recruit :
             * ContractType :
             * ContractStartDate : null
             * ContractEndDate : null
             * EmployeeType :
             * GroupType :
             * HeadShipGrade :
             * LineName : null
             * ShiftName : null
             * OrganizeId : c6482b1e-9458-11e8-ba3c-3440b58fff11
             * OrganizeCode : TW010102
             * OrganizeName : 塑胶生产
             * PositionId : null
             * PositionCode : null
             * PositionName : null
             * EventCount : 2
             * Score : 5.0
             */

            private String UserId;
            private String TenantId;
            private String UserCode;
            private Object UserSeq;
            private String UserName;
            private Object IsAdministrator;
            private String Password;
            private String LineId;
            private String ShiftId;
            private String Sex;
            private String PhoneNumber;
            private String Email;
            private String HeadShip;
            private String CardNo;
            private Object RoleName;
            private Object ConfigJSON;
            private String IsEnable;
            private int LoginCount;
            private String LastLoginDate;
            private String CreatePerson;
            private String CreateDate;
            private String UpdatePerson;
            private String UpdateDate;
            private String Description;
            private String PhotoURL;
            private String BPAddress;
            private String Nationality;
            private String NativePlace;
            private String RegisteredResidence;
            private String IDCardNo;
            private Object Birthday;
            private Object Ages;
            private String Married;
            private String Party;
            private String IsSoldier;
            private String Residence;
            private String Contacts;
            private String ContactsPhone;
            private String MaxEducation;
            private String MaxDegree;
            private String School;
            private String Studied;
            private Object WokDate;
            private Object JoinWorkDate;
            private Object StartWorkDate;
            private String Recruit;
            private String ContractType;
            private Object ContractStartDate;
            private Object ContractEndDate;
            private String EmployeeType;
            private String GroupType;
            private String HeadShipGrade;
            private Object LineName;
            private Object ShiftName;
            private String OrganizeId;
            private String OrganizeCode;
            private String OrganizeName;
            private Object PositionId;
            private Object PositionCode;
            private String PositionName;
            private int EventCount;
            private double Score;

            public String getUserId() {
                return UserId;
            }

            public void setUserId(String UserId) {
                this.UserId = UserId;
            }

            public String getTenantId() {
                return TenantId;
            }

            public void setTenantId(String TenantId) {
                this.TenantId = TenantId;
            }

            public String getUserCode() {
                return UserCode;
            }

            public void setUserCode(String UserCode) {
                this.UserCode = UserCode;
            }

            public Object getUserSeq() {
                return UserSeq;
            }

            public void setUserSeq(Object UserSeq) {
                this.UserSeq = UserSeq;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public Object getIsAdministrator() {
                return IsAdministrator;
            }

            public void setIsAdministrator(Object IsAdministrator) {
                this.IsAdministrator = IsAdministrator;
            }

            public String getPassword() {
                return Password;
            }

            public void setPassword(String Password) {
                this.Password = Password;
            }

            public String getLineId() {
                return LineId;
            }

            public void setLineId(String LineId) {
                this.LineId = LineId;
            }

            public String getShiftId() {
                return ShiftId;
            }

            public void setShiftId(String ShiftId) {
                this.ShiftId = ShiftId;
            }

            public String getSex() {
                return Sex;
            }

            public void setSex(String Sex) {
                this.Sex = Sex;
            }

            public String getPhoneNumber() {
                return PhoneNumber;
            }

            public void setPhoneNumber(String PhoneNumber) {
                this.PhoneNumber = PhoneNumber;
            }

            public String getEmail() {
                return Email;
            }

            public void setEmail(String Email) {
                this.Email = Email;
            }

            public String getHeadShip() {
                return HeadShip;
            }

            public void setHeadShip(String HeadShip) {
                this.HeadShip = HeadShip;
            }

            public String getCardNo() {
                return CardNo;
            }

            public void setCardNo(String CardNo) {
                this.CardNo = CardNo;
            }

            public Object getRoleName() {
                return RoleName;
            }

            public void setRoleName(Object RoleName) {
                this.RoleName = RoleName;
            }

            public Object getConfigJSON() {
                return ConfigJSON;
            }

            public void setConfigJSON(Object ConfigJSON) {
                this.ConfigJSON = ConfigJSON;
            }

            public String getIsEnable() {
                return IsEnable;
            }

            public void setIsEnable(String IsEnable) {
                this.IsEnable = IsEnable;
            }

            public int getLoginCount() {
                return LoginCount;
            }

            public void setLoginCount(int LoginCount) {
                this.LoginCount = LoginCount;
            }

            public String getLastLoginDate() {
                return LastLoginDate;
            }

            public void setLastLoginDate(String LastLoginDate) {
                this.LastLoginDate = LastLoginDate;
            }

            public String getCreatePerson() {
                return CreatePerson;
            }

            public void setCreatePerson(String CreatePerson) {
                this.CreatePerson = CreatePerson;
            }

            public String getCreateDate() {
                return CreateDate;
            }

            public void setCreateDate(String CreateDate) {
                this.CreateDate = CreateDate;
            }

            public String getUpdatePerson() {
                return UpdatePerson;
            }

            public void setUpdatePerson(String UpdatePerson) {
                this.UpdatePerson = UpdatePerson;
            }

            public String getUpdateDate() {
                return UpdateDate;
            }

            public void setUpdateDate(String UpdateDate) {
                this.UpdateDate = UpdateDate;
            }

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public String getPhotoURL() {
                return PhotoURL;
            }

            public void setPhotoURL(String PhotoURL) {
                this.PhotoURL = PhotoURL;
            }

            public String getBPAddress() {
                return BPAddress;
            }

            public void setBPAddress(String BPAddress) {
                this.BPAddress = BPAddress;
            }

            public String getNationality() {
                return Nationality;
            }

            public void setNationality(String Nationality) {
                this.Nationality = Nationality;
            }

            public String getNativePlace() {
                return NativePlace;
            }

            public void setNativePlace(String NativePlace) {
                this.NativePlace = NativePlace;
            }

            public String getRegisteredResidence() {
                return RegisteredResidence;
            }

            public void setRegisteredResidence(String RegisteredResidence) {
                this.RegisteredResidence = RegisteredResidence;
            }

            public String getIDCardNo() {
                return IDCardNo;
            }

            public void setIDCardNo(String IDCardNo) {
                this.IDCardNo = IDCardNo;
            }

            public Object getBirthday() {
                return Birthday;
            }

            public void setBirthday(Object Birthday) {
                this.Birthday = Birthday;
            }

            public Object getAges() {
                return Ages;
            }

            public void setAges(Object Ages) {
                this.Ages = Ages;
            }

            public String getMarried() {
                return Married;
            }

            public void setMarried(String Married) {
                this.Married = Married;
            }

            public String getParty() {
                return Party;
            }

            public void setParty(String Party) {
                this.Party = Party;
            }

            public String getIsSoldier() {
                return IsSoldier;
            }

            public void setIsSoldier(String IsSoldier) {
                this.IsSoldier = IsSoldier;
            }

            public String getResidence() {
                return Residence;
            }

            public void setResidence(String Residence) {
                this.Residence = Residence;
            }

            public String getContacts() {
                return Contacts;
            }

            public void setContacts(String Contacts) {
                this.Contacts = Contacts;
            }

            public String getContactsPhone() {
                return ContactsPhone;
            }

            public void setContactsPhone(String ContactsPhone) {
                this.ContactsPhone = ContactsPhone;
            }

            public String getMaxEducation() {
                return MaxEducation;
            }

            public void setMaxEducation(String MaxEducation) {
                this.MaxEducation = MaxEducation;
            }

            public String getMaxDegree() {
                return MaxDegree;
            }

            public void setMaxDegree(String MaxDegree) {
                this.MaxDegree = MaxDegree;
            }

            public String getSchool() {
                return School;
            }

            public void setSchool(String School) {
                this.School = School;
            }

            public String getStudied() {
                return Studied;
            }

            public void setStudied(String Studied) {
                this.Studied = Studied;
            }

            public Object getWokDate() {
                return WokDate;
            }

            public void setWokDate(Object WokDate) {
                this.WokDate = WokDate;
            }

            public Object getJoinWorkDate() {
                return JoinWorkDate;
            }

            public void setJoinWorkDate(Object JoinWorkDate) {
                this.JoinWorkDate = JoinWorkDate;
            }

            public Object getStartWorkDate() {
                return StartWorkDate;
            }

            public void setStartWorkDate(Object StartWorkDate) {
                this.StartWorkDate = StartWorkDate;
            }

            public String getRecruit() {
                return Recruit;
            }

            public void setRecruit(String Recruit) {
                this.Recruit = Recruit;
            }

            public String getContractType() {
                return ContractType;
            }

            public void setContractType(String ContractType) {
                this.ContractType = ContractType;
            }

            public Object getContractStartDate() {
                return ContractStartDate;
            }

            public void setContractStartDate(Object ContractStartDate) {
                this.ContractStartDate = ContractStartDate;
            }

            public Object getContractEndDate() {
                return ContractEndDate;
            }

            public void setContractEndDate(Object ContractEndDate) {
                this.ContractEndDate = ContractEndDate;
            }

            public String getEmployeeType() {
                return EmployeeType;
            }

            public void setEmployeeType(String EmployeeType) {
                this.EmployeeType = EmployeeType;
            }

            public String getGroupType() {
                return GroupType;
            }

            public void setGroupType(String GroupType) {
                this.GroupType = GroupType;
            }

            public String getHeadShipGrade() {
                return HeadShipGrade;
            }

            public void setHeadShipGrade(String HeadShipGrade) {
                this.HeadShipGrade = HeadShipGrade;
            }

            public Object getLineName() {
                return LineName;
            }

            public void setLineName(Object LineName) {
                this.LineName = LineName;
            }

            public Object getShiftName() {
                return ShiftName;
            }

            public void setShiftName(Object ShiftName) {
                this.ShiftName = ShiftName;
            }

            public String getOrganizeId() {
                return OrganizeId;
            }

            public void setOrganizeId(String OrganizeId) {
                this.OrganizeId = OrganizeId;
            }

            public String getOrganizeCode() {
                return OrganizeCode;
            }

            public void setOrganizeCode(String OrganizeCode) {
                this.OrganizeCode = OrganizeCode;
            }

            public String getOrganizeName() {
                return OrganizeName;
            }

            public void setOrganizeName(String OrganizeName) {
                this.OrganizeName = OrganizeName;
            }

            public Object getPositionId() {
                return PositionId;
            }

            public void setPositionId(Object PositionId) {
                this.PositionId = PositionId;
            }

            public Object getPositionCode() {
                return PositionCode;
            }

            public void setPositionCode(Object PositionCode) {
                this.PositionCode = PositionCode;
            }

            public String getPositionName() {
                return PositionName;
            }

            public void setPositionName(String PositionName) {
                this.PositionName = PositionName;
            }

            public int getEventCount() {
                return EventCount;
            }

            public void setEventCount(int EventCount) {
                this.EventCount = EventCount;
            }

            public double getScore() {
                return Score;
            }

            public void setScore(double Score) {
                this.Score = Score;
            }
        }

        public static class EventInfoBean {
            /**
             * EventId : 12345604
             * UserCode : 06153
             * UserName : 彭敏
             * TeamId : c08aeff2-9754-4e54-ad37-655a33390492
             * TeamName :
             * Description :
             * TeamLeaderId : 7565cdbd-8025-11e8-b8e8-507b9d9a63b9
             * TeamLeaderName : 全小鹏
             * ItemId : 休息
             * ItemName : 休息
             * Attributer :
             * Score : null
             * ShiftId :
             * ShiftName :
             * ShiftDate : 2019-03-27T00:00:00
             * TenantId : 00000000-0000-0000-0000-000000000001
             * CreateTime : 2019-03-27T15:40:35
             */

            private String EventId;
            private String UserCode;
            private String UserName;
            private String TeamId;
            private String TeamName;
            private String Description;
            private String TeamLeaderId;
            private String TeamLeaderName;
            private String ItemId;
            private String ItemName;
            private String Attributer;
            private int Score;
            private String ShiftId;
            private String ShiftName;
            private String ShiftDate;
            private String TenantId;
            private String CreateTime;

            public String getEventId() {
                return EventId;
            }

            public void setEventId(String EventId) {
                this.EventId = EventId;
            }

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

            public String getDescription() {
                return Description;
            }

            public void setDescription(String Description) {
                this.Description = Description;
            }

            public String getTeamLeaderId() {
                return TeamLeaderId;
            }

            public void setTeamLeaderId(String TeamLeaderId) {
                this.TeamLeaderId = TeamLeaderId;
            }

            public String getTeamLeaderName() {
                return TeamLeaderName;
            }

            public void setTeamLeaderName(String TeamLeaderName) {
                this.TeamLeaderName = TeamLeaderName;
            }

            public String getItemId() {
                return ItemId;
            }

            public void setItemId(String ItemId) {
                this.ItemId = ItemId;
            }

            public String getItemName() {
                return ItemName;
            }

            public void setItemName(String ItemName) {
                this.ItemName = ItemName;
            }

            public String getAttributer() {
                return Attributer;
            }

            public void setAttributer(String Attributer) {
                this.Attributer = Attributer;
            }

            public int getScore() {
                return Score;
            }

            public void setScore(int Score) {
                this.Score = Score;
            }

            public String getShiftId() {
                return ShiftId;
            }

            public void setShiftId(String ShiftId) {
                this.ShiftId = ShiftId;
            }

            public String getShiftName() {
                return ShiftName;
            }

            public void setShiftName(String ShiftName) {
                this.ShiftName = ShiftName;
            }

            public String getShiftDate() {
                return ShiftDate;
            }

            public void setShiftDate(String ShiftDate) {
                this.ShiftDate = ShiftDate;
            }

            public String getTenantId() {
                return TenantId;
            }

            public void setTenantId(String TenantId) {
                this.TenantId = TenantId;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }
        }

}
