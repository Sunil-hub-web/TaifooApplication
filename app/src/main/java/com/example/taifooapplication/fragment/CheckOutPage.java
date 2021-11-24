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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.adapter.OrderSummaryAdapter;
import com.example.taifooapplication.modelclas.OrderSummary_ModelClass;

import java.util.ArrayList;

public class CheckOutPage extends Fragment {

    RecyclerView orderSummaryRecycler;
    OrderSummaryAdapter orderSummaryAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<OrderSummary_ModelClass> ordersummary = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.checkoutpage,container,false);

        orderSummaryRecycler = view.findViewById(R.id.orderSummaryRecycler);

        ordersummary.add(new OrderSummary_ModelClass(R.drawable.chickenlegs,"Sunil kuamr dash","250.00"));
        ordersummary.add(new OrderSummary_ModelClass(R.drawable.chikencutpice,"Sunil kuamr dash","260.00"));
        ordersummary.add(new OrderSummary_ModelClass(R.drawable.chickenlegs,"Sunil kuamr dash","270.00"));

        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        orderSummaryAdapter = new OrderSummaryAdapter(getContext(),ordersummary);
        orderSummaryRecycler.setLayoutManager(linearLayoutManager);
        orderSummaryRecycler.setHasFixedSize(true);
        orderSummaryRecycler.setAdapter(orderSummaryAdapter);

        return view;
    }
}
