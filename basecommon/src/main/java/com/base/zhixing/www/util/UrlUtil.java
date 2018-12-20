package com.base.zhixing.www.util;

import org.json.JSONObject;

public class UrlUtil {
//	public static String LOCALIP = SharedPreferencesTool.getMStool(BaseApplication.application).getIp();
	public static String IP = "http://www.m3lean.com:8080";//服务器地址
	public static String LOGIN = "/login/doAction";//登录
	public static String GetUseInfo="/api/CMP/ApiRegistrator/PostApiGateWay";//我的信息
	public static String NoticeList = "/api/Business/NoticeList/GetList?";//首页->新通知（列表）
	public static String GetNewTaskList = "/api/Business/ToDoList/GetNewTaskList?";//首页->新任务（列表）
	public static String GetMyCalendar = "/api/Business/Task/GetMyCalendar?";//首页->任务日历
	public static String GetMyApplyList = "/api/Business/ToDoList/GetMyApplyList?";//我的任务->发起（列表）
	public static String GetMyToDoList = "/api/Business/ToDoList/GetMyToDoList?";//我的任务->责任（列表）
	public static String GetMyPartInList = "/api/Business/ToDoList/GetMyPartInList?";//我的任务->参与（列表）
	public static String GetMyCheckList = "/api/Business/ToDoList/GetMyCheckList?";//我的任务->检查（列表）
	public static String GetClosedRateChart= "/api/Business/Report/GetClosedRateChart?";//我的任务->分析->任务关闭率->我负责的任务
	public static String GetTaskTypeBySourceChart = "/api/Business/Report/GetTaskTypeBySource?";//我的任务->分析->任务分类统计->任务来源
	public static String GetTaskTypeChartByClass = "/api/Business/Report/GetTaskTypeChartByClass?";//我的任务->分析->任务分类统计->任务类型
	public static String GetAvgScoreData = "/api/Business/Report/GetAvgScoreData?";//我的任务->分析->个人任务评价->获取平均分
	public static String GetTaskCloseRateDataByTop = "/api/Business/Report/GetTaskCloseRateDataByTop?";//我的任务->分析->个人任务评价->排行榜
	public static String GetInnerUrgeUser = "/api/sys/user/GetInnerUrgeUser?";//创建催单->内部紧急催单->选择责任人
	public static String GetOuterUrgeUser = "/api/sys/user/GetOuterUrgeUser?";//创建催单->采购催单->选择责任人
	public static String InnerUrgeOrder = "/api/business/InnerUrgeOrder/Edit";//创建催单->内部紧急催单->发起催单
	public static String OuterUrgeOrder = "/api/business/OuterUrgeOrder/Edit";//创建催单->内部紧急催单->发起催单
	public static String InnerUrgeOrderCC = "/api/business/InnerUrgeOrderCC/Edit";//创建催单->内部紧急催单->保存抄送人
	public static String InnerUrgeOrder01= "/api/business/InnerUrgeOrder/GetMyApplyList?";//我的任务->我的发起——内部催单
	public static String InnerUrgeOrder02= "/api/business/InnerUrgeOrder/GetTodoList?";//我的任务->我的责任——内部催单
	public static String InnerUrgeOrder03= "/api/business/InnerUrgeOrder/GetMyCheckList?";//2.3.12我的任务->我的参与——内部催单

	public static String OuterUrgeOrder01= "/api/business/OuterUrgeOrder/GetMyApplyList?";//我的任务->我的发起——内部催单
	public static String OuterUrgeOrder02= "/api/business/OuterUrgeOrder/GetTodoList?";//我的任务->我的责任——内部催单
	public static String OuterUrgeOrder03= "/api/business/OuterUrgeOrder/GetMyCheckList?";//2.3.12我的任务->我的参与——内部催单



	public static String GetVendor = "/api/sys/vendor?";//创建催单->采购紧急催单->选择供应商
	public static String InnerUrgeOrder04 = "/api/Business/InnerUrgeOrder?";//2.3.13我的任务->查看催单详细信息
	public static String OuterUrgeOrder04 = "/api/Business/OuterUrgeOrder?";//2.3.13我的任务->查看催单详细信息

	public static String InnerCCUser = "/api/business/InnerUrgeOrderCC?";//获取内部催单抄送人
	public static String OuterCCUser = "/api/business/OuterUrgeOrderCC?";//获取外部催单抄送人

	public static String InnerUrgeOrderHandle = "/api/Business/InnerUrgeOrderHandle/Edit";//我的任务->回复——内部催单
	public static String GetInnerUrgeOrderHandle="/api/Business/InnerUrgeOrderHandle?";//我的任务->回复——内部催单
    public static String OuterUrgeOrderHandle = "/api/Business/OuterUrgeOrderHandle/Edit";//我的任务->回复——内部催单
	public static String GetOuterUrgeOrderHandle="/api/Business/OuterUrgeOrderHandle?";//我的任务->回复——内部催单

/*
* 耿献博添加
* 内外部催单统计分析接口
* */

	public static String GetCustomer="/api/Business/InnerUrgeOrder/GetCustomer";//统计分析->催单客户统计
    public static String GetCustomerAnalysis="/api/Business/GetCustomerAnalysis?";//统计分析->催单客户统计
    public static String GetTypeAnalysis="/api/Business/GetTypeAnalysis?";//2.3.28	内部紧急催单->统计分析->催单类型统计
    public static String GetEvaluateAnalysis="/api/Business/InnerUrgeOrder/GetEvaluateAnalysis?";//2.3.29	内部紧急催单->统计分析->获取平均分
    public static String GetVendorAnalysis="/api/Business/GetVendorAnalysis?";//2.3.31	采购紧急催单->统计分析->催单供应商统计


	/*我的组织统计分析接口*/
	public static String GetOrgClosedRateChart="/api/Business/Organize/GetOrgClosedRateChart?";//2.2.18	我的组织->统计分析->任务关闭率->部门发起的任务
	public static String GetOrgClosedRateCharts="/api/Business/Organize/GetOrgClosedRateChart?";//2.2.19	我的组织->统计分析->任务关闭率->部门负责的任务
	public static String GetOrgTaskTypeBySource="/api/Business/Organize/GetOrgTaskTypeBySource?";//2.2.20	我的组织->统计分析->任务分类统计->部门任务来源分析
	public static String GetOrgTaskTypeByClass="/api/Business/Organize/GetOrgTaskTypeByClass?";//2.2.21	我的组织->统计分析->任务分类统计->部门任务类型分析

	public static String GetAvgScoreDatas="/api/Business/Organize/GetAvgScoreData?";//2.2.22	我的组织->统计分析->评价管理分析->获取平均分--和我的任务里一个接口
	public static String GetCompanyTaskCloseRateDataByTop="/api/Business/Organize/GetCompanyTaskCloseRateDataByTop?";//2.2.23	我的组织->统计分析->评价管理分析->公司龙虎榜
	public static String GetOrgTaskCloseRateDataByTop="/api/Business/Organize/GetOrgTaskCloseRateDataByTop?";//2.2.24	我的组织->统计分析->评价管理分析->部门龙虎榜


	//部门
	public static String GetOrganizeList="/api/Sys/Organize/GetOrganizeTree?";//2.2.15	我的组织->获取部门列表
	public static String GetUsersByOrgId="/api/Sys/UserOrganizeMap/GetUsersByOrgId?";//2.2.16	我的组织->获取部门人员
	public static String GetOrganizeToDoList="/api/Business/ToDoList/GetOrganizeToDoList?";//2.2.17	我的组织->部门任务（列表）

	public static String InnerQuery="/api/Business/InnerUrgeOrder/GetQuery?";
	public static String OuterQuery="/api/Business/OuterUrgeOrder/GetQuery?";
	//云服务器
	//public static String BaseUrl= "http://192.168.2.253:9090";//公司服务器地址
	//public static String BaseUrl= "http://192.168.0.102:9001";//泰威服务器地址
	 public static String BaseUrl= "http://192.168.2.253:9001";//傲雷服务器地址
	//public static String BaseUrl= "http://www.m3lean.com:501";//云服务器地址

	public  static String Url="/api/CMP/ApiRegistrator/PostApiGateWay";
	public  static String SeveImageUrl="/api/CMP/AppRegistrator?AppCode=LinePatrol";
	public  static String SeveLinePatrolImageUrl="/api/CMP/AppRegistrator?AppCode=PatrolInspection";
	public  static String UpdateUrl="/api/Sys/AppVersion/Get";
	public static String LinkedTable01="linepatrol_problem01";//问题改善前图片
	public static String LinkedTable02="linepatrol_problem02";//问题改善后图片
	public static String LinkedTable03="linepatrol_highLight";//亮点图片
	public static String LinkedTable04="linepatrol_group";//合影图片
	public static String LinkedTable05="linepatrol_standardItem";//巡线标准图片
	public static String patrolinspection01="patrolinspection_problem01";//巡检问题发现
	public static String patrolinspection02="patrolinspection_problem02";//巡检问题改善
	public  static String URLIMG="http://10.6.24.237:8080/upload/";

	//看板
//	public static String KANBAN = "/api/Kanban/TurnOverDays/GetTurnOverDaysData";


	public  final static int SUCCESS=1;
	public  final static int ERRORR=0;
	//============请求码==============
	public  static int AddGroupActivity_RequstCode=201;
	public  static int EditGroupActivity_RequstCode01=202;
	public  static int EditGroupActivity_RequstCode02=203;
	public  static int AddQuestionActivity_RequstCode01=204;
	public  static int AddQuestionActivity_RequstCode02=205;
	public  static int AddLightActivity_RequstCode01=206;
	public  static int AddLightActivity_RequstCode02=207;
	public  static int OneFindActivity_RequstCode01=208;
	public  static int OneFindActivity_RequstCode02=209;
	public  static int TwoFindActivity_RequstCode01=210;
	public  static int TwoFindActivity_RequstCode02=211;
	public  static int SolveActivity_RequstCode=212;
	public  static int MyQuestionActivity_RequstCode01=213;
	public  static int MyQuestionActivity_RequstCode02=214;
	public  static int MyQuestionDetailActivity_RequstCode=215;
	public  static int AddCallActivity_RequstCode=216;
	public  static int PatrolTaskActivity_RequstCode=217;
	public  static int AddProblemActivity_RequstCode01=218;
	public  static int AddProblemActivity_RequstCode02=219;
	public  static int ProblemRecordActivity_RequstCode=220;
	public  static int InspectionFragment02_RequstCode=221;
	public  static int InspectionFragment01_RequstCode=222;










	//============返回码==============
	public  static int AddQuestionActivity_ResultCode=301;
	public  static int OrganizeActivity_ResultCode=302;
	public  static int UserActivity_ResultCode=303;
	public  static int AddLightActivity_ResultCode=304;
	public  static int SignUserActivity_ResultCode=305;
	public  static int SolveActivity_ResultCode=306;
	public  static int CallUserActivity_ResultCode=307;
	public  static int TwoClassActivity_ResultCode=308;
	public  static int ThreeClassActivity_ResultCode=309;
	public  static int AddProblemActivity_ResultCode=310;
	public  static int SolvePActivity_ResultCode=311;
	public  static int OneFindActivity_ResultCode=312;
	public  static int InspectionItemActivity_ResultCode=313;




















	//public  static String URL="http://112.74.36.119:501/api/CMP/ApiRegistrator/PostApiGateWay";


	public static String UploadImageUrl(String AppUrl,String  AppCode,String ApiCode,String TenantId ,String LinkedTableId,String LinkedTable,String FileDesc){
		String result=AppUrl+"/api/LinePatrol/Files/EditUploadFiles?";
		AppCode="AppCode="+AppCode+"&";
		ApiCode="ApiCode="+ApiCode+"&";
		TenantId="TenantId="+TenantId+"&";
		LinkedTableId="LinkedTableId="+LinkedTableId+"&";
		LinkedTable="LinkedTable="+LinkedTable+"&";
		FileDesc="FileDesc="+FileDesc;
		result=result+AppCode+ApiCode+TenantId+LinkedTableId+LinkedTable+FileDesc;
		return result;
	}
	public static String UploadInspectionImageUrl(String AppUrl,String  AppCode,String ApiCode,String TenantId ,String LinkedTableId,String LinkedTable,String FileDesc){
		String result=AppUrl+"/api/PatrolInspection/Files/EditUploadFiles?";
		AppCode="AppCode="+AppCode+"&";
		ApiCode="ApiCode="+ApiCode+"&";
		TenantId="TenantId="+TenantId+"&";
		LinkedTableId="LinkedTableId="+LinkedTableId+"&";
		LinkedTable="LinkedTable="+LinkedTable+"&";
		FileDesc="FileDesc="+FileDesc;
		result=result+AppCode+ApiCode+TenantId+LinkedTableId+LinkedTable+FileDesc;
		return result;
	}




	//2.1.1获取验证登录URL
	public static String  GetPostUrl(String path,String method ,JSONObject data){
		String result="";
		path=path+method+data.toString();
		result=path;
		return result;
	}
	public static String  GetPostUrl(String path,String method ){
		String result="";
		path=path+method;
		result=path;
		return result;
	}
	//2.2.1首页->新通知（列表）URL
	public static String  GetNoticListUrl(String path,String method ,  int page,int row,String ReceiveUserId){
		String result="";
		path=path+method;
		String paramet01="page="+page+"&";
		String paramet02="rows="+row+"&";
		String paramet03="ReceiveUserId="+ReceiveUserId;
		result=path+paramet01+paramet02+paramet03;
		return result;
	}
	//2.2.2首页->新任务（列表）URL
	public static String  GetNewTaskListUrl(String path,String method ,  int page,int row,String ToDoUserId){
		String result="";
		path=path+method;
		String paramet01="page="+page+"&";
		String paramet02="rows="+row+"&";
		String paramet03="ToDoUserId="+ToDoUserId;
		result=path+paramet01+paramet02+paramet03;
		return result;
	}
	//2.2.3首页->任务日历
	public static String  GetMyCalendarUrl(String path,String method ,  int page,int row,String ToDoUserId){
		String result="";
		path=path+method;
		String paramet01="page="+page+"&";
		String paramet02="rows="+row+"&";
		String paramet03="ToDoUserId="+ToDoUserId;
		result=path+paramet01+paramet02+paramet03;
		return result;
	}
	//2.2.4我的任务->发起（列表）
	public static String  GetMyApplyListUrl(String path,String method ,  int page,int row,String CreateUserId){
		String result="";
		path=path+method;
		String paramet01="page="+page+"&";
		String paramet02="rows="+row+"&";
		String paramet03="CreateUserId="+CreateUserId;
		result=path+paramet01+paramet02+paramet03;
		return result;
	}
	//2.2.5我的任务->责任（列表）
	public static String  GetMyToDoListUrl(String path,String method ,  int page,int row,String ToDoUserId){
		String result="";
		path=path+method;
		String paramet01="page="+page+"&";
		String paramet02="rows="+row+"&";
		String paramet03="ToDoUserId="+ToDoUserId;
		result=path+paramet01+paramet02+paramet03;
		return result;
	}
	//2.2.6我的任务->参与（列表）
	public static String  GetMyPartInListUrl(String path,String method ,  int page,int row,String ToDoUserId){
		String result="";
		path=path+method;
		String paramet01="page="+page+"&";
		String paramet02="rows="+row+"&";
		String paramet03="ToDoUserId="+ToDoUserId;
		result=path+paramet01+paramet02+paramet03;
		return result;
	}
	//2.2.7我的任务->检查（列表）
	public static String GetMyCheckListUrl(String path,String method ,  int page,int row,String ToDoUserId){
		String result="";
		path=path+method;
		String paramet01="page="+page+"&";
		String paramet02="rows="+row+"&";
		String paramet03="ToDoUserId="+ToDoUserId;
		result=path+paramet01+paramet02+paramet03;
		return result;
	}
	//2.2.8我的任务->分析->任务关闭率->我发起的任务
	public static String GetClosedRateChartUrl(String path,String method ,String Source,String UserId,String ByType,String SearchType,String DateRange){
		String result="";
		path=path+method;
		String paramet01="Source="+Source+"&";
		String paramet02="UserId="+UserId+"&";
		String paramet03="ByType="+ByType+"&";
		String paramet04="SearchType="+SearchType+"&";
		String paramet05="DateRange="+DateRange;
		result=path+paramet01+paramet02+paramet03+paramet04+paramet05;
		return result;
	}
	//2.2.9我的任务->分析->任务分类统计->任务来源
	public static String GetTaskTypeBySourceChartUrl(String path,String method ,String Source,String UserId,String SearchType,String DateRange){
		String result="";
		path=path+method;
		String paramet01="Source="+Source+"&";
		String paramet02="UserId="+UserId+"&";
		String paramet03="SearchType="+SearchType+"&";
		String paramet04="DateRange="+DateRange;
		result=path+paramet01+paramet02+paramet03+paramet04;
		return result;
	}

	//2.2.12我的任务->分析->个人任务评价->获取平均分
	public static String GetAvgScoreDataUrl(String path,String method ,String TenantId,String UserId,String Source,String SearchType,String DateRange){
		String result="";
		path=path+method;
		String paramet01="TenantId="+TenantId+"&";
		String paramet02="UserId="+UserId+"&";
		String paramet03="Source="+Source+"&";
		String paramet04="SearchType="+SearchType+"&";
		String paramet05="DateRange="+DateRange;
		result=path+paramet01+paramet02+paramet03+paramet04+paramet05;
		return result;
	}

	//2.2.13我的任务->分析->个人任务评价->获取平均分
	public static String GetTaskCloseRateDataByTopUrl(String path,String method ,String TenantId,String Source,String SearchType,String DateRange){
		String result="";
		path=path+method;
		String paramet01="TenantId="+TenantId+"&";
		String paramet02="Source="+Source+"&";
		String paramet03="SearchType="+SearchType+"&";
		String paramet04="DateRange="+DateRange;
		result=path+paramet01+paramet02+paramet03+paramet04;
		return result;
	}

	//2.3.1创建催单->内部紧急催单->选择责任人
	public static String GetInnerUrgeUserUrl(String path,String method ,String userlist,String TenantId){
		String result="";
		path=path+method;
		String paramet01="userlist="+userlist+"&";
		String paramet02="TenantId="+TenantId;
		result=path+paramet01+paramet02;
		return result;
	}

	//2.3.5创建催单->采购紧急催单->选择供应商
	public static String GetVendorUrl(String path,String method ,String TenantId){
		String result="";
		path=path+method;
		String paramet01="TenantId="+TenantId;
		result=path+paramet01;
		return result;
	}
	public static String  GetMyApplyList(String path,String method ,  int page,int row,String userid){
		String result="";
		path=path+method;
		String paramet01="page="+page+"&";
		String paramet02="rows="+row+"&";
		String paramet03="userid="+userid;
		result=path+paramet01+paramet02+paramet03;
		return result;
	}
	//2.3.13我的任务->查看催单详细信息
	public static String  GetInnerUrgeOrderUrl(String path,String method ,  String BillNo){
		String result="";
		path=path+method;
		String paramet01="BillNo="+BillNo;
		result=path+paramet01;
		return result;
	}
	public static String  GetCCUserUrl01(String path,String method ,String BillNo){
		String result="";
		path=path+method;
		String paramet01="InnerUrgeBillNo="+BillNo;
		result=path+paramet01;
		return result;
	}
	public static String  GetCCUserUrl02(String path,String method ,String BillNo){
		String result="";
		path=path+method;
		String paramet01="OuterUrgeBillNo="+BillNo;
		result=path+paramet01;
		return result;
	}
	public static String  GetInnerUrgeOrderHandleUrl(String path,String method ,  String InnerUrgeBillNo ){
		String result="";
		path=path+method;
		String paramet01="InnerUrgeBillNo="+InnerUrgeBillNo ;
		result=path+paramet01;
		return result;
	}
	public static String  GetOuterUrgeOrderHandleUrl(String path,String method ,  String OuterUrgeBillNo ){
		String result="";
		path=path+method;
		String paramet01="OuterUrgeBillNo="+OuterUrgeBillNo ;
		result=path+paramet01;
		return result;
	}

	//post请求公共接口
	public static String LoginUrl(String webService, String method) {
		String result = "";
		webService = webService + method;
		result = webService;
		return result;


	}
















	/*耿献博*/

	//2.2.2催单客户统计
	public static String  GetCustomerAnalysis(String path, String method, String UserId, String TenantId, String Source, String SearchType){
		String result="";
		path=path+method;
		String paramet01="UserId="+UserId+"&";
		String paramet02="TenantId="+TenantId+"&";
		String paramet03="Source="+Source;
		String paramet04="SearchType="+SearchType;
		result=path+paramet01+paramet02+paramet03+paramet04;
		return result;
	}

	//2.2.2催单客户统计
	public static String  GetTypeAnalysis(String path, String method, String UserId, String TenantId, String Source, String SearchType){
		String result="";
		path=path+method;
		String paramet01="UserId="+UserId+"&";
		String paramet02="TenantId="+TenantId+"&";
		String paramet03="Source="+Source;
		String paramet04="SearchType="+SearchType;
		result=path+paramet01+paramet02+paramet03+paramet04;
		return result;
	}

	//2.3.29	内部紧急催单->统计分析->获取平均分
	public static String  GetCustomerAnalysis(String path, String method, String UserId, String TenantId, String SearchType){
		String result="";
		path=path+method;
		String paramet01="UserId="+UserId+"&";
		String paramet02="TenantId="+TenantId+"&";
		String paramet03="SearchType="+SearchType;
		result=path+paramet01+paramet02+paramet03;
		return result;
	}


	//2.3.31	采购紧急催单->统计分析->催单供应商统计
	public static String  GetVendorAnalysis(String path, String method, String UserId, String TenantId, String Source, String SearchType){
		String result="";
		path=path+method;
		String paramet01="UserId="+UserId+"&";
		String paramet02="TenantId="+TenantId+"&";
		String paramet03="Source="+Source;
		String paramet04="SearchType="+SearchType;
		result=path+paramet01+paramet02+paramet03+paramet04;
		return result;
	}


	//2.2.18	我的组织->统计分析->任务关闭率->部门发起的任务
	public static String  GetOrgClosedRateChart(String path, String method, String OrgIds, String Source, String ByType, String SearchType,String DateRange){
		String result="";
		path=path+method;
		String paramet01="OrgIds="+OrgIds+"&";
		String paramet02="Source="+Source+"&";
		String paramet03="ByType="+ByType+"&";
		String paramet04="SearchType="+SearchType+"&";
		String paramet05="DateRange="+DateRange;
		result=path+paramet01+paramet02+paramet03+paramet04+paramet05;
		return result;
	}

	//2.2.20	我的组织->统计分析->任务分类统计->部门任务来源分析
	public static String  GetOrgClosedRateChart(String path, String method, String OrgIds, String Source, String SearchType,String DateRange){
		String result="";
		path=path+method;
		String paramet01="OrgIds="+OrgIds+"&";
		String paramet02="Source="+Source+"&";
		String paramet03="SearchType="+SearchType+"&";
		String paramet04="DateRange="+DateRange+"&";
		result=path+paramet01+paramet02+paramet03+paramet04;
		return result;
	}

	//2.2.22	我的组织->统计分析->评价管理分析->获取平均分
	public static String  GetAvgScoreDataChart(String path, String method, String TenantId, String OrgIds, String Source,String SearchType,String DateRange){
		String result="";
		path=path+method;
		String paramet01="TenantId="+TenantId+"&";
		String paramet02="OrgIds="+OrgIds+"&";
		String paramet03="Source="+Source+"&";
		String paramet04="SearchType="+SearchType+"&";
		String paramet05="DateRange="+DateRange;
		result=path+paramet01+paramet02+paramet03+paramet04+paramet05;
		return result;
	}


	//2.2.23	我的组织->统计分析->评价管理分析->公司龙虎榜
	public static String  GetCompanyTaskCloseRateDataByTop(String path, String method, String TenantId, String Source, String SearchType,String DateRange){
		String result="";
		path=path+method;
		String paramet01="TenantId="+TenantId+"&";
		String paramet02="Source="+Source+"&";
		String paramet03="SearchType="+SearchType+"&";
		String paramet04="DateRange="+DateRange;
		result=path+paramet01+paramet02+paramet03+paramet04;
		return result;
	}


	//2.2.24	我的组织->统计分析->评价管理分析->部门龙虎榜
	public static String  GetOrgTaskCloseRateDataByTopUrl(String path, String method, String TenantId, String Source, String SearchType,String DateRange){
		String result="";
		path=path+method;
		String paramet01="TenantId="+TenantId+"&";
		String paramet02="Source="+Source;
		String paramet03="SearchType="+SearchType;
		String paramet04="DateRange="+DateRange+"&";

		result=path+paramet01+paramet02+paramet03+paramet04;
		return result;
	}


	//2.2.15	我的组织->获取部门列表
	public static String  GetOrganizeList(String path, String method, String UserId){
		String result="";
		path=path+method;
		String paramet01="UserId="+UserId;
		result=path+paramet01;
		return result;
	}



	//2.2.16	我的组织->获取部门人员
	public static String  GetUsersByOrgId(String path, String method, String OrgIds){
		String result="";
		path=path+method;
		String paramet01="OrgIds="+OrgIds;
		result=path+paramet01;
		return result;
	}

	//2.2.17	我的组织->部门任务（列表）
	public static String  GetOrganizeToDoList(String path, String method, String OrgIds, int Source, String UserId,String page,String rows) {
		String result = "";
		path = path + method;
		String paramet01 = "OrgIds=" + OrgIds + "&";
		String paramet02 = "Source=" + Source + "&";
		String paramet03 = "UserId=" + UserId + "&";
		String paramet04 = "page=" + page + "&";
		String paramet05 = "rows=" + rows;

		result = path + paramet01 + paramet02 + paramet03 + paramet04 + paramet05;
		return result;
	}
//	2.3.26内部紧急催单->获取客户清单
		public static String  GetCustomer(String path, String method){
			String result="";
			path=path+method;
			result=path;
			return result;
		}
		//以下是cloor开发使用
//	public  static String ANDON = "/index.html";///#/MainPage

}
		
		


		

