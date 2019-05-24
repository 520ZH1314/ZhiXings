package com.zhixing.rpclib;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.XEditText;
import com.zhixing.beans.NoItem;
import com.zhixing.common.Common;
import com.zhixing.view.CommonNoSelectPop;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;

public class ScViewSelectActivity extends BaseRpcActivity {
    @BindView(R2.id.item0)
    TextView item0;
    @BindView(R2.id.item1)
    XEditText item1;
    @BindView(R2.id.item2)
    XEditText item2;
    @BindView(R2.id.tetle_tv_ok)
    TextView tetle_tv_ok;
    @BindView(R2.id.tetle_text)
    TextView tetle_text;

    private SharedUtils sharedUtils;
    @Override
    public void newIniLayout() {
        sharedUtils = new SharedUtils(Common.SHARED_);
        tetle_tv_ok.setVisibility(View.VISIBLE);
        tetle_text.setText("工单切换");
        item0.setOnClickListener((v)->{
            loadNos();
        });
        tetle_tv_ok.setOnClickListener((v)->{
            Intent intent= new Intent();
            intent.putExtra("no",item0.getText().toString());
            intent.putExtra("uph",item1.getText().toString());
            intent.putExtra("num",item2.getText().toString());
            setResult(1000,intent);
            AppManager.getAppManager().finishActivity();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.rpc_sc_view_sel;
    }

    @Override
    public void process(Message msg) {
            switch (msg.what){
                case 1:
                    CommonNoSelectPop selectPop = new CommonNoSelectPop(ScViewSelectActivity.this, getHandler(), "请选择工单");
                    selectPop.setData(noItems);
                    selectPop.setSelect(new SetSelect() {
                        @Override
                        public void select(String id, String code, String name) {

                            item0.setText(name);

                        }
                    });
                    selectPop.showSheet();
                    break;
            }
    }
    private ArrayList<NoItem> noItems = new ArrayList<>();

    private void loadNos() {

        Map<String, String> params = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetRPCOrderNoData");
        params.put("UserId", SharedPreferencesTool.getMStool(ScViewSelectActivity.this).getUserId());
        params.put("TenantId", SharedPreferencesTool.getMStool(ScViewSelectActivity.this).getTenantId());
        params.put("LineId", sharedUtils.getStringValue("line_id"));
        httpPostVolley(SharedPreferencesTool.getMStool(ScViewSelectActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("status").equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        int len = jsonArray.length();
                        noItems.clear();                                   
                        for (int i = 0; i < len; i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            NoItem noItem = new NoItem();
                            noItem.setNo(object.getString("OrderNo"));
                            noItem.setName(object.getString("ProductName"));
                            noItem.setStatus(object.getInt("State"));
                            noItem.setID(object.getString("ID"));
                            noItem.setNum(object.getString("BatchPlanCount"));
                            noItem.setKehu(object.getString("BatchCustomer"));
                            noItem.setBatchNo(object.getString("BatchNo"));
                            noItem.setTime(TimeUtil.getTime(TimeUtil.parseTimeC(object.getString("BatchCreateDate"))));
                            noItem.setBatchWorkNo(object.getString("BatchWorkNo"));
                            noItems.add(noItem);
                        }
                        getHandler().sendEmptyMessage(1);
                        dismissDialog();
                    }else{
                        Toasty.INSTANCE.showToast(ScViewSelectActivity.this,jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        }, "获取工单列表");

    }

   
}
