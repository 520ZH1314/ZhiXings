package com.shuben.contact.lib.event;

public class ConstantEvent {
    private Boolean isEdit;

    public ConstantEvent(Boolean isEdit,  Boolean isSingle,String type) {
        this.isEdit = isEdit;
        this.isSingle = isSingle;
        Type = type;

    }

    public String getType() {
        return Type;

    }

    public void setType(String type) {
        Type = type;
    }

    private String Type;

    public Boolean getSingle() {
        return isSingle;
    }

    public ConstantEvent(Boolean isEdit, Boolean isSingle) {
        this.isEdit = isEdit;
        this.isSingle = isSingle;
    }

    public void setSingle(Boolean single) {
        isSingle = single;
    }

    private Boolean isSingle;


    public Boolean getEdit() {
        return isEdit;
    }

    public void setEdit(Boolean edit) {
        isEdit = edit;
    }
}
