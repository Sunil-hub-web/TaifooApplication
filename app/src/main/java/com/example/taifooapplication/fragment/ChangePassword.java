package com.example.taifooapplication.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends Fragment {

    EditText edit_OldPassword,edit_NewPassword,edit_ConfirmPassword;
    String str_OldPassword,str_NewPassword,str_ConfirmPassword,str_UserId;
    Button btn_ChangePassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.changepassword,container,false);

        edit_OldPassword = view.findViewById(R.id.edit_OldPassword);
        edit_NewPassword = view.findViewById(R.id.edit_NewPassword);
        edit_ConfirmPassword = view.findViewById(R.id.edit_ConfirmPassword);
        btn_ChangePassword = view.findViewById(R.id.btn_ChangePassword);

       // str_UserId = SharedPrefManager.getInstance(getContext()).getUser().getId();

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
        str_UserId = sh.getString("userId", "");

        btn_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(edit_OldPassword.getText())){

                    edit_OldPassword.setError("Enter Password");

                }else if(TextUtils.isEmpty(edit_NewPassword.getText())){

                    edit_NewPassword.setError("Enter Password");

                }else if(TextUtils.isEmpty(edit_ConfirmPassword.getText())){

                    edit_ConfirmPassword.setError("Enter Password");

                }else{

                    str_OldPassword = edit_OldPassword.getText().toString().trim();
                    str_NewPassword = edit_NewPassword.getText().toString().trim();
                    str_ConfirmPassword = edit_ConfirmPassword.getText().toString().trim();

                    changePassword(str_UserId,str_OldPassword,str_NewPassword,str_ConfirmPassword);

                }

            }
        });


        return view;
    }


    public void changePassword(String userId ,String oldPassword,String newpassword,String confirmPassword){

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Change Password Please Wait...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.changePassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");

                    if(message.equals("true")){

                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Change Password not Successfully", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("user_id",userId);
                params.put("old_password",oldPassword);
                params.put("new_password",newpassword);
                params.put("cnf_password",confirmPassword);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
