package com.shuben.zhixing.www.reminder.r_fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.activity.PostAsyncTask;
import com.shuben.zhixing.www.reminder.MyjourneyTypeActivity;
import com.shuben.zhixing.www.util.CustomToast;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.wheelview.util.SharedPreferences_wheel;
import com.shuben.zhixing.www.wheelview.util.Wheelview_SelectPicPopupWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Administrator on 2017/8/21.
 */
//创建催单
public class CreateReminderssFragment extends Fragment implements View.OnClickListener{
    private ViewPager clientele_viewpager;
    private View view_layout;
    private Activity context;
    private ImageView tetle_back,createreminder_Responsible_clear;
    private RelativeLayout create_item1,create_item2;
    private CustomToast toast;
    private View view01,view02;
    private TextView tetle_text,login_login;
    private List<View> mLayoutList;
    private int currIndex;
    //内部催单
    private TextView  createreminder_cargo_time,createreminder_Responsible_tv, createreminder_lead_tv;
    private EditText createreminder_user_name_tv,createreminder_cargo_name_et,createreminder_cargo_number_et,createreminder_cargo_add_et,createreminder_cargo_id_et,createreminder_cargo_Gid_et;

    private TextView  createreminder2_user_name_tv,createreminder2_cargo_time,createreminder2_Responsible_tv, createreminder2_lead_tv;
    private EditText createreminder2_cargo_name_et,createreminder2_cargo_number_et,createreminder2_cargo_add_et,createreminder2_cargo_id_et,createreminder2_cargo_Gid_et;
   //判断需要
    private String TAG = "",item_code="",name;
    private String datas_type;//类型，来判断客户名称是否有必要输入
    private String argo_name_et,cargo_number_et,cargo_add_et,cargo_id_et,cargo_Gid_et,Responsible_tv,lead_tv;

    //时间弹出框
    private String initEndDateTime = "2017-09-03"; // 初始化结束时间
    private Wheelview_SelectPicPopupWindow menuWindow;
    private SharedPreferences_wheel sharedPreferencesTool;
    private LayoutInflater mLayoutInflater;

    private RequestQueue mQueue;

    private Map<String,String> ToDoUserMap=new HashMap<String,String>();//责任人
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_createreminders_item1,container,false);
        context = getActivity();
        toast = new CustomToast(getActivity());
        sharedPreferencesTool = new SharedPreferences_wheel(getActivity());

        init();
        return view_layout;
    }

    //id初始化
    private void init() {
        mQueue = Volley.newRequestQueue(context);
        tetle_back = (ImageView)view_layout.findViewById(R.id.tetle_back);

        tetle_text = (TextView) view_layout.findViewById(R.id.tetle_text);
        tetle_text.setText("创建内部催单");

        createreminder_user_name_tv = (EditText) view_layout.findViewById(R.id.createreminder_user_name_tv);//客户、采购
        createreminder_cargo_time = (TextView) view_layout.findViewById(R.id.createreminder_cargo_time_tv);//交货时间
        //createreminder_cargo_time.setText(initEndDateTime);
        createreminder_Responsible_tv = (TextView) view_layout.findViewById(R.id.createreminder_Responsible_tv);//责任人
        createreminder_lead_tv = (TextView) view_layout.findViewById(R.id.createreminder_lead_tv);//抄送人

        createreminder_cargo_name_et = (EditText) view_layout.findViewById(R.id.createreminder_cargo_name_et);//产品名称
        createreminder_cargo_number_et = (EditText) view_layout.findViewById(R.id.createreminder_cargo_number_et);//交货数量
        createreminder_cargo_add_et = (EditText) view_layout.findViewById(R.id.createreminder_cargo_add_et);//交货地点
        createreminder_cargo_id_et = (EditText) view_layout.findViewById(R.id.createreminder_cargo_id_et);//订单号
        createreminder_cargo_Gid_et = (EditText) view_layout.findViewById(R.id.createreminder_cargo_Gid_et);//工单号
        createreminder_Responsible_clear=(ImageView)view_layout.findViewById(R.id.createreminder_Responsible_clear);
        login_login = (TextView) view_layout.findViewById(R.id.login_login);//确认添加按钮

        //监听
        //createreminder_user_name_tv.setOnClickListener(this);
        createreminder_cargo_time.setOnClickListener(this);
        createreminder_Responsible_tv.setOnClickListener(this);
        createreminder_lead_tv.setOnClickListener(this);
        login_login.setOnClickListener(this);
        //跳转选择客户名称
        tetle_back.setOnClickListener(this);
        createreminder_Responsible_clear.setOnClickListener(this);
    }




    //获取客户或者供应商列表数据的接口，跳转页面
    private void gotoDage(String str) {
        if ("sex".equals(str)){
            //内购紧急催单抄送人选择姓名
            Intent intent = new Intent(getActivity(),MyjourneyTypeActivity.class);
            intent.putExtra("TAG","nei_chaosong");//根据传递的参数调用不同的接口
            startActivityForResult(intent, 106);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            datas_type = data.getStringExtra("item_name");
            Log.i("Text","888888888888"+datas_type+"433:"+resultCode+"7777777:"+requestCode);
        }

        //获取到客户的姓名和紧急催单的供应商
       /* if(requestCode ==101){
            if (resultCode ==10) {
                if (datas_type!=null){
                    datas_type = data.getStringExtra("item_name");
                    createreminder_user_name_tv.setText(datas_type);
                    Log.i("Text","888888888888"+datas_type);
                }
                //设置结果显示框的显示数值
            }
        }else*/  if (requestCode ==105){
            if (resultCode ==10) {
                if (datas_type!=null){
                    datas_type = data.getStringExtra("item_name");
                    ToDoUserMap.put(datas_type,data.getStringExtra("item_id"));

                        createreminder_Responsible_tv.setText(datas_type);


                }
            }


        }else if (requestCode ==106){
            if (resultCode ==10) {
                if (datas_type!=null){
                    datas_type = data.getStringExtra("item_name");
                    if(createreminder_lead_tv.getText().toString().equals("")){
                        createreminder_lead_tv.setText(datas_type);
                    }else{
                        createreminder_lead_tv.setText(createreminder_lead_tv.getText().toString()+","+datas_type);
                    }
                }
            }
        }else  {
            Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()){
            case R.id.tetle_back:
                getActivity().finish();
                break;

            case R.id.createreminder_Responsible_tv:
                //内购紧急催单责任人选择姓名
                 intent = new Intent(getActivity(),MyjourneyTypeActivity.class);
                intent.putExtra("TAG","nei_zeren");//根据传递的参数调用不同的接口
                startActivityForResult(intent, 105);
                break;


            case R.id.createreminder_lead_tv:
                //内购紧急催单抄送人选择姓名
                intent = new Intent(getActivity(),MyjourneyTypeActivity.class);
                intent.putExtra("TAG","nei_chaosong");//根据传递的参数调用不同的接口
                intent.putExtra("ToDoUserId",ToDoUserMap.get(createreminder_Responsible_tv.getText().toString()));//根据传递的参数调用不同的接口
                startActivityForResult(intent, 106);
                break;
            case R.id.login_login:
                argo_name_et = createreminder_cargo_name_et.getText().toString().trim();
                cargo_number_et = createreminder_cargo_number_et.getText().toString().trim();
                cargo_add_et = createreminder_cargo_add_et.getText().toString().trim();
                cargo_id_et=createreminder_cargo_id_et.getText().toString().trim();
                cargo_Gid_et=createreminder_cargo_Gid_et.getText().toString().trim();
                Responsible_tv = createreminder_Responsible_tv.getText().toString().trim();//责任人
                lead_tv = createreminder_lead_tv.getText().toString().trim();;//抄送人
                if (argo_name_et.isEmpty()||cargo_number_et.isEmpty()||cargo_add_et.isEmpty()||cargo_id_et.isEmpty()||cargo_Gid_et.isEmpty()||Responsible_tv.isEmpty()||lead_tv.isEmpty()){
                    toast.showToast("客户姓名、交货数量、交货地点输入框不可为空！");
                }else {
                    //调用添加接口
                    add();

                }
                break;



            case R.id.createreminder_cargo_time_tv:
                sharedPreferencesTool.save_Key("");
                TAG = "consultanstroke_stop_time";
                menuWindow = new Wheelview_SelectPicPopupWindow(getActivity(), itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置

                break;

            case R.id.createreminder_Responsible_clear:
                ToDoUserMap.clear();
                createreminder_Responsible_tv.setText("");
                break;



        }
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
                case R.id.btn_ok:
                     if (TAG.equals("consultanstroke_stop_time")){
                        String value = sharedPreferencesTool.read_Key();
                         Log.i("Text","666666666666666666"+value);
                        if (value!=null&&value.length()>1||!value.equals("")&&value.length()>1){
                            createreminder_cargo_time.setText(value);
                        }else{
                            createreminder_cargo_time.setText(value);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

   public void add(){
       //Map<String, String> map = new HashMap<String, String>();
        JSONObject result=new JSONObject();
        JSONObject list=new JSONObject();
        JSONObject data=new JSONObject();
        JSONArray deleted=new JSONArray();
        JSONArray inserted=new JSONArray();
        JSONArray updated=new JSONArray();


       try {
            data.put("InnerUrgeOrderId", UUID.randomUUID().toString());
            data.put("BillNo", "");
            data.put("CustomerName", createreminder_user_name_tv.getText().toString());
            data.put("ProductName",createreminder_cargo_name_et.getText().toString());
            data.put("DeliverQty", createreminder_cargo_number_et.getText().toString());
            data.put("DeliverDate", createreminder_cargo_time.getText().toString());
            data.put("DeliverAddress", createreminder_cargo_add_et.getText().toString());
            data.put("OrderNo", createreminder_cargo_id_et.getText().toString());
            data.put("WorkOrderNo", createreminder_cargo_Gid_et.getText().toString());
            data.put("CreateDate", DateUtil.getInstance().getDateOfToDay());
            data.put("CreateUserId", SharedPreferencesTool.getMStool(getActivity()).getUserId());
            data.put("ToDoUserId", ToDoUserMap.get(createreminder_Responsible_tv.getText().toString()));
           //data.put("ToDoUserId", "bb81510f-6251-41c1-828f-ad336cc5c4c3");
            data.put("CurrentStep", 0);
            data.put("EvaluateScore", 0);
            inserted.put(0, data);
            list.put("deleted", deleted);
            list.put("inserted", inserted);
            list.put("updated", updated);
            list.put("_changed", true);
            result.put("list", list);
       } catch (JSONException e) {
           e.printStackTrace();
       }
        PostAsyncTask postAsyncTask=new PostAsyncTask(context,result,0);
        String postUrl= UrlUtil.GetPostUrl(UrlUtil.IP,UrlUtil.InnerUrgeOrder);
        postAsyncTask.execute(postUrl);


   }





}
