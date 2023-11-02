package com.example.taifooapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.modelclas.MyOrderDetails;
import com.example.taifooapplication.modelclas.MyOrder_ModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyOrderadapter extends RecyclerView.Adapter<MyOrderadapter.ViewHolder> {

    Context context;
    ArrayList<MyOrder_ModelClass> my_order;
    ArrayList<MyOrderDetails> my_orderDetails;
    Dialog dialogMenu;
    TextView textView;
    String orderId,orderDate,orderStatues;
    public MyOrderadapter(FragmentActivity activity, ArrayList<MyOrder_ModelClass> myorder) {

        this.context = activity;
        this.my_order = myorder;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderdetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyOrder_ModelClass myorder = my_order.get(position);

        holder.text_orderId.setText(myorder.getOrderId());
        holder.text_OrderDate.setText(myorder.getOrderDate());
        holder.totalPrice.setText(myorder.getTotal());
        holder.text_PaymentMode.setText(myorder.getPayment_mode());

        holder.btn_ViewOrderDetails.setPaintFlags(holder.btn_ViewOrderDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        holder.btn_ViewOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                my_orderDetails = new ArrayList<>();
                my_orderDetails = myorder.getMyorderDetails();

                orderId = myorder.getOrderId();
                orderDate = myorder.getOrderDate();
                orderStatues = myorder.getOrderStatus();

                dialogMenu = new Dialog(context, android.R.style.Theme_Light_NoTitleBar);
                dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogMenu.setContentView(R.layout.activity_show_your_order_details);
                dialogMenu.setCancelable(true);
                dialogMenu.setCanceledOnTouchOutside(true);

                textView = dialogMenu.findViewById(R.id.addressdetails);
                TextView subTotalPrice = dialogMenu.findViewById(R.id.subTotalPrice);
                TextView shippingCharges = dialogMenu.findViewById(R.id.shippingCharges);
                TextView totalPrice = dialogMenu.findViewById(R.id.totalPrice);
                TextView addressdetails = dialogMenu.findViewById(R.id.addressdetails);

                addressdetails.setText(myorder.getAddressName()+", "+myorder.getCity()+", "+
                        myorder.getAddress()+", "+myorder.getPhoneNumber()+", "+myorder.getPincode());

                RelativeLayout btn_dismiss = dialogMenu.findViewById(R.id.btn_dismiss);
                ImageView image_Arrow = dialogMenu.findViewById(R.id.image_Arrow);

                subTotalPrice.setText(myorder.getSubtotal());
                shippingCharges.setText(myorder.getShipping_charge());
                totalPrice.setText(myorder.getTotal());

                RecyclerView rv_vars = dialogMenu.findViewById(R.id.rv_vars);

                rv_vars.setLayoutManager(new LinearLayoutManager(context));
                rv_vars.setNestedScrollingEnabled(false);
                OrderDetAdapter varad = new OrderDetAdapter(my_orderDetails,context,orderId,orderDate,orderStatues);
                rv_vars.setAdapter(varad);

                btn_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogMenu.dismiss();

                    }
                });

                image_Arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogMenu.dismiss();

                    }
                });

                dialogMenu.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return my_order.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_orderId,text_OrderDate,totalPrice,btn_ViewOrderDetails,text_PaymentMode;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            text_orderId = itemView.findViewById(R.id.text_orderId);
            text_OrderDate = itemView.findViewById(R.id.text_OrderDate);
            btn_ViewOrderDetails = itemView.findViewById(R.id.btn_ViewOrderDetails);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            text_PaymentMode = itemView.findViewById(R.id.text_PaymentMode);


        }
    }
}
