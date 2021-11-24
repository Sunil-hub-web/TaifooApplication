package com.example.taifooapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

import com.example.taifooapplication.R;

public class ProductDescription extends AppCompatActivity {

    TextView totalPrice1,priceSymbol1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

       /* totalPrice1 = findViewById(R.id.totalPrice1);
        priceSymbol1 = findViewById(R.id.priceSymbol1);

        String tt_1 = "284.00";
        String tt_2 = "Rs.";

        SpannableString ss = new SpannableString(tt_1);
        SpannableString ss1 = new SpannableString(tt_2);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        ss.setSpan(strikethroughSpan,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(strikethroughSpan,0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        totalPrice1.setText(ss);
        priceSymbol1.setText(ss1);*/

    }
}