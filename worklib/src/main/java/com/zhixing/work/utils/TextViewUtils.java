package com.zhixing.work.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.base.zhixing.www.view.Toasty;

/**
 *
 *@author zjq
 *create at 2018/12/19 上午9:39 判断textview 是否为空
 */

public class TextViewUtils {

    public static boolean isEmpty( Context context,TextView textView,String message){
         Boolean isEmpty=false;
        String s = textView.getText().toString();
        if (TextUtils.isEmpty(s)||s.equals("请选择")){
            isEmpty=false;
            Toasty.INSTANCE.showToast(context,message);

        }else{
            isEmpty=true;

        }
        return isEmpty;

    }

}
