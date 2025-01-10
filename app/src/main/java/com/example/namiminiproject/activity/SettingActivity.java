package com.example.namiminiproject.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import com.example.namiminiproject.R;
import com.example.namiminiproject.sharedPref.SharedPrefManager;
import com.example.namiminiproject.utility.AppUtility;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    // Views
    RadioGroup languageRadioGroup, themeRadioGroup;
    RadioButton englishLanguage, nepaliLanguage, lightTheme, darkTheme;
    AppCompatButton logoutButton;
    TextView goBack;

    // Variables
    SharedPrefManager sharedPrefManager;
    String savedTheme, savedLanguage;
    Intent intent;
    Locale locale;
    Configuration config;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPrefManager = SharedPrefManager.getInstance(SettingActivity.this);
        initView();

        // Load saved language
        String language = sharedPrefManager.getLanguage();
        new AppUtility().setLocale(SettingActivity.this, language);

        // Load saved theme
        String theme = sharedPrefManager.getTheme();
        new AppUtility().setLocale(SettingActivity.this, theme);

        savedLanguage = sharedPrefManager.getLanguage();
        if("en".equals(savedLanguage)) {
            englishLanguage.setChecked(true);
        } else if ("ne".equals(savedLanguage)) {
            nepaliLanguage.setChecked(true);
        }

        languageRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_button_english) {
                setLocale("en");
            } else if (checkedId == R.id.radio_button_nepali) {
                setLocale("ne");
            }
        });

        savedTheme = sharedPrefManager.getTheme();
        if("light".equals(savedTheme)) {
            lightTheme.setChecked(true);
        } else if ("dark".equals(savedTheme)) {
            darkTheme.setChecked(true);
        }

        themeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_button_light) {
                // Force Light Theme
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                setTheme("light");
            } else if (checkedId == R.id.radio_button_dark) {
                // Force Dark Theme
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                setTheme("dark");
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.clear();
                navigateTo(LoginActivity.class);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(HomeActivity.class);
            }
        });
    }


    private void setLocale(String lang) {
        locale = new Locale(lang);
        Locale.setDefault(locale);
        config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Save the selected language in SharedPreferences
        sharedPrefManager.setLanguage(lang);

        // Refresh activity

        // recreate();
        navigateTo(HomeActivity.class);
    }

    private void setTheme(String theme) {

        // Save the selected theme in SharedPreferences
        sharedPrefManager.setTheme(theme);

        // Refresh activity

        navigateTo(HomeActivity.class);
        // recreate();
    }

    void initView(){
        languageRadioGroup = (RadioGroup) findViewById(R.id.language_radio_group);
        englishLanguage = (RadioButton) findViewById(R.id.radio_button_english);
        nepaliLanguage = (RadioButton) findViewById(R.id.radio_button_nepali);

        themeRadioGroup = (RadioGroup) findViewById(R.id.theme_radio_group);
        lightTheme = (RadioButton) findViewById(R.id.radio_button_light);
        darkTheme = (RadioButton) findViewById(R.id.radio_button_dark);

        logoutButton = (AppCompatButton) findViewById(R.id.logout_button);

        goBack = (TextView) findViewById(R.id.go_back);
    }

    void navigateTo(Class activityClass) {
        intent = new Intent(SettingActivity.this,activityClass);
        startActivity(intent);
        finish();
    }
}
