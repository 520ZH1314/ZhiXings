package com.zhixing.tpmlib.api;



import com.zhixing.netlib.base.BaseRepository;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.tpmlib.bean.CheckRecordDataEntity;
import com.zhixing.tpmlib.bean.EquipmentBaseDateEntity;
import com.zhixing.tpmlib.bean.LineStationResponEntity;
import com.zhixing.tpmlib.bean.MaintenanceListDataEntity;
import com.zhixing.tpmlib.bean.MaintenanceRecordEntity;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TpmApi {


    //获取工位信息
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Flowable<BaseResponse<LineStationResponEntity>> getLineStation(@Body RequestBody body);

    //获取保养预警数据
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Flowable<BaseResponse<MaintenanceListDataEntity>> getMaintenanceListData(@Body RequestBody body);

    //获取设备履历基本数据
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Flowable<BaseResponse<EquipmentBaseDateEntity>> getEquipmentBaseData(@Body RequestBody body);



    //获取设备履历的保养记录
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Flowable<BaseResponse<MaintenanceRecordEntity>> getMaintenanceRecordData(@Body RequestBody body);

    //获取设备点检记录

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/CMP/ApiRegistrator/PostApiGateWay")
    Flowable<BaseResponse<CheckRecordDataEntity>> getCheckRecordData(@Body RequestBody body);

}
