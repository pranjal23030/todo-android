package com.example.namiminiproject.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.namiminiproject.database.entities.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks();

    @Query("UPDATE tasks SET title = :title, dueDate = :date, dueTime = :time, priority = :priority, status = :status, description = :description WHERE id = :id")
    void updateTodo(String title, String date, String time, String priority, String status, String description, int id);

}
