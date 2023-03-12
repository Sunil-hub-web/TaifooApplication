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
import com.example.taifooapplication.modelclas.MyOrderDetails;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class OrderDetAdapter extends RecyclerView.Adapter<OrderDetAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyOrderDetails> product;

    String orderId,orderDate,orderStatues;

    public OrderDetAdapter(ArrayList<MyOrderDetails> product, Context context, String orderId, String orderDate, String orderStatues) {

        this.context = context;
        this.product = product;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatues = orderStatues;

    }

    @NonNull
    @Override
    public OrderDetAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderdetails,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  OrderDetAdapter.MyViewHolder holder, int position) {

        MyOrderDetails productdet = product.get(position);

        String image = productdet.getImage();

        if (image.equals("")){}else{

            Picasso.with(context).load(image).into(holder.productImage);
        }


        holder.text_ProdectName.setText(productdet.getProductName());
        holder.totalunit.setText(productdet.getUnit());
        holder.totalPrice.setText(productdet.getPrice());

        holder.text_orderId.setText(orderId);
        holder.text_orderDate.setText(orderDate);
        holder.text_orderStatus.setText(orderStatues);

        /*str_quantity = productdet.getProductQuantity();
        str_productPrice = productdet.getProductPrice();

        d_quantity = Double.valueOf(str_quantity);
        d_productPrice = Double.valueOf(str_productPrice);

        d_TotalPrice = d_quantity * d_productPrice;
        str_TotalPrice = String.valueOf(d_TotalPrice);

        holder.totalPrice.setText(str_TotalPrice);*/

    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_orderId,text_orderDate,text_orderStatus,totalunit,totalPrice,text_ProdectName;
        ImageView productImage;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);

            text_orderId = itemView.findViewById(R.id.text_orderId);
            text_orderDate = itemView.findViewById(R.id.text_orderDate);
            text_orderStatus = itemView.findViewById(R.id.text_orderStatus);
            totalunit = itemView.findViewById(R.id.totalunit);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            productImage = itemView.findViewById(R.id.productImage);
            text_ProdectName = itemView.findViewById(R.id.text_ProdectName);

        }
    }
}
