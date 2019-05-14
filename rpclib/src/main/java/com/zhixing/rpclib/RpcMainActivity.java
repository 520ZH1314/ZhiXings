package com.zhixing.rpclib;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.common.T;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.selector.DevSelector;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.common.Common;
import butterknife.BindView;

@Route(path = "/rpclib/RpcMainActivity")
public class RpcMainActivity extends BaseRpcActivity {

    @BindView(R2.id.rpc_enter)
    TextView rpc_enter;
    @BindView(R2.id.sc_enter)
    TextView sc_enter;
    @BindView(R2.id.line_sel)
    TextView line_sel;
    @BindView(R2.id.hour_enter)
    TextView hour_enter;
@BindView(R2.id.pinzhi_enter)
TextView pinzhi_enter;


    @BindView(R2.id.work_enter)
    TextView work_enter;
    @Override
    public int getLayoutId() {
        return R.layout.rpc_main_layout;
    }

    @Override
    public void process(Message msg) {

    }

    private SharedUtils sharedUtils, parentShared;
    @Override
    public void newIniLayout() {
        setStatus(-1);
        sharedUtils = new SharedUtils(Common.SHARED_);
        parentShared = new SharedUtils(T.SET_F);

      Drawable pressedDrawable = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.txt_bor)
                .radius(7)
                .build();
        Drawable normalDrawable = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.btn_org)
                .radius(7)
                .build();

        DevShapeUtils
                .selector(DevSelector.STATE_PRESSED,pressedDrawable,normalDrawable)
                .selectorTextColor("#007AFF", "#ffffff")
                .into(rpc_enter);  
            rpc_enter.setOnClickListener((v)->{
                Intent intent = new Intent(RpcMainActivity.this, RpcEditInfoActivity.class);
                startActivity(intent);
            });

        Drawable pressedsc = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.title_bg)
                .radius(7)
                .build();
        Drawable normalsc = DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.title_bg)
                .radius(7)
                .build();

        DevShapeUtils
                .selector(DevSelector.STATE_PRESSED,normalsc,normalsc)
                .selectorTextColor("#575757", "#ffffff")
                .into(sc_enter);
        sc_enter.setOnClickListener((v)->{
            Intent intent = new Intent(RpcMainActivity.this, ScViewActivity.class);
            startActivity(intent);
        });

        line_sel.setOnClickListener((v)->{
            selectWork();
        });
        if(sharedUtils.getStringValue("workshop_id").length()==0){
            line_sel.performClick();
        }
        DevShapeUtils
                .selector(DevSelector.STATE_PRESSED,normalsc,normalsc)
                .selectorTextColor("#575757", "#ffffff")
                .into(hour_enter);
        hour_enter.setOnClickListener((v)->{
            Intent intent = new Intent(RpcMainActivity.this, HourViewActivity.class);
            startActivity(intent);
        });
        DevShapeUtils
                .selector(DevSelector.STATE_PRESSED,normalsc,normalsc)
                .selectorTextColor("#575757", "#ffffff")
                .into(work_enter);
        DevShapeUtils
                .selector(DevSelector.STATE_PRESSED,normalsc,normalsc)
                .selectorTextColor("#575757", "#ffffff")
                .into(pinzhi_enter);
        work_enter.setOnClickListener((v)->{
            Intent intent = new Intent(RpcMainActivity.this, WorkViewActivity.class);
            startActivity(intent);
        });
        pinzhi_enter.setOnClickListener((n)->{
            Intent intent = new Intent(RpcMainActivity.this, RpcPzViewActivity.class);
            startActivity(intent);
        });
    }

    private void selectWork(){
        if (parentShared.getStringValue("factory_id").length() == 0) {
            Toasty.INSTANCE.showToast(RpcMainActivity.this, "请先在基本设置中选择工厂");
            return;
        }
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(RpcMainActivity.this, getHandler(), "车间");
        setSelectPop.getSet().put("ApiCode", "GetWorkShopList");
        setSelectPop.getSet().put("FactoryId", parentShared.getStringValue("factory_id"));
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                sharedUtils.setStringValue("workshop_id", id);
                sharedUtils.setStringValue("workshop_code", code);
                sharedUtils.setStringValue("workshop_name", name);
                selectLine();
            }


        });
        setSelectPop.showSheet();
    }
    private void selectLine(){
        if (sharedUtils.getStringValue("workshop_id").length() == 0) {
            Toasty.INSTANCE.showToast(RpcMainActivity.this, "请先选择车间");
            return;
        }
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(RpcMainActivity.this, getHandler(), "产线");
        setSelectPop.getSet().put("ApiCode", "GetLineList");
        setSelectPop.getSet().put("WorkShopId", sharedUtils.getStringValue("workshop_id"));
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                sharedUtils.setStringValue("line_id", id);
                sharedUtils.setStringValue("line_name", name);
                showLine();
            }


        });
        setSelectPop.showSheet();
    }
    private void showLine(){
        line_sel.setText(sharedUtils.getStringValue("line_name"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLine();
    }
}
