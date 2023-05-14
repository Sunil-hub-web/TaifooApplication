package com.example.taifooapplication.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.taifooapplication.activity.HomePageActivity;
import com.example.taifooapplication.adapter.VariationAdapterforProductlist;
import com.example.taifooapplication.modelclas.VariationDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductDescription extends Fragment {

    TextView totalPrice1,priceSymbol1,product_Name,textUnit,productname,t1, t2, t3,totalPrice,
            text_ItemCount,Description_text,spinertext;
   // ImageView image_Arrow,productImage,img_Cart;

    ImageView productImage;
    String productName,productprice,variation_ID,Regular_price,product_Image,t,countvalue,userId,
            quantity,cartCount,Description,product_id;
    LinearLayout linearLayout;
    int count_value;
    Button btn_addToCart,backOption;
    ArrayList<VariationDetails> variationDetails;
    ArrayList<VariationDetails> variations;
    Dialog dialogMenu;
    RelativeLayout priceRel;

    SharedPrefManager sharedPrefManager ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_product_description,container,false);

        sharedPrefManager = new SharedPrefManager(getContext());

        totalPrice1 = view.findViewById(R.id.totalPrice1);
        totalPrice = view.findViewById(R.id.totalPrice);
        priceSymbol1 = view.findViewById(R.id.priceSymbol1);
     //   image_Arrow = view.findViewById(R.id.image_Arrow);
       // product_Name = view.findViewById(R.id.product_Name);
        productname = view.findViewById(R.id.productname);
        Description_text = view.findViewById(R.id.Description);
        //textUnit = findViewById(R.id.textUnit);
        productImage = view.findViewById(R.id.productImage);
        btn_addToCart = view.findViewById(R.id.btn_addToCart);
       // text_ItemCount = view.findViewById(R.id.text_ItemCount);
        spinertext = view.findViewById(R.id.spinertext);
        priceRel = view.findViewById(R.id.priceRel);
        backOption = view.findViewById(R.id.backOption);
    //    img_Cart = view.findViewById(R.id.img_Cart);

        linearLayout = view.findViewById(R.id.inc);
        t1 = view.findViewById(R.id.t1);
        t2 = view.findViewById(R.id.t2);
        t3 = view.findViewById(R.id.t3);

     //   userId = SharedPrefManager.getInstance(getContext()).getUser().getId();

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
        userId = sh.getString("userId", "");


        String image = product_Image;

        Bundle arguments = getArguments();

        if (arguments!=null){

            product_id = arguments.get("product_id").toString();
            //text_ItemCount.setText(cartCount);

            singleProduct(product_id);

            String userId = SharedPrefManager.getInstance(getContext()).getUser().getId();
            getCartCount(userId);

        }

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

                    updateToCart(userId,product_id,quantity,"","");

                }else{

                    updateToCart(userId,product_id,quantity,"",variation_ID);
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

                    updateToCart(userId,product_id,quantity,"","");

                }else{

                    updateToCart(userId,product_id,quantity,"",variation_ID);
                }


            }
        });

        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity = t2.getText().toString().trim();

                if(variationDetails.size() == 0){

                    btnAddToCart(userId,product_id,quantity,"","");

                }else{

                    if (spinertext.getText().toString().equals("Select Variation")){

                        Toast.makeText(getContext(), "Select Variation", Toast.LENGTH_SHORT).show();

                    }else{

                        btnAddToCart(userId,product_id,quantity,"",variation_ID);
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

                    dialogMenu = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
                    dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogMenu.setContentView(R.layout.variationrecycler_layout);
                    dialogMenu.setCancelable(true);
                    dialogMenu.setCanceledOnTouchOutside(true);
                    dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                    RecyclerView rv_vars = dialogMenu.findViewById(R.id.rv_vars);


                    rv_vars.setLayoutManager(new LinearLayoutManager(getContext()));
                    rv_vars.setNestedScrollingEnabled(false);

                    VariationAdapterforProductlist varad = new VariationAdapterforProductlist(variations, getContext());
                    rv_vars.setAdapter(varad);

                    rv_vars.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv_vars, new RecyclerTouchListener.ClickListener() {

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

        backOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ActivityCategory();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        return view;
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

        ProgressDialog progressDialog = new ProgressDialog(getContext());
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

                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                        //String userId = SharedPrefManager.getInstance(context).getUser().getId();
                        CartCountClass cartCountClass = new CartCountClass(getContext());
                        cartCountClass.getCartCount(userId);

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
                Toast.makeText(getContext(), "Api not response", Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void updateToCart(String userId, String productId, String quantity,String attribute_id, String variation_id) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
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

                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getContext(), "address Details Not Found", Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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

                    String userId = SharedPrefManager.getInstance(getContext()).getUser().getId();
                    CartCountClass cartCountClass = new CartCountClass(getContext());
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    public void singleProduct(String product_id){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
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
                            Picasso.with(getContext()).load(product_Image).into(productImage);
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
                            //HomePageActivity.text_name.setText(productName);
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
                            //HomePageActivity.text_name.setText(productName);
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

                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                        //text_ItemCount.setText(count);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}
