package com.shuben.zhixing.www.fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.volley.VolleyError;
import com.base.zhixing.www.common.Common;
import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.google.gson.JsonObject;
import com.leo618.zip.IZipCallback;
import com.leo618.zip.ZipManager;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.sdk.chat.ChatSdk;
import com.base.zhixing.www.BaseFragment;
import com.shuben.zhixing.module.mess.MessActivity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.activity.LoginActivity;
import com.shuben.zhixing.www.activity.NewMissionActivity;
import com.shuben.zhixing.www.activity.NewNotificationActivity;
import com.shuben.zhixing.www.activity.SettingComActivity;
import com.shuben.zhixing.www.activity.SquareActivity;
import com.shuben.zhixing.www.adapter.ViewPagerAdapter;
import com.shuben.zhixing.module.andon.AndonActivity;
import com.shuben.zhixing.module.jsf.JavaScriptAndon;
import com.shuben.zhixing.module.center_room.CenterRoomActivity;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.www.common.ARouterContants;
import com.shuben.zhixing.www.common.T;
import com.shuben.zhixing.www.data.UserData;
import com.shuben.zhixing.www.fragment.adapter.FoucsAdapter;
import com.shuben.zhixing.www.inspection.InspectionActivity;
import com.base.zhixing.www.inter.ScreenSelect;
import com.base.zhixing.www.inter.Tips;
import com.base.zhixing.www.inter.VolleyResult;
import com.shuben.zhixing.www.inter.EnterI;
import com.shuben.zhixing.www.patrol.PatrolActivity;
import com.shuben.zhixing.www.reminder.ReminderActivity;
import com.shuben.zhixing.www.reminder.Reminder_CaiGouActivity;
import com.shuben.zhixing.www.util.IPQCLTBAlertDialog;
import com.shuben.zhixing.www.util.SelectPicPopupWindow;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.CommonTips;
import com.base.zhixing.www.widget.InGridView;
import com.shuben.zhixing.www.util.UpdateManager;
import com.zhixing.tpmlib.activity.TpmActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Administrator on 2017/8/21.智行力首页
 */
public class Fragment01 extends BaseFragment implements View.OnClickListener{
    private View view_layout;
    private Context context;
    private ImageView tetle_back;
    private TextView tx_exit,fm_guangchang;
    private RelativeLayout   re_new_tongzhi,re_new_renwu,richeng;
    private TextView tv_dialog_i_know_but,tv_dialog_commit_but,tx_content_info;
    private IPQCLTBAlertDialog ltbAlertDialog;
    private SelectPicPopupWindow menuWindow;
    //轮播图
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.mipmap.two_banner01,
            R.mipmap.two_banner02,
            R.mipmap.two_banner03,
    };
    //存放图片的标题
    private String[]  titles = new String[]{
            "智慧制造，我们携手前行",
            "为有理想的企业持续提供动力",
            "工业生产，我们为您指明方向",
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
    private InGridView foucs;
    private  SharedUtils sharedUtils;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view_layout = inflater.inflate(R.layout.fragment01,container,false);
        context = getActivity();
        sharedUtils = new SharedUtils(T.SET_F);
        init();
        loadInfo();
        return view_layout;
    }
    private ArrayList< Map<String,Integer>> items = new ArrayList<Map<String, Integer>>();
    private void initItems(){
        addItem(R.mipmap.sy_cuidan,R.string.frg_i0);
        addItem(R.mipmap.sy_task,R.string.frg_i1);
        addItem(R.mipmap.sy_gxmeeting,R.string.frg_i2);
        addItem(R.mipmap.sy_bad,R.string.frg_i3);
        addItem(R.mipmap.sy_ecn,R.string.frg_i4);
        addItem(R.mipmap.rolling_mrb,R.string.frg_i5);
        addItem(R.mipmap.andon_ico,R.string.frg_i6);
        addItem(R.mipmap.sy_task,R.string.frg_i7);
        addItem(R.mipmap.center_room_tag,R.string.frg_i9);
        addItem(R.mipmap.sy_rpc,R.string.frg_i10);
        addItem(R.mipmap.sy_empl,R.string.frg_i11);
        addItem(R.mipmap.sy_more,R.string.frg_i8);
    }
    private void addItem(int im,int txt){
        Map<String,Integer> ms = new HashMap<>();
        ms.put("img",im);
        ms.put("txt",txt);
        items.add(ms);
    }
    private FoucsAdapter foucsAdapter;
    //id初始化
    private void init() {
        setStatus(-1);
        initItems();
       // load();
        checkUpdate();
        foucsAdapter = new FoucsAdapter(getActivity(),items);
        foucs = view_layout.findViewById(R.id.foucs);
        foucs.setAdapter(foucsAdapter);
        mViewPaper = (ViewPager) view_layout.findViewById(R.id.vp_main);
       /* tetle_back = (ImageView)view_layout.findViewById(R.id.tetle_back);
        tetle_back.setVisibility(View.GONE);*/

        tx_exit = (TextView) view_layout.findViewById(R.id.tx_exit);
        tx_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonTips tips = new CommonTips(getActivity(),getHandler());
                tips.init("取消","确定","是否退出账户");
                tips.setI(new Tips() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void sure() {
                        ChatSdk.close();
                        SharedPreferencesTool.getMStool(getActivity()).clear("UserId");
                        /**
                        * @author cloor
                        * @time   2018-12-21 11:30
                        * @describe  :  清除
                        */

                      sharedUtils.clear();
                      P.c("数据"+SharedPreferencesTool.getMStool(getActivity()).getIp());
                        Intent intent=new Intent();
                        intent.setClass(context, LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                tips.showSheet();
            }
        });



        fm_guangchang = (TextView)view_layout.findViewById(R.id.fm_guangchang);//应用广场



        re_new_tongzhi = (RelativeLayout)view_layout.findViewById(R.id.re_new_tongzhi);//新通知
        re_new_renwu = (RelativeLayout)view_layout.findViewById(R.id.re_new_renwu);//新任务
        richeng = (RelativeLayout)view_layout.findViewById(R.id.richeng);//新任务

        foucs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickItem(i);
            }
        });
        setOnclick();
        setviewpager();
    }
    private void downRar(String MOUDLE,String ur,String current,EnterI enterI){
        SharedUtils sharedUtils = new SharedUtils(MOUDLE);
        showDialog("正在更新安灯模块");
        P.c("正在更新安灯模块");
        //解析就统一解析到模块的module文件夹下面
        String path = Common.SD+MOUDLE+"/"+MOUDLE+ur.substring(ur.lastIndexOf("."));
        P.c(path+"地址");



        File f = new File(path);
        if(f.isFile()&&f.exists()){
            boolean flag = f.delete();
            P.c("删除情况"+flag);
        }
        FileDownloader.getImpl().create(ur).setPath(path).setListener(new FileDownloadLargeFileListener() {

            @Override
            protected void started(BaseDownloadTask task) {
                super.started(task);
                P.c("文件更新。。。。。。。。。。。。。。。。。");
            }

            @Override
            protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                P.c("下载"+soFarBytes);
            }

            @Override
            protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {

            }

            @Override
            protected void completed(BaseDownloadTask task) {
               P.c( task.getPath());
                String taskPath = task.getPath();


                try {
                    String dir = Common.SD+MOUDLE+"/MODULE/";
                    if(path.endsWith("zip")){
                        FileUtils.deleteDir(new File(dir));
                        ZipManager.unzip(task.getPath(), dir, new IZipCallback() {
                            @Override
                            public void onStart() {

                                P.c("删除资源文件");
                            }
                            @Override
                            public void onProgress(int percentDone) {

                            }

                            @Override
                            public void onFinish(boolean success) {
                                if(success){
                                    dismissDialog();
                                    sharedUtils.setStringValue("CurrentVersion",current);
                                    if(enterI!=null){
                                        enterI.enter(MOUDLE);
                                    }
                                }else {
                                    Toasty.INSTANCE.showToast(getActivity(),"更新失败");
                                }

                            }
                        });
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {

            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }
        }).start();
    }
    /**
    * @author cloor
    * @time   2019-1-11 10:20
    * @describe  : 下载模块
    */
    private void downModule(String MOUDLE,EnterI enterI){
        SharedUtils sharedUtils = new SharedUtils(MOUDLE);
        File andonDir = new File(Common.SD+MOUDLE);
        if(!andonDir.exists()) {
            andonDir.mkdirs();
        }
        Map<String,String> params = new HashMap<>();
        params.put("AppCode","EPS");
        params.put("ApiCode","GetAppCodeVersion");
        params.put("AppVerCode",MOUDLE);
        httpPostSONVolley(SharedPreferencesTool.getMStool(getActivity()).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    if(jsonObject.getBoolean("status")){
                        String old = sharedUtils.getStringValue("CurrentVersion");
                       JSONObject array = jsonObject.getJSONObject("rows");
//                       if(array.length()==0){
//                           Toasty.INSTANCE.showToast(getActivity(),"请联系管理员提交应用程序");
//                           return;
//                       }
//                       JSONObject object = array.getJSONObject(0);
                      String current =  array.getString("CurrentVersion");
                      File file = new File(Common.SD+MOUDLE+"/MODULE/index.html");
                      if(!old.equals(current)||!file.exists()){
                         CommonTips commonTips = new CommonTips(getActivity(),null);
                         commonTips.init("暂时不用","现在下载","检测到新版本"+current);
                         commonTips.setI(new Tips() {
                             @Override
                             public void cancel() {

                             }

                             @Override
                             public void sure() {
                                 try {
                                     downRar(MOUDLE,SharedPreferencesTool.getMStool(getActivity()).getIp()+array.getString("AndroidURL"),current,enterI);
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         });
                         commonTips.showSheet();
                      }else{
                          //相同的，可以进入
                          //Toasty.INSTANCE.showToast(getActivity(),"相同");

                          if(enterI!=null){
                              enterI.enter(MOUDLE);
                          }
                      }

                    }else{
                        Toasty.INSTANCE.showToast(getActivity(),"服务未部署");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        });
    }
    private void enterMes(String MOUDLE){
        Intent intent2=new Intent(getActivity(),MessActivity.class);
        intent2.putExtra("file",Common.SD+MOUDLE+"/MODULE/index.html");
        startActivity(intent2);
    }

    private void enterAndon(String MOUDLE){
        Intent intent2=new Intent();
        String user= SharedPreferencesTool.getMStool(getActivity()).getPhone();
        String psw= SharedPreferencesTool.getMStool(getActivity()).getPassword();
        intent2.putExtra("file",Common.SD+MOUDLE+"/MODULE/index.html");
//        intent2.putExtra("url","http://192.168.0.109:8081");
//        P.c("地址"+intent2.getStringExtra("url"));
        String p0 =  SharedPreferencesTool.getMStool(getActivity()).getUserId();
        String p1 =  SharedPreferencesTool.getMStool(getActivity()).getUserCode();
        String p2 = "";
        String p3 = SharedPreferencesTool.getMStool(getActivity()).getTenantId();

        String p4 =sharedUtils.getStringValue("factory_id");
        String p7 = sharedUtils.getStringValue("workshop_id");
        String p5 = sharedUtils.getStringValue("line_id");
        String p6 = sharedUtils.getStringValue("station_id");

        if(sharedUtils.getStringValue("station_id").length()==0){
            JavaScriptAndon andon =  new JavaScriptAndon(getActivity(),null,null);
            andon.setSaveInfo(true);
            andon.setScreenListen(new ScreenSelect() {
                @Override
                public void select(String id0[], String id1[], String id2[], String id3[]) {
                    String js = "javascript:loginInfoMsg("+params(p0)+","+params(p1)+","+params(p2)+","+params(p3)+","+params(id0[0])+","+params(id1[0])+","+params(id2[0])+","+params(id3[0])+","+params("-1")+")";
                    intent2.putExtra("init_value",js);
                    intent2.setClass(getActivity(), AndonActivity.class);
                    startActivity(intent2);
                }
            });
            andon.selectScreen(4,1);
            return;
        }
        //安灯
        String js = "javascript:loginInfoMsg("+params(p0)+","+params(p1)+","+params(p2)+","+params(p3)+","+params(p4)+","+params(p7)+","+params(p5)+","+params(p6)+")";
        P.c(js);
        intent2.putExtra("init_value",js);
        intent2.setClass(getActivity(), AndonActivity.class);
        startActivity(intent2);

    }

    private void clickItem(int p){
        switch (p){
            case 0://紧急催单

                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(getActivity(), itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置

                break;
            case 6:
                downModule(T.ANDON, new EnterI() {
                    @Override
                    public void enter(String moudle) {
                        enterAndon(moudle);
                    }
                });


                break;
            case 1://任务交办
                //showRefundDialogs("当前用户未开启任务交办功能");
             //   TpmSetting.getInstance(getActivity()).isSetting();

//
//                    Intent intent =new Intent(getActivity(),WorkMainActivity.class);
//                    startActivity(intent);

                break;

            case 2://高效会议
                showRefundDialogs("当前用户未开启高效会议功能");
                break;

            case 3://80管理
                //showRefundDialogs("暂无开放");
               /* Intent intent1=new Intent();
                intent1.setClass(getActivity(), MessActivity.class);
                intent1.putExtra("url",SharedPreferencesTool.getMStool(getActivity()).getString("MiniMesApp_config"));
                startActivity(intent1);*/
                downModule(T.SMESS, new EnterI() {
                    @Override
                    public void enter(String moudle) {
                        enterMes(moudle);
                    }
                });
                break;

            case 4://巡检
                //showRefundDialogs("当前用户未开启巡检功能");
                Intent mini=new Intent();
                mini.setClass(getActivity(), InspectionActivity.class);
                startActivity(mini);
                break;

            case 5://巡线管理
              //  showRefundDialogs("暂无开放");
                Intent po=new Intent();
                po.setClass(getActivity(), TpmActivity.class);
                startActivity(po);

                break;
            case 7:
               /* Intent personIntent = new Intent();
                personIntent.setClass(getActivity(),MassMainActivity.class);
                startActivity(personIntent);*/
               /**
               * @author cloor
               * @time   2018-12-24 16:18
               * @describe  :
               */
                ARouter.getInstance().build(ARouterContants.MassMainActivity).navigation();
                //通讯
                break;
            case 8:
                Intent centerIntent = new Intent();
                centerIntent.setClass(getActivity(),CenterRoomActivity.class);
                startActivity(centerIntent);

                break;
            case 9://更多
                if(sharedUtils.getStringValue("factory_id").length()==0){
                    CommonSetSelectPop setSelectPop = new CommonSetSelectPop(getActivity(),getHandler(),"工厂");
                    setSelectPop.getSet().put("ApiCode", "GetFactoryList");
                    setSelectPop.setMidH(true);
                    setSelectPop.setSelect(new SetSelect() {
                        @Override
                        public void select(String id, String code, String name) {
                            sharedUtils.setStringValue("factory_id",id);
                            sharedUtils.setStringValue("factory_name",name);
                            ARouter.getInstance().build(ARouterContants.RpcMainActivity).navigation();
                        }
                    });
                    setSelectPop.showSheet();
                    return;
                }
                ARouter.getInstance().build(ARouterContants.RpcMainActivity).navigation();



                break;
            case 10:
                ARouter.getInstance().build(ARouterContants.PerformanceActivity).navigation();
                break;
            case 11:
                Toasty.INSTANCE.showToast(getActivity(),"开发中");
                //ARouter.getInstance().build(ARouterContants.PerformanceActivity).navigation();
                break;
        }
    }

    private void setviewpager() {
        // TODO Auto-generated method stub

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(view_layout.findViewById(R.id.dot_0));
        dots.add(view_layout.findViewById(R.id.dot_1));
        dots.add(view_layout.findViewById(R.id.dot_2));

        title = (TextView) view_layout.findViewById(R.id.title);
        title.setText(titles[0]);

        adapter = new ViewPagerAdapter(getActivity(),images);
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                int POS = position%images.size();
                title.setText(titles[POS]);
                dots.get(POS).setBackgroundResource(R.drawable.dot_focused);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);

                oldPosition = POS;
                currentItem = POS;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        controlViewPagerSpeed(mViewPaper,1000);
        enqueueBannerLoopMessage();
    }
    // 反射设置ViewPager的轮播速度
    private void controlViewPagerSpeed(ViewPager viewPager, int speedTimeMillis) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getActivity(), new AccelerateDecelerateInterpolator());
            scroller.setDuration(speedTimeMillis);
            field.set(viewPager, scroller);
        } catch (Exception e) {
        }
    }
    private static final int BANNER_TYPE = 0;
    private static final int BANNER_TIME = 5000; // ms
    private Handler bannerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == BANNER_TYPE) {
                mViewPaper.setCurrentItem(mViewPaper.getCurrentItem() + 1);
                enqueueBannerLoopMessage();
            }
        }
    };

    // 添加Banner循环消息到队列
    public void enqueueBannerLoopMessage() {
        if (images == null || images.size() <= 1) return;
        bannerHandler.sendEmptyMessageDelayed(BANNER_TYPE, BANNER_TIME);
    }

    // 移除Banner循环的消息
    public void removeBannerLoopMessage() {
        if (bannerHandler.hasMessages(BANNER_TYPE)) {
            bannerHandler.removeMessages(BANNER_TYPE);
        }
    }


    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.scheduleWithFixedDelay(	new ViewPageTask(),
//                3,
//                4,
//                TimeUnit.SECONDS);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        P.c("界面显示情况"+hidden);

        if (hidden) {
            removeBannerLoopMessage();
        } else {

            enqueueBannerLoopMessage();
            loadInfo();
        }
    }
    /**
     * @author zjq
     * create at 2018/12/20 下午4:33 网络请求
     */
    private void loadInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetUserInfo");
        params.put("UserId", SharedPreferencesTool.getMStool(getActivity()).getUserId());

        httpPostVolley(SharedPreferencesTool.getMStool(getActivity()).getIp() + UrlUtil.GetUseInfo, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                String json = jsonObject.toString();
                //保存 json

                UserData bean = GsonUtil.getGson().fromJson(json, UserData.class);

                SharedPreferencesTool.getMStool(getActivity()).setString("head_ico",bean.getPhotoURL());
                SharedPreferencesTool.getMStool(getActivity()).setString("DeptName",bean.getDeptName());
            }

            @Override
            public void error(VolleyError error) {


            }
        }, false);


    }
    @Override
    public void onPause() {
        super.onPause();
       // removeBannerLoopMessage();
    }

    @Override
    public void process(Message msg) {

    }

    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }
    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    private void setOnclick() {




        re_new_tongzhi.setOnClickListener(this);
        re_new_renwu.setOnClickListener(this);
        richeng.setOnClickListener(this);
        fm_guangchang.setOnClickListener(this);

    }
    private void checkUpdate() {
        UpdateManager updateManager=new UpdateManager(getActivity());
//        String updateUrl="https://ilean.m3lean.com:2001/"+UrlUtil.UpdateUrl;
        updateManager.checkUpdateInfo(null);
    }
    private String params(String params) {
        return "\'"+params+"\'";
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){

            case R.id.fm_guangchang://更多
                intent = new Intent(getActivity(), SquareActivity.class);
                startActivity(intent);
                break;

            case R.id.re_new_tongzhi://新通知
                intent = new Intent(getActivity(), NewNotificationActivity.class);
                startActivity(intent);
                break;

            case R.id.re_new_renwu://新任务
                intent = new Intent(getActivity(), NewMissionActivity.class);
                startActivity(intent);
                break;

            case R.id.richeng://日程
//                intent = new Intent(getActivity(), ScheduleActivity.class);
//                startActivity(intent);
                ARouter.getInstance().build(ARouterContants.ScheduleActivity).navigation();



            case R.id.tv_dialog_i_know_but://日程
                if (ltbAlertDialog!=null){
                    ltbAlertDialog.dismiss();
                }
                break;
            case R.id.tv_dialog_commit_but://日程
                if (ltbAlertDialog!=null){
                    ltbAlertDialog.dismiss();
                }
                break;
        }
    }

    private void showRefundDialogs(String message) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_title_ipqc,null);
        tv_dialog_i_know_but= (TextView) layout.findViewById(R.id.tv_dialog_i_know_but);
        tv_dialog_commit_but= (TextView) layout.findViewById(R.id.tv_dialog_commit_but);
        tx_content_info= (TextView) layout.findViewById(R.id.tx_content_info);
        tx_content_info.setText(message);
        if(tv_dialog_i_know_but!=null){
            tv_dialog_i_know_but.setOnClickListener(this);
        }
        if(tv_dialog_commit_but!=null){
            tv_dialog_commit_but.setOnClickListener(this);
        }
//	        tx_content_info.setText(message);
        ltbAlertDialog = IPQCLTBAlertDialog.getLtbAlertDialog(getActivity(), false, false);
        ltbAlertDialog.setViewContainer(layout).show();
    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){
        Intent intent;
        Uri imageUri;
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_cancel:
                    menuWindow.dismiss();
                    break;

                case R.id.btn_gongshang_photo:
                    //内部催单
                    intent = new Intent(getActivity(),ReminderActivity.class);
                    intent.putExtra("tag",0);
                    startActivity(intent);

                    break;
                case R.id.btn_zhaoshang_photo:
                    //采购催单
                    intent = new Intent(getActivity(), Reminder_CaiGouActivity.class);
                    intent.putExtra("tag",0);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    private void load(){
        Map<String,String> params = new HashMap<>();
        params.put("AppCode","EPS");
        params.put("ApiCode","GetAppModuleList");
        httpPostVolley(SharedPreferencesTool.getMStool(getActivity()).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                        SharedPreferencesTool.getMStool(getActivity()).setString(o.getString("AppCode")+"_config",o.getString("AppUrl"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
            }
        },false);
    }

}
