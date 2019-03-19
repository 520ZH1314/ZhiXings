package com.shuben.contact.lib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.shuben.contact.lib.common.ConstantS;
import com.shuben.contact.lib.event.ConstantDataEvent;
import com.shuben.contact.lib.event.ConstantEvent;
import com.shuben.contact.lib.event.ConstantIsCheck;
import com.shuben.contact.lib.event.ConstantOneEvent;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.BannerPager;
import com.base.zhixing.www.widget.PagerSlidingTabStrip;
import com.base.zhixing.www.widget.scrollablelayoutlib.MyFragmentPagerAdapter;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollAbleFragment;
import com.base.zhixing.www.widget.scrollablelayoutlib.ScrollableLayout;
import com.google.gson.Gson;
import com.shuben.contact.lib.bean.Bean;
import com.shuben.contact.lib.bean.Type;
import com.shuben.contact.lib.dataBase.ConstantDB;
import com.shuben.contact.lib.frame.ConstantFrame0;
import com.shuben.contact.lib.frame.ConstantFrame1;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantActivity extends BaseActvity implements View.OnClickListener {
    ArrayList<Type> types0 = new ArrayList<>();
    ArrayList<Type> types1 = new ArrayList<>();
    private boolean isEdit = false;
    private RelativeLayout rlConstantSend;
    private TextView mTvIsTrue;
    private TextView mBtnConstantSend;
    private Boolean isSingle;
    private String type;



    @Override
    public void process(Message msg) {
        switch (msg.what) {
            case 3:
                if (data_pager.getCurrentItem() == 0) {
                    //所有人
                    ConstantDB.getInstance().getPersons(types0, ConstantActivity.this);

                    getHandler().sendEmptyMessage(0);
                } else {
                    //部分列表
                    ConstantDB.getInstance().getBersons(types1, ConstantActivity.this);
                    getHandler().sendEmptyMessage(1);
                }
                break;
            case 0:

                fragment0.updata(types0);
                break;
            case 1:
                fragment1.updata(types1, 1);
                break;
        }
    }

    private PagerSlidingTabStrip strip;
    private BannerPager data_pager;
    private ScrollableLayout scroollable;
    private TextView title, edit;

    @Override
    public void initLayout() {


        if (getIntent().hasExtra(ConstantS.ISEDIT)) {
            isEdit = getIntent().getBooleanExtra(ConstantS.ISEDIT, false);
        }
        if (getIntent().hasExtra(ConstantS.ISSINGLE)) {
            isSingle = getIntent().getBooleanExtra(ConstantS.ISSINGLE, false);
        }
        if (getIntent().hasExtra(ConstantS.TYPE)) {
            type = getIntent().getStringExtra(ConstantS.TYPE);
        }
        setStatus(-1);
        edit = findViewById(R.id.edit);
        title = findViewById(R.id.title);
        strip = findViewById(R.id.strip);
        rlConstantSend = (RelativeLayout) findViewById(R.id.rl_constant_send);//编辑显示发送布局
        mTvIsTrue = (TextView) findViewById(R.id.tv_constant_istrue);//已选中
        mBtnConstantSend =  findViewById(R.id.btn_constant_send);
        mBtnConstantSend.setOnClickListener(this);
        scroollable = findViewById(R.id.scroollable);
        data_pager = findViewById(R.id.data_pager);
        EventBus.getDefault().register(this);
        initFragmentPager(data_pager, scroollable, strip);



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit.getText().toString().equals("编辑")) {
                    edit.setText("退出编辑");
                    rlConstantSend.setVisibility(View.VISIBLE);
                    fragment0.edit(true);
                    fragment0.isSingle(isSingle);
                    fragment0.getType(type);
                } else if (edit.getText().toString().equals("退出编辑")) {
                    edit.setText("编辑");
                    rlConstantSend.setVisibility(View.GONE);
                    fragment0.edit(false);
                }
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_constant;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    final ArrayList<ScrollAbleFragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    private ConstantFrame0 fragment0;
    private ConstantFrame1 fragment1;

    private void initFragmentPager(final ViewPager viewPager, final ScrollableLayout mScrollLayout, PagerSlidingTabStrip strip) {
        fragmentList.clear();
        fragment0 = ConstantFrame0.newInstance();
        fragment0.setHandler(getHandler());
        fragment0.init(types0);
        fragmentList.add(fragment0);
        //  fragmentList.add(ScrollViewFragment.newInstance());
        fragment1 = ConstantFrame1.newInstance();
        fragment1.setHandler(getHandler());
        fragment1.init(types1);
        fragmentList.add(fragment1);
        // fragmentList.add(WebViewFragment.newInstance());
        titleList.clear();
        titleList.add("同事");
        //  titleList.add("ScrollView");
        titleList.add("部门");
        // titleList.add("WebView");
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
        strip.setViewPager(viewPager);
        strip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                if (position == 1) {
                    edit.setVisibility(View.GONE);
                } else {
                    if (isEdit) {
                        edit.setVisibility(View.VISIBLE);
                        edit.setText("退出编辑");
                        rlConstantSend.setVisibility(View.VISIBLE);
                        fragment0.edit(true);
                        fragment0.isSingle(isSingle);
                        fragment0.getType(type);
                    }
                }

                title.setText(titleList.get(position));
                scroollable.getHelper().setCurrentScrollableContainer(fragmentList.get(position));
                getHandler().sendEmptyMessage(3);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (isEdit) {
            edit.setVisibility(View.VISIBLE);
            //mod by zc

                //如果是编辑模式就默认打开
               edit.post(new Runnable() {
                   @Override
                   public void run() {
                       edit.performClick();
                   }
               });

        } else {
            edit.setVisibility(View.GONE);
        }

        mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
        viewPager.setCurrentItem(0);
        title.setText(titleList.get(0));
        getHandler().sendEmptyMessage(3);
        getData();
    }

    ArrayList<Bean> beans = new ArrayList<>();

    private void getData() {
        showDialog("同步数据中");
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetOrganizeUserForApp");
        params.put("TenantId", SharedPreferencesTool.getMStool(ConstantActivity.this).getTenantId());
        httpPostVolley(SharedPreferencesTool.getMStool(ConstantActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(final JSONObject jsonObject) {
                dismissDialog();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            JSONArray array = jsonObject.getJSONArray("rows");
                            for (int i = 0; i < array.length(); i++) {
                                JSONArray ja = array.getJSONArray(i);
                                for (int j = 0; j < ja.length(); j++) {
                                    JSONObject o = ja.getJSONObject(j);
                                    Gson gson = new Gson();
                                    Bean b = gson.fromJson(o.toString(), Bean.class);
                                    beans.add(b);
                                }
                            }

                            ConstantDB.getInstance().clear();
                            ConstantDB.getInstance().addPerson(beans);
                            getHandler().sendEmptyMessage(3);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


            }

            @Override
            public void error(VolleyError error) {

            }
        }, false);
    }


//    //EventBus接受事件
//    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
//    public void Event(ConstantEvent event){
//        ConstantEvent stickyEvent = EventBus.getDefault().getStickyEvent(ConstantEvent.class);
//        if (stickyEvent!=null){
//            EventBus.getDefault().removeStickyEvent(stickyEvent);
//
//          }
//        isEdit=event.getEdit();
//        isSingle = event.getSingle();
//        type = event.getType();
//
//
//    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_constant_send) {

            //发送一条确定的消息通知ConstantAdapte 保存数据
            EventBus.getDefault().post(new ConstantOneEvent(true));
            EventBus.getDefault().post(new ConstantDataEvent(true));
            AppManager.getAppManager().finishActivity();


        }


    }


    //EventBus接受事件 已选
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ConstantIsCheck event) {

        mTvIsTrue.setText("已选" + event.getNum() + "个");


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

}
