package com.example.namiminiproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.namiminiproject.AppDatabase;
import com.example.namiminiproject.R;
import com.example.namiminiproject.sharedPref.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    // Views
    AppCompatButton loginButton;
    EditText loginUserName, loginUserPassword;

    TextView signUp;

    // Variables
    String name, password;
    Intent intent;
    AppDatabase appDatabase;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appDatabase = AppDatabase.getDatabase(LoginActivity.this);
        sharedPrefManager = SharedPrefManager.getInstance(LoginActivity.this);

        initView();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initVariable();

                if(name.isEmpty()) {
                    loginUserName.setError("Username shouldn't be empty !!");
                } else if (password.isEmpty()) {
                    loginUserPassword.setError("Password shouldn't be empty !!");
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int userExist = appDatabase.userDao().isUserNameExist(name);

                            if (userExist > 0) {
                                int passwordExist = appDatabase.userDao().isUserPasswordExist(name, password);
                                if (passwordExist > 0) {

                                    sharedPrefManager.saveLoggedIn(true);

                                    navigateTo(TodoListItemActivity.class);

                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(RegisterActivity.class);
            }
        });

    }

    void initView(){
        loginButton = (AppCompatButton) findViewById(R.id.login_button);
        loginUserName = (EditText) findViewById(R.id.et_login_userName);
        loginUserPassword = (EditText) findViewById(R.id.et_login_userPassword);
        signUp = (TextView) findViewById(R.id.tv_signUp);
    }

    void initVariable(){
        name = loginUserName.getText().toString();
        password = loginUserPassword.getText().toString();
    }
    void navigateTo(Class activityClass) {
        intent = new Intent(LoginActivity.this,activityClass);
        startActivity(intent);
        finish();
    }
}
