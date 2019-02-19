package com.example.dell.carz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdAdapter extends RecyclerView.Adapter<AdViewHolder> {
    private List<AdView> items;
    private int itemLayout;
    private Context context;

    public AdAdapter(List<AdView> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public AdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new AdViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdViewHolder holder, int position) {
        if (items != null && holder != null) {
            holder.setValues(items.get(position));
        }

    }

    @Override
    public int getItemCount() {
        if(items != null)
            return items.size();
        else
            return 0;
    }}