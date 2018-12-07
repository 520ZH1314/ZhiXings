package com.shuben.zhixing.www.patrol.billboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuben.zhixing.www.R;

/**
 * Created by Geyan on 2018/3/22.
 */

public class FragmentBill01 extends Fragment {
    private View view_layout;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_bill01,container,false);
        context = getActivity();
        return view_layout;
    }
}
