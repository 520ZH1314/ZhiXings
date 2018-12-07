package com.zhixing.kpilib.http;

import com.zhixing.kpilib.httpbean.KpiEntitys;
import com.zhixing.kpilib.httpbean.MenuEntity;

import io.reactivex.Observable;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface KpiServer {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<MenuEntity> getMenuData(
            @Body RequestBody body
                                      );

    //kpi图表数据
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")

    Observable<KpiEntitys>getKpiData(@Body RequestBody body);

}
