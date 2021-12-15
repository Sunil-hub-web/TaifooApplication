package com.example.taifooapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.taifooapplication.modelclas.ViewAddressDetails_ModelClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewaddressDetailsAdapter extends RecyclerView.Adapter<ViewaddressDetailsAdapter.ViewHolder> {

    Context context;
    ArrayList<ViewAddressDetails_ModelClass> address_Details;
    private OnItemClickListener mListener;
    public static String addressId;
    int index;

    public ViewaddressDetailsAdapter(Context context, ArrayList<ViewAddressDetails_ModelClass> addressDetails) {

        this.context = context;
        this.address_Details = addressDetails;
    }


    public interface OnItemClickListener {

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewaddressDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewaddressdetails,parent,false);
        return new ViewHolder(view, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull  ViewaddressDetailsAdapter.ViewHolder holder, int position) {

        ViewAddressDetails_ModelClass viewAddress = address_Details.get(position);

        holder.text_Address.setText(viewAddress.getUserName()+", "+viewAddress.getMobileNo()
                                    +", "+viewAddress.getEmail()+", "+viewAddress.getCityName()
                                    +", "+viewAddress.getArea()+", "+viewAddress.getAddress()+", "+viewAddress.getPincode());

        holder.addressId.setText(viewAddress.getAddressId());

        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Delete Address Details");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.deleteAddressDetails, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String message = jsonObject.getString("success");

                            if (message.equals("true")){

                                Toast.makeText(context, "Address Deleted Success Fully.", Toast.LENGTH_SHORT).show();
                                address_Details.remove(position);
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        error.printStackTrace ();
                        Toast.makeText (context, "Address not Stored Successfully", Toast.LENGTH_SHORT).show ( );

                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params = new HashMap<>();

                        params.put("address_id",addressId);

                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);

            }
        });

        holder.rel_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addressId = viewAddress.getAddressId();

                index = position;
                notifyDataSetChanged();
                String all_values = holder.text_Address.getText().toString().trim();
                Toast.makeText(context, addressId, Toast.LENGTH_SHORT).show();

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

    public String addressId(){

        return addressId;
    }

    @Override
    public int getItemCount() {
        return address_Details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_Address,addressId;
        Button btn_Delete;
        RelativeLayout rel_Click;

        public ViewHolder(@NonNull  View itemView,final OnItemClickListener listener) {
            super(itemView);

            text_Address = itemView.findViewById(R.id.addressdetails);
            btn_Delete = itemView.findViewById(R.id.btn_Delete);
            rel_Click = itemView.findViewById(R.id.rel_Click);
            addressId = itemView.findViewById(R.id.addressId);

            btn_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
