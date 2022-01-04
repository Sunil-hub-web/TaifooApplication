package com.example.taifooapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.SharedPrefManager;
import com.example.taifooapplication.fragment.LoginPageFragment;
import com.example.taifooapplication.fragment.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    TextView text_Login,text_SignUp;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.white));*/

        text_Login = findViewById(R.id.login);
        text_SignUp = findViewById(R.id.signup);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LoginPageFragment loginPageFragment = new LoginPageFragment();
        ft.replace(R.id.fram,loginPageFragment);
        ft.commit();

        text_Login.setTextColor(getResources().getColor(R.color.some_color));
        text_SignUp.setTextColor(getResources().getColor(R.color.some_color1));

        /*text_Login.setBackgroundResource(R.drawable.loginstock);*/

        text_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                LoginPageFragment loginPageFragment = new LoginPageFragment();
                ft.replace(R.id.fram,loginPageFragment);
                ft.commit();

                text_Login.setTextColor(getResources().getColor(R.color.some_color));
                text_SignUp.setTextColor(getResources().getColor(R.color.some_color1));

                /*text_Login.setBackgroundResource(R.drawable.loginstock);
                text_SignUp.setBackgroundResource(R.drawable.loginstock1);*/

            }
        });

        text_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                ft.replace(R.id.fram,registerFragment);
                ft.commit();

                text_Login.setTextColor(getResources().getColor(R.color.some_color1));
                text_SignUp.setTextColor(getResources().getColor(R.color.some_color));

                /*text_SignUp.setBackgroundResource(R.drawable.loginstock);
                text_Login.setBackgroundResource(R.drawable.loginstock1);*/

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(MainActivity.this).isLoggedIn()) {

            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(intent);
        }
    }
}