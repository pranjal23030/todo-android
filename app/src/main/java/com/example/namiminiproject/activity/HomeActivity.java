package com.example.namiminiproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.namiminiproject.database.AppDatabase;
import com.example.namiminiproject.R;
import com.example.namiminiproject.adapter.TaskAdapter;
import com.example.namiminiproject.database.entities.Task;
import com.example.namiminiproject.sharedPref.SharedPrefManager;
import com.example.namiminiproject.utility.AppUtility;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    // Views
    AppCompatButton createTaskHome;
    TextView settings, numberOfTasks, username;

    // Variables
    RecyclerView taskRecycler;
    LinearLayoutManager manager;
    AppDatabase appDatabase;
    TaskAdapter adapter;
    SharedPrefManager sharedPrefManager;
    Intent intent;
    ArrayList<Task> taskArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        appDatabase = AppDatabase.getDatabase(HomeActivity.this);
        sharedPrefManager = SharedPrefManager.getInstance(HomeActivity.this);

        // Load saved language
        String language = sharedPrefManager.getLanguage();
        new AppUtility().setLocale(HomeActivity.this, language);

        // Load saved theme
        String theme = sharedPrefManager.getTheme();
        new AppUtility().setLocale(HomeActivity.this, theme);

        initView();

        username.setText("Hello, " + sharedPrefManager.getUserName());

        getTodoList();

        createTaskHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(HomeActivity.this, CreateTaskActivity.class);
                intent.putExtra("task_operation", "add");
                startActivity(intent);
                finish();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(SettingActivity.class);
            }
        });
    }

    void getTodoList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                taskArrayList = (ArrayList<Task>) appDatabase.taskDao().getAllTasks();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        numberOfTasks.setText(String.valueOf(taskArrayList.size()));
                        setAdapter();
                    }
                });
            }
        }).start();
    }


    void setAdapter() {
        manager = new LinearLayoutManager(this);
        taskRecycler.setLayoutManager(manager);

        adapter = new TaskAdapter(taskArrayList, HomeActivity.this);
        taskRecycler.setAdapter(adapter);
    }

    void initView(){
        createTaskHome = (AppCompatButton) findViewById(R.id.create_task_home_button);
        taskRecycler = (RecyclerView) findViewById(R.id.recyclerViewTasks);
        settings = (TextView) findViewById(R.id.settings);
        numberOfTasks = (TextView) findViewById(R.id.no_of_tasks);
        username = (TextView) findViewById(R.id.dashboard_username);
    }

    void navigateTo(Class activityClass) {
        intent = new Intent(HomeActivity.this,activityClass);
        startActivity(intent);
        finish();
    }

    public void showDeleteDialog(Task task) {
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete ?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                refreshUI(task);
                            }
                        }).start();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void refreshUI(Task task) {
        appDatabase.taskDao().deleteTask(task);
        getTodoList();
    }
}