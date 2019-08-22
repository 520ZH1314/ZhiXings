package com.shuben.zhixing.www.inspection.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Geyan on 2018/7/13.
 */

public class ItemInfo implements Serializable{
    private String PatrolRecord;//巡检记录ID
    private String PatrolTaskId;//巡检任务ID
    private String ItemId;//巡检项ID
    private String ItemName;//巡线项描述
    private String ItemType;//类型，人、机、料、法、环
    private String PatrolFashion;//巡检方式，取值有;//数量、拍照、说明
    private String Result;//取值有OK/NG/空值
    private String CreateTime;
    private String ItemSource;
    private List<ParamInfo> list;//该巡检项对应的文本框信息，返回值为Json格式，
    private  String path;
    private String tx03V;
    private String tx04V;

    public String getTx03V() {
        return tx03V;
    }

    public void setTx03V(String tx03V) {
        this.tx03V = tx03V;
    }

    public String getTx04V() {
        return tx04V;
    }

    public void setTx04V(String tx04V) {
        this.tx04V = tx04V;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ItemInfo(String patrolRecord, String patrolTaskId, String itemId, String itemName, String itemType, String patrolFashion, String result, String createTime, String itemSource, List<ParamInfo> list) {

        PatrolRecord = patrolRecord;
        PatrolTaskId = patrolTaskId;
        ItemId = itemId;
        ItemName = itemName;
        ItemType = itemType;
        PatrolFashion = patrolFashion;
        Result = result;
        CreateTime = createTime;
        ItemSource = itemSource;
        this.list = list;
    }

    public String getPatrolRecord() {
        return PatrolRecord;
    }

    public void setPatrolRecord(String patrolRecord) {
        PatrolRecord = patrolRecord;
    }

    public String getPatrolTaskId() {
        return PatrolTaskId;
    }

    public void setPatrolTaskId(String patrolTaskId) {
        PatrolTaskId = patrolTaskId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public String getPatrolFashion() {
        return PatrolFashion;
    }

    public void setPatrolFashion(String patrolFashion) {
        PatrolFashion = patrolFashion;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getItemSource() {
        return ItemSource;
    }

    public void setItemSource(String itemSource) {
        ItemSource = itemSource;
    }

    public List<ParamInfo> getList() {
        return list;
    }

    public void setList(List<ParamInfo> list) {
        this.list = list;
    }
}


