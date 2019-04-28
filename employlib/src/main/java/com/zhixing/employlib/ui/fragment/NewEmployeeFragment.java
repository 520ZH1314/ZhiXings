package com.zhixing.employlib.ui.fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.base.zhixing.www.BaseFragment;
import com.example.stateviewlibrary.StateView;
import com.zhixing.employlib.R;
import com.zhixing.employlib.adapter.NewEmployeeAdapt;
import com.zhixing.employlib.model.NewEmployeeEntity;
import com.zhixing.employlib.model.eventbus.UpdateEmployeeEvent;
import com.zhixing.employlib.model.gardenplot.NewEmployeeBean;
import com.zhixing.employlib.viewmodel.fragment.TeamViewModel;
import com.zhixing.netlib.base.BaseResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjq
 * create at 2019/3/14 下午2:05
 * 新员工
 */
public class NewEmployeeFragment extends BaseLazyFragment {
    private RecyclerView recyclerView;
    private TeamViewModel teamViewModel;
    private String imgPath;
    private String NativePlace;
    private String NewDeeds;
    private String PositionName;
    private String OrganizeName;
    private String JoinWorkDate;
    private LinearLayout linearLayout;
    private StateView mStateView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_employee, container, false);
         linearLayout =view.findViewById(R.id.ll_recy_new_employee);
         mStateView=StateView.inject(linearLayout);
        teamViewModel = ViewModelProviders.of(getActivity()).get(TeamViewModel.class);
        EventBus.getDefault().register(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_new_employee);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private void initDatas() {
        mStateView.showLoading();
        teamViewModel.getRefrshNewEmployeeData(true);
        teamViewModel.newEmployee.observe(getActivity(), new Observer<BaseResponse<NewEmployeeBean>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<NewEmployeeBean> newEmployeeBeanBaseResponse) {
                if (newEmployeeBeanBaseResponse.getRows()!=null){
                    if (newEmployeeBeanBaseResponse.getRows().size()!=0){
                        mStateView.showContent();
                        List<NewEmployeeBean> newEmployeeBeans = newEmployeeBeanBaseResponse.getRows();

                        List<NewEmployeeEntity> datas = new ArrayList<>();
                        for (int i = 0; i < newEmployeeBeans.size(); i++) {
                            if (newEmployeeBeans.get(i).getFiles().size() == 0) {
                                imgPath = "";
                            } else {
                                imgPath = newEmployeeBeans.get(i).getFiles().get(0).getFilePath();
                            }
                            if (newEmployeeBeans.get(i).getUserInfo() != null) {
                                if (newEmployeeBeans.get(i).getUserInfo().getNativePlace()!=null){
                                    NativePlace = newEmployeeBeans.get(i).getUserInfo().getNativePlace();
                                }else{
                                    NativePlace = "";
                                }
                                if (newEmployeeBeans.get(i).getUserInfo().getPositionName()!=null){
                                    PositionName = newEmployeeBeans.get(i).getUserInfo().getPositionName();
                                }else{
                                    PositionName = "";
                                }
                                if (newEmployeeBeans.get(i).getUserInfo().getOrganizeName()!=null){
                                    OrganizeName = newEmployeeBeans.get(i).getUserInfo().getOrganizeName();
                                }else{
                                    OrganizeName = "";
                                }
                                if (newEmployeeBeans.get(i).getUserInfo().getJoinWorkDate()!=null){
                                    JoinWorkDate = newEmployeeBeans.get(i).getUserInfo().getJoinWorkDate();
                                }else{
                                    JoinWorkDate = "";
                                }


                            }else{
                                NativePlace = "";
                                PositionName = "";
                                OrganizeName = "";
                                JoinWorkDate = "";
                            }



                            datas.add(new NewEmployeeEntity(NativePlace, imgPath, newEmployeeBeans.get(i).getUserName(),
                                    PositionName,
                                    OrganizeName, clearTime(JoinWorkDate), newEmployeeBeans.get(i).getNewDeeds()));
                        }
                        NewEmployeeAdapt newEmployeeAdapt = new NewEmployeeAdapt(R.layout.item_new_employee, datas);
                        recyclerView.setAdapter(newEmployeeAdapt);
                    }else{
                        mStateView.showEmpty();
                    }
                }else if ("404".equals(newEmployeeBeanBaseResponse.getStatus())){
                    mStateView.showRetry();
                    mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                        @Override
                        public void onRetryClick() {
                            teamViewModel.getRefrshNewEmployeeData(true);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initData() {
        initDatas();
    }

    public String clearTime(String date) {

        if (!TextUtils.isEmpty(date)){
            String[] ts = date.split("T");
            String t = ts[0];
            String[] split = t.split("-");
            return split[1] + "月" + split[2] + "日";

        }else{
            return  "";
        }
//        2007-11-14T00:00:00

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Update(UpdateEmployeeEvent employeeEvent) {
        if ("4".equals(employeeEvent.EmPloyeeType)) {
            if (mStateView==null){
                mStateView=StateView.inject(linearLayout);
            }
            mStateView.showLoading();
            teamViewModel.getRefrshNewEmployeeData(true);
            teamViewModel.newEmployee.observe(getActivity(), new Observer<BaseResponse<NewEmployeeBean>>() {
                @Override
                public void onChanged(@Nullable BaseResponse<NewEmployeeBean> newEmployeeBeanBaseResponse) {
                    if (newEmployeeBeanBaseResponse.getRows()!=null){
                        if (newEmployeeBeanBaseResponse.getRows().size()!=0){
                            mStateView.showContent();
                            List<NewEmployeeBean> newEmployeeBeans = newEmployeeBeanBaseResponse.getRows();

                            List<NewEmployeeEntity> datas = new ArrayList<>();
                            for (int i = 0; i < newEmployeeBeans.size(); i++) {
                                if (newEmployeeBeans.get(i).getFiles().size() == 0) {
                                    imgPath = "";
                                } else {
                                    imgPath = newEmployeeBeans.get(i).getFiles().get(0).getFilePath();
                                }
                                if (newEmployeeBeans.get(i).getUserInfo() != null) {
                                    if (newEmployeeBeans.get(i).getUserInfo().getNativePlace()!=null){
                                        NativePlace = newEmployeeBeans.get(i).getUserInfo().getNativePlace();
                                    }else{
                                        NativePlace = "";
                                    }
                                    if (newEmployeeBeans.get(i).getUserInfo().getPositionName()!=null){
                                        PositionName = newEmployeeBeans.get(i).getUserInfo().getPositionName();
                                    }else{
                                        PositionName = "";
                                    }
                                    if (newEmployeeBeans.get(i).getUserInfo().getOrganizeName()!=null){
                                        OrganizeName = newEmployeeBeans.get(i).getUserInfo().getOrganizeName();
                                    }else{
                                        OrganizeName = "";
                                    }
                                    if (newEmployeeBeans.get(i).getUserInfo().getJoinWorkDate()!=null){
                                        JoinWorkDate = newEmployeeBeans.get(i).getUserInfo().getJoinWorkDate();
                                    }else{
                                        JoinWorkDate = "";
                                    }


                                }else{
                                    NativePlace = "";
                                    PositionName = "";
                                    OrganizeName = "";
                                    JoinWorkDate = "";
                                }



                                datas.add(new NewEmployeeEntity(NativePlace, imgPath, newEmployeeBeans.get(i).getUserName(),
                                        PositionName,
                                        OrganizeName, clearTime(JoinWorkDate), newEmployeeBeans.get(i).getNewDeeds()));
                            }
                            NewEmployeeAdapt newEmployeeAdapt = new NewEmployeeAdapt(R.layout.item_new_employee, datas);
                            recyclerView.setAdapter(newEmployeeAdapt);
                        }else{
                            mStateView.showEmpty();
                        }
                    }else if ("404".equals(newEmployeeBeanBaseResponse.getStatus())){
                        mStateView.showRetry();
                        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
                            @Override
                            public void onRetryClick() {
                                teamViewModel.getRefrshNewEmployeeData(true);
                            }
                        });
                    }
                }
            });


        }


    }


}
