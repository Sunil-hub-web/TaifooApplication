package com.example.taifooapplication.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import com.example.taifooapplication.modelclas.CityModelClass;
import com.example.taifooapplication.modelclas.ShowImage_ModelClass;
import com.example.taifooapplication.modelclas.ShowItem_ModelClass;
import com.example.taifooapplication.modelclas.VariationDetails;
import com.google.android.material.badge.BadgeDrawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Homepage extends Fragment {

    TextView text_shopByCategory,text_name;
    ImageView image_search;
    SliderAdpter sliderAdpter;
    ViewPager2 sliderViewPager2;
    TextView[] dots;
    LinearLayout dots_container;
    RecyclerView showitemRecycler, bestsellingRecycler;
    ShowItemAdapter showItemAdapter;
    GridLayoutManager gridLayoutManager, gridLayoutManager1;
    BestSellingAdapter bestSellingAdapter;
    String userId,selectCityDet = "";
    Handler sliderhandler = new Handler();
    ArrayList<ShowImage_ModelClass> imageSlider = new ArrayList<>();
    ArrayList<BestSelling_modelClass> bestselling = new ArrayList<>();
    ArrayList<ShowItem_ModelClass> showItem = new ArrayList<>();
    ArrayList<VariationDetails> variationDetails;
    int currentPossition = 0;
    int arraysize;
    SharedPrefManager sharedPrefManager;
   // AutoCompleteTextView tv_SelectCity;

    ArrayList<CityModelClass> cityModelClasses = new ArrayList<>();
    ArrayList<String> cityModel_Name = new ArrayList<>();
    HashMap<String,String> mapCity = new HashMap<>();

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homepage, container, false);

        sliderViewPager2 = view.findViewById(R.id.sliderViewPager2);
        dots_container = view.findViewById(R.id.dots_container);
        showitemRecycler = view.findViewById(R.id.showitemRecycler);
        text_shopByCategory = view.findViewById(R.id.shopByCategory);
        text_name = view.findViewById(R.id.name);
        bestsellingRecycler = view.findViewById(R.id.bestsellingRecycler);
       // text_ItemCount = view.findViewById(R.id.text_ItemCount);
        //tv_SelectCity = view.findViewById(R.id.tv_SelectCity);

        sharedPrefManager = new SharedPrefManager(getContext());

        sharedpreferences = getContext().getSharedPreferences(mypreference,Context.MODE_PRIVATE);

        String mystring = new String("Shop");
        String text = "by Category";
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        text_shopByCategory.setText(content + " " + text);

        userId = SharedPrefManager.getInstance(getContext()).getUser().getId();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("userId", userId);
        myEdit.apply();
        myEdit.commit();

        getHomePageDetails(userId);

        sliderViewPager2.setClipToPadding(false);
        sliderViewPager2.setClipChildren(false);
        sliderViewPager2.setOffscreenPageLimit(3);
        sliderViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

                float r = 1 - Math.abs(position);
                page.setScaleY(1.0f + r * 0.90f);

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
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onPageSelected(int position) {
                //super.onPageSelected(position);

                if (currentPossition >= arraysize)
                    currentPossition = 0;
                selectedIndicatorPosition(currentPossition++);

                sliderhandler.removeCallbacks(sliderRunable);
                sliderhandler.postDelayed(sliderRunable, 2000);

            }
        });

        getCartCount(userId);

        selectCityDet = sharedpreferences.getString("selectcity","null");

       /* if (selectCityDet.equals("null")){
        }else{
            if (selectCityDet.equals("Select You City")){

            }else{
                tv_SelectCity.setText(selectCityDet);
            }

        }*/

       /* tv_SelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_SelectCity.showDropDown();
            }
        });*/

        /*tv_SelectCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectCityDet = tv_SelectCity.getAdapter().getItem(position).toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("selectcity", selectCityDet);
                editor.commit();

            }
        });*/

        return view;
    }


    public void getHomePageDetails(String userid) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait we are loading for you");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getHomePageDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    String cart_count = "0";
                    String All_banner = jsonObject.getString("All_banner");
                    String All_category = jsonObject.getString("All_category");
                    String Best_sellig = jsonObject.getString("All_products");
                    String All_city = jsonObject.getString("All_city");

                    if (message.equals("true")) {



                        // Retrive Home Page Banner

                        JSONArray jsonArray_banner = new JSONArray(All_banner);

                        for (int i = 0; i < jsonArray_banner.length(); i++) {

                            JSONObject jsonObject_banner = jsonArray_banner.getJSONObject(i);

                            String banner_id = jsonObject_banner.getString("banner_id");
                            String title = jsonObject_banner.getString("title");
                            String image = jsonObject_banner.getString("img");

                            ShowImage_ModelClass showImage_modelClass = new ShowImage_ModelClass(image, banner_id, title);

                            imageSlider.add(showImage_modelClass);
                        }

                        sliderAdpter = new SliderAdpter(getContext(), imageSlider, sliderViewPager2);
                        sliderViewPager2.setAdapter(sliderAdpter);

                        arraysize = imageSlider.size();
                        dots = new TextView[arraysize];
                        dotsIndicator();
                        selectedIndicatorPosition(currentPossition--);

                        //Retrive All_category For Home

                        JSONArray jsonArray_Categary = new JSONArray(All_category);

                        for (int j = 0; j < jsonArray_Categary.length(); j++) {

                            JSONObject jsonObject_Category = jsonArray_Categary.getJSONObject(j);

                            String category_id = jsonObject_Category.getString("category_id");
                            String category_name = jsonObject_Category.getString("cate_name");
                            String category_image = jsonObject_Category.getString("cate_img");

                            String category_name_replace = category_name.replace("&amp;", "");

                            ShowItem_ModelClass showItem_modelClass = new ShowItem_ModelClass(category_image, category_id, category_name_replace);
                            showItem.add(showItem_modelClass);

                        }

                        gridLayoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
                        showItemAdapter = new ShowItemAdapter(getContext(), showItem, cart_count);
                        showitemRecycler.setLayoutManager(gridLayoutManager);
                        showitemRecycler.setHasFixedSize(true);
                        showitemRecycler.setAdapter(showItemAdapter);

                        //Retrive All Best_sellig Product

                        JSONArray jsonArray_BestSellig = new JSONArray(Best_sellig);

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

                            BestSelling_modelClass bestSelling_modelClass = new BestSelling_modelClass(
                                    product_id, product_img, product_name, "", "",
                                    "", regular_price, sale_price, "0", description, variationDetails,
                                    "0","0","0"
                            );

                            bestselling.add(bestSelling_modelClass);

                        }

                        gridLayoutManager1 = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                        bestSellingAdapter = new BestSellingAdapter(getContext(), bestselling, cart_count);
                        bestsellingRecycler.setLayoutManager(gridLayoutManager1);
                        bestsellingRecycler.setHasFixedSize(true);
                        bestsellingRecycler.setNestedScrollingEnabled(false);
                        bestsellingRecycler.setAdapter(bestSellingAdapter);

                        JSONArray jsonArray_city = new JSONArray(All_city);

                        for (int i = 0; i < jsonArray_city.length(); i++) {

                            JSONObject jsonObject_city = jsonArray_city.getJSONObject(i);

                            String city_id = jsonObject_city.getString("city_id");
                            String city_name = jsonObject_city.getString("city_name");

                            CityModelClass cityModelClass = new CityModelClass(city_id,city_name);

                            cityModelClasses.add(cityModelClass);
                            cityModel_Name.add(city_name);
                        }

                        ArrayAdapter godownListAdapter = new ArrayAdapter(
                                getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                cityModel_Name
                        );

                        godownListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // tv_SelectCity.setThreshold(1); //will start working from first character
                      //  tv_SelectCity.setAdapter(godownListAdapter);

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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userid);
                Log.d("parameters",params.toString());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);


    }

    private void dotsIndicator() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(20);
            dots[i].setPadding(5, 0, 5, 0);
            dots[i].setLayoutParams(params);
            dots_container.addView(dots[i]);
        }
    }

    private void selectedIndicatorPosition(int position) {


        for (int i = 0; i < dots.length; i++) {


            if (i == position) {

                dots[i].setTextColor(Color.RED);

            } else {

                dots[i].setTextColor(Color.GREEN);
            }
        }

    }

    public void getCartCount(String userId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.cartCount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    if(msg.equals("success")){

                        String count = jsonObject.getString("count");
                        sharedPrefManager.cartCount(count);
                        //HomePageActivity.text_ItemCount.setText(count);

                        int int_total_cart = Integer.parseInt(count);

                        BadgeDrawable badge = HomePageActivity.bottomNavigation.getOrCreateBadge(R.id.cart);//R.id.action_add is menu id
                        badge.setNumber(int_total_cart);
                        badge.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.some_color));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("user_id",userId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}
