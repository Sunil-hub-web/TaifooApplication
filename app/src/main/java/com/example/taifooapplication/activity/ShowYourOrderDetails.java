package com.example.taifooapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taifooapplication.R;

public class ShowYourOrderDetails extends AppCompatActivity {

    TextView text_orderId,text_orderDate,text_orderStatus,totalunit,totalPrice,text_ProdectName;
    ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorderdetails);


    }
}