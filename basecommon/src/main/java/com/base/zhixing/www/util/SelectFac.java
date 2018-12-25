package com.base.zhixing.www.util;

import android.app.Activity;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.common.T;
import com.base.zhixing.www.inter.JsRet;
import com.base.zhixing.www.inter.ScreenSelect;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CommonSetSelectPop;

import org.json.JSONException;
import org.json.JSONObject;


public class SelectFac {
    Activity mContxt;
    private WebView commonView;
    private SharedUtils sharedUtils;

    /**
     *
     * @param mContxt
     * @param handler
     * @param commonView
     */
    public SelectFac(Activity mContxt, Handler handler, WebView commonView) {
        this.mContxt = mContxt;
        this.commonView =commonView;
        sharedUtils = new SharedUtils(T.SET_F);
    }

    private boolean isSave = false;
    /**
    * @author cloor
    * @time   2018-12-25 11:36
    * @describe  : 是否覆盖本地保存数据
    */
    public void setSaveInfo(boolean isSave){
        this.isSave = isSave;
    }
    @JavascriptInterface
    public void selectScreen(final int STEP){
        P.c("深度"+STEP);
        if(STEP<1||STEP>4){
            Toasty.INSTANCE.showToast(mContxt,"参数传入错误");
            return;
        }
        final int stemp = 1;
        final StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContxt,null,"工厂");
        setSelectPop.getSet().put("ApiCode", "GetFactoryList");
        setSelectPop.setMidH(true);
        if(stemp==STEP){
            setSelectPop.isDoall(false);
        }

        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                if(isSave){
                    sharedUtils.setStringValue("factory_id",id);
                    sharedUtils.setStringValue("factory_name",name);
                }
                if(STEP==stemp){
                    if(commonView==null){
                        if(screenSelect!=null){
                            screenSelect.select(new String[]{id,code,name},null,null,null);
                        }
                    }else {
                        if (jsRet != null) {
                            jsRet.result(elements, screenBack(elements, new String[]{id, code, name}, null, null, null));

                        }
                    }


                }else{
                    selectScreen0(new String[]{id,code,name},elements,STEP);
                }
            }

        });
        setSelectPop.showSheet();
    }
    private void selectScreen0(final String id0[], final StackTraceElement[] elements, final int STEP){

        final int stemp = 2;
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContxt,null,"车间");
        setSelectPop.getSet().put("ApiCode", "GetWorkShopList");
        setSelectPop.getSet().put("FactoryId",id0[0]);
        if(STEP==stemp){
            setSelectPop.isDoall(false);
        }
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                if(isSave){
                    sharedUtils.setStringValue("workshop_id",id);
                    sharedUtils.setStringValue("workshop_code",code);
                    sharedUtils.setStringValue("workshop_name",name);
                }
                if(STEP==stemp){


                    if(commonView==null){
                        if(screenSelect!=null){
                            screenSelect.select(id0,new String[]{id,code,name},null,null);
                        }
                    }else {
                        if (jsRet != null) {
                            jsRet.result(elements, screenBack(elements, id0, new String[]{id, code, name}, null, null));

                        }
                    }


                }else{
                    selectScreen1(id0,new String[]{id,code,name},elements,STEP);
                }
            }
        });
        setSelectPop.showSheet();
    }

    private void selectScreen1(final String id0[], final String id1[], final StackTraceElement[] elements, final int STEP ){
        final int stemp = 3;
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContxt,null,"产线");
        setSelectPop.getSet().put("ApiCode", "GetLineList");
        setSelectPop.getSet().put("WorkShopId",id1[0]);
        P.c("是什么"+(STEP==stemp));
        if(STEP==stemp){
            setSelectPop.isDoall(false);
        }

        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                if(isSave){
                    sharedUtils.setStringValue("line_id",id);
                    sharedUtils.setStringValue("line_name",name);
                }

                if(STEP==stemp){
                    if(commonView==null){
                        if(screenSelect!=null){
                            screenSelect.select(id0,id1,new String[]{id,code,name},null);
                        }
                    }else {
                        if (jsRet != null) {
                            jsRet.result(elements,screenBack(elements, id0, id1, new String[]{id, code, name}, null));

                        }
                    }



                }else{
                    selectScreen2(id0,id1,new String[]{id,code,name},elements);
                }

            }


        });
        setSelectPop.showSheet();
    }

    private ScreenSelect screenSelect;
    public void setScreenListen(ScreenSelect screenSelect){
        this.screenSelect = screenSelect;
    }
    private void selectScreen2(final String id0[], final String id1[], final String id2[], final StackTraceElement[] elements){

        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContxt,null,"工位");
        setSelectPop.getSet().put("ApiCode", "GetLineStationList");
        setSelectPop.getSet().put("LineId", id2[0]);
        setSelectPop.isDoall(false);
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {

                if(isSave){
                    sharedUtils.setStringValue("station_id",id);
                    sharedUtils.setStringValue("station_name",name);
                }
                if(commonView==null){
                    if(screenSelect!=null){
                        screenSelect.select(id0,id1,id2,new String[]{id,code,name});
                    }
                }else{
                   if(jsRet!=null){
                       jsRet.result(elements, screenBack(elements,id0,id1,id2,new String[]{id,code,name}));
                      ;
                   }
                }
            }
        });
        setSelectPop.showSheet();
    }
    private JsRet jsRet;
    /**
    * @author cloor
    * @time   2018-12-25 11:35
    * @describe  : 返回js需要的json串
    */
    public void setJsRet(JsRet jsRet){
        this.jsRet = jsRet;
    }
    private String screenBack( final StackTraceElement[] elements,String id0[],String id1[],String id2[],String id3[]){
        JSONObject object = new JSONObject();
        try {
            if(id0!=null){
                object.put("factory",id0[0]);
                object.put("factory_name",id0[2]);
            }

            if(id1!=null){
                object.put("work",id1[0]);
                object.put("work_name",id1[2]);
            }

            if(id2!=null){
                object.put("line",id2[0]);
                object.put("line_name",id2[2]);
            }

            if(id3!=null){
                object.put("station",id3[0]);
                object.put("station_name",id3[2]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  object.toString();

    }
}
