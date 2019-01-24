package com.shuben.zhixing.www.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.zhixing.tpmlib.activity.DailyCheckActivity;
import com.zhixing.tpmlib.activity.TpmActivity;
import com.zhixing.work.http.base.RetrofitClients;

public class TpmSetting {

    private static TpmSetting mTpmSetting;
    private boolean isSetting = false;
    public static Activity mContext;
    private static SharedUtils sharedUtils;

    private TpmSetting(Activity mContext) {
        this.mContext = mContext;
    }

    public static TpmSetting getInstance(Activity mContext) {

        if (mTpmSetting == null) {
            return new TpmSetting(mContext);
        }
        return mTpmSetting;
    }


    /**
     * @author zjq
     * create at 2019/1/3 下午4:15
     * 判断是否保存Tpm模块的工厂的数据
     */
    public void isSetting() {

        sharedUtils = new SharedUtils("TpmSetting");
        if (sharedUtils.getStringValue("TpmFactoryId").length() == 0) {
            SettingData();
//            getWorkPosition();
        } else {
            isSetting = true;
            Intent intent = new Intent(mContext, TpmActivity.class);
            mContext.startActivity(intent);
        }


    }


    private void SettingData() {
        //设置工厂
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContext, null, "工厂");
        setSelectPop.getSet().put("ApiCode", "GetFactoryList");
        setSelectPop.setMidH(true);
        setSelectPop.isDoall(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                selectScreen0(id);
                String data = id + "," + name;
                sharedUtils.setStringValue("TpmFactoryId", data);
            }
        });
        setSelectPop.showSheet();


    }

    //设置车间
    private void selectScreen0(String id) {
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContext, null, "车间");
        setSelectPop.getSet().put("ApiCode", "GetWorkShopList");
        setSelectPop.getSet().put("FactoryId", id);
        setSelectPop.isDoall(true);
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                selectScreen1(id);
                String data = id + "," + name;
                sharedUtils.setStringValue("TpmWorkShopId", data);
            }
        });
        setSelectPop.showSheet();
    }

    //设置产线
    private void selectScreen1(String WorkShopId) {
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContext, null, "产线");
        setSelectPop.getSet().put("ApiCode", "GetLineList");
        setSelectPop.getSet().put("WorkShopId", WorkShopId);
        setSelectPop.isDoall(false);
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                String data = id + "," + name;
                sharedUtils.setStringValue("LineListId", data);
                sharedUtils.setStringValue("LineListCode", code);
                getLineStationPop(id, code);
            }
        });
        setSelectPop.showSheet();
    }

    /*private void getWorkPosition() {
        CommonSetSelectPop commonSetSelectPop = new CommonSetSelectPop(mContext, null, "产线");
        commonSetSelectPop.setMidH(true);
        commonSetSelectPop.isDoall(true);
        commonSetSelectPop.getSet().put("ApiCode", "GetLineList");
        commonSetSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                sharedUtils.setStringValue("tpmLinecode", code);
                sharedUtils.setStringValue("tpmLineid", id);
                sharedUtils.setStringValue("tpmLineName", name);
                getLineStationPop(id, code);
            }
        });
        commonSetSelectPop.showSheet();

    }*/

    private void getLineStationPop(String id, String Linecode) {
        CommonSetSelectPop commonSetSelectPop = new CommonSetSelectPop(mContext, null, "工位");
        commonSetSelectPop.setMidH(true);
        commonSetSelectPop.isDoall(false);
        commonSetSelectPop.getSet().put("ApiCode", "GetLineStationList");
        commonSetSelectPop.getSet().put("LineCode", Linecode);
        commonSetSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                sharedUtils.setStringValue("tpmStationCode", code);
                sharedUtils.setStringValue("tpmName", name);
                sharedUtils.setStringValue("tpmStationId",id);
                Intent intent = new Intent(mContext, TpmActivity.class);
                mContext.startActivity(intent);
            }
        });
        commonSetSelectPop.showSheet();
    }
}
