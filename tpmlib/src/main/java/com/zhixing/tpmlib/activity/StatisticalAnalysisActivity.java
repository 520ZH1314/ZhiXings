package com.zhixing.tpmlib.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.adapter.TpmTableAdapt;
import com.zhixing.tpmlib.bean.ColumnarBean;
import com.zhixing.tpmlib.bean.EquipmentBaseDateEntity;
import com.zhixing.tpmlib.bean.LineStationResponEntity;
import com.zhixing.tpmlib.bean.MaintenanceListDataEntity;
import com.zhixing.tpmlib.bean.MaintenanceWarnBean;
import com.zhixing.tpmlib.bean.StaticticalAnalAnalyEntity;
import com.zhixing.tpmlib.fragment.TpmClounarTableFragment;
import com.zhixing.tpmlib.fragment.TpmColumnarFragment;
import com.zhixing.tpmlib.viewModel.ColumnarViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *@author zjq
 *create at 2019/1/22 下午4:02
 */
public class StatisticalAnalysisActivity extends BaseTpmActivity {
    private Toolbar toolbar;
    private FrameLayout flPicCondtionTpm;
    private RecyclerView leftMenu;
    private DrawerLayout drawerLayout;
    private ColumnarViewModel mColumnarViewModel;

    private boolean isReplace = false;
    private TpmColumnarFragment tpmColumnarFragment;
    private TpmClounarTableFragment tpmClounarTableFragment;
    private TextView tvType;
    private SharedUtils sharedUtils;
    private String LineListCode;
    private String EquipmentId;
    private List<String> Key;
    private TpmTableAdapt adapt;
    private RecyclerView recyclerView;
    private boolean isFrist = true;
    private ACache aCache;
    private String tpmStationCode;
    private String tpmStationName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_statistical_analysis;

    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void newIniLayout() {
        setStatus(-1);
        aCache = ACache.get(this);
        initView();

        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();
        tpmColumnarFragment = new TpmColumnarFragment();
        //添加首页
        ft.add(R.id.fl_pic_condtion_tpms, tpmColumnarFragment).show(tpmColumnarFragment).commit();
        mColumnarViewModel = ViewModelProviders.of(this).get(ColumnarViewModel.class);

        //        初始化数据
        initData();

        //切换布局
        mColumnarViewModel.getIsReplace().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null) {
                    isReplace = aBoolean;
                }
                if (isReplace) {
                    FragmentTransaction ft =
                            getSupportFragmentManager().beginTransaction();

                    if (tpmColumnarFragment != null) {
                        ft.hide(tpmColumnarFragment);
                    }
                    if (tpmClounarTableFragment == null) {
                        tpmClounarTableFragment = new TpmClounarTableFragment();
                        ft.add(R.id.fl_pic_condtion_tpms, tpmClounarTableFragment);
                    }

                    ft.show(tpmClounarTableFragment).commit();

                } else {
                    FragmentTransaction ft =
                            getSupportFragmentManager().beginTransaction();

                    if (tpmClounarTableFragment != null) {
                        ft.hide(tpmClounarTableFragment);
                    }
                    if (tpmColumnarFragment == null) {
                        tpmColumnarFragment = new TpmColumnarFragment();
                        ft.add(R.id.fl_pic_condtion_tpms, tpmColumnarFragment);
                    }

                    ft.show(tpmColumnarFragment).commit();


                }


            }
        });

        initToobar();

    }


    /**
     * @author zjq
     * create at 2019/1/18 下午2:47
     * 获取数据
     */
    private void getData(String equipmentId) {
        showDialog("加载中.....");

        mColumnarViewModel.getStaticticalAnalAnalyData(equipmentId).observe(this, new Observer<BaseResponse<StaticticalAnalAnalyEntity>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<StaticticalAnalAnalyEntity> staticticalAnalAnalyEntityBaseResponse) {

                if (staticticalAnalAnalyEntityBaseResponse.getRows() != null) {
                    Key = new ArrayList<>();
                    Map<String, List<StaticticalAnalAnalyEntity>> map = new HashMap<>();
                    for (StaticticalAnalAnalyEntity bean : staticticalAnalAnalyEntityBaseResponse.getRows()) {
                        if (map.containsKey(bean.getGradeName())) {//map中存在此id，将数据存放当前key的map中
                            map.get(bean.getGradeName()).add(bean);
                        } else {//map中不存在，新建key，用来存放数据

                            Key.add(bean.getGradeName());
                            List<StaticticalAnalAnalyEntity> tmpList = new ArrayList<>();
                            tmpList.add(bean);
                            map.put(bean.getGradeName(), tmpList);
                        }
                    }
                    String json = GsonUtil.getGson().toJson(map);
                    aCache.put("mapData", json);
                    mColumnarViewModel.key.setValue(Key);

                        mColumnarViewModel.nomalKey.setValue(Key.get(0));


                   dismissDialog();
                }else{
                   dismissDialog();
                }
            }
        });
    }
    private void initView() {
        sharedUtils = new SharedUtils("TpmSetting");
        LineListCode = sharedUtils.getStringValue("LineListCode");
        tpmStationCode = sharedUtils.getStringValue("tpmStationCode");
        tpmStationName = sharedUtils.getStringValue("tpmStationName");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        flPicCondtionTpm = (FrameLayout) findViewById(R.id.fl_pic_condtion_tpms);
        leftMenu = (RecyclerView) findViewById(R.id.tpm_left_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        TextView textView = (TextView) findViewById(R.id.tpm_statical_close);
        recyclerView = (RecyclerView) findViewById(R.id.tpm_left_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvType = (TextView) findViewById(R.id.tv_tpm_statistics_type);
        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                CommonSetSelectPop commonSetSelectPop = new CommonSetSelectPop(StatisticalAnalysisActivity.this, null, "产线");
                commonSetSelectPop.setMidH(true);
                commonSetSelectPop.isDoall(true);
                commonSetSelectPop.getSet().put("ApiCode", "GetLineList");
                commonSetSelectPop.setSelect(new SetSelect() {
                    @Override
                    public void select(String id, String code, String name) {

                        getStation(code);
                    }
                });
                commonSetSelectPop.showSheet();

            }

        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }

    private void getStation(String code) {
        CommonSetSelectPop commonSetSelectPop1 = new CommonSetSelectPop(StatisticalAnalysisActivity.this, null, "工位");
        commonSetSelectPop1.setMidH(true);
        commonSetSelectPop1.isDoall(false);
        commonSetSelectPop1.getSet().put("ApiCode", "GetLineStationList");
        commonSetSelectPop1.getSet().put("LineCode", code);
        commonSetSelectPop1.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                tpmStationCode=code;
                mColumnarViewModel.getEquipmentBaseData(tpmStationCode);
            }
        });
        commonSetSelectPop1.showSheet();
    }


    private void initToobar() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toolbar.setTitle("");
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void initData() {
        mColumnarViewModel.getEquipmentBaseData(tpmStationCode);
        mColumnarViewModel.BaseDateEntity.observe(StatisticalAnalysisActivity.this, new Observer<BaseResponse<EquipmentBaseDateEntity>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<EquipmentBaseDateEntity> equipmentBaseDateEntityBaseResponse) {
                if (equipmentBaseDateEntityBaseResponse.getRows() != null) {
                    String equipmentId = equipmentBaseDateEntityBaseResponse.getRows().get(0).getEquipmentId();
                    tvType.setText(equipmentBaseDateEntityBaseResponse.getRows().get(0).getEquipmentName());
                    getData(equipmentId);
                }
            }
        });


        //设置左边布局数据

        mColumnarViewModel.getAllKey().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (strings != null) {
                    if (adapt == null) {
                        adapt = new TpmTableAdapt(R.layout.item_recy_tpm_left, strings);
                        recyclerView.setAdapter(adapt);
                    }
                    adapt.setNewData(strings);

                    adapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            mColumnarViewModel.nomalKey.setValue(strings.get(position));
                            drawerLayout.closeDrawers();
                        }
                    });


                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return false;
//        if (super.onOptionsItemSelected(item)) return true;
//        else return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

             super.onBackPressed();
        }
    }


}
