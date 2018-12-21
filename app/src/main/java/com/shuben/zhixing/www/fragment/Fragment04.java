package com.shuben.zhixing.www.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.volley.VolleyError;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.UrlUtil;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.shuben.contact.lib.ConstantActivity;
import com.shuben.zhixing.module.mass.ScanMassActivity;
import com.base.zhixing.www.BaseFragment;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.activity.SettingComActivity;
import com.shuben.zhixing.www.common.ARouterContants;
import com.shuben.zhixing.www.common.ImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.widget.CharAvatarView;
import com.base.zhixing.www.widget.CircularImage;
import com.shuben.zhixing.www.data.UserData;


import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/21.智行力设置
 */

public class Fragment04 extends BaseFragment {
    private View view_layout;
    private Context context;
    private ImageView tetle_back;
    private TextView tetle_text, me_user_name_tv;
    private LinearLayout tongxunlu, richeng;
    private CharAvatarView txt_head;
    private CircularImage image_head;
    private RelativeLayout setting, sao_layout;
    private String ip;
    private ACache aCache;
    private TextView mTvDepartName;
    private TextView mTvPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment04, container, false);
        context = getActivity();
        return view_layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    //id初始化
    private void init() {
        ip = SharedPreferencesTool.getMStool(getActivity()).getIp();
        aCache=ACache.get(getActivity());
        sao_layout = view_layout.findViewById(R.id.sao_layout);
        txt_head = view_layout.findViewById(R.id.txt_head);
        image_head = view_layout.findViewById(R.id.image_head);
        setStatus(-1);

        tetle_back = (ImageView) view_layout.findViewById(R.id.tetle_back);
        tetle_back.setVisibility(View.GONE);
        mTvDepartName=(TextView) view_layout.findViewById(R.id.tv_my_depart_name);
        mTvPhone=(TextView) view_layout.findViewById(R.id.tv_my_phone);

        tetle_text = (TextView) view_layout.findViewById(R.id.tetle_text);
        tetle_text.setText("设置");

        me_user_name_tv = (TextView) view_layout.findViewById(R.id.me_user_name_tv);//姓名
        richeng = view_layout.findViewById(R.id.richeng);
        tongxunlu = view_layout.findViewById(R.id.tongxunlu);
        tongxunlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personIntent = new Intent();
                personIntent.setClass(getActivity(), ConstantActivity.class);
                startActivity(personIntent);
            }
        });
        richeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(ARouterContants.ScheduleActivity).navigation();
            }
        });
        setting = view_layout.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent personIntent = new Intent();
                personIntent.setClass(getActivity(), SettingComActivity.class);
                startActivity(personIntent);
            }
        });
        sao_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScanMassActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1000);
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }


    /**
     * @author zjq
     * create at 2018/12/20 下午4:33 网络请求
     */
    private void load() {
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetUserInfo");
        params.put("UserId", SharedPreferencesTool.getMStool(getActivity()).getUserId());

        httpPostVolley(ip + UrlUtil.GetUseInfo, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                String json = jsonObject.toString();
                //保存 json
                aCache.put("App_MyInfo",json);
                UserData bean = GsonUtil.getGson().fromJson(json, UserData.class);
                mTvDepartName.setText(bean.getDeptName());
                mTvPhone.setText(bean.getPhoneNumber());
            }

            @Override
            public void error(VolleyError error) {



            }
        }, false);


    }

    @Override
    public void onResume() {
        super.onResume();
        load(); //网络数据刷新
        String name = SharedPreferencesTool.getMStool(getActivity()).getUserName();
        me_user_name_tv.setText(name);
        String path = SharedPreferencesTool.getMStool(getActivity()).getString("head_ico");//头像
        if (path.length() == 0) {
            image_head.setVisibility(View.GONE);
            txt_head.setVisibility(View.VISIBLE);
            txt_head.setText(name);
        } else {
            image_head.setVisibility(View.VISIBLE);
            txt_head.setVisibility(View.GONE);
            ImageLoader.load(path, image_head, R.mipmap.person_icon);
        }
    }


    @Override
    public void process(Message msg) {

    }
}
