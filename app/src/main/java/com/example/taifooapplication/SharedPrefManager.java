package com.example.taifooapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.taifooapplication.activity.MainActivity;
import com.example.taifooapplication.activity.SplashScreen;
import com.example.taifooapplication.modelclas.Login_ModelClass;


public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_id = "keyid";
    private static final String KEY_name = "keyfirst_name";
    private static final String KEY_email = "keylast_name";
    private static final String KEY_mobile_number = "keymobile_number";
    private static final String KEY_password = "keypassword";
    private static final String KEY_wallet_Amount = "wallet_Amount";
    private static final String KEY_image = "keyimage_image";
    private static final String KEY_Count = "keycartcount";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user register
    //this method will store the user data in shared preferences
    public void userLogin(Login_ModelClass login_modelClass) {

        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();


        editor.putString(KEY_id,                login_modelClass.getId ());
        editor.putString(KEY_name,        login_modelClass.getName ());
        editor.putString(KEY_email,         login_modelClass.getEmail ());
        editor.putString(KEY_mobile_number,     login_modelClass.getMobile ());
        editor.putString(KEY_password,                login_modelClass.getPassword ());
        editor.putString(KEY_wallet_Amount,                login_modelClass.getWalletAmount ());
        editor.putString(KEY_image,                login_modelClass.getImage ());

        editor.apply();
    }

    public void cartCount(String cartCount){

        SharedPreferences sharedPrefManager1 = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPrefManager1.edit();

        editor1.putString(KEY_Count,cartCount);
        editor1.apply();
        editor1.commit();

    }

    public String getCartCount(){
        SharedPreferences sharedPrefManager1 = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPrefManager1.getString(KEY_Count,"DEFAULT");
    }



    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPrefManager.getString(KEY_id, null) != null;
    }

    //this method will give the logged in user
    public Login_ModelClass getUser() {
        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Login_ModelClass(


                sharedPrefManager.getString(KEY_id, null),
                sharedPrefManager.getString(KEY_name, null),
                sharedPrefManager.getString(KEY_email, null),
                sharedPrefManager.getString(KEY_mobile_number, null),
                sharedPrefManager.getString(KEY_password, null),
                sharedPrefManager.getString(KEY_wallet_Amount, null),
                sharedPrefManager.getString(KEY_image, null)
        );

    }

    //this method will logout the user
    public void logout() {

        SharedPreferences sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefManager.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent (mCtx, MainActivity.class));
    }

}
