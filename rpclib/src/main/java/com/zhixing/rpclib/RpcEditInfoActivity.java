package com.zhixing.rpclib;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.common.T;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.BannerPager;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.base.zhixing.www.widget.scrollablelayoutlib.MyFragmentPagerAdapter;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollAbleFragment;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollableLayout;
import com.google.gson.Gson;
import com.zhixing.beans.NoItem;
import com.zhixing.beans.TxtInfo;
import com.zhixing.common.Common;
import com.zhixing.view.CommonNoSelectPop;
import com.zhixing.view.CommonStatetPop;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
public class RpcEditInfoActivity extends BaseRpcActivity {
    @Override
    public int getLayoutId() {

        return R.layout.rpc_edit_activity;
    }

    @Override
    public void backActivity(View v) {
        super.backActivity(v);
        if(txtInfo!=null){
            if( txtInfo.getState()==3){
                sharedUtils.clear("nos");
                sharedUtils.clear("nos_id");
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(txtInfo!=null){
               if( txtInfo.getState()==3){
                   sharedUtils.clear("nos");
                   sharedUtils.clear("nos_id");
               }

            }
            AppManager.getAppManager().finishActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @BindView(R2.id.submit_lay)
    LinearLayout submit_lay;
    @BindView(R2.id.change_state)
    LinearLayout change_state;
    @Override
    public void process(Message msg) {
        switch (msg.what) {
            case 1:
                if(noItems.size()==0){
                    Toasty.INSTANCE.showToast(RpcEditInfoActivity.this,"当前无可用工单");
                }

                CommonNoSelectPop selectPop = new CommonNoSelectPop(RpcEditInfoActivity.this, getHandler(), "请选择工单");
                selectPop.setData(noItems);
                selectPop.setSelect(new SetSelect() {
                    @Override
                    public void select(String id, String code, String name) {

                        sharedUtils.setStringValue("nos", name);
                        sharedUtils.setStringValue("nos_id",code);
                        item4.setText(sharedUtils.getStringValue("nos"));
                        getNoInfos(name,false);
                    }
                });
                selectPop.showSheet();
                break;
            case 2:

                STATU = txtInfo.getState();
                changeChStatue();
                fragment0.changeTextInfo(txtInfo);


                break;
            case 4:
                STATU = txtInfo.getState();
                changeChStatue();
                fragment0.changeTextInfo(txtInfo);
                fragment0.setGoT();
                break;
            case 3:

                String ad[] = (String[]) msg.obj;
                addLeiji(TextUtils.isEmpty(ad[0])?"0":ad[0],TextUtils.isEmpty(ad[1])?"0":ad[1]);
                fragment0.resetNg();
                break;
            case 5:
                //代替submit的提交操作,
                addUpph(fragment0.getItem14(), fragment0.getItem15());
                break;
        }
    }
    private void addLeiji(String edi0,String c){
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "EditRPCOrderQtyData");
        params.put("TenantId", SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getTenantId());
        params.put("LineId", sharedUtils.getStringValue("line_id"));
        params.put("OrderNo",txtInfo.getOrderNo());
        params.put("LastTotalQty",String.valueOf(txtInfo.getQty()));
        params.put("ChangeTotalQty",edi0);
        params.put("ID",sharedUtils.getStringValue("nos_id"));
        params.put("LastNGQty",String.valueOf(txtInfo.getQtyNG()));
        params.put("ChangeNGQty",c);
        params.put("CreateDate",TimeUtil.getTime(System.currentTimeMillis()));
        params.put("UserId",SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getUserId());
        httpPostVolley(SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                parseInfo(jsonObject,2,false);
                P.c("数量"+txtInfo.getQty());

            }

            @Override
            public void error(VolleyError error) {

            }
        },"提交数据");
    }

    @BindView(R2.id.tetle_text)
    TextView tetle_text;
    @BindView(R2.id.prc_corl)
    BannerPager data_pager;
    @BindView(R2.id.prc_sc)
    ScrollableLayout prc_sc;
    @BindView(R2.id.item4)
    TextView item4;

    @Override
    public void newIniLayout() {
        setStatus(-1);
        tetle_text.setText("数据录入");

        initFragmentPager(data_pager, prc_sc);
        if(sharedUtils.getStringValue("nos").length()!=0){
            getNoInfos(sharedUtils.getStringValue("nos"),true);
        }
    }

    final ArrayList<ScrollAbleFragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    private RpcFrame0 fragment0;
    private SharedUtils sharedUtils, parentShared;

    private void initFragmentPager(final ViewPager viewPager, final ScrollableLayout mScrollLayout) {
        sharedUtils = new SharedUtils(Common.SHARED_);
        parentShared = new SharedUtils(T.SET_F);
        fragmentList.clear();
        fragment0 = RpcFrame0.newInstance();
        fragment0.setHandler(getHandler());

        fragmentList.add(fragment0);
        //  fragmentList.add(ScrollViewFragment.newInstance());

        // fragmentList.add(WebViewFragment.newInstance());
        titleList.clear();
        titleList.add(" ");
        titleList.add(" ");
        // titleList.add("WebView");
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));


//        mScrollLayout.requestScrollableLayoutDisallowInterceptTouchEvent(true);
        mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
        viewPager.setCurrentItem(0);

        mScrollLayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                P.c(currentY + "==" + maxY + "==" + mScrollLayout.canPtr());


            }
        });


    }

    @OnClick({R2.id.layout0, R2.id.layout3, R2.id.layout4,R2.id.ch0, R2.id.ch1, R2.id.ch2, R2.id.ch3,R2.id.submit,R2.id.change_state})
    public void onViewClicked(View view) {
        int i = view.getId();
        P.c("点击" + i);
        if (R.id.layout0 == i) {
            //车间
            if (parentShared.getStringValue("factory_id").length() == 0) {
                Toasty.INSTANCE.showToast(RpcEditInfoActivity.this, "请先在基本设置中选择工厂");
                return;
            }
            CommonSetSelectPop setSelectPop = new CommonSetSelectPop(RpcEditInfoActivity.this, getHandler(), "车间");
            setSelectPop.getSet().put("ApiCode", "GetWorkShopList");
            setSelectPop.getSet().put("FactoryId", parentShared.getStringValue("factory_id"));
            setSelectPop.setMidH(true);
            setSelectPop.setSelect(new SetSelect() {
                @Override
                public void select(String id, String code, String name) {
                    sharedUtils.setStringValue("workshop_id", id);
                    sharedUtils.setStringValue("workshop_code", code);
                    sharedUtils.setStringValue("workshop_name", name);
                    setTe();
                }


            });
            setSelectPop.showSheet();

        } else if (R.id.layout3 == i) {
            //产线
            if (sharedUtils.getStringValue("workshop_id").length() == 0) {
                Toasty.INSTANCE.showToast(RpcEditInfoActivity.this, "请先选择车间");
                return;
            }
            CommonSetSelectPop setSelectPop = new CommonSetSelectPop(RpcEditInfoActivity.this, getHandler(), "产线");
            setSelectPop.getSet().put("ApiCode", "GetLineList");
            setSelectPop.getSet().put("WorkShopId", sharedUtils.getStringValue("workshop_id"));
            setSelectPop.setMidH(true);
            setSelectPop.setSelect(new SetSelect() {
                @Override
                public void select(String id, String code, String name) {

                    if(!sharedUtils.getStringValue("line_id").equals(id)){
                        sharedUtils.clear("nos");
                        sharedUtils.clear("nos_id");
                        item4.setText("");
                    }

                    sharedUtils.setStringValue("line_id", id);
                    sharedUtils.setStringValue("line_name", name);
                    setTe();
                }


            });
            setSelectPop.showSheet();
        } else if (R.id.layout4 == i) {
            loadNos();


        }/*else if(R.id.ch0==i){
            STATU = 0;
            changeChStatue();
        }else if(R.id.ch1==i){
//            STATU = 1;
            //submitState();
            ch1.setChecked(false);
            if(STATU==2){
                submitState(txtInfo.getOrderNo(),1, "","","",TimeUtil.getTime(System.currentTimeMillis()));
            }else{
                submitState(txtInfo.getOrderNo(),1, TimeUtil.getTime(System.currentTimeMillis()),"","","");
            }
//            changeChStatue();
        }else if(R.id.ch2==i){
           // STATU = 2;
           // changeChStatue();
            ch2.setChecked(false);
            submitState(txtInfo.getOrderNo(),2, "","",TimeUtil.getTime(System.currentTimeMillis()),"");
        }else if(R.id.ch3==i){
            ch3.setChecked(false);
            submitState(txtInfo.getOrderNo(),3, "",TimeUtil.getTime(System.currentTimeMillis()),"","");
        }*/else if(R.id.submit==i) {
            addUpph(fragment0.getItem14(), fragment0.getItem15());
        }else if(R.id.change_state==i){
            if(txtInfo==null){
                Toasty.INSTANCE.showToast(RpcEditInfoActivity.this,"请先选择工单号");
                return;
            }
            CommonStatetPop statetPop = new CommonStatetPop(RpcEditInfoActivity.this,getHandler(),STATU);
            statetPop.setMidH(true);
            statetPop.setSelect(new SetSelect() {
                @Override
                public void select(String id, String code, String name) {
                    if(name.equals("1")){
                        if(STATU==2){
                            submitState(txtInfo.getOrderNo(),1, "","","",TimeUtil.getTime(System.currentTimeMillis()));
                        }else{
                            submitState(txtInfo.getOrderNo(),1, TimeUtil.getTime(System.currentTimeMillis()),"","","");
                        }
                    }else if(name.equals("2")){
                        submitState(txtInfo.getOrderNo(),2, "","",TimeUtil.getTime(System.currentTimeMillis()),"");
                    }else if(name.equals("3")){
                        submitState(txtInfo.getOrderNo(),3, "",TimeUtil.getTime(System.currentTimeMillis()),"","");
                    }
                }
            });

            statetPop.showSheet();
        }
    }

    /**
     * 添加不良计数
     * @param uph
     * @param Ratio
     */
        private void  addUpph(String Ratio,String uph){
            Map<String, String> params = new HashMap<>();
            params.put("AppCode", Common.APPCODE);
            params.put("ApiCode", "PostRPCOrderDetailData");
            params.put("TenantId", SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getTenantId());
            params.put("LineId", sharedUtils.getStringValue("line_id"));
            params.put("OrderNo",txtInfo.getOrderNo());
            params.put("ID",sharedUtils.getStringValue("nos_id"));
            params.put("UPH",uph);
            params.put("Ratio",Ratio);
            params.put("UserId",SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getUserId());
            httpPostVolley(SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
                @Override
                public void success(JSONObject jsonObject) {
                          parseInfo(jsonObject,2,false);

                }

                @Override
                public void error(VolleyError error) {

                }
            },"提交数据");
        }



    @BindView(R2.id.item0)
    TextView item0;
    @BindView(R2.id.item3)
    TextView item3;


    public void setTe() {
        item0.setText(sharedUtils.getStringValue("workshop_name"));
        item3.setText(sharedUtils.getStringValue("line_name"));
        item4.setText(sharedUtils.getStringValue("nos"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTe();
    }

    private ArrayList<NoItem> noItems = new ArrayList<>();

    private void loadNos() {

        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetRPCOrderNoData");
        params.put("UserId",SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getUserId());
        params.put("TenantId", SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getTenantId());
        params.put("LineId", sharedUtils.getStringValue("line_id"));
        params.put("IsToday","1");
        httpPostVolley(SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("status").equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        int len = jsonArray.length();
                        noItems.clear();
                        for (int i = 0; i < len; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            NoItem noItem = new NoItem();
                            noItem.setNo(object.getString("OrderNo"));
                            noItem.setName(object.getString("ProductName"));
                            noItem.setStatus(object.getInt("State"));
                            noItem.setID(object.getString("ID"));
                            noItem.setNum(object.getString("BatchPlanCount"));
                            noItem.setKehu(object.getString("BatchCustomer"));
                            noItem.setBatchNo(object.getString("BatchNo"));
                            noItem.setTime(TimeUtil.getTime(TimeUtil.parseTimeC(object.getString("BatchCreateDate"))));
                            noItem.setBatchWorkNo(object.getString("BatchWorkNo"));

                           /* noItem.setBatchCompletedCount(object.getString("BatchCompletedCount"));
                            noItem.setBatchCreateDate(object.getString("BatchCreateDate"));
                            noItem.setBatchCustomer(object.getString("BatchCustomer"));
                            noItem.setBatchId(object.getString("BatchId"));
                            noItem.setBatchNo(object.getString("BatchNo"));
                            noItem.setBatchPlanCount(object.getString("BatchPlanCount"));*/
                            noItems.add(noItem);
                        }
                        getHandler().sendEmptyMessage(1);
                        dismissDialog();

                    }else{
                        Toasty.INSTANCE.showToast(RpcEditInfoActivity.this,jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        }, "获取工单列表");

    }
    TxtInfo txtInfo;
    /**
     * 获得工单详细情况
     * @param id
     */
    private void getNoInfos(String id,boolean auto) {

        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetRPCOrderDetailData");
        params.put("OrderNo", id);
        params.put("ID",sharedUtils.getStringValue("nos_id"));
        params.put("LineId", sharedUtils.getStringValue("line_id"));
        params.put("UserId",SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getUserId());
        params.put("TenantId", SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getTenantId());
        httpPostVolley(SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {

            @Override
            public void success(JSONObject jsonObject) {
                dismissDialog();
                parseInfo(jsonObject,4,auto);
            }

            @Override
            public void error(VolleyError error) {

            }
        }, "信息加载中");
    }

    private void parseInfo(JSONObject jsonObject,int x,boolean auto){
        try {
            if(jsonObject.getString("status").equals("success")){
                if(!auto){
                    Toasty.INSTANCE.showToast(RpcEditInfoActivity.this,"操作成功!");
                }
                Gson gson = new Gson();
                txtInfo =   gson.fromJson( jsonObject.getJSONArray("rows").getJSONObject(0).toString(),TxtInfo.class);
//                if(txtInfo.getState()==3){
//                    sharedUtils.setStringValue("nos_id","0");
//                }else{
                    sharedUtils.setStringValue("nos_id",txtInfo.getID());
//                }

                getHandler().sendEmptyMessage(x);
            }else{
                Toasty.INSTANCE.showToast(RpcEditInfoActivity.this,jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @BindView(R2.id.ch3)
    CheckBox ch3;
    @BindView(R2.id.ch2)
    CheckBox ch2;
    @BindView(R2.id.ch1)
    CheckBox ch1;
    @BindView(R2.id.ch0)
    CheckBox ch0;
    private volatile int STATU = 0;

    /**
     * 改变状态
     */
    private void changeChStatue() {

        switch (STATU) {
            case 0:
//                ch0.setEnabled(true);
//                ch1.setEnabled(true);
//                ch2.setEnabled(true);
//                ch3.setEnabled(true);

                ch0.setChecked(true);
                ch1.setChecked(false);
                ch2.setChecked(false);
                ch3.setChecked(false);
                fragment0.setEdit_rpV(View.GONE);
               // submit_lay.setVisibility(View.GONE);
                break;
            case 1:

//                ch0.setEnabled(false);
//                ch1.setEnabled(true);
//                ch2.setEnabled(true);
//                ch3.setEnabled(true);

                ch0.setChecked(false);
                ch1.setChecked(true);
                ch2.setChecked(false);
                ch3.setChecked(false);
                fragment0.setEdit_rpV(View.VISIBLE);
              //  submit_lay.setVisibility(View.VISIBLE);
                break;
            case 2:

//                ch0.setEnabled(false);
//                ch1.setEnabled(true);
//                ch2.setEnabled(true);
//                ch3.setEnabled(true);

                ch0.setChecked(false);
                ch1.setChecked(false);
                ch2.setChecked(true);
                ch3.setChecked(false);
                fragment0.setEdit_rpV(View.GONE);
               // submit_lay.setVisibility(View.GONE);
                break;
            case 3:
//                ch0.setEnabled(false);
//                ch1.setEnabled(false);
//                ch2.setEnabled(false);
//                ch3.setEnabled(true);
                ch0.setChecked(false);
                ch1.setChecked(false);
                ch2.setChecked(false);
                ch3.setChecked(true);
                fragment0.setEdit_rpV(View.GONE);
               // submit_lay.setVisibility(View.GONE);
                break;
        }

    }

    /**
     * 提交状态
     */
    private void  submitState(String OrderNo,int state,String RealStartDate,String RealEndDate,String StopTime,String RecoverDate){
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "EditRPCOrderStateData");
        params.put("OrderNo",OrderNo);
        params.put("LastState", String.valueOf(STATU));
        params.put("State",String.valueOf(state));
        params.put("RealStartDate",RealStartDate);
        params.put("RealEndDate",RealEndDate);
        params.put("StopTime",StopTime);
        params.put("RecoverDate",RecoverDate);
        params.put("StopTime",String.valueOf(0));
        params.put("ID",sharedUtils.getStringValue("nos_id"));
        params.put("UserId",SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getUserId());
        params.put("LineId", sharedUtils.getStringValue("line_id"));
        params.put("TenantId", SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getTenantId());



        httpPostVolley(SharedPreferencesTool.getMStool(RpcEditInfoActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult(){

            @Override
            public void success(JSONObject jsonObject) {
                dismissDialog();
                parseInfo(jsonObject,2,false);

            }

            @Override
            public void error(VolleyError error) {

            }
        },"工单状态切换中");
    }

}
 