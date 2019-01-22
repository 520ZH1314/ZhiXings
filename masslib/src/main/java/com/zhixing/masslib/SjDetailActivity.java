package com.zhixing.masslib;

import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.Tips;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CommonTips;
import com.zhixing.masslib.adapter.ExpandableRecycleAdapter;
import com.zhixing.masslib.bean.BaseExpandableBean;
import com.zhixing.masslib.bean.GroupBean;
import com.zhixing.masslib.bean.MassItemBean;
import com.zhixing.masslib.bean.sJitemBean;
import com.zhixing.masslib.inter.OnExpandableClickListener;
import com.zhixing.masslib.util.Common;
import com.zhixing.masslib.util.RecycleDividerItemLinear;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SjDetailActivity extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.sj_detail_layout;
    }

    @Override
    public void process(Message msg) {
        switch (msg.what){
            case 1:
                adapter.updataB(groupBeans);
                for (int i = 0; i < groupBeans.size(); i++) {
                    if(groupBeans.get(i).getItemBeanList()==null){
                        adapter.addGroupAndItem(0);
                    }else{
                        adapter.addGroupAndItem(groupBeans.get(i).getItemBeanList().size());
                    }

                };

                break;
        }
    }

    private void loadData(String LineID){
        Map<String,String> params = new HashMap<>();
        params.put("AppCode",Common.APPCODE);
        params.put("ApiCode","GetFirstCheckItem");
        params.put("LineID",LineID);
        params.put("TenantId",SharedPreferencesTool.getMStool(SjDetailActivity.this).getTenantId());
        httpPostVolley(SharedPreferencesTool.getMStool(SjDetailActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                FileUtils.write(com.base.zhixing.www.common.Common.SD+"log1.txt",false,jsonObject.toString());
                    parse(jsonObject);
            }

            @Override
            public void error(VolleyError error) {

            }
        },true);
    }
    private void sendCheck(String s){
        Map  params = new HashMap();
        params.put("Date",obj.getData());
        params.put("AppCode",Common.APPCODE);
        params.put("ApiCode","EditPostSave");
        params.put("LineId",obj.getLineId());
        params.put("LineName",obj.getLine());
        params.put("guifan","1");
        params.put("WorkNo",obj.getNo());
        try {
            params.put("dataDetail",send());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("Status",s);
        params.put("IsConfig",String.valueOf(true));
        params.put("Content","");
        params.put("ID",ItemListValueID==null?"":ItemListValueID);
        params.put("Creator",SharedPreferencesTool.getMStool(SjDetailActivity.this).getUserCode()+SharedPreferencesTool.getMStool(SjDetailActivity.this).getUserName());
        params.put("TenantId",SharedPreferencesTool.getMStool(SjDetailActivity.this).getTenantId());
        FileUtils.write(com.base.zhixing.www.common.Common.SD+"log2.txt",false,FileUtils.parmsRet(params));



        httpPostVolley(SharedPreferencesTool.getMStool(SjDetailActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    Toasty.INSTANCE.showToast(SjDetailActivity.this,jsonObject.getString("msg"));
                    AppManager.getAppManager().finishActivity();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        },true);
    }

    private TextView tetle_text,tetle_tv_ok;
    private RecyclerView rlv_all;
    private ExpandableRecycleAdapter adapter;
    private ArrayList<GroupBean> groupBeans;
    private MassItemBean obj;
    private Map<String,String> constant;
    @Override
    public void initLayout() {
        setStatus(-1);
        constant = new HashMap<>();
        if(getIntent().hasExtra("obj")){
            obj = (MassItemBean) getIntent().getSerializableExtra("obj");
            constant.put("客户",obj.getCustomer());
            constant.put("产品名称",obj.getProduct());
            constant.put("料号",obj.getProductCode());
            constant.put("生产单号",obj.getNo());
            constant.put("订单量",obj.getSingleQuantity());
            constant.put("送检时间",obj.getCreateDate());
            constant.put("完成时间",obj.getAll_t());
        }
        tetle_text = findViewById(R.id.tetle_text);
        tetle_text.setText("首件检验");
        tetle_tv_ok = findViewById(R.id.tetle_tv_ok);
        tetle_tv_ok.setVisibility(View.VISIBLE);
        tetle_tv_ok.setText("提交");
        tetle_tv_ok.setOnClickListener(view -> {
            CommonTips commonTips = new CommonTips(SjDetailActivity.this,null);
            commonTips.init("OK","NG","您确定要提交数据吗?");
            commonTips.setI(new Tips() {
                @Override
                public void cancel() {
                    sendCheck("1");
                }

                @Override
                public void sure() {
                    sendCheck("0");
                }
            });
            commonTips.showSheet();
        });
        rlv_all = findViewById(R.id.rlv_all);
        groupBeans = new ArrayList<>();


      /*  for (int size = 0; size < 3; size++) {
            GroupBean groupBean = new GroupBean();
            List<sJitemBean> itemBeanList = new ArrayList<>();
            groupBean.setGroup(size);

            for (int j = 0; j < 10; j++) {
                sJitemBean itemBean = new sJitemBean();
                itemBean.setItem(j);
                itemBeanList.add(itemBean);
            }
            groupBean.setItemBeanList(itemBeanList);
            groupBeans.add(groupBean);
        }*/

        adapter = new ExpandableRecycleAdapter(SjDetailActivity.this,groupBeans,constant);
        rlv_all.setAdapter(adapter);

        setRecycle(adapter, null, new RecycleDividerItemLinear(SjDetailActivity.this,
                LinearLayoutManager.VERTICAL,
                getResources().getColor(R.color.gray03))
        );
        adapter.setOnExpandableClickListener(new OnExpandableClickListener() {
            @Override
            public void onExpandableClick(View clickView, BaseExpandableBean selecBean) {
                int index = selecBean.getGroupPosition();
                Toasty.INSTANCE.showToast(SjDetailActivity.this,groupBeans.get(index).isDynamicRow()+String.valueOf(groupBeans.get(index).getGroupName()));
            }
        });
        loadData(obj.getLineId());

    }
/**
* @author cloor
* @time   2019-1-22 16:44
* @describe  : 追加删除的数据
*/
    private void putDel(GroupBean groupBean,JSONArray array, List<sJitemBean> list){
        LinkedHashMap<Integer,Integer> map = groupBean.getItemV();
        Set s = map.keySet();
        Iterator i = s.iterator();

        while(i.hasNext()){
            int key = Integer.parseInt(i.next().toString());
            if(groupBean.getItemV().get(key)==-1){

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("ItemId",list.get(0).getId());
                    jsonObject.put("itemIndex",String.valueOf(key));//默认从1开始
                    jsonObject.put("LineId",obj.getLineId());
                    jsonObject.put("rowStatus","del");
                    array.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }

    }


    /**
    * @author cloor
    * @time   2019-1-8 18:29
    * @describe  : 封装数据
    */
    private JSONArray send() throws JSONException {

       JSONArray array = new JSONArray();
        //int m  = 0;
       for(int i=0;i<groupBeans.size();i++){
           //只处理子项
           GroupBean gb = groupBeans.get(i);
           boolean isDynamicRow =gb.isDynamicRow();
           List<sJitemBean> list =  gb.getItemBeanList();

           if(isDynamicRow){

               int CON = gb.getRowLi();
               int count =  list.size()/CON;//长度

               for(int m=0;m<list.size();m++){
                   P.c(m+"    %%%%%%%%%%%     " +list.get(m).getItemIndex());
               }


               //整个长度都是处理这个子项的
               P.c(CON+"长度"+list.size());
               for(int k=1;k<count+1;k++){
                   JSONObject jsonObject = new JSONObject();
                   jsonObject.put("ItemId",list.get(0).getId());
//                   jsonObject.put("itemIndex",String.valueOf(k));//默认从1开始
                   int index = list.get(k*CON-1).getItemIndex();
                   jsonObject.put("itemIndex",String.valueOf(index));//默认从1开始
//                     gb.isDetermine()
                   jsonObject.put("WorkNo",obj.getNo());
                   jsonObject.put("WorkDate",obj.getData());
                   jsonObject.put("LineId",obj.getLineId());
                    if(gb.getItemV().size()!=0 ){
                        if(!gb.getItemV().containsKey(index)){
                            jsonObject.put("rowStatus","add");
                        }

                    }else{
                        jsonObject.put("rowStatus","add");
                    }

                   int cot = gb.getItemBeanList().size();
                   if(gb.isDetermine()){
                       //存在

                       if(list.get(k*CON-1).getOk_ng()!=-1){
                           jsonObject.put("ItemCheckValue",list.get(k*CON-1).getOk_ng()==1?"OK":"NG");
                       }else{
                           jsonObject.put("ItemCheckValue","");
                       }
                   }
                   if(gb.isCheckRecord()){

                       jsonObject.put("ItemRecordValue", list.get(k*CON-1).getEditValue());
                   }
                   int start = (k-1)*CON;
                   int TG = 0;
                   for(int p=start;p<start+CON;p++){
                       TG++;
                       P.c("序号是"+(TG)+"==="+list.get(p).getValue());

                       jsonObject.put("ItemValue"+(TG),list.get(p).getValue());
                   }
                   array.put(jsonObject);

               }
               putDel(gb,array,list);

           }else{
               for(int j=0;j<list.size();j++){
                   sJitemBean sb = list.get(j);
                   JSONObject jsonObject = new JSONObject();
                   jsonObject.put("ItemId",sb.getId());


                       jsonObject.put("itemIndex","1");

                       switch (sb.getItemType()){
                           case 0:
                           case 1:
                               jsonObject.put("ItemValue1",sb.getValue());
                               break;
                           case 2:
                               //单选
                               String temp2[] = sb.getValue().split(",");
                               jsonObject.put("ItemValue1",sb.getRbc()!=-1?temp2[sb.getRbc()]:"");
                               break;
                           case 3:
                               //多选
                               String temp3[] = sb.getValue().split(",");
                               StringBuilder builder = new StringBuilder();
                               if(sb.getChe()!=null){
                                   for(int d=0;d<sb.getChe().length;d++){
                                       if( sb.getChe()[d]){

                                           builder.append(temp3[d]);
                                           if(d!=sb.getChe().length-1){
                                               builder.append(",");
                                           }
                                       }
                                   }
                               }
                               jsonObject.put("ItemValue1",builder.toString());
                               break;
                           case 4:
                               //系统
                               jsonObject.put("ItemValue1",constant.get(sb.getName()));
                               break;
                           default:break;
                       }


                       jsonObject.put("ItemRecordValue",sb.getEditValue()==null?"":sb.getEditValue());
                       //保证是需要选择，并且无知
                       if(sb.isNeedCheck()&&sb.getOk_ng()!=-1){
                           jsonObject.put("ItemCheckValue",sb.getOk_ng()==1?"OK":"NG");
                       }else{
                           jsonObject.put("ItemCheckValue","");
                       }

                   jsonObject.put("WorkNo",obj.getNo());
                   jsonObject.put("WorkDate",obj.getData());
                   jsonObject.put("LineId",obj.getLineId());

                   array.put(jsonObject);
               }
           }


       }



        return  array;
    }


    private void initData(String LineID,String WorkNO,String PlanDate){
        Map<String,String> params = new HashMap<>();
        params.put("AppCode",Common.APPCODE);
        params.put("ApiCode","GetFirstCheckPageInfo");
        params.put("LineID",LineID);
        params.put("WorkNO",WorkNO);
        params.put("PlanDate",PlanDate);
        params.put("TenantId",SharedPreferencesTool.getMStool(SjDetailActivity.this).getTenantId());
        httpPostSONVolley(SharedPreferencesTool.getMStool(SjDetailActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                FileUtils.write(com.base.zhixing.www.common.Common.SD+"log.txt",false,jsonObject.toString());
                parseInit(jsonObject);

            }

            @Override
            public void error(VolleyError error) {
                getHandler().sendEmptyMessage(1);
            }
        });
    }
    String ItemListValueID = null;
    //解析初始化数据
    private void parseInit(JSONObject jsonObject){
        //
        try {
            if(jsonObject.isNull("ItemListValue")){
                getHandler().sendEmptyMessage(1);
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("ItemListValue");
            int len = jsonArray.length();
            P.c("长度"+ adapter.getChangeListBean().size()+"==="+len);
            for(int i=0;i<len;i++){
                //测试
                JSONObject object = jsonArray.getJSONObject(i);
                if(ItemListValueID==null){
                    ItemListValueID = object.getString("ID");
                }
                    is(object);

//                    adapter.notifyDataSetChanged();
               // adapter.updataB(groupBeans);



            }
            getHandler().sendEmptyMessage(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    /**
    * @author cloor
    * @time   2019-1-8 18:12
    * @describe  :  计算动态行的最后一组数据
    */
    private void end(int c,GroupBean groupBean,  List<sJitemBean> list,JSONObject object) throws JSONException {
        P.c("执行了。。。dao"+object.getInt("itemIndex"));
        int index = object.getInt("itemIndex");
        list.get(0).setItemIndex(index);
        //在每个的最后一行增加其序号记录
        groupBean.getItemV().put(index,0);//默认是没有变化
            //最后一个检查是否存在
            if(groupBean.isDetermine()){
                String ItemCheckValue0 = object.getString("ItemCheckValue");
                if(ItemCheckValue0.equals("OK")){
                    list.get(c).setOk_ng(1);
                }else if(ItemCheckValue0.equals("NG")){
                    list.get(c).setOk_ng(0);
                }

            }
            if(groupBean.isCheckRecord()){
                String ItemRecordValue0 = object.getString("ItemRecordValue");
                list.get(c).setEditValue(ItemRecordValue0);
            }


    }
//    private String WORKNO,WORKDATE,LINEID;
        /**
        * @author cloor
        * @time   2019-1-3 19:42
        * @describe  : 设置同等的数据对象
        */
    private void is(JSONObject object) throws JSONException {
        String value  = object.getString("ItemValue1");
        String id = object.getString("ItemId");
        for(int i=0;i<groupBeans.size();i++){
            GroupBean groupBean = groupBeans.get(i);

            //是否动态行
            boolean isDynamicRow = groupBean.isDynamicRow();
          List<sJitemBean> list =  groupBean.getItemBeanList();
            P.c(groupBeans.size()+"groupBeans"+groupBean.getGroupName()+"===="+i+"长度是"+list.size());
          for(int j=0;j<list.size();j++){
              P.c(list.get(j).getId()+"....."+id+"........"+list.get(j).getName());
             if( list.get(j).getId()!=null&&list.get(j).getId().equals(id)){
                  //一样的话
                 if(groupBean.getGroupName().equals("尺寸规范")){
                     P.c(i+"@@@@@@"+j);
                 }
                 P.c(list.size()+"设置了"+list.get(j).getName()+"==="+list.get(j).getId());
                 sJitemBean sj =   list.get(j);
                P.c("什么情况");
                if(isDynamicRow){
                    //动态行数据自增
                    int cot = object.getInt("index");
                    int step = cot - 1;
                    if(groupBean.getStep()==0){
                        groupBean.setStep(step);
                    }
                    //重新计算真实的自增序号
                    cot = cot-groupBean.getStep();
                    P.c("动态行序号"+cot);
                    int nowt = list.size()/groupBean.getRowLi();
                        P.c("itemIndex111......"+cot);
                    P.c("长度是ooo"+list.size());
                    if(cot-nowt==1){
                        P.c("itemIndex111./////"+cot);
                        //增加动态行的量，其实应该是+1
                        //最后一个参数是计算起始点，减1是表示去掉首项列
                        P.c(list.size()+"===="+(cot-1)*groupBean.getRowLi()+"====");
                        copy(groupBean,list,object,(cot-1)*groupBean.getRowLi());
                    }else{
                        P.c("itemIndex111.```````"+object.getString("ItemValue"+(j+1)));
                        for(int c=0;c<groupBean.getRowLi();c++){
                            //赋值动态行
                            //P.c("ItemValue"+c+"------"+object.getString("ItemValue"+c));
                            //此处c-1是恢复初始化的动态行，标
                            list.get(c).setValue(object.getString("ItemValue"+(c+1)));
                            if(c==groupBean.getRowLi()-1){
                                P.c("是否是动态行"+groupBean.isCheckRecord()+"==="+groupBean.isDetermine());
                                end(c,groupBean,list,object);
                            }
                        }
                    }

                }else{
                    //非动态行处理
                    String ItemCheckValue = object.getString("ItemCheckValue");
                    if(ItemCheckValue.equals("OK")){
                        sj.setOk_ng(1);
                    }else if(ItemCheckValue.equals("NG")){
                        sj.setOk_ng(0);
                    }
                    String ItemRecordValue = object.getString("ItemRecordValue");
                    sj.setEditValue(ItemRecordValue);
                    if(value!=null&&value.length()!=0){
                        switch (sj.getItemType()){
                            case 0:
                            case 1:
                                sj.setValue(value);
                                break;
                            case 2:
                                //单选，需要做一下处理操作
                                int index3 = findSingleBox(sj,value);
                                sj.setRbc(index3);
                                break;
                            case 3:
                                boolean t[] = findSomeBox(sj,value);
                                sj.setChe(t);
                                break;
                        }
                    }
                }
                  return;
              }
          }
        }
    }

    private void copy( GroupBean groupBean, List<sJitemBean> list,JSONObject object,int index) {
        String sbs = groupBean.getCopyBean();
        try {
            JSONArray array = new JSONArray(sbs);
            int jen = array.length();

            for (int j = index; j < index+jen; j++) {
                //恢复真实数据
                int real  = j-index;
                JSONObject o1 = array.getJSONObject(real);
                sJitemBean bean = new sJitemBean();
                bean.setItemType(o1.getInt("ItemType"));
                bean.setName(o1.getString("CheckTypeName2"));
                bean.setValue(o1.getString("CheckItemContent"));
                bean.setNeedCheck(o1.getString("NeedCheck").equals("True"));
                bean.setNeedRecord(o1.getString("NeedRecord").equals("True"));
                bean.setItemIndex(object.getInt("itemIndex"));

                list.add(bean);
                //同等复制clone
                //增加一个序号，保证ItemValue一直行
                list.get(j).setValue(object.getString("ItemValue"+(real+1)));
                //

                if(j==index+jen-1){

                    end(j,groupBean,list,object);
                }

            }

            //定位nex ,这个值是包含了前面的所有项目
            //position+1  下在下一位
            //  changeDynamicRow(parent,position+1,len,line);
        } catch (JSONException e) {
            e.printStackTrace();
             P.c(e.getLocalizedMessage());


        }
    }
    /**
    * @author cloor
    * @time   2019-1-7 11:42
    * @describe  : 解析单选
    */
    private int findSingleBox(sJitemBean sj,String value){
        String temp2[] = sj.getValue().split(",");
        for(int k=0;k<temp2.length;k++){
            //计划
            if(temp2[k].equals(value)){
                return k;
            }

        }
        return -1;
    }
    /**
    * @author cloor
    * @time   2019-1-7 11:46
    * @describe  : 解析多选
    */
    private boolean[] findSomeBox(sJitemBean sj,String value){
        String temp3[] = sj.getValue().split(",");
        String tempV[]  = value.split(",");
        boolean []rest = new boolean[temp3.length];//记录temp3的真实数据量
        for(int i=0;i<temp3.length;i++){
            rest[i] = false;
                for(int j=0;j<tempV.length;j++){
                    if(temp3[i].equals(tempV[j])){
                        rest[i] = true;
                    }
                }
        }
        return  rest;
    }


    /**
     * 初始化Recycleview
     */
    protected void setRecycle(RecyclerView.Adapter adapter,
                              RecyclerView.LayoutManager manager,
                              RecyclerView.ItemDecoration itemDecoration) {
        if (rlv_all == null) {
            rlv_all =   findViewById(R.id.rlv_all);
        }
        if (manager != null) {
            rlv_all.setLayoutManager(manager);
        }
        else {
            rlv_all.setLayoutManager(new LinearLayoutManager(this));
        }

        rlv_all.setAdapter(adapter);
        if (itemDecoration != null) {
            rlv_all.addItemDecoration(itemDecoration);
        }

    }

    private void parse(JSONObject object){

        try {
            groupBeans.clear();

            JSONArray jsonArray = object.getJSONArray("chekckClass");
            int len = jsonArray.length();
            for(int i=0;i<len;i++){
                GroupBean groupBean = new GroupBean();
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject o = obj.getJSONObject("checkclasses");
                groupBean.setGroupName(o.getString("ClassName"));
                groupBean.setDynamicRow(o.getString("IsDynamicRow").equals("1"));
                groupBean.setCheckRecord(o.getString("IsCheckRecord").equals("1"));
                groupBean.setDetermine(o.getString("IsDetermine").equals("1"));
                JSONArray ja = obj.getJSONArray("firstcheckitemsList");
                int jen = ja.length();
                ArrayList<sJitemBean> jbs = new ArrayList<>();

                    for(int j=0;j<jen;j++){
                        JSONObject o1 = ja.getJSONObject(j);
                        sJitemBean bean = new sJitemBean();
                        bean.setItemType(o1.getInt("ItemType"));
                        bean.setName(o1.getString("CheckTypeName2"));
                        bean.setId(o1.getString("ID"));//加入ID序号
                        bean.setValue(o1.getString("CheckItemContent"));
                        bean.setNeedCheck(o1.getString("NeedCheck").equals("True"));
                        bean.setNeedRecord(o1.getString("NeedRecord").equals("True"));
                        bean.setItemIndex(1);
                        jbs.add(bean);
                    }

                //复制该列
                groupBean.setCopyBean(ja.toString());
                groupBean.setItemBeanList(jbs);
                groupBean.setRowLi(ja.length());//动态行长度，计算节点
                //计算动态行的可显
                if(groupBean.isDynamicRow()){
//                    List<List<sJitemBean>> rowLists = new ArrayList<>();
//                    rowLists.add(groupBean.getItemBeanList());
//                    groupBean.setRowLists(rowLists);
                    groupBean.setShowGs(getTempMap(jbs));
                }
                groupBeans.add(groupBean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            initData(obj.getLineId(),obj.getNo(),obj.getData());
        }
    }

    private void a(String sbs, ArrayList<sJitemBean> jbs){
        try {
            JSONArray array = new JSONArray(sbs);
            int jen = array.length();

            for(int j=0;j<jen;j++){
                JSONObject o1 = array.getJSONObject(j);
                sJitemBean bean = new sJitemBean();
                bean.setItemType(o1.getInt("ItemType"));
                bean.setName(o1.getString("CheckTypeName2"));
                bean.setValue(o1.getString("CheckItemContent"));
                bean.setNeedCheck(o1.getString("NeedCheck").equals("True"));
                bean.setNeedRecord(o1.getString("NeedRecord").equals("True"));
                jbs.add(bean);
                bean.setValue(""+j);
                P.c(j+"复制"+bean.getName());
                //同等复制clone
            }





        } catch (JSONException e) {
            e.printStackTrace();
            P.c(e.getLocalizedMessage());
        }
    }

    private   Map<String,Integer> getTempMap(List<sJitemBean> jbs){
        Map<String,Integer> tempMap = new HashMap<>();
        for(int m=0;m<jbs.size();m++){
            String key = jbs.get(m).getName();
            if(tempMap.containsKey(key)){
                continue;
            }else{
//                            Integer []it = new Integer[2];//固定两位，第一位是文字序号；第二位是列序号
                tempMap.put(key,m);//随便存一个临时值
            }
        }
        return tempMap;
    }
}
