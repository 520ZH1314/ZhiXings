package com.zhixing.tpmlib.repertory;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.netlib.base.BaseRepository;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;
import com.zhixing.netlib.base.MyBaseSubscriber;
import com.zhixing.netlib.base.ResponseThrowable;
import com.zhixing.netlib.base.RetrofitClients;
import com.zhixing.netlib.base.RxUtils;
import com.zhixing.tpmlib.api.Api;
import com.zhixing.tpmlib.api.TpmApi;
import com.zhixing.tpmlib.bean.LineStationPostBean;
import com.zhixing.tpmlib.bean.LineStationResponEntity;

import okhttp3.RequestBody;

public class MaintenanceWarnGetStatonRepertory extends MyBaseRepository<LineStationResponEntity> {
    private  Context mContext;

    public MaintenanceWarnGetStatonRepertory(Context context) {
        super(context);
        this.mContext=context;
    }

    //返回默认工位数据
    public MutableLiveData<BaseResponse<LineStationResponEntity>>getLineStationData(String  LineCode){
         String AppCode="EPS";
         String ApiCode="GetLineStationList";
         String TenantId=SharedPreferencesTool.getMStool(mContext).getTenantId();
         String ip=SharedPreferencesTool.getMStool(mContext).getIp();
         LineStationPostBean bean =new LineStationPostBean(AppCode,ApiCode,TenantId,LineCode);
         return  request(Api.getLineStation(bean,mContext,ip)).send().get();
    }




}
