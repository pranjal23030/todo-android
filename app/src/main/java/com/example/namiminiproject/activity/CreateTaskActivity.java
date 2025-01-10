package com.example.namiminiproject.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.namiminiproject.database.AppDatabase;
import com.example.namiminiproject.R;
import com.example.namiminiproject.database.entities.Task;
import com.example.namiminiproject.sharedPref.SharedPrefManager;
import com.example.namiminiproject.utility.AppUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTaskActivity extends AppCompatActivity {

    // Views
    EditText taskTitle, taskDescription;
    TextView taskDate, taskTime;
    Spinner taskPriority, taskStatus;
    AppCompatButton createTaskForm;

    // Variables
    AppDatabase appDatabase;
    Intent intent;
    String title, date, time, priority, status, description;
    String taskOperation = "add";
    int priorityPosition, statusPosition;
    Task task;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        initView();

//        // Load saved language
//        String language = sharedPrefManager.getLanguage();
//        new AppUtility().setLocale(CreateTaskActivity.this, language);
//
//        // Load saved theme
//        String theme = sharedPrefManager.getTheme();
//        new AppUtility().setLocale(CreateTaskActivity.this, theme);

        appDatabase = AppDatabase.getDatabase(CreateTaskActivity.this);

        intent = getIntent();
        taskOperation = intent.getStringExtra("task_operation");

        if (taskOperation.equals("edit")) {
            task = (Task) intent.getSerializableExtra("task");
            taskTitle.setText(task.getTitle());
            taskDate.setText(task.getDueDate());
            taskTime.setText(task.getDueTime());
            taskDescription.setText(task.getDescription());

            ArrayAdapter<CharSequence> priorityAdapter = (ArrayAdapter<CharSequence>) taskPriority.getAdapter();
            priorityPosition = priorityAdapter.getPosition(task.getPriority());
            taskPriority.setSelection(priorityPosition);

            ArrayAdapter<CharSequence> statusAdapter = (ArrayAdapter<CharSequence>) taskStatus.getAdapter();
            statusPosition = statusAdapter.getPosition(task.getStatus());
            taskStatus.setSelection(statusPosition);
        }


        createTaskForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initVariable();

                if (title.isEmpty()) {
                    taskTitle.setError("Title shouldn't be empty !!");
                } else if (date.isEmpty()) {
                    taskDate.setError("Choose a date !!");
                } else if (time.isEmpty()) {
                    taskTime.setError("Pick a time!!");
                } else if (description.isEmpty()) {
                    taskDescription.setError("Description shouldn't be empty !!");
                } else {
                    new Thread(()->{
                        if (taskOperation.equals("edit")) {

                            appDatabase.taskDao().updateTodo(title, date, time, priority, status, description, task.getId());
                            navigateTo(HomeActivity.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CreateTaskActivity.this, "Task edited !!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {

                            Task insertTask = new Task(title, description, priority, status, date, time);
                            appDatabase.taskDao().insertTask(insertTask);
                            navigateTo(HomeActivity.class);

                            runOnUiThread (new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CreateTaskActivity.this, "Task created !!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).start();
                }
            }
        });

        // For Date
        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current date
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        view.getContext(),
                        (view1, selectedYear, selectedMonth, selectedDay) -> {
                            // etDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

                            Calendar selectedCalendar =  Calendar.getInstance();
                            selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
                            String formattedDate = new SimpleDateFormat("d MMM yyy", Locale.ENGLISH).format(selectedCalendar.getTime());
                            taskDate.setText(formattedDate);
                        },
                        year, month, day);

                // Show the dialog
                datePickerDialog.show();
            }
        });

        // For Time
        taskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current time
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Create a TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        view.getContext(),
                        (TimePicker view1, int selectedHour, int selectedMinute) -> {

                            // Update the TextView with the selected time
                            String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                            taskTime.setText(formattedTime);
                        },
                        hour, minute, true); // 'true' for 24-hour time format

                // Show the dialog
                timePickerDialog.show();
            }
        });
    }

    void initView() {
        taskTitle = (EditText) findViewById(R.id.et_create_task_title);
        taskDescription = (EditText) findViewById(R.id.et_create_task_description);
        taskDate = (TextView) findViewById(R.id.tv_task_select_date);
        taskTime = (TextView) findViewById(R.id.tv_task_select_time);
        taskPriority = (Spinner) findViewById(R.id.spinner_priority);
        taskStatus = (Spinner) findViewById(R.id.spinner_status);
        createTaskForm = (AppCompatButton) findViewById(R.id.create_task_form_button);
    }


    void initVariable() {
        title = taskTitle.getText().toString();
        date = taskDate.getText().toString();
        time = taskTime.getText().toString();
        priority = taskPriority.getSelectedItem().toString();
        status = taskStatus.getSelectedItem().toString();
        description = taskDescription.getText().toString();
    }

    void navigateTo(Class activityClass) {
        intent = new Intent(CreateTaskActivity.this,activityClass);
        startActivity(intent);
        finish();
    }
}
