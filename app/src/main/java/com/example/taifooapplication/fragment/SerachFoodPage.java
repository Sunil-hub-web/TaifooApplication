package com.example.taifooapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.taifooapplication.AppURL;
import com.example.taifooapplication.R;
import com.example.taifooapplication.adapter.SerachAdapter;
import com.example.taifooapplication.modelclas.SerachProductModel;
import com.example.taifooapplication.modelclas.VariationDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SerachFoodPage extends Fragment {

    SerachAdapter serachAdapter;
    GridLayoutManager gridLayoutManager1;
    ArrayList<VariationDetails> variationDetails;
    ArrayList<SerachProductModel> serachProductModels = new ArrayList<>();
    RecyclerView recyclerSerach;
    EditText serachProduct;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.serachpage,container,false);

        recyclerSerach = view.findViewById(R.id.recyclerSerach);
        serachProduct = view.findViewById(R.id.serachProduct);

        singleProduct();

        serachProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (serachProduct.getText().toString().trim().equals("")){

                    singleProduct();

                }else{

                    serachAdapter.filter(s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    public void singleProduct(){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Show You Product Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.searchProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String product = jsonObject.getString("All_Products");
                    JSONArray jsonArray_BestSellig = new JSONArray(product);

                    for (int k = 0; k < jsonArray_BestSellig.length(); k++) {

                        JSONObject jsonObject_BestSellig = jsonArray_BestSellig.getJSONObject(k);

                        // String quantity = jsonObject_BestSellig.getString("quantity");
                        String product_id = jsonObject_BestSellig.getString("products_id");
                        String product_name = jsonObject_BestSellig.getString("name");
                        String product_img = jsonObject_BestSellig.getString("image");
                        //String product_weight = jsonObject_BestSellig.getString("product_weight");
                        //String product_grossweight = jsonObject_BestSellig.getString("product_grossweight");
                        //String plate = jsonObject_BestSellig.getString("plate");
                        String description = jsonObject_BestSellig.getString("description");
                        String regular_price = jsonObject_BestSellig.getString("regular_price");
                        String sale_price = jsonObject_BestSellig.getString("sales_price");
                        String variations = jsonObject_BestSellig.getString("variations");

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

                        SerachProductModel selling_modelClass = new SerachProductModel(
                                product_id, product_img, product_name, "", "",
                                "", regular_price, sale_price, "0", description, variationDetails,
                                "0","0","0"
                        );

                        serachProductModels.add(selling_modelClass);

                    }

                    gridLayoutManager1 = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                    serachAdapter = new SerachAdapter(getContext(), serachProductModels);
                    recyclerSerach.setLayoutManager(gridLayoutManager1);
                    recyclerSerach.setHasFixedSize(true);
                    recyclerSerach.setNestedScrollingEnabled(false);
                    recyclerSerach.setAdapter(serachAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
