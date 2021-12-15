package com.example.taifooapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.taifooapplication.activity.ProductDescription;
import com.example.taifooapplication.adapter.BestSellingAdapter;
import com.example.taifooapplication.adapter.ProductCateGoryAdapter;
import com.example.taifooapplication.modelclas.BestSelling_modelClass;
import com.example.taifooapplication.modelclas.Category_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryPage extends Fragment {

    RecyclerView categoryProductRecycler;
    ArrayList<Category_ModelClass> category = new ArrayList<>();
    ProductCateGoryAdapter productCateGoryAdapter;
    GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.categoryproduct,container,false);


        categoryProductRecycler = view.findViewById(R.id.categoryProductRecycler);

        String categoryId = getTag();

        getcategoryProduct(categoryId);

        return view;
    }

    public void getcategoryProduct(String categoryId){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Show You Product Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getProductCategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    if(message.equals("true")){

                        String All_shop = jsonObject.getString("All_shop");

                        JSONArray jsonArray_Category = new JSONArray(All_shop);
                        for (int i=0;i<jsonArray_Category.length();i++) {

                            JSONObject jsonObject_Category = jsonArray_Category.getJSONObject(i);

                            String quantity = jsonObject_Category.getString("quantity");
                            String product_id = jsonObject_Category.getString("product_id");
                            String product_img = jsonObject_Category.getString("product_img");
                            String product_name = jsonObject_Category.getString("product_name");
                            String product_weight = jsonObject_Category.getString("product_weight");
                            String product_grossweight = jsonObject_Category.getString("product_grossweight");
                            String plate = jsonObject_Category.getString("plate");
                            String regular_price = jsonObject_Category.getString("regular_price");
                            String sale_price = jsonObject_Category.getString("sale_price");

                            Category_ModelClass category_modelClass = new Category_ModelClass(
                                    product_id, product_img, product_name, product_weight, product_grossweight,
                                    plate, regular_price, sale_price, quantity
                            );

                            category.add(category_modelClass);

                        }

                        gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                        productCateGoryAdapter = new ProductCateGoryAdapter(getContext(),category);
                        categoryProductRecycler.setLayoutManager(gridLayoutManager);
                        categoryProductRecycler.setHasFixedSize(true);
                        categoryProductRecycler.setAdapter(productCateGoryAdapter);
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
                Toast.makeText(getContext(), "No Product Found", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("category_id",categoryId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

}
