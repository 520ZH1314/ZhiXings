package com.shuben.zhixing.www.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/8.
 * 2.2.15	我的组织->获取部门列表
 *
 "id": "6bb6648e-3dc7-404d-8a9f-7c14e2968c45",
 "text": "深圳市建滔科技有限公司"
 */

public class GetOrganizeList_Data implements Serializable{
    private  String  id;
    private  String  text;


    public GetOrganizeList_Data() {
    }

    public GetOrganizeList_Data(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
