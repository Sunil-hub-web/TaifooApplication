package com.example.taifooapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.fragment.CartPage;
import com.example.taifooapplication.fragment.Homepage;
import com.example.taifooapplication.fragment.MyOrder;
import com.example.taifooapplication.fragment.PersonalInformation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    BottomNavigationView bottomNavigation;

    public static TextView nav_MyOrder,text_name,nav_Profile;
    public static ImageView loc,logo,search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationview);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        loc = findViewById(R.id.loc);
        logo = findViewById(R.id.logo);
        search = findViewById(R.id.search);
        text_name = findViewById(R.id.name);

        loc.setVisibility(View.VISIBLE);
        logo.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);

        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        nav_MyOrder = header.findViewById(R.id.nav_MyOrder);
        nav_Profile = header.findViewById(R.id.nav_Profile);


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

                text_name.setText("personalInformation");

            }
        });

        bottomNavigation.setSelectedItemId(R.id.home);

        getSupportFragmentManager().beginTransaction().replace(R.id.framLayout,new Homepage()).commit();

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
                        text_name.setText("PersonalInformation");

                        break;

                    case R.id.home:

                        selectedFragment = new Homepage();
                        loc.setVisibility(View.VISIBLE);
                        logo.setVisibility(View.VISIBLE);
                        search.setVisibility(View.VISIBLE);
                        text_name.setText("Home Page");

                        break;

                    case R.id.cart:

                        selectedFragment = new CartPage();
                        loc.setVisibility(View.GONE);
                        logo.setVisibility(View.GONE);
                        search.setVisibility(View.GONE);
                        text_name.setText("My Cart");

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framLayout,selectedFragment).commit();

                return true;
            }
        });


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


}