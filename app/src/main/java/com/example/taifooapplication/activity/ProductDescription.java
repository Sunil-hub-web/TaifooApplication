package com.example.taifooapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.example.taifooapplication.RecyclerTouchListener;
import com.example.taifooapplication.SharedPrefManager;
import com.example.taifooapplication.adapter.VariationAdapterforProductlist;
import com.example.taifooapplication.fragment.CartCountClass;
import com.example.taifooapplication.fragment.CartPage;
import com.example.taifooapplication.modelclas.VariationDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductDescription extends AppCompatActivity {

    TextView totalPrice1,priceSymbol1,product_Name,textUnit,productname,t1, t2, t3,totalPrice,
            text_ItemCount,Description_text,spinertext;
    ImageView image_Arrow,productImage,img_Cart;
    String productName,productprice,variation_ID,Regular_price,product_Image,t,countvalue,productId,userId,
            quantity,cartCount,Description,product_id;
    LinearLayout linearLayout;
    int count_value;
    Button btn_addToCart;
    ArrayList<VariationDetails> variationDetails;
    ArrayList<VariationDetails> variations;
    Dialog dialogMenu;
    RelativeLayout priceRel;

    SharedPrefManager sharedPrefManager = new SharedPrefManager(this);

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
        Description_text = findViewById(R.id.Description);
        //textUnit = findViewById(R.id.textUnit);
        productImage = findViewById(R.id.productImage);
        btn_addToCart = findViewById(R.id.btn_addToCart);
        text_ItemCount = findViewById(R.id.text_ItemCount);
        spinertext = findViewById(R.id.spinertext);
        priceRel = findViewById(R.id.priceRel);
        img_Cart = findViewById(R.id.img_Cart);

        linearLayout = findViewById(R.id.inc);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);

        userId = SharedPrefManager.getInstance(ProductDescription.this).getUser().getId();

        String image = product_Image;
        //Picasso.with(ProductDescription.this).load(image).into(productImage);

        /*totalPrice1.setPaintFlags(totalPrice1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        priceSymbol1.setPaintFlags(priceSymbol1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        SpannableString ss = new SpannableString(tt_1);
        SpannableString ss1 = new SpannableString(tt_2);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();*/

       // ss.setSpan(strikethroughSpan,0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //ss1.setSpan(strikethroughSpan,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

       // t2.setText(quantity);

        product_id = getIntent().getStringExtra("product_id");

        singleProduct(product_id);

        image_Arrow.setOnClickListener(new View.OnClickListener() {
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

                if(variationDetails.size() == 0){

                    updateToCart(userId,productId,quantity,"","");

                }else{

                    updateToCart(userId,productId,quantity,"",variation_ID);
                }


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

                if(variationDetails.size() == 0){

                    updateToCart(userId,productId,quantity,"","");

                }else{

                    updateToCart(userId,productId,quantity,"",variation_ID);
                }


            }
        });

        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity = t2.getText().toString().trim();

                if(variationDetails.size() == 0){

                    btnAddToCart(userId,productId,quantity,"","");

                }else{

                    if (spinertext.getText().toString().equals("Select Variation")){

                        Toast.makeText(ProductDescription.this, "Select Variation", Toast.LENGTH_SHORT).show();

                    }else{

                        btnAddToCart(userId,productId,quantity,"",variation_ID);
                    }
                }

            }
        });

        spinertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                variations = new ArrayList<VariationDetails>();

                if(variationDetails.isEmpty()){

                }else {
                    variations = variationDetails;

                    dialogMenu = new Dialog(ProductDescription.this, android.R.style.Theme_Light_NoTitleBar);
                    dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogMenu.setContentView(R.layout.variationrecycler_layout);
                    dialogMenu.setCancelable(true);
                    dialogMenu.setCanceledOnTouchOutside(true);
                    dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                    RecyclerView rv_vars = dialogMenu.findViewById(R.id.rv_vars);


                    rv_vars.setLayoutManager(new LinearLayoutManager(ProductDescription.this));
                    rv_vars.setNestedScrollingEnabled(false);

                    VariationAdapterforProductlist varad = new VariationAdapterforProductlist(variations, ProductDescription.this);
                    rv_vars.setAdapter(varad);

                    rv_vars.addOnItemTouchListener(new RecyclerTouchListener(ProductDescription.this, rv_vars, new RecyclerTouchListener.ClickListener() {

                        @Override
                        public void onClick(View view, int post) {

                            Log.d("gbrdsfbfbvdz", "clicked");

                            priceRel.setVisibility(View.VISIBLE);

                            VariationDetails parenting = variations.get(post);

                            variation_ID = parenting.getPrice_id();

                            totalPrice1.setPaintFlags(totalPrice1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            priceSymbol1.setPaintFlags(priceSymbol1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                            totalPrice1.setText("₹ " + parenting.getPrice());
                            totalPrice.setText("₹ " + parenting.getPrice());
                            spinertext.setText(parenting.getVarations());

                            dialogMenu.dismiss();
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }

                    }));

                    dialogMenu.show();
                }

            }
        });

        String userId = SharedPrefManager.getInstance(ProductDescription.this).getUser().getId();
        getCartCount(userId);

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

    public void btnAddToCart(String userId, String productId, String quantity, String attribute_id, String variation_id) {

        ProgressDialog progressDialog = new ProgressDialog(ProductDescription.this);
        progressDialog.setMessage("Item Add To Cart");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.addToCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");

                    if (message.equals("true")) {

                        Toast.makeText(ProductDescription.this, msg, Toast.LENGTH_SHORT).show();

                        String userId = SharedPrefManager.getInstance(ProductDescription.this).getUser().getId();
                        getCartCount(userId);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(ProductDescription.this, "address Details Not Found", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                params.put("product_id", productId);
                params.put("quantity", quantity);
                params.put("attribute_id", attribute_id);
                params.put("variation_id", variation_id);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDescription.this);
        requestQueue.add(stringRequest);

    }

    public void updateToCart(String userId, String productId, String quantity,String attribute_id, String variation_id) {

        ProgressDialog progressDialog = new ProgressDialog(ProductDescription.this);
        progressDialog.setMessage("Updte Cart SuccessFully");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.updateToCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");

                    if (message.equals("true")) {

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
                error.printStackTrace();
                Toast.makeText(ProductDescription.this, "address Details Not Found", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                params.put("product_id", productId);
                params.put("quantity", quantity);
                params.put("attribute_id", attribute_id);
                params.put("variation_id", variation_id);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDescription.this);
        requestQueue.add(stringRequest);

    }

    public void deleteCartItem(String userId, String productId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.deleteFormCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //String message = jsonObject.getString("success");
                    String cart_count = jsonObject.getString("cart_count");
                    HomePageActivity.text_ItemCount.setText(cart_count);

                    String userId = SharedPrefManager.getInstance(ProductDescription.this).getUser().getId();
                    CartCountClass cartCountClass = new CartCountClass(ProductDescription.this);
                    cartCountClass.getCartCount(userId);

                    /*if(message.equals("true")){

                        String msg = jsonObject.getString("msg");
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("user_id", userId);
                params.put("product_id", productId);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDescription.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    public void singleProduct(String product_id){

        ProgressDialog progressDialog = new ProgressDialog(ProductDescription.this);
        progressDialog.setMessage("Show You Product Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.singleProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String product = jsonObject.getString("product");
                    JSONArray jsonArray_product = new JSONArray(product);

                    for (int i=0;i<jsonArray_product.length();i++){

                        JSONObject jsonObject_product = jsonArray_product.getJSONObject(0);

                        // String quantity = jsonObject_BestSellig.getString("quantity");
                        String product_id = jsonObject_product.getString("product_id");
                        productName = jsonObject_product.getString("product_name");
                        product_Image = jsonObject_product.getString("product_image");
                        //String product_weight = jsonObject_BestSellig.getString("product_weight");
                        //String product_grossweight = jsonObject_BestSellig.getString("product_grossweight");
                        //String plate = jsonObject_BestSellig.getString("plate");
                        Description = jsonObject_product.getString("description");
                        Regular_price = jsonObject_product.getString("regular_price");
                        productprice = jsonObject_product.getString("sales_price");
                        String variations = String.valueOf(jsonObject_product.getString("variation"));

                        variationDetails = new ArrayList<>();

                        if(variations.equals("null")){
                        }else{
                            JSONArray jsonArray_variat = new JSONArray(variations);

                            if (jsonArray_variat.length() == 0) {
                            } else {
                                for (int l = 0; l < jsonArray_variat.length(); l++) {

                                    JSONObject jsonObject_vari = jsonArray_variat.getJSONObject(l);

                                    String price_id = jsonObject_vari.getString("variation_id");
                                    String price = jsonObject_vari.getString("price");
                                    String varations = jsonObject_vari.getString("variation_name");

                                    VariationDetails variationDetails1 = new VariationDetails(price_id, price, varations);
                                    variationDetails.add(variationDetails1);

                                }
                            }

                        }

                        if(product_Image.equals("")){

                        }else{
                            Picasso.with(ProductDescription.this).load(product_Image).into(productImage);
                        }

                        if(variationDetails.size() != 0){

                            spinertext.setVisibility(View.VISIBLE);
                            priceRel.setVisibility(View.GONE);

                            totalPrice1.setPaintFlags(totalPrice1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            priceSymbol1.setPaintFlags(priceSymbol1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                            String tt_1 = Regular_price;
                            String tt_2 = "Rs.";

                            SpannableString ss = new SpannableString(tt_1);
                            SpannableString ss1 = new SpannableString(tt_2);
                            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

                            totalPrice1.setText(ss);
                            priceSymbol1.setText(ss1);
                            //textUnit.setText(text_Unit);
                            product_Name.setText(productName);
                            productname.setText(productName);
                            totalPrice.setText(productprice);
                            Description_text.setText(Description);


                        }else{

                            spinertext.setVisibility(View.GONE);
                            priceRel.setVisibility(View.VISIBLE);

                            totalPrice1.setPaintFlags(totalPrice1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            priceSymbol1.setPaintFlags(priceSymbol1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                            String tt_1 = Regular_price;
                            String tt_2 = "Rs.";

                            SpannableString ss = new SpannableString(tt_1);
                            SpannableString ss1 = new SpannableString(tt_2);
                            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

                            totalPrice1.setText(ss);
                            priceSymbol1.setText(ss1);
                            //textUnit.setText(text_Unit);
                            product_Name.setText(productName);
                            productname.setText(productName);
                            totalPrice.setText(productprice);
                            Description_text.setText(Description);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                Toast.makeText(ProductDescription.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("product_id",product_id);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDescription.this);
        requestQueue.add(stringRequest);

    }

    public void getCartCount(String userId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.cartCount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    if(msg.equals("success")){

                        String count = jsonObject.getString("count");
                        sharedPrefManager.cartCount(count);
                        text_ItemCount.setText(count);

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("user_id",userId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProductDescription.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}