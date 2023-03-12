package com.example.taifooapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.taifooapplication.SharedPrefManager;
import com.example.taifooapplication.adapter.MyOrderadapter;
import com.example.taifooapplication.modelclas.MyOrderDetails;
import com.example.taifooapplication.modelclas.MyOrder_ModelClass;
import com.example.taifooapplication.modelclas.OrderSummary_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyOrder extends Fragment {

    RecyclerView myOrderRecyclerView;
    LinearLayoutManager linearLayoutManager;
    MyOrderadapter myOrderDetailsadapter;
    ArrayList<MyOrder_ModelClass> myorder;
    ArrayList<MyOrderDetails> myorderDetails;

    String userId, productName, img, price, weight,addressName,state,city,pincode,address,phoneNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.myorder, container, false);

        myOrderRecyclerView = view.findViewById(R.id.myOrderRecyclerView);

        userId = SharedPrefManager.getInstance(getContext()).getUser().getId();

        getorderDetails(userId);

        return view;
    }

    public void getorderDetails(String userid) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("View Order Details");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getOrderDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    if (message.equals("true")) {

                        myorder = new ArrayList<>();

                        String All_Orders = jsonObject.getString("All_Orders");

                        JSONArray jsonArray_Order = new JSONArray(All_Orders);

                        for (int i = 0; i < jsonArray_Order.length(); i++) {

                            JSONObject jsonObject_Order = jsonArray_Order.getJSONObject(i);

                            String order_id = jsonObject_Order.getString("order_id");
                            String delivery_date = jsonObject_Order.getString("delivery_date");
                            String order_status = jsonObject_Order.getString("order_status");
                            String shipping_charge = jsonObject_Order.getString("shipping_charge");
                            String subtotal = jsonObject_Order.getString("subtotal");
                            String total = jsonObject_Order.getString("total");

                            JSONObject jsonobject_Address = jsonObject_Order.getJSONObject("Address");

                            addressName = jsonobject_Address.getString("name");
                            state = jsonobject_Address.getString("state");
                            city = jsonobject_Address.getString("city");
                            pincode = jsonobject_Address.getString("pincode");
                            address = jsonobject_Address.getString("address");
                            phoneNumber = jsonobject_Address.getString("phone");


                            myorderDetails = new ArrayList<>();

                            String Order_details = jsonObject_Order.getString("Order_details");
                            JSONArray jsonArray_OrderDetails = new JSONArray(Order_details);

                            for (int j = 0; j < jsonArray_OrderDetails.length(); j++) {

                                JSONObject jsonObject_OrderDetails = jsonArray_OrderDetails.getJSONObject(j);

                                productName = jsonObject_OrderDetails.getString("name");
                                img = jsonObject_OrderDetails.getString("img");
                                price = jsonObject_OrderDetails.getString("price");
                                String qty = jsonObject_OrderDetails.getString("qty");
                                weight = jsonObject_OrderDetails.getString("weight");

                                MyOrderDetails myOrderDetails = new MyOrderDetails(
                                        productName, price, weight, img,qty
                                );
                                myorderDetails.add(myOrderDetails);
                            }

                            Log.d("orderdetail", myorderDetails.toString());

                            MyOrder_ModelClass myOrder_modelClass = new MyOrder_ModelClass(
                                    order_id, delivery_date, order_status, myorderDetails, "",
                                    "", "", "",addressName,state,city,pincode,address,phoneNumber,
                                    shipping_charge,subtotal,total
                            );

                            myorder.add(myOrder_modelClass);

                        }

                        Log.d("order", myorder.toString());

                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        myOrderDetailsadapter = new MyOrderadapter(getActivity(), myorder);
                        myOrderRecyclerView.setLayoutManager(linearLayoutManager);
                        myOrderRecyclerView.setHasFixedSize(true);
                        myOrderRecyclerView.setAdapter(myOrderDetailsadapter);

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
                params.put("user_id", userid);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
