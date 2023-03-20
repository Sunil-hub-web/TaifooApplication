package com.example.taifooapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentTransaction;
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
import com.example.taifooapplication.adapter.ProductCateGoryAdapter;
import com.example.taifooapplication.fragment.CartCountClass;
import com.example.taifooapplication.fragment.CartPage;
import com.example.taifooapplication.modelclas.Category_ModelClass;
import com.example.taifooapplication.modelclas.VariationDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityCategoryPage extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoryproduct);

       /* Window window = ActivityCategoryPage.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ActivityCategoryPage.this, R.color.white));*/



//        Intent intent = getIntent();
//        CategoryId = intent.getStringExtra("categoryId");
//        CategoryName = intent.getStringExtra("categoryName");
        //cartCount = intent.getStringExtra("cartCount");



/*        image_Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ActivityCategoryPage.this,HomePageActivity.class);
                startActivity(intent1);
            }
        });

        img_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomePageActivity.search.setVisibility(View.GONE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                CartPage cartPage = new CartPage();
                ft.replace(R.id.framLayout, cartPage);
                ft.commit();
                HomePageActivity.text_name.setTextSize(18);
                HomePageActivity.text_name.setText("My Cart");
            }
        });*/

    }



}
