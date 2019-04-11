package com.zhixing.employlib.model.gardenplot;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExcellentEmployeePostBean  implements Serializable {


    /**
     * AppCode : EMS
     * ApiCode : GetAllExcellentEmployee
     * TenantId : 00000000-0000-0000-0000-000000000001
     * TeamId : 2ea1bb97-52fe-4e96-9ad8-9fa82110d6e0
     * “Index” : ”0”
     * “PageSize” : ”1000”
     */

    private String AppCode;
    private String ApiCode;
    private String TenantId;
    private String TeamId;
    @SerializedName("“Index”")
    private String _$Index212; // FIXME check this code
    @SerializedName("“PageSize”")
    private String _$PageSize52; // FIXME check this code

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String AppCode) {
        this.AppCode = AppCode;
    }

    public String getApiCode() {
        return ApiCode;
    }

    public void setApiCode(String ApiCode) {
        this.ApiCode = ApiCode;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String TenantId) {
        this.TenantId = TenantId;
    }

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String TeamId) {
        this.TeamId = TeamId;
    }

    public String get_$Index212() {
        return _$Index212;
    }

    public void set_$Index212(String _$Index212) {
        this._$Index212 = _$Index212;
    }

    public String get_$PageSize52() {
        return _$PageSize52;
    }

    public void set_$PageSize52(String _$PageSize52) {
        this._$PageSize52 = _$PageSize52;
    }
}
