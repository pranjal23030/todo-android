package com.example.namiminiproject.sharedPref;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    // Variables
    public static String KEY_IS_LOGGED_IN = "is_logged_in";
    public static String KEY_USERNAME = "user_name";
    public static String KEY_PASSWORD = "password";
    public static String KEY_LANGUAGE = "language";
    public static String KEY_THEME = "theme";
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
        boolean isUserLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false); // default value --> false
        return isUserLoggedIn;
    }

    public void saveLoggedIn(Boolean isLoggedIn) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public void saveUserInfo(String name, String password) {
        sharedPreferences.edit().putString(KEY_USERNAME, name).apply();
        sharedPreferences.edit().putString(KEY_PASSWORD, password).apply();
    }

    public void setLoginCredential(String name, String password) {
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

    public void setLanguage(String language) {
        sharedPreferences.edit().putString(KEY_LANGUAGE, language).apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(KEY_LANGUAGE, "en");
    }

    public void setTheme(String theme) {
        sharedPreferences.edit().putString(KEY_THEME, theme).apply();
    }

    public String getTheme() {
        return sharedPreferences.getString(KEY_THEME, "light");
    }

    public void clear() {
        // Iterate over all the keys and remove them, except for the "language" key
        for (String key: sharedPreferences.getAll().keySet()) {
            if (!key.equals(KEY_LANGUAGE) && !key.equals(KEY_THEME)) {
                sharedPreferences.edit().remove(key);
            }
        }
        // Apply the changes
        sharedPreferences.edit().apply();
    }

}
