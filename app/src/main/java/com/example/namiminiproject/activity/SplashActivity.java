package com.example.namiminiproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.namiminiproject.R;
import com.example.namiminiproject.sharedPref.SharedPrefManager;
import com.example.namiminiproject.utility.AppUtility;

public class SplashActivity extends AppCompatActivity {

    // Variables
    SharedPrefManager sharedPrefManager;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPrefManager = SharedPrefManager.getInstance(SplashActivity.this);
        Boolean isUserLoggedIn = sharedPrefManager.isLoggedIn();

        // Load saved language
        String language = sharedPrefManager.getLanguage();
        new AppUtility().setLocale(SplashActivity.this, language);

        // Load saved theme
        String theme = sharedPrefManager.getTheme();
        new AppUtility().setLocale(SplashActivity.this, theme);

        new Handler().postDelayed(() ->{
            System.out.println(isUserLoggedIn);
            if (isUserLoggedIn == true) {
                navigateToHome();
            } else {
                navigateToLogin();
            }
        }, 2000);
    }

    void navigateToHome() {
        intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    void navigateToLogin() {
        intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
