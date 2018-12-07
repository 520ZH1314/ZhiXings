package com.zhixing.work.http.httpapi;




import com.zhixing.work.activity.CreateTaskActivity;
import com.zhixing.work.bean.CreateTaskEntity;

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

}
