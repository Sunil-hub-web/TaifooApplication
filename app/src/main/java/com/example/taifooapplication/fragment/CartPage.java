package com.example.taifooapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.activity.HomePageActivity;
import com.example.taifooapplication.activity.ProductDescription;
import com.example.taifooapplication.adapter.CartPageAdapter;
import com.example.taifooapplication.adapter.MyOrderadapter;
import com.example.taifooapplication.modelclas.CartPage_ModelClass;
import com.example.taifooapplication.modelclas.MyOrder_ModelClass;

import java.util.ArrayList;

public class CartPage extends Fragment {

    RecyclerView recyclerCartPage;
    LinearLayoutManager linearLayoutManager;
    CartPageAdapter cartPageAdapter;
    ArrayList<CartPage_ModelClass> itemList = new ArrayList<>();
    TextView text_gotoCheckout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cartpage,container,false);


        recyclerCartPage = view.findViewById(R.id.recyclerCartPage);
        text_gotoCheckout = view.findViewById(R.id.text_gotoCheckout);


        itemList.add(new CartPage_ModelClass(R.drawable.save, "Sunil in good","250gm","250.00"));

        itemList.add(new CartPage_ModelClass(R.drawable.save, "Cancelled","350gm","350.00"));

        itemList.add(new CartPage_ModelClass(R.drawable.save, "Delivered","450gm","450.00"));

        itemList.add(new CartPage_ModelClass(R.drawable.save,"orader","550gm","550.00"));

        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        cartPageAdapter = new CartPageAdapter(getActivity(),itemList);
        recyclerCartPage.setLayoutManager(linearLayoutManager);
        recyclerCartPage.setHasFixedSize(true);
        recyclerCartPage.setAdapter(cartPageAdapter);

        text_gotoCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.text_name.setText("Checkout");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                CheckOutPage checkOutPage = new CheckOutPage();
                ft.replace(R.id.framLayout, checkOutPage,"testID");
                ft.commit();

            }
        });



        return view;

    }
}
