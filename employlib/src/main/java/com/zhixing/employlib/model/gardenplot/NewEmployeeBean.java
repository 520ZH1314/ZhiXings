package com.zhixing.employlib.model.gardenplot;

import java.io.Serializable;
import java.util.List;

public class NewEmployeeBean  implements Serializable {



        /**
         * NewId : c9780683-0033-4ff3-a104-3f86a9dc1065
         * TeamId : 2ea1bb97-52fe-4e96-9ad8-9fa82110d6e0
         * UserCode : 01322
         * UserName : 王朝坤
         * NewDeeds : 11
         * Seq : 1
         * CreateTime : 2019-03-29T23:47:13
         * TenantId : 00000000-0000-0000-0000-000000000001
         * CreateUserCode : test1admin
         * CreateUserName : STD
         * Files : []
         * UserInfo : {"UserId":"7565a94b-8025-11e8-b8e8-507b9d9a63b9","TenantId":"00000000-0000-0000-0000-000000000001","UserCode":"01322","UserSeq":null,"UserName":"王朝坤","IsAdministrator":null,"Password":"81DC9BDB52D04DC20036DBD8313ED055","LineId":null,"ShiftId":null,"Sex":"男","PhoneNumber":null,"Email":null,"HeadShip":null,"CardNo":null,"RoleName":null,"ConfigJSON":null,"IsEnable":"1","LoginCount":null,"LastLoginDate":null,"CreatePerson":null,"CreateDate":"2018-07-05T15:30:51","UpdatePerson":null,"UpdateDate":null,"Description":null,"PhotoURL":null,"BPAddress":null,"Nationality":"侗族","NativePlace":"贵州省","RegisteredResidence":null,"IDCardNo":"522627198709224419","Birthday":"1987-09-22T00:00:00","Ages":30,"Married":null,"Party":null,"IsSoldier":null,"Residence":"贵州省黔东南苗族侗族自治州天柱县","Contacts":null,"ContactsPhone":null,"MaxEducation":"初中","MaxDegree":null,"School":null,"Studied":null,"WokDate":null,"JoinWorkDate":"2007-11-14T00:00:00","StartWorkDate":"2007-11-14T00:00:00","Recruit":null,"ContractType":null,"ContractStartDate":"2016-11-14T00:00:00","ContractEndDate":"2019-11-13T00:00:00","EmployeeType":null,"GroupType":null,"HeadShipGrade":null,"LineName":null,"ShiftName":null,"OrganizeId":"d52c82e1-9458-11e8-ba3c-3440b58fff11","OrganizeCode":"TW010201","OrganizeName":"五金模具","PositionId":null,"PositionCode":null,"PositionName":null}
         */

        private String NewId;
        private String TeamId;
        private String UserCode;
        private String UserName;
        private String NewDeeds;
        private int Seq;
        private String CreateTime;
        private String TenantId;
        private String CreateUserCode;
        private String CreateUserName;
        private UserInfoBean UserInfo;
        private List<EmployeeFlie> Files;

        public String getNewId() {
            return NewId;
        }

        public void setNewId(String NewId) {
            this.NewId = NewId;
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

        public String getNewDeeds() {
            return NewDeeds;
        }

        public void setNewDeeds(String NewDeeds) {
            this.NewDeeds = NewDeeds;
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
             * UserId : 7565a94b-8025-11e8-b8e8-507b9d9a63b9
             * TenantId : 00000000-0000-0000-0000-000000000001
             * UserCode : 01322
             * UserSeq : null
             * UserName : 王朝坤
             * IsAdministrator : null
             * Password : 81DC9BDB52D04DC20036DBD8313ED055
             * LineId : null
             * ShiftId : null
             * Sex : 男
             * PhoneNumber : null
             * Email : null
             * HeadShip : null
             * CardNo : null
             * RoleName : null
             * ConfigJSON : null
             * IsEnable : 1
             * LoginCount : null
             * LastLoginDate : null
             * CreatePerson : null
             * CreateDate : 2018-07-05T15:30:51
             * UpdatePerson : null
             * UpdateDate : null
             * Description : null
             * PhotoURL : null
             * BPAddress : null
             * Nationality : 侗族
             * NativePlace : 贵州省
             * RegisteredResidence : null
             * IDCardNo : 522627198709224419
             * Birthday : 1987-09-22T00:00:00
             * Ages : 30
             * Married : null
             * Party : null
             * IsSoldier : null
             * Residence : 贵州省黔东南苗族侗族自治州天柱县
             * Contacts : null
             * ContactsPhone : null
             * MaxEducation : 初中
             * MaxDegree : null
             * School : null
             * Studied : null
             * WokDate : null
             * JoinWorkDate : 2007-11-14T00:00:00
             * StartWorkDate : 2007-11-14T00:00:00
             * Recruit : null
             * ContractType : null
             * ContractStartDate : 2016-11-14T00:00:00
             * ContractEndDate : 2019-11-13T00:00:00
             * EmployeeType : null
             * GroupType : null
             * HeadShipGrade : null
             * LineName : null
             * ShiftName : null
             * OrganizeId : d52c82e1-9458-11e8-ba3c-3440b58fff11
             * OrganizeCode : TW010201
             * OrganizeName : 五金模具
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
            private Object LineId;
            private Object ShiftId;
            private String Sex;
            private Object PhoneNumber;
            private Object Email;
            private Object HeadShip;
            private Object CardNo;
            private Object RoleName;
            private Object ConfigJSON;
            private String IsEnable;
            private Object LoginCount;
            private Object LastLoginDate;
            private Object CreatePerson;
            private String CreateDate;
            private Object UpdatePerson;
            private Object UpdateDate;
            private Object Description;
            private Object PhotoURL;
            private Object BPAddress;
            private String Nationality;
            private String NativePlace;
            private Object RegisteredResidence;
            private String IDCardNo;
            private String Birthday;
            private int Ages;
            private Object Married;
            private Object Party;
            private Object IsSoldier;
            private String Residence;
            private Object Contacts;
            private Object ContactsPhone;
            private String MaxEducation;
            private Object MaxDegree;
            private Object School;
            private Object Studied;
            private Object WokDate;
            private String JoinWorkDate;
            private String StartWorkDate;
            private Object Recruit;
            private Object ContractType;
            private String ContractStartDate;
            private String ContractEndDate;
            private Object EmployeeType;
            private Object GroupType;
            private Object HeadShipGrade;
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

            public Object getLineId() {
                return LineId;
            }

            public void setLineId(Object LineId) {
                this.LineId = LineId;
            }

            public Object getShiftId() {
                return ShiftId;
            }

            public void setShiftId(Object ShiftId) {
                this.ShiftId = ShiftId;
            }

            public String getSex() {
                return Sex;
            }

            public void setSex(String Sex) {
                this.Sex = Sex;
            }

            public Object getPhoneNumber() {
                return PhoneNumber;
            }

            public void setPhoneNumber(Object PhoneNumber) {
                this.PhoneNumber = PhoneNumber;
            }

            public Object getEmail() {
                return Email;
            }

            public void setEmail(Object Email) {
                this.Email = Email;
            }

            public Object getHeadShip() {
                return HeadShip;
            }

            public void setHeadShip(Object HeadShip) {
                this.HeadShip = HeadShip;
            }

            public Object getCardNo() {
                return CardNo;
            }

            public void setCardNo(Object CardNo) {
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

            public Object getLoginCount() {
                return LoginCount;
            }

            public void setLoginCount(Object LoginCount) {
                this.LoginCount = LoginCount;
            }

            public Object getLastLoginDate() {
                return LastLoginDate;
            }

            public void setLastLoginDate(Object LastLoginDate) {
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

            public Object getUpdatePerson() {
                return UpdatePerson;
            }

            public void setUpdatePerson(Object UpdatePerson) {
                this.UpdatePerson = UpdatePerson;
            }

            public Object getUpdateDate() {
                return UpdateDate;
            }

            public void setUpdateDate(Object UpdateDate) {
                this.UpdateDate = UpdateDate;
            }

            public Object getDescription() {
                return Description;
            }

            public void setDescription(Object Description) {
                this.Description = Description;
            }

            public Object getPhotoURL() {
                return PhotoURL;
            }

            public void setPhotoURL(Object PhotoURL) {
                this.PhotoURL = PhotoURL;
            }

            public Object getBPAddress() {
                return BPAddress;
            }

            public void setBPAddress(Object BPAddress) {
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

            public Object getRegisteredResidence() {
                return RegisteredResidence;
            }

            public void setRegisteredResidence(Object RegisteredResidence) {
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

            public Object getMarried() {
                return Married;
            }

            public void setMarried(Object Married) {
                this.Married = Married;
            }

            public Object getParty() {
                return Party;
            }

            public void setParty(Object Party) {
                this.Party = Party;
            }

            public Object getIsSoldier() {
                return IsSoldier;
            }

            public void setIsSoldier(Object IsSoldier) {
                this.IsSoldier = IsSoldier;
            }

            public String getResidence() {
                return Residence;
            }

            public void setResidence(String Residence) {
                this.Residence = Residence;
            }

            public Object getContacts() {
                return Contacts;
            }

            public void setContacts(Object Contacts) {
                this.Contacts = Contacts;
            }

            public Object getContactsPhone() {
                return ContactsPhone;
            }

            public void setContactsPhone(Object ContactsPhone) {
                this.ContactsPhone = ContactsPhone;
            }

            public String getMaxEducation() {
                return MaxEducation;
            }

            public void setMaxEducation(String MaxEducation) {
                this.MaxEducation = MaxEducation;
            }

            public Object getMaxDegree() {
                return MaxDegree;
            }

            public void setMaxDegree(Object MaxDegree) {
                this.MaxDegree = MaxDegree;
            }

            public Object getSchool() {
                return School;
            }

            public void setSchool(Object School) {
                this.School = School;
            }

            public Object getStudied() {
                return Studied;
            }

            public void setStudied(Object Studied) {
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

            public Object getRecruit() {
                return Recruit;
            }

            public void setRecruit(Object Recruit) {
                this.Recruit = Recruit;
            }

            public Object getContractType() {
                return ContractType;
            }

            public void setContractType(Object ContractType) {
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

            public Object getEmployeeType() {
                return EmployeeType;
            }

            public void setEmployeeType(Object EmployeeType) {
                this.EmployeeType = EmployeeType;
            }

            public Object getGroupType() {
                return GroupType;
            }

            public void setGroupType(Object GroupType) {
                this.GroupType = GroupType;
            }

            public Object getHeadShipGrade() {
                return HeadShipGrade;
            }

            public void setHeadShipGrade(Object HeadShipGrade) {
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
