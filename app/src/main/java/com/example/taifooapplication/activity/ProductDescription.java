package com.example.taifooapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.taifooapplication.AppURL;
import com.example.taifooapplication.R;
import com.example.taifooapplication.SharedPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductDescription extends AppCompatActivity {

    TextView totalPrice1,priceSymbol1,product_Name,textUnit,productname,t1, t2, t3,totalPrice,
            text_ItemCount;
    ImageView image_Arrow,productImage;
    String productName,productprice,text_Unit,Regular_price,product_Image,t,countvalue,productId,userId,
            quantity,cartCount;
    LinearLayout linearLayout;
    int count_value;
    Button btn_addToCart;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

       /* Window window = ProductDescription.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ProductDescription.this, R.color.white));*/

        totalPrice1 = findViewById(R.id.totalPrice1);
        totalPrice = findViewById(R.id.totalPrice);
        priceSymbol1 = findViewById(R.id.priceSymbol1);
        image_Arrow = findViewById(R.id.image_Arrow);
        product_Name = findViewById(R.id.product_Name);
        productname = findViewById(R.id.productname);
        textUnit = findViewById(R.id.textUnit);
        productImage = findViewById(R.id.productImage);
        btn_addToCart = findViewById(R.id.btn_addToCart);

        linearLayout = findViewById(R.id.inc);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);

        Intent intent = getIntent();

        productName = intent.getStringExtra("productName");
        productprice = intent.getStringExtra("productprice");
        text_Unit = intent.getStringExtra("text_Unit");
        quantity = intent.getStringExtra("quantity");
        Regular_price = intent.getStringExtra("Regular_price");
        product_Image = intent.getStringExtra("productImage");
        productId = intent.getStringExtra("productId");
        cartCount = intent.getStringExtra("cartCount");
        userId = SharedPrefManager.getInstance(ProductDescription.this).getUser().getId();

        String image = "https://"+product_Image;
        Picasso.with(ProductDescription.this).load(image).into(productImage);

        String tt_1 = Regular_price;
        String tt_2 = "Rs.";

        text_ItemCount.setText(cartCount);

        SpannableString ss = new SpannableString(tt_1);
        SpannableString ss1 = new SpannableString(tt_2);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

        ss.setSpan(strikethroughSpan,0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(strikethroughSpan,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        totalPrice1.setText(ss);
        priceSymbol1.setText(ss1);
        textUnit.setText(text_Unit);
        product_Name.setText(productName);
        totalPrice.setText(productprice);

        t2.setText(quantity);

        image_Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductDescription.this,HomePageActivity.class);
                startActivity(intent);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout(false);

                quantity = t2.getText().toString().trim();
                count_value = Integer.valueOf(t2.getText().toString());

                countvalue = String.valueOf(count_value);

                Double productPrice = Double.valueOf(productprice);

                Double amount = productPrice * count_value;
                String str_Amount = String.valueOf(amount);

                totalPrice.setText(str_Amount);


            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout(true);

                quantity = t2.getText().toString().trim();

                count_value = Integer.valueOf(t2.getText().toString());

                countvalue = String.valueOf(count_value);

                Double productPrice = Double.valueOf(productprice);

                Double amount = productPrice * count_value;
                String str_Amount = String.valueOf(amount);

                totalPrice.setText(str_Amount);


            }
        });

        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity = t2.getText().toString().trim();

                btnAddToCart(userId,productId,quantity);
            }
        });

    }

    private void linearLayout(Boolean x) {
        int y = Integer.parseInt(t2.getText().toString());
        if (x) {
            y++;
            t2.setText(String.valueOf(y));
        } else {
            y--;
            if (y <= 0) {
                t2.setText("1");
            } else {
                t2.setText(String.valueOf(y));
            }
        }
    }

    public void btnAddToCart(String userId,String productId,String quantity){

        ProgressDialog progressDialog = new ProgressDialog(ProductDescription.this);
        progressDialog.setMessage("Item Add To Cart");
        progressDialog.show();

        StringRequest stringRequest  = new StringRequest(Request.Method.POST, AppURL.addToCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");

                    if(message.equals("true")){

                        Toast.makeText(ProductDescription.this, msg, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace ();
                Toast.makeText(ProductDescription.this, "address Details Not Found", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("user_id",userId);
                params.put("product_id",productId);
                params.put("quantity",quantity);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDescription.this);
        requestQueue.add(stringRequest);

    }

}