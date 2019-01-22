package com.zhixing.masslib;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.BannerPager;
import com.base.zhixing.www.widget.PagerSlidingTabStrip;
import com.base.zhixing.www.widget.RecycleViewDivider;
import com.base.zhixing.www.widget.scrollablelayoutlib.MyFragmentPagerAdapter;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollAbleFragment;
import com.zhixing.masslib.adapter.LeftListAdapter;
import com.zhixing.masslib.bean.MassItemBean;
import com.zhixing.masslib.bean.WxItem;
import com.zhixing.masslib.chart.cj.CjLineOk_Chart;
import com.zhixing.masslib.chart.cj.Cj_Chart;
import com.zhixing.masslib.chart.qj.QJ_Chart;
import com.zhixing.masslib.chart.qj.QJ_Line_Chart;
import com.zhixing.masslib.chart.sj.Sj_Chart;
import com.zhixing.masslib.chart.sj.Sj_Line_Chart;
import com.zhixing.masslib.chart.wx.WX_Chart;
import com.zhixing.masslib.chart.wx.WX_Chart1;
import com.zhixing.masslib.chart.wx.WX_Chart2;
import com.zhixing.masslib.chart.wx.WX_Chart3;
import com.zhixing.masslib.frame.MassFrame0;
import com.zhixing.masslib.frame.MassFrame1;
import com.zhixing.masslib.util.Common;
import com.zhixing.masslib.util.SyLinearLayoutManager;
import com.zhixing.masslib.widget.AddWxList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进入质量管理界面
 */
public class EnterMassActivity extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.first_mass_layout;
    }
    private RelativeLayout add;

    /**
     * 添加维修小项
     * @param item
     * @param bean
     */
    private void addWxItem(WxItem item, int pos){
        /*
        ProductCode：产品型号
WorkNo：工单号
AbnormalCause：不良现象id
RepairContent：不良原因id
Result：维护结果
CreatePerson:维修人
RepairCount：维修数量
TenantId：租列号

         */
        MassItemBean bean = massItemBeans.get(pos);
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "EditAddRepairCount");
        params.put("ProductCode",bean.getProductCode());
        params.put("AbnormalCause",item.getNoC());
        params.put("RepairContent",item.getOkC());
        params.put("Result","报废");
        params.put("CreatePerson",SharedPreferencesTool.getMStool(EnterMassActivity.this).getUserCode()+SharedPreferencesTool.getMStool(EnterMassActivity.this).getUserName());
        params.put("RepairCount",item.getNum());
        params.put("WorkNo",bean.getNo());

        params.put("TenantId",SharedPreferencesTool.getMStool(EnterMassActivity.this).getTenantId());
        httpPostSONVolley(SharedPreferencesTool.getMStool(EnterMassActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                    //在这里进行数据保存
                ArrayList<WxItem> x =  massItemBeans.get(pos).getWxItems();
                x.add(item);
                getHandler().sendEmptyMessage(2);
            }

            @Override
            public void error(VolleyError error) {

            }
        });

    }

    @Override
    public void process(Message msg) {
            switch (msg.what){
                case 31:
                    //维修类目
                    int pos = msg.arg1;
                    AddWxList wxList = new AddWxList(EnterMassActivity.this,massItemBeans.get(pos));
                    wxList.setR(wxItem -> {
                        addWxItem(wxItem,pos);
                    });
                    wxList.showSheet();
                    break;
                case -21:
                    String temps [] = ((String)msg.obj).split("#");
                    String picNo = "";
                    if(temps.length==3){
                        picNo = temps[2];
                    }
                    startTime = temps[0];
                    endTime = temps[1];
                    P.c(temps[0]+"==="+temps[1]+"=="+picNo);
                    loadDataCjRight(picNo);
                    break;
                case -1:
                    //右边
                    String time = (String) msg.obj;
                    loadDataRight(time);
                    break;
                case -2:
                    fragment1.updataData(massItemBeans1);
                    break;
                case 1:

                    break;
                case 2:
                    //装载全检数据接口
                    fragment0.updataData(massItemBeans);
                    break;
                case 4:
                    //frame0 刷新
                    loadData(String.valueOf(type),NOW_WORKNO);
                    break;
            }
    }
    private String startTime,endTime;
    private TextView tetle_text,tetle_tv1;
    private RelativeLayout title_rl;
    private PagerSlidingTabStrip strip;
    private BannerPager data_pager;
    private TextView title,title_btn;
    private String titleValue,leftT,rightT;
    private int type = -1;
    private String title_btn_Value ;
    private String NOW_WORKNO;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        titleValue = intent.getStringExtra("title");
        leftT = intent.getStringExtra("left_t");
        rightT  = intent.getStringExtra("right_t");
        type = intent.getIntExtra("type",-1);
        title_btn_Value = intent.getStringExtra("btn_t");
        super.onCreate(savedInstanceState);
    }
    private void showInfo(int type){
        switch (type){

            case 0:
                loadData(String.valueOf(type),SharedPreferencesTool.getMStool(EnterMassActivity.this).getString("mass_cache"+type));
                break;
            case 1:
                //mass_cache是记录不同模块的最后一次访问记录
                loadData(String.valueOf(type),SharedPreferencesTool.getMStool(EnterMassActivity.this).getString("mass_cache"+type));
                break;
            case 2:
                PC_NO = SharedPreferencesTool.getMStool(EnterMassActivity.this).getString("mass_cache"+type+"PC_NO");
                PC_NUM = SharedPreferencesTool.getMStool(EnterMassActivity.this).getString("mass_cache"+type+"PC_NUM");
                loadData(String.valueOf(type),SharedPreferencesTool.getMStool(EnterMassActivity.this).getString("mass_cache"+type));
                break;
            case 3:
                loadData(String.valueOf(type),SharedPreferencesTool.getMStool(EnterMassActivity.this).getString("mass_cache"+type));
                break;

        }
    }
    private ArrayList<MassItemBean> massItemBeans = new ArrayList<>();
    private ArrayList<MassItemBean> massItemBeans1 = new ArrayList<>();

    /**
     * 左边查询
     * @param CHECK_TYPE
     * @param ret
     */
    private  void loadData(String CHECK_TYPE,String ret ){
        if(ret.length()==0){
            return;
        }
        NOW_WORKNO = ret;
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetWorkBasicCheckInfo");
        params.put("WorkNO",ret);//WORK015149
        params.put("PlanDate",TimeUtil.getTimeCh(System.currentTimeMillis()));
        params.put("TenantId",SharedPreferencesTool.getMStool(EnterMassActivity.this).getTenantId());
        httpPostVolley(SharedPreferencesTool.getMStool(EnterMassActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                //记录质量管理的最近使用
                SharedPreferencesTool.getMStool(EnterMassActivity.this).setString("mass_cache"+CHECK_TYPE,ret);
                if(CHECK_TYPE.equals("2")){
                    //处理抽检
                    SharedPreferencesTool.getMStool(EnterMassActivity.this).setString("mass_cache"+CHECK_TYPE+"PC_NO",PC_NO);
                    SharedPreferencesTool.getMStool(EnterMassActivity.this).setString("mass_cache"+CHECK_TYPE+"PC_NUM",PC_NUM);
                }
                JSONArray array = null;
                try {
                    array = jsonObject.getJSONArray("rows");
                    int len  = array.length();
                    massItemBeans.clear();
                    for(int i=0;i<len;i++){
                        JSONObject object = array.getJSONObject(i);
                        MassItemBean bean = new MassItemBean();
                        bean.setNo(object.getString("WorkSheetNo"));
                        bean.setWork(object.getString("WorkShopName"));
                        bean.setLine(object.getString("LineName"));
                        bean.setProduct(object.getString("ProductName"));
                        bean.setProductCode(object.getString("ProductCode"));
                        bean.setCount(object.getInt("PlanCount")*1000);
                        bean.setLineCode(object.getString("LineCode"));
                        bean.setLineId(object.getString("LineId"));
                        bean.setSingleQuantity(object.getString("SingleQuantity"));
                        bean.setCustomer(object.getString("Customer"));
                        bean.setCreateDate(TimeUtil.getTime(TimeUtil.parseTimeC(object.getString("CreateDate"))));
                        if(type==2){
                            //抽检处理
                            bean.setpNo(PC_NO);
                            bean.setpNum(PC_NUM);
                           // bean.setCreatePerson(object.getString("createPerson"));
                        }
                        String time = object.getString("PlanDate");
                        bean.setAll_t(TimeUtil.getTime(TimeUtil.parseTimeC(time)));
                        String[] tps = time.split("T");
                        bean.setData(tps[0]);
                        bean.setTime(tps[1]);
                        if(CHECK_TYPE.equals("3")){
                           ArrayList<WxItem> wxItems =new ArrayList<>();
                           bean.setWxItems(wxItems);
                        }
                        massItemBeans.add(bean);

                    }
                    getHandler().sendEmptyMessage(2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void error(VolleyError error) {

            }
        },false);
    }

    /**
     * 全检右边查询
     * @param time
     */
    private  void loadDataRight(String time ){
            Map<String,String> params  = new HashMap<>();
            params.put("AppCode", Common.APPCODE);
            params.put("ApiCode", "GetWorkBasicCheckInfo");
            params.put("WorkNO","");//WORK015149
            params.put("PlanDate",time);
            params.put("TenantId",SharedPreferencesTool.getMStool(EnterMassActivity.this).getTenantId());



        httpPostVolley(SharedPreferencesTool.getMStool(EnterMassActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray array = null;
                try {
                    array = jsonObject.getJSONArray("rows");
                    int len  = array.length();
                    massItemBeans1.clear();
                    for(int i=0;i<len;i++){
                        JSONObject object = array.getJSONObject(i);
                        MassItemBean bean = new MassItemBean();
                        bean.setNo(object.getString("WorkSheetNo"));
                        bean.setWork(object.getString("WorkShopName"));
                        bean.setLine(object.getString("LineName"));
                        bean.setProduct(object.getString("ProductName"));
                        bean.setProductCode(object.getString("ProductCode"));
                        bean.setCount(object.getInt("PlanCount")*1000);
                        bean.setLineCode(object.getString("LineCode"));
                        bean.setLineId(object.getString("LineId"));
                        String time = object.getString("PlanDate");
                        bean.setAll_t(time);
                        String[] tps = time.split("T");
                        bean.setData(tps[0]);
                        bean.setTime(tps[1]);
                        massItemBeans1.add(bean);

                    }
                    getHandler().sendEmptyMessage(-2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void error(VolleyError error) {

            }
        },false);
    }

    /**
     * 抽检数据加载
     */
    private void loadDataCjRight(String picNo){
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetRandomPicCheckList");
        params.put("workNO","");//WORK015149
        params.put("productCode","");
        params.put("picNO",picNo);
        params.put("state","");
        params.put("beginDate",startTime);
        params.put("endDate",endTime);
        params.put("TenantId",SharedPreferencesTool.getMStool(EnterMassActivity.this).getTenantId());
        httpPostSONVolley(SharedPreferencesTool.getMStool(EnterMassActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                P.c("抽检右边");
                JSONArray array = null;

                try {
                    array = jsonObject.getJSONArray("rows");
                    int len  = array.length();
                    massItemBeans1.clear();
                    for(int i=0;i<len;i++){
                        JSONObject object = array.getJSONObject(i);
                        MassItemBean bean = new MassItemBean();
                        bean.setNo(object.getString("workNO"));
                        bean.setpNo(object.getString("picNO"));
                        bean.setCreatePerson(object.getString("createPerson"));
                        bean.setCount(object.getInt("picCount"));
                        bean.setState(object.getInt("state"));
                        String time = object.getString("createTime");

                        bean.setAll_t(time);
                        String[] tps = time.split("T");
                        bean.setData(tps[0]);
                        bean.setTime(tps[1]);
                        massItemBeans1.add(bean);

                    }
                    getHandler().sendEmptyMessage(-2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                }

            @Override
            public void error(VolleyError error) {

            }
        });

    }

    //
    private ArrayList<String> menus = new ArrayList<>();
    private void init(int type){
        menus.clear();
            switch (type){
                case 0:
                    menus.add("产品型号不合格率柏拉图");
                    menus.add("首件不合格率趋势图");
                    break;
                case 1:
                    menus.add("总体不良柏拉图");
                    menus.add("工单层别不良现象柏拉图");
                    menus.add("产品型号不良现象柏拉图 ");
                    menus.add("不良数量产品型号柏拉图 ");
                    menus.add("不良率产品型号柏拉图 ");
                    menus.add("总体不良率趋势图");
                    menus.add("不良现象趋势图");
                    menus.add("产品型号不良趋势图");
                    break;
                case 2:
                    menus.add("产品型号不合格率柏拉图");
                    menus.add("时间合格率趋势图");
                    break;
                case 3:

                    menus.add("不良现象总体柏拉图");
                    menus.add("不良原因总体柏拉图");
                    menus.add("不良现象工单柏拉图");
                    menus.add("不良原因工单柏拉图");
                    menus.add("不良率产品型号柏拉图");
                    menus.add("不良现象产品型号总体柏拉图");
                    menus.add("不良原因产品型号总体柏拉图");

                    break;
            }


    }
    private LeftListAdapter leftListAdapter;
    private RecyclerView left_list;
    @Override
    public void initLayout() {
        setStatus(-1);
        title = findViewById(R.id.title);
        tetle_tv1 = findViewById(R.id.tetle_tv1);
        title_rl = findViewById(R.id.title_rl);
        tetle_text = findViewById(R.id.tetle_text);
        title_btn = findViewById(R.id.title_btn);
        drawerLayout = findViewById(R.id.content0);
        tetle_text.setText(titleValue);
        add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (type){
                    case 0:
                        intent = new Intent(EnterMassActivity.this,AddMassActivity.class);
                        break;
                    case 1:
                        intent = new Intent(EnterMassActivity.this,AddMassActivity1.class);
                        break;
                    case 2:
                        intent = new Intent(EnterMassActivity.this,AddMassActivity2.class);
                        break;
                    case 3:
                        intent = new Intent(EnterMassActivity.this,AddMassActivity3_.class);
                        break;
                }
                startActivityForResult(intent,type);

            }
        });
        left_list = findViewById(R.id.left_list);
        SyLinearLayoutManager manager = new SyLinearLayoutManager(EnterMassActivity.this,LinearLayoutManager.VERTICAL,false);
        leftListAdapter = new LeftListAdapter(EnterMassActivity.this,menus,getHandler());
        left_list.addItemDecoration(new RecycleViewDivider(EnterMassActivity.this,LinearLayoutManager.HORIZONTAL,1,getResources().getColor(R.color.content_line)));
        left_list.setLayoutManager(manager);
        left_list.setAdapter(leftListAdapter);
        title_rl.setVisibility(View.VISIBLE);
        tetle_tv1.setText("记录分析");
        title_btn.setText(title_btn_Value);
        strip = findViewById(R.id.strip);
        data_pager = findViewById(R.id.data_pager);
        initFragmentPager(data_pager,strip);
        tetle_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init(type);
                leftListAdapter.updata(menus);
                drawerLayout.openDrawer(Gravity.LEFT);
                switch (type) {
                    case 0:
                        leftListAdapter.setOnItemClick(pos -> {
                            Intent intent = null;
                            switch (pos) {
                                case 0:


                                    intent = new Intent(EnterMassActivity.this, Sj_Chart.class);
                                    intent.putExtra("titleName", menus.get(pos));
                                    intent.putExtra("index", pos);
                                    break;
                                case 1:
                                    intent = new Intent(EnterMassActivity.this, Sj_Line_Chart.class);
                                    intent.putExtra("titleName", menus.get(pos));
                                    intent.putExtra("index", pos);

                                    break;
                            }
                            startActivity(intent);
                        });
                        break;
                    case 1:

//                        Intent intent = new Intent(EnterMassActivity.this,QJ_Chart.class);
//                        startActivity(intent);
                        leftListAdapter.setOnItemClick(new LeftListAdapter.onItemClick() {
                            @Override
                            public void clickItem(int pos) {
                                Intent intent = null;
                                switch (pos){
                                    case 0:
                                    case 1:
                                    case 2:
                                    case 3:
                                    case 4:
                                        intent = new Intent(EnterMassActivity.this,QJ_Chart.class);
                                        intent.putExtra("titleName",menus.get(pos));
                                        intent.putExtra("index",pos);

                                        break;
                                    case 5:
                                    case 6:
                                    case 7:
                                        intent = new Intent(EnterMassActivity.this,QJ_Line_Chart.class);
                                        intent.putExtra("titleName",menus.get(pos));
                                        intent.putExtra("index",pos);

                                        break;

                                }
                                startActivity(intent);
                            }
                        });
                        break;
                    case 2:
                        leftListAdapter.setOnItemClick(new LeftListAdapter.onItemClick() {
                            @Override
                            public void clickItem(int pos) {
                                Intent intent = null;
                                switch (pos){
                                    case 0:
                                        intent = new Intent(EnterMassActivity.this,Cj_Chart.class);
                                        intent.putExtra("titleName",menus.get(pos));
                                        intent.putExtra("index",pos);
                                        break;
                                    case 1:
                                        intent = new Intent(EnterMassActivity.this,CjLineOk_Chart.class);
                                        intent.putExtra("titleName",menus.get(pos));
                                        intent.putExtra("index",pos);
                                        break;
                                }
                                startActivity(intent);
                            }
                        });
                        break;
                    case 3:
                        leftListAdapter.setOnItemClick(new LeftListAdapter.onItemClick() {
                            @Override
                            public void clickItem(int pos) {
                                Intent intent = null;
                                switch (pos){
                                    case 0:
                                    case 2:
                                        intent = new Intent(EnterMassActivity.this,WX_Chart.class);
                                        intent.putExtra("titleName",menus.get(pos));
                                        intent.putExtra("index",pos);
                                        break;
                                    case 1:
                                    case 3:
                                        intent = new Intent(EnterMassActivity.this,WX_Chart1.class);
                                        intent.putExtra("titleName",menus.get(pos));
                                        intent.putExtra("index",pos);
                                        break;
                                    case 4:
                                        intent = new Intent(EnterMassActivity.this,WX_Chart2.class);
                                        intent.putExtra("titleName",menus.get(pos));
                                        intent.putExtra("index",pos);
                                        break;
                                    case 5:
                                    case 6:
                                        intent = new Intent(EnterMassActivity.this,WX_Chart3.class);
                                        intent.putExtra("titleName",menus.get(pos));
                                        intent.putExtra("index",pos);
                                        break;
                                }
                                startActivity(intent);
                            }
                        });
                        break;
                }
            }
        });
        showInfo(type);
    }
    final ArrayList<ScrollAbleFragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    private MassFrame0 fragment0;
    private MassFrame1 fragment1;
    private void initFragmentPager(final ViewPager viewPager,PagerSlidingTabStrip strip){
        fragmentList.clear();
        fragment0 =  MassFrame0.newInstance();
        fragment0.setHandler(getHandler());
        fragment0.setType(type);
        fragment0.updataData(massItemBeans);
       // fragment0.init(types0);
        fragmentList.add(fragment0);
        titleList.clear();
        titleList.add(leftT);
        //暂时关闭首件和维修的右边页面
        if(type!=3&&type!=0){
            fragment1 =  MassFrame1.newInstance();
            fragment1.setHandler(getHandler());
            //  fragment1.init(types1);
            fragment1.setType(type);
            fragment1.updataData(massItemBeans1);
            fragmentList.add(fragment1);
            // fragmentList.add(WebViewFragment.newInstance());
            ;
            //  titleList.add("ScrollView");
            titleList.add(rightT);
        }
        //  fragmentList.add(ScrollViewFragment.newInstance());

        // titleList.add("WebView");
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
        strip.setViewPager(viewPager);
        strip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }
            @Override
            public void onPageSelected(int position) {


                getHandler().sendEmptyMessage(3);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
        getHandler().sendEmptyMessage(3);

    }
    //抽检批次数量
    private String PC_NO;//批次号
    private String PC_NUM;//批次数量
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(requestCode==0&&resultCode==1000){
           if( data.hasExtra("ret")){
               //进行查询
               //扫码返回的
               loadData(String.valueOf(requestCode),data.getStringExtra("ret"));
           }
       }else if(requestCode==1&&resultCode==1000){
            //全检
          if( data.hasExtra("ret")){
              //进行查询
                //扫码返回的
              loadData(String.valueOf(requestCode),data.getStringExtra("ret"));
          }
       }else  if(requestCode==2&&resultCode==1000){
        //抽检
            if(data.hasExtra("ret")){
                PC_NO = data.getStringExtra("pNo");
                PC_NUM = data.getStringExtra("pNum");
                loadData(String.valueOf(requestCode),data.getStringExtra("ret"));
            }

       }else  if(requestCode==3&&resultCode==1000){
//        维修

           loadData(String.valueOf(requestCode),data.getStringExtra("ret"));

       }
    }

}
