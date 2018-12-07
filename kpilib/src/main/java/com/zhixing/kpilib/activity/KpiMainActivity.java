package com.zhixing.kpilib.activity;

import android.os.Message;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;



import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.zhixing.kpilib.R;

import com.zhixing.kpilib.fragment.BrokenLineFragment;
import com.zhixing.kpilib.fragment.ColumnarFragment;
import com.zhixing.kpilib.http.KpiServer;
import com.zhixing.kpilib.httpbean.FristEntitys;
import com.zhixing.kpilib.httpbean.KpiEntitys;
import com.zhixing.kpilib.httpbean.LeftMenuBean;
import com.zhixing.kpilib.httpbean.MenuEntity;
import com.base.zhixing.www.util.ACache;
import com.zhixing.kpilib.httpbean.MenuEntity;
import com.zhixing.kpilib.httpbean.PostKpiBean;
import com.zhixing.kpilib.utils.Contant;
import com.zhixing.kpilib.utils.GsonUtil;

import com.zhixing.kpilib.utils.RetrofitClients;
import com.zhixing.kpilib.utils.RxUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class KpiMainActivity extends BaseActvity   {

    private RadioButton radButton_Brokenline;
    private RadioButton radButton_Columnar;
    private BrokenLineFragment brokenLineFragment;
    private ColumnarFragment columnarFragment;
    private BaseFragment[] mFragments;
    private int mIndex;
    private DrawerLayout drawer;
    private RecyclerView recyclerView_LeftMenu;
    private List<MenuEntity.RowsBean> menuDatas = new ArrayList<>();//菜单数据
    private MenuAdapter menuAdapter;
    private ACache mCache;
    private TextView textViewClose;
    private String tenantId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main_kpi1;
    }


       //初始化网络数据
     private void initData() {
         LeftMenuBean leftMenuBean =new LeftMenuBean();
         leftMenuBean.setAppCode("CEOAssist");
         leftMenuBean.setApiCode("GetType");
         leftMenuBean.setTenantId(tenantId);
         String json = com.base.zhixing.www.util.GsonUtil.getGson().toJson(leftMenuBean);
         RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

         RetrofitClients.getInstance(this).create(KpiServer.class)
                .getMenuData(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())// 网络错误的异常转换
                .subscribe(new Consumer<MenuEntity>() {
                    @Override
                    public void accept(MenuEntity response) throws Exception {

                        final List<MenuEntity.RowsBean> kpiBean = response.getRows();
                        if (mCache.getAsObject(Contant.MENUDATA)!=null){
                            mCache.remove(Contant.MENUDATA);
                        }
                        String menusJson = GsonUtil.getGson().toJson(kpiBean);
                        //默认缓存
                        mCache.put(Contant.MENUDATA,menusJson);
                        //加载初始数据 默认第一个
                        initFristData(kpiBean);
                        menuAdapter=new MenuAdapter(R.layout.rec_menu_layout,kpiBean) ;
                        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                                drawer.closeDrawers();
                                 initKpiData(i,kpiBean);



                            }
                        });
                            recyclerView_LeftMenu.setAdapter(menuAdapter);

                    }

                });

    }

       //第一次加载数据
     private void initFristData(List<MenuEntity.RowsBean> kpiBean) {

         PostKpiBean postKpiBean=new PostKpiBean();
         postKpiBean.setApiCode("GetParameter");
         postKpiBean.setAppCode("CEOAssist");
         postKpiBean.setCycleCode(kpiBean.get(0).getCycleCode());
         postKpiBean.setTypeCode(kpiBean.get(0).getTypeCode());
         postKpiBean.setTenantId(tenantId);
         String json = com.base.zhixing.www.util.GsonUtil.getGson().toJson(postKpiBean);
         RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

         RetrofitClients.getInstance(this).create(KpiServer.class)
                .getKpiData(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("加载中");
                    }
                })
                .subscribe(new Consumer<KpiEntitys>() {
                    @Override
                    public void accept(KpiEntitys kpiEntity) throws Exception {
                           dismissDialog();
                        List<FristEntitys.RowsBean> fristData=new ArrayList<>();
                        for (KpiEntitys.RowsBean bean :kpiEntity.getRows()) {
                                FristEntitys.RowsBean rowsBean=new FristEntitys.RowsBean();
                                 rowsBean.setData(bean.getData());
                                 rowsBean.setParameter(bean.getParameter());
                                 fristData.add(rowsBean);
                        }
                        EventBus.getDefault().postSticky(new FristEntitys(fristData));

                    }
                });
    }


    //联网加载图表数据
    private void initKpiData(final int i, List<MenuEntity.RowsBean> kpiBean) {

        PostKpiBean postKpiBean=new PostKpiBean();
        postKpiBean.setApiCode("GetParameter");
        postKpiBean.setAppCode("CEOAssist");
        postKpiBean.setCycleCode(kpiBean.get(i).getCycleCode());
        postKpiBean.setTypeCode(kpiBean.get(i).getTypeCode());
        postKpiBean.setTenantId(tenantId);
        String json = com.base.zhixing.www.util.GsonUtil.getGson().toJson(postKpiBean);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

        RetrofitClients.getInstance(this).create(KpiServer.class)
                .getKpiData(body)
                .compose(RxUtils.schedulersTransformer())  // 线程调度
                .compose(RxUtils.exceptionTransformer())   // 网络错误的异常转换
                .subscribe(new Consumer<KpiEntitys>() {
                    @Override
                    public void accept(KpiEntitys kpiEntity) throws Exception {
                        EventBus.getDefault().postSticky(new KpiEntitys(kpiEntity.getTotal(),kpiEntity.getRows(),i));
                        FristEntitys stickyEvent = EventBus.getDefault().getStickyEvent(FristEntitys.class);
                        if (stickyEvent!=null){
                            EventBus.getDefault().removeStickyEvent(stickyEvent);
                        }
                    }
                });


    }

    private void initToobar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView_LeftMenu = (RecyclerView) findViewById(R.id.left_menu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(KpiMainActivity.this);
        recyclerView_LeftMenu.setLayoutManager(layoutManager);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toolbar.setTitle("");
        //setSupportActionBar(toolbar);
        drawer.addDrawerListener(toggle);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);

        toggle.syncState();


//
    }

    private void initView() {
        radButton_Brokenline = (RadioButton) findViewById(R.id.radButton_Brokenline);
        radButton_Columnar = (RadioButton) findViewById(R.id.radButton_Columnar);


        radButton_Brokenline.setOnClickListener(c);
        radButton_Columnar.setOnClickListener(c);
         textViewClose = (TextView) findViewById(R.id.tv_close_kpi);
        textViewClose.setOnClickListener(c);
        brokenLineFragment = new BrokenLineFragment();
        columnarFragment = new ColumnarFragment();
        mFragments = new BaseFragment[]{brokenLineFragment,
                columnarFragment};
        //开启事务

        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();

        //添加首页
        ft.add(R.id.fl_pic_condtion, brokenLineFragment).commit();

        //默认设置为第0个
        setIndexSelected(0);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

           // super.onBackPressed();
        }
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        mCache = ACache.get(this);
         tenantId = SharedPreferencesTool.getMStool(this).getTenantId();
        initView();
        initToobar();

        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();


        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.fl_pic_condtion, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }

        ft.commit();
        //再次赋值
        mIndex = index;


    }
    private View.OnClickListener c = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int i = view.getId();
            if (i == R.id.radButton_Brokenline) {
                setIndexSelected(0);
            } else if(i==R.id.radButton_Columnar){
                setIndexSelected(1);

            }else{
                finish();
            }
        }
    };



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return  false;
//        if (super.onOptionsItemSelected(item)) return true;
//        else return false;
    }

    public class MenuAdapter extends BaseQuickAdapter<MenuEntity.RowsBean, BaseViewHolder> {
        public MenuAdapter(int layoutResId, List<MenuEntity.RowsBean> data) {
            super(layoutResId, data);
        }


        @Override
        protected void convert(BaseViewHolder baseViewHolder, MenuEntity.RowsBean menuItem) {
            baseViewHolder.setText(R.id.tv_kpi_menu, menuItem.getTypeName());
        }
    }





}


