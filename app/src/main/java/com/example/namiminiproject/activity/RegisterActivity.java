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

import com.example.namiminiproject.database.AppDatabase;
import com.example.namiminiproject.R;
import com.example.namiminiproject.database.entities.User;
import com.example.namiminiproject.sharedPref.SharedPrefManager;
import com.example.namiminiproject.utility.AppUtility;

public class RegisterActivity extends AppCompatActivity {

    // Views
    AppCompatButton registerButton;
    EditText registerUsername, registerUserEmail, registerUserPassword, registerUserConfirmPassword;
    TextView signIn;

    // Variables
    AppDatabase appDatabase;
    String name, email, password, confirmPassword;
    SharedPrefManager sharedPrefManager;
    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        appDatabase = AppDatabase.getDatabase(RegisterActivity.this);
        sharedPrefManager = SharedPrefManager.getInstance(RegisterActivity.this);

        initView();

        // Load saved language
        String language = sharedPrefManager.getLanguage();
        new AppUtility().setLocale(RegisterActivity.this, language);

        // Load saved theme
        String theme = sharedPrefManager.getTheme();
        new AppUtility().setLocale(RegisterActivity.this, theme);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initVariable();

                if (name.isEmpty()) {

                    registerUsername.setError("Name shouldn't be empty !!");

                } else if (email.isEmpty()) {

                    registerUserEmail.setError("Email shouldn't be empty !!");

                } else if (password.isEmpty()) {

                    registerUserPassword.setError("Password shouldn't be empty !!");

                } else if (confirmPassword.isEmpty()) {

                    registerUserConfirmPassword.setError("Confirm password shouldn't  be empty !!");

                } else {
                    new Thread(()->{
                        User insertUser = new User(name, email, password);
                        int count = appDatabase.userDao().isUserExist(email, name);
                        if (count > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            appDatabase.userDao().insertUser(insertUser);

                            sharedPrefManager.setLoginCredential(name,password);
                            sharedPrefManager.saveLoggedIn(true);

                            navigateTo(HomeActivity.class);
                            System.out.println("User added to db !!");
                        }

                    }).start();

                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(LoginActivity.class);
            }
        });

    }

    void initView(){
        registerButton = (AppCompatButton) findViewById(R.id.register_button);
        registerUsername = (EditText) findViewById(R.id.et_register_userName);
        registerUserEmail = (EditText) findViewById(R.id.et_register_userEmail);
        registerUserPassword = (EditText) findViewById(R.id.et_register_userPassword);
        registerUserConfirmPassword = (EditText) findViewById(R.id.et_register_userConfirmPassword);
        signIn = (TextView) findViewById(R.id.tv_signIn);
    }

    void initVariable() {
        name = registerUsername.getText().toString();
        email = registerUserEmail.getText().toString();
        password = registerUserPassword.getText().toString();
        confirmPassword = registerUserConfirmPassword.getText().toString();
    }


   void navigateTo(Class activityClass) {
        intent = new Intent(RegisterActivity.this,activityClass);
        startActivity(intent);
        finish();
    }
}
