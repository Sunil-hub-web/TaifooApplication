package com.example.taifooapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import com.example.taifooapplication.adapter.ProductCateGoryAdapter;
import com.example.taifooapplication.modelclas.Category_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityCategoryPage extends AppCompatActivity {

    RecyclerView categoryProductRecycler;
    ArrayList<Category_ModelClass> category = new ArrayList<>();
    ProductCateGoryAdapter productCateGoryAdapter;
    GridLayoutManager gridLayoutManager;
    String CategoryId,CategoryName;
    TextView product_Name;
    ImageView image_Arrow;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoryproduct);

       /* Window window = ActivityCategoryPage.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ActivityCategoryPage.this, R.color.white));*/


        categoryProductRecycler = findViewById(R.id.categoryProductRecycler);
        product_Name = findViewById(R.id.product_Name);
        image_Arrow = findViewById(R.id.image_Arrow);

        Intent intent = getIntent();
        CategoryId = intent.getStringExtra("categoryId");
        CategoryName = intent.getStringExtra("categoryName");

        product_Name.setText(CategoryName);

        getcategoryProduct(CategoryId);

        image_Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ActivityCategoryPage.this,HomePageActivity.class);
                startActivity(intent1);
            }
        });


    }

    public void getcategoryProduct(String categoryId){

        ProgressDialog progressDialog = new ProgressDialog(ActivityCategoryPage.this);
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

                        gridLayoutManager = new GridLayoutManager(ActivityCategoryPage.this,2,GridLayoutManager.VERTICAL,false);
                        productCateGoryAdapter = new ProductCateGoryAdapter(ActivityCategoryPage.this,category);
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
                Toast.makeText(ActivityCategoryPage.this, "No Product Found", Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(ActivityCategoryPage.this);
        requestQueue.add(stringRequest);


    }
}
