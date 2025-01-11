package com.example.namiminiproject.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tasks")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "description")
    @NonNull
    private String description;

    @ColumnInfo(name = "priority")
    @NonNull
    private String priority;

    @ColumnInfo(name = "status")
    @NonNull
    private String status;

    @ColumnInfo(name = "dueDate")
    @NonNull
    private String dueDate;

    @ColumnInfo(name = "dueTime")
    @NonNull
    private String dueTime;

    public Task(String title, String description, String priority, String status, String dueDate, String dueTime) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
}
