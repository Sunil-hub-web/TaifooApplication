package com.example.taifooapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.modelclas.MyOrder_ModelClass;

import java.util.ArrayList;

public class MyOrderadapter extends RecyclerView.Adapter<MyOrderadapter.ViewHolder> {

    Context context;
    ArrayList<MyOrder_ModelClass> my_order;

    public MyOrderadapter(FragmentActivity activity, ArrayList<MyOrder_ModelClass> myorder) {

        this.context = activity;
        this.my_order = myorder;

    }

    @NonNull
    @Override
    public MyOrderadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderdetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderadapter.ViewHolder holder, int position) {

        MyOrder_ModelClass myorder = my_order.get(position);

        holder.productImage.setImageResource(myorder.getImage());
        holder.text_orderId.setText(myorder.getOrderId());
        holder.text_orderDate.setText(myorder.getOrderDate());
        holder.text_orderStatus.setText(myorder.getOrderStatus());
        holder.totalunit.setText(myorder.getUnit());
        holder.totalPrice.setText(myorder.getPrice());

        if(myorder.getOrderStatus().equals("Delivered")){
            holder.text_orderStatus.setTextColor(Color.GREEN);
        }
        if ((myorder.getOrderStatus().equals("Cancelled"))){
            holder.text_orderStatus.setTextColor(Color.RED);
        }
        if ((myorder.getOrderStatus().equals("OnGoing"))){
            holder.text_orderStatus.setTextColor(Color.YELLOW);
        }
    }

    @Override
    public int getItemCount() {
        return my_order.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_orderId,text_orderDate,text_orderStatus,totalunit,totalPrice;
        ImageView productImage;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            text_orderId = itemView.findViewById(R.id.text_orderId);
            text_orderDate = itemView.findViewById(R.id.text_orderDate);
            text_orderStatus = itemView.findViewById(R.id.text_orderStatus);
            totalunit = itemView.findViewById(R.id.totalunit);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
