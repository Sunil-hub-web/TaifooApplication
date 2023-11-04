package com.example.taifooapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.fragment.app.FragmentActivity;
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
import com.example.taifooapplication.activity.Product_Description;
import com.example.taifooapplication.fragment.CartCountClass;
import com.example.taifooapplication.fragment.ProductDescription;
import com.example.taifooapplication.modelclas.SerachProductModel;
import com.example.taifooapplication.modelclas.VariationDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SerachAdapter extends RecyclerView.Adapter<SerachAdapter.ViewHolder> {

    Context context;
    ArrayList<SerachProductModel> sellProduct;
    ArrayList<SerachProductModel> payment2;
    int count_value;
    String countvalue, userid, productid, quantity, cartCount;
    ArrayList<VariationDetails> variations;
    Dialog dialogMenu;
    public SerachAdapter(Context context, ArrayList<SerachProductModel> serachProductModels) {

        this.context = context;
        this.sellProduct = serachProductModels;
        this.payment2 = new ArrayList<>();
        this.payment2.addAll(serachProductModels);
    }

    @NonNull
    @Override
    public SerachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepageproduct, parent, false);
        return new SerachAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerachAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SerachProductModel bestSell = sellProduct.get(position);

        String image = bestSell.getProduct_img();

        if (image.equals("")) {

        } else {
            Picasso.with(context).load(image).into(holder.productImage);
        }

        if (bestSell.getVariation().size() == 0) {

            holder.text_salesPrice.setText(bestSell.getSale_price());
            holder.text_Regularprice.setText(bestSell.getRegular_price());

            holder.spinertext.setVisibility(View.GONE);

            holder.priceRel.setVisibility(View.VISIBLE);
            holder.addToCart.setVisibility(View.VISIBLE);

        } else {

            holder.priceRel.setVisibility(View.GONE);
            holder.addToCart.setVisibility(View.GONE);

            holder.spinertext.setVisibility(View.VISIBLE);

        }

        holder.productName.setText(bestSell.getProduct_name());

        //holder.text_Unit.setText (bestSell.getProduct_weight ());

        String str_quantity_item = bestSell.getQuantity();

        holder.text_Regularprice.setPaintFlags(holder.text_Regularprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //holder.rs1.setPaintFlags(holder.rs1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        int quantity_item = Integer.valueOf(str_quantity_item);

        if (quantity_item != 0) {

            holder.addToCart.setVisibility(View.GONE);
            holder.showProduct.setVisibility(View.VISIBLE);

        } else {

            holder.addToCart.setVisibility(View.VISIBLE);
            holder.showProduct.setVisibility(View.GONE);
        }

        //userid = SharedPrefManager.getInstance(context).getUser().getId();

        SharedPreferences sh = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
        userid = sh.getString("userId", "");

        holder.spinertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                variations = new ArrayList<VariationDetails>();

                if (sellProduct.get(position).getVariation().isEmpty()) {

                } else {
                    variations = sellProduct.get(position).getVariation();

                    dialogMenu = new Dialog(context, android.R.style.Theme_Light_NoTitleBar);
                    dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogMenu.setContentView(R.layout.variationrecycler_layout);
                    dialogMenu.setCancelable(true);
                    dialogMenu.setCanceledOnTouchOutside(true);
                    dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                    RecyclerView rv_vars = dialogMenu.findViewById(R.id.rv_vars);

                    rv_vars.setLayoutManager(new LinearLayoutManager(context));
                    rv_vars.setNestedScrollingEnabled(false);

                    VariationAdapterforProductlist varad = new VariationAdapterforProductlist(variations, context);
                    rv_vars.setAdapter(varad);

                    rv_vars.addOnItemTouchListener(new RecyclerTouchListener(context, rv_vars, new RecyclerTouchListener.ClickListener() {

                        @Override
                        public void onClick(View view, int post) {

                            Log.d("gbrdsfbfbvdz", "clicked");

                            VariationDetails parenting = variations.get(post);

                            sellProduct.get(position).setVar_id(parenting.getPrice_id());
                            sellProduct.get(position).setVar_price(parenting.getPrice());
                            sellProduct.get(position).setVar_name(parenting.getVarations());


                            holder.text_salesPrice.setText("₹ " + parenting.getPrice());
                            holder.text_Regularprice.setText("₹ " + parenting.getPrice());
                            holder.spinertext.setText(parenting.getVarations());

                            holder.priceRel.setVisibility(View.VISIBLE);
                            holder.addToCart.setVisibility(View.VISIBLE);

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

        /*holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.showProduct.setVisibility(View.VISIBLE);
                holder.addToCart.setVisibility(View.GONE);
            }
        });*/

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity = holder.t2.getText().toString().trim();

                ProductDescription productDescription = new ProductDescription();
                Bundle bundle=new Bundle();
                bundle.putString("product_id", bestSell.getProduct_id());
                productDescription.setArguments(bundle);
                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.framLayout,productDescription);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(false);

                quantity = holder.t2.getText().toString().trim();
                //-

                String producy_id = bestSell.getProduct_id();

                if (quantity.equals("0")) {

                    deleteCartItem(userid, producy_id);

                    holder.showProduct.setVisibility(View.GONE);
                    holder.addToCart.setVisibility(View.VISIBLE);

                } else {

                    holder.showProduct.setVisibility(View.VISIBLE);
                    holder.addToCart.setVisibility(View.GONE);

                    quantity = holder.t2.getText().toString().trim();
                    count_value = Integer.valueOf(holder.t2.getText().toString());

                    countvalue = String.valueOf(count_value);

                    productid = bestSell.getProduct_id();

                    if (bestSell.getVariation().size() == 0) {

                        updateToCart(userid, productid, quantity, "", "");

                    } else {

                        String variId = bestSell.getVar_id();
                        updateToCart(userid, productid, quantity, "", variId);
                    }


                }


            }
        });

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(true);

                //+
                quantity = holder.t2.getText().toString().trim();
                productid = bestSell.getProduct_id();
                if (bestSell.getVariation().size() == 0) {

                    updateToCart(userid, productid, quantity, "", "");

                } else {

                    String variId = bestSell.getVar_id();
                    updateToCart(userid, productid, quantity, "", variId);
                }


            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bestSell.getVariation().size() == 0) {

                    holder.t2.setText("1");
                    productid = bestSell.getProduct_id();
                    quantity = holder.t2.getText().toString().trim();

                    if (bestSell.getVariation().size() == 0) {

                        btnAddToCart(userid, productid, quantity, "", "");

                    } else {

                        String variId = bestSell.getVar_id();
                        btnAddToCart(userid, productid, quantity, "", variId);
                    }


                    holder.addToCart.setVisibility(View.GONE);
                    holder.showProduct.setVisibility(View.VISIBLE);

                }else{

                    if (holder.spinertext.getText().toString().equals("Select Variation")){

                        Toast.makeText(context, "Select Variation", Toast.LENGTH_SHORT).show();

                    }else {

                        holder.t2.setText("1");
                        productid = bestSell.getProduct_id();
                        quantity = holder.t2.getText().toString().trim();

                        if (bestSell.getVariation().size() == 0) {

                            btnAddToCart(userid, productid, quantity, "", "");

                        } else {

                            String variId = bestSell.getVar_id();
                            btnAddToCart(userid, productid, quantity, "", variId);
                        }


                        holder.addToCart.setVisibility(View.GONE);
                        holder.showProduct.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return sellProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, text_salesPrice, text_Unit, t1, t2, t3, text_Regularprice, rs1, spinertext;
        RelativeLayout showProduct;
        Button addToCart;
        LinearLayout linearLayout;
        RelativeLayout priceRel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            text_salesPrice = itemView.findViewById(R.id.text_salesPrice);
            text_Regularprice = itemView.findViewById(R.id.text_Regularprice);
            //text_Unit = itemView.findViewById (R.id.text_Unit);
            showProduct = itemView.findViewById(R.id.showProduct);
            spinertext = itemView.findViewById(R.id.spinertext);
            addToCart = itemView.findViewById(R.id.addToCart);
            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            rs1 = itemView.findViewById(R.id.rs1);
            priceRel = itemView.findViewById(R.id.priceRel);
        }

        private void linearLayout(Boolean x) {
            int y = Integer.parseInt(t2.getText().toString());
            if (x) {
                y++;
                t2.setText(String.valueOf(y));
            } else {
                y--;
                if (y <= 0) {
                    t2.setText("0");
                } else {
                    t2.setText(String.valueOf(y));
                }
            }
        }
    }

    public void btnAddToCart(String userId, String productId, String quantity, String attribute_id, String variation_id) {

        ProgressDialog progressDialog = new ProgressDialog(context);
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

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                        //String userId = SharedPrefManager.getInstance(context).getUser().getId();
                        CartCountClass cartCountClass = new CartCountClass(context);
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
                Toast.makeText(context, "address Details Not Found", Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void updateToCart(String userId, String productId, String quantity,String attribute_id, String variation_id) {

        ProgressDialog progressDialog = new ProgressDialog(context);
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

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(context, "address Details Not Found", Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
              //      HomePageActivity.text_ItemCount.setText(cart_count);

                    String userId = SharedPrefManager.getInstance(context).getUser().getId();
                    CartCountClass cartCountClass = new CartCountClass(context);
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    public void filter(CharSequence charSequence){

        ArrayList<SerachProductModel> tempArrayList = new ArrayList<SerachProductModel>();

        if(!TextUtils.isEmpty(charSequence)){

            for(SerachProductModel item : sellProduct){

                if(item.getProduct_name().contains((charSequence))){
                    tempArrayList.add(item);
                }
            }

        }else{

            sellProduct.addAll(payment2);
        }

        sellProduct.clear();
        sellProduct.addAll(tempArrayList);
        notifyDataSetChanged();
        tempArrayList.clear();
    }
}
