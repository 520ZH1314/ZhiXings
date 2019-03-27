package com.zhixing.employlib.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.PersonTestAdapt;
import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.viewmodel.fragment.PerFormanceViewModel;

import java.util.List;

public class DialogFragmentPersonTest extends DialogFragment implements View.OnClickListener {

    private ImageView iv_close;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        PerFormanceViewModel integralEventViewModel = ViewModelProviders.of(getActivity()).get(PerFormanceViewModel.class);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_person_test, null);
        recyclerView=(RecyclerView) view.findViewById(R.id.rec_person_test);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iv_close = (ImageView) view.findViewById(R.id.iv_dialog_fragment_perison_test);
        iv_close.setOnClickListener(this);

         integralEventViewModel.getPersonTestEntitysData();
        integralEventViewModel.testEnt.observe(getActivity(), new Observer<List<PersonTestEntity>>() {
            @Override
            public void onChanged(@Nullable List<PersonTestEntity> personTestEntities) {
                recyclerView.setAdapter(new PersonTestAdapt(R.layout.item_person_test,personTestEntities));
            }
        });
        return view;


    }




    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.2f;

        window.setAttributes(windowParams);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_dialog_fragment_perison_test) {
            dismiss();
        }
    }
}
