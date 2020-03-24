package com.loftblog.loftmoney.screens.main.adapter;

import java.io.Serializable;

public class Item implements Serializable {

    public static String KEY_NAME = Item.class.getName();

    private String name;
    private String value;

    public Item(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
