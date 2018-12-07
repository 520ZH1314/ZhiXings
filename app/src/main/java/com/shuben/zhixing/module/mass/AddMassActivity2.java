package com.shuben.zhixing.module.mass;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.view.Toasty;
import com.shuben.zhixing.www.widget.XEditText;

/**
 * 添加质量管理
 */
public class AddMassActivity2 extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.add_2_mass_layout;
    }

    @Override
    public void process(Message msg) {

    }
    private XEditText edit,edit2,edit1;
    private TextView tetle_text,sure;
    private ImageView scan_view;
    @Override
    public void initLayout() {
        setStatus(-1);
        edit = findViewById(R.id.edit);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        tetle_text = findViewById(R.id.tetle_text);
        tetle_text.setText("添加抽检检验项");
        scan_view = findViewById(R.id.scan_view);
        scan_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMassActivity2.this,ScanMassActivity.class);
                intent.putExtra("type",0);
                startActivityForResult(intent,1000);
            }
        });
        sure = findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(edit.getTextEx().length()==0){
                        Toasty.INSTANCE.showToast(AddMassActivity2.this,"请输入工单号");
                        return;
                    }
                    //添加抽检信息
                Intent intent = new Intent();
                intent.putExtra("ret",edit.getTextEx().toString());
                intent.putExtra("pNo",edit1.getTextEx().toString());
                intent.putExtra("pNum",edit2.getTextEx().toString());
                setResult(1000,intent);
                AppManager.getAppManager().finishActivity();
//                Toasty.INSTANCE.showToast(AddMassActivity2.this,"添加操作");
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000&&resultCode==1000){
            if(data.hasExtra("ret"))
                edit.setText(data.getStringExtra("ret"));
        }
    }
}
