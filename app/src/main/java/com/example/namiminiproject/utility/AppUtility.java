package com.example.namiminiproject.utility;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class AppUtility {

    // Variables
    Locale locale;
    Configuration config;

    public void setLocale(Context context, String lang) {
        locale = new Locale(lang);
        Locale.setDefault(locale);
        config = new Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public void setTheme(Context context, String theme) {
        if(theme.equals("light")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (theme.equals("dark")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}