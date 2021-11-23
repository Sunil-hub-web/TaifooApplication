package com.example.taifooapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.fragment.LoginPageFragment;
import com.example.taifooapplication.fragment.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    TextView text_Login,text_SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}