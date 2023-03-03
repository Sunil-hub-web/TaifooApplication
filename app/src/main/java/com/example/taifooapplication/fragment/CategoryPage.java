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
import com.example.taifooapplication.SharedPrefManager;
import com.example.taifooapplication.activity.HomePageActivity;
import com.example.taifooapplication.adapter.ShowItemAdapter;
import com.example.taifooapplication.modelclas.ShowItem_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryPage extends Fragment {

    RecyclerView categoryPageRecycler;
    ArrayList<ShowItem_ModelClass> category = new ArrayList<>();
    ShowItemAdapter showItemAdapter;
    GridLayoutManager gridLayoutManager;
    String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.categorypage,container,false);


        categoryPageRecycler = view.findViewById(R.id.categoryPageRecycler);

        userId = SharedPrefManager.getInstance(getContext()).getUser().getId();

        getHomePageDetails(userId);

        return view;
    }

    public void getHomePageDetails(String userid){

        ProgressDialog progressDialog  = new ProgressDialog(getContext());
        progressDialog.setMessage("Show Your HomePage Details");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getHomePageDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                      String message = jsonObject.getString("success");
                 //   String cart_count = jsonObject.getString("cart_count");
                      String cart_count = "0";
                      String All_category = jsonObject.getString("All_category");

                    if(message.equals("true")){

                       // HomePageActivity.text_ItemCount.setText(cart_count);

                        //Retrive All_category For Home

                        JSONArray jsonArray_Categary = new JSONArray(All_category);
                        for(int j=0;j<jsonArray_Categary.length();j++){

                            JSONObject jsonObject_Category = jsonArray_Categary.getJSONObject(j);

                            String category_id = jsonObject_Category.getString("category_id");
                            String category_name = jsonObject_Category.getString("cate_name");
                            String category_image = jsonObject_Category.getString("cate_img");

                            ShowItem_ModelClass showItem_modelClass = new ShowItem_ModelClass(category_image,category_id,category_name);
                            category.add(showItem_modelClass);

                        }

                        gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                        showItemAdapter = new ShowItemAdapter(getContext(),category, cart_count);
                        categoryPageRecycler.setLayoutManager(gridLayoutManager);
                        categoryPageRecycler.setHasFixedSize(true);
                        categoryPageRecycler.setAdapter(showItemAdapter);

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
                Toast.makeText(getContext(), "Some Error Found Data Not Featch", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("user_id",userid);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);


    }

}
