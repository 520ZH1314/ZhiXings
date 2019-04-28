package com.zhixing.employlib.model.performance;

import java.util.List;

public class PerformanceRankBean {


    /**
     * status : success
     * message : 保存状态成功
     * rows : [{"StartDate":"2019-01-01 00:00:00","EndDate":"2019-04-22 16:40:59","TeamInfo":[{"UserCode":"00538","UserName":"张凯","Score":10,"Seq":2,"UserInfo":{"UserId":"7565a792-8025-11e8-b8e8-507b9d9a63b9","TenantId":"00000000-0000-0000-0000-000000000001","UserCode":"00538","UserSeq":null,"UserName":"张凯","IsAdministrator":null,"Password":"81DC9BDB52D04DC20036DBD8313ED055","LineId":null,"ShiftId":null,"Sex":"男","PhoneNumber":null,"Email":null,"HeadShip":null,"CardNo":null,"RoleName":null,"ConfigJSON":null,"IsEnable":"1","LoginCount":null,"LastLoginDate":null,"CreatePerson":null,"CreateDate":"2018-07-05T15:30:51","UpdatePerson":null,"UpdateDate":null,"Description":null,"PhotoURL":null,"BPAddress":null,"Nationality":"汉族","NativePlace":"湖北省","RegisteredResidence":null,"IDCardNo":"420117198709288716","Birthday":"1987-09-28T00:00:00","Ages":30,"Married":null,"Party":null,"IsSoldier":null,"Residence":"湖北省武汉市新洲区","Contacts":null,"ContactsPhone":null,"MaxEducation":"初中","MaxDegree":null,"School":null,"Studied":null,"WokDate":null,"JoinWorkDate":"2005-05-09T00:00:00","StartWorkDate":"2005-05-09T00:00:00","Recruit":null,"ContractType":null,"ContractStartDate":"2015-02-20T00:00:00","ContractEndDate":"2088-01-01T00:00:00","EmployeeType":null,"GroupType":null,"HeadShipGrade":null,"LineName":null,"ShiftName":null,"OrganizeId":"d52c82e1-9458-11e8-ba3c-3440b58fff11","OrganizeCode":"TW010201","OrganizeName":"五金模具","PositionId":null,"PositionCode":null,"PositionName":null}}]}]
     */


        /**
         * StartDate : 2019-01-01 00:00:00
         * EndDate : 2019-04-22 16:40:59
         * TeamInfo : [{"UserCode":"00538","UserName":"张凯","Score":10,"Seq":2,"UserInfo":{"UserId":"7565a792-8025-11e8-b8e8-507b9d9a63b9","TenantId":"00000000-0000-0000-0000-000000000001","UserCode":"00538","UserSeq":null,"UserName":"张凯","IsAdministrator":null,"Password":"81DC9BDB52D04DC20036DBD8313ED055","LineId":null,"ShiftId":null,"Sex":"男","PhoneNumber":null,"Email":null,"HeadShip":null,"CardNo":null,"RoleName":null,"ConfigJSON":null,"IsEnable":"1","LoginCount":null,"LastLoginDate":null,"CreatePerson":null,"CreateDate":"2018-07-05T15:30:51","UpdatePerson":null,"UpdateDate":null,"Description":null,"PhotoURL":null,"BPAddress":null,"Nationality":"汉族","NativePlace":"湖北省","RegisteredResidence":null,"IDCardNo":"420117198709288716","Birthday":"1987-09-28T00:00:00","Ages":30,"Married":null,"Party":null,"IsSoldier":null,"Residence":"湖北省武汉市新洲区","Contacts":null,"ContactsPhone":null,"MaxEducation":"初中","MaxDegree":null,"School":null,"Studied":null,"WokDate":null,"JoinWorkDate":"2005-05-09T00:00:00","StartWorkDate":"2005-05-09T00:00:00","Recruit":null,"ContractType":null,"ContractStartDate":"2015-02-20T00:00:00","ContractEndDate":"2088-01-01T00:00:00","EmployeeType":null,"GroupType":null,"HeadShipGrade":null,"LineName":null,"ShiftName":null,"OrganizeId":"d52c82e1-9458-11e8-ba3c-3440b58fff11","OrganizeCode":"TW010201","OrganizeName":"五金模具","PositionId":null,"PositionCode":null,"PositionName":null}}]
         */

        private String StartDate;
        private String EndDate;
        private List<TeamInfoBean> TeamInfo;

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public List<TeamInfoBean> getTeamInfo() {
            return TeamInfo;
        }

        public void setTeamInfo(List<TeamInfoBean> TeamInfo) {
            this.TeamInfo = TeamInfo;
        }

        public static class TeamInfoBean {
            /**
             * UserCode : 00538
             * UserName : 张凯
             * Score : 10
             * Seq : 2
             * UserInfo : {"UserId":"7565a792-8025-11e8-b8e8-507b9d9a63b9","TenantId":"00000000-0000-0000-0000-000000000001","UserCode":"00538","UserSeq":null,"UserName":"张凯","IsAdministrator":null,"Password":"81DC9BDB52D04DC20036DBD8313ED055","LineId":null,"ShiftId":null,"Sex":"男","PhoneNumber":null,"Email":null,"HeadShip":null,"CardNo":null,"RoleName":null,"ConfigJSON":null,"IsEnable":"1","LoginCount":null,"LastLoginDate":null,"CreatePerson":null,"CreateDate":"2018-07-05T15:30:51","UpdatePerson":null,"UpdateDate":null,"Description":null,"PhotoURL":null,"BPAddress":null,"Nationality":"汉族","NativePlace":"湖北省","RegisteredResidence":null,"IDCardNo":"420117198709288716","Birthday":"1987-09-28T00:00:00","Ages":30,"Married":null,"Party":null,"IsSoldier":null,"Residence":"湖北省武汉市新洲区","Contacts":null,"ContactsPhone":null,"MaxEducation":"初中","MaxDegree":null,"School":null,"Studied":null,"WokDate":null,"JoinWorkDate":"2005-05-09T00:00:00","StartWorkDate":"2005-05-09T00:00:00","Recruit":null,"ContractType":null,"ContractStartDate":"2015-02-20T00:00:00","ContractEndDate":"2088-01-01T00:00:00","EmployeeType":null,"GroupType":null,"HeadShipGrade":null,"LineName":null,"ShiftName":null,"OrganizeId":"d52c82e1-9458-11e8-ba3c-3440b58fff11","OrganizeCode":"TW010201","OrganizeName":"五金模具","PositionId":null,"PositionCode":null,"PositionName":null}
             */

            private String UserCode;
            private String UserName;
            private int Score;
            private int Seq;
            private UserInfoBean UserInfo;

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

            public int getSeq() {
                return Seq;
            }

            public void setSeq(int Seq) {
                this.Seq = Seq;
            }

            public UserInfoBean getUserInfo() {
                return UserInfo;
            }

            public void setUserInfo(UserInfoBean UserInfo) {
                this.UserInfo = UserInfo;
            }

            public static class UserInfoBean {
                /**
                 * UserId : 7565a792-8025-11e8-b8e8-507b9d9a63b9
                 * TenantId : 00000000-0000-0000-0000-000000000001
                 * UserCode : 00538
                 * UserSeq : null
                 * UserName : 张凯
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
                 * Nationality : 汉族
                 * NativePlace : 湖北省
                 * RegisteredResidence : null
                 * IDCardNo : 420117198709288716
                 * Birthday : 1987-09-28T00:00:00
                 * Ages : 30
                 * Married : null
                 * Party : null
                 * IsSoldier : null
                 * Residence : 湖北省武汉市新洲区
                 * Contacts : null
                 * ContactsPhone : null
                 * MaxEducation : 初中
                 * MaxDegree : null
                 * School : null
                 * Studied : null
                 * WokDate : null
                 * JoinWorkDate : 2005-05-09T00:00:00
                 * StartWorkDate : 2005-05-09T00:00:00
                 * Recruit : null
                 * ContractType : null
                 * ContractStartDate : 2015-02-20T00:00:00
                 * ContractEndDate : 2088-01-01T00:00:00
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
                private String PhotoURL;
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
                private Object PositionName;

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

                public String getPhotoURL() {
                    return PhotoURL;
                }

                public void setPhotoURL(String PhotoURL) {
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

                public Object getPositionName() {
                    return PositionName;
                }

                public void setPositionName(Object PositionName) {
                    this.PositionName = PositionName;
                }
            }
        }
    }

