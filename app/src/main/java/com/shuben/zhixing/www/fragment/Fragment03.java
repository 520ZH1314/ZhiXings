package com.shuben.zhixing.www.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseFragment;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.activity.DataAnalysisActivity;
import com.shuben.zhixing.www.adapter.OrganizeAdapter;
import com.shuben.zhixing.www.adapter.spinnerAdapter;
import com.shuben.zhixing.www.data.GetOrganizeList_Data;
import com.shuben.zhixing.www.data.GetOrganizeToDoList_Data;
import com.shuben.zhixing.www.data.GetUsersByOrgId_Data;
import com.shuben.zhixing.www.data.MyTASkDate;
import com.shuben.zhixing.www.util.DataChangeUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.SpinerPopWindow;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.XmlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/21.
 * 智行力我的组织
 */

public class Fragment03 extends BaseFragment implements View.OnClickListener{
    private View view_layout;
    private Context context;
    private ImageView tetle_back;
    private TextView tetle_text,tetle_tv1,fm_sp1,serch_text;
    private TextView fm_sp2,fm_sp3;
    private List<MyTASkDate> data;
    private OrganizeAdapter adapter;
    private ListView fm_three_list;
    private spinnerAdapter sp_adapter;
    private String tag = "";
    private RelativeLayout title_rl;

    private SpinerPopWindow<String> mSpinerPopWindow01;
    private SpinerPopWindow<String> mSpinerPopWindow02;
    private SpinerPopWindow<String> mSpinerPopWindow03;

    private List<String> list_name01=new ArrayList<String>();
    private List<String> list_name02=new ArrayList<String>();
    private List<String> list_name03=new ArrayList<String>();
    private List<Integer> list_id01=new ArrayList<Integer>();
    private List<String> list_id02=new ArrayList<String>();
    private List<String> list_id03=new ArrayList<String>();
    private RequestQueue mQueue;
    //2.2.15	我的组织->获取部门列表
    private List<GetOrganizeList_Data> GetOrganizeList_list_data;
    private GetOrganizeList_Data mGetOrganizeList_Data;

    //2.2.16	我的组织->获取部门人员
    private List<GetUsersByOrgId_Data> GetUsersByOrgId_list_data;
    private GetUsersByOrgId_Data mGetUsersByOrgId_Data;

    //2.2.17	我的组织->部门任务（列表）
    private List<GetOrganizeToDoList_Data> GetOrganizeToDoList_list_data;
    private GetOrganizeToDoList_Data mGetOrganizeToDoList_Data;


    private String  orgIds="";
    private String  userId="";
    private int  source=0;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment03,container,false);
        context = getActivity();
        mQueue = Volley.newRequestQueue(getActivity());
        initData01();//spiner假数据
        mSpinerPopWindow01 = new SpinerPopWindow<String>(getActivity(), list_name01,itemClickListener);
        mSpinerPopWindow02 = new SpinerPopWindow<String>(getActivity(), list_name02,itemClickListener);
        mSpinerPopWindow03 = new SpinerPopWindow<String>(getActivity(), list_name03,itemClickListener);
        mSpinerPopWindow01.setOnDismissListener(dismissListener);
        mSpinerPopWindow02.setOnDismissListener(dismissListener);
        mSpinerPopWindow03.setOnDismissListener(dismissListener);
        init();

       //2.2.15	我的组织->获取部门列表
       GetOrganizeListWebDate();

//        //2.2.16	我的组织->获取部门人员
//
//        //2.2.17	我的组织->部门任务（列表）


        return view_layout;
    }
    //id初始化
    private void init() {
        data = getDate();
        title_rl = (RelativeLayout)view_layout.findViewById(R.id.title_rl);
        title_rl.setVisibility(View.VISIBLE);

        tetle_back = (ImageView)view_layout.findViewById(R.id.tetle_back);
        tetle_back.setVisibility(View.GONE);

        tetle_text = (TextView) view_layout.findViewById(R.id.tetle_text);
        tetle_text.setText("我的组织");

        tetle_tv1 = (TextView) view_layout.findViewById(R.id.tetle_tv1);
        tetle_tv1.setText("分析");

        fm_sp1 = (TextView) view_layout.findViewById(R.id.fm_sp1);
        fm_sp2 = (TextView) view_layout.findViewById(R.id.fm_sp2);
        fm_sp3 = (TextView) view_layout.findViewById(R.id.fm_sp3);
        fm_three_list = (ListView) view_layout.findViewById(R.id.fm_three_list);

        serch_text=(TextView) view_layout.findViewById(R.id.fm_item_jindu);

        twospinner();
        setOnclick();
    }


    private void setOnclick() {
        //控件监听
        title_rl.setOnClickListener(this);
        fm_sp1.setOnClickListener(this);
        fm_sp2.setOnClickListener(this);
        fm_sp3.setOnClickListener(this);
        serch_text.setOnClickListener(this);
    }
    //自定义布局sp
    private void twospinner() {

       // sp_adapter = new spinnerAdapter(getActivity(),datass);
        //fm_sp2.setAdapter(sp_adapter);
//
//        sp_adapter = new spinnerAdapter(getActivity(),datass);
//        fm_sp3.setAdapter(sp_adapter);
    }
    /**
     * spiner初始化假数据
     */
    private void initData01() {
        list_name01 = new ArrayList<String>();
        list_name01.add("全部来源");
        list_name01.add("高效会议");
        list_name01.add("任务交办");
        list_name01.add("内部催单");
        list_name01.add("外部催单");
        list_id01.add(0);
        list_id01.add(1);
        list_id01.add(2);
        list_id01.add(3);
        list_id01.add(4);
    }
    private void initData02() {
        list_name02 = new ArrayList<String>();
        list_name02.add("全部来源2");
        list_name02.add("高效会议2");
        list_name02.add("任务交办2");
        list_name02.add("内部催单2");
        list_name02.add("外部催单2");
    }
    private void initData03() {
        list_name03 = new ArrayList<String>();
        list_name03.add("全部来源3");
        list_name03.add("高效会议3");
        list_name03.add("任务交办3");
        list_name03.add("内部催单3");
        list_name03.add("外部催单3");
    }

    private List<MyTASkDate> getDate() {
        //假数据来源，有借口了在此写接口
        List<MyTASkDate> datas = new ArrayList<>();

        for (int i =0;i<6;i++){
            MyTASkDate nd = new MyTASkDate();
            nd.setTitle("我的通知"+1);
            nd.setTaskNo("2017822"+1);
            nd.setSource("高级会议");
            nd.setTaskStatusName("进行中");
            nd.setUser("李聪老师");
            nd.setCreateDate("2017-8-22");
            datas.add(nd);
        }
        return datas;
    }

    //2.2.15	我的组织->获取部门列表
    private void GetOrganizeListWebDate() {
        //催单客户统计接口
        String Url = UrlUtil.GetOrganizeList(UrlUtil.IP, UrlUtil.GetOrganizeList, SharedPreferencesTool.getMStool(getActivity()).getUserId());
        Log.e("获取部门列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        GetOrganizeList_list_data = new ArrayList<>();
                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);
                        GetOrganizeList_list_data.clear();
                        try {
                            JSONObject jsonObject;
                            JSONObject jsonData;
                            JSONArray arrayData = new JSONArray(response);
                            for(int i = 0; i < arrayData.length(); i++){
                                JSONArray array = new JSONArray(response);
                                for (int j = 0; j < array.length(); j++) {
                                    jsonData = array.getJSONObject(j);
                                    String id = jsonData.getString("id");//任务ID
                                    String text = jsonData.getString("text");//任务内容
                                    mGetOrganizeList_Data = new GetOrganizeList_Data(id, text);
                                    GetOrganizeList_list_data.add(mGetOrganizeList_Data);
                                    Log.e("部门1",GetOrganizeList_list_data.get(i).getText());
                                }
                            }


                            for (int i=0;i<GetOrganizeList_list_data.size();i++){
                                list_name02.add(GetOrganizeList_list_data.get(i).getText());
                                list_id02.add(GetOrganizeList_list_data.get(i).getId());
                                Log.e("部门2",GetOrganizeList_list_data.get(i).getText());
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }

    //2.2.16	我的组织->获取部门人员
    private void GetUsersByOrgIdWebDate( String id) {

        //催单客户统计接口
        String Url = UrlUtil.GetUsersByOrgId(UrlUtil.IP, UrlUtil.GetUsersByOrgId, id);
        Log.e("获取获取部门人员列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        GetUsersByOrgId_list_data = new ArrayList<>();
                        GetUsersByOrgId_list_data.clear();
                        list_name03.clear();
                        list_id03.clear();
                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);
                        GetUsersByOrgId_list_data.clear();
                        try {
                            JSONObject jsonObject;
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);
                                String UserId = jsonObject.getString("UserId");//任务ID
                                String UserCode = jsonObject.getString("UserCode");//任务内容
                                String UserName = jsonObject.getString("UserName");//任务编号
                                mGetUsersByOrgId_Data = new GetUsersByOrgId_Data(UserId, UserCode, UserName);
                                GetUsersByOrgId_list_data.add(mGetUsersByOrgId_Data);
                                //获取部门后添加到列表中
                            }

                            for (int i=0;i<GetUsersByOrgId_list_data.size();i++){
                                list_name03.add(GetUsersByOrgId_list_data.get(i).getUserName());
                                list_id03.add(GetUsersByOrgId_list_data.get(i).getUserId());
                                Log.e("人员2",GetUsersByOrgId_list_data.get(i).getUserName());
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }


    //2.2.17	我的组织->部门任务（列表）
    private void GetOrganizeToDoListWebDate() {

        //催单客户统计接口
        String Url = UrlUtil.GetOrganizeToDoList(UrlUtil.IP, UrlUtil.GetOrganizeToDoList, orgIds,source,userId,"1","20");
        Log.e("获取部门任务列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        GetOrganizeToDoList_list_data = new ArrayList<>();
                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);
                        GetOrganizeToDoList_list_data.clear();
                        try {
                            JSONObject jsonData = new JSONObject(response);
                            JSONObject jsonObject;
                            JSONArray array = jsonData.getJSONArray("rows");
                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);


                                  String  ToDoListId = jsonObject.getString("ToDoListId");
                                  String  TaskNo = jsonObject.getString("TaskNo");
                                  String  Title = jsonObject.getString("Title");
                                  String  Contents = jsonObject.getString("Contents");
                                  String  Source = DataChangeUtil.getInstance().getSource(jsonObject.getInt("Source"));
                                  String  TaskType = jsonObject.getString("TaskType");
                                  String  CreateDate = jsonObject.getString("CreateDate");
                                  String  CreateUserId = jsonObject.getString("CreateUserId");
                                  String  ToDoUserId = jsonObject.getString("ToDoUserId");
                                  String  DoType = jsonObject.getString("DoType");
                                  String  DueDate = jsonObject.getString("DueDate");
                                  String  CompleteDate = jsonObject.getString("CompleteDate");
                                  String  TaskStatus = jsonObject.getString("TaskStatus");
                                  String  IsRead = jsonObject.getString("IsRead");
                                  String  EvaluateScore = jsonObject.getString("EvaluateScore");
                                  String  ToDoUser = jsonObject.getString("ToDoUser");
                                  String  ToDoDept = jsonObject.getString("ToDoDept");
                                  String  Cycle = jsonObject.getString("Cycle");
                                  String  Countdown = jsonObject.getString("Countdown");
                                  String  TaskStatusName = jsonObject.getString("TaskStatusName");
                                      mGetOrganizeToDoList_Data = new GetOrganizeToDoList_Data(ToDoListId, TaskNo, Title,Contents,Source,TaskType,CreateDate,
                                        CreateUserId,ToDoUserId,DoType,DueDate,CompleteDate,TaskStatus,IsRead,EvaluateScore,ToDoUser,ToDoDept,Cycle,Countdown,TaskStatusName);
                                GetOrganizeToDoList_list_data.add(mGetOrganizeToDoList_Data);

                            }
                            adapter = new OrganizeAdapter(getActivity(),GetOrganizeToDoList_list_data);
                            fm_three_list.setAdapter(adapter);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.title_rl:
                intent = new Intent(getActivity(), DataAnalysisActivity.class);
                intent.putExtra("OrgIds",orgIds);
                startActivity(intent);
                break;
            case R.id.fm_sp1:
                tag = "one";
                mSpinerPopWindow01.setWidth(fm_sp1.getWidth());
                mSpinerPopWindow01.showAsDropDown(fm_sp1);
                setTextImage(R.mipmap.icon_up);
                break;

            case R.id.fm_sp2:
                tag = "two";
                mSpinerPopWindow02.setWidth(fm_sp2.getWidth());
                mSpinerPopWindow02.showAsDropDown(fm_sp2);
                setTextImage_two(R.mipmap.icon_up);
                break;

            case R.id.fm_sp3:
                tag = "three";
                mSpinerPopWindow03.setWidth(fm_sp3.getWidth());
                mSpinerPopWindow03.showAsDropDown(fm_sp3);
                setTextImage_three(R.mipmap.icon_up);
                break;
            case R.id.fm_item_jindu:
                GetOrganizeToDoListWebDate();
                break;





        }


    }

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

            if ("one".equals(tag)){
                mSpinerPopWindow01.dismiss();
                fm_sp1.setText(list_name01.get(position));
                source= list_id01.get(position);
            }else if("two".equals(tag)){
                mSpinerPopWindow02.dismiss();
                fm_sp2.setText(list_name02.get(position));
                GetUsersByOrgIdWebDate(list_id02.get(position));
                orgIds=list_id02.get(position);

            }else if("three".equals(tag)){
                mSpinerPopWindow03.dismiss();
                fm_sp3.setText(list_name03.get(position));
                userId=list_id03.get(position);
            }
        }
    };

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener=new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            if ("one".equals(tag)){
                setTextImage(R.mipmap.icon_down);
            }else if("two".equals(tag)){
                setTextImage_two(R.mipmap.icon_down);
            }else if("three".equals(tag)){
                setTextImage_three(R.mipmap.icon_down);
            }

        }
    };


    /**
     * 给TextView右边设置图片
     * @param resId
     */
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        fm_sp1.setCompoundDrawables(null, null, drawable, null);
    }
    private void setTextImage_two(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        fm_sp2.setCompoundDrawables(null, null, drawable, null);
    }

    private void setTextImage_three(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        fm_sp3.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void process(Message msg) {

    }
}
