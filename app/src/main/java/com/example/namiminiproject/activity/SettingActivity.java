package com.example.namiminiproject.activity;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.namiminiproject.R;

public class SettingActivity extends AppCompatActivity {

    // Views
    RadioGroup languageRadioGroup, themeRadioGroup;
    AppCompatButton logoutButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initView();

    }

    void initView(){
        languageRadioGroup = (RadioGroup) findViewById(R.id.language_radio_group);
        themeRadioGroup = (RadioGroup) findViewById(R.id.theme_radio_group);
        logoutButton = (AppCompatButton) findViewById(R.id.login_button);
    }
}
