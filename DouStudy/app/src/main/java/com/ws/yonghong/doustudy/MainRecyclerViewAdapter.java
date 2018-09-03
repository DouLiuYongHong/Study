package com.ws.yonghong.doustudy;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
    private List<ItemMainBean> list;

    public MainRecyclerViewAdapter(List<ItemMainBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(list.get(position).itemName)) {
            holder.mText.setText(list.get(position).getItemName());
            if (list.get(position).getIntenClass() != null) {
                holder.item_tv_class.setText(list.get(position).getIntenClass().getSimpleName());
            } else {
                holder.item_tv_class.setText("");
            }
            holder.llyt_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position).getIntenClass() == null) {
                        return;
                    }
                    Intent mIntent = new Intent(holder.mText.getContext(), list.get(position).getIntenClass());
                    holder.mText.getContext().startActivity(mIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llyt_view;
        TextView mText;
        TextView item_tv_class;

        ViewHolder(View itemView) {
            super(itemView);
            llyt_view= itemView.findViewById(R.id.llyt_view);
            mText = itemView.findViewById(R.id.item_tx);
            item_tv_class = itemView.findViewById(R.id.item_tv_class);
        }
    }

}