package com.ws.yonghong.doustudy;

import java.io.Serializable;

public class ItemMainBean implements Serializable {
    long itemId = 0;
    String itemName = null;
    Class intenClass = null;

    public ItemMainBean(String itemId, String itemName, String intenClass) {
        Class clazz = null;
        try {
            this.itemId = Long.parseLong(itemId);
            this.itemName = itemName;
            clazz = Class.forName(intenClass);
            this.intenClass = clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    @Override
    public String toString() {
        return "ItemMainBean{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", intenClass=" + intenClass +
                '}';
    }
}
