package com.loftblog.loftmoney.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.screens.addItem.AddItemActivity;
import com.loftblog.loftmoney.screens.main.adapter.Item;
import com.loftblog.loftmoney.screens.main.adapter.ItemsAdapter;
import com.loftblog.loftmoney.web.WebFactory;
import com.loftblog.loftmoney.web.models.AuthResponse;
import com.loftblog.loftmoney.web.models.GetItemsResponseModel;
import com.loftblog.loftmoney.web.models.ItemRemote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BudgetFragments extends Fragment {

    private ItemsAdapter itemsAdapter = new ItemsAdapter();
    public static int ADD_ITEM_REQUEST = 1;
    private List<Disposable> disposables = new ArrayList();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onStop() {
        for(Disposable disposable: disposables) {
            disposable.dispose();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

        loadItems();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerMain);
        swipeRefreshLayout = view.findViewById(R.id.swiper);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    public void loadItems() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(AuthResponse.AUTH_TOKEN_KEY, "");

        Disposable response = WebFactory.getInstance().loadItemsRequest().request("expense", authToken)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ItemRemote>>() {
                    @Override
                    public void accept(List<ItemRemote> listItems) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        List<Item> items = new ArrayList<>();
                        for (ItemRemote itemRemote: listItems) {
                            items.add(new Item(itemRemote));
                        }
                        sortItems(items);
                        itemsAdapter.setNewData(items);
                    }
                });
        disposables.add(response);
    }

    private void sortItems(List<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item first, Item second) {
                return first.getId().compareTo(second.getId());
            }
        });
    }
}
