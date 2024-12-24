package com.example.namiminiproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.namiminiproject.R;

public class LoginActivity extends AppCompatActivity {

    // Views
    Button loginButton;
    EditText userName;
    EditText userPassword;

    // Variables

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TodoActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    void initView(){
        loginButton = (Button) findViewById(R.id.login_button);
        userName = (EditText) findViewById(R.id.user_name);
        userPassword = (EditText) findViewById(R.id.user_password);
    }
}
