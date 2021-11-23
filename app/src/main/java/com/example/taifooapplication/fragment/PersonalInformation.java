package com.example.taifooapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taifooapplication.R;

public class PersonalInformation extends Fragment {

    ImageView loc,logo,search;
    TextView text_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.personal_information,container,false);


        return view;
    }
}
