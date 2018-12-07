package com.shuben.zhixing.www.patrol.twoclass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.ReViewAdapter;
import com.shuben.zhixing.www.patrol.bean.ReviewInfo;
import com.shuben.zhixing.www.patrol.bean.SubInfo;
import com.shuben.zhixing.www.util.JsonArrayRequest;
import com.shuben.zhixing.www.util.ScrollListview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/4/25.
 */

@SuppressLint("ValidFragment")
public class FragmentNotice03 extends Fragment {
    private View view_layout;
    private Context context;
    private LoadingDailog dialog;//加载动画
    private List<ReviewInfo> rList;
    private List<SubInfo> sList;
    private ListView ligth_listview;
    private String mclass;

    public FragmentNotice03(String mclass) {
        this.mclass = mclass;
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_notice03,container,false);
        context = getActivity();
        initView();
        if(mclass.equals("two")){
            initData01();
        }else{
            initData02();
        }


        return view_layout;
    }

    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(context)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        ligth_listview=(ListView) view_layout.findViewById(R.id.ligth_listview);


    }
    private void initData01() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetHighLightByGroup");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("RecordId", getActivity().getIntent().getStringExtra("RecordId"));
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetHighLightByGroup");
            myData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            myData.put("RecordId", getActivity().getIntent().getStringExtra("RecordId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****二级巡线通报亮点展示*******",myData.toString());
        JsonArrayRequest newMissRequest = new JsonArrayRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jArray) {
                Log.e("KKKK二级巡线通报亮点展示KKKK", " " + jArray.toString());

                dialog.dismiss();
                try {

                    JSONObject jData01;
                    JSONObject jData02;
                    JSONObject jData03;
                    SubInfo subInfo;
                    rList=new ArrayList<ReviewInfo>();

                    for (int i=0;i<jArray.length();i++){
                        jData01=jArray.getJSONObject(i);
                        String GroupName=jData01.getString("GroupName");
                        String WorkShopName=jData01.getString("WorkShopName");//车间
                        String GroupLeader=jData01.getString("GroupLeader");//
                        JSONObject HighLightList=jData01.getJSONObject("HighLightList");
                        JSONArray rows=HighLightList.getJSONArray("rows");
                        sList=new ArrayList<SubInfo>();
                        if(rows.length()>0){
                            for (int j=0;j<rows.length();j++){
                                jData02=rows.getJSONObject(j);
                                String HighlightDesc=jData02.getString("HighlightDesc");
                                String ImproveUserName=jData02.getString("ImproveUserName");
                                String ImproveDeptName=jData02.getString("ImproveDeptName");
                                JSONArray FileList=jData02.getJSONArray("FileList");
                                String FilePath="";
                                for (int k=0;k<FileList.length();k++){
                                    jData03=FileList.getJSONObject(k);
                                    if (!FilePath.equals("")){
                                        FilePath += "#";
                                    }
                                    FilePath += jData03.getString("FilePath");
                                }
                                subInfo=new SubInfo(HighlightDesc,ImproveUserName,ImproveDeptName,FilePath);
                                sList.add(subInfo);

                            }
                        }

                        if(sList.size()>0){
                            ReviewInfo reviewInfo=new ReviewInfo(GroupName,WorkShopName,GroupLeader,sList);
                            rList.add(reviewInfo);
                        }



                    }
                    ReViewAdapter adapter=new ReViewAdapter(getActivity(),rList);
                    ligth_listview.setAdapter(adapter);
                    new ScrollListview(ligth_listview);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );

        dialog.show();
        requestQueue.add(newMissRequest);
    }
    private void initData02() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetHighLightByTaskGroup");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("RecordId", getActivity().getIntent().getStringExtra("RecordId"));
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetHighLightByTaskGroup");
            myData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            myData.put("RecordId", getActivity().getIntent().getStringExtra("RecordId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****三级巡线通报亮点展示*****",myData.toString());
        JsonArrayRequest newMissRequest = new JsonArrayRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jArray) {
                Log.e("KKKK三级巡线通报亮点展示KKKK", " " + jArray.toString());

                dialog.dismiss();
                try {

                    JSONObject jData01;
                    JSONObject jData02;
                    JSONObject jData03;
                    SubInfo subInfo;
                    rList=new ArrayList<ReviewInfo>();

                    for (int i=0;i<jArray.length();i++){
                        jData01=jArray.getJSONObject(i);
                        String GroupName=jData01.getString("GroupName");
                        String WorkShopName=jData01.getString("WorkShopName");//车间
                        JSONObject HighLightList=jData01.getJSONObject("HighLightList");
                        JSONArray rows=HighLightList.getJSONArray("rows");
                        sList=new ArrayList<SubInfo>();
                        if(rows.length()>0){
                            for (int j=0;j<rows.length();j++){
                                jData02=rows.getJSONObject(j);
                                String HighlightDesc=jData02.getString("HighlightDesc");
                                String ImproveUserName=jData02.getString("ImproveUserName");
                                String ImproveDeptName=jData02.getString("ImproveDeptName");
                                JSONArray FileList=jData02.getJSONArray("FileList");
                                String FilePath="";
                                for (int k=0;k<FileList.length();k++){
                                    jData03=FileList.getJSONObject(k);
                                    if (!FilePath.equals("")){
                                        FilePath += "#";
                                    }
                                    FilePath += jData03.getString("FilePath");
                                }
                                subInfo=new SubInfo(HighlightDesc,ImproveUserName,ImproveDeptName,FilePath);
                                sList.add(subInfo);

                            }
                        }

                        if(sList.size()>0){
                            ReviewInfo reviewInfo=new ReviewInfo(GroupName,WorkShopName,"",sList);
                            rList.add(reviewInfo);
                        }



                    }
                    ReViewAdapter adapter=new ReViewAdapter(getActivity(),rList);
                    ligth_listview.setAdapter(adapter);
                    new ScrollListview(ligth_listview);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );

        dialog.show();
        requestQueue.add(newMissRequest);
    }


}
