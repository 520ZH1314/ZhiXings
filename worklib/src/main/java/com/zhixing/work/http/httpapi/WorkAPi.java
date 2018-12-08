package com.zhixing.work.http.httpapi;




import com.zhixing.work.activity.CreateTaskActivity;
import com.zhixing.work.bean.CreateTaskEntity;
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


}
