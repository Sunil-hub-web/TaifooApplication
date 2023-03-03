package com.example.taifooapplication.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.taifooapplication.SharedPrefManager;
import com.example.taifooapplication.adapter.CitySpinerAdapter;
import com.example.taifooapplication.adapter.PincodeSpinerAdapter;
import com.example.taifooapplication.adapter.SateSpinearAdapter;
import com.example.taifooapplication.adapter.ViewaddressDetailsAdapter;
import com.example.taifooapplication.modelclas.City_ModelClass;
import com.example.taifooapplication.modelclas.PinCode_ModelClass;
import com.example.taifooapplication.modelclas.State_ModelClass;
import com.example.taifooapplication.modelclas.ViewAddressDetails_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddressDetails extends Fragment {

    Dialog dialog;
    String cityid,pin_code,pin_id;
    ArrayList<City_ModelClass> list_city = new ArrayList<>();
    ArrayList<State_ModelClass> list_state = new ArrayList<>();
    ArrayList<PinCode_ModelClass> arrayListPincode = new ArrayList<PinCode_ModelClass>();
    Spinner spinner_City,spinner_Pincode,spinner_State;
    Button btn_addAddress;
    String str_Name,str_Email,str_MobileNo,str_City,str_Area,str_Address,
            str_PinCode,userId,city_Id,city_Name,pincode,pincode_id,state_Id,state_Name,
            Name,Email,MobileNo,City,Area,Address,PinCode,addressId,city_id,state_id,state_name;

    ArrayList<ViewAddressDetails_ModelClass> addressDetails = new ArrayList<>();
    RecyclerView recyclerAddressDetails;
    ViewaddressDetailsAdapter viewaddressDetailsAdapter;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.addaddressdetails,container,false);

        btn_addAddress = view.findViewById(R.id.btn_addAddress);
        recyclerAddressDetails = view.findViewById(R.id.recyclerAddressDetails);

        userId = SharedPrefManager.getInstance(getContext()).getUser().getId();

        btn_addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAddress();
            }
        });

        getaddressDetails(userId);

        return view;
    }

    public void addAddress(){

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.addressdetails);
        //dialog.setCancelable(false);
        spinner_City = dialog.findViewById(R.id.spinner_City);
        spinner_Pincode = dialog.findViewById(R.id.spinner_Pincode);
        spinner_State = dialog.findViewById(R.id.spinner_State);

        getLocationCity();

        EditText edit_userName = dialog.findViewById(R.id.edit_userName);
        EditText edit_MobileNo = dialog.findViewById(R.id.edit_MobileNo);
        EditText edit_EmailId = dialog.findViewById(R.id.edit_EmailId);
        EditText edit_Areas = dialog.findViewById(R.id.edit_Ares);
        EditText edit_Address = dialog.findViewById(R.id.edit_Address);
        Button btn_Save = dialog.findViewById(R.id.btn_Save);
        Button btn_cancle = dialog.findViewById(R.id.btn_cancle);

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(edit_userName.getText().toString().trim().equals("")){

                    edit_userName.setError("Please Enter Name");

                }else if (TextUtils.isEmpty(edit_EmailId.getText())){

                    edit_EmailId.setError("Please Enter Email");

                }else if (TextUtils.isEmpty(edit_MobileNo.getText()) && edit_MobileNo.getText().toString().trim().length() == 10) {

                    edit_MobileNo.setError("Please Enter MobileNumber");

                }else if (TextUtils.isEmpty(edit_Areas.getText())) {

                    edit_Areas.setError("Please Enter Area");

                }else if (TextUtils.isEmpty(edit_Address.getText())) {

                    edit_Address.setError("Please Enter Address");

                }else{

                    str_Name = edit_userName.getText().toString().trim();
                    str_Email = edit_EmailId.getText().toString().trim();
                    str_MobileNo = edit_MobileNo.getText().toString().trim();
                    str_Area = edit_Areas.getText().toString().trim();
                    str_Address = edit_Address.getText().toString().trim();
                    str_PinCode = pincode;
                    str_City = city_Id;

                    addAddress_Save(userId,str_Name,state_Id,city_Id,pincode_id,str_MobileNo,str_Address);

                }


            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //window.setBackgroundDrawableResource(R.drawable.dialogback);


    }

    public void getLocationCity(){

        ProgressDialog progressDialog  = new ProgressDialog(getContext());
        progressDialog.setMessage("Show Your City");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.getUserLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        String allLocation = jsonObject.getString("All_loc");

                        JSONArray jsonArray = new JSONArray(allLocation);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String state_id = jsonObject1.getString("state_id");
                            String state_name = jsonObject1.getString("state_name");

                            JSONArray jsinArraycity = jsonObject1.getJSONArray("city_list");

                            State_ModelClass state_modelClass = new State_ModelClass
                                    (jsonObject1.getString("state_name"), state_id);

                            list_state.add(state_modelClass);
                        }

                        SateSpinearAdapter adapter = new SateSpinearAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item
                                , list_state);
                        spinner_State.setAdapter(adapter);

                        Log.d("citylist", list_city.toString());


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                spinner_State.setSelection(-1, true);

               spinner_State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                   @Override
                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                       try {
                           State_ModelClass mystate = (State_ModelClass) parent.getSelectedItem();

                           state_Id = mystate.getState_id();
                           state_Name = mystate.getCity();

                           getCityData(state_Id);

                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                   }

                   @Override
                   public void onNothingSelected(AdapterView<?> parent) {

                   }
               });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    public void getCityData(String state_Id){

        ProgressDialog progressDialog  = new ProgressDialog(getContext());
        progressDialog.setMessage("Show Your City");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.getUserLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        String allLocation = jsonObject.getString("All_loc");

                        JSONArray jsonArray = new JSONArray(allLocation);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String state_id = jsonObject1.getString("state_id");

                            if(state_id.equals(state_Id)){

                                JSONArray jsinArraycity = jsonObject1.getJSONArray("city_list");

                                for (int j =0;j<jsinArraycity.length();j++){

                                    JSONObject jsonObjectcity = jsinArraycity.getJSONObject(j);

                                    JSONArray jsinArrayPincode = jsonObjectcity.getJSONArray("pincode_list");

                                    cityid = jsonObjectcity.getString("city_id");

                                    City_ModelClass city_modelClass = new City_ModelClass
                                            (jsonObjectcity.getString("city_name"), cityid);

                                    list_city.add(city_modelClass);
                                }

                                CitySpinerAdapter adapter = new CitySpinerAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item
                                        , list_city);
                                spinner_City.setAdapter(adapter);

                                Log.d("citylist", list_city.toString());
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                spinner_City.setSelection(-1, true);

                spinner_City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        try {

                            City_ModelClass mystate = (City_ModelClass) parent.getSelectedItem();

                            city_Id = mystate.getCity_id();
                            city_Name = mystate.getCity();
                            Log.d("R_Pincode", city_Id);
                            GetPincode(city_Id);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } // to close the onItemSelected

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    public void GetPincode(String city_id) {

        arrayListPincode.clear();

         ProgressDialog progressDialog  = new ProgressDialog(getContext());
         progressDialog.setMessage("Show Your PinCode");
         progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.getUserLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        String allLocation = jsonObject.getString("All_loc");

                        JSONArray jsonArray = new JSONArray(allLocation);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String state_id = jsonObject1.getString("state_id");

                            if(state_id.equals(state_Id)){

                                JSONArray jsinArraycity = jsonObject1.getJSONArray("city_list");

                                for (int j =0;j<jsinArraycity.length();j++){

                                    JSONObject jsonObjectcity = jsinArraycity.getJSONObject(j);



                                    cityid = jsonObjectcity.getString("city_id");

                                    if (cityid.equals(city_id)) {

                                        JSONArray jsinArrayPincode = jsonObjectcity.getJSONArray("pincode_list");

                                        for (int k = 0; k < jsinArrayPincode.length(); k++) {

                                            JSONObject jsonObject2 = jsinArrayPincode.getJSONObject(k);
                                            pin_code = jsonObject2.getString("pincode");
                                            pin_id = jsonObject2.getString("pin_id");

                                            PinCode_ModelClass pinCode_modelClass = new PinCode_ModelClass(pin_code, pin_id);
                                            arrayListPincode.add(pinCode_modelClass);
                                        }

                                    }
                                }

                            }
                        }

                        PincodeSpinerAdapter pincodeSpinerAdapter = new PincodeSpinerAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item
                                , arrayListPincode);
                        spinner_Pincode.setAdapter(pincodeSpinerAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //spiner_Pincode.setSelection(-1, true);

                spinner_Pincode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        try {

                            PinCode_ModelClass mystate = (PinCode_ModelClass) parent.getSelectedItem();

                            pincode = mystate.getPincode();
                            pincode_id = mystate.getPin_id();

                            Log.d("R_Pincode", city_id);

                            //Log.d("Pinocde_array",janamam.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } // to close the onItemSelected

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void addAddress_Save(String userId,String name,String state_id,String city_id,
                                String pincode,String number,String address){

        ProgressDialog progressDialog  = new ProgressDialog(getContext());
        progressDialog.setMessage("Add Address Details");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.addAddress, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if (message.equals("true")){

                        Toast.makeText(getContext(), "Address Insterted Success Fully..", Toast.LENGTH_SHORT).show();

                        getaddressDetails(userId);


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
                Toast.makeText (getContext(), "Address not Stored Successfully", Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("id",userId);
                params.put("name",name);
                params.put("state_id",state_id);
                params.put("city_id",city_id);
                params.put("pincode",pincode);
                params.put("number",number);
                params.put("address",address);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    public void getaddressDetails(String userid){

        addressDetails.clear();

        ProgressDialog progressDialog  = new ProgressDialog(getContext());
        progressDialog.setMessage("update User Details");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getAddressDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if(message.equals("true")){

                        String address = jsonObject.getString("All_address");

                        JSONArray jsonArray = new JSONArray(address);

                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            addressId = jsonObject1.getString("addres_id");
                            Name = jsonObject1.getString("name");
                            state_id = jsonObject1.getString("state_id");
                            state_name = jsonObject1.getString("state_name");
                            city_id = jsonObject1.getString("city_id");
                            City = jsonObject1.getString("city_name");
                            pin_id = jsonObject1.getString("pin_id");
                           // Area = jsonObject1.getString("area");
                            PinCode = jsonObject1.getString("pincode");
                            MobileNo = jsonObject1.getString("number");
                            Address = jsonObject1.getString("address");
                           // Email = jsonObject1.getString("email");


                            ViewAddressDetails_ModelClass viewAddressDetails_modelClass = new ViewAddressDetails_ModelClass(
                                    addressId,city_id,Name,City,Area,PinCode,MobileNo,Address,Email
                            );

                          /* if(addressDetails.size()>0){

                               addressDetails.clear();
                           }*/

                            addressDetails.add(viewAddressDetails_modelClass);
                        }

                        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        recyclerAddressDetails.setLayoutManager(linearLayoutManager);
                        recyclerAddressDetails.setHasFixedSize(true);
                        viewaddressDetailsAdapter = new ViewaddressDetailsAdapter(getContext(),addressDetails);
                        recyclerAddressDetails.setAdapter(viewaddressDetailsAdapter);

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
                Toast.makeText(getContext(), "address Details Not Found", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("id",userid);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
}
