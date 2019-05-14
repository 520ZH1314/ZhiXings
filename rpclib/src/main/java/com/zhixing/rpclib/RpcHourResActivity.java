package com.zhixing.rpclib;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.widget.XEditText;

import butterknife.BindView;

public class RpcHourResActivity extends BaseRpcActivity {
    @BindView(R2.id.tetle_text)
    TextView tetle_text;
    @BindView(R2.id.item15)
    XEditText item15;
    @BindView(R2.id.item16)
    XEditText item16;
    @BindView(R2.id.tetle_tv_ok)
    TextView tetle_tv_ok;
    @Override
    public void newIniLayout() {
        setStatus(-1);
        tetle_text.setText("问题录入");
        tetle_tv_ok.setVisibility(View.VISIBLE);
        tetle_tv_ok.setText("确定");
        Intent itent = getIntent();
        if(itent.getStringExtra("v1").length()!=0){
            item15.setTextEx(itent.getStringExtra("v1"));
        }
        if(itent.getStringExtra("v2").length()!=0){
            item16.setTextEx(itent.getStringExtra("v2"));
        }
        tetle_tv_ok.setOnClickListener((n)->{
            Intent intent = new Intent();
            intent.putExtra("i0",item15.getTextEx().intern());
            intent.putExtra("i1",item16.getTextEx().intern());
            intent.putExtra("id",getIntent().getStringExtra("id"));
            intent.putExtra("index",getIntent().getIntExtra("index",-1));
            setResult(1000,intent);
            AppManager.getAppManager().finishActivity();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.hour_view_res;
    }

    @Override
    public void process(Message msg) {

    }
}
