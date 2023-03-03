package com.example.taifooapplication.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.taifooapplication.activity.ProductDescription;
import com.example.taifooapplication.adapter.CartPageAdapter;
import com.example.taifooapplication.adapter.MyOrderadapter;
import com.example.taifooapplication.adapter.ShappingAdapter;
import com.example.taifooapplication.modelclas.CartPage_ModelClass;
import com.example.taifooapplication.modelclas.MyOrder_ModelClass;
import com.example.taifooapplication.modelclas.ShappingCharges_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartPage extends Fragment {

    public static RecyclerView recyclerCartPage,rv_vars;;
    LinearLayoutManager linearLayoutManager;
    CartPageAdapter cartPageAdapter;
    ArrayList<CartPage_ModelClass> itemList = new ArrayList<>();
    ArrayList<ShappingCharges_ModelClass> ShippingCharges = new ArrayList<ShappingCharges_ModelClass>();
    public static TextView text_gotoCheckout,text_deliveryPrice,text_subTotalPrice,text_taxandfee,
            text_totalPrice,delivery;
    String userId;
    Dialog dialogMenu;
    double totalprice, sales_Price, quanTity, totalAmount = 0, shipCharge,taxCharge;
    public static LinearLayout cartempty,lin_amount;
    public static RelativeLayout rel_totalprice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cartpage,container,false);


        recyclerCartPage = view.findViewById(R.id.recyclerCartPage);
        text_gotoCheckout = view.findViewById(R.id.text_gotoCheckout);
        delivery = view.findViewById(R.id.delivery);
        text_deliveryPrice = view.findViewById(R.id.text_deliveryPrice);
        text_subTotalPrice = view.findViewById(R.id.text_subTotalPrice);
        text_taxandfee = view.findViewById(R.id.text_taxandfee);
        text_totalPrice = view.findViewById(R.id.text_totalPrice);
        cartempty = view.findViewById(R.id.cartempty);
        rel_totalprice = view.findViewById(R.id.totalprice);
        lin_amount = view.findViewById(R.id.amount);

        userId = SharedPrefManager.getInstance(getContext()).getUser().getId();

        text_gotoCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(text_deliveryPrice.getText().toString().trim().equals("0")){

                    Toast.makeText(getContext(), "Please Select Delevery Charges", Toast.LENGTH_SHORT).show();

                }else{

                    HomePageActivity.text_name.setText("Checkout");
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    CheckOutPage checkOutPage = new CheckOutPage();
                    ft.replace(R.id.framLayout, checkOutPage,"testID");
                    ft.commit();

                    SharedPreferences sp = getContext().getSharedPreferences("details", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("subTotalPrice",text_subTotalPrice.getText().toString().trim());
                    editor.putString("deliveryPrice",text_deliveryPrice.getText().toString().trim());
                    editor.putString("totalPrice",text_totalPrice.getText().toString().trim());
                    editor.putString("taxandfee",text_taxandfee.getText().toString().trim());
                    editor.putString("ShippingNmae",delivery.getText().toString().trim());
                    editor.commit();

                }


            }
        });

        getShippingCharges();
        getCartItem(userId);


        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogMenu = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
                dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogMenu.setContentView(R.layout.shapping_layout);
                dialogMenu.setCancelable(true);
                dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialogMenu.setCanceledOnTouchOutside(true);

                rv_vars = dialogMenu.findViewById(R.id.rv_vars);

                rv_vars.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_vars.setNestedScrollingEnabled(false);
                ShappingAdapter varad = new ShappingAdapter(ShippingCharges, getContext());
                rv_vars.setAdapter(varad);

               rv_vars.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv_vars, new RecyclerTouchListener.ClickListener() {
                   @Override
                   public void onClick(View view, int position) {

                       ShappingCharges_ModelClass parenting = ShippingCharges.get(position);

                       String shippingPrice = parenting.getDeliveryPrice();
                       String shippingName = parenting.getShappingName();

                       delivery.setText(shippingName);
                       text_deliveryPrice.setText(shippingPrice);

                       shipCharge = Double.valueOf(shippingPrice);

                       String tax = text_taxandfee.getText().toString().trim();
                       taxCharge = Double.valueOf(tax);

                       Double AmountTotal = totalAmount + shipCharge + taxCharge;

                       String tot_Amount = String.valueOf(AmountTotal);

                       text_totalPrice.setText(tot_Amount);

                       dialogMenu.dismiss();

                   }

                   @Override
                   public void onLongClick(View view, int position) {

                   }

               }));

                dialogMenu.show();

            }
        });

        return view;

    }

    public void getCartItem(String userid){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Get Cart Item Details");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getItemFormCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //String message = jsonObject.getString("success");
                    String All_Cart = jsonObject.getString("allcart");

                    JSONArray jsonArray_AllCart1 = new JSONArray(All_Cart);

                    if(jsonArray_AllCart1.length() != 0){

                        JSONArray jsonArray_AllCart = new JSONArray(All_Cart);

                        for (int i=0;i<jsonArray_AllCart.length();i++){

                            JSONObject jsonObject_AllCart = jsonArray_AllCart.getJSONObject(i);

                           // String cart_id = jsonObject_AllCart.getString("cart_id");
                            String product_id = jsonObject_AllCart.getString("product_id");
                            String product_name = jsonObject_AllCart.getString("product_name");
                           // String product_weight = jsonObject_AllCart.getString("product_weight");
                            String variation = jsonObject_AllCart.getString("variation");
                            String product_img = jsonObject_AllCart.getString("product_image");
                            String quantity = jsonObject_AllCart.getString("product_qty");
                            String sale_price = jsonObject_AllCart.getString("price");

                            CartPage_ModelClass cartPage_modelClass = new CartPage_ModelClass(
                                    product_id,product_img,product_name,variation,sale_price,quantity
                            );

                            sales_Price = Double.valueOf(sale_price);

                            quanTity = Double.valueOf(quantity);

                            totalprice = sales_Price * quanTity;

                            totalAmount = totalAmount + totalprice;

                            itemList.add(cartPage_modelClass);


                        }

                        if(itemList.size() == 0){

                            cartempty.setVisibility(View.VISIBLE);
                            lin_amount.setVisibility(View.GONE);
                            recyclerCartPage.setVisibility(View.GONE);
                            rel_totalprice.setVisibility(View.GONE);

                        }else{

                            cartempty.setVisibility(View.GONE);
                            lin_amount.setVisibility(View.VISIBLE);
                            recyclerCartPage.setVisibility(View.VISIBLE);
                            rel_totalprice.setVisibility(View.VISIBLE);

                            int size =  itemList.size();

                            String str_size = String.valueOf(size);

                            //text_ItemCount.setText(str_size);

                            String total_price = String.valueOf(totalAmount);

                            text_subTotalPrice.setText(total_price);

                            String tax = text_taxandfee.getText().toString().trim();
                            taxCharge = Double.valueOf(tax);

                            Double amount = taxCharge + totalAmount;
                            String str_Amount = String.valueOf(amount);

                            //text_totalPrice.setText(str_Amount);

                            String shippingPrice = text_deliveryPrice.getText().toString().trim();

                            //delivery.setText(shippingName);
                            //text_deliveryPrice.setText(shippingPrice);

                            shipCharge = Double.valueOf(shippingPrice);

                           /* String tax1 = text_taxandfee.getText().toString().trim();
                            taxCharge = Double.valueOf(tax1);*/

                            Double AmountTotal = totalAmount + shipCharge + taxCharge;

                            String tot_Amount = String.valueOf(AmountTotal);

                            text_totalPrice.setText(tot_Amount);

                            linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                            cartPageAdapter = new CartPageAdapter(getActivity(),itemList);
                            recyclerCartPage.setLayoutManager(linearLayoutManager);
                            recyclerCartPage.setHasFixedSize(true);
                            recyclerCartPage.setAdapter(cartPageAdapter);

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
                error.printStackTrace ();
                Toast.makeText(getContext(), "address Details Not Found", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("user_id",userid);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void getShippingCharges(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getShippingCharges, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        String All_shipping_chr = jsonObject.getString("All_shipping_chr");

                        JSONArray jsonArray_Shipping = new JSONArray(All_shipping_chr);

                        for (int i = 0; i < jsonArray_Shipping.length(); i++) {

                            JSONObject jsonObject_Shipping = jsonArray_Shipping.getJSONObject(i);

                            String shipping_id = jsonObject_Shipping.getString("shipping_id");
                            String price = jsonObject_Shipping.getString("price");
                            String delivery_price = jsonObject_Shipping.getString("delivery_price");
                            String name = jsonObject_Shipping.getString("name");

                            ShappingCharges_ModelClass shappingCharges_modelClass = new ShappingCharges_ModelClass(shipping_id,
                                    price, delivery_price, name
                            );

                            ShippingCharges.add(shappingCharges_modelClass);

                        }

                        Log.d("ShippingCharges", ShippingCharges.toString());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);


    }
}
