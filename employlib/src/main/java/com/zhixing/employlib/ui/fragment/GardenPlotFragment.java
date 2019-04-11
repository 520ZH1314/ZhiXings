package com.zhixing.employlib.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseFragment;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.MyAdapter;
import com.zhixing.employlib.ui.activity.OutOfAdActivity;
import com.zhixing.employlib.ui.activity.UpdateTeamDataActivity;
import com.zhixing.employlib.view.TypeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author zjq
 * 园地的flame
 * create at 2019/3/14 下午2:07
 */
public class GardenPlotFragment extends BaseLazyFragment {

    @BindView(R2.id.tab_garden_plot)
    TabLayout tabGardenPlot;
    @BindView(R2.id.viewpager_garden_plot)
    ViewPager viewpagerGardenPlot;
    @BindView(R2.id.tetle_back)
    ImageView tetleBack;
    @BindView(R2.id.tetle_text)
    TextView tetleText;
    @BindView(R2.id.tetle_tv_img)
    ImageView tetleTvImg;
    private Unbinder unbinder;
    private List<BaseFragment> list;
    private String[] titles = {"优秀员工", "新员工", "班组风采"};
    private MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_garden_plot, container, false);
        unbinder = ButterKnife.bind(this, view);

        tetleTvImg.setVisibility(View.VISIBLE);
        tetleText.setVisibility(View.VISIBLE);
        tetleTvImg.setImageResource(R.mipmap.add_more);
        tetleBack.setVisibility(View.GONE);
        tetleText.setText("园地");

        return view;

    }

    private void initFragment() {

        list = new ArrayList<>();
        list.add(new ExcellentEmployeeFragment());
        list.add(new NewEmployeeFragment());
        list.add(new BetterTeamEmployeeFragment());
        adapter = new MyAdapter(getChildFragmentManager(), list, titles);
        viewpagerGardenPlot.setAdapter(adapter);
        //将TabLayout和ViewPager绑定在一起，一个动另一个也会跟着动
        tabGardenPlot.setupWithViewPager(viewpagerGardenPlot);
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    public void initData() {
        initFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R2.id.tetle_back, R2.id.tetle_tv_img})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tetle_back) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tetle_tv_img) {


            TypeDialog typeDialog =new TypeDialog();
            typeDialog.setOnDialogInforCompleted(new TypeDialog.OnDialogInforCompleted() {
                @Override
                public void dialogInforCompleted(String name) {
                    if ("1".equals(name)){
                       //发布园地
                        Intent intent =new Intent(getActivity(),UpdateTeamDataActivity.class);
                        startActivity(intent);

                      }else{

                        Intent intent =new Intent(getActivity(),OutOfAdActivity.class);
                        startActivity(intent);
                        //发布公告
                    }


                }
            });
            typeDialog.show(getChildFragmentManager(),"");
        }
    }
}
