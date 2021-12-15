package com.example.taifooapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.taifooapplication.fragment.AddressDetails;
import com.example.taifooapplication.fragment.CartPage;
import com.example.taifooapplication.fragment.Homepage;
import com.example.taifooapplication.fragment.MyOrder;
import com.example.taifooapplication.fragment.PersonalInformation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    BottomNavigationView bottomNavigation;

    public static TextView nav_MyOrder,text_name,nav_Profile,nav_MyAddress,nav_Home,
            nav_Logout,nav_Name,nav_MobileNo,text_ItemCount;

    public static ImageView loc,logo,search,img_Cart;

    CircleImageView profile_image;

    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double latitude,longitude;
    GoogleMap mMap;
    String name,mobileNo,image,userid,addressDetails;
    Homepage test;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Window window = HomePageActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(HomePageActivity.this, R.color.white));

        userid = SharedPrefManager.getInstance(HomePageActivity.this).getUser().getId();
        getProfileDetails(userid);

        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout,new Homepage(),"HomeFragment").commit();
        test = (Homepage) getSupportFragmentManager().findFragmentByTag("HomeFragment");

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Write Function To enable gps
                locationPermission();

            } else {
                //GPS is already On then
                getLocation();
            }


        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationview);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        loc = findViewById(R.id.loc);
        logo = findViewById(R.id.logo);
        search = findViewById(R.id.image_search);
        img_Cart = findViewById(R.id.img_Cart);
        text_ItemCount = findViewById(R.id.text_ItemCount);

        loc.setVisibility(View.VISIBLE);
        logo.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);

        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        nav_MyOrder = header.findViewById(R.id.nav_MyOrder);
        nav_Profile = header.findViewById(R.id.nav_Profile);
        nav_MyAddress = header.findViewById(R.id.nav_MyAddress);
        nav_Logout = header.findViewById(R.id.nav_Logout);
        profile_image = header.findViewById(R.id.nav_profile_image);
        nav_Name = header.findViewById(R.id.nav_Name);
        nav_MobileNo = header.findViewById(R.id.nav_MobileNo);
        nav_Home = header.findViewById(R.id.nav_Home);
        text_name = findViewById(R.id.text_addressName);


        nav_MyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawer(GravityCompat.START);
                loc.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                MyOrder myOrder = new MyOrder();
                ft.replace(R.id.framLayout, myOrder);
                ft.commit();

                text_name.setText("My Order");
            }
        });

        nav_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawer(GravityCompat.START);
                loc.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                PersonalInformation personalInformation = new PersonalInformation();
                ft.replace(R.id.framLayout, personalInformation);
                ft.commit();

                text_name.setTextSize(18);
                text_name.setText("PersonalInformation");


            }
        });

        nav_MyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawer(GravityCompat.START);
                loc.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                AddressDetails addressDetails = new AddressDetails();
                ft.replace(R.id.framLayout, addressDetails);
                ft.commit();
                text_name.setTextSize(18);
                text_name.setText("Manage Address");

            }
        });

        nav_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance(HomePageActivity.this).logout();
            }
        });

        nav_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(test != null && test.isVisible()){

                    drawerLayout.closeDrawer(GravityCompat.START);

                }else{

                    drawerLayout.closeDrawer(GravityCompat.START);
                    loc.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.VISIBLE);
                    search.setVisibility(View.VISIBLE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Homepage homepage = new Homepage();
                    ft.replace(R.id.framLayout, homepage);
                    ft.commit();
                    text_name.setTextSize(15);
                    text_name.setText(addressDetails);

                }

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePageActivity.this,SerachLocation.class);
                startActivity(intent);

            }
        });

        img_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loc.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                CartPage cartPage = new CartPage();
                ft.replace(R.id.framLayout, cartPage);
                ft.commit();
                text_name.setTextSize(18);
                text_name.setText("My Cart");


            }
        });

        bottomNavigation.setSelectedItemId(R.id.home);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()){

                    case R.id.myAccount:

                        selectedFragment = new PersonalInformation();

                        loc.setVisibility(View.GONE);
                        logo.setVisibility(View.GONE);
                        search.setVisibility(View.GONE);
                        text_name.setTextSize(18);
                        text_name.setText("PersonalInformation");

                        break;

                    case R.id.home:

                        selectedFragment = new Homepage();
                        loc.setVisibility(View.VISIBLE);
                        logo.setVisibility(View.VISIBLE);
                        search.setVisibility(View.VISIBLE);
                        text_name.setTextSize(15);
                        text_name.setText(addressDetails);

                        break;

                    case R.id.cart:

                        selectedFragment = new CartPage();
                        loc.setVisibility(View.GONE);
                        logo.setVisibility(View.GONE);
                        search.setVisibility(View.GONE);
                        text_name.setTextSize(18);
                        text_name.setText("My Cart");

                        break;

                    case R.id.wishlist:

                        return true;


                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framLayout,selectedFragment).commit();

                return true;
            }
        });

        //location();

    }
    public void Clickmenu(View view){

        // open drawer
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout){

        // opendrawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull  MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void locationPermission() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable Your GPS Location").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                //initialize location
                Location location = task.getResult();

                if (location != null) {

                    try {
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(HomePageActivity.this, Locale.getDefault());

                        //initialize AddressList
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        //set Latitude On Text View
                        latitude = addresses.get(0).getLatitude();

                        //set Longitude On Text View
                        longitude = addresses.get(0).getLongitude();

                        addressDetails = addresses.get(0).getSubLocality()+","+addresses.get(0).getLocality();

                        //set address On Text View
                        text_name.setText(addresses.get(0).getSubLocality()+","+addresses.get(0).getLocality());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                //We do not have the permission..
            }
        }
    }


   /* @Override
    protected void onStart() {
        super.onStart();

        if(test != null && test.isVisible()){

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Write Function To enable gps
                locationPermission();

            } else {
                //GPS is already On then
                getLocation();
            }

            String userid = SharedPrefManager.getInstance(HomePageActivity.this).getUser().getId();
            getProfileDetails(userid);
            getLocation();
        }

    }*/

    public void getProfileDetails(String userId) {

        ProgressDialog dialog = new ProgressDialog(HomePageActivity.this);
        dialog.setMessage("Show Profile Details Please Wait....");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getUserDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");
                    String img = jsonObject.getString("img");

                    if (message.equals("true")) {

                        if (img.equals("")) {

                            Toast.makeText(HomePageActivity.this, msg, Toast.LENGTH_SHORT).show();

                            String name = jsonObject.getString("name");
                            String mobile = jsonObject.getString("contact_no");

                            nav_Name.setText(name);
                            nav_MobileNo.setText(mobile);


                        } else {

                            //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                            String name = jsonObject.getString("name");
                            String mobile = jsonObject.getString("contact_no");

                            nav_Name.setText(name);
                            nav_MobileNo.setText(mobile);

                            String image_profile = jsonObject.getString("img");

                            Log.d("image", image_profile);

                            String url = "https://" + image_profile;

                            nav_Name.setText(name);
                            nav_MobileNo.setText(mobile);

                            Picasso.with(HomePageActivity.this).load(url)
                                    .placeholder(R.drawable.profileimage)
                                    .into(profile_image);
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(HomePageActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(HomePageActivity.this);
        requestQueue.add(stringRequest);
    }

    public void location(){

        if(test != null && test.isVisible()){

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Write Function To enable gps
                locationPermission();

            } else {
                //GPS is already On then
                getLocation();
            }

            String userid = SharedPrefManager.getInstance(HomePageActivity.this).getUser().getId();
            getProfileDetails(userid);
            getLocation();
        }
    }
}