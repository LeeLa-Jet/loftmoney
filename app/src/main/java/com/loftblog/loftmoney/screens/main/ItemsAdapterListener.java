package com.loftblog.loftmoney.screens.main;

import com.loftblog.loftmoney.screens.main.adapter.Item;

public interface ItemsAdapterListener {
    public void onItemClick(Item item, int position);

    public void onItemLongClick(Item item, int position);
}
