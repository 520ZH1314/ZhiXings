package com.zhixing.masslib.inter;

import android.view.View;

import com.zhixing.masslib.bean.BaseExpandableBean;

/**
* @author cloor
* @time   2018-12-26 10:25
* @describe  :
*/

//对调接口
public interface OnExpandableClickListener {
    void onExpandableClick(View clickView, BaseExpandableBean selecBean);
}
