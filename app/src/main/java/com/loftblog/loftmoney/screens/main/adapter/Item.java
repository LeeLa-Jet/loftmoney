package com.loftblog.loftmoney.screens.main.adapter;

import com.loftblog.loftmoney.web.models.ItemRemote;

import java.io.Serializable;

public class Item implements Serializable {

    public static String KEY_NAME = Item.class.getName();

    private String id;
    private String name;
    private String value;

    public Item(String name, String value) {
        this.id = "1";
        this.name = name;
        this.value = value;
    }

    public Item(ItemRemote itemRemote) {
        this.id = itemRemote.getId();
        this.name = itemRemote.getName();
        this.value = itemRemote.getPrice() + " Р";
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getId() {
        return id;
    }
}
