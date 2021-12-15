package com.example.taifooapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taifooapplication.R;
import com.example.taifooapplication.modelclas.ViewAddressDetails_ModelClass;

import java.util.ArrayList;

public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.ViewHolder> {

    Context context;
    ArrayList<ViewAddressDetails_ModelClass> address_Details;
    public static String addressId,all_values;
    int index;


    public SelectAddressAdapter(Context context, ArrayList<ViewAddressDetails_ModelClass> addressDetails) {

        this.context = context;
        this.address_Details = addressDetails;
    }


    @NonNull
    @Override
    public SelectAddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_address,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull  SelectAddressAdapter.ViewHolder holder, int position) {

        ViewAddressDetails_ModelClass viewAddress = address_Details.get(position);

        holder.text_Address.setText(viewAddress.getUserName()+", "+viewAddress.getMobileNo()
                +", "+viewAddress.getEmail()+", "+viewAddress.getCityName()
                +", "+viewAddress.getArea()+", "+viewAddress.getAddress()+", "+viewAddress.getPincode());

        holder.addressId.setText(viewAddress.getAddressId());

        holder.rel_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addressId = viewAddress.getAddressId();

                index = position;
                notifyDataSetChanged();
                all_values = holder.text_Address.getText().toString().trim();
                Toast.makeText(context, all_values, Toast.LENGTH_SHORT).show();

            }
        });

        if(index == position){

            holder.rel_Click.setBackgroundResource(R.drawable.selectaddressback);
            holder.rel_Click.setElevation(15);

            addressId = holder.addressId.getText().toString().trim();
            Toast.makeText(context, addressId, Toast.LENGTH_SHORT).show();

        }
        else {
            holder.rel_Click.setBackgroundResource(R.drawable.homecard_back);
            holder.rel_Click.setElevation(5);
        }

    }

    public String addressvalue(){

        return all_values;
    }

    @Override
    public int getItemCount() {
        return address_Details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_Address,addressId;
        Button btn_Delete;
        RelativeLayout rel_Click;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            text_Address = itemView.findViewById(R.id.addressdetails);
            btn_Delete = itemView.findViewById(R.id.btn_Delete);
            rel_Click = itemView.findViewById(R.id.rel_Click);
            addressId = itemView.findViewById(R.id.addressId);

        }
    }
}
