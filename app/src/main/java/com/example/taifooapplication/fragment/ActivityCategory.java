package com.example.taifooapplication.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.taifooapplication.SharedPrefManager;
import com.example.taifooapplication.activity.ActivityCategoryPage;
import com.example.taifooapplication.activity.HomePageActivity;
import com.example.taifooapplication.adapter.ProductCateGoryAdapter;
import com.example.taifooapplication.modelclas.Category_ModelClass;
import com.example.taifooapplication.modelclas.VariationDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityCategory extends Fragment {

    RecyclerView categoryProductRecycler;
    ArrayList<Category_ModelClass> category = new ArrayList<>();
    ProductCateGoryAdapter productCateGoryAdapter;
    GridLayoutManager gridLayoutManager;
    String CategoryId, CategoryName;
    TextView product_Name, text_ItemCount;
    // ImageView image_Arrow,img_Cart;
    ArrayList<VariationDetails> variationDetails;

    SharedPrefManager sharedPrefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.categoryproduct, container, false);

        sharedPrefManager = new SharedPrefManager(getContext());

        categoryProductRecycler = view.findViewById(R.id.categoryProductRecycler);
        // product_Name = view.findViewById(R.id.product_Name);
        // image_Arrow = view.findViewById(R.id.image_Arrow);
        // text_ItemCount = view.findViewById(R.id.text_ItemCount);
        //img_Cart = view.findViewById(R.id.img_Cart);

        Bundle arguments = getArguments();


        if (arguments != null) {

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPrefdet", getContext().MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("CategoryId", arguments.get("categoryId").toString());
            myEdit.putString("CategoryName", arguments.get("categoryName").toString());
            myEdit.apply();

            CategoryId = arguments.get("categoryId").toString();
            CategoryName = arguments.get("categoryName").toString();

            Log.d("categoryName", CategoryId + CategoryName);

            HomePageActivity.text_name.setText(CategoryName);
            //text_ItemCount.setText(cartCount);

            getcategoryProduct(CategoryId);

            String userId = SharedPrefManager.getInstance(getContext()).getUser().getId();
            getCartCount(userId);

        } else {

            SharedPreferences sh = getContext().getSharedPreferences("MySharedPrefdet", getContext().MODE_PRIVATE);
            CategoryId = sh.getString("CategoryId", "");
            CategoryName = sh.getString("CategoryName", "");

            /*CategoryId = arguments.get("categoryId").toString();
            CategoryName = arguments.get("categoryName").toString();*/

            Log.d("categoryName", CategoryId + CategoryName);

            HomePageActivity.text_name.setText(CategoryName);
            //text_ItemCount.setText(cartCount);

            getcategoryProduct(CategoryId);

            String userId = SharedPrefManager.getInstance(getContext()).getUser().getId();
            getCartCount(userId);
        }

        return view;
    }

    public void getcategoryProduct(String categoryId) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Show You Product Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getProductCategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                category.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        String All_Products = jsonObject.getString("All_Products");

                        JSONArray jsonArray_Category = new JSONArray(All_Products);

                        if (jsonArray_Category.length() != 0) {

                            for (int i = 0; i < jsonArray_Category.length(); i++) {

                                JSONObject jsonObject_Category = jsonArray_Category.getJSONObject(i);

                                // String quantity = jsonObject_BestSellig.getString("quantity");
                                String product_id = jsonObject_Category.getString("products_id");
                                String product_name = jsonObject_Category.getString("name");
                                String product_img = jsonObject_Category.getString("image");
                                //String product_weight = jsonObject_BestSellig.getString("product_weight");
                                //String product_grossweight = jsonObject_BestSellig.getString("product_grossweight");
                                //String plate = jsonObject_BestSellig.getString("plate");
                                String description = jsonObject_Category.getString("description");
                                String regular_price = jsonObject_Category.getString("regular_price");
                                String sale_price = jsonObject_Category.getString("sales_price");
                                String variations = jsonObject_Category.getString("variations");

                                variationDetails = new ArrayList<>();

                                JSONArray jsonArray_variat = new JSONArray(variations);

                                if (jsonArray_variat.length() == 0) {

                                } else {
                                    for (int l = 0; l < jsonArray_variat.length(); l++) {

                                        JSONObject jsonObject_vari = jsonArray_variat.getJSONObject(l);

                                        String price_id = jsonObject_vari.getString("price_id");
                                        String price = jsonObject_vari.getString("price");
                                        String varations = jsonObject_vari.getString("varations");

                                        VariationDetails variationDetails1 = new VariationDetails(price_id, price, varations);
                                        variationDetails.add(variationDetails1);

                                    }
                                }

                                Category_ModelClass category_modelClass = new Category_ModelClass(
                                        product_id, product_img, product_name, "", "",
                                        "", regular_price, sale_price, "0", description, variationDetails,
                                        "0", "0", "0"
                                );

                                category.add(category_modelClass);

                            }

                            gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                            productCateGoryAdapter = new ProductCateGoryAdapter(getContext(), category, CategoryName);
                            categoryProductRecycler.setLayoutManager(gridLayoutManager);
                            categoryProductRecycler.setHasFixedSize(true);
                            categoryProductRecycler.setAdapter(productCateGoryAdapter);

                        } else {

                            Toast.makeText(getContext(), "Product is Not Available", Toast.LENGTH_SHORT).show();
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
                error.printStackTrace();
                Toast.makeText(getContext(), "No Product Found", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("sub_cate_id", categoryId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

    public void getCartCount(String userId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.cartCount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    if (msg.equals("success")) {

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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}
