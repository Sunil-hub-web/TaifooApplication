package com.example.taifooapplication.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.taifooapplication.AppURL;
import com.example.taifooapplication.R;
import com.example.taifooapplication.activity.HomePageActivity;
import com.example.taifooapplication.activity.TeramConditionApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    Button btn_signup;
    EditText edit_UserFullName, edit_MobileNumber, edit_EmailId, edit_UserName, edit_Password;
    String str_UserFullName, str_MobileNumber, str_EmailId, str_UserName, str_Password;
    private AwesomeValidation awesomeValidation;
    CheckBox termsconditions;
    TextView termsconditions1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.registerpage, container, false);

        edit_UserFullName = view.findViewById(R.id.edit_UserFullName);
        edit_MobileNumber = view.findViewById(R.id.edit_MobileNumber);
        edit_EmailId = view.findViewById(R.id.edit_EmailId);
        edit_UserName = view.findViewById(R.id.edit_UserName);
        edit_Password = view.findViewById(R.id.edit_Password);
        termsconditions = view.findViewById(R.id.termsconditions);
        termsconditions1 = view.findViewById(R.id.termsconditions1);

        btn_signup = view.findViewById(R.id.btn_signup);

        String checkBox_html = "<font color=#817F7F>Read our </font>";
        String checkBox_html1 = "<font color=#F44336><b><u>Terms &amp; Conditions</u></b></font>";
        termsconditions1.setText(Html.fromHtml(checkBox_html1));
        termsconditions.setText(Html.fromHtml(checkBox_html));

        termsconditions1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TeramConditionApp.class);
                startActivity(intent);

            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(edit_UserFullName.getText())) {

                    edit_UserFullName.setError("Please Enter User Name");

                } else if (TextUtils.isEmpty(edit_MobileNumber.getText())) {

                    edit_MobileNumber.setError("Please Enter Mobile No");

                } else if (edit_MobileNumber.getText().toString().trim().length() != 10) {

                    edit_MobileNumber.setError("Enter Your 10 Digit Mobile Number");

                } else if (TextUtils.isEmpty(edit_EmailId.getText())) {

                    edit_EmailId.setError("Please Enter EmailId");

                } else if (!isValidEmail(edit_EmailId.getText().toString().trim())) {

                    edit_EmailId.requestFocus();
                    edit_EmailId.setError("Please Enter Valide Email id");

                } else if (TextUtils.isEmpty(edit_UserName.getText())) {

                    edit_UserName.setError("Please Enter User Name");

                } else if (TextUtils.isEmpty(edit_Password.getText())) {

                    edit_Password.setError("Please EnterYour password");

                } else if (!termsconditions.isChecked()) {

                    Toast.makeText(getActivity(), "Please Click Terms & Conditions", Toast.LENGTH_SHORT).show();

                } else {

                    str_UserFullName = edit_UserFullName.getText().toString().trim();
                    str_MobileNumber = edit_MobileNumber.getText().toString().trim();
                    str_EmailId = edit_EmailId.getText().toString().trim();
                    str_UserName = edit_UserName.getText().toString().trim();
                    str_Password = edit_Password.getText().toString().trim();


                    userRegister(str_UserFullName, str_MobileNumber, str_EmailId, str_UserName, str_Password);

                    //Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                }

               /* Intent intent = new Intent(getContext(), HomePageActivity.class);
                startActivity(intent);*/
            }
        });

        return view;
    }

    public void userRegister(String userName, String mobileNo, String emailId, String name, String password) {

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Register Your Data Please Wait....");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.userRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("registerRanjeet",response.toString());

                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");

                    if (message.equals("true")) {

                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                        edit_UserFullName.setText("");
                        edit_MobileNumber.setText("");
                        edit_EmailId.setText("");
                        edit_Password.setText("");
                        edit_UserName.setText("");


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getContext(), "Register not Successfully", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("fullname", userName);
                params.put("contact", mobileNo);
                params.put("mail", emailId);
                params.put("username", name);
                params.put("password", password);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

    public boolean isValidEmail(final String email) {

        Pattern pattern;
        Matcher matcher;

        //final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

        pattern = Patterns.EMAIL_ADDRESS;
        matcher = pattern.matcher(email);

        return matcher.matches();

        //return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


   /* public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }


    public boolean isValidUserName(final String userName) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN =  "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";

        pattern =  Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (userName);

        return matcher.matches ( );

    }

    public boolean isValidMobile(final String mobile) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN =  "^[0-9]{10}$";

        pattern =  Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (mobile);

        return matcher.matches ( );

    }*/
}
