package com.example.namiminiproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.namiminiproject.R;
import com.example.namiminiproject.adapter.TaskAdapter;
import com.example.namiminiproject.entities.Task;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    // Variables
    RecyclerView taskRecycler;
    LinearLayoutManager manager;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        taskRecycler = (RecyclerView) findViewById(R.id.recyclerViewTasks);

        ArrayList<Task> taskArrayList = new ArrayList<>();
        taskArrayList.add(new Task("Task 1", "Description 1", "Dec 25, 2024"));
        taskArrayList.add(new Task("Task 2", "Description 2", "Dec 26, 2024"));
        taskArrayList.add(new Task("Task 3", "Description 3", "Dec 27, 2024"));
        taskArrayList.add(new Task("Task 4", "Description 4", "Dec 28, 2024"));
        taskArrayList.add(new Task("Task 5", "Description 5", "Dec 29, 2024"));
        taskArrayList.add(new Task("Task 6", "Description 6", "Dec 30, 2024"));
        taskArrayList.add(new Task("Task 7", "Description 7", "Dec 31, 2024"));

        manager = new LinearLayoutManager(this);
        taskRecycler.setLayoutManager(manager);

        adapter = new TaskAdapter(taskArrayList, HomeActivity.this);
        taskRecycler.setAdapter(adapter);
    }
}