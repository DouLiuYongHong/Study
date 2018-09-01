package com.ws.yonghong.doustudy;

import java.io.Serializable;

public class ItemMainBean implements Serializable {
    long itemId = 0;
    String itemName = null;
    Class<?> intenClass = null;

    public ItemMainBean(long itemId, String itemName, Class<?> intenClass) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.intenClass = intenClass;
    }

    public ItemMainBean() {

    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Class<?> getIntenClass() {
        return intenClass;
    }

    public void setIntenClass(Class<?> intenClass) {
        this.intenClass = intenClass;
    }
}
