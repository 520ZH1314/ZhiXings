package com.zhixing.tpmlib.service;
import com.base.zhixing.www.util.UrlUtil;
import com.zhixing.tpmlib.bean.PicturesBean;
import com.zhixing.tpmlib.bean.PlanDatailBean;

import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
/*
 * @Author smart
 * @Date 2019/1/15
 * @Des 接口地址请求参数
 */
public interface RetrofitInterface {
    @FormUrlEncoded
    @POST("UploadPhoto.ashx")
    Observable<PicturesBean> getPicturesBean(@FieldMap Map<String, String> map);
    @FormUrlEncoded
    @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
    Observable<PlanDatailBean> getPlanDatailBean(@FieldMap Map<String, String> map);
}
