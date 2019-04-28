package com.zhixing.employlib.api;

import android.content.Context;

import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.common.T;
import com.zhixing.employlib.model.AppealList;
import com.zhixing.employlib.model.AppealPersonEntity;
import com.zhixing.employlib.model.MonthViewBean;
import com.zhixing.employlib.model.NoticeBean;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.StandScore;
import com.zhixing.employlib.model.gardenplot.ExcellentEmployeeBean;
import com.zhixing.employlib.model.gardenplot.ExcellentEmployeePostBean;
import com.zhixing.employlib.model.gardenplot.NewEmployeeBean;
import com.zhixing.employlib.model.gardenplot.TeamDemeanorBean;
import com.zhixing.employlib.model.gardenplot.UpLoadBean;
import com.zhixing.employlib.model.gardenplot.UpLoadFour;
import com.zhixing.employlib.model.gardenplot.UpLoadOne;
import com.zhixing.employlib.model.gardenplot.UpLoadThree;
import com.zhixing.employlib.model.gardenplot.UpLoadTwo;
import com.zhixing.employlib.model.grading.GoGradingPostBean;
import com.zhixing.employlib.model.grading.GradListBean;
import com.zhixing.employlib.model.grading.GradListDetailPostBean;
import com.zhixing.employlib.model.grading.GradingListDetailBean;
import com.zhixing.employlib.model.grading.GradingListPostBean;
import com.zhixing.employlib.model.grading.RankBean;
import com.zhixing.employlib.model.grading.RankStandPostBean;
import com.zhixing.employlib.model.performance.EventKeyBean;
import com.zhixing.employlib.model.performance.EventPostBean;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.model.performance.PerformanceRankBean;
import com.zhixing.employlib.model.performance.PersonDayEventBean;
import com.zhixing.employlib.model.performance.PersonDayEventPostBean;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.model.performance.PersonTeamPostBean;
import com.zhixing.employlib.model.performance.RankPostBean;
import com.zhixing.employlib.model.performance.TotalMonthPerformanceBean;
import com.zhixing.employlib.model.performance.YesterdayPerformancePostBean;
import com.zhixing.employlib.model.recrui.DoJobPostBean;
import com.zhixing.employlib.model.recrui.PutRefferPostBean;
import com.zhixing.employlib.model.recrui.RecruitListBean;
import com.zhixing.employlib.model.recrui.RecruitListPostBean;
import com.zhixing.employlib.model.resume.EditResumeData;
import com.zhixing.employlib.model.resume.GetResumeBean;
import com.zhixing.employlib.model.resume.GetResumePostBean;
import com.zhixing.employlib.model.resume.PutResumeData;
import com.zhixing.netlib.base.BaseHttpUtil;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.RetrofitClients;

import java.io.File;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;

public class APi {


    //获取个人班组以及权限
    public static Flowable<DBaseResponse<PersonTeamBean>> getPersonTeamInfo(PersonTeamPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getPersonTeamInfo((new BaseHttpUtil<PersonTeamPostBean>().convertVo2Json(bean)));
    }

    //获取昨日绩效

    public static Flowable<DBaseResponse<TotalMonthPerformanceBean>> getYesterDayInfo(YesterdayPerformancePostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getYesterDayInfo((new BaseHttpUtil<YesterdayPerformancePostBean>().convertVo2Json(bean)));


    }

    //获取月绩效

    public static Flowable<DBaseResponse<MonthPerformanceBean>> getMonthDayInfo(YesterdayPerformancePostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getMonthDayInfo((new BaseHttpUtil<YesterdayPerformancePostBean>().convertVo2Json(bean)));


    }

    //获取评分员工列表数据

    public static Flowable<BaseResponse<GradListBean>> getGradingListData(GradingListPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getGradingListData((new BaseHttpUtil<GradingListPostBean>().convertVo2Json(bean)));


    }

    //获取评分详情员工列表数据
    public static Flowable<BaseResponse<GradingListDetailBean>> getGradingListDetailData(GradListDetailPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getGradingListDetailData((new BaseHttpUtil<GradListDetailPostBean>().convertVo2Json(bean)));

    }

    //去评分
    public static Flowable<BaseResponse> getGoGrading(String bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getGoGrading((new BaseHttpUtil<GoGradingPostBean>().toJson(bean)));

    }

    //获取事件
    public static Flowable<DBaseResponse<EventKeyBean>> getEvent(EventPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getEvent((new BaseHttpUtil<EventPostBean>().convertVo2Json(bean)));

    }

    //获取考核标准数据请求
    public static Flowable<DBaseResponse<PersonTestEntity>> getEventInfo(PersonTeamPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getEventInfo((new BaseHttpUtil<PersonTeamPostBean>().convertVo2Json(bean)));
    }

    private static Map put(PersonTeamPostBean bean, Map map, BaseHttpUtil httpUtil) {
        Map tempMap = httpUtil.convertVo2Map(bean);
        if (map != null) {
            tempMap.putAll(map);
        }
        FileUtils.parms(tempMap);
        return tempMap;
    }

    //统一数据处理
    public static Flowable<BaseResponse<AppealPersonEntity>> net(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil httpUtil = new BaseHttpUtil<PersonTeamPostBean>();

        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).net(httpUtil.convertVo2Json(put(bean, map, httpUtil)));
    }

    //申诉请求
    public static Flowable<BaseResponse> sendAppealPerson(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil httpUtil = new BaseHttpUtil();


        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).sendAppealPerson(httpUtil.convertVo2Json(put(bean, map, httpUtil)));
    }

    //申诉列表
    public static Flowable<BaseResponse<AppealList>> getAppealList(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil httpUtil = new BaseHttpUtil();

        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getAppealList(httpUtil.convertVo2Json(put(bean, map, httpUtil)));
    }

    //申诉请求
    public static Flowable<BaseResponse> sendAppealRes(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil httpUtil = new BaseHttpUtil();


        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).sendAppealRes(httpUtil.convertVo2Json(put(bean, map, httpUtil)));
    }


    //获取等级标准


    public static Flowable<BaseResponse<RankBean>> getRankStand(RankStandPostBean bean, Context mContext, String url) {

        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getRankStand((new BaseHttpUtil<RankStandPostBean>().convertVo2Json(bean)));

    }

    //获取个人当日积分事件
    public static Flowable<DBaseResponse<PersonDayEventBean>> getPersonDayEvent(PersonDayEventPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getPersonDayEvent(new BaseHttpUtil<PersonDayEventPostBean>().convertVo2Json(bean));

    }

    //获取园地优秀员工

    public static Flowable<BaseResponse<ExcellentEmployeeBean>> getExcellentEmployee(ExcellentEmployeePostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getExcellentEmployee(new BaseHttpUtil<ExcellentEmployeePostBean>().convertVo2Json(bean));


    }

    ;

    //获取园地新员工

    public static Flowable<BaseResponse<NewEmployeeBean>> getNewEmployee(ExcellentEmployeePostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getNewEmployee(new BaseHttpUtil<ExcellentEmployeePostBean>().convertVo2Json(bean));


    }

    ;

    //获取园地班组风采
    public static Flowable<BaseResponse<TeamDemeanorBean>> getTeamDemeanor(ExcellentEmployeePostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getTeamDemeanor(new BaseHttpUtil<ExcellentEmployeePostBean>().convertVo2Json(bean));


    }

    ;


    //获取招聘列表

    public static Flowable<BaseResponse<RecruitListBean>> getRecruitList(RecruitListPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getRecruitList(new BaseHttpUtil<RecruitListPostBean>().convertVo2Json(bean));

    }

    //投递简历

    public static Flowable<BaseResponse> SendJob(DoJobPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).SendJob(new BaseHttpUtil<DoJobPostBean>().convertVo2Json(bean));

    }

    //推荐岗位


    public static Flowable<BaseResponse> PutJob(PutRefferPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).PutJob(new BaseHttpUtil<PutRefferPostBean>().convertVo2Json(bean));

    }


    //月视图
    public static Flowable<BaseResponse<MonthViewBean>> getMonthViews(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil httpUtil = new BaseHttpUtil();

        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getMonthViews(httpUtil.convertVo2Json(put(bean, map, httpUtil)));
    }

    //月视图
    public static Flowable<BaseResponse<StandScore>> getScoreColor(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil httpUtil = new BaseHttpUtil();

        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getScoreColor(httpUtil.convertVo2Json(put(bean, map, httpUtil)));
    }


    //园地图片上传
    public static Flowable<BaseResponse<UpLoadBean>> UpLoadImage(Map<String, String> map, File file, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).upLoadImage(map, BaseHttpUtil.SingleFlie(file));
    }



    //上传优秀员工

    public static Flowable<BaseResponse>UpLoadOne(UpLoadOne one,Context mContext, String url){
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).UpLoadOne(new BaseHttpUtil<UpLoadOne>().convertVo2Json(one));

    }


    //上传新员工
    public static Flowable<BaseResponse>UpLoadTwo(UpLoadTwo one, Context mContext, String url){
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).UpLoadTwo(new BaseHttpUtil<UpLoadTwo>().convertVo2Json(one));

    }

    //上传班组风采

    public static Flowable<BaseResponse>UpLoadThree(UpLoadThree one, Context mContext, String url){
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).UpLoadthree(new BaseHttpUtil<UpLoadThree>().convertVo2Json(one));

    }


    //上传公告

    public static Flowable<BaseResponse>UpLoadFour(UpLoadFour one, Context mContext, String url){
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).UpLoadFour(new BaseHttpUtil<UpLoadFour>().convertVo2Json(one));

    }


    //获取个人简历

    public static Flowable<BaseResponse<GetResumeBean>> GetResumeData(GetResumePostBean one, Context mContext, String url){
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).GetResumeData(new BaseHttpUtil<GetResumePostBean>().convertVo2Json(one));

    }


    //增加个人简历

    public static Flowable<BaseResponse> AddResumeData(PutResumeData one, Context mContext, String url){
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).PutResumeData(new BaseHttpUtil<PutResumeData>().convertVo2Json(one));

    }


    //修改个人简历

    public static Flowable<BaseResponse> AlterResumeData(EditResumeData one, Context mContext, String url){
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).AlterResumeData(new BaseHttpUtil<EditResumeData>().convertVo2Json(one));

    }

    //公告
    public static  Flowable<BaseResponse<NoticeBean>> getNotices(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil  httpUtil = new BaseHttpUtil();

        return RetrofitClients.getInstance(mContext,url) .create(PerformanceApi.class) .getNotices(httpUtil.convertVo2Json(put(bean,map,httpUtil)));
    }


    //获取排名
    public static Flowable<BaseResponse<PerformanceRankBean>> getRank(RankPostBean bean, Context mContext, String url){
        return RetrofitClients.getInstance(mContext, url).create(PerformanceApi.class).getRank(new BaseHttpUtil<RankPostBean>().convertVo2Json(bean));


    }


}
