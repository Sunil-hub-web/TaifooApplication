package com.example.taifooapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taifooapplication.R;
import com.example.taifooapplication.modelclas.PinCode_ModelClass;

import java.util.ArrayList;

public class PincodeSpinerAdapter extends ArrayAdapter<PinCode_ModelClass> {

    private ArrayList<PinCode_ModelClass> myarrayList1;

    public PincodeSpinerAdapter(Context context, int textViewResourceId, ArrayList<PinCode_ModelClass> modelArrayList) {
        super(context, textViewResourceId, modelArrayList);
        this.myarrayList1 = modelArrayList;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @Nullable
    @Override
    public PinCode_ModelClass getItem(int position) {
        return myarrayList1.get(position);
    }

    @Override
    public int getCount() {
        int count = myarrayList1.size();
        //return count > 0 ? count - 1 : count;
        return count;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    private View getCustomView(int position, ViewGroup parent) {
        PinCode_ModelClass model = getItem(position);

        View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_textview, parent, false);

        TextView label = spinnerRow.findViewById(R.id.spinner_text);
        label.setText(String.format("%s", model != null ? model.getPincode() : ""));


        return spinnerRow;
    }
}
