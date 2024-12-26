package com.example.namiminiproject.sharedPref;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static String KEY_IS_LOGGED_IN = "is_logged_in";
    public static String KEY_USERNAME = "user_name";
    public static String KEY_PASSWORD = "password";

    public static SharedPrefManager INSTANCE;
    SharedPreferences sharedPreferences;
    SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences("LoginPrefs", MODE_PRIVATE);
    }

    public static SharedPrefManager getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new SharedPrefManager(context);
        }
        return INSTANCE;
    }

    public boolean isLoggedIn() {
        boolean isUserLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false); // default value -- false
        return isUserLoggedIn;
    }

    public void saveLoggedIn(Boolean isLoggedIn) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public void saveUserInfo(String name, String password) {
        sharedPreferences.edit().putString(KEY_USERNAME, name).apply();
        sharedPreferences.edit().putString(KEY_PASSWORD, password).apply();
    }

    public String getUserName(){
        String name = sharedPreferences.getString(KEY_USERNAME,"");
        return name;

    }
    public String getUserPassword(){
        String password = sharedPreferences.getString(KEY_PASSWORD,"");
        return password;
    }
}
