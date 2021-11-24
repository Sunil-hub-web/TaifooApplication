package com.example.taifooapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.modelclas.OrderSummary_ModelClass;

import java.util.ArrayList;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {

    Context context;
    ArrayList<OrderSummary_ModelClass> ordersummary;

    public OrderSummaryAdapter(Context context, ArrayList<OrderSummary_ModelClass> ordersummary) {

        this.context = context;
        this.ordersummary = ordersummary;
    }

    @NonNull
    @Override
    public OrderSummaryAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordersummary,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  OrderSummaryAdapter.ViewHolder holder, int position) {

        OrderSummary_ModelClass order_summary = ordersummary.get(position);

        holder.productImage.setImageResource(order_summary.getImage());
        holder.productName.setText(order_summary.getName());
        holder.total.setText(order_summary.getPrice());

    }

    @Override
    public int getItemCount() {
        return ordersummary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName,total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            total = itemView.findViewById(R.id.total);

        }
    }
}
