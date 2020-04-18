package com.loftblog.loftmoney.screens.main.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loftblog.loftmoney.R;
import com.loftblog.loftmoney.screens.main.ItemsAdapterListener;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> dataList = new ArrayList<>();
    private ItemsAdapterListener listener;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void toggleItem(final int position) {
        selectedItems.put(position, !selectedItems.get(position));
        notifyDataSetChanged();
    }

    public void clearItem(final int position) {
        selectedItems.put(position, false);
        notifyDataSetChanged();
    }

    public int selectedSize() {
        int result = 0;
        for(int i = 0; i < dataList.size(); i++) {
            if (selectedItems.get(i)) {
                result++;
            }
        }
        return result;
    }

    public List<String> getSelectedItemsIds() {
        List<String> result = new ArrayList<>();
        int i = 0;
        for(Item item: dataList) {
            if (selectedItems.get(i)) {
                result.add(item.getId());
            }
            i++;
        }
        return result;
    }

    public void setNewData(List<Item> newData) {
        dataList.clear();
        dataList.addAll(newData);
        notifyDataSetChanged();
    }

    public void addDataToTop(Item item) {
        dataList.add(0, item);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(layoutInflater.inflate(R.layout.cell_item, parent, false));
    }

    public void setListener(ItemsAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(dataList.get(position), selectedItems.get(position));
        holder.setListener(listener, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView txtName = itemView.findViewById(R.id.txtItemName);
        private TextView txtValue = itemView.findViewById(R.id.txtItemValue);

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Item item, final Boolean isSelected) {
            itemView.setSelected(isSelected);
            txtName.setText(item.getName());
            txtValue.setText(item.getValue());
        }

        public void setListener(final ItemsAdapterListener listener, final Item item, final int positon) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, positon);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(item, positon);
                    return false;
                }
            });
        }
    }
}
