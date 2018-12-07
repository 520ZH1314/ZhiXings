package com.shuben.zhixing.www.reminder.r_fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.reminder.CaiGouAnalysisTwoActivity;
import com.shuben.zhixing.www.reminder.CuiDanAnalysisTwoActivity;

/**
 * Created by Administrator on 2017/8/21.
 */

public class StatisticsFragment extends Fragment implements View.OnClickListener{

    private View view_layout;
    private Context context;
    private ImageView tetle_back;
    private TextView tetle_text,neibu_biao,caigou_biao;
    private int type=-1;
    public StatisticsFragment(int type){
        this.type=type;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view_layout = inflater.inflate(R.layout.fragment_statistics,container,false);
        context = getActivity();

        init();
        return view_layout;
    }

    //id初始化
    private void init() {
        RelativeLayout lay_neibu=(RelativeLayout)view_layout.findViewById(R.id.lay_neibu);
        RelativeLayout lay_caigou=(RelativeLayout)view_layout.findViewById(R.id.lay_caigou);
        if(type==1){
            lay_neibu.setVisibility(View.VISIBLE);
            lay_caigou.setVisibility(View.GONE);
        }else if(type==2){
            lay_neibu.setVisibility(View.GONE);
            lay_caigou.setVisibility(View.VISIBLE);
        }

        tetle_back = (ImageView)view_layout.findViewById(R.id.tetle_back);

        neibu_biao = (TextView) view_layout.findViewById(R.id.neibu_biao);//内部
        caigou_biao = (TextView) view_layout.findViewById(R.id.caigou_biao);//采购
        tetle_text = (TextView) view_layout.findViewById(R.id.tetle_text);
        tetle_text.setText("统计分析");
        setOnclick();
    }

    private void setOnclick() {
        tetle_back.setOnClickListener(this);
        neibu_biao.setOnClickListener(this);
        caigou_biao.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tetle_back://紧急催单
                getActivity().finish();
                break;
            case R.id.neibu_biao://紧急催单
                intent = new Intent(getActivity(), CuiDanAnalysisTwoActivity.class);
                startActivity(intent);

                break;


            case R.id.caigou_biao://紧急催单
                intent = new Intent(getActivity(), CaiGouAnalysisTwoActivity.class);
                startActivity(intent);
                break;


        }
    }
}
