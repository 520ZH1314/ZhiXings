package com.zhixing.tpmlib.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.ColumnarBean;
import com.zhixing.tpmlib.viewModel.ColumnarViewModel;

import java.util.ArrayList;
import java.util.List;


public class StatisticalAnalysisActivity extends BaseTpmActivity {


    private Toolbar toolbar;

    private FrameLayout flPicCondtionTpm;

    private RecyclerView leftMenu;

    private DrawerLayout drawerLayout;
    private ColumnarViewModel mColumnarViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_statistical_analysis;

    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void newIniLayout() {

        initView();

        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();

        //添加首页
        ft.add(R.id.fl_pic_condtion_tpms, new TpmColumnarFragment()).commit();
        mColumnarViewModel = ViewModelProviders.of(this).get(ColumnarViewModel.class);

        initToobar();
        //        初始化数据
        initData();
    }

    private void initView() {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        flPicCondtionTpm = (FrameLayout) findViewById(R.id.fl_pic_condtion_tpms);
        leftMenu=(RecyclerView) findViewById(R.id.left_menu);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initToobar() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toolbar.setTitle("");
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void initData() {

          List<ColumnarBean> data=new ArrayList<>();
          data.add(new ColumnarBean("准时完成数","2018-10","4","2","50","月"));
          data.add(new ColumnarBean("准时完成数","2018-11","10","4","40","月"));
          data.add(new ColumnarBean("准时完成数","2018-12","11","22","50","月"));
          mColumnarViewModel.ColumnarValue.setValue(data);

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
        return  false;
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

            // super.onBackPressed();
        }
    }

}
