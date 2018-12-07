package com.shuben.contact.lib.event;

public class ConstantOneEvent {
    private Boolean isRemind;

    public ConstantOneEvent(Boolean isRemind) {
        this.isRemind = isRemind;
    }

    public Boolean getEdit() {
        return isRemind;
    }

    public void setEdit(Boolean isRemind) {
        isRemind = isRemind;
    }
}
