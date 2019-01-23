package com.zhixing.masslib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.Tips;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CharAvatarView;
import com.base.zhixing.www.widget.CommonTips;
import com.base.zhixing.www.widget.XEditText;
import com.zhixing.masslib.R;
import com.zhixing.masslib.bean.BaseExpandableBean;
import com.zhixing.masslib.bean.GroupBean;
import com.zhixing.masslib.bean.sJitemBean;
import com.zhixing.masslib.widget.AutoLinefeedLayout;
import com.zhixing.masslib.widget.MyTextView;
import com.zhixing.masslib.widget.RadioGroupEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.beppi.tristatetogglebutton_library.TriStateToggleButton;

/**
* @author cloor
* @time   2018-12-26 10:19
* @describe  :
*/
public class ExpandableRecycleAdapter extends BaseExpandableRecycleAdapter<GroupBean> {
    private Context context;
    private List<GroupBean> listAll;
    private Map<String,String> constant;
    public ExpandableRecycleAdapter(Context context, List<GroupBean> list,Map<String,String> constant) {
        super(context, list);
        this.context = context;
        this.constant = constant;
        this.listAll = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseExpandableBean.GROUP) {
            return new GropViewHolder(inflater.inflate(R.layout.adatper_group, parent, false));
        }
        else {

            return new ItemViewHolder(inflater.inflate(R.layout.adatper_group_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == BaseExpandableBean.GROUP) {
            GropViewHolder gropViewHolder = (GropViewHolder) holder;
            gropViewHolder.setData(list, position);
        }
        else {
            ItemViewHolder  itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.setData(list, position);


        }
    }

    public class GropViewHolder extends BaseExpandableViewHolder<GroupBean> {

        private TextView tv_group;

        @Override
        public void showGroupAndItemBean(GroupBean groupBean,
                                         BaseExpandableBean expandableBean,int position) {
            tv_group.setText(groupBean.getGroupName());
        }

        public GropViewHolder(View itemView) {
            super(itemView);
            tv_group = (TextView) itemView.findViewById(R.id.tv_group);
            tv_group.setOnClickListener(this);
            setGroupSelectView(tv_group);
        }

        @Override
        public void onClick(View v) {
            setGroupSelecltViewOnClick();
        }
    }
   /* //计算节点
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
    }*/
    public class ItemViewHolder extends BaseExpandableViewHolder<GroupBean> {


        private LinearLayout ll_title;
        private TriStateToggleButton view3;
        private TextView view0;
        private AutoLinefeedLayout view1;
        //view2是备注
        private XEditText view2;
        private CharAvatarView viewT;
        private LinearLayout add_aro;
        private ImageView del_aro;
        @Override
        public void showGroupAndItemBean(GroupBean groupBean, BaseExpandableBean expandableBean,int position) {
           /* tv_item.setText("组别：" + groupBean.getGroupName() +
                    "成员：" + groupBean.getItemBeanList().get(expandableBean.getItemNumber()).getItem());*/

            int index = expandableBean.getItemNumber();
            int parent  = expandableBean.getGroupPosition();
            int len = groupBean.getItemBeanList().size();
            List<sJitemBean> tempList = groupBean.getItemBeanList();
            int line = groupBean.getRowLi();
                P.c("拍错"+tempList.size()+"==="+index+"====="+position);
              sJitemBean b = tempList.get(index);

            viewT.setVisibility(View.GONE);
            del_aro.setVisibility(View.GONE);
           if(groupBean.isDynamicRow()){
               //名称倍数+bei
                  int bei = 0;
                    try {
                          bei = (index/line)*line+groupBean.getShowGs().get(b.getName());
                    }catch (Exception e){

                    }
                     //动态行，需要过滤
                    if(index==bei){
                        if(index%line==0){
                            String xulie = String.valueOf(index/line+1);
                            viewT.setVisibility(View.VISIBLE);
                            //动态组  默认字一个不进行
                            del_aro.setVisibility(index==0?View.GONE:View.VISIBLE);
                            //是当前值,序号显示
                            viewT.setText(xulie);
                        }
                        if(bei==index){
                            view0.setText(b.getName());

                        }else{
                            view0.setText("");
                        }

                    }else{
                        view0.setText("");
                    }
                    //通配机算方式，计算动态行每组的附加项
                    if((index+1)%line==0){
                        //节点
                        if(groupBean.isDetermine()){
                            view3.setVisibility(View.VISIBLE);
                        }else{
                            view3.setVisibility(View.GONE);
                        }
                        if( groupBean.isCheckRecord()){
                                view2.setVisibility(View.VISIBLE);

                        }else{
                            view2.setVisibility(View.GONE);
                        }
                    }else {
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                    }
                    //计算节点
                    //添加按钮，这个是不变的

               P.c("长度是"+(len-1) +"==="+index+"===="+line*6+"====="+tempList.size());

               if(len-1==index&&line*6!=tempList.size()) {
                   add_aro.setVisibility(View.VISIBLE);
               }else{
                   add_aro.setVisibility(View.GONE);
               }
               del_aro.setOnClickListener(view -> {
                   //清除当前位置+组长度
                   CommonTips tips = new CommonTips(context,null);
                   tips.init("取消","删除","是否删除\n"+groupBean.getGroupName()+"序列"+viewT.getText());
                   tips.setI(new Tips() {
                       @Override
                       public void cancel() {
                       }
                       @Override
                       public void sure() {
                           getPosi(groupBean,tempList.get(index).getItemIndex());
                            for(int i=0;i<line;i++){
                              tempList.remove(index);
                            }

                            changeDynamicRow(position,line);

                       }
                   });
                   tips.showSheet();
                  // Toasty.INSTANCE.showToast(context,"del");
               });
               add_aro.setOnClickListener(view -> {
                       //增加一组动态行
                        int max = 6;

                       Toasty.INSTANCE.showToast(context,"已新增一组"+groupBean.getGroupName());
                        //复制多个项，组成一个组，从中间插入
                       String sbs =  groupBean.getCopyBean();
                       try {
                           JSONArray array = new JSONArray(sbs);
                           int jen = array.length();

                            int last = tempList.get(tempList.size()-1).getItemIndex();
                            int mapLast = getLast(groupBean);
                            if(mapLast>=last){
                                //序号不能大于或等于初始化的最大值
                                last = mapLast;
                            }
                           for(int j=0;j<jen;j++){
                               JSONObject o1 = array.getJSONObject(j);
                               sJitemBean bean = new sJitemBean();
                               bean.setItemType(o1.getInt("ItemType"));
                               bean.setName(o1.getString("CheckTypeName2"));
                               bean.setValue(o1.getString("CheckItemContent"));
                               bean.setNeedCheck(o1.getString("NeedCheck").equals("True"));
                               bean.setNeedRecord(o1.getString("NeedRecord").equals("True"));
                               P.c("增加的序号是："+(last+1));
                                bean.setItemIndex(last+1);
                               tempList.add(bean);
                               //同等复制clone
                           }

                            //定位nex ,这个值是包含了前面的所有项目
                           //position+1  下在下一位
                           changeDynamicRow(parent,position+1,len,line);
                       } catch (JSONException e) {
                           e.printStackTrace();
                           P.c(e.getLocalizedMessage());
                       }
                   });
           }else{
               //增加界面
               add_aro.setVisibility(View.GONE);
//               viewT.setVisibility(View.GONE);
               view0.setText(b.getName());
               //非动态行,每个都要
               if(b.isNeedRecord()){
                   view2.setVisibility(View.VISIBLE);
               }else{
                   view2.setVisibility(View.GONE);
               }
               if(b.isNeedCheck()){
                   view3.setVisibility(View.VISIBLE);
               }else{
                   view3.setVisibility(View.GONE);
               }
           }

            view3.setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
                @Override
                public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean b, int i) {
                    switch (toggleStatus) {
                        case off:
                            listAll.get(parent).getItemBeanList().get(index).setOk_ng(-1);
                            break;
                        case mid:
                            listAll.get(parent).getItemBeanList().get(index).setOk_ng(0);
                            break;
                        case on:
                            listAll.get(parent).getItemBeanList().get(index).setOk_ng(1);
                            break;
                    }
                }
            });

            switch (b.getOk_ng()) {
                case -1:
                    view3.setToggleStatus(TriStateToggleButton.ToggleStatus.off);
                    break;
                case 0:
                    view3.setToggleStatus(TriStateToggleButton.ToggleStatus.mid);
                    break;
                case 1:

                    view3.setToggleStatus(TriStateToggleButton.ToggleStatus.on);
                    break;
            }


//            view3.invalidate();
//            addView2Lis(parent,index);

            P.c(parent+"--"+index+"保存的数据是测试===============??"+b.getEditValue());
            view2.setTag(parent+"-"+index);
            view2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    /**
                    * @author cloor
                    * @time   2018-12-27 15:37
                    * @describe  : 以此方式确定
                    */
                        if(view2.getTag().toString().equals(parent+"-"+index)){
                            listAll.get(parent).getItemBeanList().get(index).setEditValue(charSequence.toString());
                        }
                }
                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            view2.setTextEx(b.getEditValue());
            view1.removeAllViews();

            switch (b.getItemType()){
                case 0:
                    //只读
                    MyTextView t = getView0();
                    view1.addView(t);
                    t.setText(b.getValue());
                    break;
                case 1:
                    //文本
                    XEditText x = getView1(parent,index);
                    view1.addView(x);
                    x.setTextEx(b.getValue());
                    break;
                case 2:
                    //单选
                    RadioGroup ex = new RadioGroup(context);
                    ex.setOrientation(RadioGroupEx.VERTICAL);
                    String temp2[] = b.getValue().split(",");

                    for(int i=0;i<temp2.length;i++){
                        RadioButton rb = new RadioButton(context);
                        rb.setText(temp2[i]);
                        ex.addView(rb);
                        if(b.getRbc()==i){
                            rb.setChecked(true);
                        }
                    }
                    view1.addView(ex);
                    ex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            P.c("点击的是"+i);
                            for(int k=0;k<radioGroup.getChildCount();k++){
                                //统计点击的属于哪一个位置
                               if( radioGroup.getChildAt(k).getId()==i){
                                   b.setRbc(k);
                               }
                            }
                        }
                    });
                    break;
                case 3:
                    //多选
                    String temp3[] = b.getValue().split(",");
                    for(int i=0;i<temp3.length;i++){
                        CheckBox checkBox = new CheckBox(context);
                        checkBox.setText(temp3[i]);
                        if(b.getChe()!=null){
                            checkBox.setChecked(b.getChe()[i]);
                        }
                        view1.addView(checkBox);
                        //监听多选框，并且赋值给
                        checkBox.setOnCheckedChangeListener((compoundButton, b1) -> {
                            boolean []st = new boolean[temp3.length];
                            for(int k=0;k<temp3.length;k++){
                                View v = view1.getChildAt(k);
                                st[k] = ((CheckBox)v).isChecked();
                            }
                            b.setChe(st);
                        });
                    }

                    break;
                case 4:
                    //系统
                    MyTextView t4 = getView0();
                    view1.addView(t4);
                    t4.setText(constant.get(view0.getText().toString()));


                    break;
            }
        }
        /**
        * @author cloor
        * @time   2019-1-22 14:17
        * @describe  : 获取最后一项
        */
        private int getLast(GroupBean groupBean){
            LinkedHashMap map = groupBean.getItemV();
            Set s = map.keySet();
            Iterator i = s.iterator();
            int last = -1;
            while(i.hasNext()){
                last = Integer.parseInt(i.next().toString());
            }
            P.c("这个值是"+last);
            return last;
        }
        /**
        * @author cloor
        * @time   2019-1-22 14:17
        * @describe  : 寻找位置
        */
        private void  getPosi(GroupBean groupBean,int key){

            LinkedHashMap map = groupBean.getItemV();
            if(map.containsKey(key)){
                map.put(key,-1);

            }else{

            }

        }


        public ItemViewHolder(View itemView) {
            super(itemView);
            ll_title =  itemView.findViewById(R.id.ll_title);
            view1 = itemView.findViewById(R.id.view1);
            view3 = itemView.findViewById(R.id.view3);
            view0 = itemView.findViewById(R.id.view0);
            view2  = itemView.findViewById(R.id.view2);
            viewT = itemView.findViewById(R.id.viewT);
            add_aro = itemView.findViewById(R.id.add_aro);
            del_aro = itemView.findViewById(R.id.del_aro);
            view3.setMidColor(context.getColor(R.color.red));


        }

        @Override
        public void onClick(View v) {

            if (onExpandableClickListener != null) {
                onExpandableClickListener.onExpandableClick(v, expandableBean);
            }
        }

        private MyTextView getView0(){
           MyTextView t = (MyTextView) inflater.inflate(R.layout.wd_txt,null);
            t.setTextColor(context.getResources().getColor(R.color.txt_nor));
            t.setTextSize(15);
            return t;
        }
        /**
        * @author cloor
        * @time   2018-12-27 15:32
        * @describe  : 自加按钮的实现
        */
        private XEditText getView1(int p,int i){
            XEditText t = (XEditText) inflater.inflate(R.layout.wd_edit,null);

            t.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            t.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
                    P.c(p+"---"+i+"你看呢。。。aa"+charSequence.toString());

                        listAll.get(p).getItemBeanList().get(i).setValue(charSequence.toString());


                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            return t;
        }

    }
}
