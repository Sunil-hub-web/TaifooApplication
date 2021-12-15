package com.example.taifooapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForGotPassword extends AppCompatActivity {

    EditText edit_MobileNumber;
    String str_MobileNumber;
    Button btn_Verifay;
    TextView text_btnBack;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_got_password);

        edit_MobileNumber = findViewById(R.id.edit_MobileNumber);
        btn_Verifay = findViewById(R.id.btn_Verifay);
        text_btnBack = findViewById(R.id.text_btnBack);

        Window window = ForGotPassword.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ForGotPassword.this, R.color.white));

        btn_Verifay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_MobileNumber = edit_MobileNumber.getText().toString().trim();

                forgotPassword(str_MobileNumber);

            }
        });

        text_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ForGotPassword.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


    public void forgotPassword(String email){

        ProgressDialog progressDialog = new ProgressDialog(ForGotPassword.this);
        progressDialog.setMessage("Pelase wait...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.forgotPassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if(message.equals("true")){

                        Toast.makeText(ForGotPassword.this, "Check Your Email to get UserId and Password", Toast.LENGTH_SHORT).show();
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
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                return params ;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ForGotPassword.this);
        requestQueue.add(stringRequest);

    }
}