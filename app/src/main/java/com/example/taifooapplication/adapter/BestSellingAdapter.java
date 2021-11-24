package com.example.taifooapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.activity.ProductDescription;
import com.example.taifooapplication.modelclas.BestSelling_modelClass;
import com.example.taifooapplication.modelclas.ShowItem_ModelClass;

import java.util.ArrayList;

public class BestSellingAdapter extends RecyclerView.Adapter<BestSellingAdapter.ViewHolder> {

    Context context;
    ArrayList<BestSelling_modelClass> sellProduct;

    public BestSellingAdapter(Context context, ArrayList<BestSelling_modelClass> showItem) {

        this.context = context;
        this.sellProduct = showItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ( )).inflate (R.layout.homepageproduct,parent,false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        BestSelling_modelClass bestSell = sellProduct.get (position);
        holder.productImage.setImageResource (bestSell.getImage ());

        if(bestSell.getUnit ().equals ("")){

            holder.productName.setText (bestSell.getProductName ());
            holder.productprice.setText (bestSell.getProductPrice ());
            holder.showProduct.setVisibility (View.GONE);

        }else{

            holder.productName.setText (bestSell.getProductName ());
            holder.productprice.setText (bestSell.getProductPrice ());
            holder.text_Unit.setText (bestSell.getUnit ());
            holder.showProduct.setVisibility (View.VISIBLE);
            holder.addToCart.setVisibility (View.GONE);

        }

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductDescription.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sellProduct.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName,productprice,text_Unit;
        RelativeLayout showProduct;
        Button addToCart;
        public ViewHolder(@NonNull  View itemView) {
            super (itemView);

            productImage = itemView.findViewById (R.id.productImage);
            productName = itemView.findViewById (R.id.productName);
            productprice = itemView.findViewById (R.id.productprice);
            text_Unit = itemView.findViewById (R.id.text_Unit);
            showProduct = itemView.findViewById (R.id.showProduct);
            addToCart = itemView.findViewById (R.id.addToCart);
        }
    }
}
