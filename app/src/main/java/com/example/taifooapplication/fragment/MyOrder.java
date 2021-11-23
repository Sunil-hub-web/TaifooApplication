package com.example.taifooapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.adapter.MyOrderadapter;
import com.example.taifooapplication.modelclas.MyOrder_ModelClass;

import java.util.ArrayList;

public class MyOrder extends Fragment {

    RecyclerView myOrderRecyclerView;
    LinearLayoutManager linearLayoutManager;
    MyOrderadapter myOrderDetailsadapter;
    ArrayList<MyOrder_ModelClass> myorder = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.myorder, container, false);

        myOrderRecyclerView = view.findViewById(R.id.myOrderRecyclerView);

        myorder.add(new MyOrder_ModelClass(R.drawable.save,"sad-hgdg-kk","25.05.2021",
                "OnGoing","250gm","250.00"));

        myorder.add(new MyOrder_ModelClass(R.drawable.save,"sad-hgdg-kk",
                "25.06.2021", "Cancelled","350gm","350.00"));

        myorder.add(new MyOrder_ModelClass(R.drawable.save,"sad-hgdg-kk",
                "25.07.2021", "Delivered","450gm","450.00"));

        myorder.add(new MyOrder_ModelClass(R.drawable.save,"sad-hgdg-kk",
                "25.08.2021", "orader","550gm","550.00"));

        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        myOrderDetailsadapter = new MyOrderadapter(getActivity(),myorder);
        myOrderRecyclerView.setLayoutManager(linearLayoutManager);
        myOrderRecyclerView.setHasFixedSize(true);
        myOrderRecyclerView.setAdapter(myOrderDetailsadapter);

        return view;
    }
}
