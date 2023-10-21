package com.example.taifooapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.taifooapplication.R;
import com.example.taifooapplication.activity.HomePageActivity;

public class More_Fragment extends Fragment {

    TextView nav_AboutAs,nav_TermsAndConditions,nav_PrivacyPolicyr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_more,container,false);

        nav_AboutAs = view.findViewById(R.id.nav_AboutAs);
        nav_TermsAndConditions = view.findViewById(R.id.nav_TermsAndConditions);
        nav_PrivacyPolicyr = view.findViewById(R.id.nav_PrivacyPolicyr);

        nav_AboutAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new AboutAs();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                HomePageActivity.search.setVisibility(View.GONE);
                HomePageActivity.text_name.setTextSize(18);
                HomePageActivity.text_name.setText("About As");
            }
        });

        nav_TermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new TermsConditionsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                HomePageActivity.search.setVisibility(View.GONE);

                HomePageActivity.text_name.setTextSize(18);
                HomePageActivity.text_name.setText("Terms And Conditions");
            }
        });

        nav_PrivacyPolicyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new PrivacyPolicyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                HomePageActivity.search.setVisibility(View.GONE);

                HomePageActivity.text_name.setTextSize(18);
                HomePageActivity.text_name.setText("Privacy Policy");

            }
        });


        return view;
    }
}
