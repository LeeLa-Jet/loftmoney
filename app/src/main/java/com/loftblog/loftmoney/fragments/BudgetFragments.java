package com.loftblog.loftmoney.fragments;

import android.content.Intent;
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

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.screens.addItem.AddItemActivity;
import com.loftblog.loftmoney.screens.main.adapter.Item;
import com.loftblog.loftmoney.screens.main.adapter.ItemsAdapter;
import com.loftblog.loftmoney.web.WebFactory;
import com.loftblog.loftmoney.web.models.GetItemsResponseModel;
import com.loftblog.loftmoney.web.models.ItemRemote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BudgetFragments extends Fragment {

    private ItemsAdapter itemsAdapter = new ItemsAdapter();
    static int ADD_ITEM_REQUEST = 1;
    private List<Disposable> disposables = new ArrayList();

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
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        view.findViewById(R.id.fabMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItemIntent = new Intent(getActivity(), AddItemActivity.class);
                startActivityForResult(addItemIntent, ADD_ITEM_REQUEST);
            }
        });
        return view;
    }

    public void loadItems() {
        Disposable response = WebFactory.getInstance().loadItemsRequest().request("expense")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetItemsResponseModel>() {
                    @Override
                    public void accept(GetItemsResponseModel getItemsResponseModel) throws Exception {
                        List<Item> items = new ArrayList<>();
                        for (ItemRemote itemRemote: getItemsResponseModel.getData()) {
                            items.add(new Item(itemRemote));
                        }
                        itemsAdapter.setNewData(items);
                    }
                });
        disposables.add(response);
    }
}
