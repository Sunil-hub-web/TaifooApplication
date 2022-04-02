package com.example.taifooapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.taifooapplication.SharedPrefManager;
import com.example.taifooapplication.activity.HomePageActivity;
import com.example.taifooapplication.activity.ProductDescription;
import com.example.taifooapplication.modelclas.BestSelling_modelClass;
import com.example.taifooapplication.modelclas.ShowItem_ModelClass;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BestSellingAdapter extends RecyclerView.Adapter<BestSellingAdapter.ViewHolder> {

    Context context;
    ArrayList<BestSelling_modelClass> sellProduct;
    int count_value;
    String countvalue,userid,productid,quantity,cartCount;

    public BestSellingAdapter(Context context, ArrayList<BestSelling_modelClass> showItem, String cart_count) {

        this.context = context;
        this.sellProduct = showItem;
        this.cartCount = cart_count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ( )).inflate (R.layout.homepageproduct,parent,false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        BestSelling_modelClass bestSell = sellProduct.get (position);

        String image = "https://"+bestSell.getProduct_img();
        Picasso.with(context).load(image).into(holder.productImage);

        holder.productName.setText (bestSell.getProduct_name ());
        holder.text_salesPrice.setText (bestSell.getSale_price ());
        holder.text_Regularprice.setText (bestSell.getRegular_price ());
        //holder.text_Unit.setText (bestSell.getProduct_weight ());

        String str_quantity_item = bestSell.getQuantity();

        holder.text_Regularprice.setPaintFlags(holder.text_Regularprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //holder.rs1.setPaintFlags(holder.rs1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        int quantity_item = Integer.valueOf(str_quantity_item);

        if(quantity_item != 0){

            holder.addToCart.setVisibility(View.GONE);
            holder.showProduct.setVisibility(View.VISIBLE);

        }else{

            holder.addToCart.setVisibility(View.VISIBLE);
            holder.showProduct.setVisibility(View.GONE);
        }

        userid = SharedPrefManager.getInstance(context).getUser().getId();

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.showProduct.setVisibility (View.VISIBLE);
                holder.addToCart.setVisibility (View.GONE);
            }
        });

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity = holder.t2.getText().toString().trim();

                Intent intent = new Intent(context, ProductDescription.class);
                intent.putExtra("productName",bestSell.getProduct_name ());
                intent.putExtra("productprice",bestSell.getSale_price ());
                //intent.putExtra("text_Unit",bestSell.getProduct_weight());
                intent.putExtra("quantity",quantity);
                intent.putExtra("Regular_price",bestSell.getRegular_price ());
                intent.putExtra("productImage",bestSell.getProduct_img ());
                intent.putExtra("productId",bestSell.getProduct_id ());
                intent.putExtra("cartCount",cartCount);

                context.startActivity(intent);

            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(false);

                quantity = holder.t2.getText().toString().trim();
                //-

                String producy_id = bestSell.getProduct_id();

                if(quantity.equals("0")){

                    deleteCartItem(userid,producy_id);

                    holder.showProduct.setVisibility (View.GONE);
                    holder.addToCart.setVisibility (View.VISIBLE);

                }else{

                    holder.showProduct.setVisibility (View.VISIBLE);
                    holder.addToCart.setVisibility (View.GONE);

                    quantity = holder.t2.getText().toString().trim();
                    count_value = Integer.valueOf(holder.t2.getText().toString());

                    countvalue = String.valueOf(count_value);

                    productid = bestSell.getProduct_id();

                    updateToCart(userid,productid,quantity);

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
                updateToCart(userid,productid,quantity);


            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.t2.setText("1");
                productid = bestSell.getProduct_id();
                quantity = holder.t2.getText().toString().trim();

                btnAddToCart(userid,productid,quantity);
                holder.addToCart.setVisibility (View.GONE);
                holder.showProduct.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sellProduct.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName,text_salesPrice,text_Unit,t1, t2, t3,text_Regularprice,rs1;
        RelativeLayout showProduct;
        Button addToCart;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull  View itemView) {
            super (itemView);

            productImage = itemView.findViewById (R.id.productImage);
            productName = itemView.findViewById (R.id.productName);
            text_salesPrice = itemView.findViewById (R.id.text_salesPrice);
            text_Regularprice = itemView.findViewById (R.id.text_Regularprice);
            //text_Unit = itemView.findViewById (R.id.text_Unit);
            showProduct = itemView.findViewById (R.id.showProduct);
            addToCart = itemView.findViewById (R.id.addToCart);
            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            rs1 = itemView.findViewById(R.id.rs1);

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

    public void btnAddToCart(String userId,String productId,String quantity){

        ProgressDialog progressDialog = new ProgressDialog(context);
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
                error.printStackTrace ();
                Toast.makeText(context, "address Details Not Found", Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void updateToCart(String userId,String productId,String quantity){

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Updte Cart SuccessFully");
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
                error.printStackTrace ();
                Toast.makeText(context, "address Details Not Found", Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void deleteCartItem(String userId,String productId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.deleteFormCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //String message = jsonObject.getString("success");
                    String cart_count = jsonObject.getString("cart_count");
                    HomePageActivity.text_ItemCount.setText(cart_count);

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("user_id",userId);
                params.put("product_id",productId);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

}
