package com.shuben.common;
interface IPush{
 /**
     * @param tos 接收人，多个人之间用逗号分隔
     * @param title 推送标题
     *              des 描述
     * @param txt 推送内容  自定义
     * @param flag 设备来源  H5是3   PC是2  android是1
     * @param  action 动作，表示点击推送消息之后要产生什么动作。默认空字符
     *                module  模块名称
     *                IP跨进程，否则需要设计成contentprovide
     */
     void push(String IP,String from,String tos,String title,String des,String txt,int flag,int action,String module);


     /**
     *跨进程更新
     */
    // String changeShared();
}