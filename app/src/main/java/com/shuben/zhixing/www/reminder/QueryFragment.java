package com.shuben.zhixing.www.reminder;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuben.zhixing.www.R;

public class QueryFragment extends Fragment implements View.OnClickListener {
    private View view_layout;
    private Context context;
    private ImageView tetle_back;
    private TextView tetle_text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view_layout = inflater.inflate(R.layout.fragment_mytask,container,false);
        context = getActivity();

        init();
        return view_layout;
    }

    private void init() {
        tetle_back = (ImageView)view_layout.findViewById(R.id.tetle_back);

        tetle_text = (TextView) view_layout.findViewById(R.id.tetle_text);
        tetle_text.setText("历史查询");
        setOnclick();
    }

    private void setOnclick() {
        tetle_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tetle_back://紧急催单
                getActivity().finish();
                break;
        }
    }
}
