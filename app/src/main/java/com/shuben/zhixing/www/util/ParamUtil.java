package com.shuben.zhixing.www.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.shuben.zhixing.www.inspection.bean.ParamInfo;

import java.util.regex.Pattern;

/**
 * Created by Geyan on 2017/12/14.
 */

public class ParamUtil {
    public static ParamUtil paramUtil;
    private Context context;
    private String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？^a-zA-Z]";
    public ParamUtil(Context context) {
       this.context= context;
    }
    public static ParamUtil getInstance(Context context) {
        if (paramUtil == null ) {
            paramUtil = new ParamUtil(context);
        }
        return paramUtil;
    }
    private boolean isDouble(String str){
        if(null==str||"".equals(str)){
            return false;
        }
        Pattern pattern=Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
    public boolean paramCheck(String mValue, ParamInfo paramInfo){
        boolean res=false;
        if(paramInfo.getCategoryName().equals("标准值型")){
            Log.e("类型：","标准值型");
            if(!isDouble(mValue)){
                Toast.makeText(context,"输入检验值为空或者非法",Toast.LENGTH_SHORT).show();
                res=false;
            }else{
                if(Double.parseDouble(mValue)==Double.parseDouble(paramInfo.getStandValue())){
                    res=true;
                }else{
                    res=false;
                }
            }


        }else if(paramInfo.getCategoryName().equals("区间内OK型")){
            Log.e("类型：","区间内OK型");
            Log.e("填入值：",""+mValue);
            Log.e("最大值：",""+paramInfo.getStandMaxValue());
            Log.e("最小值：",""+paramInfo.getStandMinValue());
            Log.e("间隔：","——————————————————");

            if(!isDouble(mValue)){
                Toast.makeText(context,"输入检验值为空或者非法",Toast.LENGTH_SHORT).show();
                res=false;
            }else{
                if(Double.parseDouble(mValue)<Double.parseDouble(paramInfo.getStandMaxValue())&&Double.parseDouble(mValue)>Double.parseDouble(paramInfo.getStandMinValue())){
                    Log.e("判断：","合格");
                    res=true;
                }else{
                    Log.e("判断：","不合格");
                    res=false;
                }
            }



        }else if(paramInfo.getCategoryName().equals("区间外OK型")){
            Log.e("类型：","区间外OK型");
            if(!isDouble(mValue)){
                Toast.makeText(context,"输入检验值为空或者非法",Toast.LENGTH_SHORT).show();
                res=false;
            }else{
                if(Double.parseDouble(mValue)>Double.parseDouble(paramInfo.getStandMaxValue())&&Double.parseDouble(mValue)<Double.parseDouble(paramInfo.getStandMinValue())){
                    res=true;
                }else{
                    res=false;
                }
            }


        }else if(paramInfo.getCategoryName().equals("上限型")){
            Log.e("类型：","上限型");
            if(!isDouble(mValue)){
                Toast.makeText(context,"输入检验值为空或者非法",Toast.LENGTH_SHORT).show();
                res=false;
            }else{
                if(Double.parseDouble(mValue)<Double.parseDouble(paramInfo.getStandMaxValue())){
                    res=true;
                }else{
                    res=false;
                }
            }


        }else if(paramInfo.getCategoryName().equals("下限型")){
            Log.e("类型：","下限型");
            if(!isDouble(mValue)){
                Toast.makeText(context,"输入检验值为空或者非法",Toast.LENGTH_SHORT).show();
                res=false;
            }else{
                if(Double.parseDouble(mValue)>Double.parseDouble(paramInfo.getStandMinValue())){
                    res=true;
                }else{
                    res=false;
                }
            }


        }else if(paramInfo.getCategoryName().equals("无标准型")){
            Log.e("类型：","无标准型");
            res=true;

        }else if(paramInfo.getCategoryName().equals("多标准值型")){
            Log.e("类型：","多标准值型");
            if(mValue.equals(paramInfo.getStandValue())){
                res=true;
            }else{
                res=false;
            }

        }


        return  res;

    }



}
