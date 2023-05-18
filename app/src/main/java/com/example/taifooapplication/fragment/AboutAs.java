package com.example.taifooapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taifooapplication.R;

public class AboutAs extends Fragment {

    TextView datadetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aboutas,container,false);

        datadetails = view.findViewById(R.id.datadetails);

        datadetails.setText("Why The Taifoo?\n" +
                "\n" +
                "\n" +
                "Quality\n" +
                "\n" +
                "\n" +
                "Many people we talk to don't understand different grades of meat and what makes a good cut.\n" +
                " we strive to provide the best product available without all the confusion.\n" +
                " We select our high quality meats specifically for colour and tenderness and odia people test.\n" +
                " Just like the old-fashioned service you used to get from a local butcher,\n" +
                " you're buying user friendly portion sizes, so you don't have to worry about slicing up bulk meat or trimming any portions,\n" +
                " we care about your health to make bhubaneswar best online non veg market through our website .\n" +
                " \n" +
                "Convenience\n" +
                "\n" +
                "\n" +
                "At Freshshoppe you can buy high quality chicken and mutton without leaving home and at a price point lower than supermarkets' regular prices.\n" +
                "No tricks or gimmicks, just good cuts of meat delivered to your door\n" +
                "We are providing best quality foods online meat at bhubaneswar ,\n" +
                "our  chicken , mutton , crab and fish shop in bhubaneswar to provide you online non veg items at your doorstep.\n" +
                "\n" +
                "Useful package sizes\n" +
                "\n" +
                "\n" +
                "Our pack size options have been created based on our experience in retail markets.\n" +
                "We have different size options to suit each product. These choices allow you to buy just the right amount for your family.\n" +
                " In each size option we have declared how much you can expect.");

        return view;
    }
}
