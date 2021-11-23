package com.example.taifooapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.modelclas.CartPage_ModelClass;

import java.util.ArrayList;

public class CartPageAdapter extends RecyclerView.Adapter<CartPageAdapter.ViewHolder> {

    Context context;
    ArrayList<CartPage_ModelClass> itemlist;

    public CartPageAdapter(FragmentActivity activity, ArrayList<CartPage_ModelClass> itemList) {

        this.context = activity;
        this.itemlist = itemList;
    }

    @NonNull
    @Override
    public CartPageAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartpagedetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CartPageAdapter.ViewHolder holder, int position) {

        CartPage_ModelClass cartItem = itemlist.get(position);

        holder.productImage.setImageResource(cartItem.getImage());
        holder.product_Name.setText(cartItem.getProductName());
        holder.totalPrice.setText(cartItem.getProductPrice());
        holder.unit_Name.setText(cartItem.getUnit());

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView totalPrice,product_Name,unit_Name;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            product_Name = itemView.findViewById(R.id.product_Name);
            unit_Name = itemView.findViewById(R.id.unit_Name);
        }
    }
}
