package com.shuben.zhixing.www.inspection.fragment;

import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.common.T;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.inter.Tips;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.base.zhixing.www.widget.CommonTips;
import com.base.zhixing.www.widget.GridSpacingItemDecoration;
import com.example.stateviewlibrary.StateView;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.AddProblemActivity;
import com.shuben.zhixing.www.inspection.InspectionActivity;
import com.shuben.zhixing.www.inspection.InspectionItemActivity;
import com.shuben.zhixing.www.inspection.ScanInspectionActivity;
import com.shuben.zhixing.www.inspection.SwitchView;
import com.shuben.zhixing.www.inspection.bean.TypeInfo;
import com.shuben.zhixing.www.view.PopupAdapter;
import com.zhixing.rpclib.RpcMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InspectionMain extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_inspection_main;
    }

    /**
     * 接收handler消息处理方法
     *
     * @param msg
     */
    @Override
    public void process(Message msg) {
            switch (msg.what){
                case 1:
                    inspection_item.updata(data);
                    break;
                case 2:
                    layout2.setVisibility(View.GONE);
                    glview.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    layout2.setVisibility(View.VISIBLE);
                    glview.setVisibility(View.GONE);
                    break;
                case 4:
                    String tx = (String) msg.obj;
                    Intent intent = new Intent(InspectionMain.this, AddProblemActivity.class);
                    intent.putExtra("id",tx);
                    startActivity(intent);
                    break;
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode==1000){
            if(intent!=null&&intent.hasExtra("ret")){
                try {
                    JSONObject object = new JSONObject(intent.getStringExtra("ret"));
                    String line = object.getString("LineId");
                    String LineName = object.getString("LineName");
                    int pos = object.getInt("pos");
                    P.c(line+"=="+LineName+"==="+pos);
                    if(pos==-1){
                        Toasty.INSTANCE.showToast(InspectionMain.this,"数据异常");
                        return;
                    }
                    String clsCode = data.get(pos).getClassCode();
                    String clasId = data.get(pos).getClassId();
                    if(clsCode.equals("XLINE")){
                        getOrder(clasId,line,LineName);
                    }else{
                        Intent intent0 = new Intent(InspectionMain.this, InspectionItemActivity.class);
                        intent0.putExtra("ClassId",clasId);
                        intent0.putExtra("scopeID",line);
                        intent0.putExtra("scopeName",LineName);
                        startActivity(intent0);
                    }
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toasty.INSTANCE.showToast(InspectionMain.this,"识别错误");
                }


            }
        }
    }

    private SharedUtils parentShared;
    private void setStand(String clsId,int pos){
        if (parentShared.getStringValue("factory_id").length() == 0) {
            Toasty.INSTANCE.showToast(InspectionMain.this, "请先在基本设置中选择工厂");
            return;
        }
        //替换成二维码扫描
        Intent intent = new Intent(InspectionMain.this, ScanInspectionActivity.class);
        intent.putExtra("type",2);
        intent.putExtra("pos",pos);
        intent.putExtra("title",data.get(pos).getClassName()+"签到");
        startActivityForResult(intent,0);
       /* CommonSetSelectPop setSelectPop = new CommonSetSelectPop(this, getHandler(), "车间");
        setSelectPop.getSet().put("ApiCode", "GetWorkShopList");
        setSelectPop.getSet().put("FactoryId", parentShared.getStringValue("factory_id"));
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                selectLine(id,clsId,pos);
            }


        });
        setSelectPop.showSheet();*/
    }
    private void selectLine(String workshop_id,String clsId,int pos ){
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(this, getHandler(), "产线");
        setSelectPop.getSet().put("ApiCode", "GetLineList");
        setSelectPop.getSet().put("WorkShopId", workshop_id);
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                createOrder(clsId,id,code,name,pos);
            }
        });
        setSelectPop.showSheet();
    }

    private void createOrder(String clsId,String id, String code, String name,int pos){
        CommonTips commonTips = new CommonTips(this,getHandler());
        commonTips.init("取消","开始","是否开始"+data.get(pos).getClassName());
        commonTips.setI(new Tips() {
            @Override
            public void cancel() {

            }

            @Override
            public void sure() {


                if(data.get(pos).getClassCode().equals("XLINE")){
                    getOrder(clsId,id,name);
                }else{
                    Intent intent = new Intent(InspectionMain.this, InspectionItemActivity.class);
                    intent.putExtra("ClassId",clsId);
                    intent.putExtra("scopeID",id);
                    intent.putExtra("scopeName",name);
                    startActivity(intent);
                }
//                Intent intent = new Intent(InspectionMain.this, InspectionActivity.class);




            }
        });
        commonTips.showSheet();
    }

    private void getOrder(String clsId,String scopeID,String scopeName){
        /*
        {
"AppCode":"PatrolInspection",
"ApiCode":"EditHandleGenerateTask",
"TenantId":"00000000-0000-0000-0000-000000000001",
"ClassId":"76267119-51ff-4837-aa15-62360ed85734",
"scopeID":"731feceb-a52d-41b6-a776-76a0498ebd86",
"scopeName":"塑胶生产1号机",
"UserName":"超级管理员",
"UserId":"0a01a07a-b616-6b3b-2b44-328e6f38d465"
}

         */
        Map<String,String> params = new HashMap<>();
        params.put("AppCode","PatrolInspection");
        params.put("ApiCode","EditHandleGenerateTask");
        params.put("TenantId",SharedPreferencesTool.getMStool(this).getTenantId());
        params.put("ClassId",clsId);
        params.put("scopeID",scopeID);
        params.put("scopeName",scopeName);
        params.put("UserName",SharedPreferencesTool.getMStool(this).getUserName());
        params.put("UserId",SharedPreferencesTool.getMStool(this).getUserId());


        
        httpPostSONVolley(SharedPreferencesTool.getMStool(this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("rows");
                    int len  = jsonArray.length();
                    if(len!=0){
                        Message msg = new Message();
                        msg.what =  4;
                        msg.obj  = jsonArray.getJSONObject(0).getString("PatrolTaskId");
                        getHandler().sendMessage(msg);
                    }else{
                        Toasty.INSTANCE.showToast(InspectionMain.this,"服务端数据异常");
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


    private TextView title;
    private StateView mStateView;
    private LinearLayout root_content,layout2,layout1,my_p1,my_p0;
    private Inspection_item inspection_item;
    private SwitchView swich;
    /**
     * 空间数据初始化方法
     */
    @Override
    public void initLayout() {
        parentShared = new SharedUtils(T.SET_F);
        title = findViewById(R.id.tetle_text);
        title.setText("巡线巡检管理");
        my_p1 = findViewById(R.id.my_p1);
        my_p0 = findViewById(R.id.my_p0);
        swich = findViewById(R.id.swich);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        root_content = findViewById(R.id.root_content);
        mStateView = StateView.inject(layout1);
        mStateView.setShowText("iLean");
        data=new ArrayList<TypeInfo>();
        inspection_item = new Inspection_item(this,data,getHandler());
        glview = findViewById(R.id.left_list);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        glview.setLayoutManager(layoutManager);
        int spanCount = 3; // 3 columns
        int spacing = 30; // 50px
        boolean includeEdge = false;
        glview.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        glview.setAdapter(inspection_item);
        initData();
        inspection_item.setOnItemClick(new Inspection_item.onItemClick() {
            @Override
            public void clickItem(int pos) {


                setStand(data.get(pos).getClassId(),pos);
               /* Intent intent = new Intent(InspectionMain.this, InspectionActivity.class);
                intent.putExtra("index",0);
                intent.putExtra("obj",data.get(pos));
                startActivity(intent);*/



            }
        });
        swich.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                if(isChecked){
                   getHandler().sendEmptyMessage(2);
                }else{
                   getHandler().sendEmptyMessage(3);
                }
               // P.c(Thread.currentThread().getName()+"滑块状态"+isChecked);
            }
        });

        my_p0.setOnClickListener((v)->{
            Intent intent = new Intent(InspectionMain.this, InspectionActivity.class);
            intent.putExtra("index",1);
            intent.putExtra("des",0);
            startActivity(intent);
        });
        my_p1.setOnClickListener((v)->{
            Intent intent = new Intent(InspectionMain.this, InspectionActivity.class);
            intent.putExtra("index",1);
            intent.putExtra("des",1);
            startActivity(intent);
        });
//        swich.setChecked(true);
    }

    private RecyclerView glview;
    private List<TypeInfo> data=null;
    private void initData(){
        if(mStateView!=null){
            mStateView.showLoading();
        }
        //获取分类数据
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "GetPatrolInspectionClass");
        params.put("TenantId", SharedPreferencesTool.getMStool(this).getTenantId());
        httpPostSONVolley(SharedPreferencesTool.getMStool(this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                mStateView.showContent();

                try {
                    int total=jsonObject.getInt("total");
                    JSONArray jArray=jsonObject.getJSONArray("rows");
                    JSONObject jData;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String ClassId=jData.getString("ClassId");//问题编号
                        String ClassName=jData.getString("ClassName");//车间
                        String Period=jData.getString("Period");//
                        String Frequency=jData.getString("Frequency");//
                        String IsRelationWorksheet=jData.getString("IsRelationWorksheet");//
                        String IsCreateTaskByStart=jData.getString("IsCreateTaskByStart");//
                        String FilePath = jData.getString("FilePath");
                        String ClassCode = jData.getString("ClassCode");
                        data.add(new TypeInfo(ClassId,ClassName,Period,Frequency,IsRelationWorksheet,IsCreateTaskByStart,FilePath,ClassCode));

                    }
                    getHandler().sendEmptyMessage(1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void error(VolleyError error) {

            }
        });
    }

}
