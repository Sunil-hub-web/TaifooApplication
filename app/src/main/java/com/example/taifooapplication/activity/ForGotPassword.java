package com.example.taifooapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForGotPassword extends AppCompatActivity {

    EditText edit_Emailid;
    String str_Emailid;
    Button btn_Verifay;
    TextView text_btnBack;
    ImageView img_back;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_got_password);

        edit_Emailid = findViewById(R.id.edit_Emailid);
        btn_Verifay = findViewById(R.id.btn_Verifay);
        text_btnBack = findViewById(R.id.text_btnBack);
        img_back = findViewById(R.id.img_back);

        /*Window window = ForGotPassword.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ForGotPassword.this, R.color.white));*/

        btn_Verifay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(edit_Emailid.getText())){

                    edit_Emailid.setText("Fill The Details");

                }else if(!isValidEmail(edit_Emailid.getText().toString().trim())){

                    edit_Emailid.requestFocus();
                    edit_Emailid.setError("Please Enter Valide Email id");

                }else{

                    str_Emailid = edit_Emailid.getText().toString().trim();
                    forgotPassword(str_Emailid);
                }



            }
        });

        text_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ForGotPassword.this,MainActivity.class);
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
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
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.forgotPassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");

                    if(message.equals("true")){

                        Toast.makeText(ForGotPassword.this, "Check Your Email to get UserId and Password", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ForGotPassword.this,MainActivity.class);
                        startActivity(intent);

                    }else if(message.equals("false")){

                        Toast.makeText(ForGotPassword.this, msg, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(ForGotPassword.this,MainActivity.class);
        startActivity(intent);
    }

    public boolean isValidEmail(final String email) {

        Pattern pattern;
        Matcher matcher;

        //final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

        pattern =  Patterns.EMAIL_ADDRESS;
        matcher = pattern.matcher (email);

        return matcher.matches ( );

        //return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}