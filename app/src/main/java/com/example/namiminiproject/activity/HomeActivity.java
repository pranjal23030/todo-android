package com.example.namiminiproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.namiminiproject.database.AppDatabase;
import com.example.namiminiproject.R;
import com.example.namiminiproject.adapter.TaskAdapter;
import com.example.namiminiproject.database.entities.Task;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    // Views
    AppCompatButton createTaskHome;

    TextView settings;

    // Variables
    RecyclerView taskRecycler;
    LinearLayoutManager manager;
    AppDatabase appDatabase;
    TaskAdapter adapter;
    Intent intent;
    ArrayList<Task> taskArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        appDatabase = AppDatabase.getDatabase(HomeActivity.this);

        initView();

        getTodoList();

        createTaskHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(CreateTaskActivity.class);
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
                setAdapter();
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
    }

    void navigateTo(Class activityClass) {
        intent = new Intent(HomeActivity.this,activityClass);
        startActivity(intent);
        finish();
    }

}