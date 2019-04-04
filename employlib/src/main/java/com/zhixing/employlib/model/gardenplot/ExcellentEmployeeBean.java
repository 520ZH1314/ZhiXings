package com.zhixing.employlib.model.gardenplot;

import java.util.List;

public class ExcellentEmployeeBean {
        /**
         * ExcellentId : 98c65b7f-4a99-444b-96e9-4b33be786c84
         * EvaluationDate : 2019-03
         * TeamId : 2ea1bb97-52fe-4e96-9ad8-9fa82110d6e0
         * UserCode : 00228
         * UserName : 侯玉林
         * ExcellentDeeds : 666
         * Seq : 1
         * CreateTime : 2019-03-30T17:15:58
         * TenantId : 00000000-0000-0000-0000-000000000001
         * ExcellentType : 月度
         * CreateUserCode : test1admin
         * CreateUserName : STD
         * EventScore : 0
         * Files : []
         * UserInfo : {"UserId":"75659f79-8025-11e8-b8e8-507b9d9a63b9","TenantId":"00000000-0000-0000-0000-000000000001","UserCode":"00228","UserSeq":null,"UserName":"侯玉林","IsAdministrator":null,"Password":"81DC9BDB52D04DC20036DBD8313ED055","LineId":"","ShiftId":"","Sex":"男","PhoneNumber":"15917672650","Email":"","HeadShip":"","CardNo":"","RoleName":null,"ConfigJSON":null,"IsEnable":"1","LoginCount":1,"LastLoginDate":"2019-03-31T15:37:48","CreatePerson":null,"CreateDate":"2018-07-05T15:30:51","UpdatePerson":"严成林","UpdateDate":"2019-03-31T15:37:49","Description":"","PhotoURL":"/upload/UserPhoto/201903/79f7c86c-2208-44a2-b19e-8d81e595cb04.png","BPAddress":"0000013","Nationality":"汉族","NativePlace":"江苏省","RegisteredResidence":"","IDCardNo":"320322198612096812","Birthday":"1986-12-09T00:00:00","Ages":31,"Married":"","Party":"","IsSoldier":"0","Residence":"江苏省徐州市沛县","Contacts":"","ContactsPhone":"","MaxEducation":"初中","MaxDegree":"","School":"","Studied":"","WokDate":null,"JoinWorkDate":"2004-04-17T00:00:00","StartWorkDate":"2004-04-17T00:00:00","Recruit":"","ContractType":"","ContractStartDate":"2015-02-20T00:00:00","ContractEndDate":"2088-01-01T00:00:00","EmployeeType":"","GroupType":"","HeadShipGrade":"","LineName":null,"ShiftName":null,"OrganizeId":"c05cfb57-9458-11e8-ba3c-3440b58fff11","OrganizeCode":"TW010101","OrganizeName":"塑胶模具","PositionId":null,"PositionCode":null,"PositionName":null}
         */

        private String ExcellentId;
        private String EvaluationDate;
        private String TeamId;
        private String UserCode;
        private String UserName;
        private String ExcellentDeeds;
        private int Seq;
        private String CreateTime;
        private String TenantId;
        private String ExcellentType;
        private String CreateUserCode;
        private String CreateUserName;
        private int EventScore;
        private UserInfoBean UserInfo;
        private List<EmployeeFlie> Files;

        public String getExcellentId() {
            return ExcellentId;
        }

        public void setExcellentId(String ExcellentId) {
            this.ExcellentId = ExcellentId;
        }

        public String getEvaluationDate() {
            return EvaluationDate;
        }

        public void setEvaluationDate(String EvaluationDate) {
            this.EvaluationDate = EvaluationDate;
        }

        public String getTeamId() {
            return TeamId;
        }

        public void setTeamId(String TeamId) {
            this.TeamId = TeamId;
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

        public String getExcellentDeeds() {
            return ExcellentDeeds;
        }

        public void setExcellentDeeds(String ExcellentDeeds) {
            this.ExcellentDeeds = ExcellentDeeds;
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

        public String getExcellentType() {
            return ExcellentType;
        }

        public void setExcellentType(String ExcellentType) {
            this.ExcellentType = ExcellentType;
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

        public int getEventScore() {
            return EventScore;
        }

        public void setEventScore(int EventScore) {
            this.EventScore = EventScore;
        }

        public UserInfoBean getUserInfo() {
            return UserInfo;
        }

        public void setUserInfo(UserInfoBean UserInfo) {
            this.UserInfo = UserInfo;
        }

        public List<EmployeeFlie> getFiles() {
            return Files;
        }

        public void setFiles(List<EmployeeFlie> Files) {
            this.Files = Files;
        }

        public static class UserInfoBean {
            /**
             * UserId : 75659f79-8025-11e8-b8e8-507b9d9a63b9
             * TenantId : 00000000-0000-0000-0000-000000000001
             * UserCode : 00228
             * UserSeq : null
             * UserName : 侯玉林
             * IsAdministrator : null
             * Password : 81DC9BDB52D04DC20036DBD8313ED055
             * LineId :
             * ShiftId :
             * Sex : 男
             * PhoneNumber : 15917672650
             * Email :
             * HeadShip :
             * CardNo :
             * RoleName : null
             * ConfigJSON : null
             * IsEnable : 1
             * LoginCount : 1
             * LastLoginDate : 2019-03-31T15:37:48
             * CreatePerson : null
             * CreateDate : 2018-07-05T15:30:51
             * UpdatePerson : 严成林
             * UpdateDate : 2019-03-31T15:37:49
             * Description :
             * PhotoURL : /upload/UserPhoto/201903/79f7c86c-2208-44a2-b19e-8d81e595cb04.png
             * BPAddress : 0000013
             * Nationality : 汉族
             * NativePlace : 江苏省
             * RegisteredResidence :
             * IDCardNo : 320322198612096812
             * Birthday : 1986-12-09T00:00:00
             * Ages : 31
             * Married :
             * Party :
             * IsSoldier : 0
             * Residence : 江苏省徐州市沛县
             * Contacts :
             * ContactsPhone :
             * MaxEducation : 初中
             * MaxDegree :
             * School :
             * Studied :
             * WokDate : null
             * JoinWorkDate : 2004-04-17T00:00:00
             * StartWorkDate : 2004-04-17T00:00:00
             * Recruit :
             * ContractType :
             * ContractStartDate : 2015-02-20T00:00:00
             * ContractEndDate : 2088-01-01T00:00:00
             * EmployeeType :
             * GroupType :
             * HeadShipGrade :
             * LineName : null
             * ShiftName : null
             * OrganizeId : c05cfb57-9458-11e8-ba3c-3440b58fff11
             * OrganizeCode : TW010101
             * OrganizeName : 塑胶模具
             * PositionId : null
             * PositionCode : null
             * PositionName : null
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
            private Object CreatePerson;
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
            private String Birthday;
            private int Ages;
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
            private String JoinWorkDate;
            private String StartWorkDate;
            private String Recruit;
            private String ContractType;
            private String ContractStartDate;
            private String ContractEndDate;
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

            public Object getCreatePerson() {
                return CreatePerson;
            }

            public void setCreatePerson(Object CreatePerson) {
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

            public String getBirthday() {
                return Birthday;
            }

            public void setBirthday(String Birthday) {
                this.Birthday = Birthday;
            }

            public int getAges() {
                return Ages;
            }

            public void setAges(int Ages) {
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

            public String getJoinWorkDate() {
                return JoinWorkDate;
            }

            public void setJoinWorkDate(String JoinWorkDate) {
                this.JoinWorkDate = JoinWorkDate;
            }

            public String getStartWorkDate() {
                return StartWorkDate;
            }

            public void setStartWorkDate(String StartWorkDate) {
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

            public String getContractStartDate() {
                return ContractStartDate;
            }

            public void setContractStartDate(String ContractStartDate) {
                this.ContractStartDate = ContractStartDate;
            }

            public String getContractEndDate() {
                return ContractEndDate;
            }

            public void setContractEndDate(String ContractEndDate) {
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
        }

}
