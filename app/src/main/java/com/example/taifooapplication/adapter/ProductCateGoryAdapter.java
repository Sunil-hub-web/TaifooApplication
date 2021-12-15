package com.example.taifooapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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
import com.example.taifooapplication.modelclas.BestSelling_modelClass;
import com.example.taifooapplication.modelclas.Category_ModelClass;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductCateGoryAdapter extends RecyclerView.Adapter<ProductCateGoryAdapter.ViewHolder> {

    Context context;
    ArrayList<Category_ModelClass> category;
    int count_value;
    String countvalue,userid,productid,quantity;

    public ProductCateGoryAdapter(Context context, ArrayList<Category_ModelClass> category) {

        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public ProductCateGoryAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryproductdetails,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCateGoryAdapter.ViewHolder holder, int position) {

        Category_ModelClass productCategory = category.get (position);

        String image = "https://"+productCategory.getProduct_img();
        Picasso.with(context).load(image).into(holder.productImage);

        holder.productName.setText(productCategory.getProduct_name());
        holder.text_NetWt.setText(productCategory.getProduct_weight());
        holder.text_GrossWt.setText(productCategory.getProduct_grossweight());
        holder.text_salesPrice.setText(productCategory.getSale_price());
        holder.text_Unit.setText(productCategory.getProduct_weight());

        String Regular_price = productCategory.getRegular_price();

        /*String tt_2 = "Rs.";

        SpannableString Regular_price_ss = new SpannableString(Regular_price);
        SpannableString ss1 = new SpannableString(tt_2);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

        Regular_price_ss.setSpan(strikethroughSpan,0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(strikethroughSpan,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
*/
        holder.text_Regularprice.setText(Regular_price);
        holder.rs1.setText("Rs.");

        /*String str_quantity_item = productCategory.getQuantity();

        int quantity_item = Integer.valueOf(str_quantity_item);


        if( quantity_item != 0){

            holder.btn_addToCart.setVisibility(View.GONE);
        }*/


        userid = SharedPrefManager.getInstance(context).getUser().getId();

        holder.btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity = holder.t2.getText().toString().trim();
                productid = productCategory.getProduct_id();

                btnAddToCart(userid,productid,quantity);

            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(false);

                quantity = holder.t2.getText().toString().trim();
                count_value = Integer.valueOf(holder.t2.getText().toString());

                countvalue = String.valueOf(count_value);

                productid = productCategory.getProduct_id();

                updateToCart(userid,productid,quantity);

            }
        });

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(true);

                quantity = holder.t2.getText().toString().trim();

                productid = productCategory.getProduct_id();

                updateToCart(userid,productid,quantity);


            }
        });

    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName,text_NetWt,text_GrossWt,text_salesPrice,text_Regularprice,text_Unit,rs,rs1,t1, t2, t3;
        Button btn_addToCart;
        LinearLayout linearLayout;
        RelativeLayout showProduct;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            text_NetWt = itemView.findViewById(R.id.text_NetWt);
            text_GrossWt = itemView.findViewById(R.id.text_GrossWt);
            text_salesPrice = itemView.findViewById(R.id.text_salesPrice);
            text_Regularprice = itemView.findViewById(R.id.text_Regularprice);
            text_Unit = itemView.findViewById(R.id.text_Unit);
            rs = itemView.findViewById(R.id.rs);
            rs1 = itemView.findViewById(R.id.rs1);
            btn_addToCart = itemView.findViewById(R.id.btn_addToCart);
            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            showProduct = itemView.findViewById(R.id.showProduct);

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
                Toast.makeText(context, "cart not add success", Toast.LENGTH_SHORT).show();

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
}
