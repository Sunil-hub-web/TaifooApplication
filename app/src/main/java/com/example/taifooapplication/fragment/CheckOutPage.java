package com.example.taifooapplication.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.taifooapplication.activity.HomePageActivity;
import com.example.taifooapplication.activity.OrderSuccessFully;
import com.example.taifooapplication.adapter.CartPageAdapter;
import com.example.taifooapplication.adapter.CitySpinerAdapter;
import com.example.taifooapplication.adapter.OrderSummaryAdapter;
import com.example.taifooapplication.adapter.PincodeSpinerAdapter;
import com.example.taifooapplication.adapter.SateSpinearAdapter;
import com.example.taifooapplication.adapter.SelectAddressAdapter;
import com.example.taifooapplication.adapter.ViewaddressDetailsAdapter;
import com.example.taifooapplication.modelclas.CartPage_ModelClass;
import com.example.taifooapplication.modelclas.City_ModelClass;
import com.example.taifooapplication.modelclas.OrderSummary_ModelClass;
import com.example.taifooapplication.modelclas.PinCode_ModelClass;
import com.example.taifooapplication.modelclas.State_ModelClass;
import com.example.taifooapplication.modelclas.ViewAddressDetails_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckOutPage extends Fragment {

    RecyclerView orderSummaryRecycler;
    OrderSummaryAdapter orderSummaryAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<OrderSummary_ModelClass> ordersummary = new ArrayList<>();
    ArrayList<City_ModelClass> list_city = new ArrayList<>();
    ArrayList<State_ModelClass> list_state = new ArrayList<>();
    ArrayList<PinCode_ModelClass> arrayListPincode = new ArrayList<PinCode_ModelClass>();
    ArrayList<ViewAddressDetails_ModelClass> addressDetails = new ArrayList<>();
    double totalprice, sales_Price, quanTity, totalAmount = 0, d_taxandfee, d_subTotalPrice, d_Total;
    TextView text_subTotalPrice, text_shippingCharges, text_TotalPrice, text_ShowAddress, date_txt,text_statae,text_city,text_pincode;
    Dialog dialog;
    Spinner spinner_City, spinner_Pincode, spinner_State, timeslot;
    Button btn_AddnewAddress, btn_selectAddress, btn_ProceedCheckout;
    RadioButton radio_cashondelivery, selectedRadioButton;
    RadioGroup radioGroup;
    String str_Name, str_Email, str_MobileNo, str_City, str_Area, str_Address, city_Id, city_Name, pincode,
            str_PinCode, City_id, pincode_Name, userId, subTotalPrice, deliveryPrice, totalPrice, taxandfee,
            Name, Email, MobileNo, City, Area, Address, PinCode, addressId, city_id, cityid, pin_code, pin_id, str_Total,
            str_ShowAddress, str_ShippingNmae, addreessid, selectPaymentOption, currentDate, currentTime, state_id,
            state_name, pincode_id, state_Id, state_Name, order_id, dateselected = "", name_time = "", name_date = "";

    String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    RecyclerView recyclerAddressDetails;
    LinearLayoutManager linearLayoutManager1;
    SelectAddressAdapter selectAddressAdapter;
    DatePickerDialog datePickerDialog;
    int year, month, dayOfMonth, i;
    Calendar calendar;
    CardView addresslayout;
    ArrayAdapter<CharSequence> adapter;
    long timeInMilliseconds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.checkoutpage, container, false);

        orderSummaryRecycler = view.findViewById(R.id.orderSummaryRecycler);
        text_subTotalPrice = view.findViewById(R.id.text_subTotalPrice);
        text_shippingCharges = view.findViewById(R.id.text_shippingCharges);
        text_TotalPrice = view.findViewById(R.id.text_TotalPrice);
        btn_AddnewAddress = view.findViewById(R.id.btn_AddnewAddress);
        btn_selectAddress = view.findViewById(R.id.btn_selectAddress);
        text_ShowAddress = view.findViewById(R.id.text_ShowAddress);
        btn_ProceedCheckout = view.findViewById(R.id.btn_ProceedCheckout);
        radio_cashondelivery = view.findViewById(R.id.radio_cashondelivery);
        //radio_payonline = view.findViewById(R.id.radio_payonline);
        radioGroup = view.findViewById(R.id.radioGroup);
        date_txt = view.findViewById(R.id.date_txt);
        timeslot = view.findViewById(R.id.timeslot);

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
        userId = sh.getString("userId", "");

        //userId = SharedPrefManager.getInstance(getContext()).getUser().getId();

        SharedPreferences pref1 = getContext().getSharedPreferences("order_id123", 0);
        order_id = pref1.getString("order_id", null);

        if (order_id == null) {
            getCartItem(userId);
        } else {
            successPayment(userId);
        }


        SharedPreferences sp = getContext().getSharedPreferences("details", Context.MODE_PRIVATE);

        subTotalPrice = sp.getString("subTotalPrice", null);
        deliveryPrice = sp.getString("deliveryPrice", null);
        totalPrice = sp.getString("totalPrice", null);
        taxandfee = sp.getString("taxandfee", null);
        str_ShippingNmae = sp.getString("ShippingNmae", null);

        d_taxandfee = Double.valueOf(taxandfee);
        d_subTotalPrice = Double.valueOf(subTotalPrice);

        d_Total = d_subTotalPrice + d_taxandfee;
        str_Total = String.valueOf(d_Total);

        text_subTotalPrice.setText(str_Total);
        text_shippingCharges.setText(deliveryPrice);
        text_TotalPrice.setText(totalPrice);

        btn_AddnewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAddress();
            }
        });

        btn_selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectAddress();
            }
        });

        btn_ProceedCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (date_txt.getText().toString().trim().equals("Select Date")){

                    Toast.makeText(getActivity(), "Please Select Your date", Toast.LENGTH_SHORT).show();

                }else{

                    name_date = date_txt.getText().toString().trim();
                    name_time = timeslot.getSelectedItem().toString();
                }

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                if (text_ShowAddress.getText().toString().trim().equals("")) {

                    Toast.makeText(getContext(), "Select Your address", Toast.LENGTH_SHORT).show();

                } else if (selectedRadioButtonId == -1) {

                    Toast.makeText(getContext(), "Please select", Toast.LENGTH_SHORT).show();

                } else if (date_txt.getText().toString().trim().equals("Select Date")) {

                    Toast.makeText(getContext(), "Select Your Date", Toast.LENGTH_SHORT).show();

                } else if (name_time.equals("")) {

                    Toast.makeText(getContext(), "Select Your Time", Toast.LENGTH_SHORT).show();

                } else {

                    name_time = timeslot.getSelectedItem().toString();
                    name_date = date_txt.getText().toString().trim();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                    currentDate = sdf.format(new Date());

                    SimpleDateFormat sdt = new SimpleDateFormat("HH:mm:ss");
                    currentTime = sdt.format(new Date());

                    selectedRadioButton = view.findViewById(selectedRadioButtonId);
                    selectPaymentOption = selectedRadioButton.getText().toString();


                    if (selectPaymentOption.equals("Pay Online")) {

                        String str_totalprice = text_TotalPrice.getText().toString().trim();

                        orderPlaced_online(userId, addreessid, deliveryPrice, str_ShippingNmae, selectPaymentOption,
                                name_date, name_time, str_totalprice);

                    } else {

                        orderPlaced(userId, addreessid, deliveryPrice, str_ShippingNmae, selectPaymentOption,
                                name_date, name_time);

                    }
                }
            }
        });

        date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                edit_date.setText(year + "-" + (month + 1) + "-" + day);

                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-d");
                                DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                                String inputDateStr = year + "-" + (month + 1) + "-" + day;
                                Log.d("sufifn", inputDateStr);
                                dateselected = inputDateStr;
                                Date date = null;
                                try {
                                    date = inputFormat.parse(inputDateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String outputDateStr = outputFormat.format(date);

                                date_txt.setText(outputDateStr);
                                setSpinnerData();

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
//                  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        return view;
    }

    public void getCartItem(String userid) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Get Cart Item Details");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getItemFormCart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //String message = jsonObject.getString("success");
                    String All_Cart = jsonObject.getString("allcart");

                    JSONArray jsonArray_AllCart1 = new JSONArray(All_Cart);

                    if (jsonArray_AllCart1.length() != 0) {

                        JSONArray jsonArray_AllCart = new JSONArray(All_Cart);

                        for (int i = 0; i < jsonArray_AllCart.length(); i++) {

                            JSONObject jsonObject_AllCart = jsonArray_AllCart.getJSONObject(i);

                            // String cart_id = jsonObject_AllCart.getString("cart_id");
                            String product_id = jsonObject_AllCart.getString("product_id");
                            String product_name = jsonObject_AllCart.getString("product_name");
                            // String product_weight = jsonObject_AllCart.getString("product_weight");
                            String variation = jsonObject_AllCart.getString("variation");
                            String product_img = jsonObject_AllCart.getString("product_image");
                            String quantity = jsonObject_AllCart.getString("product_qty");
                            String sale_price = jsonObject_AllCart.getString("price");

                            OrderSummary_ModelClass orderSummary_modelClass = new OrderSummary_ModelClass(
                                    product_id, product_img, product_name, variation, sale_price, quantity
                            );

                            sales_Price = Double.valueOf(sale_price);

                            quanTity = Double.valueOf(quantity);

                            totalprice = sales_Price * quanTity;

                            totalAmount = totalAmount + totalprice;

                            ordersummary.add(orderSummary_modelClass);


                        }

                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        orderSummaryAdapter = new OrderSummaryAdapter(getContext(), ordersummary);
                        orderSummaryRecycler.setLayoutManager(linearLayoutManager);
                        orderSummaryRecycler.setHasFixedSize(true);
                        orderSummaryRecycler.setAdapter(orderSummaryAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getContext(), "address Details Not Found", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", userid);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void addAddress() {

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
        text_statae = dialog.findViewById(R.id.text_statae);
        text_city = dialog.findViewById(R.id.text_city);
        text_pincode = dialog.findViewById(R.id.text_pincode);

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edit_userName.getText().toString().trim().equals("")) {

                    edit_userName.setError("Please Enter Name");

                } else if (TextUtils.isEmpty(edit_EmailId.getText())) {

                    edit_EmailId.setError("Please Enter Email");

                }else if (!edit_EmailId.getText().toString().trim().matches(regex)){

                    edit_EmailId.setError("Email Is Not Valide");

                } else if (TextUtils.isEmpty(edit_MobileNo.getText()) && edit_MobileNo.getText().toString().trim().length() != 10) {

                    edit_MobileNo.setError("Please Enter MobileNumber");

                } else if (TextUtils.isEmpty(edit_Areas.getText())) {

                    edit_Areas.setError("Please Enter Area");

                } else if (TextUtils.isEmpty(edit_Address.getText())) {

                    edit_Address.setError("Please Enter Address");

                } else {

                    str_Name = edit_userName.getText().toString().trim();
                    str_Email = edit_EmailId.getText().toString().trim();
                    str_MobileNo = edit_MobileNo.getText().toString().trim();
                    str_Area = edit_Areas.getText().toString().trim();
                    str_Address = edit_Address.getText().toString().trim();
                    str_PinCode = pincode;
                    str_City = city_Id;

                   // addAddress_Save(userId, str_Name, state_Id, city_Id, pincode_id, str_MobileNo, str_Address);
                    addAddress_Save(userId,str_Name,state_Id,city_Id,pincode_id,str_MobileNo,str_Address,str_Email,str_Area);

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

                        list_state.clear();

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
                            text_statae.setVisibility(View.GONE);
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

                        list_city.clear();

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

                                    text_city.setVisibility(View.GONE);
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

                        arrayListPincode.clear();

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

                                            text_pincode.setVisibility(View.GONE);
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

    public void addAddress_Save(String userId, String name, String state_id, String city_id,
                                String pincode, String number, String address, String email, String address1) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
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

                    if (message.equals("true")) {

                        Toast.makeText(getContext(), "Address Insterted Success Fully..", Toast.LENGTH_SHORT).show();

                        //getaddressDetails(userId);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getContext(), "Address not Stored Successfully", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("id",userId);
                params.put("name",name);
                params.put("state_id",state_id);
                params.put("city_id",city_id);
                params.put("pincode",pincode);
                params.put("number",number);
                params.put("email",email);
                params.put("address",address);
                params.put("address1",address1);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    public void selectAddress() {

        Dialog dialogSelect = new Dialog(getContext());
        dialogSelect.setContentView(R.layout.selectaddress);
        dialogSelect.setCancelable(false);

        Button btn_SelectAddress = dialogSelect.findViewById(R.id.btn_SelectAddress);

        recyclerAddressDetails = dialogSelect.findViewById(R.id.recyclerAddressDetails);

        btn_SelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_ShowAddress = selectAddressAdapter.addressvalue();
                addreessid = SelectAddressAdapter.addressId;

                text_ShowAddress.setText(str_ShowAddress);

                dialogSelect.dismiss();
            }
        });

        getaddressDetails(userId);

        dialogSelect.show();
        Window window = dialogSelect.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


    }

    public void getaddressDetails(String userid) {

        addressDetails.clear();

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Show User Address Details");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getAddressDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        String address = jsonObject.getString("All_address");

                        JSONArray jsonArray = new JSONArray(address);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            addressId = jsonObject1.getString("addres_id");
                            Name = jsonObject1.getString("name");
                            state_id = jsonObject1.getString("state_id");
                            state_name = jsonObject1.getString("state_name");
                            city_id = jsonObject1.getString("city_id");
                            City = jsonObject1.getString("city_name");
                            pin_id = jsonObject1.getString("pin_id");
                            Area = jsonObject1.getString("address1");
                            PinCode = jsonObject1.getString("pincode");
                            MobileNo = jsonObject1.getString("number");
                            Address = jsonObject1.getString("address");
                            Email = jsonObject1.getString("email");


                            ViewAddressDetails_ModelClass viewAddressDetails_modelClass = new ViewAddressDetails_ModelClass(
                                    addressId, city_id, Name, City, Area, PinCode, MobileNo, Address, Email
                            );

                            addressDetails.add(viewAddressDetails_modelClass);
                        }

                        linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerAddressDetails.setLayoutManager(linearLayoutManager1);
                        recyclerAddressDetails.setHasFixedSize(true);
                        selectAddressAdapter = new SelectAddressAdapter(getContext(), addressDetails);
                        recyclerAddressDetails.setAdapter(selectAddressAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getContext(), "address Details Not Found", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("id", userid);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    public void orderPlaced(String userid, String addressid, String shippingCharges, String shippingType,
                            String paymentType, String deliveryDate, String ArrivalTime) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Your OrderPlaced Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.orderPlaced, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {

                        String order_id = jsonObject.getString("order_id");
                        // String order_date = jsonObject.getString("order_date");
                        String msg = jsonObject.getString("msg");

                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                        //vieworder();

                        Intent intent = new Intent(getContext(), OrderSuccessFully.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getContext(), "address Details Not Found", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("user_id", userid);
                params.put("adresss_id", addressid);
                params.put("shipChar", shippingCharges);
                params.put("shiptype", shippingType);
                params.put("payment_type", paymentType);
                params.put("delivery_date", deliveryDate);
                params.put("AvalTimeSlot", ArrivalTime);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

    public void orderPlaced_online(String userid, String addressid, String shippingCharges, String shippingType,
                                   String paymentType, String deliveryDate, String ArrivalTime, String GrandTotal) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Your OrderPlaced Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.onlineorder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("true")) {

                        String online_url = jsonObject.getString("online_url");
                        String order_id = jsonObject.getString("order_id");

                        // Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();

                        SharedPreferences pref = getContext().getSharedPreferences("order_id123", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("order_id", order_id);
                        editor.commit();

                        Fragment fragment = new WebViewFragment();
                        Bundle args = new Bundle();
                        args.putString("weburl", online_url);
                        fragment.setArguments(args);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framLayout, fragment, "WebViewFragment");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getContext(), "address Details Not Found", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("user_id", userid);
                params.put("adresss_id", addressid);
                params.put("shipChar", shippingCharges);
                params.put("shiptype", shippingType);
                params.put("payment_type", paymentType);
                params.put("delivery_date", deliveryDate);
                params.put("AvalTimeSlot", ArrivalTime);
                params.put("GrandTotal", GrandTotal);

                Log.d("parameterlist", params.toString());

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

    public void vieworder() {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.paymentsuccessfully);
        dialog.setCancelable(false);

        TextView text_shopping = dialog.findViewById(R.id.shopping);
        Button button = dialog.findViewById(R.id.view);

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), HomePageActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });*/

        text_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), HomePageActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //window.setBackgroundDrawableResource(R.drawable.dialogback);

    }

    public void successPayment(String userId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.successfail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("false")) {
                        String msg = jsonObject.getString("msg");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                        getCartItem(userId);

                    } else {

                        startActivity(new Intent(getActivity(), HomePageActivity.class));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Toast.makeText(getContext(), "address Details Not Found", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SharedPreferences pref1 = getContext().getSharedPreferences("order_id123", 0);
                order_id = pref1.getString("order_id", null);

                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void setSpinnerData() {

        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.timearray1, R.layout.spinnerfront2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        timeslot.setAdapter(adapter);

        Date currentTime = Calendar.getInstance().getTime();

        timeInMilliseconds = getMilliFromDate(dateselected);
        if (DateUtils.isToday(timeInMilliseconds)) {

            try {

                Date time1 = new SimpleDateFormat("HH:mm:ss").parse("07:00:00");
                Calendar calendar1 = Calendar.getInstance();
                //  calendar1.setTime(time1);
                //   calendar1.add(Calendar.DATE, 1);
                //  calendar1.setTime(time1);
                //   calendar1.add(Calendar.DATE, 1);

                calendar1.set(Calendar.HOUR_OF_DAY, 7);
                calendar1.set(Calendar.MINUTE, 0);
                calendar1.set(Calendar.SECOND, 0);

                Date time2 = new SimpleDateFormat("HH:mm:ss").parse("09:00:00");
                Calendar calendar2 = Calendar.getInstance();
                //calendar2.setTime(time2);
                //  calendar2.add(Calendar.DATE, 1);

                //  calendar2.add(Calendar.DATE, 1);


                calendar2.set(Calendar.HOUR_OF_DAY, 9);
                calendar2.set(Calendar.MINUTE, 0);
                calendar2.set(Calendar.SECOND, 0);

                Date time3 = new SimpleDateFormat("HH:mm:ss").parse("11:00:00");
                Calendar calendar3 = Calendar.getInstance();

                // calendar3.setTime(time3);
                // calendar3.add(Calendar.DATE, 1);

                // calendar3.setTime(time3);
                // calendar3.add(Calendar.DATE, 1);


                calendar3.set(Calendar.HOUR_OF_DAY, 11);
                calendar3.set(Calendar.MINUTE, 0);
                calendar3.set(Calendar.SECOND, 0);

                Date time4 = new SimpleDateFormat("HH:mm:ss").parse("13:00:00");
                Calendar calendar4 = Calendar.getInstance();
                //calendar4.setTime(time4);
                //calendar4.add(Calendar.DATE, 1);

                calendar4.set(Calendar.HOUR_OF_DAY, 13);
                calendar4.set(Calendar.MINUTE, 0);
                calendar4.set(Calendar.SECOND, 0);

                Date time5 = new SimpleDateFormat("HH:mm:ss").parse("15:00:00");
                Calendar calendar5 = Calendar.getInstance();
                //calendar5.setTime(time5);
                // calendar5.add(Calendar.DATE, 1);

                // calendar5.add(Calendar.DATE, 1);

                calendar5.set(Calendar.HOUR_OF_DAY, 15);
                calendar5.set(Calendar.MINUTE, 0);
                calendar5.set(Calendar.SECOND, 0);

                Date time6 = new SimpleDateFormat("HH:mm:ss").parse("17:00:00");
                Calendar calendar6 = Calendar.getInstance();
                // calendar6.setTime(time6);
                // calendar6.add(Calendar.DATE, 1);
                // calendar6.setTime(time6);
                // calendar6.add(Calendar.DATE, 1);

                calendar6.set(Calendar.HOUR_OF_DAY, 17);
                calendar6.set(Calendar.MINUTE, 0);
                calendar6.set(Calendar.SECOND, 0);

                Date time7 = new SimpleDateFormat("HH:mm:ss").parse("18:00:00");
                Calendar calendar7 = Calendar.getInstance();

                // calendar7.setTime(time7);
                // calendar7.setTime(time7);
                //calendar7.add(Calendar.DATE, 1);

                calendar7.set(Calendar.HOUR_OF_DAY, 19);
                calendar7.set(Calendar.MINUTE, 0);
                calendar7.set(Calendar.SECOND, 0);

//                Date time8 = new SimpleDateFormat("HH:mm:ss").parse("18:00:00");
//                Calendar calendar8 = Calendar.getInstance();
//                // calendar7.setTime(time7);
//                //calendar7.add(Calendar.DATE, 1);
//
//                calendar8.set(Calendar.HOUR_OF_DAY, 23);
//                calendar8.set(Calendar.MINUTE, 0);
//                calendar8.set(Calendar.SECOND, 0);
//
//                Date time9 = new SimpleDateFormat("HH:mm:ss").parse("18:00:00");
//                Calendar calendar9 = Calendar.getInstance();
//                // calendar7.setTime(time7);
//                //calendar7.add(Calendar.DATE, 1);
//
//                calendar8.set(Calendar.HOUR_OF_DAY, 24);
//                calendar8.set(Calendar.MINUTE, 0);
//                calendar8.set(Calendar.SECOND, 0);


                Date x = Calendar.getInstance().getTime();
//                final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//                Date x = dateFormat.format(new Date());

                Log.d("mkfgud_1", "" + calendar1.getTime());
                Log.d("mkfgud_2", "" + calendar2.getTime());
                Log.d("mkfgud_3", "" + calendar3.getTime());
                Log.d("mkfgud_4", "" + calendar4.getTime());
                Log.d("mkfgud_5", "" + calendar5.getTime());
                Log.d("mkfgud_6", "" + calendar6.getTime());
                Log.d("mkfgud_7", "" + calendar7.getTime());
                Log.d("mkfgud_8", "" + x);

                if (x.before(calendar1.getTime())) {
                    adapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.timearray1, R.layout.spinnerfront2);

                } else if (x.before(calendar2.getTime())) {
                    adapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.timearray2, R.layout.spinnerfront2);

                } else if (x.before(calendar3.getTime())) {
                    adapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.timearray3, R.layout.spinnerfront2);

                } else if (x.before(calendar4.getTime())) {
                    adapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.timearray4, R.layout.spinnerfront2);

                } else if (x.before(calendar5.getTime())) {
                    adapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.timearray5, R.layout.spinnerfront2);

                } else if (x.before(calendar6.getTime())) {
                    adapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.timearray6, R.layout.spinnerfront2);

                } else if (x.before(calendar7.getTime())) {
                    adapter = ArrayAdapter.createFromResource(getActivity(),
                            R.array.timearray7, R.layout.spinnerfront2);

                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                timeslot.setAdapter(adapter);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.timearray1, R.layout.spinnerfront2);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            timeslot.setAdapter(adapter);
        }

    }

    public long getMilliFromDate(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-d");
        try {
            date = formatter.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Today is " + date);
        return date.getTime();
    }


}
