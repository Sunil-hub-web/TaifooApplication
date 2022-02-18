package com.example.taifooapplication.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

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
import com.example.taifooapplication.activity.MainActivity;
import com.example.taifooapplication.adapter.BestSellingAdapter;
import com.example.taifooapplication.adapter.ShowItemAdapter;
import com.example.taifooapplication.adapter.SliderAdpter;
import com.example.taifooapplication.modelclas.BestSelling_modelClass;
import com.example.taifooapplication.modelclas.ShowImage_ModelClass;
import com.example.taifooapplication.modelclas.ShowItem_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Homepage extends Fragment {

    TextView text_name,text_shopByCategory,text_ItemCount;
    SliderAdpter sliderAdpter;
    ViewPager2 sliderViewPager2;
    TextView [] dots;
    LinearLayout dots_container;
    RecyclerView showitemRecycler,bestsellingRecycler;
    ShowItemAdapter showItemAdapter;
    GridLayoutManager gridLayoutManager,gridLayoutManager1;
    BestSellingAdapter bestSellingAdapter;

     String userId;
    Handler sliderhandler = new Handler();

    ArrayList<ShowImage_ModelClass> imageSlider = new ArrayList<>();
    ArrayList<BestSelling_modelClass> bestselling = new ArrayList<>();
    ArrayList<ShowItem_ModelClass> showItem = new ArrayList<>();

    int currentPossition = 0;
    int arraysize;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homepage,container,false);

        sliderViewPager2 = view.findViewById(R.id.sliderViewPager2);
        dots_container = view.findViewById(R.id.dots_container);
        showitemRecycler = view.findViewById(R.id.showitemRecycler);
        text_shopByCategory = view.findViewById(R.id.shopByCategory);
        text_name = view.findViewById(R.id.name);
        bestsellingRecycler = view.findViewById(R.id.bestsellingRecycler);
        //text_ItemCount = view.findViewById(R.id.text_ItemCount);


        String mystring=new String("Shop");
        String text = "by Category";
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        text_shopByCategory.setText(content+" "+text);

        userId = SharedPrefManager.getInstance(getContext()).getUser().getId();

        getHomePageDetails(userId);

        sliderViewPager2.setClipToPadding(false);
        sliderViewPager2.setClipChildren(false);
        sliderViewPager2.setOffscreenPageLimit(3);
        sliderViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull  View page, float position) {

                float r = 1 - Math.abs(position);
                page.setScaleY(1.0f +  r * 0.90f);

            }
        });

        sliderViewPager2.setPageTransformer(compositePageTransformer);

        Runnable sliderRunable = new Runnable() {
            @Override
            public void run() {

                sliderViewPager2.setCurrentItem(sliderViewPager2.getCurrentItem() + 1);
            }
        };

        sliderViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                //super.onPageSelected(position);

                if (currentPossition >= arraysize)
                    currentPossition = 0;
                    selectedIndicatorPosition (currentPossition++);

                sliderhandler.removeCallbacks(sliderRunable);
                sliderhandler.postDelayed(sliderRunable,2000);

            }
        });


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
                    String cart_count = jsonObject.getString("cart_count");
                    String All_banner = jsonObject.getString("All_banner");
                    String All_category = jsonObject.getString("All_category");
                    String Best_sellig = jsonObject.getString("Best_sellig");

                    if(message.equals("true")){

                        HomePageActivity.text_ItemCount.setText(cart_count);

                        // Retrive Home Page Banner

                        JSONArray jsonArray_banner = new JSONArray(All_banner);

                        for (int i=0;i<jsonArray_banner.length();i++){

                            JSONObject jsonObject_banner = jsonArray_banner.getJSONObject(i);

                            String banner_id = jsonObject_banner.getString("banner_id");
                            String title = jsonObject_banner.getString("title");
                            String image = jsonObject_banner.getString("img");

                            ShowImage_ModelClass showImage_modelClass = new ShowImage_ModelClass(image,banner_id,title);

                            imageSlider.add(showImage_modelClass);
                        }

                        sliderAdpter = new SliderAdpter(getContext(),imageSlider,sliderViewPager2);
                        sliderViewPager2.setAdapter(sliderAdpter);

                        arraysize = imageSlider.size();
                        dots = new TextView[arraysize];
                        dotsIndicator();
                        selectedIndicatorPosition(currentPossition);

                        //Retrive All_category For Home

                        JSONArray jsonArray_Categary = new JSONArray(All_category);
                        for(int j=0;j<jsonArray_Categary.length();j++){

                            JSONObject jsonObject_Category = jsonArray_Categary.getJSONObject(j);

                            String category_id = jsonObject_Category.getString("category_id");
                            String category_name = jsonObject_Category.getString("category_name");
                            String category_image = jsonObject_Category.getString("img");

                            String category_name_replace = category_name.replace("&amp;","");

                            ShowItem_ModelClass showItem_modelClass = new ShowItem_ModelClass(category_image,category_id,category_name_replace);
                            showItem.add(showItem_modelClass);

                        }

                        gridLayoutManager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
                        showItemAdapter = new ShowItemAdapter(getContext(),showItem,cart_count);
                        showitemRecycler.setLayoutManager(gridLayoutManager);
                        showitemRecycler.setHasFixedSize(true);
                        showitemRecycler.setAdapter(showItemAdapter);

                        //Retrive All Best_sellig Product

                        JSONArray jsonArray_BestSellig = new JSONArray(Best_sellig);

                        for (int k=0;k<jsonArray_BestSellig.length();k++){

                            JSONObject jsonObject_BestSellig = jsonArray_BestSellig.getJSONObject(k);

                            String quantity = jsonObject_BestSellig.getString("quantity");
                            String product_id = jsonObject_BestSellig.getString("product_id");
                            String product_img = jsonObject_BestSellig.getString("product_img");
                            String product_name = jsonObject_BestSellig.getString("product_name");
                            String product_weight = jsonObject_BestSellig.getString("product_weight");
                            String product_grossweight = jsonObject_BestSellig.getString("product_grossweight");
                            String plate = jsonObject_BestSellig.getString("plate");
                            String regular_price = jsonObject_BestSellig.getString("regular_price");
                            String sale_price = jsonObject_BestSellig.getString("sale_price");

                            BestSelling_modelClass bestSelling_modelClass = new BestSelling_modelClass(
                                    product_id,product_img,product_name,product_weight,product_grossweight,
                                    plate,regular_price,sale_price,quantity
                            );

                            bestselling.add(bestSelling_modelClass);

                        }

                        gridLayoutManager1 = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                        bestSellingAdapter = new BestSellingAdapter (getContext(),bestselling,cart_count);
                        bestsellingRecycler.setLayoutManager(gridLayoutManager1);
                        bestsellingRecycler.setHasFixedSize(true);
                        bestsellingRecycler.setNestedScrollingEnabled(false);
                        bestsellingRecycler.setAdapter(bestSellingAdapter);

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

    private void dotsIndicator() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);

        for(int i=0;i<dots.length;i++){

            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(20);
            dots[i].setPadding(5, 0, 5, 0);
            dots[i].setLayoutParams(params);
            dots_container.addView(dots[i]);
        }
    }

    private void selectedIndicatorPosition(int position) {


        for(int i=0;i<dots.length;i++){


            if(i == position){

                dots[i].setTextColor(Color.RED);

            }else{

                dots[i].setTextColor(Color.GREEN);
            }
        }

    }

}
