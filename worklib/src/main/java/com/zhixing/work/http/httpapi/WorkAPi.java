package com.zhixing.work.http.httpapi;




import com.zhixing.work.activity.CreateTaskActivity;
import com.zhixing.work.bean.CreateTaskEntity;
import com.zhixing.work.bean.ResponseCompeteBean;
import com.zhixing.work.bean.ResponseJoinBean;
import com.zhixing.work.bean.ResponseMeetDetailEntity;
import com.zhixing.work.bean.ResponseMeetingEntity;
import com.zhixing.work.bean.TaskDetailEntity;
import com.zhixing.work.bean.TaskListItemEntity;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface WorkAPi {

    //创建任务
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<CreateTaskEntity>CreateTask( @Body RequestBody body);

    //创建会议
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<CreateTaskEntity>CreateMeet( @Body RequestBody body);


    //获取任务列表
    //创建任务
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<TaskListItemEntity>getTaskList(@Body RequestBody body);

    //获取任务详情
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<TaskDetailEntity>getTaskDetail(@Body RequestBody body);


    //发送评论

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<CreateTaskEntity>SendMessage(@Body RequestBody body);


    //关闭任务
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<CreateTaskEntity>CloseTask(@Body RequestBody body);

    //完成任务
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<CreateTaskEntity>CompeteTask(@Body RequestBody body);

    //获取未完成的执行人

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<ResponseCompeteBean>getTaskUnFinishPeople(@Body RequestBody body);


    //获取完成的执行人
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<ResponseCompeteBean>getTaskFinishPeople(@Body RequestBody body);


    //获取会议列表数据
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<ResponseMeetingEntity>getMeetingList(@Body RequestBody body);

    //获取会议详情

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<ResponseMeetDetailEntity>getMeetingDetail(@Body RequestBody body);


    //获取添加会议纪要

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<CreateTaskEntity>addMeetingRecord(@Body RequestBody body);


    //获取会议人员的参会状态

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<ResponseJoinBean>getJoinStatus(@Body RequestBody body);

    //参加会议
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<CreateTaskEntity>JoinMeet(@Body RequestBody body);
    //取消会议
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<CreateTaskEntity>DisMeet(@Body RequestBody body);

    //关闭会议
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<CreateTaskEntity>CompeteMeet(@Body RequestBody body);



}
