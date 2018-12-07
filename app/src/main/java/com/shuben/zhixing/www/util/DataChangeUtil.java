package com.shuben.zhixing.www.util;

/**
 * Created by Geyan on 2017/8/24.
 */

public class DataChangeUtil {
    private  static  DataChangeUtil dataChangeUtil=null;

    public static synchronized DataChangeUtil getInstance() {

        if (dataChangeUtil==null){
            dataChangeUtil=new DataChangeUtil();
        }
        return dataChangeUtil;
    }
    public String getSource(int source){
        String result="";
        switch (source) {
            case 0:
                result="全部来源";
                break;
            case 1:
                result="高效会议";
                break;
            case 2:
                result="任务交办";
                break;
            case 3:
                result="内部催单";
                break;
            case 4:
                result="采购催单";
                break;
            case 5:
                result="智慧安灯";
                break;
            case 6:
                result="三级巡线";
                break;

            default:
                break;
        }
        return result;
    }

    public String getStatus(int source){
        String result="";
        switch (source) {
            case -10:
                result="审核未通过";
                break;
            case -5:
                result="未完成";
                break;
            case 0:
                result="未启动";
                break;
            case 2:
                result="进行中";
                break;
            case 5:
                result="待审核";
                break;
            case 10:
                result="已审核";
                break;

            default:
                break;
        }
        return result;
    }

}
