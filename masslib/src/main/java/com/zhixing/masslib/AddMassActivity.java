package com.zhixing.masslib;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.XEditText;

/**
 * 添加质量管理
 */
public class AddMassActivity extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.add_first_mass_layout;
    }

    @Override
    public void process(Message msg) {

    }
    private XEditText edit;
    private TextView tetle_text,sure;
    private ImageView scan_view;
    @Override
    public void initLayout() {
        setStatus(-1);
        edit = findViewById(R.id.edit);
        tetle_text = findViewById(R.id.tetle_text);
        tetle_text.setText("添加首件检验");
        scan_view = findViewById(R.id.scan_view);
        scan_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMassActivity.this,ScanMassActivity.class);
                intent.putExtra("type",0);
                startActivityForResult(intent,1000);
            }
        });
        sure = findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.getTextEx().length()==0){
                    Toasty.INSTANCE.showToast(AddMassActivity.this,"请输入工单号");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("ret",edit.getTextEx().toString());
                setResult(1000,intent);
                AppManager.getAppManager().finishActivity();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000&&resultCode==1000){
            if(data.hasExtra("ret")) {
                edit.setText(data.getStringExtra("ret"));
            }
        }
    }
}
