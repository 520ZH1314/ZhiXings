package com.shuben.zhixing.www.reminder;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.activity.PostAsyncTask;
import com.shuben.zhixing.www.util.DateUtil;
import com.shuben.zhixing.www.util.SpinerPopWindow;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.wheelview.util.SharedPreferences_wheel;
import com.shuben.zhixing.www.wheelview.util.Wheelview_SelectPicPopupWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/22.
 * 任务子菜单
 */

public class ReplyActivity extends BaseActvity implements View.OnClickListener {
    private ImageView tetle_back;
    private TextView tetle_text,reply_ok,reply_time;
    private EditText reply_number,reply_content;
    private String BillNo,deliverQty,deliverDate,type="";
    private int step=0;
    private List<String> list;
    private SpinerPopWindow<String> mSpinerPopWindow;
    private TextView reply_type;

    private Wheelview_SelectPicPopupWindow menuWindow;
    private SharedPreferences_wheel sharedPreferencesTool;
    //判断需要
    private String TAG = "",item_code="",name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reply;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        sharedPreferencesTool = new SharedPreferences_wheel(ReplyActivity.this);

        init();
        initData();
        mSpinerPopWindow = new SpinerPopWindow<String>(ReplyActivity.this, list,itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);
    }

    private void init() {
        type=getIntent().getStringExtra("Type");
        if(type.equals("内部催单")){
            BillNo=getIntent().getStringExtra("InnerUrgeBillNo");
        }else{
            BillNo=getIntent().getStringExtra("OuterUrgeBillNo");
        }

        deliverQty=getIntent().getStringExtra("DeliverQty");
        deliverDate=getIntent().getStringExtra("DeliverDate");
        step=getIntent().getIntExtra("Step",0);
        tetle_back = (ImageView)findViewById(R.id.tetle_back);//返回
        tetle_back.setOnClickListener(this);
        tetle_text = (TextView) findViewById(R.id.tetle_text);//title
        tetle_text.setText("任务回复");

        reply_ok = (TextView) findViewById(R.id.reply_ok);//确认回复按钮
        reply_number = (EditText) findViewById(R.id.reply_number);//输入货物数量
        reply_time = (TextView) findViewById(R.id.reply_time);//时间
        reply_type=(TextView) findViewById(R.id.reply_type);//类型
        reply_content = (EditText) findViewById(R.id.reply_content);//回复内容
        setData();
        setOnclick();
    }
    private void setData() {
        reply_number.setText(deliverQty);
        reply_time.setText(deliverDate);
    }

    private void setOnclick() {
        tetle_back.setOnClickListener(this);
        reply_ok.setOnClickListener(this);
        reply_type.setOnClickListener(this);
        reply_time.setOnClickListener(this);

    }
    /**
     * spiner初始化假数据
     */
    private void initData() {
        list = new ArrayList<String>();
        list.add("人");
        list.add("机");
        list.add("料");
        list.add("法");
        list.add("环");
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;

            case R.id.reply_time:
                //采购页面选择供应商跳转
                sharedPreferencesTool.save_Key("");
                TAG = "consultanstroke2_stop_time";
                menuWindow = new Wheelview_SelectPicPopupWindow(ReplyActivity.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(ReplyActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置


                break;

            case R.id.reply_ok:
                if (type.equals("内部催单")){
                    addInner();
                }else{
                    addOuter();
                }

                finish();
                break;
            case R.id.reply_type:
                mSpinerPopWindow.setWidth(reply_type.getWidth());
                mSpinerPopWindow.showAsDropDown(reply_type);
                setTextImage(R.mipmap.icon_up);
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
                    if (TAG.equals("consultanstroke2_stop_time")){
                        String value = sharedPreferencesTool.read_Key();
                        Log.i("Text","777777777777777777777777"+value);
                        if (value!=null&&value.length()>1||!value.equals("")&&value.length()>1){
                            reply_time.setText(value);
                        }else{
                            reply_time.setText(value);
                        }
                    }


                    break;
                default:
                    break;
            }
        }
    };

    public void addInner(){
        //Map<String, String> map = new HashMap<String, String>();
        JSONObject result=new JSONObject();
        JSONObject list=new JSONObject();
        JSONObject data=new JSONObject();
        JSONArray deleted=new JSONArray();
        JSONArray inserted=new JSONArray();
        JSONArray updated=new JSONArray();
        try {
            data.put("HandleId", UUID.randomUUID().toString());
            data.put("InnerUrgeBillNo",BillNo);
            data.put("Step", step+5);
            data.put("DeliverQty",reply_number.getText().toString());
            data.put("DeliverDate", reply_time.getText().toString());
            data.put("Type", reply_type.getText().toString());
            data.put("Memo", reply_content.getText().toString());
            data.put("HandleDate", DateUtil.getInstance().getDateOfToDay());

            inserted.put(0, data);
            list.put("deleted", deleted);
            list.put("inserted", inserted);
            list.put("updated", updated);
            list.put("_changed", true);
            result.put("list", list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PostAsyncTask postAsyncTask=new PostAsyncTask(ReplyActivity.this,result,2);
        String postUrl= UrlUtil.GetPostUrl(UrlUtil.IP,UrlUtil.InnerUrgeOrderHandle);
        postAsyncTask.execute(postUrl);
        Log.e("回复Url",postUrl);
        Log.e("回复result",result.toString());


    }
    public void addOuter(){
        //Map<String, String> map = new HashMap<String, String>();
        JSONObject result=new JSONObject();
        JSONObject list=new JSONObject();
        JSONObject data=new JSONObject();
        JSONArray deleted=new JSONArray();
        JSONArray inserted=new JSONArray();
        JSONArray updated=new JSONArray();
        try {
            data.put("HandleId", UUID.randomUUID().toString());
            data.put("OuterUrgeBillNo",BillNo);
            data.put("Step", step+5);
            data.put("DeliverQty",reply_number.getText().toString());
            data.put("DeliverDate", reply_time.getText().toString());
            data.put("Type", reply_type.getText().toString());
            data.put("Memo", reply_content.getText().toString());
            data.put("HandleDate", DateUtil.getInstance().getDateOfToDay());

            inserted.put(0, data);
            list.put("deleted", deleted);
            list.put("inserted", inserted);
            list.put("updated", updated);
            list.put("_changed", true);
            result.put("list", list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PostAsyncTask postAsyncTask=new PostAsyncTask(ReplyActivity.this,result,2);
        String postUrl= UrlUtil.GetPostUrl(UrlUtil.IP,UrlUtil.OuterUrgeOrderHandle);
        postAsyncTask.execute(postUrl);
        Log.e("回复Url",postUrl);
        Log.e("回复result",result.toString());


    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            mSpinerPopWindow.dismiss();
                reply_type.setText(list.get(position));

        }
    };

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener=new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {

                setTextImage(R.mipmap.icon_down);

        }
    };
    /**
     * 给TextView右边设置图片
     * @param resId
     */
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        reply_type.setCompoundDrawables(null, null, drawable, null);
    }



}
