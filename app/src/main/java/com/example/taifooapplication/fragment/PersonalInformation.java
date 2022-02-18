package com.example.taifooapplication.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class PersonalInformation extends Fragment {

    EditText edit_fillname, edit_mobileNo, edit_EmailId;
    String str_fillname, str_mobileNo, str_EmailId, profile_photo, userId;
    Button btn_Update;
    CircleImageView profile_image;
    public static final int IMAGE_CODE = 1;
    Uri imageUri;
    TextView text_editOption;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.personal_information, container, false);

        edit_fillname = view.findViewById(R.id.edit_fillname);
        edit_mobileNo = view.findViewById(R.id.edit_mobileNo);
        edit_EmailId = view.findViewById(R.id.edit_EmailId);
        profile_image = view.findViewById(R.id.profile_image);
        btn_Update = view.findViewById(R.id.btn_Update);
        text_editOption = view.findViewById(R.id.text_editOption);

        userId = SharedPrefManager.getInstance(getContext()).getUser().getId();

        getProfileDetails(userId);

        text_editOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit_fillname.requestFocus();
                edit_fillname.setEnabled(true);

                edit_mobileNo.requestFocus();
                edit_mobileNo.setEnabled(true);

                edit_EmailId.requestFocus();
                edit_EmailId.setEnabled(true);

                Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_CODE);
            }
        });

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageUri != null) {

                    str_fillname = edit_fillname.getText().toString().trim();
                    str_EmailId = edit_EmailId.getText().toString().trim();
                    str_mobileNo = edit_mobileNo.getText().toString().trim();

                    updateProfileDetails(userId, str_fillname, str_EmailId, str_mobileNo, profile_photo);

                } else {

                    Toast.makeText(getContext(), "Please Select Your Image", Toast.LENGTH_SHORT).show();

                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {

            imageUri = data.getData();
            profile_image.setImageURI(imageUri);

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap = Bitmap.createScaledBitmap(bitmap, 500, 750, true);
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos); //bm is the bitmap object
                byte[] img = baos.toByteArray();
                profile_photo = Base64.encodeToString(img, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getProfileDetails(String userId) {

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Show Profile Details Please Wait....");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.getUserDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");
                    String img = jsonObject.getString("img");

                    if (message.equals("true")) {

                        if (img.equals("")) {

                            //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                            String name = jsonObject.getString("name");
                            String mobile = jsonObject.getString("contact_no");
                            String email = jsonObject.getString("email");

                            edit_fillname.setText(name);
                            edit_EmailId.setText(email);
                            edit_mobileNo.setText(mobile);

                            //edit_fillname.setFocusable(false);
                            edit_fillname.setEnabled(false);

                            //edit_EmailId.setFocusable(false);
                            edit_EmailId.setEnabled(false);

                            //edit_mobileNo.setFocusable(false);
                            edit_mobileNo.setEnabled(false);

                        } else {

                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                            String name = jsonObject.getString("name");
                            String mobile = jsonObject.getString("contact_no");
                            String email = jsonObject.getString("email");

                            String image_profile = jsonObject.getString("img");

                            Log.d("image", image_profile);

                            String url = "https://" + image_profile;

                            Picasso.with(getContext()).load(url)
                                    .placeholder(R.drawable.profileimage)
                                    .into(profile_image);

                            edit_fillname.setText(name);
                            edit_EmailId.setText(email);
                            edit_mobileNo.setText(mobile);

                            //edit_fillname.setFocusable(false);
                            edit_fillname.setEnabled(false);

                            //edit_EmailId.setFocusable(false);
                            edit_EmailId.setEnabled(false);

                            //edit_mobileNo.setFocusable(false);
                            edit_mobileNo.setEnabled(false);
                        }


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
                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void updateProfilePicDetails(String userId, String image) {

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Update Profile Pic Please Wait....");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.updateProfilePic, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");

                    if (message.equals("true")) {

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
                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                params.put("img", image);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void updateProfileDetails(String id, String name, String email, String mobileNo, String image) {

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Update Profile Please Wait....");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppURL.updateProfileDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    String msg = jsonObject.getString("msg");

                    if (message.equals("true")) {

                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                        getProfileDetails(userId);

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
                Toast.makeText(getContext(), "Update Not Successfully", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("user_id",id);
                params.put("name",name);
                params.put("email",email);
                params.put("contact_no",mobileNo);
                params.put("img",image);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
