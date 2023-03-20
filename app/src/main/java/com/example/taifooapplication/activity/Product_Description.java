package com.example.taifooapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.example.taifooapplication.R;

public class Product_Description extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

       /* Window window = ProductDescription.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ProductDescription.this, R.color.white));*/


        //Picasso.with(ProductDescription.this).load(image).into(productImage);

        /*totalPrice1.setPaintFlags(totalPrice1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        priceSymbol1.setPaintFlags(priceSymbol1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        SpannableString ss = new SpannableString(tt_1);
        SpannableString ss1 = new SpannableString(tt_2);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();*/

       // ss.setSpan(strikethroughSpan,0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //ss1.setSpan(strikethroughSpan,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

       // t2.setText(quantity);



       /* image_Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductDescription.this,HomePageActivity.class);
                startActivity(intent);
            }
        });

        img_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.search.setVisibility(View.GONE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                CartPage cartPage = new CartPage();
                ft.replace(R.id.framLayout, cartPage);
                ft.commit();
                HomePageActivity.text_name.setTextSize(18);
                HomePageActivity.text_name.setText("My Cart");
            }
        });
*/



    }

}