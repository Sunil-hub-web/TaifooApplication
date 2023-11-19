package com.example.taifooapplication.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
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
import com.example.taifooapplication.fragment.CartCountClass;
import com.example.taifooapplication.fragment.CartPage;
import com.example.taifooapplication.modelclas.CartPage_ModelClass;
import com.example.taifooapplication.modelclas.ShappingCharges_ModelClass;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartPageAdapter extends RecyclerView.Adapter<CartPageAdapter.ViewHolder> {

    Context context;
    ArrayList<CartPage_ModelClass> itemlist;
    String productid, userid1, price_total, total_price, str_SumTotal, str_Shpping, str_TotalPrice, sum, tax;
    int Total, price, quantity, salesPrice;
    Double d_totPrice, d_Sum, sumOfTotal, d_ShppingCharges, d_TotlAmount, taxCharge;

    public CartPageAdapter(FragmentActivity activity, ArrayList<CartPage_ModelClass> itemList, String userid) {

        this.context = activity;
        this.itemlist = itemList;
        this.userid1 = userid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartpagedetails, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CartPage_ModelClass cartItem = itemlist.get(position);

        String price1 = cartItem.getSale_price();
        String quantity1 = cartItem.getQuantity();

        price = Integer.parseInt(price1);
        quantity = Integer.parseInt(quantity1);

        Total = price * quantity;

        price_total = String.valueOf(Total);

        holder.totalPrice.setText(price_total);

        String image = cartItem.getProduct_img();
        Picasso.with(context).load(image).into(holder.productImage);

        holder.product_Name.setText(cartItem.getProduct_name());
        holder.unit_Name.setText("Q :" + cartItem.getQuantity());
        holder.t2.setText(cartItem.getQuantity());

        //  userid = SharedPrefManager.getInstance(context).getUser().getId();

        //SharedPreferences sh = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
        //userid = sh.getString("userId", "");

        holder.img_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productid = cartItem.getProduct_id();

                deleteCartItem(userid1, productid);
                itemlist.remove(position);
                notifyDataSetChanged();

                int arraySize = itemlist.size();
                String str_ArraySize = String.valueOf(arraySize);
                //CartPage.text_ItemCount.setText(str_ArraySize);

                sum = CartPage.text_subTotalPrice.getText().toString().trim();

                String totPrice = holder.totalPrice.getText().toString().trim();

                double d_sumTotal = Double.valueOf(totPrice);
                d_Sum = Double.valueOf(sum);

                double d_deletePrice = d_Sum - d_sumTotal;

                String str_DeletePrice = String.valueOf(d_deletePrice);

                CartPage.text_subTotalPrice.setText(str_DeletePrice);

                str_Shpping = CartPage.text_deliveryPrice.getText().toString().trim();

                d_ShppingCharges = Double.valueOf(str_Shpping);

                tax = CartPage.text_taxandfee.getText().toString().trim();
                taxCharge = Double.valueOf(tax);

                d_TotlAmount = d_deletePrice + d_ShppingCharges + taxCharge;

                str_TotalPrice = String.valueOf(d_TotlAmount);

                CartPage.text_totalPrice.setText(str_TotalPrice);

                CartPage.text_totalPrice.setText(str_TotalPrice);

                if (itemlist.size() == 0) {

                    CartPage.cartempty.setVisibility(View.VISIBLE);
                    CartPage.lin_amount.setVisibility(View.GONE);
                    CartPage.recyclerCartPage.setVisibility(View.GONE);
                    CartPage.rel_totalprice.setVisibility(View.GONE);
                }

            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.linearLayout(false);

                String productid = cartItem.getProduct_id();
                String quantity = holder.t2.getText().toString().trim();

                if (quantity.equals("0")) {

                    Toast.makeText(context, "Do You want To delete Please click Delete", Toast.LENGTH_SHORT).show();

                } else {

                    String price1 = holder.totalPrice.getText().toString().trim();
                    String price = cartItem.getSale_price();
                    int qun = Integer.valueOf(quantity);
                    int tot = Integer.valueOf(price);
                    int total = qun * tot;
                    String tot_al = String.valueOf(total);
                    holder.totalPrice.setText(tot_al);

                    total_price = holder.totalPrice.getText().toString().trim();

                    salesPrice = Integer.valueOf(price);

                    holder.unit_Name.setText("Q :" + quantity);

                    sum = CartPage.text_subTotalPrice.getText().toString().trim();
                    d_totPrice = Double.valueOf(price);
                    d_Sum = Double.valueOf(sum);
                    sumOfTotal = d_Sum - d_totPrice;
                    str_SumTotal = String.valueOf(sumOfTotal);

                    CartPage.text_subTotalPrice.setText(str_SumTotal);

                    ShappingCharges_ModelClass shipping_data = new ShappingCharges_ModelClass();

                   // String deliveryprice = shipping_data.getDeliveryPrice();
                   // String str_pricedetails = shipping_data.getPrice();

                    double d_deliveryprice = Double.parseDouble(CartPage.deliveryprice);
                    double d_pricedetails = Double.parseDouble(CartPage.deliveryamount);

                    if (sumOfTotal < d_deliveryprice){

                        CartPage.text_deliveryPrice.setText(String.valueOf(d_pricedetails));

                        str_Shpping = CartPage.text_deliveryPrice.getText().toString().trim();

                        d_ShppingCharges = Double.valueOf(str_Shpping);

                        tax = CartPage.text_taxandfee.getText().toString().trim();
                        taxCharge = Double.valueOf(tax);

                        d_TotlAmount = sumOfTotal + 20 + taxCharge;

                        str_TotalPrice = String.valueOf(d_TotlAmount);

                        CartPage.text_totalPrice.setText(str_TotalPrice);

                        updateToCart(userid1, productid, quantity);

                        //  holder.unit_Name.setText("Q :"+quantity);
                    }else{

                        CartPage.text_deliveryPrice.setText(String.valueOf(d_pricedetails));

                        str_Shpping = CartPage.text_deliveryPrice.getText().toString().trim();

                        d_ShppingCharges = Double.valueOf(str_Shpping);

                        tax = CartPage.text_taxandfee.getText().toString().trim();
                        taxCharge = Double.valueOf(tax);

                        d_TotlAmount = sumOfTotal + 0 + taxCharge;

                        str_TotalPrice = String.valueOf(d_TotlAmount);

                        CartPage.text_totalPrice.setText(str_TotalPrice);

                        updateToCart(userid1, productid, quantity);
                    }





                }


            }
        });

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.linearLayout(true);

                String productid = cartItem.getProduct_id();
                String quantity = holder.t2.getText().toString().trim();

                holder.unit_Name.setText("Q :" + quantity);

                String price1 = holder.totalPrice.getText().toString().trim();
                String price = cartItem.getSale_price();

                int qun = Integer.valueOf(quantity);
                int tot = Integer.valueOf(price);
                int total = qun * tot;
                String tot_al = String.valueOf(total);
                holder.totalPrice.setText(tot_al);

                total_price = holder.totalPrice.getText().toString().trim();

                salesPrice = Integer.valueOf(price);

                sum = CartPage.text_subTotalPrice.getText().toString().trim();
                d_totPrice = Double.valueOf(price);
                d_Sum = Double.valueOf(sum);
                sumOfTotal = d_Sum + d_totPrice;
                str_SumTotal = String.valueOf(sumOfTotal);

                CartPage.text_subTotalPrice.setText(str_SumTotal);

                ShappingCharges_ModelClass shipping_data = new ShappingCharges_ModelClass();

              //  String deliveryprice = shipping_data.getDeliveryPrice();
              //  String str_pricedetails = shipping_data.getPrice();

                double d_deliveryprice = Double.parseDouble(CartPage.deliveryprice);
                double d_pricedetails = Double.parseDouble(CartPage.deliveryamount);

                if (sumOfTotal < d_deliveryprice){

                    CartPage.text_deliveryPrice.setText(String.valueOf("20"));


                    str_Shpping = CartPage.text_deliveryPrice.getText().toString().trim();

                    d_ShppingCharges = Double.valueOf(str_Shpping);

                    tax = CartPage.text_taxandfee.getText().toString().trim();
                    taxCharge = Double.valueOf(tax);

                    d_TotlAmount = sumOfTotal + d_pricedetails + taxCharge;

                    str_TotalPrice = String.valueOf(d_TotlAmount);

                    CartPage.text_totalPrice.setText(str_TotalPrice);

                    updateToCart(userid1, productid, quantity);

                }else{

                    CartPage.text_deliveryPrice.setText(String.valueOf("0"));

                    str_Shpping = CartPage.text_deliveryPrice.getText().toString().trim();

                    d_ShppingCharges = Double.valueOf(str_Shpping);

                    tax = CartPage.text_taxandfee.getText().toString().trim();
                    taxCharge = Double.valueOf(tax);

                    d_TotlAmount = sumOfTotal + 0 + taxCharge;

                    str_TotalPrice = String.valueOf(d_TotlAmount);

                    CartPage.text_totalPrice.setText(str_TotalPrice);

                    updateToCart(userid1, productid, quantity);

                }



            }
        });

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView totalPrice, product_Name, unit_Name, t1, t2, t3;
        ImageView productImage;
        LinearLayout linearLayout;
        RelativeLayout img_Delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            product_Name = itemView.findViewById(R.id.product_Name);
            unit_Name = itemView.findViewById(R.id.unit_Name);
            img_Delete = itemView.findViewById(R.id.img_Delete);

            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
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
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void deleteCartItem(String userId, String productId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.deleteFormCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    // String cart_count = jsonObject.getString("cart_count");
                    // HomePageActivity.text_ItemCount.setText(cart_count);

                    if (message.equals("true")) {

                        String msg = jsonObject.getString("msg");
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                        /*SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
                        userId = sh.getString("userId", "");*/

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

    public void updateToCart(String userId, String productId, String quantity) {

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

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
