package com.example.taifooapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.modelclas.ShappingCharges_ModelClass;

import java.util.ArrayList;


public class ShappingAdapter extends RecyclerView.Adapter<ShappingAdapter.MyViewHolder> {

    Context context;
    ArrayList<ShappingCharges_ModelClass> shipping;

    public ShappingAdapter(ArrayList<ShappingCharges_ModelClass> shippingCharges, Context context) {

        this.context = context;
        this.shipping = shippingCharges;
    }


    @NonNull
    @Override
    public ShappingAdapter.MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shapping_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShappingAdapter.MyViewHolder holder, int position) {

        ShappingCharges_ModelClass shapping = shipping.get(position);

        holder.text_heading.setText(shapping.getShappingName());
        String shippPrice = shapping.getPrice();

    }

    @Override
    public int getItemCount() {
        return shipping.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_heading;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_heading = itemView.findViewById(R.id.heading);
        }
    }
}
