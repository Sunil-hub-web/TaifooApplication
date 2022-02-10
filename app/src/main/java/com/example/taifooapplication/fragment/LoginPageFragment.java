package com.example.taifooapplication.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.taifooapplication.activity.ForGotPassword;
import com.example.taifooapplication.activity.HomePageActivity;
import com.example.taifooapplication.activity.MainActivity;
import com.example.taifooapplication.modelclas.Login_ModelClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPageFragment extends Fragment {

    Button btn_signin;
    EditText edit_UserName, edit_Password;
    String str_UserName, str_Password;
    TextView text_ClickHere;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.loginpage, container, false);

        btn_signin = view.findViewById(R.id.btn_signin);
        edit_UserName = view.findViewById(R.id.edit_UserName);
        edit_Password = view.findViewById(R.id.edit_Password);
        text_ClickHere = view.findViewById(R.id.text_ClickHere);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(edit_UserName.getText())){

                    edit_UserName.setText("Fill The Details");

                }else if(TextUtils.isEmpty(edit_Password.getText())){

                    edit_Password.setText("Fill The Details");

                }else{

                    str_UserName = edit_UserName.getText().toString().trim();
                    str_Password = edit_Password.getText().toString().trim();

                    userLogin(str_UserName, str_Password);
                }


            }
        });

        text_ClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), ForGotPassword.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void userLogin(String userName, String password) {

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Login Please Wait....");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.userLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");

                    if (message.equals("true")) {

                        Toast.makeText(getContext(), "Login" + msg, Toast.LENGTH_SHORT).show();

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String contact_no = jsonObject.getString("contact_no");
                        String wallet_amt = jsonObject.getString("wallet_amt");
                        String image = jsonObject.getString("img");
                        String password = edit_Password.getText().toString().trim();

                        Login_ModelClass login_modelClass = new Login_ModelClass(id, name, email, contact_no, password, wallet_amt,image);
                        SharedPrefManager.getInstance(getContext()).userLogin(login_modelClass);

                        Intent intent = new Intent(getContext(), HomePageActivity.class);
                        startActivity(intent);

                    }else if(message.equals("false")){

                        Toast.makeText(getContext(), "Incorrect UserName and Password", Toast.LENGTH_SHORT).show();

                        Toast.makeText(getContext(), "Login" + msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Login not Successfully", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("usr_name", userName);
                params.put("password", password);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }









  /*  //Apply the rounded corners
    ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED,radius)
            .build();

    MaterialShapeDrawable shapeDrawable =
            new MaterialShapeDrawable(shapeAppearanceModel);
//Fill the background color
shapeDrawable.setFillColor(ContextCompat.getColorStateList(this,R.color....));
//You can also apply a stroke
shapeDrawable.setStroke(2.0f, ContextCompat.getColor(this,R.color....));

//Apply the shapeDrawable to the background.
ViewCompat.setBackground(editText,shapeDrawable);*/


}
